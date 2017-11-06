package com.fortech.elasticSearchREST.model;

public class FileContent {

	private String fileName;
	private String fileContent;
	
	public FileContent() {
		
	}

	public FileContent(String fileName, String fileContent) {
		this.fileName = fileName;
		this.fileContent = fileContent;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	@Override
	public String toString() {
		return "FileContent [fileName=" + fileName + ", fileContent=" + fileContent + "]";
	}
		
}
