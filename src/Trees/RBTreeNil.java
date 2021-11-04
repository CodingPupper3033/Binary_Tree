package Trees;

public class RBTreeNil<E extends Comparable> extends RBTree<E>{
    public RBTreeNil() {
        super();
        setRed(false);
    }
}