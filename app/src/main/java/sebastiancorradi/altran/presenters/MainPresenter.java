package sebastiancorradi.altran.presenters;

import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

import sebastiancorradi.altran.R;
import sebastiancorradi.altran.activities.MainActivity;
import sebastiancorradi.altran.presenters.adapters.ExpandableListAdapter;
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

    }

    public void setGnomesList(ArrayList<Gnome> list){
        this.listGnomesHeader = list;
    }



}
