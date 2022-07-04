package com.mycompany.lambda;

import javax.swing.tree.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import turban.utils.*;

/**
 * Class for a more flexible TreeNode than DefaultMutableTreeNode. The class is
 * programmed as a generic working on IGuifiable objects
 *
 * @param <T> Type variable for the type of the user object.
 */
public class FlexibleTreeNode<T> extends DefaultMutableTreeNode implements IDebugable {

    /**
     * Constructor
     *
     * IN: The user object to be retrieved by 'getUserObject()'.
     *
     * @param userObj
     */
    public FlexibleTreeNode(T userObj) {
        super(userObj);
        // NOTE (BeTu; 2015-05-19): 'super'-Statement must always be first statement in
        // constructor.
        // Therefore the check for != null is performed afterwards. This is not so nice,
        // but it's quite ok.
        ErrorHandler.Assert(userObj != null, true, FlexibleTreeNode.class, "No user object provided!");
    }

    public void forEach(BiConsumer<FlexibleTreeNode<T>, T> biConsumer){
        getAllTreeNodesAsList(this).forEach((node) -> {
            biConsumer.accept(node, node.getUserObject());
        });
    }
    
    public List<FlexibleTreeNode<T>> getLeaves() {
        List<FlexibleTreeNode<T>> leaves = new ArrayList<>();
        if (this.isLeaf()) {
            leaves.add(this);
        } else {
            this.children().asIterator().forEachRemaining((TreeNode child) -> {
                if (child.isLeaf()) {
                    leaves.add((FlexibleTreeNode) child);
                } else {
                    leaves.addAll(getLeaves());
                }
            });
        }

        return leaves;
    }
    
    public List<T> getAllUserObjects(){
        List<T> lstResults = new ArrayList<>(countTreeNodes(this) + 5);

        getAllUserObjects_recursive(lstResults, this);

        return lstResults;
//        List<T> list = new ArrayList<>();
//        for (FlexibleTreeNode<T> flexibleTreeNode : this.getAllTreeNodesAsList(tnStart)) {
//            list.add(flexibleTreeNode.getUserObject());
//        }
//        return list;
    }
    
    public void getAllUserObjects_recursive(List<T> lstResults, FlexibleTreeNode<T> tnCurrent){
        lstResults.add(tnCurrent.getUserObject());

        for (int i = 0; i < tnCurrent.getChildCount(); i++) {
            @SuppressWarnings("unchecked")
            FlexibleTreeNode<T> tnChild = (FlexibleTreeNode<T>) tnCurrent.getChildAt(i);
            getAllUserObjects_recursive(lstResults, tnChild);
        }
    }

    @Override
    public void setUserObject(Object userObj) {
        this.userObject = userObj;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getUserObject() {
        return (T) super.getUserObject();
    }

    /**
     * Performs indentation (Einrückung) according to the level in the tree.
     */
    private void toDebugString_indent(StringBuilder sb, int indentLvl) {
        for (int i = 0; i < indentLvl; i++) {
            sb.append("  ");
        }
        if (indentLvl > 0) {
            sb.append("|-");
        }
    }

    @SuppressWarnings("unchecked")
    private void toDebugString_recursive(StringBuilder sb, int indentLvl, FlexibleTreeNode<T> tn) {
        toDebugString_indent(sb, indentLvl);
        sb.append(tn.toString());
        sb.append("\n");
        for (TreeNode tnChild : tn.getChildren()) {
            toDebugString_recursive(sb, indentLvl + 1, (FlexibleTreeNode<T>) tnChild);
        }
    }

    /**
     * String to be resolved for Debugging-Purposes
     *
     * @return Returns the content of the tree for debugging purposes.
     */
    @Override
    public String toDebugString() {
        try {
            int capacity = countTreeNodes(this) * 70; // Initiale Cap. berechnen - bei größeren Bäumen wahrscheinlich effizienter!!
            StringBuilder sb = new StringBuilder(capacity);
            toDebugString_recursive(sb, 0, this);
            return sb.toString();
        } catch (Throwable th) {
            return "Unable to fully resolve " + this.getClass().getName() + " [" + this.toString() + "]";
        }
    }

    /**
     * Overwritten toString(). The method now calls toGuiString of the generic
     * object to do easier working with the IGuifiable-Pattern. As this method
     * is used by JTree.
     *
     * @return the string to be displayed in the GUI (e.g., in JTree)
     */
    @Override
    public String toString() {
        try {
            return ((IGuifiable) this.getUserObject()).toGuiString();
        } catch (Throwable th) {
            ErrorHandler.logException(th, false, FlexibleTreeNode.class, "Error resolving toGuiString()");
            return this.getClass().getName() + ": [Unresolvable Symbol!]";
        }
    }

    /**
     * Returns the children as Iterator
     *
     * NOTE: Hier als anonyme Implementierung realisiert -> wir nutzen
     * children(), das eine Enumeration zurückgibt.
     *
     * @return
     */
    public Iterable<FlexibleTreeNode<T>> getChildren() {
        @SuppressWarnings("rawtypes")
        final Enumeration enumChildren = this.children();
        return () -> {
            return new Iterator<FlexibleTreeNode<T>>() {
                @Override
                public boolean hasNext() {
                    return enumChildren.hasMoreElements();
                }

                @SuppressWarnings("unchecked")
                @Override
                public FlexibleTreeNode<T> next() {
                    return (FlexibleTreeNode<T>) enumChildren.nextElement();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        };
    }

    /**
     * Counts the tree nodes underneath the start node including the start node
     *
     * @param tnStart IN: The tree node to start
     * @return the number of nodes
     */
    public static int countTreeNodes(TreeNode tnStart) {
        if (tnStart == null) {
            return 0;
        }

        int iCount = 1; // 0+1 (==this node)
        for (int i = 0; i < tnStart.getChildCount(); i++) {
            TreeNode tnChild = tnStart.getChildAt(i);
            iCount += countTreeNodes(tnChild);
        }
        return iCount;
    }

    /**
     * Gets all tree nodes of the tree defined by tnStart (including tnStart!)
     * as a list HINWEIS: Diese Methode könnte man eigentlich auch static
     * machen, weil das eigentliche Knotenobjekt gar nicht verwendet wird. Ich
     * habe darauf verzichtet, weil wegen des generischen-Typparameters hier
     * sonst Probleme auftreten, die Sie noch mehr verwirren würden. -> Sehen
     * Sie hierzu aber die danach auskommentierte Methode, die zeigt, wie man
     * Sie als static-Methode deklarieren müsste - hauptsächlich müsste man dann
     * einen neuen Typparameter einführen.
     *
     * @param tnStart IN: The tree node to start
     * @return list with the tree nodes.
     */
    public /* static */ List<FlexibleTreeNode<T>> getAllTreeNodesAsList(FlexibleTreeNode<T> tnStart) {

        // Hinweis: Da in der Signatur oben das T verlangt wird können wir keine
        // static-Methode daraus machen, weil wir dazu eine Objektinstanz benötigen.
        List<FlexibleTreeNode<T>> lstRet = new ArrayList<>(); // Ineffizient!!!!!

        lstRet.add(tnStart);

        for (int i = 0; i < tnStart.getChildCount(); i++) {
            FlexibleTreeNode<T> tnChild = (FlexibleTreeNode<T>) tnStart.getChildAt(i);

            lstRet.addAll(getAllTreeNodesAsList(tnChild)); // Ineffizient!!!!!
        }

        return lstRet;
    }

    /*
	 * Andere Möglichkeit: Als static-Methode mit eigener Typvariable: public static
	 * <R extends IGuifiable> List<FlexibleTreeNode<R>>
	 * getAllTreeNodesAsList(FlexibleTreeNode<R> tnStart) { //Hinweis: Hier wird der
	 * Startknoten übergeben, deshalb können wir eine static-Methode daraus machen.
	 * List<FlexibleTreeNode<R>> lstToReturn=new ArrayList<FlexibleTreeNode<R>> ();
	 * if(tnStart==null){ return lstToReturn; //Return empty list }
	 * 
	 * lstToReturn.add(tnStart); for(int i=0; i< tnStart.getChildCount(); i++){
	 * 
	 * @SuppressWarnings("unchecked") FlexibleTreeNode<R> tnChild=
	 * (FlexibleTreeNode<R>)tnStart.getChildAt(i);
	 * lstToReturn.addAll(getAllTreeNodesAsList(tnChild)); } return lstToReturn; }
     */
    private void getAllTreeNodesAsListMoreEfficient_recursive(List<FlexibleTreeNode<T>> lstResults,
            FlexibleTreeNode<T> tnCurrent) {

        lstResults.add(tnCurrent);

        for (int i = 0; i < tnCurrent.getChildCount(); i++) {
            @SuppressWarnings("unchecked")
            FlexibleTreeNode<T> tnChild = (FlexibleTreeNode<T>) tnCurrent.getChildAt(i);
            getAllTreeNodesAsListMoreEfficient_recursive(lstResults, tnChild);
        }
    }

    /**
     * Effizientere Variante von getAllTreeNodesAsList, die nur einmal eine
     * Liste erzeugt und mit this arbeitet.
     *
     * @return list with the tree nodes.
     */
    public List<FlexibleTreeNode<T>> getAllTreeNodesAsListMoreEfficient() {

        List<FlexibleTreeNode<T>> lstResults = new ArrayList<>(countTreeNodes(this) + 5);

        getAllTreeNodesAsListMoreEfficient_recursive(lstResults, this);

        return lstResults;
    }

    /**
     * Gets all tree nodes of the tree defined by tnStart (excluding tnStart!)
     * as List. HINWEIS: Diese Methode könnte man eigentlich auch static machen,
     * weil das eigentliche Knotenobjekt gar nicht verwendet wird. Ich habe
     * darauf verzichtet, weil wegen des generischen-Typparameters hier sonst
     * Probleme auftreten, die Sie noch mehr verwirren würden.
     *
     * @param tnStart IN: The tree node to start
     * @return list with the tree nodes.
     */
    public /* static */ List<FlexibleTreeNode<T>> getAllSubTreeNodesAsList(FlexibleTreeNode<T> tnStart) {

        List<FlexibleTreeNode<T>> lstRet = new ArrayList<>();
        // Wenn Startknoten dabei sein soll:
        // lstRet.add(tnStart);

        for (int i = 0; i < tnStart.getChildCount(); i++) {
            FlexibleTreeNode<T> tnChild = (FlexibleTreeNode<T>) tnStart.getChildAt(i);
            lstRet.add(tnChild); // Hier soll er nicht dabei sein.
            List<FlexibleTreeNode<T>> lstCh = getAllSubTreeNodesAsList(tnChild);
            lstRet.addAll(lstCh);
        }

        return lstRet;
    }

    // Verwendung:
    // FlexibleTreeNode<...> tn= ...
    // List<FlexibleTreeNode<...>>lst=
    // FlexibleTreeNode.getAllSubTreeNodesAsList(tn);
    private static <Z> void getAllTreeNodesAsListMoreEfficient_recursive(FlexibleTreeNode<Z> tnStart,
            List<FlexibleTreeNode<Z>> lstToReturn) {
        lstToReturn.add(tnStart);
        for (int i = 0; i < tnStart.getChildCount(); i++) {
            @SuppressWarnings("unchecked")
            FlexibleTreeNode<Z> tnChild = (FlexibleTreeNode<Z>) tnStart.getChildAt(i);
            // lstToReturn.add(tnChild); Für not including
            getAllTreeNodesAsListMoreEfficient_recursive(tnChild, lstToReturn);
        }
    }

    /**
     * Gets all tree nodes of the tree defined by tnStart (including tnStart!)
     * as List NOTE: Same as getAllTreeNodesAsList, but more efficient because
     * we first create a list and then use it in the recursion HINWEIS: Diese
     * Methode könnte man eigentlich auch static machen, weil das eigentliche
     * Knotenobjekt gar nicht verwendet wird.Ich habe darauf verzichtet, weil
     * wegen des generischen-Typparameters hier sonst Probleme auftreten, die
     * Sie noch mehr verwirren würden.
     *
     * @param <Z>
     * @param tnStart IN: The tree node to start
     * @return list with the tree nodes.
     */
    public static <Z> List<FlexibleTreeNode<Z>> getAllTreeNodesAsListMoreEfficient_old(
            FlexibleTreeNode<Z> tnStart) {
        List<FlexibleTreeNode<Z>> lstToReturn = new ArrayList<>();
        if (tnStart == null) {
            return lstToReturn;
        }
        getAllTreeNodesAsListMoreEfficient_recursive(tnStart, lstToReturn);
        return lstToReturn;
    }

//    public List<FlexibleTreeNode<T>> getAllTreeNodesAsListMoreEfficient_old() {
//        return getAllTreeNodesAsListMoreEfficient_old((FlexibleTreeNode<Z>) this);
//    }

    // FlexibleTreeNode<...> tn= ...
    // tn.getAllTreeNodesAsListMoreEfficient();

    /*
	 * @FunctionalInterface public interface Predicate<T>{ boolean test(T t); //...
	 * (weitere Default-Methoden) }
	 * 
	 * Verwendung getAllNodesWithCondition: FlexibleTreeNode<MyGuifObj> tnRoot=...;
	 * List<FlexibleTreeNode<MyGuifObj>> lstFoundTnds=
	 * tnRoot.getAllNodesWithCondition( new
	 * Predicate<FlexibleTreeNode<MyGuifObj>>(){ public boolean
	 * test(FlexibleTreeNode<MyGuifObj> tn){ return tn.isLeaf(); //return
	 * tn.getChildCount()>5; //return tn.getUserObject() instanceof KlassenTypX;
	 * //return tn.getUserObject().toGuiString().contains ("test"); } } );
     */
    /**
     * Gets all tree nodes of the tree (including the start tree node) meeting a
     * condition
     *
     * @param cond IN: The condition to test as interface
     *
     * @return the treenodes meeting the condition
     */
    public List<FlexibleTreeNode<T>> getAllNodesWithCondition(Predicate<FlexibleTreeNode<T>> cond) {
        // ToDo: Probieren Sie das mal selbst noch:
        throw new UnsupportedOperationException();
    }

}

//**** Verwendungsbeispiel für FlexibleTreeNode:

/*
 
 import java.awt.Image;
import java.util.List;

import turban.utils.IGuifiable;

public class UseFTN implements IGuifiable {

	
	public static void main(String[] args) {
		FlexibleTreeNode<UseFTN> tnRoot= new FlexibleTreeNode<>(new UseFTN());
		
		FlexibleTreeNode<UseFTN> tnChild= new FlexibleTreeNode<>(new UseFTN());
		tnRoot.add(tnChild);
		
		FlexibleTreeNode<UseFTN> tnChild2= new FlexibleTreeNode<>(new UseFTN());
		tnChild.add(tnChild2);
		
		FlexibleTreeNode<UseFTN> tnChild3= new FlexibleTreeNode<>(new UseFTN());
		tnRoot.add(tnChild3);
		
		String debug= tnRoot.toDebugString();
		System.out.println(debug);
		
		System.out.println("\n\ngetAllTreeNodesAsList:");
		List<FlexibleTreeNode<UseFTN>> lst= tnRoot.getAllTreeNodesAsList(tnRoot);
		for(FlexibleTreeNode<UseFTN> tn: lst) {
			 System.out.println(tn);
		 }
		
		
		
		System.out.println("\n\ngetAllTreeNodesAsListMoreEfficient:");
		List<FlexibleTreeNode<UseFTN>> lst1= tnRoot.getAllTreeNodesAsListMoreEfficient();
		for(FlexibleTreeNode<UseFTN> tn: lst1) {
			 System.out.println(tn);
		 }
		
		
	}

	@Override
	public Image getGuiIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toGuiString() {
		
		return "Bla";
	}
	
}

 
 
 
 */
