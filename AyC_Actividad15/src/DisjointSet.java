interface IDisjointSet {
	int find(int i);
	void union(int i, int j);
}


class ClassicDisjointSet implements IDisjointSet {

	int[] parent;
	int n;

	public ClassicDisjointSet (int n) {
		parent = new int[n];
		this.n = n;
		makeSet();
	}

	private void makeSet()
	{
		for (int i = 0; i < n; i++) {
			this.parent[i] = i;
		}
	}

	// Finds the representative of the set that i is an element of
	public int find(int x)
	{
		if (parent[x] == x)
		{
			return x;
		}
		else
		{
			return find(parent[x]);
		}
	}

	// Unites the set that includes i and the set that includes j
	public void union(int x, int y)
	{
		int i_representative = this.find(x);
		int j_representative = this.find(y);

		parent[i_representative] = j_representative;
	}

}

/**
 * Esta clase implementa Disjoint-Set con Union by Rank y Path compression.
 */
class ImprovedDisjointSet implements IDisjointSet {

	int[] parent, rank;
	int n;

	public ImprovedDisjointSet (int n) {
		parent = new int[n];
		rank = new int[n];
		this.n = n;
		makeSet();
	}

	private void makeSet()
	{
		for (int i = 0; i < n; i++) {
			this.parent[i] = i;
		}
	}

	public int find(int x)
	{
		if (parent[x] != x) {
			parent[x] = find(parent[x]);
		}

		return parent[x];
	}

	public void union(int x, int y)
	{
		int xRoot = find(x), yRoot = find(y);

		if (xRoot == yRoot)
			return;

		if (rank[xRoot] < rank[yRoot])
			parent[xRoot] = yRoot;
		else
			if (rank[yRoot] < rank[xRoot])
				parent[yRoot] = xRoot;
			else {
				parent[yRoot] = xRoot;
				rank[xRoot] = rank[xRoot] + 1;
			}
	}

}

class MainDisjointSet {
	public static void main(String[] args)
	{
		// Let there be 5 persons with ids as
		// 0, 1, 2, 3 and 4
		int n = 5;
		IDisjointSet ds = new ImprovedDisjointSet(n);

		// 0 is a friend of 2
		ds.union(0, 2);

		// 4 is a friend of 2
		ds.union(4, 2);

		// 3 is a friend of 1
		ds.union(3, 1);

		// Check if 4 is a friend of 0
		if (ds.find(4) == ds.find(0))
			System.out.println("Is 4 friend of 0 ? Yes");
		else
			System.out.println("Is 4 friend of 0 ? No");

		// Check if 1 is a friend of 0
		if (ds.find(1) == ds.find(0))
			System.out.println("Is 1 friend of 0 ? Yes");
		else
			System.out.println("Is 1 friend of 0 ? No");
	}
}
