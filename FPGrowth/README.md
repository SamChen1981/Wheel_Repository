## FP-Growth
- - -

### Introduce
This is a java implementation of the FPGrowth algorithm.I refer to a opensource project: [relekang/fpgrowth](https://github.com/relekang/fpgrowth) and reuse some code of it. I modify and improve the project in order to align the code to the idea of FPGrowth algorithm presented in this [paper](http://hanj.cs.illinois.edu/pdf/dami04_fptree.pdf). Moreover, I fix some 'bugs'(not sure, but I think it`s better to fix them).

### Usage
* The format of data file should follow the example below (with a separator between two item IDs):  

 > itemId1 itemId2 ...  
   itemId2 itemId3 ...  
   itemId4 itemId5 ...  
  
(We call each row of data an **itemIdSet** rather than a transaction because it may be easier to understand.)
