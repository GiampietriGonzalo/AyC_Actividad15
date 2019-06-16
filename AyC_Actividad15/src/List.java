import java.util.*;

class DNode<E> implements Position<E>{
    //Atributos
    E elemento;
    DNode<E> siguiente;
    DNode<E> anterior;

    //Constructor
    public DNode(){this(null,null,null);}	//Dummy Node

    public DNode(E elem, DNode<E> ant, DNode<E> sig){
        elemento=elem;
        anterior=ant;
        siguiente=sig;
    }

    //Comandos
    public void setSiguiente(DNode<E> sig){siguiente=sig;}
    public void setAnterior(DNode<E> ant){anterior=ant;}
    public void setElemento(E elem){elemento=elem;}

    //Consultas
    public E element(){return elemento;}
    public DNode<E> getSiguiente(){return siguiente;}
    public DNode<E> getAnterior() {return anterior;}
}

class EmptyListException extends Exception{

    /**
     * Construye una nueva EmptyListException con el mensaje pasado como parametro.
     * @param m, mensaje de la exception.
     * */
    public EmptyListException(String m){
        super(m);
    }

}

interface PositionList<E> extends Iterable<E>{

    /**
     * Inserta un elemento al principio de la lista.
     * @param e Elemento a insertar al principio de la lista.
     */
    void addFirst(E e);

    /**
     * Inserta un elemento al final de la lista.
     * @param e Elemento a insertar al final de la lista.
     */
    void addLast(E e);

    /**
     * Inserta un elemento luego de la posicion pasada por paramatro.
     * @param pos Posicion en cuya posicion siguiente se insertara el elemento pasado por parametro.
     * @param e Elemento a insertar luego de la posicion pasada como parametro.
     * @throws InvalidPositionException si la posicion es invalida o la lista esta vacia.
     */
    void addAfter(Position<E> pos, E e)throws InvalidPositionException;

    /**
     * Inserta un elemento antes de la posicion pasada como parametro.
     * @param pos Posicion en cuya posicion anterior se insertara el elemento pasado por parametro.
     * @param e Elemento a insertar antes de la posicion pasada como parametro.
     * @throws InvalidPositionException si la posicion es invalida o la lista esta vacia.
     */
    void addBefore(Position<E> pos, E e)throws InvalidPositionException;

    /**
     * Devuelve la posicion del primer elemento de la lista.
     * @return Posicion del primer elemento de la lista.
     * @throws EmptyListException si la lista esta vacia.
     */
    Position<E> first()throws EmptyListException;

    /**
     * Devuelve la posicion del ultimo elemento de la lista.
     * @return Posicion del ultimo elemento de la lista.
     * @throws EmptyListException si la lista esta vacia.
     *
     */
    Position<E> last()throws EmptyListException;

    /**
     * Devuelve la posicion del elemento anterior a la posicion pasada por parametro.
     * @param pos Posicion a obtener su elemento anterior.
     * @return Posicion del elemento anterior a la posicion pasada por parametro.
     * @throws InvalidPositionException si la posicion pasada por parametro es invalida o la lista esta vacia.
     * @throws BoundaryViolationException si la posicion pasada por parametro corresponde al primer elemento de la lista.
     */
    Position<E> prev(Position<E> pos)throws InvalidPositionException, BoundaryViolationException;

    /**
     * Devuelve la posicion del elemento siguiente a la posicion pasada por parametro.
     * @param pos Posicion a obtener su elemento siguiente.
     * @return Posicion del elemento siguiente a la posicion pasada por parametro.
     * @throws InvalidPositionException si el posicion pasada por parametro es invalida o la lista esta vacia.
     * @throws BoundaryViolationException si la posicion pasada por parametro corresponde al ultimo elemento de la lista.
     */
    Position<E> next(Position<E> pos)throws InvalidPositionException, BoundaryViolationException;

    /**
     * Establece el elemento en la posicion pasados por parametro. Reemplaza el elemento que se encontraba anteriormente en esa posicion y devuelve el elemento anterior.
     * @param pos Posicion a establecer el elemento pasado por parametro.
     * @param e Elemento a establecer en la posicion pasada por parametro.
     * @return Elemento anterior.
     * @throws InvalidPositionException si la posicion es invalida o la lista esta vacia.
     */
    E set(Position<E> pos, E e)throws InvalidPositionException;

    /**
     * Remueve el elemento que se encuentra en la posicion pasada por parametro.
     * @param pos Posicion del elemento a eliminar.
     * @return element Elemento removido.
     * @throws InvalidPositionException si la posicion es invalida o la lista esta vacia.
     */
    E remove(Position<E> pos)throws InvalidPositionException;

    /**
     * Consulta la cantidad de elementos de la lista.
     * @return Cantidad de elementos de la lista.
     */
    int size();

    /**
     * Consulta si la lista esta vacia.
     * @return Verdadero si la lista esta vacia, falso en caso contrario.
     */
    boolean isEmpty();
    Iterable<Position<E>> positions();
}

class InvalidPositionException extends Exception {
    public InvalidPositionException(String m){super(m);}

}

class BoundaryViolationException extends Exception{

    /**
     * Construye un nuevo BoundaryViolationException con el mensaje pasado como parametro.
     * @param m mensaje de la excepcion.
     * */
    public BoundaryViolationException(String m){super(m);}

}

class DoubleLinkedList<E> implements PositionList<E>{

    //Atributos
    DNode<E> header;
    DNode<E> trailer;
    int longitud;


    //Constructor
    public DoubleLinkedList(){
        header=new DNode<E>(null,null,null);
        trailer= new DNode<E>(null,null,null);
        header.setSiguiente(trailer);
        trailer.setAnterior(header);
        longitud=0;
    }


    //Metodos
    public void addFirst(E e){

        DNode<E>nuevo=new DNode<E>(e,header,header.getSiguiente());
        (header.getSiguiente()).setAnterior(nuevo);
        header.setSiguiente(nuevo);
        longitud++;
    }

    public void addLast(E e){
        DNode<E> nuevo= new DNode<E>(e,trailer.getAnterior(),trailer);
        trailer.getAnterior().setSiguiente(nuevo);
        trailer.setAnterior(nuevo);
        longitud++;

    }

    public void addAfter(Position<E> pos, E e)throws InvalidPositionException{
        DNode<E> n=checkPosition(pos);
        DNode<E> nuevo=new DNode<E>(e,n,n.getSiguiente());
        ((DNode<E>)n).getSiguiente().setAnterior(nuevo);
        n.setSiguiente(nuevo);
        longitud++;
    }

    public void addBefore(Position<E> pos, E e)throws InvalidPositionException{
        DNode<E> n=checkPosition(pos);
        DNode<E> nuevo= new DNode<E>(e,n.getAnterior(),n);
        n.getAnterior().setSiguiente(nuevo);
        n.setAnterior(nuevo);
        longitud++;
    }

    //Consultas
    public boolean isEmpty(){return longitud==0;}

    public int size(){return longitud;}

    public Position<E> first()throws EmptyListException{
        if(isEmpty())
            throw new EmptyListException("Lista vacia");
        return header.getSiguiente();
    }


    public Position<E> last() throws EmptyListException{
        if(isEmpty())
            throw new EmptyListException("Lista vacia");
        return trailer.getAnterior();
    }

    public Position<E> prev(Position<E> pos)throws InvalidPositionException, BoundaryViolationException{

        DNode<E> n=checkPosition(pos);
        if (n.getAnterior()==header)
            throw new BoundaryViolationException("Es el primer nodo");
        return n.getAnterior();
    }



    public Position<E> next(Position<E> pos)throws InvalidPositionException, BoundaryViolationException{
        DNode<E> n=checkPosition(pos);
        if (n.getSiguiente()==trailer)
            throw new BoundaryViolationException("Es el ultimo nodo");
        return n.getSiguiente();

    }

    public E remove(Position<E> pos)throws InvalidPositionException{

        DNode<E> n=checkPosition(pos);
        E toReturn=n.element();
        n.getAnterior().setSiguiente(n.getSiguiente());
        n.getSiguiente().setAnterior(n.getAnterior());
        n.setAnterior(null);
        n.setSiguiente(null);
        longitud--;
        return toReturn;
    }

    public E set(Position<E> pos, E e)throws InvalidPositionException{
        DNode<E> n= checkPosition(pos);
        E aux=n.element();
        n.setElemento(e);
        return aux;
    }

    private DNode<E> checkPosition(Position<E> pos)throws InvalidPositionException{
        try{
            if(pos==null)
                throw new InvalidPositionException("Posicion nula");
            if(pos==header)
                throw new InvalidPositionException("Posicion Invalida, es el header");
            if (pos==trailer)
                throw new InvalidPositionException("Posicion Invalida, es el trailer");
            if (((DNode<E>)pos).getSiguiente()==null)
                throw new InvalidPositionException("Posicion Invalida, no peretenece a esta lista");
            if(((DNode<E>)pos).getAnterior()==null)
                throw new InvalidPositionException("Posicion Invalida, no pertenece a esta lista");

            return (DNode<E>)pos;
        }
        catch(ClassCastException e){throw new InvalidPositionException("error de casteo");}

    }

    public PositionList<Position<E>> positions(){
        PositionList<Position<E>> lis= new DoubleLinkedList<Position<E>>();
        try{
            ;
            if (!isEmpty()){
                Position<E> pos=first();
                while(pos!=null){
                    lis.addLast(pos);
                    pos=next(pos);
                }

            }
        }
        catch(BoundaryViolationException r){}
        catch(EmptyListException f){}
        catch(InvalidPositionException g){}

        return lis;
    }

     public Iterator<E> iterator(){
        return new ElementIterator<E>(this);
    }
}


class ElementIterator<E> implements Iterator<E>{

    //Atributos
    protected PositionList<E> list; //Lista a iterar
    protected Position<E> cursor; //Posicion del elemento corriente


    //Constructor

    /**
     * Crea un nuevo iterador y establece su lista a iterar.
     * @param l, lista a iterar.
     * */

    public ElementIterator(PositionList<E> l){

        list=l;
        if (list.isEmpty())
            cursor=null;
        else
            try {
                cursor=l.first();
            } catch (EmptyListException e) {
                System.out.println(e.getMessage());
            }
    }

    /**
     * Determina si hay elementos a iterar.
     * @return true si hay al menos un elemento a iterar, false en caso contrario.
     * */
    public boolean hasNext(){return cursor!=null;}

    /**
     * Retorna el siguiente elemento de la iteracion
     * @return E elemento de la lista.
     * @throws NoSuchElementException si no hay elementos a iterar.
     * */
    public E next()throws NoSuchElementException{
        E toReturn=null;
        try{
            if (cursor == null)
                throw new NoSuchElementException("No tiene siguiente");
            toReturn= cursor.element();

            cursor=(cursor==list.last())? null : list.next(cursor);


        }//Fin Try
        catch(EmptyListException f){System.out.println(f.getMessage());}
        catch(BoundaryViolationException t){System.out.println(t.getMessage());}
        catch(InvalidPositionException r){System.out.println(r.getMessage());}
        return toReturn;
    }

    public void remove(){
        //No lo definimos.
    }

}//Fin clase

