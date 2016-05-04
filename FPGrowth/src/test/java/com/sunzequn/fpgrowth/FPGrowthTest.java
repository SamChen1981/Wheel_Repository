package com.sunzequn.fpgrowth;

import com.sunzequn.fpgrowth.fpgrowth.FPGrowth;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by sunzequn on 2016/5/3.
 */
public class FPGrowthTest {

    @Before
    public void preprocessTest(){
        String dataFile = "src/main/resources/data";
        FPGrowth.preprocess(dataFile, " ");
    }

    @Test
    public void buildTest(){
        FPGrowth.buildFPTree(0.5);
    }

    @Test
    public void findFrequentItemsetTest(){
        System.out.println(FPGrowth.findFrequentItemset(FPGrowth.buildFPTree(0.51), new ArrayList<>(), 2));
    }
}
