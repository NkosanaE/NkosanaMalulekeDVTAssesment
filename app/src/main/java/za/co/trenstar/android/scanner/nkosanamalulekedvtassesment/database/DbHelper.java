package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper  extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "Locations";

    // Table columns
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String LAT = "lat";
    public static final String LON = "lon";

    // Database Information
    static final String DB_NAME = "WEATHER_LOCATIONS.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT UNIQUE NOT NULL, " + LAT + " Int, " + LON + " Int);";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
