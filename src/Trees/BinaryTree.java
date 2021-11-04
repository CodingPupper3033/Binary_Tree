package Trees;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BinaryTree<E> implements Iterable<BinaryTree<E>> {
    TreeNode<E> root;

    // String Stuff
    public enum StringMethods {
        NOTHING,
        ALL_NULL,
        NO_NULL,
        SOME_NULL
    }
    public static final char spaceChar = ' ';
    public static final char fillChar = ' ';
    public static final char connectionTopChar = '|';
    public static final char connectionRightToBottom = '┌';
    public static final char connectionLeftToBottom = '┐';
    public static final char connectionLeftToRight = '─';
    public static final char connectionLeftToRightToTop = '┴';
    public static final char connectionLeftToTop = '┘';
    public static final char connectionRightToTop = '└';

    public static final int spaceBetweenItems = 1;

    public BinaryTree(BinaryTreeNode<E> root) {
        setRoot(root);
    }

    public BinaryTree() {
        this(new BinaryTreeNode<>(null, null,null,null));
    }
    public BinaryTree(E value, BinaryTreeNode<E> parent) {
        this(new BinaryTreeNode<>(value, parent, null, null));
    }
    public BinaryTree(E value) {
        this(new BinaryTreeNode<>(value, null, null, null));
    }

    // Root
    public void setRoot(BinaryTreeNode<E> root) {
        this.root = root;
    }
    public void setRoot(E value) {
        if (hasRoot())
            root.setValue(value);
        else
            setRoot(new BinaryTreeNode<E>(value,null,null,null));
    }
    public TreeNode getRootNode() {
        return root;
    }
    public boolean hasRoot() {
        return root != null && hasValue();
    }

    // Parent
    public TreeNode<E> getParentNode() {
        return root.getParent();
    }
    public BinaryTree<E> getParent() {
        return new BinaryTree<E>((BinaryTreeNode<E>) getParentNode());
    }
    public boolean hasParent() {
        return getParentNode() != null;
    }

    public void setParent(BinaryTreeNode<E> node) {
        getRootNode().setParent(node);
    }

    // Value
    public void setValue(E value) {
        root.setValue(value);
    }
    public E getValue() {
        return root.getValue();
    }

    public boolean hasValue() {
        return root.getValue() != null;
    }

    // Left
    public BinaryTree<E> setLeft(TreeNode<E> left) {
        if (left != null) left.setParent(getRootNode());
        getRootNode().setLeft(left);
        return getLeft();
    }

    public BinaryTree<E> setLeft(E value) {
        return setLeft(new BinaryTreeNode<>(value));
    }

    public TreeNode<E> getLeftNode() {
        return root.getLeft();
    }
    public BinaryTree<E> getLeft() {
        return new BinaryTree<E>((BinaryTreeNode<E>) getLeftNode());
    }
    public boolean hasLeft() {
        return getLeftNode() != null;
    }

    public boolean isLeftChild() {
        return hasParent() && getParent().hasLeft() && getParent().getLeftNode() == getRootNode();
    }

    // Right
    public BinaryTree<E> setRight(TreeNode<E> right) {
        if (right != null) right.setParent(getRootNode());
        getRootNode().setRight(right);
        return getRight();
    }
    public BinaryTree<E> setRight(E value) {
        return setRight(new BinaryTreeNode<>(value));
    }
    public TreeNode<E> getRightNode() {
        return root.getRight();
    }
    public BinaryTree<E> getRight() {
        return new BinaryTree<E>((BinaryTreeNode<E>) getRightNode());
    }
    public boolean hasRight() {
        return getRightNode() != null;
    }

    public boolean isRightChild() {
        return hasParent() && getParent().hasRight() && getParent().getRightNode() == getRootNode();
    }

    // Child
    public boolean isChild(BinaryTree child) {
        return getLeftNode() == child.getRootNode() || getRightNode() == child.getRootNode();
    }

    // Height
    public int getHeight() {
        int leftLength = (hasLeft() ? getLeft().getHeight() : 0)+1;
        int rightLength = (hasRight() ? getRight().getHeight() : 0)+1;
        return Math.max(leftLength,rightLength);
    }

    // String
    private static String getFilledString(int size, String string, int stringLength) {
        int totalSpace = size - stringLength;
        // Right biased
        int leftSpace = (int) Math.ceil(totalSpace/2.0);
        int rightSpace = (int) Math.floor(totalSpace/2.0);

        return multiplyChar(leftSpace, fillChar) + string + multiplyChar(rightSpace, fillChar);
    }
    private static String multiplyChar(int amount, char character) {
        return new String(new char[amount]).replace("\0", String.valueOf(character));
    }

    public String getNullPrintString() {
        return String.valueOf((E) null);
    }
    public int getNullPrintStringLength() {
        return getNullPrintString().length();
    }
    public String getPrintString() {
        return hasRoot() ? String.valueOf(getValue()) : getNullPrintString();
    }
    public int getPrintStringLength() {
        return getPrintString().length();
    }
    public int getMaxPrintStringLengthOfTree() {
        int max = getNullPrintStringLength();
        for (BinaryTree<E> tree: this) {
            if (tree != null)
                max = Math.max(tree.getPrintStringLength(), max);
        }
        return max % 2 == 0 ? max+1 : max;
    }
    public int getMaxPrintLevelLength() {
        return (int) Math.pow(2, getHeight())*getMaxPrintStringLengthOfTree() + (int)(Math.pow(2, getHeight())-1)*spaceBetweenItems;
    }

    public String getPrintStringFilled(int maxSize) {
        return getFilledString(maxSize, getPrintString(), getPrintStringLength());
    }
    public String getNullPrintStringFilled(int maxSize) {
        return getFilledString(maxSize, getNullPrintString(), getNullPrintStringLength());
    }

    public String getPrintStringSpaced(BinaryTree<E> item, double lineSpaceItem, int filledStringSize) {
        StringBuilder out = new StringBuilder();

        int lineSpaceFront = (int)Math.floor(lineSpaceItem/2);
        int lineSpaceBack = (int)Math.ceil(lineSpaceItem/2);

        out.append(multiplyChar(lineSpaceFront, spaceChar));
        out.append(item != null ? item.getPrintStringFilled(filledStringSize) : getNullPrintStringFilled(filledStringSize)); // Add text
        out.append(multiplyChar(lineSpaceBack, spaceChar));

        return out.toString();
    }

    public String getNothingPrintSpaced(double lineSpaceItem, int filledStringSize) {
        StringBuilder out = new StringBuilder();

        int total = (int)Math.floor(lineSpaceItem/2) + (int)Math.ceil(lineSpaceItem/2) + filledStringSize;
        out.append(multiplyChar(total, spaceChar));
        return out.toString();
    }

    public String getConnectionLineAbove(boolean[] show, double lineSpaceItem, int filledStringSize) {
        double totalElementSize = lineSpaceItem + filledStringSize;
        StringBuilder out = new StringBuilder();
        if (show.length == 1) {
            return hasParent() ? multiplyChar((int) (totalElementSize/2), spaceChar) + connectionTopChar + "\n" : "";
        } else {
            for (int i = 0; i < show.length; i++) {
                if (i %2 == 0) { // Even
                    out.append(multiplyChar((int)Math.floor(totalElementSize/2),spaceChar));
                    out.append(show[i] ? connectionRightToBottom : spaceChar);
                    out.append(multiplyChar((int)Math.ceil(totalElementSize/2)-1,show[i] ? connectionLeftToRight : spaceChar));
                    if (show[i]) {
                        if (show[i+1]) {
                            out.append(connectionLeftToRightToTop);
                        } else {
                            out.append(connectionLeftToTop);
                        }
                    } else {
                        if (show[i+1]) {
                            out.append(connectionRightToTop);
                        } else {
                            out.append(spaceChar);
                        }
                    }

                } else {
                    out.append(multiplyChar((int)Math.floor(totalElementSize/2),show[i] ? connectionLeftToRight : spaceChar));
                    out.append(show[i] ? connectionLeftToBottom : spaceChar);
                    out.append(multiplyChar((int)Math.ceil(totalElementSize/2),spaceChar));
                }
            }
        }
        out.append("\n");
        return out.toString();
    }

    public String getLine(ArrayList<BinaryTree<E>> level, boolean[] show, double lineSpaceItem, int filledStringSize) {
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < level.size(); i++) {
            BinaryTree<E> item = level.get(i);
            out.append(show[i] ? getPrintStringSpaced(item, lineSpaceItem, filledStringSize) : getNothingPrintSpaced(lineSpaceItem, filledStringSize));
        }
        out.append("\n");
        return out.toString();
    }

    public String getConnectionLineAbove(int levelSize, double lineSpaceItem, int filledStringSize) {
        double totalElementSize = lineSpaceItem + filledStringSize;
        StringBuilder out = new StringBuilder();
        if (levelSize == 1) {
            return hasParent() ? multiplyChar((int) (totalElementSize/2), spaceChar) + connectionTopChar + "\n" : "";
        } else {
            for (int i = 0; i < levelSize; i++) {
                if (i %2 == 0) { // Even
                    out.append(multiplyChar((int)Math.floor(totalElementSize/2),spaceChar));
                    out.append(connectionRightToBottom);
                    out.append(multiplyChar((int)Math.ceil(totalElementSize/2)-1,connectionLeftToRight));
                    out.append(connectionLeftToRightToTop);
                } else {
                    out.append(multiplyChar((int)Math.floor(totalElementSize/2),connectionLeftToRight));
                    out.append(connectionLeftToBottom);
                    out.append(multiplyChar((int)Math.ceil(totalElementSize/2),spaceChar));
                }
            }
        }
        out.append("\n");
        return out.toString();
    }


    public String toString(StringMethods method) {
        StringBuilder out = new StringBuilder();
        ArrayList<ArrayList<BinaryTree<E>>> levels = getLevelItems();

        int filledStringSize = getMaxPrintStringLengthOfTree();
        int maxLevelStringSize = getMaxPrintLevelLength();

        ArrayList<BinaryTree<E>> prevLevel = null;
        for (int i = 0; i < levels.size(); i++) {
            ArrayList<BinaryTree<E>> level = levels.get(i);

            boolean[] show = new boolean[level.size()];

            switch (method) {
                case NOTHING:
                    break;
                case ALL_NULL:
                    for (int j = 0; j < level.size(); j++) {
                        show[j] = true;
                    }
                    break;
                case NO_NULL:
                    for (int j = 0; j < level.size(); j++) {
                        show[j] = level.get(j) != null;
                    }
                    break;
                case SOME_NULL:
                    if (prevLevel != null) {
                        for (int j = 0; j < level.size(); j++) {
                            show[j] = prevLevel.get(j/2) != null;
                        }
                    } else {
                        show[0] = true;
                    }
                    break;
            }


            int lineTotalSizeItems = level.size()*filledStringSize;
            int lineTotalSpaceSize = maxLevelStringSize-lineTotalSizeItems;
            double lineSpaceItem = (double)lineTotalSpaceSize/level.size();
            out.append(getConnectionLineAbove(show, lineSpaceItem, filledStringSize));
            out.append(getLine(level, show, lineSpaceItem, filledStringSize));

            prevLevel = level;
        }
        return out.toString();
    }

    public String toString() {
        return toString(StringMethods.SOME_NULL);
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>(5);
        bst.add(10);
        bst.add(9);
        bst.add(200);
        bst.add(15);

        System.out.println(bst.getRight().toString());
    }


    // Iterators
    public ArrayList<ArrayList<BinaryTree<E>>> getLevelItems() {
        ArrayList<ArrayList<BinaryTree<E>>> out = new ArrayList<>();

        for (LevelOrderTraversal<E> iter = (LevelOrderTraversal<E>)iterator(); iter.hasNext(); ) {
            BinaryTree<E> next = iter.next();
            if (iter.getLevel() == out.size())
                out.add(new ArrayList<>());
            out.get(iter.getLevel()).add(next);
        }

        return out;
    }

    @NotNull
    @Override
    public Iterator<BinaryTree<E>> iterator() {
        return new LevelOrderTraversal<E>(this);
    }

    private class LevelOrderTraversal<E> implements Iterator<BinaryTree<E>> {
        BinaryTree<E> tree;
        Queue<BinaryTree<E>> treeQueue;
        int pos;

        public LevelOrderTraversal(BinaryTree<E> tree, int pos) {
            this.tree = tree;
            this.pos = pos;
            this.treeQueue = new LinkedList<>();
            treeQueue.add(tree);
        }

        public LevelOrderTraversal(BinaryTree<E> tree) {
            this(tree, 0);
        }

        @Override
        public boolean hasNext() {
            return Math.pow(2,tree.getHeight()+1)-1 > pos;
        }

        @Override
        public BinaryTree<E> next() {
            BinaryTree<E> out = treeQueue.poll();

            if (out != null && out.hasLeft())
                treeQueue.add(out.getLeft());
            else
                treeQueue.add(null);


            if (out != null && out.hasRight())
                treeQueue.add(out.getRight());
            else
                treeQueue.add(null);

            pos++;
            return out;
        }

        public int getLevel() {
            return (int)Math.floor(Math.log(pos)/Math.log(2));
        }
    }
}
