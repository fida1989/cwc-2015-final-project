package com.mobioapp.klassify.utils;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.mobioapp.klassify.models.Category;
import com.mobioapp.klassify.models.Item;
import com.mobioapp.klassify.models.MyPost;

import com.mobioapp.klassify.models.SubCategory;

public class Database {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "Klassify";

	private static final String DATABASE_TABLE = "category";

	private static final String DATABASE_TABLE_3 = "item";

	private static final String DATABASE_TABLE_5 = "mypost";

	private static final String DATABASE_TABLE_4 = "favorite";

	private static final String DATABASE_TABLE_2 = "subCategory";

	public static final String KEY_ID = "id";
	public static final String KEY_ID_2 = "id2";
	public static final String KEY_ID_3 = "id3";

	public static final String KEY_MATCH_ID = "match_id";
	public static final String KEY_QUES_ID = "ques_id";
	public static final String KEY_QUES = "ques";
	public static final String KEY_EXTRA = "extra";

	public static final String KEY_CAT_ID = "cat_id";
	public static final String KEY_CAT_NAME = "cat_name";
	public static final String KEY_CAT_PARENT_ID = "cat_parent_id";
	public static final String KEY_CAT_SUMMARY = "cat_summary";
	public static final String KEY_CAT_IMAGE = "cat_image";

	public static final String KEY_SUB_CAT_ID = "sub_cat_id";
	public static final String KEY_SUB_CAT_NAME = "sub_cat_name";
	public static final String KEY_SUB_CAT_PARENT_ID = "sub_cat_parent_id";
	public static final String KEY_SUB_CAT_SUMMARY = "sub_cat_summary";
	public static final String KEY_SUB_CAT_IMAGE = "sub_cat_image";

	public static final String KEY_ITEM_ID = "item_id";
	public static final String KEY_ITEM_TITLE = "item_title";
	public static final String KEY_ITEM_IMAGE = "item_image";
	public static final String KEY_ITEM_PRICE = "item_price";
	public static final String KEY_ITEM_POSTER_PHONE = "item_phone";
	public static final String KEY_ITEM_POSTER_NAME = "item_poster_name";
	public static final String KEY_ITEM_POSTER_EMAIL = "item_poster_email";
	public static final String KEY_ITEM_POSTER_LOCATION = "item_poster_location";
	public static final String KEY_ITEM_POSTER_TYPE = "item_type";
	public static final String KEY_ITEM_POSTER_DETAIL = "item_detail";
	public static final String KEY_ITEM_POSTER_TAG = "item_tag";
	public static final String KEY_ITEM_POSTER_CAT = "item_cat";
	public static final String KEY_ITEM_POSTER_SUB_CAT = "item_sub_cat";

	public static final String KEY_FAV_ITEM_ID = "item_id";
	public static final String KEY_FAV_ITEM_TITLE = "item_title";
	public static final String KEY_FAV_ITEM_IMAGE = "item_image";
	public static final String KEY_FAV_ITEM_PRICE = "item_price";
	public static final String KEY_FAV_ITEM_POSTER_PHONE = "item_phone";
	public static final String KEY_FAV_ITEM_POSTER_NAME = "item_poster_name";
	public static final String KEY_FAV_ITEM_POSTER_EMAIL = "item_poster_email";
	public static final String KEY_FAV_ITEM_POSTER_LOCATION = "item_poster_location";
	public static final String KEY_FAV_ITEM_POSTER_TYPE = "item_type";
	public static final String KEY_FAV_ITEM_POSTER_DETAIL = "item_detail";

	public static final String KEY_MY_ID = "id";
	public static final String KEY_MY_IMAGE_1 = "image_1";
	public static final String KEY_MY_IMAGE_2 = "image_2";
	public static final String KEY_MY_IMAGE_3 = "image_3";
	public static final String KEY_MY_CAT = "cat";
	public static final String KEY_MY_SUB_CAT = "sub_cat";
	public static final String KEY_MY_TITLE = "title";
	public static final String KEY_MY_PRICE = "price";
	public static final String KEY_MY_DESCRIPTION = "description";
	public static final String KEY_MY_COMPANY = "company";

	public static final String KEY_TABLE_ID = "id";
	public static final String KEY_GIFT_ID = "gift_id";
	public static final String KEY_GIFT_NAME = "name";
	public static final String KEY_GIFT_PRICE = "price";
	public static final String KEY_GIFT_FAVORITE = "favorite";
	public static final String KEY_GIFT_COMPANY = "company";
	public static final String KEY_GIFT_PRODUCT_TYPE = "product_type";
	public static final String KEY_GIFT_IMAGE = "image";

	private DatabaseHelper ourhelper;
	private Context ourcontext;
	private SQLiteDatabase ourdatabase;

	public class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub

			db.execSQL(" CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ID
					+ " INTEGER AUTO INCREMENT PRIMARY KEY, " + KEY_CAT_ID
					+ " TEXT UNIQUE , " + KEY_CAT_PARENT_ID + " TEXT, "
					+ KEY_CAT_NAME + " TEXT, " + KEY_CAT_SUMMARY + " TEXT  , "
					+ KEY_CAT_IMAGE + " TEXT );");

			db.execSQL(" CREATE TABLE " + DATABASE_TABLE_2 + " (" + KEY_ID
					+ " INTEGER AUTO INCREMENT PRIMARY KEY, " + KEY_SUB_CAT_ID
					+ " TEXT UNIQUE , " + KEY_SUB_CAT_PARENT_ID + " TEXT, "
					+ KEY_SUB_CAT_NAME + " TEXT, " + KEY_SUB_CAT_SUMMARY
					+ " TEXT  , " + KEY_SUB_CAT_IMAGE + " TEXT );");

			db.execSQL(" CREATE TABLE " + DATABASE_TABLE_3 + " (" + KEY_ID
					+ " INTEGER AUTO INCREMENT PRIMARY KEY, " + KEY_ITEM_ID
					+ " TEXT  , " + KEY_ITEM_TITLE + " TEXT, " + KEY_ITEM_PRICE
					+ " TEXT, " + KEY_ITEM_IMAGE + " TEXT  , "
					+ KEY_ITEM_POSTER_NAME + " TEXT, " + KEY_ITEM_POSTER_PHONE
					+ " TEXT, " + KEY_ITEM_POSTER_EMAIL + " TEXT, "
					+ KEY_ITEM_POSTER_LOCATION + " TEXT, "
					+ KEY_ITEM_POSTER_TYPE + " TEXT, " + KEY_ITEM_POSTER_TAG
					+ " TEXT, " + KEY_ITEM_POSTER_CAT + " TEXT, "
					+ KEY_ITEM_POSTER_SUB_CAT + " TEXT, "
					+ KEY_ITEM_POSTER_DETAIL + " TEXT );");

			db.execSQL(" CREATE TABLE " + DATABASE_TABLE_4 + " (" + KEY_ID
					+ " INTEGER AUTO INCREMENT PRIMARY KEY, " + KEY_FAV_ITEM_ID
					+ " TEXT UNIQUE , " + KEY_FAV_ITEM_TITLE + " TEXT, "
					+ KEY_FAV_ITEM_PRICE + " TEXT, " + KEY_FAV_ITEM_IMAGE
					+ " TEXT  , " + KEY_FAV_ITEM_POSTER_NAME + " TEXT, "
					+ KEY_FAV_ITEM_POSTER_PHONE + " TEXT, "
					+ KEY_FAV_ITEM_POSTER_EMAIL + " TEXT, "
					+ KEY_FAV_ITEM_POSTER_LOCATION + " TEXT, "
					+ KEY_FAV_ITEM_POSTER_DETAIL + " TEXT, "
					+ KEY_FAV_ITEM_POSTER_TYPE + " TEXT );");

			db.execSQL(" CREATE TABLE " + DATABASE_TABLE_5 + " (" + KEY_MY_ID
					+ " INTEGER AUTO INCREMENT PRIMARY KEY, " + KEY_MY_IMAGE_1
					+ " TEXT  , " + KEY_MY_IMAGE_2 + " TEXT, " + KEY_MY_IMAGE_3
					+ " TEXT, " + KEY_MY_PRICE + " TEXT  , " + KEY_MY_TITLE
					+ " TEXT, " + KEY_MY_CAT + " TEXT, " + KEY_MY_SUB_CAT
					+ " TEXT, " + KEY_MY_COMPANY + " TEXT, "
					+ KEY_MY_DESCRIPTION + " TEXT );");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

			if (oldVersion < newVersion) {

			}

			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public Database(Context c) {
		ourcontext = c;
	}

	public Database(OnItemSelectedListener a) {
		// TODO Auto-generated constructor stub
		ourcontext = (Context) a;
	}

	public Database(OnItemClickListener a) {
		// TODO Auto-generated constructor stub
		ourcontext = (Context) a;
	}

	public Database open() throws SQLException {
		ourhelper = new DatabaseHelper(ourcontext);
		ourdatabase = ourhelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourhelper.close();
	}

	public long addCategory(Category category) {

		ContentValues values = new ContentValues();

		Log.e("Data Insert", "D");

		values.put(KEY_CAT_ID, category.getId());
		values.put(KEY_CAT_PARENT_ID, category.getParent_id());
		values.put(KEY_CAT_NAME, category.getName());
		values.put(KEY_CAT_SUMMARY, category.getSummary());
		values.put(KEY_CAT_IMAGE, category.getImage());

		return ourdatabase.insert(DATABASE_TABLE, null, values);

	}

	public long addSubCategory(SubCategory suBcategory) {

		ContentValues values = new ContentValues();

		Log.e("Data Insert", "D");

		values.put(KEY_SUB_CAT_ID, suBcategory.getId());
		values.put(KEY_SUB_CAT_PARENT_ID, suBcategory.getParent_id());
		values.put(KEY_SUB_CAT_NAME, suBcategory.getName());
		values.put(KEY_SUB_CAT_SUMMARY, suBcategory.getSummary());
		values.put(KEY_SUB_CAT_IMAGE, suBcategory.getStatus());

		return ourdatabase.insert(DATABASE_TABLE_2, null, values);

	}

	public long addItem(Item item, String tag, String cat, String sub) {

		ContentValues values = new ContentValues();

		Log.e("Data Insert", "D");

		values.put(KEY_ITEM_ID, item.getId());
		values.put(KEY_ITEM_TITLE, item.getTitle());
		values.put(KEY_ITEM_PRICE, item.getPrice());
		values.put(KEY_ITEM_IMAGE, item.getImage());
		values.put(KEY_ITEM_POSTER_NAME, item.getOwner());
		values.put(KEY_ITEM_POSTER_PHONE, item.getPhone());
		values.put(KEY_ITEM_POSTER_EMAIL, item.getEmail());
		values.put(KEY_ITEM_POSTER_LOCATION, item.getLocation());
		values.put(KEY_ITEM_POSTER_TYPE, item.getType());
		values.put(KEY_ITEM_POSTER_DETAIL, item.getDescription());
		values.put(KEY_ITEM_POSTER_TAG, tag);
		values.put(KEY_ITEM_POSTER_CAT, cat);
		values.put(KEY_ITEM_POSTER_SUB_CAT, sub);
		return ourdatabase.insert(DATABASE_TABLE_3, null, values);

	}

	public long addFavItem(Item item) {

		ContentValues values = new ContentValues();

		Log.e("Data Insert", "D");

		values.put(KEY_FAV_ITEM_ID, item.getId());
		values.put(KEY_FAV_ITEM_TITLE, item.getTitle());
		values.put(KEY_FAV_ITEM_PRICE, item.getPrice());
		values.put(KEY_FAV_ITEM_IMAGE, item.getImage());
		values.put(KEY_FAV_ITEM_POSTER_NAME, item.getOwner());
		values.put(KEY_FAV_ITEM_POSTER_PHONE, item.getPhone());
		values.put(KEY_FAV_ITEM_POSTER_EMAIL, item.getEmail());
		values.put(KEY_FAV_ITEM_POSTER_LOCATION, item.getLocation());
		values.put(KEY_FAV_ITEM_POSTER_TYPE, item.getType());
		values.put(KEY_FAV_ITEM_POSTER_DETAIL, item.getDescription());

		return ourdatabase.insert(DATABASE_TABLE_4, null, values);

	}

	public long addMyPost(MyPost post) {

		ContentValues values = new ContentValues();

		Log.e("Data Insert", "D");

		values.put(KEY_MY_ID, post.getPost_id());
		values.put(KEY_MY_IMAGE_1, post.getImage_1());
		values.put(KEY_MY_IMAGE_2, post.getImage_2());
		values.put(KEY_MY_IMAGE_3, post.getImage_3());
		values.put(KEY_MY_PRICE, post.getPost_price());
		values.put(KEY_MY_TITLE, post.getPost_title());
		values.put(KEY_MY_CAT, post.getPost_cat());
		values.put(KEY_MY_SUB_CAT, post.getPost_sub_cat());
		values.put(KEY_MY_DESCRIPTION, post.getPost_des());
		values.put(KEY_MY_COMPANY, post.getPost_company());

		return ourdatabase.insert(DATABASE_TABLE_5, null, values);

	}

	//
	// public long addEventData(Event event) {
	//
	// ContentValues values = new ContentValues();
	//
	// Log.e("Data Insert", "D Event ");
	//
	// values.put(KEY_EVENT_NAME, event.getName());
	// values.put(KEY_EVENT_TYPE, event.getType());
	// values.put(KEY_EVENT_DATE, event.getDate());
	// values.put(KEY_EVENT_ID_ID, event.getId());
	// values.put(KEY_EVENT_SOCIAL_ID, event.getSocial_id());
	// values.put(KEY_EVENT_USER_NAME, event.getUsername());
	// values.put(KEY_EVENT_IMAGE, event.getImage());
	// values.put(KEY_EVENT_BUDDY_ID, event.getBuddy_id());
	//
	// return ourdatabase.insert(DATABASE_TABLE_3, null, values);
	//
	// }
	//
	// public long addHomeGiftData(Gift gift) {
	//
	// ContentValues values = new ContentValues();
	//
	// Log.e("Data Insert", "D Event ");
	//
	// values.put(KEY_GIFT_COMPANY, gift.getCompany());
	// values.put(KEY_GIFT_FAVORITE, gift.getFavorite());
	// values.put(KEY_GIFT_ID, gift.getId());
	// values.put(KEY_GIFT_IMAGE, gift.getImage());
	// values.put(KEY_GIFT_NAME, gift.getName());
	// values.put(KEY_GIFT_PRICE, gift.getPrice());
	// values.put(KEY_GIFT_PRODUCT_TYPE, gift.getProductType());
	//
	// return ourdatabase.insert(DATABASE_TABLE_4, null, values);
	//
	// }
	//
	// public int getUserProfile() {
	//
	// String[] cloumns = new String[] { KEY_USER_NAME, KEY_USER_LNAME,
	// KEY_USER_FNAME, KEY_USER_EMAIL, KEY_USER_BIRTH,
	// KEY_USER_GENDER, KEY_USER_USER_ID, KEY_USER_IMAGE };
	// Cursor cursor = ourdatabase.query(DATABASE_TABLE, cloumns, null, null,
	// null, null, null);
	//
	// int t1 = cursor.getColumnIndex(KEY_USER_NAME);
	// int t2 = cursor.getColumnIndex(KEY_USER_LNAME);
	// int t3 = cursor.getColumnIndex(KEY_USER_FNAME);
	// int t4 = cursor.getColumnIndex(KEY_USER_EMAIL);
	// int t5 = cursor.getColumnIndex(KEY_USER_BIRTH);
	// int t6 = cursor.getColumnIndex(KEY_USER_GENDER);
	// int t7 = cursor.getColumnIndex(KEY_USER_USER_ID);
	// int t8 = cursor.getColumnIndex(KEY_USER_IMAGE);
	//
	// Log.e("D", "D12");
	//
	// for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
	//
	// User.name = cursor.getString(t1);
	// User.firstName = cursor.getString(t2);
	// User.lastName = cursor.getString(t3);
	// User.email = cursor.getString(t4);
	// User.birthday = cursor.getString(t5);
	// User.gender = cursor.getString(t6);
	// User.id = cursor.getString(t7);
	// User.bitmapPropic = cursor.getString(t8);
	//
	// User.name = cursor.getString(t1);
	//
	// }
	// return cursor.getCount();
	// }

	public ArrayList<Category> getCategoryList() {

		Cursor cursor = ourdatabase.rawQuery("Select * from " + DATABASE_TABLE,
				null);

		int t1 = cursor.getColumnIndex(KEY_CAT_ID);
		int t2 = cursor.getColumnIndex(KEY_CAT_PARENT_ID);
		int t3 = cursor.getColumnIndex(KEY_CAT_NAME);
		int t4 = cursor.getColumnIndex(KEY_CAT_SUMMARY);
		int t5 = cursor.getColumnIndex(KEY_CAT_IMAGE);

		ArrayList<Category> catList = new ArrayList<Category>();

		Log.e("D", "D12");

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			catList.add(new Category(cursor.getString(t1),
					cursor.getString(t2), cursor.getString(t3), "", cursor
							.getString(t4), "", "", cursor.getString(t5)));

		}

		return catList;
	}

	public ArrayList<SubCategory> getSubCategoryList() {

		Cursor cursor = ourdatabase.rawQuery("Select * from "
				+ DATABASE_TABLE_2, null);

		int t1 = cursor.getColumnIndex(KEY_SUB_CAT_ID);
		int t2 = cursor.getColumnIndex(KEY_SUB_CAT_PARENT_ID);
		int t3 = cursor.getColumnIndex(KEY_SUB_CAT_NAME);
		int t4 = cursor.getColumnIndex(KEY_SUB_CAT_SUMMARY);
		int t5 = cursor.getColumnIndex(KEY_SUB_CAT_IMAGE);

		ArrayList<SubCategory> catList = new ArrayList<SubCategory>();

		Log.e("D", "D12");

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			catList.add(new SubCategory(cursor.getString(t1), cursor
					.getString(t2), cursor.getString(t3), cursor.getString(t4),
					cursor.getString(t5)));

		}

		return catList;
	}

	public ArrayList<Item> getItemCategoryList(String tag, String c, String s) {

		Cursor cursor = ourdatabase.rawQuery("Select * from "
				+ DATABASE_TABLE_3 + " where item_tag='" + tag + "'"
				+ " and item_cat='" + c + "' and item_sub_cat='" + s + "'",
				null);

		int t1 = cursor.getColumnIndex(KEY_ITEM_ID);
		int t2 = cursor.getColumnIndex(KEY_ITEM_TITLE);
		int t3 = cursor.getColumnIndex(KEY_ITEM_PRICE);
		int t4 = cursor.getColumnIndex(KEY_ITEM_IMAGE);
		int t5 = cursor.getColumnIndex(KEY_ITEM_POSTER_NAME);
		int t6 = cursor.getColumnIndex(KEY_ITEM_POSTER_NAME);
		int t7 = cursor.getColumnIndex(KEY_ITEM_POSTER_EMAIL);
		int t8 = cursor.getColumnIndex(KEY_ITEM_POSTER_LOCATION);
		int t9 = cursor.getColumnIndex(KEY_ITEM_POSTER_TYPE);
		int t10 = cursor.getColumnIndex(KEY_ITEM_POSTER_DETAIL);

		ArrayList<Item> itemList = new ArrayList<Item>();

		Log.e("D", "D12");

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			itemList.add(new Item(cursor.getString(t1), cursor.getString(t2),
					cursor.getString(t4), cursor.getString(t3), cursor
							.getString(t6), cursor.getString(t5), cursor
							.getString(t7), cursor.getString(t8), cursor
							.getString(t9), cursor.getString(t10)));

		}

		return itemList;
	}

	public ArrayList<Item> getFavItemCategoryList() {

		Cursor cursor = ourdatabase.rawQuery("Select * from "
				+ DATABASE_TABLE_4, null);

		int t1 = cursor.getColumnIndex(KEY_FAV_ITEM_ID);
		int t2 = cursor.getColumnIndex(KEY_FAV_ITEM_TITLE);
		int t3 = cursor.getColumnIndex(KEY_FAV_ITEM_PRICE);
		int t4 = cursor.getColumnIndex(KEY_FAV_ITEM_IMAGE);
		int t5 = cursor.getColumnIndex(KEY_FAV_ITEM_POSTER_NAME);
		int t6 = cursor.getColumnIndex(KEY_FAV_ITEM_POSTER_NAME);
		int t7 = cursor.getColumnIndex(KEY_FAV_ITEM_POSTER_EMAIL);
		int t8 = cursor.getColumnIndex(KEY_FAV_ITEM_POSTER_LOCATION);
		int t9 = cursor.getColumnIndex(KEY_FAV_ITEM_POSTER_TYPE);
		int t10 = cursor.getColumnIndex(KEY_FAV_ITEM_POSTER_DETAIL);

		ArrayList<Item> itemList = new ArrayList<Item>();

		Log.e("D", "D12");

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			itemList.add(new Item(cursor.getString(t1), cursor.getString(t2),
					cursor.getString(t4), cursor.getString(t3), cursor
							.getString(t6), cursor.getString(t5), cursor
							.getString(t7), cursor.getString(t8), cursor
							.getString(t9), cursor.getString(t10)));

		}

		return itemList;
	}

	public ArrayList<MyPost> getMyPostCategoryList() {

		Cursor cursor = ourdatabase.rawQuery("Select * from "
				+ DATABASE_TABLE_5, null);

		int t1 = cursor.getColumnIndex(KEY_MY_ID);
		int t2 = cursor.getColumnIndex(KEY_MY_IMAGE_1);
		int t3 = cursor.getColumnIndex(KEY_MY_IMAGE_2);
		int t4 = cursor.getColumnIndex(KEY_MY_IMAGE_3);
		int t5 = cursor.getColumnIndex(KEY_MY_TITLE);
		int t6 = cursor.getColumnIndex(KEY_MY_PRICE);
		int t7 = cursor.getColumnIndex(KEY_MY_CAT);
		int t8 = cursor.getColumnIndex(KEY_MY_SUB_CAT);
		int t9 = cursor.getColumnIndex(KEY_MY_DESCRIPTION);
		int t10 = cursor.getColumnIndex(KEY_MY_COMPANY);

		ArrayList<MyPost> postList = new ArrayList<MyPost>();

		Log.e("D", "D12");

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			postList.add(new MyPost(cursor.getString(t1), cursor.getString(t2),
					cursor.getString(t3), cursor.getString(t4), cursor
							.getString(t6), cursor.getString(t5), cursor
							.getString(t9), cursor.getString(t7), cursor
							.getString(t8), cursor.getString(t10), ""));

		}

		return postList;
	}

	public boolean isAlreadyAdded(String item_id) {

		Cursor cursor = ourdatabase.rawQuery("Select * from "
				+ DATABASE_TABLE_4 + " where item_id='" + item_id + "'", null);

		if (cursor.getCount() > 0) {
			return true;
		} else {
			return false;
		}

	}

	// public ArrayList<Gift> getHomeGiftData() {
	//
	// Cursor cursor = ourdatabase.rawQuery("Select * from "
	// + DATABASE_TABLE_4, null);
	//
	// int t1 = cursor.getColumnIndex(KEY_GIFT_ID);
	// int t2 = cursor.getColumnIndex(KEY_GIFT_NAME);
	// int t3 = cursor.getColumnIndex(KEY_GIFT_PRICE);
	// int t4 = cursor.getColumnIndex(KEY_GIFT_IMAGE);
	//
	// int t5 = cursor.getColumnIndex(KEY_GIFT_PRODUCT_TYPE);
	// int t6 = cursor.getColumnIndex(KEY_GIFT_COMPANY);
	// int t7 = cursor.getColumnIndex(KEY_GIFT_FAVORITE);
	//
	// ArrayList<Gift> giftList = new ArrayList<Gift>();
	//
	// Log.e("D", "D12");
	//
	// for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
	//
	// giftList.add(new Gift(cursor.getString(t1), cursor.getString(t4),
	// cursor.getString(t2), cursor.getString(t3), cursor
	// .getString(t7), cursor.getString(t6), cursor
	// .getString(t5)));
	//
	// }
	//
	// return giftList;
	// }
	//
	// // public long addAnswerData(String a, String b, String c, String d) {
	// //
	// // ContentValues values = new ContentValues();
	// //
	// // Log.e("D", "D");
	// //
	// // values.put(KEY_MATCH_ID, a);
	// // values.put(KEY_QUES_ID, b);
	// // values.put(KEY_QUES, c);
	// // values.put(KEY_EXTRA, d);
	// //
	// // return ourdatabase.insert(DATABASE_TABLE_3, null, values);
	// //
	// // }
	// //
	// // public long addHistoryData(String a, String b, String c, String d) {
	// //
	// // ContentValues values = new ContentValues();
	// //
	// // Log.e("D", "D");
	// //
	// // values.put(KEY_YEAR, a);
	// // values.put(KEY_WIN, b);
	// // values.put(KEY_LOSE, c);
	// // values.put(KEY_SHOE, d);
	// //
	// // return ourdatabase.insert(DATABASE_TABLE_2, null, values);
	// //
	// // }
	// //
	// // public long addTrackId(String a) {
	// //
	// // ContentValues values = new ContentValues();
	// //
	// // Log.e("D", "D");
	// //
	// // values.put(KEY_ID_TRACK, a);
	// //
	// // return ourdatabase.insert(DATABASE_TABLE_4, null, values);
	// //
	// // }
	// //
	// // public String[] ViewTrackId() {
	// //
	// // String[] cloumns = new String[] { KEY_ID_TRACK };
	// // Cursor cursor = ourdatabase.query(DATABASE_TABLE_4, cloumns, null,
	// // null, null, null, null);
	// // String[] str = new String[cursor.getCount()];
	// // int i = 0;
	// //
	// // int name = cursor.getColumnIndex(KEY_ID_TRACK);
	// //
	// // for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
	// {
	// //
	// // str[i] = cursor.getString(name);
	// // i++;
	// //
	// // }
	// //
	// // return str;
	// // }
	// //
	// // public List<Fixture> getFixtureData(String round) {
	// //
	// // String[] cloumns = new String[] { KEY_DATE, KEY_TEAM_1, KEY_TEAM_2,
	// // KEY_TIME, KEY_VENUE, KEY_ROUND };
	// // Cursor cursor = ourdatabase.query(DATABASE_TABLE, cloumns, KEY_ROUND
	// // + "='" + round + "'", null, null, null, null);
	// //
	// // List<Fixture> fixtureList = new ArrayList<Fixture>();
	// // Fixture fixture;
	// //
	// // int dt = cursor.getColumnIndex(KEY_DATE);
	// // int t1 = cursor.getColumnIndex(KEY_TEAM_1);
	// // int t2 = cursor.getColumnIndex(KEY_TEAM_2);
	// // int tm = cursor.getColumnIndex(KEY_TIME);
	// // int vnu = cursor.getColumnIndex(KEY_VENUE);
	// //
	// // int rnd = cursor.getColumnIndex(KEY_ROUND);
	// //
	// // for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
	// {
	// //
	// // fixture = new Fixture(cursor.getString(dt), cursor.getString(t1),
	// // cursor.getString(t2), cursor.getString(tm),
	// // cursor.getString(vnu), cursor.getString(rnd));
	// // fixtureList.add(fixture);
	// //
	// // }
	// //
	// // return fixtureList;
	// //
	// // }
	// //
	// // public List<Today> getTodayFixtureData(String date) {
	// //
	// // String[] cloumns = new String[] { KEY_DATE, KEY_TEAM_1, KEY_TEAM_2,
	// // KEY_TIME, KEY_ID };
	// // Cursor cursor = ourdatabase.query(DATABASE_TABLE, cloumns, KEY_DATE
	// // + "='" + date + "'", null, null, null, null);
	// //
	// // List<Today> fixtureList = new ArrayList<Today>();
	// // Today fixture;
	// //
	// // int dt = cursor.getColumnIndex(KEY_DATE);
	// // int t1 = cursor.getColumnIndex(KEY_TEAM_1);
	// // int t2 = cursor.getColumnIndex(KEY_TEAM_2);
	// // int tm = cursor.getColumnIndex(KEY_TIME);
	// // int vnu = cursor.getColumnIndex(KEY_ID);
	// //
	// // Log.e("D", "D12");
	// //
	// // for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
	// {
	// // Log.e("D", "D1");
	// // fixture = new Today(cursor.getString(vnu), cursor.getString(t1),
	// // cursor.getString(t2), cursor.getString(dt),
	// // cursor.getString(tm));
	// // fixtureList.add(fixture);
	// //
	// // }
	// //
	// // return fixtureList;
	// //
	// // }
	// //
	// // public List<Today> getTodayFixtureDataTodayMatch(String id) {
	// //
	// // String[] cloumns = new String[] { KEY_DATE, KEY_TEAM_1, KEY_TEAM_2,
	// // KEY_TIME, KEY_ID };
	// // Cursor cursor = ourdatabase.query(DATABASE_TABLE, cloumns, KEY_ID
	// // + "='" + id + "'", null, null, null, null);
	// //
	// // List<Today> fixtureList = new ArrayList<Today>();
	// // Today fixture;
	// //
	// // int dt = cursor.getColumnIndex(KEY_DATE);
	// // int t1 = cursor.getColumnIndex(KEY_TEAM_1);
	// // int t2 = cursor.getColumnIndex(KEY_TEAM_2);
	// // int tm = cursor.getColumnIndex(KEY_TIME);
	// // int vnu = cursor.getColumnIndex(KEY_ID);
	// //
	// // Log.e("D", "D12");
	// //
	// // for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
	// {
	// // Log.e("D", "D1");
	// // fixture = new Today(cursor.getString(vnu), cursor.getString(t1),
	// // cursor.getString(t2), cursor.getString(dt),
	// // cursor.getString(tm));
	// // fixtureList.add(fixture);
	// //
	// // }
	// //
	// // return fixtureList;
	// //
	// // }
	// //
	// // public List<AnswerTrack> getAnswerData(String date) {
	// //
	// // String[] cloumns = new String[] { KEY_QUES_ID, KEY_QUES, KEY_EXTRA };
	// // Cursor cursor = ourdatabase.query(DATABASE_TABLE_3, cloumns,
	// // KEY_MATCH_ID + "='" + date + "'", null, null, null, null);
	// //
	// // List<AnswerTrack> fixtureList = new ArrayList<AnswerTrack>();
	// // AnswerTrack fixture;
	// //
	// // int dt = cursor.getColumnIndex(KEY_QUES_ID);
	// // int t1 = cursor.getColumnIndex(KEY_QUES);
	// // int t2 = cursor.getColumnIndex(KEY_EXTRA);
	// //
	// // Log.e("D", "D12");
	// //
	// // for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
	// {
	// // Log.e("D", "D1");
	// // fixture = new AnswerTrack(null, cursor.getString(dt),
	// // cursor.getString(t1), cursor.getString(t2));
	// // fixtureList.add(fixture);
	// //
	// // }
	// //
	// // return fixtureList;
	// //
	// // }
	// //
	// // public List<History> getHistoryData() {
	// //
	// // String[] cloumns = new String[] { KEY_YEAR, KEY_WIN, KEY_LOSE };
	// // Cursor cursor = ourdatabase.query(DATABASE_TABLE_2, cloumns, null,
	// // null, null, null, null);
	// //
	// // List<History> historyList = new ArrayList<History>();
	// // History history;
	// //
	// // int yr = cursor.getColumnIndex(KEY_YEAR);
	// // int wn = cursor.getColumnIndex(KEY_WIN);
	// // int ls = cursor.getColumnIndex(KEY_LOSE);
	// //
	// // for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
	// {
	// //
	// // history = new History(cursor.getString(yr), cursor.getString(wn),
	// // cursor.getString(ls), null, null);
	// // historyList.add(history);
	// //
	// // }
	// //
	// // return historyList;
	// //
	// // }
	// //
	// // public String getMatch(String id) {
	// //
	// // String[] cloumns = new String[] { KEY_TEAM_1, KEY_TEAM_2 };
	// // Cursor cursor = ourdatabase.query(DATABASE_TABLE, cloumns, KEY_ID
	// // + "='" + id + "'", null, null, null, null);
	// //
	// // List<Today> fixtureList = new ArrayList<Today>();
	// // Today fixture;
	// // String result = null;
	// //
	// // int t1 = cursor.getColumnIndex(KEY_TEAM_1);
	// // int t2 = cursor.getColumnIndex(KEY_TEAM_2);
	// //
	// // Log.e("D", "D12");
	// //
	// // for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
	// {
	// //
	// // result = cursor.getString(t1) + "   Vs   " + cursor.getString(t2);
	// // }
	// // return result;
	// // }
	// //
	// // /*
	// // * public long addApprovalId(int a) {
	// // *
	// // * ContentValues values = new ContentValues(); Log.e("a", a + "");
	// // * values.put(KEY_APPROVAL, a);
	// // *
	// // * Log.e("H", "H");
	// // *
	// // * return ourdatabase.insert(DATABASE_TABLE_2, null, values);
	// // *
	// // * }
	// // *
	// // * public long addItem(String item) {
	// // *
	// // * ContentValues values = new ContentValues(); Log.e("a", item);
	// // * values.put(KEY_CATEGORY, item);
	// // *
	// // * Log.e("H", "H");
	// // *
	// // * return ourdatabase.insert(DATABASE_TABLE_2, null, values);
	// // *
	// // * }
	// // *
	// // * public int[] ViewApproval() { String[] cloumns = new String[] {
	// // * KEY_APPROVAL }; Cursor cursor = ourdatabase.query(DATABASE_TABLE_2,
	// // * cloumns, null, null, null, null, null); int[] str = new
	// // * int[cursor.getCount()]; int i = 0;
	// // *
	// // * int catgry = cursor.getColumnIndex(KEY_APPROVAL);
	// // *
	// // * for (cursor.moveToFirst(); !cursor.isAfterLast();
	// cursor.moveToNext())
	// // {
	// // *
	// // * str[i] = cursor.getInt(catgry);
	// // *
	// // * i++; }
	// // *
	// // * return str; }
	// // *
	// // * public String[] ViewLeaveInformation() { String[] cloumns = new
	// // String[]
	// // * { KEY_NAME, KEY_FROM, KEY_TO, KEY_NDAY, KEY_REASON }; Cursor cursor
	// =
	// // * ourdatabase.query(DATABASE_TABLE, cloumns, null, null, null, null,
	// // * KEY_NAME + " ASC"); String[] str = new String[cursor.getCount()];
	// int i
	// // =
	// // * 0;
	// // *
	// // * int name = cursor.getColumnIndex(KEY_NAME); int from =
	// // * cursor.getColumnIndex(KEY_FROM); int to =
	// // cursor.getColumnIndex(KEY_TO);
	// // * int nday = cursor.getColumnIndex(KEY_NDAY); int reason =
	// // * cursor.getColumnIndex(KEY_REASON);
	// // *
	// // * for (cursor.moveToFirst(); !cursor.isAfterLast();
	// cursor.moveToNext())
	// // {
	// // *
	// // * str[i] = "Employee Name:  " + cursor.getString(name) + "\n" +
	// // * "Leave From:  " + cursor.getString(from) + "-" +
	// cursor.getString(to) +
	// // * "\n" + "Number of days:  " + cursor.getString(nday) + "\n" +
	// // "Reason:  "
	// // * + cursor.getString(reason) + "\n............................\n";
	// // *
	// // * i++;
	// // *
	// // * }
	// // *
	// // * return str; }
	// // */
	// public void deleteAll() {
	//
	// ourdatabase.delete(DATABASE_TABLE, null, null);
	//
	// }
	//
	// public void deleteUserProfile() {
	//
	// ourdatabase.delete(DATABASE_TABLE, null, null);
	//
	// }
	//
	// public void deleteMatch(String id) {
	//
	// ourdatabase.delete(DATABASE_TABLE_3, KEY_MATCH_ID + "='" + id + "'",
	// null);
	//
	// }

	public void deleteItem(String id, String id1, String id2) {

		ourdatabase.delete(DATABASE_TABLE_3, KEY_ITEM_POSTER_TAG + "='" + id
				+ "' and " + KEY_ITEM_POSTER_CAT + "='" + id1 + "' and "
				+ KEY_ITEM_POSTER_SUB_CAT + "='" + id2 + "'", null);
	}

	public void deleteFavItem(String id) {

		ourdatabase.delete(DATABASE_TABLE_4, KEY_FAV_ITEM_ID + "='" + id + "'",
				null);
	}
}
