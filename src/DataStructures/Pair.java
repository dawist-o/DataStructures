package DataStructures;

public class Pair<V,T>{
    private V node;
    private T lvl;

    public Pair(V fst, T scnd){
        this.node=fst;
        this.lvl=scnd;
    }

    public V getNode(){
        return node;
    }
    public T getLvl(){
        return lvl;
    }
}
