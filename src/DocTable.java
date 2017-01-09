package avaidya4_ykharwa1_part3;


public class DocTable {
	
	int docId;
	String Headline;
	int numberOfTokens;
	String snippet;
	String filePath;
	
	public DocTable(int docId, String headline, int numberOfTokens, String snippet, String filePath) {
		
		this.filePath = filePath;
		this.numberOfTokens = numberOfTokens;
		this.Headline = headline;
		this.docId = docId;
		Headline = headline;
		
		this.snippet = snippet;
		
	}
	

}



