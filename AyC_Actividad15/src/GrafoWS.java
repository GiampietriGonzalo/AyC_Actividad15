import java.util.ArrayList;

class GrafoWS {
	private int[] nodos;
	private ArrayList<Pesado> arcos;

	class Pesado {
		private Arco arco;
		private int peso;
		
		private Pesado(ArrayList<Integer> arcoLista, int peso) {
			this.arco = new Arco(arcoLista.get(0), arcoLista.get(1));
			this.peso = peso;
		}

		public Arco getArco() {
			return arco;
		}

		public int getPeso() {
			return peso;
		}

		class Arco {
			private int nodo1;
			private int nodo2;
			
			public Arco(int i, int j) {
				this.nodo1 = i;
				this.nodo2 = j;
			}

			public int getNodo1() {
				return nodo1;
			}

			public int getNodo2() {
				return nodo2;
			}
		}
	}
	
	public int getNodosCount(){
		return this.nodos.length;
	}
	
	public int getArcosCount(){
		return this.arcos.size();
	}

	public int[] getNodos() {
		return nodos;
	}

	public ArrayList<Pesado> getArcos(){
		return arcos;
	}

	@SuppressWarnings("rawtypes")
	public GrafoWS(GrafoObj grafoJson){
		this.nodos = grafoJson.nodos;
		this.arcos = new ArrayList<Pesado>();
		
		Object[][] arcosJson = grafoJson.arcos;
		
		for (int i = 0; i<arcosJson.length; i++){
			
			ArrayList<Integer> arcoLista = new ArrayList<>(); 
			arcoLista.add(((Double) ((ArrayList) arcosJson[i][0]).get(0)).intValue());
			arcoLista.add(((Double) ((ArrayList) arcosJson[i][0]).get(1)).intValue());
			Pesado pesado = new Pesado(arcoLista, ((Double) arcosJson[i][1]).intValue());
			this.arcos.add(pesado); 
		}
	}
	
	public static class GrafoObj {
		int[] nodos;
		Object[][] arcos;
	}

}
