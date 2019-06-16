public class Ejercicio1 {


    /**
     * Implementado un recorrido por niveles o breadth-first search(BFS)
     * */
    public Vertex<Integer>[] breadhFirstSearch(GraphD<Integer,Integer> grafo) throws EmptyQueueException, InvalidVertexException, InvalidEdgeException {
        Iterable<Vertex<Integer>> vertices = grafo.vertices();
        char [] color = new char[grafo.totalVertex()];
        Queue<Vertex<Integer>> cola = new ArrayQueue<Vertex<Integer>>();
        Vertex<Integer> [] foresta = new Vertex[grafo.totalVertex()];

        for (Vertex<Integer> vertice: vertices) {
            color[vertice.element()] = 'B';
        }

        for (Vertex<Integer> vertice: vertices) {
            if (color[vertice.element()] == 'B') {
                color[vertice.element()] = 'G';
                cola.enqueue(vertice);
                visitarBF(grafo,cola,color,foresta);
            }
        }
        return foresta;
    }

    private Vertex<Integer>[] visitarBF(GraphD<Integer,Integer> grafo, Queue<Vertex<Integer>> cola, char[] color, Vertex<Integer>[] foresta) throws EmptyQueueException, InvalidVertexException, InvalidEdgeException {
        Vertex<Integer> verticeA;
        Vertex<Integer> verticeB;
        Iterable<Edge<Integer>> arcosAdyacentes;
        boolean existeAdyacenteBlanco = false;

        while(!cola.isEmpty()) {
            verticeA = cola.front();
            arcosAdyacentes = grafo.succesorEdges(verticeA);

            for(Edge<Integer> arco: arcosAdyacentes) {
                verticeB = grafo.opposite(verticeA, arco);
                if (color[verticeB.element()] == 'B') {
                    existeAdyacenteBlanco = true;
                    break;
                }

                if (existeAdyacenteBlanco) {
                    color[verticeB.element()] = 'G';
                    foresta [verticeA.element()] = verticeB;
                    cola.enqueue(verticeB);
                } else {
                    color[verticeA.element()] = 'N';
                    cola.dequeue();
                }
            }
        }
        return foresta;
    }
}
