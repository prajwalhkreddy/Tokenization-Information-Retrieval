package com.token.prajwal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Tokenizer {
	
	public static List<String> fileNames = new ArrayList<String>();
	public static Map<String, Integer> countMap = new HashMap<String, Integer>();
	public static Map<String, Integer> sortedCountMap = new TreeMap<String, Integer>();
	public static Map<String, Integer> stemMap = new HashMap<String, Integer>();
	public static Map<String, Integer> sortedStemMap = new TreeMap<String, Integer>();
	public static File folder;
	static int totalNumOfTokens = 0;
	static int numOfTokenPerDoc = 0;
	static double avgNumOfTokenPerDoc = 0.0;
	static int countWithOne = 0;
	static int totalCount = 0;
	static long startTime=0;
	static long endTime=0;

	public static void main(String[] args) {
		
		if(args.length==0){
			System.out.println("Please Enter the Path of Cranfield Dataset");
			System.exit(0);
		}
		folder = new File(args[0]);
		long startTime = Calendar.getInstance().getTimeInMillis();
		listFilesForFolder(folder);
		fileReader();
		long endTime = Calendar.getInstance().getTimeInMillis();
		calculateCount(countMap);
		System.out.println("************Before Stemming********************");
		System.out.println("Number of tokens in Cranfield Collection: "
				+ totalCount);
		System.out.println("Number of unique Words: " + countMap.size());
		System.out.println("Number of words occurring only once: "
				+ countWithOne);
		System.out.println("Average Number of Word Tokens per document: "
				+ totalCount / fileNames.size() + "\n");
		System.out
				.println("30 most frequent words in the Cranfield text collection");
		sortedCountMap = sortCountmap(countMap);
		printBasedOnFrequency(sortedCountMap);
		
		System.out.println("\n************After Stemming********************");
		System.out.println("Number of distinct Stems: " + stemMap.size());
		calculateCount(stemMap);
		System.out.println("Number of Stems occurring only once: "+ countWithOne);
		System.out.println("Average Number of Word Stems per document: "
				+ totalCount / fileNames.size() + "\n");
		System.out
				.println("30 most frequent stems in the Cranfield text collection");
		sortedStemMap = sortCountmap(stemMap);
		printBasedOnFrequency(sortedStemMap);
		
		System.out.println("\nTime taken to read files: "+(endTime-startTime)+" ms");
	}

	
	/*Traverse the Hashmap containing filename and read each file*/
	@SuppressWarnings("resource")
	public static void fileReader() {

		try {
			for (int i = 0; i < fileNames.size(); i++) {

				String tempPath = folder + "//" + fileNames.get(i);

				Scanner readFile = new Scanner(new File(tempPath));

				while (readFile.hasNextLine()) {

					String curLine = readFile.nextLine();
					/*If the line contains SGML tags ignore the line*/
					if (!(curLine.contains("<") && curLine.contains(">"))) {
						curLine = curLine.replaceAll("[-]", " ");
						StringTokenizer stringTokenizer = new StringTokenizer(
								curLine);

						while (stringTokenizer.hasMoreTokens()) {
							String word = stringTokenizer.nextToken()
									.replaceAll("[^a-zA-Z0-9]", "")
									.toLowerCase();
							String tempWord=word;
							if (word.equals(""))
								continue;
							else {
								if (countMap.containsKey(word)) {
									countMap.put(word, countMap.get(word) + 1);
								} else {
									countMap.put(word, 1);
								}
								
								/*Stemming Process*/
								String stemmedWord = null;
			      			    Stemmer myStemmer = new Stemmer();
			      			    myStemmer.add(tempWord.toCharArray(),tempWord.length());
			      			    myStemmer.stem();
			      			    stemmedWord=myStemmer.toString();
			      			  if (stemMap.containsKey(stemmedWord)) {
									stemMap.put(stemmedWord, stemMap.get(stemmedWord) + 1);
								} else {

									stemMap.put(stemmedWord, 1);
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

	
	public static void calculateCount(Map<String, Integer> tempMap) {
		totalCount = 0;
		countWithOne = 0;
		for (Entry<String, Integer> entry : tempMap.entrySet()) {
			totalCount += entry.getValue();
			if (entry.getValue() == 1) {
				countWithOne++;

			}
		}
	}

	public static void printBasedOnFrequency(Map<String, Integer> printMap) {

		Iterator<Entry<String, Integer>> iterator = printMap.entrySet()
				.iterator();
		for (int i = 0; iterator.hasNext() && i < 30; i++) {
			System.out.println(i + 1 + ". " + iterator.next());
		}
	}

	// The below method receives the countMap/stemMap and sorts it based on values and
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

	//Traverse the Directory Structure and add all fileNames to a List
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
