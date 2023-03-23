package com.example.processor.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Component;
import com.example.processor.dao.AbstractFile;
import com.example.processor.dao.TextFile;
import com.example.processor.scheduler.FileMonitoringScheduler;

@Component
public class FileProcessor {
	
	private String sourceFolder;
	private String processFolder; 
	private final String regexSpecial = "[@#$%^^*)*))]";
	
	
	public void processFile(String sourceFolder, String processedFolder, String filePath) {
		this.sourceFolder = sourceFolder;
		this.processFolder = processedFolder;
		
		System.out.println("PROCESSING FILE: " + filePath);
		
		try {
			checkFileType(filePath);
			AbstractFile file = new TextFile(filePath);
			 			
			processFileSetProperties(file);
			moveFileToProcessedFolder(filePath);
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			FileMonitoringScheduler.setIsProcessing(false);
		}
	}
	
	private void processFileSetProperties(AbstractFile file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file.getPath()))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                System.out.println(line);
             
                lineProcessor(file, line);
            }
            
            printStatisticsOfFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void lineProcessor(AbstractFile file, String line) {
		char[] charArr = line.toCharArray();
		int numOfWords = line.split(" ").length;
		
		file.setNumOfWords(numOfWords);
		
		for (int i = 0; i<charArr.length; i++) {
			Character charInLine = charArr[i];
			
			if (Character.isAlphabetic(charInLine))
				file.incrementLetters();
			
			if (Character.isDigit(charInLine))
				file.incrementNumbers();
			
			if (Character.toString(charInLine).matches(regexSpecial)) {
				file.incrementSpecial();
			}
		}
	}
	
	public void moveFileToProcessedFolder(String filePath) {
	    Path sourcePath = Paths.get(filePath);
	    Path targetPath = Paths.get(this.processFolder, "processed", sourcePath.getFileName().toString());

	    try {
	        String targetDirectory = targetPath.getParent().toString();
	        File directory = new File(targetDirectory);

	        if (!directory.exists()) {
	            System.out.println("PROCESSED FOLDER DOES NOT EXIST. CREATING FOLDER: " + targetDirectory);
	            directory.mkdirs();
	        }
	        
	        System.out.println("MOVING FILE TO PROCESSED FOLDER: " + targetPath);

	        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}
	
	private void checkFileType(String filePath) {
        String fileExtension = "";
        int extensionIndex = filePath.lastIndexOf(".");
        
        if (extensionIndex > 0 && extensionIndex < filePath.length() - 1) {
            fileExtension = filePath.substring(extensionIndex + 1);
        }
        
        if (!fileExtension.equals("txt")) {
        	System.out.print("Not a text file :: ");
        	
        	moveFileToProcessedFolder(filePath);
        	throw new RuntimeException("INVALID FILE FORMAT");
        }
	}
	
	private void printStatisticsOfFile(AbstractFile file) {
		System.out.println("				");
		System.out.println("~~ STATISTICS ~~");
		System.out.println("NUMBER OF WORDS: " + file.getNumOfWords());
		System.out.println("NUMBER OF LETTERS: " + file.getNumLetters());
		System.out.println("NUMBER OF NUMBERS: " + file.getNumNumbers());
		System.out.println("NUMBER OF SPECIAL CHAR: " + file.getNumSpecial());
		System.out.println("                 ");
		System.out.println("                 ");
	}
}
