package com.sunzequn.fpgrowth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunzequn on 2016/5/2.
 */
public class Util {

    /**
     * Read the input data to find frequent itemsets.

     *
     * @param filePath
     * @param separator
     * @return
     */
    public static List<List<Integer>> readDataFromFile(String filePath, String separator){
        try {
            List<List<Integer>> itemIdSets = new ArrayList<List<Integer>>();
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                List<Integer> itemIdSet = parseDataLine(line, separator);
                itemIdSets.add(itemIdSet);
            }
            return itemIdSets;
        }catch (Exception e){
            System.out.println("Please check the path or format of your data file. ");
            e.printStackTrace();
        }
        return null;
    }

    private static List<Integer> parseDataLine(String line, String separator){
        if (isNullOrEmpty(line)){
            return null;
        }
        line = line.trim();
        List<Integer> itemSet = new ArrayList<Integer>();
        String[] itemIdArray = line.split(separator);
        for (String itemId : itemIdArray) {
            itemSet.add(Integer.parseInt(itemId));
        }
        return itemSet;
    }

    private static boolean isNullOrEmpty(String string){
        return string == null || string.trim().equals("");
    }
}
