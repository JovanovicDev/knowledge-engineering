package com.owl.api.example.model;

public class BaseModel {
    private Manufacturer manufacturer;
    private Purpose purpose;
    private int price;

    public BaseModel() {
    }

    public BaseModel(Manufacturer manufacturer, Purpose purpose, int price) {
		super();
		this.manufacturer = manufacturer;
		this.purpose = purpose;
		this.price = price;
	}

	public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Purpose getPurpose() {
		return purpose;
	}


	public void setPurpose(Purpose purpose) {
		this.purpose = purpose;
	}


	public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
