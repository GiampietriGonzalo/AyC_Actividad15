public class Ejercicio1 {


    //Solución con BFS

    /**
     * Determinar si un grafo no-dirigido y pesado es conexo implementado un recorrido por niveles o breadth-first search(BFS).
     * FALTA VER COMO CHECKEAR SI ES CONVEXO DURANTE EL RECORRIDO.
     * */
    public Vertex<Integer>[] breadthFirstSearch(Graph<Integer,Integer> grafo) throws EmptyQueueException, InvalidVertexException, InvalidEdgeException {
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

    private Vertex<Integer>[] visitarBF(Graph<Integer,Integer> grafo, Queue<Vertex<Integer>> cola, char[] color, Vertex<Integer>[] foresta) throws EmptyQueueException, InvalidVertexException, InvalidEdgeException {
        Vertex<Integer> verticeA;
        Vertex<Integer> verticeB;
        Iterable<Edge<Integer>> arcosAdyacentes;
        boolean existeAdyacenteBlanco = false;

        while(!cola.isEmpty()) {
            verticeA = cola.front();
            arcosAdyacentes = grafo.incidentEdges(verticeA);

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

    //Solucion con DisjointSet

    public boolean esConexo(Graph<Integer,Integer> grafo) {
        DisjointSet conjunto = new DisjointSet(grafo.totalVertex());

        for (Vertex<Integer> vertice: grafo.vertices()) {
            conjunto.makeSet(vertice.element());
        }

        return conjunto.esConexo();
    }

}
