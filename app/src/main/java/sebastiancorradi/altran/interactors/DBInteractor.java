package sebastiancorradi.altran.interactors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import sebastiancorradi.altran.SQL.AltranDBHelper;
import sebastiancorradi.altran.Utils.Utils;
import sebastiancorradi.altran.model.Gnome;
import sebastiancorradi.altran.model.GnomeCreatorInterface;
import sebastiancorradi.altran.repository.GnomeRepository;

/**
 * Created by Gregorio on 12/9/2017.
 */

public class DBInteractor {
    private AltranDBHelper dbHelper;
    private boolean working = false;
    private GnomeRepository repository;
    private GnomeCreatorInterface gnomeCreator;

    private DBInteractor(Context context, GnomeCreatorInterface gnomeCreator){
        dbHelper = AltranDBHelper.getInstance(context);
        repository = GnomeRepository.getInstance();
        this.gnomeCreator = gnomeCreator;
    }
    private static DBInteractor instance;
    String[] projection = {
            AltranDBHelper.COLUMN_GNOME_ID,
            AltranDBHelper.COLUMN_NAME,
            AltranDBHelper.COLUMN_THUMBNAIL,
            AltranDBHelper.COLUMN_AGE,
            AltranDBHelper.COLUMN_HEIGHT,
            AltranDBHelper.COLUMN_WEIGHT,
            AltranDBHelper.COLUMN_HAIRCOLOR,
            AltranDBHelper.COLUMN_PROFESSIONS,
            AltranDBHelper.COLUMN_FRIENDS
    };

    public static DBInteractor getInstance(Context context, GnomeCreatorInterface gnomeCreator){
        if (instance == null){
            instance = new DBInteractor(context, gnomeCreator);
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
        values.put(AltranDBHelper.COLUMN_THUMBNAIL, gnome.getThumbnail());
        values.put(AltranDBHelper.COLUMN_AGE, gnome.getAge());
        values.put(AltranDBHelper.COLUMN_HEIGHT, gnome.getHeight());
        values.put(AltranDBHelper.COLUMN_WEIGHT, gnome.getWeight());
        values.put(AltranDBHelper.COLUMN_HAIRCOLOR, gnome.getHair_color());
        values.put(AltranDBHelper.COLUMN_PROFESSIONS, Utils.join(", ", gnome.getProfessions()));
        values.put(AltranDBHelper.COLUMN_FRIENDS, Utils.join(", ", gnome.getFriends()));


// Insert the new row, returning the primary key value of the new row

        return dbHelper.insert(AltranDBHelper.TABLE_NAME, values);
    }
    public long insertOrThrow(Gnome gnome){

        ContentValues values = new ContentValues();
        values.put(AltranDBHelper.COLUMN_GNOME_ID, gnome.getId());
        values.put(AltranDBHelper.COLUMN_NAME, gnome.getName());
        values.put(AltranDBHelper.COLUMN_THUMBNAIL, gnome.getThumbnail());
        values.put(AltranDBHelper.COLUMN_AGE, gnome.getAge());
        values.put(AltranDBHelper.COLUMN_HEIGHT, gnome.getHeight());
        values.put(AltranDBHelper.COLUMN_WEIGHT, gnome.getWeight());
        values.put(AltranDBHelper.COLUMN_HAIRCOLOR, gnome.getHair_color());
        values.put(AltranDBHelper.COLUMN_PROFESSIONS, Utils.join(", ", gnome.getProfessions()));
        values.put(AltranDBHelper.COLUMN_FRIENDS, Utils.join(", ", gnome.getFriends()));

// Insert the new row, returning the primary key value of the new row

        return dbHelper.insertOrThrow(AltranDBHelper.TABLE_NAME, values);
    }

    public ArrayList<Gnome> getAll(){
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
                c.moveToNext();
            }
        }
        c.close();
        return result;
    }

    public Gnome getGnome(int gnomeId){

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
        if (c.moveToFirst()) {
                gnome = createFromCursor(c);
        }
        c.close();
        return gnome;
    }

    public boolean gnomeExists(int gnomeId){
        String[] customProjection = {
                AltranDBHelper.COLUMN_GNOME_ID,
        };

        String selection = AltranDBHelper.COLUMN_GNOME_ID + " = ?";
        String[] selectionArgs = {String.valueOf(gnomeId)};

        String sortOrder =
                AltranDBHelper.COLUMN_NAME + " ASC";

        Cursor c = dbHelper.query(
                AltranDBHelper.TABLE_NAME,                     // The table to query
                customProjection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                sortOrder                                 // The sort order
        );
        Gnome gnome = null;
        ArrayList<Gnome> result = new ArrayList<Gnome>();
        if (c.getCount() > 0) {
            c.close();
            return true;
        } else {
            c.close();
            return false;
        }

    }
    public void insertAll(ArrayList<Gnome> gnomes){
        working = true;
        for (int i = 0; i < gnomes.size(); i++){
                insertOrThrow(gnomes.get(i));
        }
        working = false;
    }

    public void insertAll(String jsonData){
        repository.setData(jsonData);
        final ArrayList<Gnome> gnomes = repository.getGnomeList();
        if (gnomeCount() == 0) {
            new Thread(new Runnable() {
                public void run(){
                    insertAll(gnomes);
                }
            }).start();

        }
    }

    public ArrayList<Gnome> getGnomesByHairColor(String hairColor){
        String columns[] = {AltranDBHelper.COLUMN_HAIRCOLOR};
        String values[] = {hairColor};
        return getGnomesByColumn(columns, values);
    }

    public ArrayList<Gnome> getGnomesByColumn(String[] columns, String[] selectionArgs){
        String selection = "";
        for (String column: columns){
            selection = column + " = ? AND ";
        }
        selection = selection.substring(0, selection.lastIndexOf("AND "));

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
                result.add(createFromCursor(c));
                c.moveToNext();
            }
        }
        c.close();
        return result;
    }

    public Gnome createFromCursor(Cursor c){
        Gnome result = gnomeCreator.createFromCursor(c, dbHelper);

        return result;
    }
    public int gnomeCount(){
        int result = 0;
        String fieldName = "CANTIDAD";
        String[] customProjection = {
                AltranDBHelper.COLUMN_GNOME_ID,
        };

        String proj[] = new String[1];
        proj[0] = "Count (*) as " + fieldName;


        String sortOrder =
                AltranDBHelper.COLUMN_NAME + " ASC";

        Cursor c = dbHelper.query(
                AltranDBHelper.TABLE_NAME,                     // The table to query
                proj,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null                                 // The sort order
        );

        if (c.getCount() > 0){
            c.moveToFirst();
            result =  Integer.valueOf(c.getString(c.getColumnIndex(fieldName)));
        } else {
            result = 0;
        }
        c.close();
        return result;
    }


    private  float getCalculatedFloat(String field, String function){
        float result = 0;
        String fieldName = "FIELD";

        String proj[] = new String[1];
        proj[0] = function + "(" + field + ") as " + fieldName;

        Cursor c = dbHelper.query(
                AltranDBHelper.TABLE_NAME,                     // The table to query
                proj,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null                                 // The sort order
        );

        if (c.getCount() > 0){
            c.moveToFirst();
            result =  Float.valueOf(c.getString(c.getColumnIndex(fieldName)));
        } else {
            result = 0;
        }
        c.close();
        return result;
    }

    public float getMaxHeight(){
        return getCalculatedFloat("height", "max");
    }



    public float getMinHeight(){
        return getCalculatedFloat("height", "min");
    }
    public float getMaxWeight(){
        return getCalculatedFloat("weight", "max");
    }

    public float getMinWeight(){
        return getCalculatedFloat("weight", "min");
    }

    public boolean isDataBaseReady(){
        return  (!working) && (gnomeCount() > 0);
    }

    public void prepareData(){
        working = true;
        repository.setData(getAll());
        working = false;
    }
}
