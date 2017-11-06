package com.fortech.beans;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name="fileUpload")
public class FileUploadBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> zipContent;
	private List<String> fileTxtContent;
	private String txtFileContent;
	private String zipFileName;
	private List<StreamedContent> stre;
	private List<String> images;
	private byte[] imgs;
	
	@PostConstruct
	public void init() {
		zipContent = new ArrayList<>();
		zipFileName = "";
		fileTxtContent = new ArrayList<>();
		txtFileContent = "";
		stre = new ArrayList<>();
		images = new ArrayList<>();
		imgs = new byte[1024];
	}


	public void upload(FileUploadEvent e) throws IOException {
		String destination="C:\\Users\\andreig.muresan\\Downloads";

		final File result = new File(destination, e.getFile().getFileName());
		String name = "";
		try{
			setZipFileName(result.getName());
			ZipFile zipFile = new ZipFile(result);
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries(); 

			while (enumeration.hasMoreElements()) {				
				ZipEntry zipEntry =  enumeration.nextElement();	
				
				if(zipEntry.isDirectory()){
					name = "dir : " +  zipEntry.getName();
					zipContent.add(name);
				}
				
				 name = zipEntry.getName();
			     name = name.substring(name.lastIndexOf("/")+1, name.length());
			     
				if(name.endsWith(".txt")) {
					try {
					InputStream stream = zipFile.getInputStream(zipEntry);
					String streamOutput = IOUtils.toString(stream, StandardCharsets.UTF_8.name());
					String finalStreamOutput = streamOutput + System.lineSeparator();
					System.out.println(finalStreamOutput);
				    txtFileContent = name;
					fileTxtContent.add(finalStreamOutput);
					}catch (Exception e3) {
						e3.printStackTrace();
					} 
								
				}
				
				if(name.endsWith(".jpg")){
					InputStream stream = zipFile.getInputStream(zipEntry);
					byte[] imageData = getImage(stream);
					ByteArrayInputStream is = new ByteArrayInputStream(imageData);
					StreamedContent image = new DefaultStreamedContent(is, "image/jpg");
					String imageDataString = Base64.encodeBase64String(imageData);
					images.add(imageDataString);
					stre.add(image);
				}
				zipContent.add(name);
			}
			zipFile.close();
		}catch (Exception e1) {
			e1.printStackTrace();
		}

		FacesMessage message = new FacesMessage("Succesful", result.getName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void printFileList(String filePath){

		FileInputStream fis = null;
		ZipInputStream zipIs = null;
		ZipEntry zEntry = null;
		try {
			fis = new FileInputStream(filePath);
			zipIs = new ZipInputStream(new BufferedInputStream(fis));
			while((zEntry = zipIs.getNextEntry()) != null){
				System.out.println(zEntry.getName());
				zipContent.add(zEntry.getName());
			}
			zipIs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyFile(String fileName, InputStream in) {

		String destination="D:\\";
		try {

			File theDir = new File(destination);            
			if(!theDir.exists())
			{
				try {
					theDir.mkdir();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}            

			OutputStream out = new FileOutputStream(new File(destination + fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();           

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String newLine(String content){
		StringBuilder sBuilder = new StringBuilder();
		int i = 0;
		while((i = sBuilder.indexOf(" ", i + 10)) >= 0) {
			sBuilder.replace(i, i + 1, "\n");
			
		}
		return sBuilder.toString();
	}

	private byte[] getImage(InputStream in)  {
	    try {
	        BufferedImage image = ImageIO.read(in); //just checking if the InputStream belongs in fact to an image
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(image, "jpg", baos);
	        return baos.toByteArray();
	    } catch (IOException e) {
	        // do something, it is not a image
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public String getZipFileName() {
		return zipFileName;
	}

	public void setZipFileName(String zipFileName) {
		this.zipFileName = zipFileName;
	}

	public List<String> getZipContent() {
		return zipContent;
	}

	public void setZipContent(List<String> zipContent) {
		this.zipContent = zipContent;
	}

	public List<String> getFileTxtContent() {
		return fileTxtContent;
	}

	public void setFileTxtContent(List<String> fileTxtContent) {
		this.fileTxtContent = fileTxtContent;
	}

	public String getTxtFileContent() {
		return txtFileContent;
	}

	public void setTxtFileContent(String txtFileContent) {
		this.txtFileContent = txtFileContent;
	}


	public List<StreamedContent> getStre() {
		return stre;
	}


	public void setStre(List<StreamedContent> stre) {
		this.stre = stre;
	}


	public List<String> getImages() {
		return images;
	}


	public void setImages(List<String> images) {
		this.images = images;
	}


	public byte[] getImgs() {
		return imgs;
	}


	public void setImgs(byte[] imgs) {
		this.imgs = imgs;
	}

			
}
