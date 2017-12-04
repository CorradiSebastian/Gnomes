package sebastiancorradi.altran.presenters;

import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sebastiancorradi.altran.R;
import sebastiancorradi.altran.activities.MainActivity;
import sebastiancorradi.altran.adapters.ExpandableListAdapter;
import sebastiancorradi.altran.model.Gnome;
import sebastiancorradi.altran.repository.GnomeRepository;

/**
 * Created by Gregorio on 12/2/2017.
 */

public class MainPresenter {
    private final String TAG = "MainPresenter";
    //private ArrayList<Gnome> gnomesList;
    public MainActivity mainView;
    private ExpandableListView expListView;
    private ExpandableListAdapter listAdapter;
    private ArrayList<Gnome> listGnomesHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, Gnome> listDataChild;

    public MainPresenter(MainActivity activity){
        this.mainView = activity;
        Log.d(TAG, "gnomes count: " + GnomeRepository.getInstance().getGnomeList().size());
        listGnomesHeader = new ArrayList<Gnome>();
        listGnomesHeader.addAll(GnomeRepository.getInstance().getGnomeList());
        // get the listview
        expListView = (ExpandableListView) mainView.findViewById(R.id.lvGnomes);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(mainView, listGnomesHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        listDataChild = new HashMap<String, Gnome>();

        // Adding child data
        for (int i = 0; i < listGnomesHeader.size(); i ++){
            listDataChild.put(listGnomesHeader.get(i).getName(), listGnomesHeader.get(i));
        }
        /*
        listGnomesHeader.add(gnomesList);
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");
        */

        /*
        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
        */
    }

    public void setGnomesList(ArrayList<Gnome> list){
        this.listGnomesHeader = list;
    }



}
