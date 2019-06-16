/**
 * Nodos que constan de un elemento generico E y una referencia a otro nodo.
 * 3 constructores: Uno que establece un Dummy node
 * 					Otro que establece el item del nodo, y referencia nula hacia el siguiente
 * 					Otro que establece el item del nodo y una referencia hacia el nodo siguiente
 * */

class Nodo<E> {

    //ATRIBUTOS
    private E elemento;
    private Nodo<E> siguiente;

    //CONSTRUCTORES

    public Nodo(){
        this(null,null);
    } //Dummy node

    public Nodo(E item){
        this(item,null);
    }

    public Nodo(E item, Nodo<E> sig){
        elemento=item; siguiente=sig;
    }

    //Metodos
    /**Establace un elemento al nodo*/
    public void setElemento(E item){elemento=item;}

    //CONSULTAS
    /**Retorna el elemento del nodo*/
    public E getElemento(){
        return elemento;
    }

    /**Retorna la referencia al nodo siguiente al nodo que se hace la llamada.*/
    public Nodo<E> getSiguiente(){
        return siguiente;
    }

    /**Establace la referencia hacia el nodo siguiente*/
    public void setSiguiente(Nodo<E> sig) {
        siguiente=sig;
    }

}

interface Stack<E>{

    /**
     * Consulta la cantidad de elementos de la pila.
     * @return Cantidad de elementos de la pila.
     */
    int size();

    /**
     * Consulta si la pila est� vac�a.
     * @return Verdadero si la pila est� vac�a, falso en caso contrario.
     */
    boolean isEmpty();

    /**
     * Examina el elemento que se encuentra en el tope de la pila.
     * @return Elemento que se encuentra en el tope de la pila.
     * @throws EmptyStackException si la pila est� vac�a.
     */
    E top()throws EmptyStackException;

    /**
     * Inserta un elemento en el tope de la pila.
     * @param element Elemento a insertar.
     */
    public void push(E element);

    /**
     * Remueve el elemento que se encuentra en el tope de la pila.
     * @return Elemento removido.
     * @throws EmptyStackException si la pila est� vac�a.
     */
    E pop() throws EmptyStackException;
}

class EmptyStackException extends Exception{
    public EmptyStackException(String msg){super(msg);}

}

class LinkedStack<E> implements Stack<E> {

    //ATRIBUTOS
    protected Nodo<E> head;
    protected int tamaño;

    //CONSTRUCTOR
    /**
     * El constructor establece el nodo "head" en nulo y el tamaño de la pila en 0
     * */
    public LinkedStack(){head=null; tamaño=0;}

    //METODOS

    /**Coloca un nuevo nodo en la pila enlazada, haciendolo el head y aumentando el tamñano de la pila en 1*/
    public void push(E item){
        Nodo<E> aux= new Nodo<E>(item,head);
        head=aux;
        tamaño++;
    }

    //CONSULTAS
    /** Retorna el tamaño de la pila enlazada*/
    public int size(){
        return tamaño;
    }

    /**
     * Retorna true si la pila enlazada esta vacía, false en caso contrario
     * */
    public boolean isEmpty(){return tamaño==0;}

    /**
     * Elimina el elemento tope de la pila (nodo head) y lo retorna
     * 	Si la pila esta vacía lanza una excepcion.
     * */
    public E pop() throws EmptyStackException{
        if (isEmpty())
            throw new EmptyStackException("Pila vacia");
        E aux= head.getElemento();
        head=head.getSiguiente();
        tamaño--;
        return aux;
    }

    /**
     *  retorna el elemnto de tope de la pila (nodo head).
     * 	Si la pila esta vacía lanza una excepcion*/
    public E top() throws EmptyStackException{
        if (isEmpty())
            throw new EmptyStackException("Pila vacia");
        return head.getElemento();
    }

}

