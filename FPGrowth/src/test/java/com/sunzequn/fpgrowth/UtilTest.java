package com.sunzequn.fpgrowth;

import com.sunzequn.fpgrowth.fpgrowth.FileUtil;
import org.junit.Test;

import java.util.List;

/**
 * Created by Sloriac on 16/5/2.
 */
public class UtilTest {

    @Test
    public void readDataFromFileTest() {
        String dataFile = "src/main/resources/data";
        List<List<Integer>> itemIdSets = FileUtil.readDataFromFile(dataFile, " ");
        System.out.println(itemIdSets != null ? itemIdSets.size() : 0);
    }
}
