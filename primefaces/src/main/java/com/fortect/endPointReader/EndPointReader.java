package com.fortect.endPointReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EndPointReader {

	private static final String END_POINTS = "endpoint.properties";
	
	private final String URL_GET_VEHICLES_FROM_ES = "urlGetVehiclesFromEs";
	private final String URL_GET_VEHICLES_FROM_DB = "urlGetVehiclesFromDb";
	private final String URL_SAVE_VEHICLE_TO_DB = "urlSaveVehicleToDb";
	private final String URL_DELETE_VEHICLE_FROM_DB = "urlDeleteVehicleFromDb";
	private final String URL_UPDATE_VEHICLE_TO_DB = "urlUpdateVehicleToDb";
	private final String URL_TRANSFER_VEHICLE_FROM_DB_TO_ES = "urlTransferVehicleFromDbToEs";
	private final String URL_TRANSFER_ALL_VEHICLES_FROM_DB_TO_ES = "urlTransferAllVehiclesFromDbToEs";
	private final String URL_SAVE_VEHICLE_TO_ES = "urlSaveVehicleToEs";
	private final String URL_DELETE_ONE_VEHICLE_FROM_ES = "urlDeleteOneVehicleFromEs";
	private final String URL_SEARH_BY_FIELD_IN_ES = "urlSearchByFieldInEs";
	private final String URL_COUNT_NUMBER_OF_SEARCH_FROM_ES = "urlCountNumberOfSearchFromEs";
	private final String URL_GET_FIELD_VALUE_FROM_ES ="urlGetFieldValueFromEs";
	private final String URL_POST_GET_UPFILE = "urlPostUploadFile";
		
public String[] getEndPoints() {
		
		String[] endPoints = new String[13];

		Properties prop = new Properties();

		InputStream input = null;
		try {
			input = getClass().getClassLoader().getResourceAsStream(END_POINTS);
			
			prop.load(input);

			endPoints[0] = prop.getProperty(URL_GET_VEHICLES_FROM_ES); 
			endPoints[1] = prop.getProperty(URL_GET_VEHICLES_FROM_DB);
			endPoints[2] = prop.getProperty(URL_SAVE_VEHICLE_TO_DB);
			endPoints[3] = prop.getProperty(URL_DELETE_VEHICLE_FROM_DB);
			endPoints[4] = prop.getProperty(URL_UPDATE_VEHICLE_TO_DB);
			endPoints[5] = prop.getProperty(URL_TRANSFER_VEHICLE_FROM_DB_TO_ES);
			endPoints[6] = prop.getProperty(URL_TRANSFER_ALL_VEHICLES_FROM_DB_TO_ES);
			endPoints[7] = prop.getProperty(URL_SAVE_VEHICLE_TO_ES);
			endPoints[8] = prop.getProperty(URL_DELETE_ONE_VEHICLE_FROM_ES);
			endPoints[9] = prop.getProperty(URL_SEARH_BY_FIELD_IN_ES);
			endPoints[10] = prop.getProperty(URL_COUNT_NUMBER_OF_SEARCH_FROM_ES);
			endPoints[11] = prop.getProperty(URL_GET_FIELD_VALUE_FROM_ES);
			endPoints[12] = prop.getProperty(URL_POST_GET_UPFILE);
		} catch (IOException e) {
			e.getMessage();
		}
	
		return endPoints;
	}
	
	
	
	
	
	
}
