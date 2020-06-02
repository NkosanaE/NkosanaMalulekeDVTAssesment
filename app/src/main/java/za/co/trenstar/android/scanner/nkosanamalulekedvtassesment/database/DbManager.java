package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DbManager {

    private DbHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DbManager(Context c) {
        context = c;
    }

    public DbManager open() throws SQLException {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertLocation(String name,String lat, String lon) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DbHelper.NAME, name);
        contentValue.put(DbHelper.LAT, lat);
        contentValue.put(DbHelper.LON, lon);
        database.insert(DbHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor getAllLocations() {
        String[] columns = new String[] { DbHelper._ID, DbHelper.NAME};
        Cursor cursor = database.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public void delete(long _id) {
        database.delete(DbHelper.TABLE_NAME, DbHelper._ID + "=" + _id, null);
    }
}
