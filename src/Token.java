package avaidya4_ykharwa1_part3;

import java.util.ArrayList;
import java.util.HashMap;

public class Token implements Comparable<Token>{
	String name;
	ArrayList<String> documentList;
	int cf;
	int df;
	HashMap <String, Integer> posting;
	

	Token(String name, int cf, int df, String docName){
		this.name = name;
		this.cf = cf;
		this.df = df;
		documentList = new ArrayList<String>();
		documentList.add(docName);
		posting = new HashMap<String,Integer>();
		posting.put(docName, 1);
		
	}
	
	public Token(Token t) {
		this.name = t.name;
		this.cf = t.cf;
		this.df = t.df;
		this.documentList = t.documentList;
		this.posting = t.posting;
		
	}
	
	public int compareTo(Token t2) {
		/*String s1 = t1.name;
		String s2 = t2.name;
		
		int flag = s1.compareTo(s2);
		//System.out.println("Flag is: "+flag);
		*/return name.compareTo(t2.name);
	}

	void addDocument(String name){
		boolean flag = false;
		
		for(String docName:documentList ){
			if(docName.equals(name)){
				flag = true;
			}
		}
		
		if(flag){
			/*for(Map.Entry<String, Integer> entry:posting.entrySet()){
				String key = entry.getKey();
				System.out.println("Key is: "+key);
			}*/
			int tf = posting.get(name);
			tf++;
			posting.put(name, tf);
			
			cf++;
			
		}else{
			documentList.add(name);
			cf++;
			//System.out.println("Incrementing df for token: "+this.name);
			this.df = this.df + 1;
			//System.out.println("New df is: "+this.df);
			posting.put(name, 1);
		}
			
	}
}


