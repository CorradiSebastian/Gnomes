package sebastiancorradi.altran;

import android.content.Context;

import com.google.gson.JsonSyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.InstanceOf;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import sebastiancorradi.altran.interactors.DBInteractor;
import sebastiancorradi.altran.interactors.parsers.GnomesParser;
import sebastiancorradi.altran.model.Brastlewark;
import sebastiancorradi.altran.model.Gnome;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class JsonGnomeParserUnitTest {

    @Mock
    Context mMockContext;

    @Test
    public void TestParseGnome1() throws Exception {
        String stringGnome = "{\n" +
                "\t\t\"id\": 0,\n" +
                "\t\t\"name\": \"Tobus Quickwhistle\",\n" +
                "\t\t\"thumbnail\": \"http://www.publicdomainpictures.net/image.jpg\",\n" +
                "\t\t\"age\": 306,\n" +
                "\t\t\"weight\": 39.065952,\n" +
                "\t\t\"height\": 107.75835,\n" +
                "\t\t\"hair_color\": \"Pink\",\n" +
                "\t\t\"professions\": [\"Metalworker\", \"Woodcarver\"],\n" +
                "\t\t\"friends\": [\"Cogwitz Chillwidget\", \"Tinadette Chillbuster\"]\n" +
                "\t}";
        GnomesParser parser = GnomesParser.getInstance();
        Gnome gnome = parser.parseGnome(stringGnome);

        assertNotNull("Error parseando un Gnomo", gnome);
        assertEquals("Error parseando nombre del gnomo", "Tobus Quickwhistle", gnome.getName());
        assertEquals("Error parseando Edad", 306, gnome.getAge());
        assertEquals(39.065952, gnome.getWeight(), 0.01);
        assertEquals(107.75, gnome.getHeight(), 0.01);
        assertEquals("Error parseando nombre del gnomo", "Tobus Quickwhistle", gnome.getName());
        assertNotNull("Error parseando vector de profesiones", gnome.getProfessions());
        List<String> professions = new ArrayList<String>();
        professions.add("Metalworker");
        professions.add("Woodcarver");
        assertEquals("Error parseando profesiones", professions, gnome.getProfessions());

        List<String> friends = new ArrayList<String>();
        friends.add("Cogwitz Chillwidget");
        friends.add("Tinadette Chillbuster");
        assertEquals("Error parseando amigos", friends, gnome.getFriends());

    }

    @Test
    public void TestParseGnome2() throws Exception {
        String stringGnome = "{\n" +
                "\t\t\"id\": 0,\n" +
                "\t\t\"name\": \"Tobus Quickwhistle\",\n" +
                "\t\t\"thumbnail\": \"http://www.publicdomainpictures.net/image.jpg\",\n" +
                "\t\t\"age\": 0,\n" +
                "\t\t\"weight\": -39.06,\n" +
                "\t\t\"height\": -107.75835,\n" +
                "\t\t\"hair_color\": \"Pink\",\n" +
                "\t\t\"professions\": [],\n" +
                "\t\t\"friends\": []\n" +
                "\t}";
        GnomesParser parser = GnomesParser.getInstance();
        Gnome gnome = parser.parseGnome(stringGnome);

        assertNotNull("Error parseando un Gnomo", gnome);
        assertEquals("Error parseando nombre del gnomo", "Tobus Quickwhistle", gnome.getName());
        assertEquals("Error parseando Edad", 0, gnome.getAge());
        assertEquals(-39.065952, gnome.getWeight(), 0.02);
        assertEquals(-107.75, gnome.getHeight(), 0.02);
        assertEquals("Error parseando nombre del gnomo", "Tobus Quickwhistle", gnome.getName());
        assertNotNull("Error parseando vector de profesiones", gnome.getProfessions());
        List<String> professions = new ArrayList<String>();
        assertEquals("Error parseando profesiones con longitud 0", professions, gnome.getProfessions());

        List<String> friends = new ArrayList<String>();
        assertEquals("Error parseando amigos con longitud 0", friends, gnome.getFriends());

    }

    @Test
    public void TestParseGnome3() throws Exception {
        try {
            String stringGnome = "{\n" +
                    "\t\t\"id\":\n" +
                    "\t\t\"name\": \"Tobus Quickwhistle\",\n" +
                    "\t\t\"thumbnail\": \"http://www.publicdomainpictures.net/image.jpg\",\n" +
                    "\t\t\"age\": 0,\n" +
                    "\t\t\"weight\": -39.06,\n" +
                    "\t\t\"height\": -107.75835,\n" +
                    "\t\t\"hair_color\": \"Pink\",\n" +
                    "\t\t\"professions\": [],\n" +
                    "\t\t\"friends\": []\n" +
                    "\t}";
            GnomesParser parser = GnomesParser.getInstance();
            Gnome gnome = parser.parseGnome(stringGnome);
            assertFalse("Formato de JSON era invalido y no fue detectado", true);
        } catch (Exception e){
            if (!(e instanceof JsonSyntaxException)){
               assertFalse("Formato de JSON era invalido y no fue detectado", true);
            }
        }
    }

    @Test
    public void TestBrastlewark() throws Exception {
        String jsonData = "{\"Brastlewark\": [{" +
                "\t}, {}]}";
        Brastlewark brastlewark = GnomesParser.getInstance().parseGnomesListBrastlewark(jsonData);
        assertNotNull("Parser devuelve null", brastlewark);
        assertEquals("Error en la cantidad de gnomos parseados",2, brastlewark.list.size());
    }

    @Test
    public void TestParser() throws Exception {
        String stringGnome = "{}";
        GnomesParser parser = GnomesParser.getInstance();
        Gnome gnome = parser.parseGnome(stringGnome);
        assertNotNull("Error en el Parser", gnome);
    }

}

