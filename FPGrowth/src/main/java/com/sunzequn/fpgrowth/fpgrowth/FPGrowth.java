package com.sunzequn.fpgrowth.fpgrowth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sloriac on 16/5/2.
 */
public class FPGrowth {

    private static List<List<Integer>> itemSets;
    private static Map<Integer, Integer> itemsFrequency;

    public static void preprocess(String filePath, String separator) {
        itemSets = FileUtil.readDataFromFile(filePath, separator);
        itemsFrequency = computeItemsFrequency(itemSets);
    }

    public static FPTree buildFPTree(double minSupportThreshold) {
        FPTree fpTree = new FPTree();
        fpTree.build(itemSets, itemsFrequency, minSupportThreshold);
        return fpTree;
    }

    public static List<List<Integer>> findFrequentItemset(FPTree tree, List<Integer> suffix){
        int minSupportCount = tree.getMinSupportCount();
        System.out.println(minSupportCount);
        return findFrequentItemset(tree, suffix, minSupportCount);
    }

    public static List<List<Integer>> findFrequentItemset(FPTree tree, List<Integer> suffix, int minSupportCount){
        List<List<Integer>> frequentItemset = new ArrayList<>();
        for (Integer item : tree.getItems().keySet()) {
            int support = tree.getSupportForItem(item);
            if (support >= minSupportCount && !suffix.contains(item)) {
                List<Integer> found = new ArrayList<Integer>();
                found.addAll(suffix);
                found.add(item);
                frequentItemset.add(found);
                FPTree conditionalTree = tree.buildConditionalFPTreeOfItem(item, minSupportCount);
                frequentItemset.addAll(findFrequentItemset(conditionalTree, found, minSupportCount));
            }
        }
        return frequentItemset;
    }

    /**
     * compute the frequency of each item.
     *
     * @param itemSets set of items
     * @return the frequency of items
     */
    public static Map<Integer, Integer> computeItemsFrequency(List<List<Integer>> itemSets) {
        Map<Integer, Integer> itemsFrequency = new HashMap<>();
        for (List<Integer> itemSet : itemSets) {
            for (Integer item : itemSet) {
                Integer frequency = itemsFrequency.get(item);
                if (frequency == null) {
                    frequency = 0;
                }
                frequency++;
                itemsFrequency.put(item, frequency);
            }
        }
        return itemsFrequency;
    }


}
