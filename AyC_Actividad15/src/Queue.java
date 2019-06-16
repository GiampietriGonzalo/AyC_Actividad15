interface Queue<E> {

    /**
     * Encola el elemento pasado por parametro.
     * @param elemento a encolar.
     * */
    void enqueue(E elemento);

    /**
     *Desencola el primer elemento y lo retorna.
     *@return elemento desencolado.
     * */
    E dequeue() throws EmptyQueueException;

    /**
     * Retorna y no desencola el primer elemento de la cola.
     * @return el primer elemento de la cola.
     * */
    E front() throws EmptyQueueException;

    /**
     * Determina si la cola esta vacia.
     * @return true si la cola esta vacia, false en caso contrario.
     * */
    boolean isEmpty();

    /**
     * Determina la cantidad de elementos que hay en la cola.
     * @return cantidad de elementos de la cola
     * */
    int size();
}

class EmptyQueueException extends Exception{
    public EmptyQueueException(String msg){super(msg);}
}

class ArrayQueue<E> implements Queue<E>{
    //Atributos
    private E [] D;
    private int f,r;;
    private int n=10000;

    //Constructor
    public ArrayQueue(){
        D= (E[])new Object[n];
        f=0;
        r=0;
    }

    //Metodos
    public void enqueue(E item){
        if ( ! (size()==n-1)){
            D[r]=item;
            r= (r+1) % n;
        }
    }

    //Consultas
    public boolean isEmpty(){return f==r;}
    public int size(){return (n-f+r)%n;}
    public E dequeue() throws EmptyQueueException{
        if (isEmpty())
            throw new EmptyQueueException("Cola vacía");
        E aux=D[f];
        D[f]=null;
        f=(f+1)%n;

        return aux;
    }

    public E front() throws EmptyQueueException{
        if(isEmpty())
            throw new EmptyQueueException("Cola vacía");
        return D[f];
    }
}
