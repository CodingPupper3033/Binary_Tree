import Trees.RBTree;

import java.util.ArrayList;

public class IntegerRBTreeMaker {
    ArrayList<Integer> order;
    RBTree<Integer> tree;

    public IntegerRBTreeMaker(int minN, int maxN, int amount) {
        order = createRandomTreeAdd(minN, maxN, amount);
        tree = new RBTree<>();
        tree.addAll(order.toArray(new Integer[order.size()]));
    }

    private static ArrayList<Integer> createRandomTreeAdd(int minN, int maxN, int amount) {
        int diff = maxN-minN;

        ArrayList<Integer> out = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            int numb = (int) (Math.random()*diff)+minN;
            out.add(numb);
        }
        return out;
    }


}
