<<<<<<< HEAD
package com.fortech.data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author andreig.muresan
 *
 */
public class VehicleES implements Serializable {

	private static final long serialVersionUID = 1L;

	private String  elasticSearchId;
	
	private Long id;
	
	private String brandName;
	
	private String bodyType;
	
	private String fuelType;
	
	private String transmission;
	
	private String registracionDate;

	private String color;
	
	private Double price;
	
	private String vehicleLocation;
		
	private List<String> tags;
	
	public VehicleES() {
		
	}

	public VehicleES(Long id, String brandName, String bodyType, String fuelType, String transmission,
			String registracionDate, String color, Double price, String vehicleLocation, List<String> tags) {
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getVehicleLocation() {
		return vehicleLocation;
	}

	public void setVehicleLocation(String vehicleLocation) {
		this.vehicleLocation = vehicleLocation;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

		
	public String get_id() {
		return elasticSearchId;
	}

	public void set_id(String _id) {
		this.elasticSearchId = _id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bodyType == null) ? 0 : bodyType.hashCode());
		result = prime * result + ((brandName == null) ? 0 : brandName.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((fuelType == null) ? 0 : fuelType.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((registracionDate == null) ? 0 : registracionDate.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((transmission == null) ? 0 : transmission.hashCode());
		result = prime * result + ((vehicleLocation == null) ? 0 : vehicleLocation.hashCode());
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
		VehicleES other = (VehicleES) obj;
		if (bodyType == null) {
			if (other.bodyType != null)
				return false;
		} else if (!bodyType.equals(other.bodyType))
			return false;
		if (brandName == null) {
			if (other.brandName != null)
				return false;
		} else if (!brandName.equals(other.brandName))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (fuelType == null) {
			if (other.fuelType != null)
				return false;
		} else if (!fuelType.equals(other.fuelType))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (registracionDate == null) {
			if (other.registracionDate != null)
				return false;
		} else if (!registracionDate.equals(other.registracionDate))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (transmission == null) {
			if (other.transmission != null)
				return false;
		} else if (!transmission.equals(other.transmission))
			return false;
		if (vehicleLocation == null) {
			if (other.vehicleLocation != null)
				return false;
		} else if (!vehicleLocation.equals(other.vehicleLocation))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VehicleES [id=" + id + ", brandName=" + brandName + ", bodyType=" + bodyType + ", fuelType=" + fuelType
				+ ", transmission=" + transmission + ", registracionDate=" + registracionDate + ", color=" + color
				+ ", price=" + price + ", vehicleLocation=" + vehicleLocation + ", tags=" + tags + "]";
	}
	
	
	
	
}
=======
package com.fortech.data;

import java.util.List;

/**
 * 
 * @author andreig.muresan
 *
 */
public class VehicleES {

	private Long id;
	
	private String brandName;
	
	private String bodyType;
	
	private String fuelType;
	
	private String transmission;
	
	private String registracionDate;

	private String color;
	
	private Double price;
	
	private String vehicleLocation;
		
	private List<String> tags;
	
	public VehicleES() {
		
	}

	public VehicleES(Long id, String brandName, String bodyType, String fuelType, String transmission,
			String registracionDate, String color, Double price, String vehicleLocation, List<String> tags) {
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getVehicleLocation() {
		return vehicleLocation;
	}

	public void setVehicleLocation(String vehicleLocation) {
		this.vehicleLocation = vehicleLocation;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	
	
}
>>>>>>> b9aa13bfbfeaeaeeb7262f65775760c048bdfc72
