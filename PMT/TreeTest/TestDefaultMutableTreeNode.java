package com.mycompany.pmt6;

import java.util.*;
import javax.swing.tree.*;
import org.junit.*;
import static org.junit.Assert.*;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Noah
 */
public class TestDefaultMutableTreeNode {

    @Test
    public void testRootAndChildren() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root Object");
        DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("Child 1");
        root.add(child1);
        DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("Child 2");
        root.add(child2);
    }

    @Before
    public DefaultMutableTreeNode setUp() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("0. Root");
        DefaultMutableTreeNode parent1 = new DefaultMutableTreeNode("1. Parent");
        DefaultMutableTreeNode parent2 = new DefaultMutableTreeNode("1.1 Parent");
        DefaultMutableTreeNode leaf1 = new DefaultMutableTreeNode("1.1.1 Leaf");
        DefaultMutableTreeNode leaf2 = new DefaultMutableTreeNode("1.1.2 Leaf");
        DefaultMutableTreeNode leaf3 = new DefaultMutableTreeNode("1.2 Leaf");
        DefaultMutableTreeNode parent3 = new DefaultMutableTreeNode("2. Parent");
        DefaultMutableTreeNode leaf4 = new DefaultMutableTreeNode("2.1 Leaf");
        DefaultMutableTreeNode leaf5 = new DefaultMutableTreeNode("3. Leaf");

        root.add(parent1);
        root.add(parent3);
        root.add(leaf5);
        parent1.add(parent2);
        parent1.add(leaf3);
        parent2.add(leaf1);
        parent2.add(leaf2);
        parent3.add(leaf4);

        return root;
    }

    @Test
    public void testGetParent() {
        DefaultMutableTreeNode root = setUp();
        DefaultMutableTreeNode leaf = root.getFirstLeaf();
        assertEquals("Failed", leaf.getParent(), root.getFirstChild().getChildAt(0));
        assertNull("Root not Null", root.getParent());
    }

    @Test
    public void testGetUserObject() {
        DefaultMutableTreeNode root = setUp();
        DefaultMutableTreeNode leaf = root.getFirstLeaf();
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) root.getChildAt(1);
        assertEquals("Failed", (String) leaf.getUserObject(), "1.1.1 Leaf");
        assertEquals("Failed", (String) parent.getUserObject(), "2. Parent");
    }

    @Test
    public void testRemove() {
        DefaultMutableTreeNode root = setUp();
        DefaultMutableTreeNode leaf = root.getFirstLeaf();
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) root.getChildAt(1);

        leaf.removeFromParent();
        parent.removeFromParent();

        assertNull("Failed", leaf.getParent());
        assertNull("Failed", parent.getParent());
        assertNotEquals("Failed", root.getChildAt(1), parent);
        assertNotEquals("Failed", root.getFirstLeaf(), leaf);
    }
    
    public DefaultMutableTreeNode getRoot(DefaultMutableTreeNode tn){
        while(!tn.isRoot()){
            tn = (DefaultMutableTreeNode) tn.getParent();
        }
        return tn;
    }
    
    @Test
    public void testGetRoot(){
        DefaultMutableTreeNode node = setUp().getLastLeaf();
        assertTrue("Node isn't Root", getRoot(node).isRoot());
    }
    
    public List<DefaultMutableTreeNode> getLeaves(DefaultMutableTreeNode tn){
        List<DefaultMutableTreeNode> leaves = new ArrayList<>();
        if(tn.isLeaf()){
            leaves.add(tn);
        }else{
            tn.children().asIterator().forEachRemaining((TreeNode child) -> {
                if(child.isLeaf())leaves.add((DefaultMutableTreeNode) child);
                else leaves.addAll(getLeaves((DefaultMutableTreeNode) child));
            });
        }
        
        return leaves;
    }
    
    @Test
    public void testGetLeaves(){
        getLeaves(setUp()).forEach(leave -> {
            assertTrue("Node isn't leave", leave.isLeaf());
        });
    }
}
