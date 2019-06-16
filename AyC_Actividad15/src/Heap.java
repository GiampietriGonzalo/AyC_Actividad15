public interface Heap {
    void insert(int element);
    int get();
}

class MinHeap implements Heap {

    private int [] heap;
    private int actualSize;
    private int maximumSize;
    private final int firstElement = 0;
    private final int Front = 1;

    public MinHeap(int maximumSize) {
        this.maximumSize = maximumSize;
        heap = new int[maximumSize];
        this.actualSize = 0;
    }

    /**
     * Inserta un elemento al heap
     * */
    public void insert(int element) {
        if (actualSize < maximumSize) {
            actualSize++;
            heap[actualSize] = element;
            int node = actualSize;
            int parent = getParent(node);

            while (heap[node] < heap[parent]){
                swap(node, parent);
                node = parent;
                parent = getParent(node);
            }
        }
    }

    /**
     * Retorna el mínimo elemento del heap.
     * */
    public int get(){
        int minimalElement = heap[firstElement];
        heap[firstElement] = heap[actualSize--];

        return minimalElement;
    }

    /**
     * Se reordena el heap en funcion del nodo pasado.
     * */
    private void heapify(int node) {
        if (!isLeaf(node)) {
            if (heap[node] > heap[leftChild(node)] || heap[node] > heap[rightChild(node)]) {
                if (heap[leftChild(node)] < heap[rightChild(node)]) {
                    swap(node, leftChild(node));
                    heapify(leftChild(node));
                } else {
                    swap(node, rightChild(node));
                    heapify(rightChild(node));
                }
            }
        }
    }

    /**
     * Retorna el hijo derecho del nodo pasado.
     * */
    private int rightChild(int node) {
        return (2 * node) + 1;
    }

    /**
     * Retorna el hijo izquierdo del nodo pasado.
     * */
    private int leftChild(int node) {
        return (2 * node);
    }

    /**
     * Retorna True si el nodo pasado es una hoja del heap.
     * Retorna False en caso contrario.
     * */
    private boolean isLeaf(int node) {
        boolean result = false;
        if (node >= (actualSize / 2) && node <= actualSize) {
            result = true;
        }
        return result;
    }

    /**
     * Retorna el padre del nodo pasado.
     * */
    private int getParent(int childPosition) {
        return childPosition / 2;
    }

    /**
     * Intercambia de posicion dos elementos en el heap.
     * */
    private void swap(int node1, int node2) {
        int aux = heap[node1];
        heap[node1] = heap[node2];
        heap[node2] = aux;
    }
}

