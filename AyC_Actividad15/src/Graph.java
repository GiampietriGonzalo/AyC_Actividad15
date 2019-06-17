import java.util.Iterator;

interface Position<E> {
    /**
     * Retorna el elemento de la posicion.
     * @return E elemento de la poscion.
     * */
    E element();
}

interface Edge<E> extends Position<E> {}

interface Vertex<V> extends Position<V> {}

class Vertice<V,E> implements Vertex<V>{

    //Atributos
    private V rotulo;
    private PositionList<Arco<V,E>> adyascentes;
    private Position<Vertice<V,E>> posEnVertices;

    //Constructor
    public Vertice(V rotulo){
        this.rotulo=rotulo;
        adyascentes= new DoubleLinkedList<Arco<V,E>>();
        posEnVertices=null;
    }

    //Metodos
    public void setRotulo(V r){rotulo=r;}
    public void setPosEnVertices(Position<Vertice<V,E>> pos){posEnVertices=pos;}

    //Consultas
    public V element(){return rotulo;}
    public PositionList<Arco<V,E>> getAdyascentes(){return adyascentes;}
    public Position<Vertice<V,E>> getPosEnVertices(){return posEnVertices;}

}


class Arco<V,E> implements Edge<E>{

    //Atributos
    private E rotulo;
    private Position<Arco<V,E>> posEnAdyascentes;
    private Vertice<V,E> sucesor,predecesor;

    //Constructor
    public Arco(E rotulo, Vertice<V,E> p,Vertice<V,E> s){
        this.rotulo=rotulo;
        sucesor=s;
        predecesor=p;
        posEnAdyascentes=null;
    }

    //Metodos
    public void setRotulo(E r){rotulo=r;}
    public void setSucesor(Vertice<V,E> s){sucesor=s;}
    public void setPredecesor(Vertice<V,E> p){predecesor=p;}
    public void setPosicionEnAdyascentes(Position<Arco<V,E>> pos){posEnAdyascentes=pos;}


    //Consultas
    public E element(){return rotulo;}
    public E getRotulo(){return rotulo;}
    public Vertice<V,E> getSucesor(){return sucesor;}
    public Vertice<V,E> getPredecesor(){return predecesor;}
    public Position<Arco<V,E>> getPosEnAdyascentes(){return posEnAdyascentes;}


}


class InvalidEdgeException extends Exception{
    public InvalidEdgeException(String s){super(s);}
}

class InvalidVertexException extends Exception{
    public InvalidVertexException(String s){super(s);}
}

interface Graph<V,E> {

    /**
     * Devuelve una coleccion iterable de vertices.
     * @return Una coleccion iterable de vertices.
     */
    Iterable<Vertex<V>> vertices();

    /**
     * Devuelve una coleccion iterable de arcos.
     * @return Una coleccion iterable de arcos.
     */
    Iterable<Edge<E>> edges();

    /**
     * Devuelve una coleccion iterable de arcos incidentes a un vertice v.
     * @param v Un vertice.
     * @return Una coleccion iterable de arcos incidentes a un vertice v.
     * @throws InvalidVertexException si el vertice es invalido.
     */
    Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException;


    /**
     * Devuelve el vertice opuesto a un Arco E y un vertice V.
     * @param v Un vertice
     * @param e Un arco
     * @return El vertice opuesto a un Arco E y un vertice V.
     * @throws InvalidVertexException si el vertice es invalido.
     * @throws InvalidEdgeException si el arco es invalido.
     */
    Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException;

    /**
     * Devuelve un Arreglo de 2 elementos con lo vertices extremos de un Arco e.
     * @param  e Un arco
     * @return Un Arreglo de 2 elementos con los extremos de un Arco e.
     * @throws InvalidEdgeException si el arco es invalido.
     */
    Vertex<V> [] endvertices(Edge<E> e) throws InvalidEdgeException;

    /**
     * Devuelve verdadero si el vertice w es adyacente al vertice v.
     * @param v Un vertice
     * @param w Un vertice
     * @return Verdadero si el vertice w es adyacente al vertice v, falso en caso contrario.
     * @throws InvalidVertexException si uno de los vertices es invalido.
     */
    public boolean areAdjacent(Vertex<V> v,Vertex<V> w) throws InvalidVertexException;

    /**
     * Reemplaza el rotulo de v por un rotulo x.
     * @param v Un vertice
     * @param x Rotulo
     * @return El rotulo anterior del vertice v al reemplazarlo por un rotulo x.
     * @throws InvalidVertexException si el vertice es invalido.
     */
    V replace(Vertex<V> v, V x) throws InvalidVertexException;

    /**
     * Inserta un nuevo vertice con rotulo x.
     * @param x rotulo del nuevo vertice
     * @return Un nuevo vertice insertado.
     */
    Vertex<V> insertVertex(V x);

    /**
     * Inserta un nuevo arco con rotulo e, con vertices extremos v y w.
     * @param v Un vertice
     * @param w Un vertice
     * @param e rotulo del nuevo arco.
     * @return Un nuevo arco.
     * @throws InvalidVertexException si uno de los vertices es invalido.
     */
    Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException;

    /**
     * Remueve un vertice V y retorna su rotulo.
     * @param v Un vertice
     * @return rotulo de V.
     * @throws InvalidVertexException si el vertice es invalido.
     */
    V removeVertex(Vertex<V> v) throws InvalidVertexException;

    /**
     * Remueve un arco e y retorna su rotulo.
     * @param e Un arco
     * @return rotulo de E.
     * @throws InvalidEdgeException si el arco es invalido.
     */
    E removeEdge(Edge<E> e) throws InvalidEdgeException;

    /**
     * Retorna la cantidad total de vertices del grafo.
     * */
    int totalVertex();

    /**
     * Retorna la cantidad total de vertices del grafo.
     * */
    int totalEdges();
}


class GrafoNoDirigido<V,E> implements Graph<V,E> {

    //Atributos
    private PositionList<Vertice<V,E>> vertices;
    private PositionList<Arco<V,E>> adyascentes;

    //Constructor
    public GrafoNoDirigido(){
        vertices=new DoubleLinkedList<Vertice<V,E>>();
        adyascentes=new DoubleLinkedList<Arco<V,E>>();
    }

    //Metodos
    public Vertex<V> insertVertex(V x){
        Vertice<V,E> v= new Vertice<V,E>(x);
        vertices.addLast(v);
        try{
            v.setPosEnVertices(vertices.last());
        }
        catch(EmptyListException e){System.out.println(e.getMessage());}
        return v;
    }

    public Edge<E> insertEdge(Vertex<V> v1,Vertex<V> v2, E peso)throws InvalidVertexException{
        Vertice<V,E> V1= checkVertex(v1);
        Vertice<V,E> V2= checkVertex(v2);

        //Creo el nuevo arco
        Arco<V,E> arco= new Arco<V,E>(peso,V2,V1);
        adyascentes.addLast(arco);
        try{
            //Seteo el atributo posEnAdyascentes del arco
            arco.setPosicionEnAdyascentes(adyascentes.last());

            //Agrego el arco a la lista de adyascentes del vertice sucesor y predecesor
            V1.getAdyascentes().addLast(arco);
            V2.getAdyascentes().addLast(arco);
        }
        catch(EmptyListException e){System.out.println(e.getMessage());}
        return arco;
    }


    public E removeEdge(Edge<E> e) throws InvalidEdgeException{
        Arco<V,E> ar= checkEdge(e);
        try{
            //Remuevo el arco de la lista de adyascentes
            adyascentes.remove(ar.getPosEnAdyascentes());
            Vertice<V,E> v1= ar.getSucesor();
            Vertice<V,E> v2=ar.getPredecesor();
            v1.getAdyascentes().remove(ar.getPosEnAdyascentes());
            v2.getAdyascentes().remove(ar.getPosEnAdyascentes());

        }
        catch(InvalidPositionException e1){System.out.println(e1.getMessage());}

        return ar.element();
    }

    public V removeVertex(Vertex<V> e) throws InvalidVertexException{
        Vertice<V,E> v= checkVertex(e);
        try{
            for(Arco<V,E> ar: v.getAdyascentes()){
                removeEdge(ar);
            }
            vertices.remove(v.getPosEnVertices());
        }
        catch(InvalidPositionException e1){System.out.println(e1.getMessage());}
        catch(InvalidEdgeException e1){System.out.println(e1.getMessage());}

        return v.element();
    }


    private Vertice<V,E> checkVertex(Vertex<V> v)throws InvalidVertexException{
        if(v==null)
            throw new InvalidVertexException("Vertice invalido");
        return (Vertice<V,E>)v;
    }

    private Arco<V,E> checkEdge(Edge<E> v)throws InvalidEdgeException{
        if(v==null)
            throw new InvalidEdgeException("Arco invalido");
        return (Arco<V,E>)v;
    }

    public Iterable<Vertex<V>> vertices(){
        DoubleLinkedList<Vertex<V>> tR= new DoubleLinkedList<Vertex<V>>();
        for(Vertex<V> v: vertices){
            tR.addLast(v);
        }
        return tR;
    }

    public Iterable<Edge<E>> edges(){
        DoubleLinkedList<Edge<E>> tR= new DoubleLinkedList<Edge<E>>();
        for(Edge<E> e: adyascentes){
            tR.addLast(e);
        }
        return tR;
    }

    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException{
        Vertice<V,E> v1= checkVertex(v);
        Arco<V,E> a1= checkEdge(e);
        Vertex<V> tR=null;

        if(v1==a1.getSucesor())
            tR=a1.getPredecesor();
        else
        if(v1==a1.getPredecesor())
            tR=a1.getSucesor();
        else
            throw new InvalidVertexException("El vertice no tienenun opuesto con respecto a este arco");

        return tR;
    }

    public Vertex<V> [] endvertices(Edge<E> e) throws InvalidEdgeException{
        Arco<V,E> a= checkEdge(e);
        Vertex<V>[] tR= (Vertice<V,E>[])new Vertice[2];
        tR[0]=a.getPredecesor();
        tR[1]=a.getSucesor();
        return tR;
    }

    public boolean areAdjacent(Vertex<V> v,Vertex<V> w) throws InvalidVertexException{
        Vertice<V,E> v1= checkVertex(v);
        Vertice<V,E> v2= checkVertex(w);
        boolean son=false;
        Iterator<Arco<V,E>> it= v1.getAdyascentes().iterator();
        Arco<V,E> actual;

        while(!son && it.hasNext()){
            actual=it.next();
            if(actual.getSucesor()==v2 || actual.getPredecesor()==v2)
                son=true;
        }
        return son;
    }

    public V replace(Vertex<V> v, V x) throws InvalidVertexException{
        Vertice<V,E> v1= checkVertex(v);
        V tR= v1.element();
        v1.setRotulo(x);
        return tR;
    }

    public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException{
        Vertice<V,E> v1= checkVertex(v);
        DoubleLinkedList<Edge<E>> tR= new DoubleLinkedList<Edge<E>>();
        for(Arco<V,E> ar: v1.getAdyascentes())
            if(ar.getSucesor()==v1)
                tR.addLast(ar);

        return tR;
    }

    public int totalVertex() {
        return vertices.size();
    }

    public int totalEdges() {
        return adyascentes.size();
    }

    public String toString(){
        String toReturn = "";

        Vertice<E,E> nodoA;
        Vertice<E,E> nodoB;
        Arco<E,E> arco;

        for(Edge<E> edge: edges()) {
            arco = (Arco<E, E>)edge;
            nodoA = arco.getPredecesor();
            nodoB = arco.getSucesor();
            toReturn +=  "A = " + nodoA.element()  + " B = " + nodoB.element() + " PesoArco = " + arco.element() +"\n";
        }

        return toReturn;
    }
}