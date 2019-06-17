import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Service {

    private static GrafoWS getGrafo(int nodos, int arcos, int conexo) throws Exception {

        String consulta = "curl http://cs.uns.edu.ar/~mom/AyC2019/grafo.php?nodos="+nodos+"&arcos="+arcos+"&conexo="+conexo;
        Process process = Runtime.getRuntime().exec(consulta);
        InputStream inputSt = process.getInputStream();
        @SuppressWarnings("resource")
        Scanner s = new Scanner(inputSt).useDelimiter("\\A");
        String jsonString = s.hasNext() ? s.next() : "";
        System.out.println("Tengo el grafo en formato JSON. Lo convierto...");
        Gson gson = new GsonBuilder().create();
        try{
            GrafoWS.GrafoObj gr = gson.fromJson(jsonString, GrafoWS.GrafoObj.class);
            return new GrafoWS(gr);
        } catch (Exception e) {
            throw new Exception(jsonString);
        }
    }

    public static Graph<Integer,Integer> makeGraph(int nodos, int arcos, int conexo) throws Exception {
        GrafoWS graphFromWebService = getGrafo(nodos, arcos, conexo);
        ArrayList<GrafoWS.Pesado> arcosWS = graphFromWebService.getArcos();

        Graph<Integer,Integer> grafo = new GrafoNoDirigido();

        int pesoAux=0;
        int nodoAuxA, nodoAuxB;
        GrafoWS.Pesado.Arco arcoAux;

        Vertex<Integer> nodeA;
        Vertex<Integer> nodeB;
        Arco<Integer,Integer> arco;

        for(GrafoWS.Pesado arcoPesado: arcosWS) {
            pesoAux = arcoPesado.getPeso();
            arcoAux = arcoPesado.getArco();
            nodoAuxA = arcoAux.getNodo1();
            nodoAuxB = arcoAux.getNodo2();

            nodeA = grafo.insertVertex(nodoAuxA);
            nodeB = grafo.insertVertex(nodoAuxB);
            grafo.insertEdge(nodeA, nodeB, pesoAux);
        }
        return  grafo;
    }
}
