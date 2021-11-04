package Trees;

public class BinaryTreeNode<E> implements TreeNode<E>{
    private BinaryTreeNode<E> parent;
    private E value;
    private BinaryTreeNode<E> left;
    private BinaryTreeNode<E> right;

    public BinaryTreeNode(E value, BinaryTreeNode<E> parent,BinaryTreeNode<E> left,BinaryTreeNode<E> right) {
        setValue(value);
        setParent(parent);
        setLeft(left);
        setRight(right);
    }

    public BinaryTreeNode(E value) {
        this(value,null,null,null);
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public void setParent(TreeNode parent) {
        this.parent = (BinaryTreeNode<E>) parent;
    }

    @Override
    public TreeNode getLeft() {
        return left;
    }

    @Override
    public void setLeft(TreeNode left) {
        this.left = (BinaryTreeNode<E>) left;
    }

    @Override
    public TreeNode getRight() {
        return right;
    }

    @Override
    public void setRight(TreeNode right) {
        this.right = (BinaryTreeNode<E>) right;
    }

    @Override
    public E getValue() {
        return value;
    }

    @Override
    public void setValue(E value) {
        this.value = value;
    }
}
