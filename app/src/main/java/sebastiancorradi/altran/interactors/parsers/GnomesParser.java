package sebastiancorradi.altran.interactors.parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sebastiancorradi.altran.model.Brastlewark;
import sebastiancorradi.altran.model.Gnome;

/**
 * Created by Gregorio on 12/3/2017.
 */

public class GnomesParser {
    public static GnomesParser instance;

    private GnomesParser (){

    }

    public static GnomesParser getInstance(){
        if (instance == null){
            instance = new GnomesParser();
        }
        return instance;
    }

    public ArrayList<Gnome> parseGnomes (String gnomesJSON){
        ArrayList<Gnome> result = new ArrayList<Gnome>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        List<Gnome> gnomesList = Arrays.asList(gson.fromJson(gnomesJSON, Gnome[].class));

        result.addAll(gnomesList);
        return  result;
    }

    public Brastlewark parseGnomesListBrastlewark (String gnomesListJSON){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Brastlewark brastlewark = gson.fromJson(gnomesListJSON, Brastlewark.class);

        return  brastlewark;
    }

    public Gnome parseGnome (String gnomeJSON){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Gnome gnome = gson.fromJson(gnomeJSON, Gnome.class);

        return  gnome;
    }
}
