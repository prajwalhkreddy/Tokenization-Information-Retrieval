package com.token.prajwal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
	final static File folder = new File("Cranfield");
	public static List<String> fileNames = new ArrayList<String>();
	
	public static void main(String[] args) {
		
		listFilesForFolder(folder);

	}

	public void fileReader(String filePath){
		
	}
	
	public static void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}
	
	
}
