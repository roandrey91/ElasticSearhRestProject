package com.fortech.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PathZipParam {

	private String pathUpParam;
	
	public PathZipParam(){
		
	}
	
	public PathZipParam(String pathUpParam) {
		this.pathUpParam = pathUpParam;
	}

	public String getPathUpParam() {
		return pathUpParam;
	}

	public void setPathUpParam(String pathUpParam) {
		this.pathUpParam = pathUpParam;
	}

	@Override
	public String toString() {
		return "PathZipParam [pathUpParam=" + pathUpParam + "]";
	}
	
	
}
