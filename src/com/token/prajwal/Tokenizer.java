package com.token.prajwal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Tokenizer {
	final static File folder = new File("Prajwal");
	public static List<String> fileNames = new ArrayList<String>();
	public static Map<String, Integer> countMap = new HashMap<String, Integer>();

	public static void main(String[] args) {
		listFilesForFolder(folder);
		fileReader();
		System.out.println("Map:"+countMap);
	}
	
	@SuppressWarnings("resource")
	public static void fileReader() {

		try {
			for (int i = 0; i < fileNames.size(); i++) {

				String tempPath = folder + "\\" + fileNames.get(i);

				
				Scanner readFile = new Scanner(new File(tempPath))
						.useDelimiter("[^a-zA-Z0-9]+");

				while (readFile.hasNext()) {
					String word = readFile.next().toLowerCase();

					if (countMap.containsKey(word)) {
						countMap.put(word, countMap.get(word) + 1);
					} else {
						countMap.put(word, 1);
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				fileNames.add(fileEntry.getName().toString());
			}
		}
	}

}
