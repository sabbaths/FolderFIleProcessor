package com.example.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.processor.scheduler.FileMonitoringScheduler;


@SpringBootApplication
@EnableScheduling
public class ProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessorApplication.class, args);
		
		//ProcessorApplication.checkTerminalArgs(args);
		//ProcessorApplication.setFileMonitoringFolder(args[0]);
		
		new FileMonitoringScheduler().startScheduler();
	}
	
	private static void checkTerminalArgs(String[] args) {
		if (args.length == 0) {
			System.out.println("SYSTEM WILL EXIT: INPUT FOLDER PATH");
			System.exit(0);
		}
	}

}
