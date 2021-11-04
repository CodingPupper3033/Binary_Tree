package Trees;

public interface TreeNode<E> {
    // Parent
    TreeNode<E> getParent();
    void setParent(TreeNode<E> parent);

    // Left
    TreeNode<E> getLeft();
    void setLeft(TreeNode<E> left);

    // Right
    TreeNode<E> getRight();
    void setRight(TreeNode<E> right);

    // Value
    E getValue();
    void setValue(E value);

}
