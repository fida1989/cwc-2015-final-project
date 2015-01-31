package com.mobioapp.klassify.utils;

import java.util.ArrayList;
import java.util.List;

import com.mobioapp.klassify.models.Category;
import com.mobioapp.klassify.models.CategoryNew;
import com.mobioapp.klassify.models.City;
import com.mobioapp.klassify.models.Item;
import com.mobioapp.klassify.models.SubCategory;

public class Values {
	public static boolean loaded = false;
	public static String cat = "";
	public static String sub_cat = "";
	public static Item cur_item = null;
	public static boolean register = false;
	public static List<Category> catList = new ArrayList<Category>();
	public static List<SubCategory> subCatList = new ArrayList<SubCategory>();
	public static int position;
	public static ArrayList<City> cities = new ArrayList<City>();
	public static ArrayList<CategoryNew> cats = new ArrayList<CategoryNew>();
}
