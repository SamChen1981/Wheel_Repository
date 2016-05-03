package com.sunzequn.fpgrowth.fpgrowth;

import java.util.*;

/**
 * Created by Sloriac on 16/5/2.
 */
public class FPTree {

    private int minSupportCount;
    private FPNode root;
    private Map<Integer, FPNode> headerTable;

    public FPTree() {
        root = new FPNode();
        headerTable = new HashMap<>();
    }

    public Map<Integer, FPNode> getHeaderTable() {
        return headerTable;
    }

    public FPNode getRoot() {
        return root;
    }

    public int getMinSupportCount() {
        return minSupportCount;
    }

    /**
     * Construct a FP-Tree by all itemsets and the minimum support threshold.
     *
     * @param itemSets
     * @param itemsFrequency
     * @param minSupportThreshold
     */
    public void build(List<List<Integer>> itemSets, Map<Integer, Integer> itemsFrequency, double minSupportThreshold) {
        minSupportCount = (int) (itemSets.size() * minSupportThreshold);
        insertItemSets(itemSets, itemsFrequency, minSupportCount);
    }

    /**
     * Insert all itemsets into FP-Tree.
     *
     * @param itemSets
     * @param itemsFrequency
     * @param minSupportCount
     */
    public void insertItemSets(List<List<Integer>> itemSets, Map<Integer, Integer> itemsFrequency, int minSupportCount) {
        for (List<Integer> itemSet : itemSets) {
            List<Integer> filteredItemSet = filterItemSet(itemSet, itemsFrequency, minSupportCount);
            List<Integer> orderedFilteredItemSet = getOrderedItemSet(filteredItemSet, itemsFrequency);
            insertItemSet(orderedFilteredItemSet);
        }
    }

    /**
     * Get a new <code>itemSet</code> by removing items whose frequency is less than the threshold.
     *
     * @param itemSet
     * @param itemsFrequency
     * @return
     */
    public List<Integer> filterItemSet(List<Integer> itemSet, Map<Integer, Integer> itemsFrequency, int minSupportCount) {
        List<Integer> filteredItemSet = new ArrayList<>();
        for (Integer item : itemSet) {
            if (itemsFrequency.get(item) >= minSupportCount) {
                filteredItemSet.add(item);
            }
        }
        return filteredItemSet;
    }


    /**
     * Insert an itemset into FP-Tree.
     *
     * @param itemSet
     */
    public void insertItemSet(List<Integer> itemSet) {
        FPNode parent = this.getRoot();
        for (Integer item : itemSet) {
            FPNode node = parent.getChild(item);
            if (node == null) {
                node = new FPNode(item, 1);
                parent.addChild(node);
                updateNeighbour(node);
            } else {
                node.addCount(1);
            }
            parent = node;
        }
    }

    /**
     * Update header table or threaded node
     *
     * @param node
     */
    public void updateNeighbour(FPNode node) {
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
    public List<Integer> getOrderedItemSet(List<Integer> itemSet, Map<Integer, Integer> itemsFrequency) {
        Collections.sort(itemSet, new ItemSetComparator(itemsFrequency));
        return itemSet;
    }

    /**
     * Get conditional pattern base (prefix paths) of an item.
     *
     * @param item
     * @return
     */
    public List<List<FPNode>> buildConditionalPatternBaseOfItem(int item) {
        List<FPNode> nodes = getNodesOfItem(item);
        List<List<FPNode>> conditionalPatternBase = new ArrayList<>();
        for (FPNode node : nodes) {
            List<FPNode> transformedPrefixPath = transformPrefixPath(findPrefixPathOfNode(node));
            if (transformedPrefixPath.size() > 0) {
                conditionalPatternBase.add(transformedPrefixPath);
            }
        }
        return conditionalPatternBase;
    }

    /**
     * Get the transformed prefix path.
     * The prefix subpath of node ai in a path P can be copied and transformed into a count-adjusted prefix subpath
     * by adjusting the frequency count of every node in the prefix subpath to the same as the count of node ai.
     *
     * @param prefixPath
     * @return
     */
    public List<FPNode> transformPrefixPath(List<FPNode> prefixPath) {
        List<FPNode> transformedPrefixPath = new ArrayList<>();
        FPNode conditionalItem = prefixPath.get(prefixPath.size() - 1);
        for (int i = 0; i < prefixPath.size() - 1; i++) {
            FPNode node = new FPNode(prefixPath.get(i).getItem(), conditionalItem.getCount());
            transformedPrefixPath.add(node);
        }
        return transformedPrefixPath;
    }

    /**
     * Find the prefix path of a node and the tail of path is the node itself.
     *
     * @param node
     * @return
     */
    public List<FPNode> findPrefixPathOfNode(FPNode node) {
        List<FPNode> path = new ArrayList<>();
        while (node.getParent() != null) {
            path.add(node);
            node = node.getParent();
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Get threaded nodes of an item.
     *
     * @param item
     * @return
     */
    public List<FPNode> getNodesOfItem(int item) {
        List<FPNode> nodes = new ArrayList<>();
        FPNode node = headerTable.get(item);
        nodes.add(node);
        while (node.hasNeighbour()) {
            node = node.getNeighbour();
            nodes.add(node);
        }
        return nodes;
    }


    public FPTree buildConditionalFPTreeOfItem(int item, int threshold) {
        FPTree tree = new FPTree();
        List<List<FPNode>> conditionalPatternBase = buildConditionalPatternBaseOfItem(item);
        Set<Integer> filteredItems = filterConditionalPatternBase(conditionalPatternBase, threshold);
        for (List<FPNode> nodes : conditionalPatternBase) {
            tree = insertOneConditionalPatternBase(tree, nodes);
        }
        return filterConditionalFPTree(tree, filteredItems);
    }

    public FPTree filterConditionalFPTree(FPTree tree, Set<Integer> filteredItems){
        for (Integer filteredItem : filteredItems) {
            List<FPNode> filteredNodes = tree.getNodesOfItem(filteredItem);
            for (FPNode filteredNode : filteredNodes) {
                if (filteredNode.getParent() != null){
                    filteredNode.getParent().removeChild(filteredNode);
                }
            }
        }
        return tree;
    }

    public FPTree insertOneConditionalPatternBase(FPTree tree, List<FPNode> oneConditionalPatternBase) {
        FPNode parent = tree.getRoot();
        for (FPNode node : oneConditionalPatternBase) {
            FPNode oldNode = parent.getChild(node.getItem());
            if (oldNode == null) {
                oldNode = node;
                parent.addChild(oldNode);
                tree.updateNeighbour(oldNode);
            } else {
                oldNode.addCount(node.getCount());
            }
            parent = oldNode;
        }
        return tree;
    }

    public Set<Integer> filterConditionalPatternBase(List<List<FPNode>> conditionalPatternBase, int threshold) {
        Set<Integer> filteredItems = new HashSet<>();
        Map<Integer, Long> conditionalPatternBaseItemsFrequency = new HashMap<>();
        for (List<FPNode> nodes : conditionalPatternBase) {
            for (FPNode node : nodes) {
                Long count = conditionalPatternBaseItemsFrequency.get(node.getItem());
                if (count == null) {
                    conditionalPatternBaseItemsFrequency.put(node.getItem(), node.getCount());
                } else {
                    conditionalPatternBaseItemsFrequency.put(node.getItem(), node.getCount() + count);
                }
            }
        }
        for (Integer item : conditionalPatternBaseItemsFrequency.keySet()) {
            if (conditionalPatternBaseItemsFrequency.get(item) < threshold) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public Map<Integer, List<FPNode>> getItems() {
        Map<Integer, List<FPNode>> itemMap = new HashMap<Integer, List<FPNode>>();
        for (Integer item : headerTable.keySet()) {
            itemMap.put(item, getNodesOfItem(item));
        }
        return itemMap;
    }

    public int getSupportForItem(int item) {
        List<FPNode> nodes = getNodesOfItem(item);
        int support = 0;
        for (FPNode node : nodes) {
            support += node.getCount();
        }
        return support;
    }

    public void print() {
        System.out.println(this.getRoot());
        getRoot().getChildren().forEach(this::printNode);
    }

    public void printNode(FPNode tree) {
        System.out.println(tree);
        tree.getChildren().forEach(this::printNode);
    }

}
