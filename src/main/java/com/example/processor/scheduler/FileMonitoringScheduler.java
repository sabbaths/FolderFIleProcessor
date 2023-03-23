package com.example.processor.scheduler;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.processor.service.FileProcessor;

@Component
public class FileMonitoringScheduler {
	
	private static boolean isProcessing;
	private String sourceFolder;
	private String processedFolder;
	
	@Autowired
	private final FileProcessor fileProcessor = new FileProcessor();

	public void startScheduler() {
		
		checkIfValidFolder();
		
		Timer t = new Timer();
		t.schedule(new TimerTask() {
		    @Override
		    public void run() {
		    	if (!isProcessing)
		    		checkFilesInFolder();
		    	else 
		    		System.out.println("STILL PROCESSING A FILE");
		    }
		}, 0, 10000);
	}
	
	private void checkIfValidFolder() {
		File f = new File(sourceFolder);
		
		if (f.exists() && f.isDirectory()) {
			System.out.println("SOURCE FOLDER: " + sourceFolder);
		} else {
			System.out.println("System will exist: invalid folder directory");
			System.exit(0);
		}
	}
	
	private void checkFilesInFolder() {
		File directory = new File(sourceFolder);
		
        if (directory.isDirectory()) {
        	File[] files = directory.listFiles(file -> file.isFile() && !file.isHidden());directory.listFiles();
            
            if (files != null && files.length > 0) {
                System.out.println("Directory contains " + files.length + " files.");
                
                sendForProcessing(files[0].getPath());
            } else {
                System.out.println("Directory is empty.");
            }
        } else {
            System.out.println("Path does not refer to a directory.");
        }		
	}
	
	private void sendForProcessing(String file) {
		setIsProcessing(true);
		fileProcessor.processFile(sourceFolder, processedFolder, file);
	}
	
	public static void setIsProcessing(boolean isProcessingArg) {
		isProcessing = isProcessingArg;
	}
	
	public void setFolder(String folderPath) {
		this.sourceFolder = folderPath;
		this.processedFolder = folderPath;
	}
}
