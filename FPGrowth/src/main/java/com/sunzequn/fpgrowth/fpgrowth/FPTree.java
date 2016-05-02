package com.sunzequn.fpgrowth.fpgrowth;

import java.util.*;

/**
 * Created by Sloriac on 16/5/2.
 */
public class FPTree {

    private int numOfItemSets;
    private FPNode root;
    private Map<Integer, FPNode> headerTable;

    public FPTree() {
        root = new FPNode();
        headerTable = new HashMap<>();
    }


    public void build(List<List<Integer>> itemSets, Map<Integer, Integer> itemsFrequency, double minSupportThreshold) {
        numOfItemSets = itemSets.size();
        int threshold = (int) (numOfItemSets * minSupportThreshold);
        insertItemSets(itemSets, itemsFrequency, threshold);
    }

    private void insertItemSets(List<List<Integer>> itemSets, Map<Integer, Integer> itemsFrequency, int threshold) {
        for (List<Integer> itemSet : itemSets) {
            List<Integer> filteredItemSet = filterItemSet(itemSet, itemsFrequency, threshold);
            insertItemSet(filteredItemSet, itemsFrequency);
        }
    }


    /**
     * Get a new <code>itemSet</code> by removing items whose frequency is less than the threshold
     * in order to speed up sorting while inserting itemsets.
     *
     * @param itemSet
     * @param filteredItemsFrequency
     * @return
     */
    private List<Integer> filterItemSet(List<Integer> itemSet, Map<Integer, Integer> filteredItemsFrequency, int threshold) {
        List<Integer> filteredItemSet = new ArrayList<>();
        for (Integer item : itemSet) {
            if (filteredItemsFrequency.get(item) >= threshold) {
                filteredItemSet.add(item);
            }
        }
        return filteredItemSet;
    }


    /**
     * Insert a itemset into FP-Tree.
     *
     * @param itemSet
     * @param itemsFrequency
     */
    private void insertItemSet(List<Integer> itemSet, Map<Integer, Integer> itemsFrequency) {
        List<Integer> orderedItemSet = getOrderedItemSet(itemSet, itemsFrequency);
        FPNode parent = this.root;
        for (Integer item : orderedItemSet) {
            if (!parent.hasChild(item)) {
                FPNode node = new FPNode(item, 1);
                parent.insertChild(node);
                updateNeighbour(node);
            } else {
                parent.insertCount(1);
            }
            parent = parent.getChild(item);
        }
    }

    private void updateNeighbour(FPNode node) {
        FPNode header = headerTable.get(node.getItem());
        //insert the node to header table if it is not existed
        if (header == null) {
            headerTable.put(node.getItem(), node);
        }
        //insert the node to the tail of the neighbour node list
        else {
            while (header.hasNeighbour()) {
                header = header.getNeighbour();
            }
            header.setNeighbour(node);
        }
    }

    /**
     * List a item set according to the frequency of items.
     *
     * @param itemSet        a item set
     * @param itemsFrequency the frequency of items
     * @return item s in frequency-descending order
     */
    private static List<Integer> getOrderedItemSet(List<Integer> itemSet, Map<Integer, Integer> itemsFrequency) {
        Collections.sort(itemSet, new ItemSetComparator(itemsFrequency));
        return itemSet;
    }
}
