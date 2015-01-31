package com.mobioapp.klassify.models;

public class MyPost {

	String post_id, image_1, image_2, image_3, post_price, post_title,
			post_des, post_cat, post_sub_cat, post_company, extra;

	public MyPost(String post_id, String image_1, String image_2,
			String image_3, String post_price, String post_title,
			String post_des, String post_cat, String post_sub_cat,
			String post_company, String extra) {
		super();
		this.post_id = post_id;
		this.image_1 = image_1;
		this.image_2 = image_2;
		this.image_3 = image_3;
		this.post_price = post_price;
		this.post_title = post_title;
		this.post_des = post_des;
		this.post_cat = post_cat;
		this.post_sub_cat = post_sub_cat;
		this.post_company = post_company;
		this.extra = extra;
	}

	public String getPost_id() {
		return post_id;
	}

	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}

	public String getImage_1() {
		return image_1;
	}

	public void setImage_1(String image_1) {
		this.image_1 = image_1;
	}

	public String getImage_2() {
		return image_2;
	}

	public void setImage_2(String image_2) {
		this.image_2 = image_2;
	}

	public String getImage_3() {
		return image_3;
	}

	public void setImage_3(String image_3) {
		this.image_3 = image_3;
	}

	public String getPost_price() {
		return post_price;
	}

	public void setPost_price(String post_price) {
		this.post_price = post_price;
	}

	public String getPost_title() {
		return post_title;
	}

	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}

	public String getPost_des() {
		return post_des;
	}

	public void setPost_des(String post_des) {
		this.post_des = post_des;
	}

	public String getPost_cat() {
		return post_cat;
	}

	public void setPost_cat(String post_cat) {
		this.post_cat = post_cat;
	}

	public String getPost_sub_cat() {
		return post_sub_cat;
	}

	public void setPost_sub_cat(String post_sub_cat) {
		this.post_sub_cat = post_sub_cat;
	}

	public String getPost_company() {
		return post_company;
	}

	public void setPost_company(String post_company) {
		this.post_company = post_company;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

}
