package sebastiancorradi.altran.interactors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sebastiancorradi.altran.SQL.AltranDBHelper;
import sebastiancorradi.altran.Utils.Utils;
import sebastiancorradi.altran.model.Gnome;

/**
 * Created by Gregorio on 12/9/2017.
 */

public class DBInteractor {
    private AltranDBHelper dbHelper;

    private DBInteractor(Context context){
        dbHelper = AltranDBHelper.getInstance(context);
    }
    private static DBInteractor instance;

    public static DBInteractor getInstance(Context context){
        if (instance == null){
            instance = new DBInteractor(context);
        }
        return instance;
    }

    public long insert(Gnome gnome){
        if (gnomeExists(gnome.getId())){
            return -1;
        }
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AltranDBHelper.COLUMN_GNOME_ID, gnome.getId());
        values.put(AltranDBHelper.COLUMN_NAME, gnome.getName());
        values.put(AltranDBHelper.COLUMN_AGE, gnome.getAge());
        values.put(AltranDBHelper.COLUMN_HEIGHT, gnome.getHeight());
        values.put(AltranDBHelper.COLUMN_WEIGHT, gnome.getWeight());
        values.put(AltranDBHelper.COLUMN_HAIRCOLOR, gnome.getHair_color());
        values.put(AltranDBHelper.COLUMN_PROFESSIONS, Utils.join(", ", gnome.getProfessions()));
        values.put(AltranDBHelper.COLUMN_FRIENDS, Utils.join(", ", gnome.getFriends()));


// Insert the new row, returning the primary key value of the new row

        return dbHelper.insert(AltranDBHelper.TABLE_NAME, values);
    }

    public ArrayList<Gnome> getAll(){
        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                AltranDBHelper.COLUMN_GNOME_ID,
                AltranDBHelper.COLUMN_NAME,
                AltranDBHelper.COLUMN_AGE,
                AltranDBHelper.COLUMN_HEIGHT,
                AltranDBHelper.COLUMN_WEIGHT,
                AltranDBHelper.COLUMN_HAIRCOLOR,
                AltranDBHelper.COLUMN_PROFESSIONS,
                AltranDBHelper.COLUMN_FRIENDS,
        };
        
        String selection = null;
        String[] selectionArgs = null;

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                AltranDBHelper.COLUMN_NAME + " ASC";

        Cursor c = dbHelper.query(
                AltranDBHelper.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                sortOrder                                 // The sort order
        );

        ArrayList<Gnome> result = new ArrayList<Gnome>();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()){
                Gnome gnome = createFromCursor(c);
                result.add(gnome);
            }
        }
        return result;
    }

    public Gnome getGnome(int gnomeId){
        String[] projection = {
                AltranDBHelper.COLUMN_GNOME_ID,
                AltranDBHelper.COLUMN_NAME,
                AltranDBHelper.COLUMN_AGE,
                AltranDBHelper.COLUMN_HEIGHT,
                AltranDBHelper.COLUMN_WEIGHT,
                AltranDBHelper.COLUMN_HAIRCOLOR,
                AltranDBHelper.COLUMN_PROFESSIONS,
                AltranDBHelper.COLUMN_FRIENDS,
        };

        String selection = AltranDBHelper.COLUMN_GNOME_ID + " = ?";
        String[] selectionArgs = {String.valueOf(gnomeId)};

        String sortOrder =
                AltranDBHelper.COLUMN_NAME + " ASC";

        Cursor c = dbHelper.query(
                AltranDBHelper.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                sortOrder                                 // The sort order
        );
        Gnome gnome = null;
        ArrayList<Gnome> result = new ArrayList<Gnome>();
        if (c.moveToFirst()) {
                gnome = createFromCursor(c);
        }

        return gnome;
    }

    public boolean gnomeExists(int gnomeId){
        String[] projection = {
                AltranDBHelper.COLUMN_GNOME_ID,
        };

        String selection = AltranDBHelper.COLUMN_GNOME_ID + " = ?";
        String[] selectionArgs = {String.valueOf(gnomeId)};

        String sortOrder =
                AltranDBHelper.COLUMN_NAME + " ASC";

        Cursor c = dbHelper.query(
                AltranDBHelper.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                sortOrder                                 // The sort order
        );
        Gnome gnome = null;
        ArrayList<Gnome> result = new ArrayList<Gnome>();
        if (c.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }
    public void insertAll(ArrayList<Gnome> gnomes){
        for (int i = 0; i < gnomes.size(); i++){
            insert(gnomes.get(i));
        }

    }

    private Gnome createFromCursor(Cursor c){
        Gnome result = new Gnome();
        result.setId(c.getInt(c.getColumnIndex(AltranDBHelper.COLUMN_GNOME_ID)));
        result.setName(c.getString(c.getColumnIndex(AltranDBHelper.COLUMN_NAME)));
        result.setAge(c.getInt(c.getColumnIndex(AltranDBHelper.COLUMN_AGE)));
        result.setHeight(c.getFloat(c.getColumnIndex(AltranDBHelper.COLUMN_HEIGHT)));
        result.setWeight(c.getFloat(c.getColumnIndex(AltranDBHelper.COLUMN_WEIGHT)));
        result.setHair_color(c.getString(c.getColumnIndex(AltranDBHelper.COLUMN_HAIRCOLOR)));
        ArrayList<String> professions = new ArrayList<String>();
        String[] list = c.getString(c.getColumnIndex(AltranDBHelper.COLUMN_PROFESSIONS)).split(AltranDBHelper.COMMA_SEP);
        for (String s: list){
            professions.add(s);
        }
        result.setProfessions(professions);

        ArrayList<String> friends = new ArrayList<String>();
        String[] array = c.getString(c.getColumnIndex(AltranDBHelper.COLUMN_FRIENDS)).split(AltranDBHelper.COMMA_SEP);
        for (String s: array){
            friends.add(s);
        }
        result.setFriends(friends);
        return result;
    }
}
