package com.mobioapp.klassify.models;

public class NewSubCategory {

	private String id, parent_id, name, summary, status;

	public NewSubCategory(String id, String parent_id, String name,
			String summary, String status) {
		super();
		this.id = id;
		this.parent_id = parent_id;
		this.name = name;
		this.summary = summary;
		this.status = status;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
