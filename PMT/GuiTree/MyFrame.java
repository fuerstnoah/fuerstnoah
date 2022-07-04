/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.pmt7;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import turban.utils.IGuifiable;

/**
 *
 * @author noah
 */
@SuppressWarnings("serial")
public class MyFrame extends JFrame{

    private DefaultTreeModel treeModel;
    private JTree tree;
    private JScrollPane scroll;
    private JButton add, edit, remove;
    private TextField text;
    private JCheckBox check;
    private JPanel ctr, left, right;

    public MyFrame(){
        buildUi();
    }

    private void buildUi(){
        buildTree();
        buildButtons();
        buildPanels();
        buildFrame();
    }

    private void buildFrame(){
        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(ctr, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        treeModel.reload();
        setVisible(true);
    }

    private void buildPanels(){
        ctr = new JPanel(new BorderLayout());
        left = new JPanel(new GridLayout());
        right = new JPanel(new GridLayout());
        text = new TextField();
        ctr.add(text, BorderLayout.CENTER);
        left.add(add);
        left.add(remove);
        right.add(edit);
        right.add(check);
        ctr.add(left, BorderLayout.WEST);
        ctr.add(right, BorderLayout.EAST);
    }

    private void buildButtons(){
        add = new JButton("Add");
        add.addActionListener((e) -> add());
        edit = new JButton("Edit");
        edit.setEnabled(false);
        edit.addActionListener((e) -> edit());
        remove = new JButton("Remove");
        remove.setEnabled(false);
        remove.addActionListener((e) -> remove());
        check = new JCheckBox("Show Root");
        check.addActionListener((e) -> checked());
        check.setSelected(true);
    }

    private void buildTree(){
        treeModel = new DefaultTreeModel(null);
        tree = new JTree(treeModel);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener((e) -> valueChanged());
        tree.setCellRenderer(new DefaultTreeCellRenderer());
        scroll = new JScrollPane(tree);
        scroll.setPreferredSize(new Dimension(500, 500));
    }

    private void add(){
        if(treeModel.getRoot() == null){
            setRoot();
        }else if(getSelected() == null){
            selectRoot();
        }else{
            addSelected();
        }
    }

    private void edit(){
        getSelected().setUserObject(newGuiObj());
        treeModel.nodeChanged(getSelected());
    }

    private void remove(){
        if(getSelected().isRoot()){
            treeModel.setRoot(null);
        }else{
            treeModel.removeNodeFromParent(getSelected());
        }
    }

    private void addSelected(){
        getSelected().add(new FlexibleTreeNode<>(newGuiObj()));
        treeModel.reload(getSelected());
    }

    @SuppressWarnings("unchecked")
    private void selectRoot(){
        ((DefaultMutableTreeNode) treeModel.getRoot()).add(new FlexibleTreeNode<>(newGuiObj()));
        treeModel.reload((TreeNode) treeModel.getRoot());
    }

    private void setRoot(){
        treeModel.setRoot(new FlexibleTreeNode<>(newGuiObj()));
        treeModel.reload(getSelected());
    }

    private String getInput() throws HeadlessException{
        String input = text.getText();
        if(input == null || input.isBlank()){
            input = JOptionPane.showInputDialog("Enter Node Name");
        }
        return input;
    }

    @SuppressWarnings("unchecked")
    private FlexibleTreeNode<IGuifiable> getSelected(){
        return (FlexibleTreeNode<IGuifiable>) tree.getLastSelectedPathComponent();
    }

    private MyGuifiableObject newGuiObj(){
        return new MyGuifiableObject(getInput());
    }

    private void valueChanged(){
        if(getSelected() == null){
            edit.setEnabled(false);
            remove.setEnabled(false);
        }else{
            edit.setEnabled(true);
            remove.setEnabled(true);
        }
    }

    private void checked(){
        tree.setRootVisible(check.isSelected());
    }

    public static void main(String... args){
        new MyFrame();
    }

}
