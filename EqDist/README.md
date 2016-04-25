#Равноудаленные вершины
Составьте программу, выполняющую поиск всех вершин неориентированного простого графа, равноудалённых от вершин v1, v2, …, vk
(эти вершины мы будем называть опорными).

Программа должна считывать со стандартного потока ввода количество вершин графа N
, количество рёбер M, данные о рёбрах графа, количество опорных вершин K и номера опорных вершин. При этом каждое ребро кодируется номерами инцидентных ему вершин u и v такими, что 0≤u,v<N−1
.

В результате работы программы в стандартном потоке вывода должна находиться отсортированная по возрастанию последовательность номеров вершин графа, равноудалённых от опорных вершин. Если таких вершин нет, программа должна выводить «минус».

Программа должна хранить граф в памяти в виде списков инцидентности.