package com.owl.api.example.model;

public class BaseModel {
	private String name;
    private Manufacturer manufacturer;
    private Purpose purpose;
    private int price;

    public BaseModel() {
    }

    public BaseModel(String name, Manufacturer manufacturer, Purpose purpose, int price) {
		super();
		this.name = name;
		this.manufacturer = manufacturer;
		this.purpose = purpose;
		this.price = price;
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
