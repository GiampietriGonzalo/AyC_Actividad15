/**
 * DisjointSet con compression path y union-by-rank.
 * */

public class DisjointSet {

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
		rank[element] = 0;
	}

	public int findSet(int element) {
		if (parent[element] != element)
			parent[element] = findSet(parent[element]);
		return parent[element];
	}
	
	public void union(int elementX, int elementY) {
		link(findSet(elementX), findSet(elementY));
	}
	
	private void link(int elementX, int elementY) {
		if (rank[elementX] > rank[elementY])
			parent[elementY] = elementX;
		else
			parent[elementX] = elementY;
		
		if (rank[elementX] == rank[elementY])
			rank[elementY] += 1;
	}

	/**
	 * Determian si el grafo en el DisjointSet es conexo
	 * */
	/*public boolean isRelated() {
		int root = findSet(parent[0]);
		boolean isRelated = true;

		for (int i = 1; i < parent.length; i++) {
			if (findSet(parent[i]) != root)
				isRelated = false;
		}
		
		return isRelated;
	}*/


}
