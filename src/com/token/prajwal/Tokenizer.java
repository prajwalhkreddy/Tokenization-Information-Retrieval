package com.token.prajwal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Tokenizer {
	final static File folder = new File("Cranfield");
	public static List<String> fileNames = new ArrayList<String>();
	public static Map<String, Integer> countMap = new HashMap<String, Integer>();
	public static Map<String, Integer> sortedCountMap = new TreeMap<String, Integer>();
	static int totalNumOfTokens = 0;
	static int numOfTokenPerDoc = 0;
	static double avgNumOfTokenPerDoc = 0.0;
	static int countWithOne = 0;
	static int totalCount = 0;

	public static void main(String[] args) {
		listFilesForFolder(folder);
		fileReader();
		calculateCount(countMap);
		System.out.println("Number of tokens in Cranfield Collection: " + totalCount);
		System.out.println("Number of unique Words: " + countMap.size());
		System.out.println("Number of words occurring only once: "+ countWithOne);
		System.out.println("Average Number of Word Tokens per document: "+ totalCount / fileNames.size()+"\n");
		System.out.println("30 most frequent words in the Cranfield text collection");
		sortedCountMap = sortCountmap(countMap);
		printBasedOnFrequency(sortedCountMap);
	}

	@SuppressWarnings("resource")
	public static void fileReader() {

		try {
			for (int i = 0; i < fileNames.size(); i++) {

				String tempPath = folder + "\\" + fileNames.get(i);

				Scanner readFile = new Scanner(new File(tempPath));

				while (readFile.hasNextLine()) {

					String curLine = readFile.nextLine();
					if (!(curLine.contains("<") && curLine.contains(">"))) {
						curLine = curLine.replaceAll("[-]", " ");
						StringTokenizer stringTokenizer = new StringTokenizer(
								curLine);

						while (stringTokenizer.hasMoreTokens()) {
							numOfTokenPerDoc++;
							String word = stringTokenizer.nextToken()
									.replaceAll("[^a-zA-Z0-9]", "")
									.toLowerCase();

							if (word.equals(""))
								continue;
							else {
								if (countMap.containsKey(word)) {
									countMap.put(word, countMap.get(word) + 1);
								} else {

									countMap.put(word, 1);

								}
							}

						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void calculateCount(Map<String,Integer> tempMap){
		for (Entry<String, Integer> entry : tempMap.entrySet()) {
			totalCount += entry.getValue();
			if (entry.getValue() == 1) {
				countWithOne++;

			}
		}
	}
	public static void printBasedOnFrequency(Map<String, Integer> printMap) {

		List<Map.Entry<String, Integer>> frequentThirty = new ArrayList<Map.Entry<String, Integer>>(30);
		Iterator<Entry<String, Integer>> iterator = printMap.entrySet().iterator();
		for (int i = 0; iterator.hasNext() && i < 30; i++) {
			System.out.println(i+1+". "+iterator.next());
		}
	}

	// The below method recieved the coutMap and store it based on values and
	// stores it in a TreeMap.
	// Reference :
	// http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
	private static TreeMap<String, Integer> sortCountmap(
			Map<String, Integer> tempMap) {
		class My_Comapartor implements Comparator<String> {

			Map<String, Integer> map;

			public My_Comapartor(Map<String, Integer> base) {
				this.map = base;
			}

			public int compare(String firstvalue, String secondvalue) {

				if (map.get(firstvalue) >= map.get(secondvalue)) {
					return -1;
				} else {
					return 1;
				}
			}
		}

		My_Comapartor vc = new My_Comapartor(tempMap);
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(vc);
		sortedMap.putAll(tempMap);
		return sortedMap;

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
