package avaidya4_ykharwa1_part3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RetrieveFiles {
	
	public static ArrayList<String> filePathList = new ArrayList<String>();

	public static void main(String[] args) {
		int total;
		File docsTableFile = new File("DocsTable.csv");
		if(!docsTableFile.exists()){
			try {
				docsTableFile.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		File dictionaryFile = new File("Dictionary.csv");
		if(!dictionaryFile.exists()){
			try {
				dictionaryFile.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		File postingsFile = new File("Postings.csv");
		if(!postingsFile.exists()){
			try {
				postingsFile.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		File totalFile = new File("Total.csv");
		if(!totalFile.exists()){
			try {
				totalFile.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		int i=0,pointer=0;
		File currentDir = new File(args[0]); // current directory
		displayDirectoryContents(currentDir);
		//System.out.println("Size of list is: "+filePathList.size());
		for(String str:filePathList){
			if(str.endsWith("summaryFile.txt")){
				pointer = i;
				filePathList.remove(pointer);
				
			}
			str.replace("\\\\", File.separator);
			i++;
			//System.out.println("File is: "+str);
		}
		
		
		for(String filePath:filePathList){
			MyParser.parse(filePath);
		}
		
		//MyParser.displaydocTable();
		MyParser.createDocTable(docsTableFile);
		MyParser.constructDictionary();
		//MyParser.displayDictionary();
		MyParser.createDictionaryTable(dictionaryFile);
		MyParser.constructPostingFile();
		//MyParser.displayPostingFile();
		MyParser.createPostingTable(postingsFile);
		total = MyParser.countTotal();
		MyParser.createTotal(totalFile,total);
	}
	public static void displayDirectoryContents(File dir) {
		File[] files = dir.listFiles();
		
		//System.out.println("Lenght of files array is: "+files.length);
		for (File file : files) {
			if (file.isDirectory()) {
				//System.out.println("directory:" + file.getPath());
				
				displayDirectoryContents(file);
			} else {
				filePathList.add(file.getAbsolutePath());
				//System.out.println("     file:" + file.getPath());
			}
		}

	}
	
	
	

}
