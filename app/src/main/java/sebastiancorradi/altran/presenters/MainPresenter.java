package sebastiancorradi.altran.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;

import sebastiancorradi.altran.R;
import sebastiancorradi.altran.activities.MainActivity;
import sebastiancorradi.altran.interactors.DBInteractor;
import sebastiancorradi.altran.interactors.MainInteractor;
import sebastiancorradi.altran.presenters.adapters.ExpandableListAdapter;
import sebastiancorradi.altran.model.Gnome;
import sebastiancorradi.altran.repository.GnomeRepository;

/**
 * Created by Gregorio on 12/2/2017.
 */

public class MainPresenter {
    private final String TAG = "MainPresenter";
    //private ArrayList<Gnome> gnomesList;
    private MainActivity mainView;
    private MainInteractor mainInteractor;
    private ExpandableListView expListView;
    private ExpandableListAdapter listAdapter;
    private ArrayList<Gnome> listGnomesHeader; // header titles
    private ArrayList<Gnome> workingGnomeList;
    private String searchFilter;
    private EditText mTitleTextView;
    private boolean hairFiltered = false;
    // child data in format of header title, child title
    private HashMap<String, Integer> mapColors;

    public MainPresenter(MainActivity activity){
        this.mainView = activity;
        mainInteractor = new MainInteractor(this);
        Log.d(TAG, "gnomes count: " + GnomeRepository.getInstance().getGnomeList().size());
        listGnomesHeader = new ArrayList<Gnome>();
        listAdapter = new ExpandableListAdapter(mainView, listGnomesHeader);
        listAdapter.setEnvironmentValues(mainInteractor.getMaxHeight(), mainInteractor.getMinHeight(),
                                        mainInteractor.getMaxWeight(), mainInteractor.getMinWeight());
        expListView = (ExpandableListView) mainView.findViewById(R.id.lvGnomes);
        expListView.setAdapter(listAdapter);
        workingGnomeList = GnomeRepository.getInstance().getGnomeList();

        setActionBar();
    }

    private void setActionBar(){
        ActionBar mActionBar = mainView.getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(mainView);

        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        mTitleTextView = (EditText) mCustomView.findViewById(R.id.etFilter);
        TextView mSpinnerLabel = (TextView) mCustomView.findViewById(R.id.tvSpinnerLabel);

        mSpinnerLabel.setText(mainView.getResources().getString(R.string.spinner_label));
        mTitleTextView.setHint(mainView.getResources().getString(R.string.search_hint));

        mTitleTextView.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //listAdapter
                if ((hairFiltered) && (cs.length() == 0)){
                    return;
                }
                searchFilter = cs.toString();
                listGnomesHeader.clear();
                listGnomesHeader.addAll(workingGnomeList);
                for (int i = listGnomesHeader.size(); i > 0; i--){
                    if (!listGnomesHeader.get(i - 1).getName().toUpperCase().contains(searchFilter.toUpperCase())){
                        listGnomesHeader.remove(i-1);
                    } else {

                    }
                }
                listAdapter.setList(listGnomesHeader);
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
                R.array.hair_color_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spHairColor.setAdapter(adapter);

        spHairColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mainInteractor.canFilter()) {
                    showGnomesByHairColor(adapterView.getSelectedItem().toString());
                } else {
                    Toast.makeText(getContext(), "Please try again later", Toast.LENGTH_LONG).show();
                }
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
            hairFiltered = false;
            return;
        }
        listGnomesHeader.clear();
        workingGnomeList = mainInteractor.getGnomesByHairColor(hairColor);
        listGnomesHeader.addAll(workingGnomeList);
        listAdapter.setList(listGnomesHeader);
        listAdapter.notifyDataSetChanged();
        hairFiltered = true;
        mTitleTextView.setText("");
    }
    private void resetView(){
        listGnomesHeader.clear();
        listGnomesHeader.addAll(mainInteractor.getAllGnomes());
        listAdapter.setList(listGnomesHeader);
        listAdapter.notifyDataSetChanged();
    }

    public Context getContext(){
        return mainView;
    }
}
