package com.mobioapp.klassify.models;

public class Category {

	private String id;
	private String parent_id;
	private String name;
	private String alias;
	private String summary;
	private String serial;
	private String author;
	private String image;

	public Category(String id, String parent_id, String name, String alias,
			String summary, String serial, String author,String image) {
		super();
		this.id = id;
		this.parent_id = parent_id;
		this.name = name;
		this.alias = alias;
		this.summary = summary;
		this.serial = serial;
		this.author = author;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
