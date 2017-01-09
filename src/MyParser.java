package avaidya4_ykharwa1_part3;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MyParser {
	public static int documentCount = 1;
	public static ArrayList<String> listofTokensInBody = new ArrayList<String>();
	static ArrayList<Dictionary> dictionary = new ArrayList<Dictionary>();
	public static LinkedHashSet linkedHashSet = new LinkedHashSet();
	public static List l=new ArrayList<String>();
	static ArrayList<Token> tokenList = new ArrayList<Token>();
	public static ArrayList<String> listOfTokensInCurrent = new ArrayList<String>();
	static ArrayList<DocTable> docTableList = new ArrayList<DocTable>();
	static ArrayList<PostingFile> postingFileList = new ArrayList<PostingFile>();
	static int offset = 0;
	static int total = 0;
	
	public static void constructDictionary(){
		Dictionary d;
		int i;
		String term;
		int cf,df;
		int offsetAdder;
		//tokenList = tokenSort(tokenList);
		Collections.sort(tokenList);
		
		//System.out.println("Token list afer sorting is: ");
		
		Token firstToken = tokenList.get(0);
		term = firstToken.name;
		cf = firstToken.cf;
		df = firstToken.df;
		offsetAdder = df;
		d = new Dictionary(term,cf,df,0);
		dictionary.add(d);
		for(i=1;i<tokenList.size();i++){
			term =   tokenList.get(i).name;
			if(term.equals("")){
				System.out.println("present");
			}
			cf   =   tokenList.get(i).cf;
			df   =   tokenList.get(i).df;
			offset = offset + offsetAdder;
			d = new Dictionary(term,cf,df,offset);
			offsetAdder = df;
			dictionary.add(d);
			
		}

	}
	
	public static int countTotal(){
		int count = 0;
		for(Dictionary d:dictionary){
			count = count + d.cf;
			
		}
		count = dictionary.size();
		
		//return count;
		return total;
		
	}
	
	public static void createTotal(File totalFile, int total) {
		
		try{
			
			PrintWriter pw = new PrintWriter(totalFile);
			pw.append(Integer.toString(total));
			pw.close();
		}
		catch(Exception e){
			
		}
	}
	public static void parse(String filePath){
		MyTokenizer tokenizer = new MyTokenizer();
		ArrayList<String> contents = new ArrayList<String>();
		Token newToken;
		
		//System.out.println("File to be processed is: "+filePath);
		try{
			
			File file = new File(filePath);
			Reader fileReader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(fileReader);
			StringBuilder sb = new StringBuilder();
			String line = bufReader.readLine();
			while( line != null){
				sb.append(line).append("\n");
				line = bufReader.readLine();
				
			}
			String xml2String = sb.toString();
			xml2String = xml2String.replaceAll("&", "");
			
			
			
			bufReader.close();
			InputStream is = new ByteArrayInputStream(xml2String.getBytes());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			NodeList bodyList = doc.getElementsByTagName("BODY");
			String headline = null,slug = null, text = null, snippetInput = null;
			String[] linesInBody;
			for(int j=0;j<bodyList.getLength();j++){
				Node bodyNode = bodyList.item(j);
				if(bodyNode.getNodeType() == bodyNode.ELEMENT_NODE){
					Element bodyElement = (Element)bodyNode;
					try{
					slug = bodyElement.getElementsByTagName("SLUG").item(0).getTextContent();
					}catch(Exception e){
						slug = null;
					}
					try{
						headline = bodyElement.getElementsByTagName("HEADLINE").item(0).getTextContent();
					}catch(Exception e){
						headline = null;
					}
					try{
						text = bodyElement.getElementsByTagName("TEXT").item(0).getTextContent();
					}catch(Exception e){
						text = null;
					}
				}
			}
			String contentInBody = slug+headline+text; 
			snippetInput = text;				//snippetInput contains the entire content in body without tokenization.
			MyTokenizer.removeStopWords(contentInBody);
			MyTokenizer.snippetGenerator(snippetInput);
			
			Iterator<String> iterator = MyTokenizer.snippetList.iterator();
			String snippet = "";
			while(iterator.hasNext()){
				String str = iterator.next();
				snippet += str+" ";
				}
			//System.out.println("Snippet of document: "+filePath+"is: "+snippet);		//Snippet Generated
			
			xml2String = xml2String.replaceAll("<[^>]+>", "");
			String input = xml2String;
			String[] inputArray = MyTokenizer.removeStopWordsArrayVersion(input);
			total = total + inputArray.length;
			DocTable d = new DocTable(documentCount, headline, inputArray.length, snippet, filePath);
			//System.out.println("Input is: "+input);
			listOfTokensInCurrent.clear();
			listOfTokensInCurrent = tokenizer.performIndexing(input);
			
			listofTokensInBody.clear();
			listofTokensInBody = tokenizer.performIndexing(snippetInput);				//listofTokensInBody contains tokenized list of contents in body.
			
			documentCount++;
			docTableList.add(d);
			Collections.sort(listOfTokensInCurrent);									//Obtained a sorted raw list of tokens
			String temp;
			Iterator<String> tokenIterator = listOfTokensInCurrent.iterator();
			while(tokenIterator.hasNext()){
				temp = tokenIterator.next();
				if(temp.length()==1||temp.equals(null)||temp.equals(" ")||temp.equals(" 	")){
					
					tokenIterator.remove();
				}
				
			}
			
			boolean flag = false;
			int index = 0;
			for(int i=0;i<listOfTokensInCurrent.size();i++){			//listOfTokensInCurrent contains the tokenized list of terms.
				
				String token = listOfTokensInCurrent.get(i);
				//System.out.println("Token is: "+token);
				if(tokenList.size()==0){
					newToken = new Token(token, 1, 1,file.getName());
					tokenList.add(newToken);
					
					
				}else{
						flag = false;
						for(int j=0;j<tokenList.size();j++){
						if(token.equals(tokenList.get(j).name)){
							//System.out.println("Token:"+token+"present in the list");
							index = j;
							flag = true;
						}
						
					}
						
						if(flag==true){
							
							tokenList.get(index).addDocument(file.getName());
							
							
						}else{
							
							newToken = new Token(token,1,1,file.getName());
							
							tokenList.add(newToken);
						}
					
				}//else
	
					
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
	}
	
	
	public static void displaydocTable(){
		//System.out.println("Displaying Doc Table "+docTableList.size());
		
		for(DocTable docTableEntry: docTableList){
			
			System.out.println("Doc Number is: "+docTableEntry.docId);
			System.out.println("Headline is: "+docTableEntry.Headline);
			System.out.println("Number of tokens is: "+docTableEntry.numberOfTokens);
			System.out.println("Snippet is: "+docTableEntry.snippet);
			System.out.println("File path is: "+docTableEntry.filePath);

		}
		
	}

	public static void createDocTable(File docsTableFile){
		int i;
		try{
			//System.out.println("Creating file for docTable!!"+docsTableFile);
			PrintWriter pw = new PrintWriter(docsTableFile);
			for(DocTable d:docTableList){
				d.Headline = d.Headline.replaceAll("\n", "");
				d.Headline = d.Headline.replaceAll("\t", "");
				d.Headline = d.Headline.replaceAll(",", "");
				d.snippet = d.snippet.replaceAll("\n", "");
				d.snippet = d.snippet.replaceAll("\t", "");
				d.snippet = d.snippet.replaceAll(",", "");
				
				 
				
				pw.append(d.filePath+","+d.numberOfTokens+","+d.Headline+","+d.snippet+"\n");
				
			}
			pw.close();
			
			
		}catch(Exception e){
			
		}
	}
	
	public static void displayDictionary(){
		//System.out.println("Displaying dictionary");
		//System.out.println();
		int i=1;
		for(Dictionary d:dictionary){
			//System.out.println("i is: "+i);
			
			System.out.println("Term: "+d.Term);
			System.out.println("CF: "+d.cf);
			System.out.println("DF: "+d.df);
			System.out.println("Offset: "+d.offset);
			i++;
		}
		
	}
	
	public static void constructPostingFile(){
		PostingFile pf;
		HashMap<String, Integer> posting;
		String docName;
		int termFrequency;
		Collections.sort(tokenList);
		for(Token token:tokenList){
			//System.out.println("name of the token is: "+token.name);
			//System.out.println("Document list for the token is: ");
			posting = token.posting;
			for(String key:posting.keySet()){
				 docName = key;
				 //System.out.println("Doc name is: "+docName);
				 termFrequency = posting.get(docName);
				 pf = new PostingFile(key, termFrequency);
				 postingFileList.add(pf);

			}

		}
		
	}
	
	public static void displayPostingFile(){
		//System.out.println("Displaying posting file");
		for(PostingFile p:postingFileList){
			System.out.println("DocName is: "+p.docName);
			System.out.println("Term Frequency is: "+p.tf);
			
		}
	}
	
	public static void createDictionaryTable(File dictionaryFile){
		int i;
		try{
			//System.out.println("Creating file for dictionary!!"+dictionaryFile);
			PrintWriter pw = new PrintWriter(dictionaryFile);
			//dictionary = (ArrayList<Dictionary>) dictionary.subList(1, dictionary.size());
			for(Dictionary d:dictionary){
				//System.out.println(d.docName+","+d.Headline+","+d.numberOfTokens+","+d.snippet);
				if(d.Term.contains(".")){
					
					d.Term = d.Term.replaceAll("\\.","");
				}
				pw.append(d.Term+","+d.cf+","+d.df+","+d.offset+"\n");
				
			}
			pw.close();
		}
		catch(Exception e){
			
		}
		
	}
	
	public static void createPostingTable(File postingFile){
		int i;
		try{
			//System.out.println("Creating file for dictionary!!"+dictionaryFile);
			PrintWriter pw = new PrintWriter(postingFile);
			for(PostingFile d:postingFileList){
				//System.out.println(d.docName+","+d.Headline+","+d.numberOfTokens+","+d.snippet);
				pw.append(d.docName+","+d.tf+"\n");
				
			}
			pw.close();
		}
		catch(Exception e){
			
		}
		
	}

}
