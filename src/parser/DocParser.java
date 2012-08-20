package parser;

public abstract class DocParser {
	String fileName;
	
	public DocParser(){
		
	}
	public DocParser(String f){
		fileName = f;
	}
	abstract public String getText();
}
