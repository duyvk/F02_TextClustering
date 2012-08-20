package items;
	
// TODO: Auto-generated Javadoc
/**
 * The Class DocItems.
 */
public class DocItems {
	
	/** The doc id. */
	private String docID;
	
	/** The cluster id. */
	private String clusterID = "";
	
	/** The doc title. */
	private String docTitle;
	
	/** The doc text. */
	private String docText;
	
	public DocItems(){
		docID = clusterID = docTitle = docText = "";
	}
	
	public DocItems(String dID, String cID, String dTi, String dTe){
		docID = dID;
		clusterID = cID;
		docTitle = dTi;
		docText = dTe;
	}
	
	public String getDocID(){
		return docID;
	}
	
	public String getClusterID(){
		return clusterID;
	}
	
	public String getDocTitle(){
		return docTitle;
	}
	
	public String getDocText(){
		return docText;
	}
	
	public void setDocID(String dID){
		docID = dID;
	}
	
	public void setClusterID(String cID){
		clusterID = cID;
	}
	
	public void setDocTitle(String dTi){
		docTitle = dTi;
	}
	
	public void setDocText(String dTe){
		docText = dTe;
	}
	
}
