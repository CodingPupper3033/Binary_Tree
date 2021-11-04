package Trees;

import org.jetbrains.annotations.NotNull;

public class BinarySearchTree<E extends Comparable> extends BinaryTree<E> implements Comparable<E> {

    public BinarySearchTree(BinaryTreeNode<E> root) {
        setRoot(root);
    }

    public BinarySearchTree() {
        this(new BinaryTreeNode<>(null, null,null,null));
    }
    public BinarySearchTree(E value, BinaryTreeNode<E> parent) {
        this(new BinaryTreeNode<E>(value,parent, null, null));
    }
    public BinarySearchTree(E value) {
        this(new BinaryTreeNode<E>(value,null, null, null));
    }


    // Parent
    public BinarySearchTree<E> getParent() {
        return new BinarySearchTree<E>((BinaryTreeNode<E>) getParentNode());
    }

    // Left
    public BinarySearchTree<E> getLeft() {
        return new BinarySearchTree<E>((BinaryTreeNode<E>) getLeftNode());
    }

    // Right
    public BinarySearchTree<E> getRight() {
        return new BinarySearchTree<E>((BinaryTreeNode<E>) getRightNode());
    }

    // Get Tree
    public BinarySearchTree<E> getTreeFromValue(E item) {
        if (getValue() == item) {
            return this;
        } else if (compareTo(item) > 0) {
            return getLeft().getTreeFromValue(item);
        } else {
            return getRight().getTreeFromValue(item);
        }
    }

    // Add
    public BinarySearchTree<E> add(E object) {
        BinarySearchTree<E> y = null;
        BinarySearchTree<E> x = this;

        while (x != null && x.hasRoot()) {
            y = x;
            if (x.compareTo(object) > 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        if (y == null) {
            setRoot(object);
            return this;
        } else if (y.compareTo(object) > 0) {
            return (BinarySearchTree) y.setLeft(object);
        } else {
            return (BinarySearchTree) y.setRight(object);
        }
    }

    public void addAll(E[] items) {
        for (E item: items) {
            add(item);
        }
    }

    // Minimum
    public BinarySearchTree getMinimum() {
        if (hasLeft()) {
            return getLeft().getMinimum();
        } else {
            return this;
        }
    }

    // Transplant & Delete
    public void transplant(BinaryTree<E> item, BinaryTree<E> replacer) {
        if (!item.hasParent()) {
            setRoot((BinaryTreeNode<E>) replacer.getRootNode());
        } else if (item.isLeftChild()) {
            item.getParent().setLeft(replacer.getRootNode());
        } else {
            TreeNode<E> foo = replacer.getRootNode();
            item.getParent().setRight(replacer.getRootNode());
        }
    }

    public void delete(BinarySearchTree<E> item) {
        if (!item.hasLeft()) {
            transplant(item, item.getRight());
        } else if (!item.hasRight()) {
            transplant(item, item.getLeft());
        } else {
            BinarySearchTree<E> replacer = item.getRight().getMinimum();

            if (!item.isChild(replacer)) {
                transplant(replacer, replacer.getRight());
                replacer.setRight(item.getRight().getValue());
            }
            transplant(item, replacer);
            replacer.setLeft(item.getLeft().getValue());

        }
    }

    public void delete(E item) {
        delete(getTreeFromValue(item));
    }

    // Compare
    @Override
    public int compareTo(@NotNull E o) {
        return getValue().compareTo(o);
    }

    public static void main(String[] args) {

    }
}

/*
public class Trees.BinarySearchTree<E extends Comparable> extends BinaryTreeOldNew<E> implements Comparable<E> {

    public Trees.BinarySearchTree() {
        this((E)null);
    }

    public Trees.BinarySearchTree(E root) {
        setRoot(root);
        setParent((BinaryTreeNodeOld<E>) null);
    }

    public Trees.BinarySearchTree(BinaryTreeNodeOld<E> root) {
        setRoot(root);
    }

    @Override
    public BinaryTreeOldNew<E> get(BinaryTreeNodeOld<E> item) {
        return new Trees.BinarySearchTree<E>(item);
    }

    // Left
    @Override
    public Trees.BinarySearchTree<E> getLeft() {
        return (Trees.BinarySearchTree<E>) super.getLeft();
    }

    // Right
    @Override
    public Trees.BinarySearchTree<E> getRight() {
        return (Trees.BinarySearchTree<E>) super.getRight();
    }


    // Compare
    @Override
    public int compareTo(E o) {
        return getValue().compareTo(o);
    }

    // Get
    public Trees.BinarySearchTree<E> getTreeEqualTo(E item) {
        if (getValue().equals(item)) {
            return this;
        } else if (compareTo(item) > 0) {
            return getLeft().getTreeEqualTo(item);
        } else {
            return getRight().getTreeEqualTo(item);
        }
    }

    public Trees.BinarySearchTree getTreeFromValue(E item) {
        if (getValue() == item) {
            return this;
        } else if (compareTo(item) > 0) {
            return getLeft().getTreeEqualTo(item);
        } else {
            return getRight().getTreeEqualTo(item);
        }
    }

    public Trees.BinarySearchTree getMinimum() {
        if (hasLeft()) {
            return getLeft().getMinimum();
        } else {
            return this;
        }
    }

    // Add
    public void add(E object) {
        Trees.BinarySearchTree<E> y = null;
        Trees.BinarySearchTree<E> x = this;

        while (x != null && x.hasRoot()) {
            y = x;
            if (x.compareTo(object) > 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        if (y == null) {
            setRoot(object);
        } else if (y.compareTo(object) > 0) {
            y.setLeft(object);
        } else {
            y.setRight(object);
        }
    }

    // Transplant & Delete
    public void transplant(BinaryTreeOldNew<E> item, BinaryTreeOldNew<E> replacer) {
        if (!item.hasParent()) {
            setRoot(replacer);
        } else if (item.isLeftChild()) {
            item.getParent().setLeft(replacer);
        } else {
            item.getParent().setRight(replacer);
        }
    }

    public void delete(Trees.BinarySearchTree<E> item) {
        if (!item.hasLeft()) {
            transplant(item, item.getRight());
        } else if (!item.hasRight()) {
            transplant(item, item.getLeft());
        } else {
            Trees.BinarySearchTree<E> replacer = item.getRight().getMinimum();

            if (!item.isChild(replacer)) {
                transplant(replacer, replacer.getRight());
                replacer.setRight(item.getRight());
            }
            transplant(item, replacer);
            replacer.setLeft(item.getLeft());

        }
    }

    public void delete(E item) {
        delete(getTreeFromValue(item));
    }

    public static void main(String[] args) {
        Trees.BinarySearchTree<Integer> tree = new Trees.BinarySearchTree<Integer>(5);

        tree.add(10);
        tree.add(2);
        System.out.println(tree);
    }
}

 */
