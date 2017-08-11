package com.fortech.elasticSearchREST.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author andreig.muresan
 */

@Entity
@XmlRootElement
public class Vehicle {

    @Id
    @Column
    private Long id;
    @Column
    private String brandName;
    @Column
    private String bodyType;
    @Column
    private String fuelType;
    @Column
    private String transmission;
    @Column
    private String registracionDate;
    @Column
    private String color;
    @Column
    private Double price;
    @Column
    private String vehicleLocation;

    public Vehicle() {

    }

    public Vehicle(Long id, String brandName, String bodyType, String fuelType, String transmission, String registracionDate, String color, Double price, String vehicleLocation) {
        this.id = id;
        this.brandName = brandName;
        this.bodyType = bodyType;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.registracionDate = registracionDate;
        this.color = color;
        this.price = price;
        this.vehicleLocation = vehicleLocation;
    }

    public Long getId() {
        return id;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getBodyType() {
        return bodyType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getRegistracionDate() {
        return registracionDate;
    }

    public String getColor() {
        return color;
    }

    public Double getPrice() {
        return price;
    }

    public String getVehicleLocation() {
        return vehicleLocation;
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
		Vehicle other = (Vehicle) obj;
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
        return "Vehicle [id=" + id + ", brandName=" + brandName + ", bodyType=" + bodyType + ", fuelType=" + fuelType
                + ", transmission=" + transmission + ", registracionDate=" + registracionDate + ", color=" + color
                + ", price=" + price + ", vehicleLocation=" + vehicleLocation + "]";
    }

}
