package sebastiancorradi.altran.presenters;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;


import java.util.ArrayList;
import java.util.HashMap;

import sebastiancorradi.altran.R;
import sebastiancorradi.altran.activities.MainActivity;
import sebastiancorradi.altran.interactors.DBInteractor;
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
    private String searchFilter;
    // child data in format of header title, child title
    private HashMap<String, Integer> mapColors;

    public MainPresenter(MainActivity activity){
        this.mainView = activity;
        Log.d(TAG, "gnomes count: " + GnomeRepository.getInstance().getGnomeList().size());
        listGnomesHeader = new ArrayList<Gnome>();
        listAdapter = new ExpandableListAdapter(mainView, listGnomesHeader);
        expListView = (ExpandableListView) mainView.findViewById(R.id.lvGnomes);
        expListView.setAdapter(listAdapter);
        /*
        listGnomesHeader.addAll(GnomeRepository.getInstance().getGnomeList());
        // get the listview
        expListView = (ExpandableListView) mainView.findViewById(R.id.lvGnomes);

        listAdapter = new ExpandableListAdapter(mainView, listGnomesHeader);

        // setting list adapter
        expListView.setAdapter(listAdapter);
*/
        setActionBar();
    }

    private void setActionBar(){
        ActionBar mActionBar = mainView.getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(mainView);

        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        EditText mTitleTextView = (EditText) mCustomView.findViewById(R.id.etFilter);
        mTitleTextView.setHint(mainView.getResources().getString(R.string.search_hint));

        mTitleTextView.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //listAdapter
                searchFilter = cs.toString();
                listGnomesHeader.clear();
                listGnomesHeader.addAll(GnomeRepository.getInstance().getGnomeList());
                for (int i = listGnomesHeader.size(); i > 0; i--){
                    if (!listGnomesHeader.get(i - 1).getName().toUpperCase().contains(searchFilter.toUpperCase())){
                        listGnomesHeader.remove(i-1);
                    } else {

                    }
                }
                listAdapter.notifyDataSetChanged();
               // listGnomesHeader.stream().filter(gnome -> gnome.getName().contains(searchFilter));


            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }

            @Override
            public void afterTextChanged(Editable arg0) {}
        });
        Spinner spHairColor = (Spinner) mCustomView.findViewById(R.id.spHairColor);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mainView,
                R.array.hair_color_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHairColor.setAdapter(adapter);

        spHairColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showGnomesByHairColor(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void setGnomesList(ArrayList<Gnome> list){
        this.listGnomesHeader = list;
    }

    private void showGnomesByHairColor(String hairColor){
        if (hairColor.equals(mainView.getResources().getString(R.string.label_All))){
            resetView();
            return;
        }

        DBInteractor interactor = DBInteractor.getInstance(mainView);
        ArrayList<Gnome> list = interactor.getGnomesByHairColor(hairColor);
        listAdapter.setList(list);
        listAdapter.notifyDataSetChanged();
    }
    private void resetView(){
        listGnomesHeader.clear();
        listGnomesHeader.addAll(GnomeRepository.getInstance().getGnomeList());
        listAdapter.setList(listGnomesHeader);
        listAdapter.notifyDataSetChanged();
    }
}
