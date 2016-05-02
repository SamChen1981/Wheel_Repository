package com.sunzequn.fpgrowth.fpgrowth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sloriac on 16/5/2.
 */
public class FPGrowth {

    private static FPTree fpTree;
    private static List<List<Integer>> itemSets;
    private static Map<Integer, Integer> itemsFrequency;
    private static double minSupportThreshold;

    public static void preprocess(String filePath, String separator) {
        itemSets = FileUtil.readDataFromFile(filePath, separator);
        itemsFrequency = computeItemsFrequency(itemSets);
    }

    public static void buildFPTree(List<List<Integer>> itemSets, Map<Integer, Integer> itemsFrequency, double minSupportThreshold) {
        fpTree = new FPTree();
    }

    /**
     * compute the frequency of each item.
     *
     * @param itemSets set of items
     * @return the frequency of items
     */
    private static Map<Integer, Integer> computeItemsFrequency(List<List<Integer>> itemSets) {
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
