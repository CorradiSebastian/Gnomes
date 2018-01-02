package sebastiancorradi.altran.model;

import android.database.Cursor;

import sebastiancorradi.altran.SQL.AltranDBFiledsHelper;

/**
 * Created by Gregorio on 1/2/2018.
 */

public interface GnomeCreatorInterface {
    public Gnome createFromCursor(Cursor c, AltranDBFiledsHelper fieldsHelper);
}
