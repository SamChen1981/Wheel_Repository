package com.sunzequn.fpgrowth.fpgrowth;

import java.util.*;

/**
 * Created by sunzequn on 2016/5/2.
 */
public class FPNode {
    //item 
    private int item;
    //the number of times that item occurs
    private long count;
    //parent node
    private FPNode parent;
    //child nodes, using <code>Map</code> because children are different from each other.
    private Map<Integer, FPNode> children;
    //threaded node, pointing to another node with the same .
    private FPNode neighbour;

    public FPNode() {
        item = -1;
        count = -1;
        children = new HashMap<>();
    }

    public FPNode(int item, long count) {
        this.item = item;
        this.count = count;
        children = new HashMap<>();
    }

    public int getItem() {
        return item;
    }

    public long getCount() {
        return count;
    }

    public void addCount(long count) {
        this.count += count;
    }

    public FPNode getParent() {
        return parent;
    }

    public void setParent(FPNode parent) {
        this.parent = parent;
    }

    public void addChild(FPNode child) {
        if (!children.containsKey(child.getItem())) {
            children.put(child.getItem(), child);
            child.setParent(this);
        }
    }

    public void removeChild(FPNode node) {
        this.children.remove(node.getItem());
        node.parent = null;
    }

    public FPNode getChild(int item) {
        return children.get(item);
    }

    public Collection<FPNode> getChildren() {
        return children.values();
    }

    public FPNode getNeighbour() {
        return neighbour;
    }

    public void setNeighbour(FPNode neighbour) {
        this.neighbour = neighbour;
    }

    public boolean hasNeighbour() {
        return !(getNeighbour() == null);
    }

    public String toString() {
        String string = "";
        if (children != null){
            for (Integer integer : children.keySet()) {
                string = string + integer + " " ;
            }
        }
        return "id:" + item + ";count:" + count + ";children:"
                + string.trim();
    }

}
