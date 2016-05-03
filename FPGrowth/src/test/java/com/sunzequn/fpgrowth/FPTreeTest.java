package com.sunzequn.fpgrowth;

import com.sunzequn.fpgrowth.fpgrowth.FPGrowth;
import com.sunzequn.fpgrowth.fpgrowth.FPNode;
import com.sunzequn.fpgrowth.fpgrowth.FPTree;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by sunzequn on 2016/5/3.
 */
public class FPTreeTest {

    private FPTree tree;
    private List<Integer> itemset1, itemset2, itemset3, itemset4;
    private List<List<Integer>> itemsets;
    private static Map<Integer, Integer> itemsFrequency;

    @Before
    public void init() {
        tree = new FPTree();
        itemsets = new ArrayList<>();
        itemset1 = Arrays.asList(1, 2, 3);
        itemset2 = Arrays.asList(1, 2, 3, 4);
        itemset3 = Arrays.asList(1, 2, 4, 5);
        itemset4 = Arrays.asList(4, 5);
        itemsets.add(itemset1);
        itemsets.add(itemset2);
        itemsets.add(itemset3);
        itemsets.add(itemset4);
        itemsFrequency = FPGrowth.computeItemsFrequency(itemsets);
        System.out.println(itemsFrequency);
        tree.build(itemsets, itemsFrequency, 0);
    }

    @Test
    public void filterItemSetTest(){
        System.out.println(tree.filterItemSet(itemset1, itemsFrequency, 0));
        System.out.println(tree.filterItemSet(itemset1, itemsFrequency, 1));
    }

    @Test
    public void buildTest(){
        assertEquals(3, tree.getRoot().getChild(1).getCount());
        assertEquals(1, tree.getRoot().getChild(4).getCount());
        assertEquals(3, tree.getRoot().getChild(1).getChild(2).getCount());
        assertEquals(1, tree.getRoot().getChild(1).getChild(2).getChild(3).getCount());
        assertEquals(2, tree.getRoot().getChild(1).getChild(2).getChild(4).getCount());
        assertEquals(1, tree.getRoot().getChild(1).getChild(2).getChild(4).getChild(3).getCount());
        tree.print();
    }

    @Test
    public void getOrderedItemSetTest(){
        System.out.println(tree.getOrderedItemSet(itemset1, itemsFrequency));
        System.out.println(tree.getOrderedItemSet(itemset2, itemsFrequency));
        System.out.println(tree.getOrderedItemSet(itemset3, itemsFrequency));
        System.out.println(tree.getOrderedItemSet(itemset4, itemsFrequency));
    }

    @Test
    public void insertItemSetTest(){
        tree.insertItemSet(itemset1);
        tree.insertItemSet(itemset2);
        tree.insertItemSet(itemset3);
        tree.insertItemSet(itemset4);
        assertEquals(3, tree.getRoot().getChild(1).getCount());
    }

    @Test
    public void getNodesOfItemTest(){
        System.out.println(tree.getNodesOfItem(1));
        System.out.println(tree.getNodesOfItem(2));
        System.out.println(tree.getNodesOfItem(3));
        System.out.println(tree.getNodesOfItem(4));
        System.out.println(tree.getNodesOfItem(5));
    }

    @Test
    public void findPrefixPathOfNodeTest(){
        for (FPNode node : tree.getNodesOfItem(4)) {
            System.out.println(tree.findPrefixPathOfNode(node));
        }
    }

    @Test
    public void buildConditionalPatternBaseOfItemTest(){
        System.out.println(tree.buildConditionalPatternBaseOfItem(4));
    }

    @Test
    public void buildConditionalFPTreeOfItemTest(){
        tree.buildConditionalFPTreeOfItem(3, 2).print();
//        FPTree fp50 = tree.buildConditionalFPTreeOfItem(5, 0);
//        fp50.print();
//        FPTree fp52 = tree.buildConditionalFPTreeOfItem(5, 2);
//        fp52.print();
//        FPTree fp4 = tree.buildConditionalFPTreeOfItem(4, 2);
//        fp4.print();

    }

}
