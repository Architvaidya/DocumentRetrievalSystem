package avaidya4_ykharwa1_part3;


import java.util.ArrayList;
import java.util.Iterator;

public class MyTokenizer {
	public static ArrayList<String> snippetList = new ArrayList<String>();
	public static String[] removeStopWordsArrayVersion(String inputString){
		inputString.toLowerCase();
		inputString = inputString.replaceAll(" and | an | with | by | from | of | the | in | a ", " ");
		String[] str = inputString.split(" ");
		return str;
	}
	public static ArrayList<String> removeStopWords(String inputString){
		ArrayList<String> al = new ArrayList<String>();
		inputString.toLowerCase();
		inputString = inputString.replaceAll(" and | an | with | by | from | of | the | in | a ", " ");
		
		for (String str: inputString.split(" ")){
			 al.add(str);
		 }
		return al;
		
		
	}
	
	public static int computeDocumentLength(String body){
		String[] bodyContent = body.split(" ");
		MyParser.total = MyParser.total + bodyContent.length;
		return bodyContent.length;
		
		
	}
	
	public static void snippetGenerator(String inputString){
		ArrayList<String> strList = new ArrayList<String>();
		int i = 0,ptr = 0;
		boolean endOfSentenceFlag = false;
		snippetList.removeAll(snippetList);
		String snippet = null;
		inputString = inputString.replaceAll("\n", "").replace("\t", " ");
		String[] str = inputString.split(" ");
		
		
		
		for(String s:str){
			if(s.equals("\t") || s.equals(" ")||s.equals("")){
				continue;
			}else{
				strList.add(s);
			}
		}
		//inputString.replaceAll
		int count = 1;
		 
		while(i<strList.size() && count < 6){
			//System.out.println("Coujt is: "+count);
			snippetList.add(strList.get(i));
			i++;
			count++;
		}
		
		
		
		
	}
	
	public ArrayList<String> performIndexing(String s1){
		ArrayList <String> al = new ArrayList<String>();
		int i;
		//System.out.println("myindexer"+s1);
		String s2 = new String();
		s2 = s1.toLowerCase();
		s2 = s2.replaceAll("	","");
		s2 = s2.replaceAll(",|\\.", "");
		
		s2 = s2.replaceAll("[^\\w\\s]", " ");
		s2 = s2.replaceAll("_"," ");
		//s2 = s2.replaceAll(",|[|&|:|;|(|)|#|$|%|*|+|=|@|^|{|}|~|`|]|'", " ");
		s2 = s2.replaceAll(" ", "  ");
		
		s2 = s2.replaceAll("\n", " ");
		s2 = s2.replaceAll("\n", " ");
		s2 = s2.replaceAll("\n", " ");
		s2 = s2.replaceAll("     ", " ");
		s2 = s2.replaceAll("\\. ", " ");
		s2 = s2.replaceAll(", ", " ");
		s2 = s2.replaceAll("-", " " );
		s2 = s2.replaceAll("/", " " );
		s2 = s2.replaceAll(": |; ", " ");
		s2 = s2.replaceAll("'s|s'| '", " ");
		s2 = s2.replaceAll(" [a-z] "," " );
		s2 = s2.replaceAll("\\[","");
		s2 = s2.replaceAll("\\]","");
		s2 = s2.replaceAll(" and | an | with | by | from | of | the | in | a ", " ");  
		s2 = s2.replaceAll("  ", " ");
		s2 = s2.replaceAll("  ", " ");
		s2 = s2.replaceAll("  ", " ");
		

		 for (String str: s2.split(" ")){
			 al.add(str);
		 }
		 
		 //////System.out.println("Contents of array list before are: "+al);
		 //////System.out.println("Size of array list before is: "+al.size());
		 
		 int size = al.size();
		 String temp = new String();
		 
		 for(i=0;i<size;i++){
			 temp = al.get(i);
			 
			 temp = modifyString(temp);
			 al.remove(i);
			 al.add(i, temp);
		}
		 
		 size = al.size();
		 for(i=0;i<size;i++){
			 temp = al.get(i);
			 
			 temp = modifyString_2(temp);
			 temp = modifyString_3(temp);
			 temp = modifyString_4(temp);
			 
			 /*temp = removeStopWords(temp);*/
			 al.remove(i);
			 al.add(i, temp);
			 
			 
		 }
		 for(i=0;i<al.size();i++){
			 if(al.get(i).length() == 0){
				 al.remove(i);
			 }
		 }
		
		 Iterator<String> iterator = al.iterator();
		 while(iterator.hasNext()){
			 String str = iterator.next();
			 
			 if(str.equals("")){
				 iterator.remove();
			 }
			 
		 }

		return al;
	
	}
	

	
public static String modifyString(String temp){
		
		
		if(temp.contains("(")){
			//////System.out.println("String contains: '('");
			temp = temp.replace("(", "");
	
		}
		
		if(temp.contains(")")){
			//////System.out.println("String contains: ')'");
			temp = temp.replace(")", "");
		}
		
		
		if(temp.contains(",")){
			if(temp.endsWith(",")){
				temp = temp.replaceAll(",","");
			}
	
		}
		
		if(temp.startsWith("\"")||temp.endsWith("\"")){
			//////System.out.println("String contains: double quotes");
			temp = temp.replace("\"", "");
		}
		
		if(temp.contains(".")){
			if(temp.endsWith(".")){
				temp = temp.replace(".", "");
			}
		
		}
		
		if(temp.contains("?")){
			if(temp.endsWith("?")){
				temp = temp.replaceAll("\\?","");
			}
	
		}
		
		if(temp.contains("!")){
			if(temp.endsWith("!")){
				temp = temp.replaceAll("!","");
			}
		
		}
		
		if(temp.contains(":")){
			if(temp.endsWith(":")){
				temp = temp.replaceAll(":","");
			}
		}
		
		if(temp.contains("[")){
			temp = temp.replaceAll("[","");
		}
		
		if(temp.contains("]")){
			temp = temp.replaceAll("]","");
		}
		
		return temp;
	}//modify string
	
public static String modifyString_2(String temp){
		
		if(temp.endsWith("aies")|temp.endsWith("eies")){
			
			return temp;
		}
		else if(temp.endsWith("ies")){
			temp = temp.replaceAll("ies", "y");
			return temp;
		}
		
		
		return temp;
	}//modifyString_2

public static String modifyString_3(String temp){
	
	String s = new String();
	if(temp.endsWith("aes")|temp.endsWith("ees")|temp.endsWith("oes")){
		
		return temp;
	}
	else if(temp.endsWith("es")){
		int i = temp.lastIndexOf('e');
		s = temp.substring(0, i);
		
		s=s.concat("e");
		////System.out.println("Detected es, es->e"+s);
		return s;
	}
	return temp;
}

public static String modifyString_4(String temp){
	////System.out.println("modifyString:: temp is: "+temp);
	String s = new String();
	if(temp.endsWith("ss")|temp.endsWith("us")){
		
		return temp;
	}
	else if(temp.endsWith("s")){
		int i = temp.lastIndexOf('s');
		s = temp.substring(0, i);
		s.concat("");
		if(s.length() == 1){
		s = s.replaceAll("[a-z]","");
		}
		
		return s;
}
	return temp;
}

}

