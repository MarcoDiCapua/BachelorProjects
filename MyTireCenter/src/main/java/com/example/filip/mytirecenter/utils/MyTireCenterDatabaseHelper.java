package com.example.filip.mytirecenter.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.filip.mytirecenter.model.AppData;
import com.example.filip.mytirecenter.model.Tire;
import com.example.filip.mytirecenter.model.TireCenter;

import java.util.LinkedList;
import java.util.List;

/**
 * This class provides methods to access to the application database.
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class MyTireCenterDatabaseHelper extends SQLiteOpenHelper {

    // The Tag for the Log
    private static final String TAG = MyTireCenterDatabaseHelper.class.getSimpleName();

    // Database version
    private static final int DATABASE_VERSION = 7;

    // Database name
    private static final String DATABASE_NAME = "tireRepairer.db";

    // Table tire centers name
    private static final String TABLE_TIRE_CENTERS_NAME = "tireRepairer";

    // Table tires name
    private static final String TABLE_TIRES_NAME = "tires";

    // table warehouse name
    private static final String TABLE_WAREHOUSE_NAME = "warehouse";

    //Singleton instance for the database
    private static MyTireCenterDatabaseHelper database = null;

    // constructor that call the superclass
    private MyTireCenterDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Returns the MyTireCenterDatabaseHelper singleton instance
     *
     * @param context The Context
     * @return The MyTireCenterDatabaseHelper singleton
     */
    public static MyTireCenterDatabaseHelper getMyTireCenterDatabase(Context context) {
        if (database == null) {
            return new MyTireCenterDatabaseHelper(context);
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                + TABLE_TIRE_CENTERS_NAME + " ("
                + TireCenter.Keys.ID + " integer primary key autoincrement, "
                + TireCenter.Keys.NAME + " TEXT not null, "
                + TireCenter.Keys.PROVINCE_CODE + " TEXT not null, "
                + TireCenter.Keys.ADDRESS + " TEXT, "
                + TireCenter.Keys.TELEPHONE_NUMBER + " TEXT, "
                + TireCenter.Keys.LATITUDE + " REAL, "
                + TireCenter.Keys.LONGITUDE + " REAL"
                + ");");
        Log.d(TAG, "onCreate(): create table tire centers");

        List<TireCenter> listTireCenters = AppData.createTireCentersData();
        for (TireCenter tireCenter : listTireCenters) {
            ContentValues values = new ContentValues();
            values.put(TireCenter.Keys.ID, tireCenter.getId());
            values.put(TireCenter.Keys.NAME, tireCenter.getName());
            values.put(TireCenter.Keys.PROVINCE_CODE, tireCenter.getProvinceCode());
            values.put(TireCenter.Keys.ADDRESS, tireCenter.getAddress());
            values.put(TireCenter.Keys.TELEPHONE_NUMBER, tireCenter.getTelephoneNumber());
            values.put(TireCenter.Keys.LATITUDE, tireCenter.getLatitude());
            values.put(TireCenter.Keys.LONGITUDE, tireCenter.getLongitude());
            db.insert(TABLE_TIRE_CENTERS_NAME, TireCenter.Keys.ADDRESS, values);
        }

        db.execSQL("create table "
                + TABLE_TIRES_NAME + " ("
                + Tire.Keys.ID + " integer primary key autoincrement, "
                + Tire.Keys.BRAND + " TEXT not null, "
                + Tire.Keys.MODEL + " TEXT not null, "
                + Tire.Keys.TYPE + " TEXT not null, "
                + Tire.Keys.SECTION_WIDTH + " integer not null, "
                + Tire.Keys.ASPECT_RATIO + " integer not null,"
                + Tire.Keys.RIM_DIAMETER + " integer not null"
                + ");");
        Log.d(TAG, "onCreate(): create table tires");

        List<Tire> listTires = AppData.createTiresData();
        for (Tire tire : listTires) {
            ContentValues values = new ContentValues();
            values.put(TireCenter.Keys.ID, tire.getId());
            values.put(Tire.Keys.BRAND, tire.getBrand());
            values.put(Tire.Keys.MODEL, tire.getModel());
            values.put(Tire.Keys.TYPE, tire.getType());
            values.put(Tire.Keys.SECTION_WIDTH, tire.getSectionWidth());
            values.put(Tire.Keys.ASPECT_RATIO, tire.getAspectRatio());
            values.put(Tire.Keys.RIM_DIAMETER, tire.getRimDiameter());
            db.insert(TABLE_TIRES_NAME, Tire.Keys.ID, values);
        }

        db.execSQL("create table "
                + TABLE_WAREHOUSE_NAME + " ("
                + "id_tire_center integer not null, "
                + "id_tire integer not null,"
                + Tire.Keys.PRICE + " REAL not null,"
                + "CONSTRAINT warehouse_pk PRIMARY KEY (id_tire_center, id_tire), "
                + "CONSTRAINT tire_center_fk FOREIGN KEY (id_tire_center) REFERENCES "
                + TABLE_TIRE_CENTERS_NAME + " ( " + TireCenter.Keys.ID + ") MATCH SIMPLE "
                + "ON UPDATE CASCADE ON DELETE CASCADE, "
                + "CONSTRAINT tire_fk FOREIGN KEY (id_tire) REFERENCES "
                + TABLE_TIRES_NAME + " ( " + Tire.Keys.ID + ") MATCH SIMPLE "
                + "ON UPDATE CASCADE ON DELETE CASCADE"
                + ");");
        Log.d(TAG, "onCreate(): create table warehouse");

        List<ContentValues> listWarehouse = AppData.createWarehouseData();
        for (ContentValues values : listWarehouse) {
            db.insert(TABLE_WAREHOUSE_NAME, "id_tire_center", values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WAREHOUSE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIRE_CENTERS_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIRES_NAME);
        this.onCreate(db);
        Log.d(TAG, "onUpgrade(): created fresh tables");
    }

    /**
     * Get the list of all the tire centers saved in the application database
     *
     * @return The List of TireCenter
     */
    public List<TireCenter> getAllTireCenters() {
        List<TireCenter> tireCenterList = new LinkedList<>();
        String query = "SELECT  * FROM " + TABLE_TIRE_CENTERS_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        TireCenter tireCenter;
        if (cursor.moveToFirst()) {
            do {
                tireCenter = TireCenter.Builder.create(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
                        .withAddress(cursor.getString(3))
                        .withTelephoneNumber(cursor.getString(4))
                        .withLocation(cursor.getFloat(5), cursor.getFloat(6))
                        .build();
                tireCenterList.add(tireCenter);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        Log.d(TAG, "getAllTireCenter() invoked");
        return tireCenterList;
    }

    /**
     * Get the list of all the tire centers with a name that contains the given {@link String}
     *
     * @param name The name of the TireCenter
     * @return The List of TireCenter
     */
    public List<TireCenter> searchTireCenter(String name) {
        List<TireCenter> tireCenterList = new LinkedList<>();
        String query = "SELECT * FROM " + TABLE_TIRE_CENTERS_NAME + " WHERE lower(" + TireCenter.Keys.NAME + ") LIKE '%" + name + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        TireCenter tireCenter;
        if (cursor.moveToFirst()) {
            do {
                tireCenter = TireCenter.Builder.create(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
                        .withAddress(cursor.getString(3))
                        .withTelephoneNumber(cursor.getString(4))
                        .withLocation(cursor.getFloat(5), cursor.getFloat(6))
                        .build();
                tireCenterList.add(tireCenter);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        Log.d(TAG, "searchTireCenter(String name) invoked");
        return tireCenterList;
    }

    /**
     * Get the list of all the tire of the {@link TireCenter} with the given id
     *
     * @param idTireCenter The id of the TireCenter
     * @return The List of Tire
     */
    public List<Tire> searchTires(int idTireCenter) {
        List<Tire> tireList = new LinkedList<>();
        String query = "SELECT " + TABLE_TIRES_NAME + "." + Tire.Keys.ID + ", "
                + TABLE_TIRES_NAME + "." + Tire.Keys.BRAND + ", "
                + TABLE_TIRES_NAME + "." + Tire.Keys.MODEL + ", "
                + TABLE_TIRES_NAME + "." + Tire.Keys.TYPE + ", "
                + TABLE_TIRES_NAME + "." + Tire.Keys.ASPECT_RATIO + ", "
                + TABLE_TIRES_NAME + "." + Tire.Keys.SECTION_WIDTH + ", "
                + TABLE_TIRES_NAME + "." + Tire.Keys.RIM_DIAMETER + ", "
                + TABLE_WAREHOUSE_NAME + "." + Tire.Keys.PRICE
                + " FROM " + TABLE_TIRES_NAME + " JOIN " + TABLE_WAREHOUSE_NAME
                + " ON " + TABLE_TIRES_NAME + "." + Tire.Keys.ID + " = "
                + TABLE_WAREHOUSE_NAME + ".id_tire "
                + "WHERE " + TABLE_WAREHOUSE_NAME + ".id_tire_center = " + idTireCenter;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Tire tire;
        if (cursor.moveToFirst()) {
            do {
                tire = Tire.Builder.create(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6),
                        cursor.getFloat(7))
                        .build();
                tireList.add(tire);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        Log.d(TAG, "searchTires(int id) invoked");
        return tireList;
    }
}

