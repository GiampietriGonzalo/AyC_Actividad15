import java.util.Iterator;

interface Position<E> {

    /**
     * Retorna el elemento de la posicion.
     * @return E elemento de la poscion.
     * */
    public E element();
}

interface Edge<E> extends Position<E>{

}

interface Vertex<V> extends Position<V> {


}

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

interface GraphD<V,E> {

    /**
     * Devuelve una colecci�n iterable de v�rtices.
     * @return Una colecci�n iterable de v�rtices.
     */
    Iterable<Vertex<V>> vertices();

    /**
     * Devuelve una colecci�n iterable de arcos.
     * @return Una colecci�n iterable de arcos.
     */
    Iterable<Edge<E>> edges();

    /**
     * Devuelve una colecci�n iterable de arcos incidentes a un v�rtice v.
     * @param v Un v�rtice.
     * @return Una colecci�n iterable de arcos incidentes a un v�rtice v.
     * @throws InvalidVertexException si el v�rtice es inv�lido.
     */
    Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException;

    /**
     * Devuelve una colecci�n iterable de arcos adyacentes a un v�rtice v.
     * @param v Un v�rtice
     * @return Una colecci�n iterable de arcos adyacentes a un v�rtice v.
     * @throws InvalidVertexException si el v�rtice es inv�lido.
     */
    public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException;

    /**
     * Devuelve el v�rtice opuesto a un Arco E y un v�rtice V.
     * @param v Un v�rtice
     * @param e Un arco
     * @return El v�rtice opuesto a un Arco E y un v�rtice V.
     * @throws InvalidVertexException si el v�rtice es inv�lido.
     * @throws InvalidEdgeException si el arco es inv�lido.
     */
    Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException;

    /**
     * Devuelve un Arreglo de 2 elementos con lo v�rtices extremos de un Arco e.
     * @param  e Un arco
     * @return Un Arreglo de 2 elementos con los extremos de un Arco e.
     * @throws InvalidEdgeException si el arco es inv�lido.
     */
    Vertex<V> [] endvertices(Edge<E> e) throws InvalidEdgeException;

    /**
     * Devuelve verdadero si el v�rtice w es adyacente al v�rtice v.
     * @param v Un v�rtice
     * @param w Un v�rtice
     * @return Verdadero si el v�rtice w es adyacente al v�rtice v, falso en caso contrario.
     * @throws InvalidVertexException si uno de los v�rtices es inv�lido.
     */
    boolean areAdjacent(Vertex<V> v,Vertex<V> w) throws InvalidVertexException;

    /**
     * Reemplaza el r�tulo de v por un r�tulo x.
     * @param v Un v�rtice
     * @param x R�tulo
     * @return El r�tulo anterior del v�rtice v al reemplazarlo por un r�tulo x.
     * @throws InvalidVertexException si el v�rtice es inv�lido.
     */
    V replace(Vertex<V> v, V x) throws InvalidVertexException;

    /**
     * Inserta un nuevo v�rtice con r�tulo x.
     * @param x r�tulo del nuevo v�rtice
     * @return Un nuevo v�rtice insertado.
     */
    Vertex<V> insertVertex(V x);

    /**
     * Inserta un nuevo arco con r�tulo e, desde un v�rtice v a un v�rtice w.
     * @param v Un v�rtice
     * @param w Un v�rtice
     * @param e r�tulo del nuevo arco.
     * @return Un nuevo arco insertado desde un v�rtice V a un v�rtice W.
     * @throws InvalidVertexException si uno de los v�rtices es inv�lido.
     */
    Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException;

    /**
     * Remueve un v�rtice V y retorna su r�tulo.
     * @param v Un v�rtice
     * @return r�tulo de V.
     * @throws InvalidVertexException si el v�rtice es inv�lido.
     */
    V removeVertex(Vertex<V> v) throws InvalidVertexException;

    /**
     * Remueve un arco e y retorna su r�tulo.
     * @param e Un arco
     * @return r�tulo de E.
     * @throws InvalidEdgeException si el arco es inv�lido.
     */
    E removeEdge(Edge<E> e) throws InvalidEdgeException;

    /**
     * Retorna la cantidad total de vertices
     * */
    int totalVertex();
    int totalEdges();
}

class DGrafoListaAdyascentes<V,E> implements GraphD<V,E> {

    //Atributos
    private PositionList<Vertice<V,E>> vertices;
    private PositionList<Arco<V,E>> adyascentes;

    public DGrafoListaAdyascentes(){
        vertices = new DoubleLinkedList<Vertice<V,E>>();
        adyascentes = new DoubleLinkedList<Arco<V,E>>();
    }

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
            throw new InvalidVertexException("El vertice no tienen un opuesto con respecto a este arco");

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


    public Iterable<Edge<E>> succesorEdges(Vertex<V> v)throws InvalidVertexException {
        Vertice<V,E> v1=checkVertex(v);
        PositionList<Edge<E>> tR= new DoubleLinkedList<Edge<E>>();
        for(Arco<V,E> ar: v1.getAdyascentes()){
            if(ar.getPredecesor()==v1)
                tR.addLast(ar);
        }

        return tR;
    }

    public int totalVertex() {
        return vertices.size();
    }

    public int totalEdges() {
        return adyascentes.size();
    }

}
