package com.mycompany.pmt8;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import turban.utils.*;

@SuppressWarnings("serial")
public class DomParser extends JFrame{

    String _strXmlFileName;

    FlexibleTreeNode<MyGuifiable> _tnRoot;
    JTree _jtree;
    DefaultTreeModel _treeModel;

    boolean _blnRootVisible = true;

    public DomParser(String strXmlFileName) throws UserMsgException{
        super("DOM-Baum von " + strXmlFileName);
        try{
            _strXmlFileName = strXmlFileName;

            domParser_buildContentPane();
            parseXMLAndFillJTreeWithNodes();

            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //this.setSize(300,200);
            this.pack();
            this.setLocation(50, 50);

            this.setVisible(true);
        }catch(UserMsgException th){
            ErrorHandler.logException(th, true, DomParser.class,
                                      "Exception in constructor");
        }
    }

    public static Document parseXMLtoDOM(String strFileName) throws
            UserMsgException{
        try{
            DocumentBuilderFactory domFact = DocumentBuilderFactory
                                   .newInstance();
            /* M�gl. Parser-Konfigurationen:
			  domFact.setValidating(true); domFact.setNameSpaceAware(true);
			  domFact.setIgnoringComments(true);domFact.setIgnoringElementContentWhitespace(true);
             */
            DocumentBuilder domBuilder = domFact.newDocumentBuilder();
            Document domDoc = domBuilder.parse(strFileName);
            return domDoc;
        }catch(SAXException | ParserConfigurationException | java.io.IOException sxe){ //Fehler bei Analyse des XML
            ErrorHandler.logException(sxe, false, DomParser.class,
                                      "Exception in parseXMLtoDOM");
        }
        //Fehler bei Parser Config
        //Fehler bei Parser Config
        throw new UserMsgException("Error parsing file {0}",
                                   "Error parsing file {0}.\nPlease, consult logfile for detailed error message.",
                                   strFileName); //Do in case of Exception!
    }

    public static void main(String[] args){
        try{
            //String strFileName=".\\DomText.xml";
            if(args.length < 1){
                //Schoene Beispiele wofuer man UserMsgException einfach verwenden kann:
                throw new UserMsgException("Error: Wrong arguments",
                                           "Error: File name not provided!\n" + "Please, call application with argument:\n" + "   java {0} <FileName>",
                                           DomParser.class);
            }

            String strFileName = args[0];
            if((new java.io.File(strFileName)).exists() == false){
                //Schoene Beispiele wofuer man UserMsgException einfach verwenden kann:
                throw new UserMsgException("XML-File does not exist",
                                           "Specified XML-File [{0}] not found!",
                                           strFileName);
            }

            DomParser domParser = new DomParser(strFileName);

        }catch(UserMsgException th){
            ErrorHandler.logException(th, true, DomParser.class,
                                      "Exception in main");
        }
    }

    private static void traverseXmlNodesForOutputOnCommandline_doChildren(
            Node nd){
        NodeList nl = nd.getChildNodes();
        for(int i = 0; i < nl.getLength(); i ++){
            traverseXmlNodesForOutputOnCommandline(nl.item(i));
        }
    }

    private static void traverseXmlNodesForOutputOnCommandline(Node nd){
        switch(nd.getNodeType()){
            case Node.DOCUMENT_NODE -> {
                Document doc = (Document) nd;
                // Work on Document
                System.out.println(nd.getNodeName());
                traverseXmlNodesForOutputOnCommandline_doChildren(nd);
            }
            case Node.ELEMENT_NODE -> {
                Element elem = (Element) nd;
                System.out.println(nd.getNodeName() + ":" + nd.getNodeValue());
                // Analyze Attributes
                traverseXmlNodesForOutputOnCommandline_doChildren(nd);
            }
            case Node.TEXT_NODE -> {
                Text txt = (Text) nd;
                System.out.println(nd.getNodeName() + ":" + nd.getNodeValue());
                // Work on Text
            }
            default -> //Further Node Types - we just print them out, if present:
                System.out.println(nd.getNodeName() + ":" + nd.getNodeValue());
        }
    }

    private void domParser_buildContentPane(){
        _tnRoot = new FlexibleTreeNode<>(new MyGuifiable(
        "Content of XML-Document " + _strXmlFileName + ":"));

        _treeModel = new DefaultTreeModel(_tnRoot);
        _jtree = new JTree(_treeModel);

        //_jtree.setCellRenderer(new MyTreeCellRenderer());
        _jtree.setRootVisible(_blnRootVisible);

        JScrollPane treeView = new JScrollPane(_jtree);
        treeView.setPreferredSize(new Dimension(450, 250));
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(treeView, BorderLayout.CENTER);

    }

    private void parseXMLAndFillJTreeWithNodes() throws UserMsgException{
        Document doc = parseXMLtoDOM(_strXmlFileName);

        traverseXmlNodesToFillInJTree(doc, _tnRoot);

        _treeModel.reload();
    }

    private void traverseXmlNodesToFillInJTree_doChildren(Node nd,
                                                          FlexibleTreeNode<MyGuifiable> tnThisNode){
        NodeList nl = nd.getChildNodes();
        for(int i = 0; i < nl.getLength(); i ++){
            traverseXmlNodesToFillInJTree(nl.item(i), tnThisNode);
        }
    }

    private String traverseXmlNodesToFillInJTree_doAttributes(Element elem){
        if(elem.hasAttributes() == false){
            return "";
        }

        NamedNodeMap nmp = elem.getAttributes();
        String strToRet = "";
        for(int i = 0; i < nmp.getLength(); i ++){
            Attr attrib = (Attr) nmp.item(i);
            if(strToRet.length() > 0){
                strToRet += ", "; // strToRet>0 add a comma because we already have added a value before!
            }
            strToRet += attrib.getNodeName() + ": " + attrib.getNodeValue();
        }
        return "  (Attributes(" + strToRet + "))";
    }

    private void traverseXmlNodesToFillInJTree(Node nd,
                                               FlexibleTreeNode<MyGuifiable> tnParent){
        String strToShowInGui;

        switch(nd.getNodeType()){
            case Node.DOCUMENT_NODE -> //Document doc=(Document)nd;
                strToShowInGui = nd.getNodeName();
            case Node.ELEMENT_NODE -> {
                Element elem = (Element) nd;
                strToShowInGui = "#element: " + nd.getNodeName(); //+":"+nd.getNodeValue();
                strToShowInGui += traverseXmlNodesToFillInJTree_doAttributes(
                elem);
            }
            case Node.TEXT_NODE -> //Text txt=(Text)nd;
                strToShowInGui = nd.getNodeName() + ": " + nd.getNodeValue()
                                 .trim();
            // Work on Text
            //Do trim to remove beginning and ending white spaces!
            default -> {
                //All Other Node-Types
                String strValue = nd.getNodeValue();
                strToShowInGui = nd.getNodeName() + ((strValue != null) ? ": " + strValue
                                                                          .trim() : "");
            }
        }

        FlexibleTreeNode<MyGuifiable> tnThisNode =
                                      new FlexibleTreeNode<>(
                                              new MyGuifiable(strToShowInGui));
        tnParent.add(tnThisNode);
        traverseXmlNodesToFillInJTree_doChildren(nd, tnThisNode); // We can also call this at the end because this method terminates anyway, if the node has no children!
    }

    public class MyGuifiable implements IGuifiable{

        String _strGui;

        public MyGuifiable(String strGui){
            _strGui = strGui;
        }

        public void setGuiString(String str){
            _strGui = str;
        }

        @Override
        public String toGuiString(){
            return _strGui;
        }

        /**
         *
         * @return
         */
        @Override
        public Image getGuiIcon(){
            return null;
        }
    }

}
