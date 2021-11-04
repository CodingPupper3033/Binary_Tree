package Trees;

import java.awt.*;

public class RBTreeNode<E> implements TreeNode<E> {
    private RBTreeNode<E> parent;
    private E value;
    private RBTreeNode<E> left;
    private RBTreeNode<E> right;
    private boolean isRed;

    public RBTreeNode(E value, RBTreeNode<E> parent,RBTreeNode<E> left,RBTreeNode<E> right) {
        setValue(value);
        setParent(parent);
        setLeft(left);
        setRight(right);
        setRed(true);
    }

    public RBTreeNode(E value) {
        this(value,null,null,null);
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public void setParent(TreeNode parent) {
        this.parent = (RBTreeNode<E>) parent;
    }

    @Override
    public TreeNode getLeft() {
        return left;
    }

    @Override
    public void setLeft(TreeNode left) {
        this.left = (RBTreeNode<E>) left;
    }

    @Override
    public TreeNode getRight() {
        return right;
    }

    @Override
    public void setRight(TreeNode right) {
        this.right = (RBTreeNode<E>) right;
    }

    @Override
    public E getValue() {
        return value;
    }

    @Override
    public void setValue(E value) {
        this.value = value;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
    }

    @Deprecated
    public Color getColor() {
        return isRed ? Color.RED : Color.BLACK;
    }
}
