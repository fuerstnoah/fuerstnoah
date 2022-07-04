/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmt8;

import java.util.Optional;
import javax.swing.JTree;
import turban.utils.IGuifiable;

/**
 *
 * @author Noah
 */
public class TreeSelManager<UO extends IGuifiable>{

    private final JTree tree;

    public TreeSelManager(JTree _tree){
        tree = _tree;
    }

    @SuppressWarnings("unchecked")
    public Optional<FlexibleTreeNode<UO>> getSelectedTreeNode(){
        return Optional.ofNullable((FlexibleTreeNode<UO>) tree.getLastSelectedPathComponent());
    }

}
