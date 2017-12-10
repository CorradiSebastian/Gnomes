package sebastiancorradi.altran.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gregorio on 12/9/2017.
 */

public class AltranDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "altran";
    public static final String TABLE_ID = "id";
    public static final String COLUMN_GNOME_ID = "gnome_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_HAIRCOLOR = "haircolor";
    public static final String COLUMN_PROFESSIONS = "professions";
    public static final String COLUMN_FRIENDS = "friends";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    public static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    TABLE_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_GNOME_ID + INT_TYPE + COMMA_SEP +
                    COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    COLUMN_AGE + INT_TYPE + COMMA_SEP +
                    COLUMN_HEIGHT + TEXT_TYPE + COMMA_SEP +
                    COLUMN_WEIGHT + TEXT_TYPE + COMMA_SEP +
                    COLUMN_HAIRCOLOR + TEXT_TYPE + COMMA_SEP +
                    COLUMN_PROFESSIONS + TEXT_TYPE + COMMA_SEP +
                    COLUMN_FRIENDS + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "altran.db";

    private static AltranDBHelper instance;

    private AltranDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static AltranDBHelper getInstance(Context context){
        if (instance == null){
            instance = new AltranDBHelper(context);
        }
        return instance;
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insert(String tableName, ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(tableName, null, values);
    }

    public Cursor query(String tableName, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        SQLiteDatabase db = getWritableDatabase();
        return db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
    }


}
