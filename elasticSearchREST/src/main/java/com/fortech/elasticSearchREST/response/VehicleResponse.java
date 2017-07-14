package com.fortech.elasticSearchREST.response;

import com.fortech.elasticSearchREST.model.Vehicle;

public class VehicleResponse {

	private CodeAndDescriptionResponse codeAndDescriptionResponse;
	private Vehicle vehicle;
	
	public CodeAndDescriptionResponse getCodeAndDescriptionResponse() {
		return codeAndDescriptionResponse;
	}
	public void setCodeAndDescriptionResponse(CodeAndDescriptionResponse codeAndDescriptionResponse) {
		this.codeAndDescriptionResponse = codeAndDescriptionResponse;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeAndDescriptionResponse == null) ? 0 : codeAndDescriptionResponse.hashCode());
		result = prime * result + ((vehicle == null) ? 0 : vehicle.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleResponse other = (VehicleResponse) obj;
		if (codeAndDescriptionResponse == null) {
			if (other.codeAndDescriptionResponse != null)
				return false;
		} else if (!codeAndDescriptionResponse.equals(other.codeAndDescriptionResponse))
			return false;
		if (vehicle == null) {
			if (other.vehicle != null)
				return false;
		} else if (!vehicle.equals(other.vehicle))
			return false;
		return true;
	}
	

	
	
}
