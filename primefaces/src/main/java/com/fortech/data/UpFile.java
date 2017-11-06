package com.fortech.data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author andreig.muresan
 *
 */
@XmlRootElement
public class UpFile {
	
	private String fileName;
	private ZipContent zipContent;	
	
	public UpFile() {
		
	}

	public UpFile(String fileName, ZipContent zipContent) {
		this.fileName = fileName;
		this.zipContent = zipContent;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ZipContent getZipContent() {
		return zipContent;
	}

	public void setZipContent(ZipContent zipContent) {
		this.zipContent = zipContent;
	}

	@Override
	public String toString() {
		return "UpFile [fileName=" + fileName + ", zipContent=" + zipContent + "]";
	}

}
