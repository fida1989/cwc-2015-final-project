package com.mobioapp.klassify.models;

public class Item {




	private String id;
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	private String title;
	private String image;
	private String price;
	private String phone;
	private String owner;
	private String email;
	private String location;
	private String description;
	private String type;
	
	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Item(String id, String title, String image, String price,
			String phone, String owner, String email,String location,String type,String description) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.price = price;
		this.phone= phone;
		this.owner = owner;
		this.email = email;
		this.location = location;
		this.type = type;
		this.description = description;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

}
