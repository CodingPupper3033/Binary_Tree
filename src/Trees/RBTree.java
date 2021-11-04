package Trees;

import java.awt.*;

public class RBTree<E extends Comparable> extends BinarySearchTree<E>{
    public boolean stringColor = true;

    public RBTree(RBTreeNode<E> root) {
        setRoot(root);
    }

    public RBTree() {
        this(new RBTreeNode<>(null, null,null,null));
    }
    public RBTree(E value, RBTreeNode<E> parent) {
        this(new RBTreeNode<E>(value,parent, null, null));
    }
    public RBTree(E value) {
        this(new RBTreeNode<E>(value,null, null, null));
    }


    // Value
    public String getPrintString() {
        if (stringColor)
            return hasRoot() ? String.valueOf("\033["  + (isRed() ? "91": "30") + "m" + getValue() + "\033[0m") : String.valueOf((E) null);
        else
            return hasRoot() ? String.valueOf("{" + getValue() + " : " + (isRed() ? "R": "B") + "}") : String.valueOf((E) null);
    }

    public int getPrintStringLength() {
        if (stringColor)
            return String.valueOf(getValue()).length();
        else
            return String.valueOf(getPrintString()).length();
    }


    // Root
    public void setRoot(RBTreeNode<E> root) {
        this.root = root;
    }
    public void setRoot(E value) {
        if (hasRoot())
            root.setValue(value);
        else
            setRoot(new RBTreeNode<>(value,null,null,null));
    }

    // Parent
    public void setParent(RBTreeNode<E> node) {
        getRootNode().setParent(node);
    }
    public RBTree<E> getParent() {
        RBTree<E> tree = new RBTree<>((RBTreeNode<E>) getParentNode());
        tree.stringColor = stringColor;
        return tree;
    }
    public RBTree<E> getParentNil() {
        return hasParent() ? getParent() : new RBTreeNil<>();
    }
    public void giveParent(RBTreeNode<E> parentSteal) {
        if (hasParent()) {
            if (isLeftChild()) {
                getParent().setLeft(parentSteal);
            } else {
                getParent().setRight(parentSteal);
            }
        }
        setParent((RBTreeNode<E>) null);
    }

    // Left
    public RBTree setLeft(E value) {
        return (RBTree) setLeft(new RBTreeNode<>(value));
    }
    public RBTree<E> getLeft() {
        RBTree<E> tree = new RBTree<>((RBTreeNode<E>) getLeftNode());
        tree.stringColor = stringColor;
        return tree;
    }
    public RBTree<E> getLeftNil() {
        return hasLeft() ? getLeft() : new RBTreeNil<>();
    }

    public void leftRotate() {
        RBTree<E> rightChild = getRight();

        // Transfer parent
        giveParent((RBTreeNode<E>) rightChild.getRootNode());

        // Transfer β
        if (rightChild.hasLeft())
            setRight(rightChild.getLeft().getRootNode());
        else
            setRight((TreeNode<E>) null);

        // Move over this node
        rightChild.setLeft(getRootNode());

        // Update this root
        setRoot((RBTreeNode<E>) getParentNode());
    }

    /*
    public void leftRotate() {
        //System.out.println(this);
        Trees.RBTreeNode<E> rightChild = (Trees.RBTreeNode<E>) getRightNode();
        Trees.RBTreeNode<E> originalRoot = (Trees.RBTreeNode<E>) getRootNode();

        setParent(rightChild);
        //System.out.println(getParent());

        originalRoot.setRight(rightChild.getLeft());
        rightChild.setLeft(originalRoot);
        originalRoot.setParent(rightChild);

        setRoot(rightChild);
    }

     */

    // Right
    public RBTree<E> setRight(E value) {
        return (RBTree<E>) setRight(new RBTreeNode<>(value));
    }
    public RBTree<E> getRight() {
        RBTree<E> tree = new RBTree<>((RBTreeNode<E>) getRightNode());
        tree.stringColor = stringColor;
        return tree;
    }
    public RBTree<E> getRightNil() {
        return hasRight() ? getRight() : new RBTreeNil<>();
    }

    /*
    public void rightRotate() {
        //System.out.println(this);
        Trees.RBTreeNode<E> leftChild = (Trees.RBTreeNode<E>) getLeftNode();
        Trees.RBTreeNode<E> originalRoot = (Trees.RBTreeNode<E>) getRootNode();

        setParent(leftChild);
        //System.out.println(getParent());

        originalRoot.setLeft(leftChild.getRight());
        leftChild.setRight(originalRoot);
        originalRoot.setParent(leftChild);

        setRoot(leftChild);
    }

     */

    public void rightRotate() {
        RBTree<E> leftChild = getLeft();

        // Transfer parent
        giveParent((RBTreeNode<E>) leftChild.getRootNode());

        // Transfer β
        if (leftChild.hasRight())
            setLeft(leftChild.getRight().getRootNode());
        else
            setLeft((TreeNode<E>) null);

        // Move over this node
        leftChild.setRight(getRootNode());

        // Update this root
        setRoot((RBTreeNode<E>) getParentNode());
    }

    // Minimum
    public RBTree getMinimum() {
        if (hasLeft()) {
            return getLeft().getMinimum();
        } else {
            return this;
        }
    }

    // Color
    public boolean isRed() {
        return ((RBTreeNode<Integer>)getRootNode()).isRed();
    }

    @Deprecated
    public Color getColor() {
        return ((RBTreeNode<Integer>)getRootNode()).getColor();
    }

    public void setRed(boolean red) {
        ((RBTreeNode<Integer>)getRootNode()).setRed(red);
    }

    // Transplant & Delete
    public void transplant(BinaryTree<E> item, BinaryTree<E> replacer) {
        //System.out.println(item.hasParent());
        if (!item.hasParent()) {
            setRoot((RBTreeNode<E>) replacer.getRootNode());
        } else if (item.isLeftChild()) {
            item.getParent().setLeft(replacer.getRootNode());
        } else {
            item.getParent().setRight(replacer.getRootNode());
        }
    }

    // Insert
    @Override
    public RBTree<E> add(E object) {
        return add(object, false);
    }

    public RBTree<E> add(E object, boolean print) {
        RBTree<E> tree = (RBTree<E>) super.add(object);
        insertFixup(tree);
        if (print) System.out.println(this);
        return tree;
    }



    public void insertFixup(RBTree<E> tree) {
        while (tree.getParentNil().isRed()) { // Line 1
            if (tree.getParentNil().isLeftChild()) {
                RBTree<E> uncle = tree.getParentNil().getParentNil().getRightNil();

                if (uncle.isRed()) {
                    tree.getParentNil().setRed(false);
                    uncle.setRed(false);
                    tree.getParentNil().getParentNil().setRed(true);
                    tree = tree.getParentNil().getParentNil();
                } else {
                    if (tree.isRightChild()) {
                        tree = tree.getParentNil();
                        tree.leftRotate();
                        tree = tree.getLeft();
                    }

                    tree.getParentNil().setRed(false);
                    tree.getParentNil().getParentNil().setRed(true);
                    if (getRootNode() == tree.getParentNil().getParentNil().getRootNode()) {
                        rightRotate();
                    } else {
                        tree.getParentNil().getParentNil().rightRotate();
                    }
                }
            } else {
                RBTree<E> uncle = tree.getParentNil().getParentNil().getLeftNil();

                if (uncle.isRed()) {
                    tree.getParentNil().setRed(false);
                    uncle.setRed(false);
                    tree.getParentNil().getParentNil().setRed(true);
                    tree = tree.getParentNil().getParentNil();
                } else {
                    if (tree.isLeftChild()) {
                        tree = tree.getParentNil();
                        tree.rightRotate();
                        tree = tree.getRight();
                    }

                    tree.getParentNil().setRed(false);
                    tree.getParentNil().getParentNil().setRed(true);
                    if (getRootNode() == tree.getParentNil().getParentNil().getRootNode()) {
                        leftRotate();
                    } else {
                        tree.getParentNil().getParentNil().leftRotate();
                    }
                }
            }
        }
        setRed(false);
        setParent((RBTreeNode<E>) null);
    }

    public static RBTree<Integer> createRandomTree(int minN, int maxN, int amount) {
        return createRandomTree(minN, maxN, amount, false);
    }

    public static RBTree<Integer> createRandomTree(int minN, int maxN, int amount, boolean debug) {
        RBTree<Integer> tree = new RBTree<Integer>();
        int diff = maxN-minN;

        for (int i = 0; i < amount; i++) {
            int numb = (int) (Math.random()*diff)+minN;
            if (debug) System.out.println("Adding: " + numb);
            tree.add(numb);
            if (debug) System.out.println("Added");
        }
        return tree;
    }

    @Deprecated
    public void printNoises() {
        System.out.println("Printing \033[3m*printer noises*\033[0m");
        System.out.println(this);
        System.out.println("\033[3m*printer ceases*\033[0m \n");
    }

    public static void main(String[] args) {
        RBTree<Integer> tree = createRandomTree(100, 100, 100, true);

        tree.printNoises();
    }
}

