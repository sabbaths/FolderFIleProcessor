package com.example.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.processor.scheduler.FileMonitoringScheduler;


@SpringBootApplication
public class ProcessorApplication {
	
	private static FileMonitoringScheduler scheduler = new FileMonitoringScheduler();;

	public static void main(String[] args) {
		ProcessorApplication.checkTerminalArgs(args);
		SpringApplication.run(ProcessorApplication.class, args);
		
		String folderPath = args[0];
		
		System.out.println("TERMINAL ARGUMENT: " + folderPath);
		
		scheduler.setFolder(folderPath);
		scheduler.startScheduler();
	}
	
	private static void checkTerminalArgs(String[] args) {
		if (args.length == 0) {
			System.out.println("SYSTEM WILL EXIT: INPUT FOLDER PATH");
			System.exit(0);
		}
	}

}
