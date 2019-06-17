//TODO FALTA DISJOINTSET SIN HEURISTICA

/**
 * DisjointSet con compression path y union-by-rank.
 * */
class DisjointSet {

	private int[] parent;
	private int[] rank;
	private int size;
	
	public DisjointSet(int maximumElements) {
		parent = new int [maximumElements];
		rank = new int[maximumElements];
		size = maximumElements;
		
		for (int i=0; i<maximumElements; i++) {
			makeSet(i);
		}
	}
	
	public void makeSet(int element){
		parent[element] = element;
		rank[element] = 1;
	}

	public int findSet(int element) {
		if (parent[element] != element)
			parent[element] = findSet(parent[element]);
		return parent[element];
	}
	
	public void union(int elementX, int elementY) {
		int x = findSet(elementX);
		int y = findSet(elementY);

		if (rank[x] > rank[x])
			parent[y] = x;
		else
			parent[x] = y;

		if (rank[x] == rank[y])
			rank[y] += 1;
	}

	/**
	 * Determina si el grafo en el DisjointSet es conexo
	 * CHECKEAR
	 * */
	public boolean esConexo() {
		int root = findSet(parent[0]);
		boolean esConexo = true;

		int j = 0;
		while (esConexo && j<parent.length) {
			if(findSet(j) != root)
				esConexo = false;
			j++;
		}

		
		return esConexo;
	}

	public String toString() {
		String toReturn = "";
		for (int i=0; i<parent.length; i++)
			toReturn += "nodo:" + i + " parent:" + parent[i] + "\n";

		return toReturn;
	}
}
