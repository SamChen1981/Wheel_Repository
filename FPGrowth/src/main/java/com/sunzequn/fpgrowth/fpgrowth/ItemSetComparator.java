package com.sunzequn.fpgrowth.fpgrowth;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Sloriac on 16/5/2.
 */
public class ItemSetComparator implements Comparator<Integer> {

    private final Map<Integer, Integer> itemsFrequency;

    public ItemSetComparator(Map<Integer, Integer> itemsFrequency) {
        this.itemsFrequency = itemsFrequency;
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        return itemsFrequency.get(o2) - itemsFrequency.get(o1);
    }
}
