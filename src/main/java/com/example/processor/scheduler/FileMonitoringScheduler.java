package com.example.processor.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FileMonitoringScheduler {
	
	private boolean isProcessing;
	private String sourceFolder;
	private String processedFolder;
	
	public FileMonitoringScheduler(String sourceFolder) {
		this.sourceFolder = sourceFolder;
		this.processedFolder = sourceFolder;
	}

	@Scheduled(fixedRate = 5000)
	public void startScheduler() {
		System.out.println("CHECKING FOR FILE IN FOLDER: ");
	}
	
	static void setFolder(String folderPath) {
		
	}
	
	void moveFileToProcessedFolder() {
		
	}
	
}
