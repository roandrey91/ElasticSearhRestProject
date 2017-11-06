package com.fortech.elasticSearchREST.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

import com.fortech.elasticSearchREST.model.PathZipParam;
import com.fortech.elasticSearchREST.model.FileContent;
import com.fortech.elasticSearchREST.model.UpFile;
import com.fortech.elasticSearchREST.model.ZipContent;


public class ZipContentsService {

	private List<FileContent> fileContents = new ArrayList<>();


	public UpFile getZipContents(PathZipParam path) throws IOException {
		UpFile upFile = new UpFile();
		ZipContent zipContent = new ZipContent();

		final File result = new File(path.getPathUpParam());
		String name = "";
		try{
			upFile.setFileName(result.getName());
			ZipFile zipFile = new ZipFile(result);
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries(); 

			while (enumeration.hasMoreElements()) {				
				ZipEntry zipEntry =  enumeration.nextElement();	
				FileContent fileContent = new FileContent();	

				name = zipEntry.getName();
				name = name.substring(name.lastIndexOf("/")+1, name.length());
				
				if (!name.isEmpty()) {
					fileContent.setFileName(name);	

					if(name.endsWith(".txt")) {
						try {
							InputStream stream = zipFile.getInputStream(zipEntry);
							String streamOutput = IOUtils.toString(stream, StandardCharsets.UTF_8.name());
							String finalStreamOutput = streamOutput + System.lineSeparator();

							fileContent.setFileContent(finalStreamOutput);

						}catch (Exception e3) {
							e3.printStackTrace();
						} 					
					}	

					fileContents.add(fileContent);			 
					zipContent.setFileContents(fileContents);
					upFile.setZipContent(zipContent);
				}
			}
			zipFile.close();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		return upFile;

	}


	public static void main(String[] args){
		ZipContentsService uploadedFileService = new ZipContentsService();
		String destination="C:\\Users\\andreig.muresan\\Downloads\\test2.zip";
		PathZipParam param = new PathZipParam();
		param.setPathUpParam(destination);
		try {
			System.out.println(uploadedFileService.getZipContents(param));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
