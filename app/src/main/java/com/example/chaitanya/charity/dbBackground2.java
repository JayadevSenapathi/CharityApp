package com.example.chaitanya.charity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbBackground2 extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "dd2";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME = "vit";

	public static final String NAME = "name";
	public static final String PHONE = "phone";
	public static final String ADDRESS = "address";
	public static final String REASON = "reason";
	public static final String AMOUNT = "amount";

	public static final String CREATE_TABLE = "create table vit(phone varchar(10))";

	public dbBackground2(android.content.Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}
	/*
	public long insss(SQLiteDatabase sd){
		ContentValues cv= new ContentValues();
		cv.put(NAME,"Abhiram");
		cv.put(PHONE,"9629771628");
		cv.put(ADDRESS,"Vellore");
		cv.put(REASON,"Reason");
		cv.put(AMOUNT,"77777");
		long id2= sd.insert(TABLE_NAME, null, cv);
		return id2;
	}

	public long insss2(SQLiteDatabase sd){
		ContentValues cv= new ContentValues();
		cv.put(NAME,"OrgName");
		cv.put(PHONE,"99999");
		cv.put(ADDRESS,"Vellore");
		cv.put(REASON,"Treatment");
		cv.put(AMOUNT,"1000");
		long id2= sd.insert(TABLE_NAME, null, cv);
		return id2;
	}

	public long insss3(SQLiteDatabase sd){
		ContentValues cv= new ContentValues();
		cv.put(NAME,"vit");
		cv.put(PHONE,"99999");
		cv.put(ADDRESS,"Vellore");
		cv.put(REASON,"Treatment");
		cv.put(AMOUNT,"1999");
		long id2= sd.insert(TABLE_NAME, null, cv);
		return id2;
	}
	*/
	public long insert_Data(SQLiteDatabase sd, String p) {
		ContentValues cv = new ContentValues();
		cv.put(PHONE, p);
	 	long id = sd.insert(TABLE_NAME, null, cv);
	 	return id;
	}

	public String get_All_Data(SQLiteDatabase db) {
		Cursor cr = db.rawQuery("Select * from vit ", null);
		StringBuffer sb = new StringBuffer();
		while(cr.moveToNext()) {
			String un = cr.getString(0);
			sb.append(un + ",");
		}
		return sb.toString();
	}

	public String getDetails(SQLiteDatabase db, String n){
		Cursor cr = db.rawQuery("Select * from vit where name='" + n + "' ;", null);
		StringBuffer sb = new StringBuffer();
		while(cr.moveToNext()) {
			String sn = cr.getString(0);
			String sp = cr.getString(1);
			String sa = cr.getString(2);
			String sr = cr.getString(3);
			String sam = cr.getString(4);
			sb.append("Name:" + sn + "\n\n Phone:" + sp + "\n\n Address:" + sa + "\n\n Reason:" + sr + "\n\n Amount:" + sam + "," + sp);
		}
		return sb.toString();
	}

	public String getPhone(SQLiteDatabase db, String n){
		String sn = " ";
		Cursor cr = db.rawQuery("Select phone from vit where name='" + n + "' ;", null);
		StringBuffer sb = new StringBuffer();
		while(cr.moveToNext()) {
			sn = cr.getString(0);
			sb.append(sn);
		}
		return sn;
	}
}