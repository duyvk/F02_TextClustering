package parser;

import items.DocItems;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FRparser extends DocParser{
	StringBuffer text;
	public FRparser(){
		super();
		text =new StringBuffer();
	}
	@Override
	public DocItems getDocItem() {
		String docID, docTitle, docText;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
				db = dbf.newDocumentBuilder();
			
			File file  = new File (fileName);
			if(file.exists()){
				Document doc = db.parse(file);
				Element docEle = doc.getDocumentElement();
			
				//Print root element of the document
				//System.out.println("Root element of the document: "
					//	+ docEle.getNodeName());
				
				
				NodeList docIDList = docEle.getElementsByTagName("DOCNO");
				if(docIDList.getLength()!=0){
					//docIDNAME = docIDList.item(0).getNodeName();
					docID = docIDList.item(0).getChildNodes().item(0).getNodeValue();
					System.out.println(docID);
				}
				
				NodeList docTitleList = docEle.getElementsByTagName("TI");
				if(docTitleList.getLength()!=0){
					//docIDNAME = docIDList.item(0).getNodeName();
					docTitle = docTitleList.item(0).getChildNodes().item(0).getNodeValue();
					System.out.println(docTitle);
				}else
					docTitle = "N/A";
				
				NodeList docTextList = docEle.getElementsByTagName("TEXT");
				//System.out.println(docTextList.item(0).getChildNodes().getLength()); //7
				NodeList textChildList = docTextList.item(0).getChildNodes();
				int textEleLength = docTextList.item(0).getChildNodes().getLength();
				//System.out.println(docTextList.item(0).getNodeName()); //TEXT
					for(int i = 0 ; i < textEleLength; i++){
						Node tmpNode = textChildList.item(i);
						//System.out.println(tmpNode.getNodeType());
						getText(tmpNode);
					}			
				docText = text.toString();
				System.out.println(docText);
			}else{
					System.err.println("no such file");
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	 public void getText(Node node) {
    	 if(node.getNodeType() == Node.TEXT_NODE){
    		 text.append(node.getNodeValue());
    	 }else if (node.getNodeType() == Node.ELEMENT_NODE){
    		 NodeList childNodes = node.getChildNodes();
    		 int noChildren = childNodes.getLength();
    		 Node n = null;
    		 short type = 0;
    		 for (int i = 0; i < noChildren; i++) {
    			 n = childNodes.item(i);
    			 getText(n);
    		 }
    	 }
	 }
	public static void main (String []args){
		DocParser frp = new FRparser();
		frp.setDoc("FR.xml");	
		frp.getDocItem();
	}
}