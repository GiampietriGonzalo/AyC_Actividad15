import java.io.IOException;

public class Ejercicio1 {


    public static void main(String[] args) throws IOException {

       try{
        Graph<Integer,Integer> grafo = Service.makeGraph(5,7,1);
        System.out.println("GRAFO CONEXO \n" + grafo.toString());

        if(esConexo(grafo)) {
            System.out.println("El grafo es conexo, FUNCIONA");
        } else {
            System.out.println("El grafo no es conexo, NO FUNCIONA");
        }

        grafo = Service.makeGraph(5, 4,0);
        System.out.println("\nGRAFO NO CONEXO\n" + grafo.toString());

        if(!esConexo(grafo)) {
            System.out.println("El grafo no es conexo, FUNCIONA");
        } else {
            System.out.println("El grafo es conexo, NO FUNCIONA");
        }

       } catch (Exception e) {
           System.out.println(e.getMessage());
       }

    }

    //Solucion con DisjointSet

    //TODO: CORREGIR Y CHECKEAR

    public static boolean esConexo(Graph<Integer,Integer> grafo) {
        DisjointSet conjunto = buildDisjointSet(grafo);
        return conjunto.esConexo();
    }

    private static DisjointSet buildDisjointSet(Graph<Integer,Integer> grafo){
        DisjointSet set = new DisjointSet(grafo.totalVertex());
        Arco<Integer,Integer> arco;

        for (Vertex<Integer> vertice: grafo.vertices()) {
            set.makeSet(vertice.element());
        }

        for(Edge<Integer> edge: grafo.edges()) {
            arco = (Arco<Integer,Integer>) edge;
            set.union(arco.getPredecesor().element(), arco.getSucesor().element());
        }

        for (Vertex<Integer> vertice: grafo.vertices()) {
            set.findSet(vertice.element());
        }

        return set;
    }

    //ALGORTIMO BFS

    //TODO: A MODO DE EJEMPLO, LUEGO ELIMINAR.

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

    // SOLUCION CON BFS

    //TODO: TENGO QUE SEGUIR

    /**
     * Determinar si un grafo no-dirigido y pesado es conexo implementado un recorrido por niveles o breadth-first search(BFS).
     * FALTA VER COMO CHECKEAR SI ES CONVEXO DURANTE EL RECORRIDO.
     * */
    public boolean breadthFirstSearchs(Graph<Integer,Integer> grafo) throws EmptyQueueException, InvalidVertexException, InvalidEdgeException {

        Iterable<Vertex<Integer>> vertices = grafo.vertices();
        Integer [] visitados = new Integer[grafo.totalVertex()];
        boolean esConexo = true;

        for (Vertex<Integer> vertice: vertices) {
            visitados[vertice.element()] = 0;
        }

        for (Vertex<Integer> vertice: vertices) {
            if (visitados[vertice.element()] == 0)
                esConexo = visitarBFS(grafo, vertice ,visitados);
        }
        return esConexo;
    }

    private boolean visitarBFS(Graph<Integer,Integer> grafo, Vertex<Integer> vertice,Integer [] visitados) throws EmptyQueueException, InvalidVertexException, InvalidEdgeException {
        Vertex<Integer> verticeA;
        Vertex<Integer> verticeB;

        Iterable<Edge<Integer>> arcosAdyacentes;
        Queue<Vertex<Integer>> cola = new ArrayQueue<Vertex<Integer>>();
        Vertex<Integer> [] padre = new Vertex[grafo.totalVertex()];

        visitados[vertice.element()] = 1;
        cola.enqueue(vertice);
        padre[vertice.element()] = null;

        boolean esConexo = true;

        while(!cola.isEmpty()) {
            verticeA = cola.dequeue();
            arcosAdyacentes = grafo.incidentEdges(verticeA);

            for(Edge<Integer> arco: arcosAdyacentes) {
                verticeB = grafo.opposite(verticeA, arco);
                if (visitados[verticeB.element()] == 1) {
                    // Si w fue visitado y es padre de v, entonces es la arista de donde vengo (no es ciclo).
                    // Si no es su padre, esta arista (v, w) cierra un ciclo que empieza en w.
                    if (verticeB != padre[verticeA.element()])
                        //TODO: VER BIEN QUE HACER ACA
                        esConexo = reconstruirCiclo(padre,verticeB,verticeA,grafo.totalVertex());
                } else {
                    cola.enqueue(verticeB);
                    visitados[verticeA.element()] = 1;
                    padre[verticeB.element()] = verticeA;
                }
            }
        }
        return esConexo;
    }

    //TODO: VER BIEN QUE HACER CON ESTO
    private boolean reconstruirCiclo(Vertex<Integer>[] padre,  Vertex<Integer> inicio,  Vertex<Integer> fin, int size) {
        Vertex<Integer> vertice = fin;
        Vertex<Integer> [] camino = new Vertex[size];
        int i=0;
        while (vertice != inicio && i<camino.length) {
            camino[i] = vertice;
            vertice = padre[vertice.element()];
            i++;
        }

        return true;
    }

    /*
    def reconstruir_ciclo(padre, inicio, fin):
    v = fin
    camino = []
            while v != inicio:
            camino.append(v)
    v = padre[v]
            camino.append(inicio)
            return camino.invertir()
    */

}
