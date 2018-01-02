package sebastiancorradi.altran.model;

import android.database.Cursor;

import java.util.ArrayList;

import sebastiancorradi.altran.SQL.AltranDBFiledsHelper;
import sebastiancorradi.altran.SQL.AltranDBHelper;

/**
 * Created by Gregorio on 1/2/2018.
 */

public class InjectableGnomeCreator implements GnomeCreatorInterface {

    public InjectableGnomeCreator(){}

    public Gnome createFromCursor(Cursor c, AltranDBFiledsHelper fieldsHelper){
        Gnome result = new Gnome();
        result.setId(c.getInt(c.getColumnIndex(fieldsHelper.getColumnGnomeId())));
        result.setName(c.getString(c.getColumnIndex(fieldsHelper.getColumnName())));
        result.setThumbnail(c.getString(c.getColumnIndex(fieldsHelper.getColumnThumbnail())));
        result.setAge(c.getInt(c.getColumnIndex(fieldsHelper.getColumnAge())));
        result.setHeight(c.getFloat(c.getColumnIndex(fieldsHelper.getColumnHeight())));
        result.setWeight(c.getFloat(c.getColumnIndex(fieldsHelper.getColumnWeight())));
        result.setHair_color(c.getString(c.getColumnIndex(fieldsHelper.getColumnHaircolor())));
        ArrayList<String> professions = new ArrayList<String>();
        String[] list = c.getString(c.getColumnIndex(fieldsHelper.getColumnProfessions())).split(AltranDBHelper.COMMA_SEP);
        for (String s: list){
            professions.add(s);
        }
        result.setProfessions(professions);

        ArrayList<String> friends = new ArrayList<String>();
        String[] array = c.getString(c.getColumnIndex(fieldsHelper.getColumnFriends())).split(AltranDBHelper.COMMA_SEP);
        for (String s: array){
            friends.add(s);
        }
        result.setFriends(friends);
        return result;
    }
}
