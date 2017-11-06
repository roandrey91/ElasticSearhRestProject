package com.fortech.elasticSearchREST.model;

import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ZipContent {

	private List<FileContent> fileContents;

	public ZipContent(){

	}

	public ZipContent(List<FileContent> fileContents) {
		this.fileContents = fileContents;
	}

	public List<FileContent> getFileContents() {
		return fileContents;
	}

	public void setFileContents(List<FileContent> fileContents) {
		this.fileContents = fileContents;
	}

	@Override
	public String toString() {
		return "ZipContent [fileContents=" + fileContents + "]";
	}

}
