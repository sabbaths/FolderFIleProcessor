package com.example.processor.dao;

public abstract class AbstractFile {
	private  String name;
	private String path;
	private String extension;
	private int numberOfLetters;
	private int numberOfSpecial;
	private int numberOfNumbers;
	private int numberOfWords;
	
	public AbstractFile(String filePath) {
		this.path = filePath;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public int getNumLetters() {
		return this.numberOfLetters;
	}

	public int getNumNumbers() {
		return this.numberOfNumbers;
	}
	
	public int getNumSpecial() {
		return this.numberOfSpecial;
	}	
	
	public void incrementLetters() {
		numberOfLetters++;
	}
	
	public void incrementNumbers() {
		numberOfNumbers++;
	}
	
	public void incrementSpecial() {
		numberOfSpecial++;
	}
	
	public void setNumOfWords(int number) {
		this.numberOfWords = number;
	}
	
	public int getNumOfWords() {
		return this.numberOfWords;
	}
}