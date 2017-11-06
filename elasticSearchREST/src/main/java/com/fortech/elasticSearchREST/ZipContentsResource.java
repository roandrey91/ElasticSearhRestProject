package com.fortech.elasticSearchREST;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fortech.elasticSearchREST.model.PathZipParam;
import com.fortech.elasticSearchREST.model.UpFile;
import com.fortech.elasticSearchREST.services.ZipContentsService;


@Path("getZipContents")
public class ZipContentsResource {
		
	@Inject
	private ZipContentsService uploadedFileService;
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response getZipContents(PathZipParam path) throws IOException{
		
		 UpFile upFile = uploadedFileService.getZipContents(path);
		
		return Response.ok().entity(upFile).build();
	}
		
}
