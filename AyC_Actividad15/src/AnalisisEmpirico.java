import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

	public class AnalisisEmpirico{
		
		public static void main(String[] args) throws IOException {
			
			try{
				Graph<Integer,Integer> grafoNoDirigido = makeGraph(500,40000);

				Vertice<Integer,Integer> nodoA;
				Vertice<Integer,Integer> nodoB;
				Arco<Integer,Integer> arco;

				for(Edge<Integer> edge: grafoNoDirigido.edges()) {
					arco = (Arco<Integer, Integer>)edge;
					nodoA = arco.getPredecesor();
					nodoB = arco.getSucesor();
					System.out.println ("A = " + nodoA.element()  + " B = " + nodoB.element() + " PesoArco = " + arco.element());
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			
			/*
			 * Generar varios grafos de diferente configuraci�n y buscar 
			 * �rbol de cubrimiento minimal para cada uno. 
			 * 
			 * Medir el rendimiento usando timestamps.
			 * 
			 */
			
			
		}

		private static GrafoWS getGrafo(int nodos, int arcos) throws Exception {

			String consulta = "curl http://cs.uns.edu.ar/~mom/AyC2019/grafo.php?nodos="+nodos+"&arcos="+arcos+"&conexo=1";
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

		private static Graph<Integer,Integer> makeGraph(int nodos, int arcos) throws Exception {
			GrafoWS graphFromWebService = getGrafo(nodos, arcos);
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
