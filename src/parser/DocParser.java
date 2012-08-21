package parser;

import items.DocItems;

public abstract class DocParser {
	String fileName;
	
	public DocParser(){
		
	}
	public DocParser(String f){
		fileName = f;
	}
	
	public void setDoc (String f){
		fileName = f;
	}
	
	public String getDoc (){
		return fileName;
	}
	
	abstract public DocItems getDocItem();
}
