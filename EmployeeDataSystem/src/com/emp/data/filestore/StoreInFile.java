package com.emp.data.filestore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StoreInFile {
	private String filePath;
	private FileWriter fileWriter;

	public StoreInFile(String filePath) {
		this.filePath = filePath;
		File empFile = new File(this.filePath);
		try {
			if (!empFile.exists()) {
				empFile.createNewFile();
			}
			this.fileWriter = new FileWriter(empFile, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeToFile(String data) {
		try {
			fileWriter.write(data + "\n");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void closeFileWriter() {
		if(fileWriter != null) {
			try {
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
