package com.mobioapp.klassify.utils;

import java.util.ArrayList;
import java.util.List;

import com.mobioapp.klassify.models.Category;

public class SharedData {
	private String image_1;
	private String image_2;
	private String image_3;
	private String item_name;
	private String item_prize;
	private String item_id;
	private String iteme_type;
	private String item_company;
	private String item_seller_name;
	private String item_seller_phone;
	private String item_seller_location;
	private String item_seller_location_lattitude;
	private String item_seller_location_longitude;
	private String item_description;
	public static SharedData shared;
	private List<Category> categoryList = new ArrayList<Category>();

	public static SharedData getInstance() {

		if (shared == null) {
			return new SharedData();
		}
		return shared;
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

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_prize() {
		return item_prize;
	}

	public void setItem_prize(String item_prize) {
		this.item_prize = item_prize;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getIteme_type() {
		return iteme_type;
	}

	public void setIteme_type(String iteme_type) {
		this.iteme_type = iteme_type;
	}

	public String getItem_company() {
		return item_company;
	}

	public void setItem_company(String item_company) {
		this.item_company = item_company;
	}

	public String getItem_seller_name() {
		return item_seller_name;
	}

	public void setItem_seller_name(String item_seller_name) {
		this.item_seller_name = item_seller_name;
	}

	public String getItem_seller_phone() {
		return item_seller_phone;
	}

	public void setItem_seller_phone(String item_seller_phone) {
		this.item_seller_phone = item_seller_phone;
	}

	public String getItem_seller_location() {
		return item_seller_location;
	}

	public void setItem_seller_location(String item_seller_location) {
		this.item_seller_location = item_seller_location;
	}

	public String getItem_seller_location_lattitude() {
		return item_seller_location_lattitude;
	}

	public void setItem_seller_location_lattitude(
			String item_seller_location_lattitude) {
		this.item_seller_location_lattitude = item_seller_location_lattitude;
	}

	public String getItem_seller_location_longitude() {
		return item_seller_location_longitude;
	}

	public void setItem_seller_location_longitude(
			String item_seller_location_longitude) {
		this.item_seller_location_longitude = item_seller_location_longitude;
	}

	public String getItem_description() {
		return item_description;
	}

	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}

	public static SharedData getShared() {
		return shared;
	}

	public static void setShared(SharedData shared) {
		SharedData.shared = shared;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

}
