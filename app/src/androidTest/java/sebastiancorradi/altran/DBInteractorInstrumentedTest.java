package sebastiancorradi.altran;

import android.content.Context;
import android.database.DatabaseUtils;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import sebastiancorradi.altran.SQL.AltranDBHelper;
import sebastiancorradi.altran.interactors.DBInteractor;
import sebastiancorradi.altran.model.Gnome;
import sebastiancorradi.altran.model.InjectableGnomeCreator;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Gregorio on 1/2/2018.
 */

public class DBInteractorInstrumentedTest {
    private DBInteractor interactor;
    Context appContext;

    @Before
    public void setUp() {
        appContext = InstrumentationRegistry.getTargetContext();
        interactor = DBInteractor.getInstance(appContext, new InjectableGnomeCreator());
    }

    @Test
    public void testInteractorCreation() throws Exception {
        assertNotNull("El interactor no se creo", interactor);
    }

    @Test
    public void testInteractor() throws Exception {
        int numRows = (int) interactor.getGnomesCount();

        Gnome gnome = new Gnome();
        gnome.setId(new Integer(numRows + 1));
        gnome.setName("Sebastian");
        gnome.setAge(37);
        gnome.setHeight(179);
        gnome.setWeight(90);
        gnome.setHair_color("Pink");
        gnome.setThumbnail("someThumbnail");
        gnome.setFriends(null);
        gnome.setProfessions(null);

        interactor.insert(gnome);
        assertNotNull("problemas en la insercion o recuperacion de un gnomo", interactor.getGnome(numRows + 1));
    }
}
