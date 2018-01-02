package sebastiancorradi.altran;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import sebastiancorradi.altran.SQL.AltranDBHelper;
import sebastiancorradi.altran.Utils.Utils;
import sebastiancorradi.altran.model.Gnome;
import sebastiancorradi.altran.model.InjectableGnomeCreator;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DBHelperInstrumentedTest {
    private AltranDBHelper database;
    Context appContext;

    @Before
    public void setUp() {
        appContext = InstrumentationRegistry.getTargetContext();
        database = AltranDBHelper.getInstance(appContext);

    }

    @Test
    public void testDbCreation() throws Exception {
        // Context of the app under test.
        assertNotNull("La base de datos no se creo", database);
    }

    @Test
    public void testDbInsert() throws Exception {

        long numRows = DatabaseUtils.queryNumEntries(database.getReadableDatabase(), AltranDBHelper.TABLE_NAME);

        ContentValues values = new ContentValues();
        values.put(AltranDBHelper.COLUMN_GNOME_ID, numRows + 1);


        long inserted = database.insert(AltranDBHelper.TABLE_NAME, values);
        long newNumRows = DatabaseUtils.queryNumEntries(database.getReadableDatabase(), AltranDBHelper.TABLE_NAME);

        assertEquals("insert not working properly, no records were inserted", 1, newNumRows - numRows);
        assertEquals("insert not working properly, wrong id", numRows + 1, inserted);

    }

    @Test
    public void testDbSelect() throws Exception {

        long numRows = DatabaseUtils.queryNumEntries(database.getReadableDatabase(), AltranDBHelper.TABLE_NAME);

        ContentValues values = new ContentValues();
        values.put(AltranDBHelper.COLUMN_GNOME_ID, numRows + 1);
        values.put(AltranDBHelper.COLUMN_NAME, "Sebastian Corradi");
        values.put(AltranDBHelper.COLUMN_THUMBNAIL, "thumbnail.jpg");
        values.put(AltranDBHelper.COLUMN_AGE, 37);
        values.put(AltranDBHelper.COLUMN_HEIGHT, 187);
        values.put(AltranDBHelper.COLUMN_WEIGHT, 90.02);
        values.put(AltranDBHelper.COLUMN_HAIRCOLOR, "Pink");
        values.put(AltranDBHelper.COLUMN_PROFESSIONS, "Developer, Musician");
        values.put(AltranDBHelper.COLUMN_FRIENDS, "Mati, Nacho");

        long newId = database.insert(AltranDBHelper.TABLE_NAME, values);

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

        String selection = AltranDBHelper.COLUMN_GNOME_ID + " = ?";
        String[] selectionArgs = {String.valueOf(newId)};

        String sortOrder = AltranDBHelper.COLUMN_NAME + " ASC";

        Cursor c = database.query(
                AltranDBHelper.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                sortOrder                                 // The sort order
        );
        Gnome gnome = null;
        if (c.moveToFirst()) {
            InjectableGnomeCreator creator = new InjectableGnomeCreator();
            gnome = creator.createFromCursor(c, database);
        }

        assertEquals("Gnome name not porperly created", gnome.getName(), "Sebastian Corradi");
        assertEquals("Gnome age not porperly created", gnome.getAge(), 37);
        String profesions =  Utils.join(", ", gnome.getProfessions());
        assertEquals("Gnome profesions", profesions, "Developer, Musician");

    }
}
