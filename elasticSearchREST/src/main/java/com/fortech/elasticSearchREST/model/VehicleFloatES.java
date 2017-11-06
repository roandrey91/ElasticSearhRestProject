package com.fortech.elasticSearchREST.model;

import java.util.Arrays;

public class VehicleFloatES {

	private String elasticSearchId;

	private Long id;

	private String brandName;

	private String bodyType;

	private String fuelType;

	private String transmission;

	private String registracionDate;

	private String color;

	private Float price;

	private String vehicleLocation;

	private String[] tags;

	public VehicleFloatES() {

	}

	public VehicleFloatES(Long id, String brandName, String bodyType, String fuelType, String transmission,
			String registracionDate, String color, Float price, String vehicleLocation, String[] tags) {
		this.id = id;
		this.brandName = brandName;
		this.bodyType = bodyType;
		this.fuelType = fuelType;
		this.transmission = transmission;
		this.registracionDate = registracionDate;
		this.color = color;
		this.price = price;
		this.vehicleLocation = vehicleLocation;
		this.tags = tags;
	}
	
	public VehicleFloatES(String elasticSearchId, Long id, String brandName, String bodyType, String fuelType, String transmission,
			String registracionDate, String color, Float price, String vehicleLocation, String[] tags) {
		this.elasticSearchId = elasticSearchId;
		this.id = id;
		this.brandName = brandName;
		this.bodyType = bodyType;
		this.fuelType = fuelType;
		this.transmission = transmission;
		this.registracionDate = registracionDate;
		this.color = color;
		this.price = price;
		this.vehicleLocation = vehicleLocation;
		this.tags = tags;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBodyType() {
		return bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public String getRegistracionDate() {
		return registracionDate;
	}

	public void setRegistracionDate(String registracionDate) {
		this.registracionDate = registracionDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getVehicleLocation() {
		return vehicleLocation;
	}

	public void setVehicleLocation(String vehicleLocation) {
		this.vehicleLocation = vehicleLocation;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getElasticSearchId() {
		return elasticSearchId;
	}

	public void setElasticSearchId(String elasticSearchId) {
		this.elasticSearchId = elasticSearchId;
	}

	@Override
	public String toString() {
		return "VehicleFloatES [elasticSearchId=" + elasticSearchId + ", id=" + id + ", brandName=" + brandName
				+ ", bodyType=" + bodyType + ", fuelType=" + fuelType + ", transmission=" + transmission
				+ ", registracionDate=" + registracionDate + ", color=" + color + ", price=" + price
				+ ", vehicleLocation=" + vehicleLocation + ", tags=" + Arrays.toString(tags) + "]";
	}

}
