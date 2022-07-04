/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmt8;

import java.util.NoSuchElementException;
import javax.swing.JTree;
import javax.swing.tree.*;
import org.junit.*;
import turban.utils.IGuifiable;

/**
 *
 * @author Noah
 */
public class TestTreeSelManager{

    @Test
    public void testGetSelectedTreeNode(){
        DefaultTreeModel model = new DefaultTreeModel(new FlexibleTreeNode<>(new MyGuifiableObject("Root")));
        JTree tree = new JTree(model);
        @SuppressWarnings("unchecked")
        TreeSelManager<? extends IGuifiable> manager = new TreeSelManager<>(tree);
        Assert.assertThrows("Not Null", NoSuchElementException.class, () -> manager.getSelectedTreeNode().get());
        tree.addSelectionPath(new TreePath(model.getRoot()));
        Assert.assertNotNull("No Node selected", manager.getSelectedTreeNode().get());
    }
}
