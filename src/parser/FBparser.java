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

public class FBparser extends DocParser{
	StringBuffer text;
	public FBparser(){
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
				}
				
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
		DocParser fbp = new FBparser();
		fbp.setDoc("FB.xml");	
		fbp.getDocItem();
	}
}

/*
<DOC>
<DOCNO> FBIS3-1021 </DOCNO>
<HT>    "drafr053_e_94001" </HT>
<HEADER>
<AU>   FBIS-AFR-94-053 </AU>
Document Type:Daily Report 
<DATE1>  17 Mar 1994 </DATE1>
</HEADER>
<F> WEST AFRICA </F>
<F> Benin </F>
<H3> <TI>   Government Conditionally Accepts 2 Union Demands </TI></H3>
<F>  AB1703210094 Cotonou Office de Radiodiffusion-Television du 
Benin Radio in French 0615 GMT 17 Mar 94 </F>
<F> AB1703210094 </F>
<F>  Cotonou Office de Radiodiffusion-Television du 
Benin Radio </F>
<TEXT>
Language: <F> French </F>
Article Type:BFN 
<F> [Statement on cabinet meeting held in Cotonou on 16 March; </F>
read by goverment spokesman Theodore Holo] 
  [Excerpt] [passage omitted] Following the report presented 
by the minister of civil service and administrative reform on 
the ongoing negotiations with labor unions, in particular, 
concerning the two demands submitted by the unions to the 
government delegation, namely: 1. The coupling of promotions 
and an upward review of the cost of a living allowance and; 2. 
An increase in family allowances; the cabinet reaffirmed the 
government's position, which had already been communicated to 
union leaders on 15 March. First, the government intends to meet 
the above-mentioned two union demands within the limits of an 
increase in the wage bill, to the tune of 9,225,328,610 CFA 
francs. Second, the government will proceed with the necessary 
calculations for the breakdown and distribution of this wage 
bill, with the participation of union representatives designated 
by the unions themselves. The cabinet calls on all unions which 
are to participate in the calculations to get in touch with the 
paymaster general's office at the Ministry of Finance first 
thing tomorrow, 17 March. Moreover, the cabinet has decided to 
submit an enabling bill to the National Assembly as soon as 
possible to permit the government to pay immediately to workers 
those financial benefits accruing from the salary readjustment 
measures, and to hold payment of those relating to the payment 
of the actual [words indistinct] pending the passing of the 1994 
budget, in line with the provisions of Article 102 of our 
Constitution. [passage omitted] 
</TEXT>
</DOC>

 */