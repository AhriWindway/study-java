import java.util.*;
import java.util.function.IntPredicate;
 
class Position {
    protected String text;
    protected int index;
 
    public Position(String text) {
        this(text, 0);
    }
 
    private Position(String text, int index) {
        this.text = text;
        this.index = index;
    }
 
    public int getChar() {
        return index < text.length() ? text.codePointAt(index) : -1;
    }
 
    public boolean satisfies(IntPredicate p) {
        return p.test(getChar());
    }
 
    public Position skip() {
        int c = getChar();
        switch (c) {
            case -1: return this;
            default: return new Position(text, index + (c > 0xFFFF ? 2 : 1));
        }
    }
 
    public Position skipWhile(IntPredicate p) {
        Position pos = this;
        while (pos.satisfies(p)) pos = pos.skip();
        return pos;
    }
}
 
class SyntaxError extends Exception {
    public SyntaxError(Position pos, String msg) {
        //super(String.format("Syntax error at %s: %s", pos.index, msg));
        super("error");
    }
}
 
enum Tag {
    MUL,
    DIV,
    SUB,
    ADD,
    LPAREN,
    RPAREN,
    NUMBER, 
    IDENT,
    END_OF_TEXT;
}
 
class Token {
    protected Tag tag;
    protected Position start, follow;
    protected static HashSet<String> names = new HashSet<>();
 
    public Token(String text) throws SyntaxError {
        this(new Position(text));
    }
 
    private Token(Position cur) throws SyntaxError {
        start = cur.skipWhile(Character::isWhitespace);
        follow = start.skip();
        if (start.index >= follow.text.length()) tag = Tag.END_OF_TEXT;
        else {
            switch (start.getChar()) {
                case '(':
                    tag = Tag.LPAREN;
                    break;
                case ')':
                    tag = Tag.RPAREN;
                    break;
                case '+':
                    tag = Tag.ADD;
                    break;
                case '-':
                    tag = Tag.SUB;
                    break;        
                case '*':
                    tag = Tag.MUL;
                    break;
                case '/':
                    tag = Tag.DIV;
                    break;                	
                default:
                    if (start.satisfies(Character::isLetter)) {
                        follow = follow.skipWhile(Character::isLetterOrDigit);
                        tag = Tag.IDENT;
                        names.add(start.text.substring(start.index, follow.index));
                    } else if (start.satisfies(Character::isDigit)) {
                        follow = follow.skipWhile(Character::isDigit);
                        if (follow.satisfies(Character::isLetter)) {
                            throw new SyntaxError(follow, "delimiter expected");
                        }
                        tag = Tag.NUMBER;
                    } else {
                        throwError("invalid character");
                    }
           }
        }
    }
 
    public void throwError(String msg) throws SyntaxError {
        throw new SyntaxError(start, msg);
    }
 
    public boolean matches(Tag ...tags) {
        return Arrays.stream(tags).anyMatch(t -> tag == t);
    }
 
    public Token next() throws SyntaxError {
        return new Token(follow);
    }
}

class var {
	protected String name;
	protected int value;
	public var(String name, int value) { this.name = name; this.value = value; }
	public String toString() {return "" + name + " = " + value;}
} 
public class Calc{
    private static Token sym;
	private static ArrayList<var> vars = new ArrayList<>();
	private static Scanner in = new Scanner(System.in);
	
    private static void expect(Tag tag) throws SyntaxError {
        if (!sym.matches(tag)) sym.throwError(tag.toString() + " expected");
        sym = sym.next();
    }
 
    public static void main(String[] args) {
		try { System.out.println(parse(in.nextLine())); }
        catch (SyntaxError e) { 
			System.out.println(e.getMessage()); 
		}	
    }

    private static int parse(String txt) throws SyntaxError {	
		sym = new Token(txt);
		//System.out.println(txt);
		int res = parseE(0);
		//System.out.println(txt.substring(sym.start.index, sym.follow.index));
		expect(Tag.END_OF_TEXT);
		return res;
    }
 
    private static int parseE(int res) throws SyntaxError {
        res = parseT(res);
        res = parseEE(res);
        return res;
    }
 
    private static int parseT(int res) throws SyntaxError {
        res = parseF(res);
        res = parseTT(res);
        return res;
    }
 
    private static int parseF(int res) throws SyntaxError {
	    String buf = sym.start.text.substring(sym.start.index, sym.follow.index);
		if (sym.matches(Tag.NUMBER)) {
            res = Integer.valueOf(buf);
            sym = sym.next();
            return res;
        }
        if (sym.matches(Tag.LPAREN)) {
            expect(Tag.LPAREN);
			int count = 0;
			Token buffer = sym;
            for (int i = sym.start.index; i < sym.start.text.length(); i++) {
				if ((sym.start.text.charAt(i) == ')') && (count == 0)) {
					res = parse(sym.start.text.substring(sym.start.index, i));
					break;
				}
				if (sym.start.text.charAt(i) == ')') count--;
				if (sym.start.text.charAt(i) == '(') count++;
				if (i == sym.start.text.length() - 1) sym.throwError("paren");
			}
			
			count = 0;
			while (true) {
				if ((buffer.tag == Tag.RPAREN) && (count == 0)) break;
				if (buffer.tag == Tag.LPAREN) count++;
				if (buffer.tag == Tag.RPAREN) count--;
				buffer = buffer.next();
			}
			
			sym = buffer;
            expect(Tag.RPAREN);
            return res;
        }
		if (sym.matches(Tag.IDENT)) {
			sym = sym.next();
			for (var x: vars) if (x.name.equals(buf)) return x.value;	
			int x = in.nextInt();
			vars.add(new var(buf, x));
			return x;
		}

        if (sym.matches(Tag.SUB)) {
            sym = sym.next();
			return - parseF(res);
        } 
        sym.throwError("F - problems");
        
        return 0;
    }
	
    private static int parseEE(int res) throws SyntaxError {
        if (sym.matches(Tag.ADD)) {
            sym = sym.next();
            res += parseT(res);
            res = parseEE(res);
        } 
        if (sym.matches(Tag.SUB)) {
            sym = sym.next();
            res -= parseT(res);
            res = parseEE(res);
        }
        return res;
    }
	
    private static int parseTT(int res) throws SyntaxError{
        if (sym.matches(Tag.MUL)) {
            sym = sym.next();
            res *= parseF(res);
            res = parseTT(res);
        } 
        if (sym.matches(Tag.DIV)) {
            sym = sym.next();
            res /= parseF(res);
            res = parseTT(res);
        }        
        return res;
    }
}
