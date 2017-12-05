package sebastiancorradi.altran.repository;

import java.util.ArrayList;

import sebastiancorradi.altran.model.Brastlewark;
import sebastiancorradi.altran.model.Gnome;
import sebastiancorradi.altran.interactors.parsers.GnomesParser;

/**
 * Created by Gregorio on 12/3/2017.
 */

public class GnomeRepository {
    private static GnomeRepository instance;
    private ArrayList<Gnome> gnomesList;
    private GnomeRepository(){
        gnomesList = new ArrayList<Gnome>();
    }

    public static GnomeRepository getInstance(){
        if (instance == null){
            instance = new GnomeRepository();
        }
        return instance;
    }

    public void setData(ArrayList<Gnome>list ){
        this.gnomesList = list;
    }
    public void setData(String jsonData ){
        Brastlewark brastlewark = GnomesParser.getInstance().parseGnomesListBrastlewark(jsonData);
        gnomesList.addAll(brastlewark.list);
    }

    public ArrayList<Gnome> getGnomeList(){
        return gnomesList;
    }
}
