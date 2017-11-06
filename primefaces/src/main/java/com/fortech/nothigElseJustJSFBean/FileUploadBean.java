package com.fortech.nothigElseJustJSFBean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import com.fortech.client.VehicleClient;
import com.fortech.data.FileContent;
import com.fortech.data.UpFile;
import com.fortech.data.ZipContent;

@ManagedBean(name="fileUpload1")
public class FileUploadBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fileName;
	private List<FileContent> textContents;
	private List<FileContent> fileContents;
	
	private VehicleClient vehicleClient = new VehicleClient();
	
	@PostConstruct
	public void init() {
		fileName = "";
		textContents = new ArrayList<>();
		fileContents = new ArrayList<>();
	}

	public void upload(FileUploadEvent e) throws IOException {
		String destination="C:\\Users\\andreig.muresan\\Downloads";
		final File result = new File(destination, e.getFile().getFileName());
	
		UpFile upFile = vehicleClient.getFileZipContent(result.getAbsolutePath());
		
		ZipContent zipContent = upFile.getZipContent();
				
		fileName = upFile.getFileName();
		
		fileContents = zipContent.getFileContents();
		for(FileContent file : fileContents){
			      FileContent content = new FileContent();
			 if (file.getFileName().endsWith(".txt")) {
				 content = file;
				 textContents.add(content);
			}
		}
		
		FacesMessage message = new FacesMessage("Succesful", result.getName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
	}
			
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<FileContent> getTextContents() {
		return textContents;
	}

	public void setTextContents(List<FileContent> textContents) {
		this.textContents = textContents;
	}

	public List<FileContent> getFileContents() {
		return fileContents;
	}

	public void setFileContents(List<FileContent> fileContents) {
		this.fileContents = fileContents;
	}

}
