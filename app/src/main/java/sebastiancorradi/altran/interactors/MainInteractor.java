package sebastiancorradi.altran.interactors;

import java.util.ArrayList;

import sebastiancorradi.altran.model.Gnome;
import sebastiancorradi.altran.model.InjectableGnomeCreator;
import sebastiancorradi.altran.presenters.MainPresenter;

/**
 * Created by Gregorio on 12/15/2017.
 */

public class MainInteractor {

    MainPresenter mainPresenter;
    DBInteractor dbInteractor;

    public MainInteractor(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter;
        dbInteractor = DBInteractor.getInstance(mainPresenter.getContext(), new InjectableGnomeCreator());

    }

    public ArrayList<Gnome> getGnomesByHairColor(String hairColor){
        ArrayList<Gnome> list = dbInteractor.getGnomesByHairColor(hairColor);
        return list;
    }

    public ArrayList<Gnome> getAllGnomes(){
        return dbInteractor.getAll();
    }

    public boolean canFilter(){
        return DBInteractor.getInstance(mainPresenter.getContext(), new InjectableGnomeCreator()).isDataBaseReady();
    }

    public float getMaxHeight(){
        return DBInteractor.getInstance(mainPresenter.getContext(), new InjectableGnomeCreator()).getMaxHeight();
    }
    public float getMinHeight(){
        return DBInteractor.getInstance(mainPresenter.getContext(), new InjectableGnomeCreator()).getMinHeight();
    }
    public float getMaxWeight(){
        return DBInteractor.getInstance(mainPresenter.getContext(), new InjectableGnomeCreator()).getMaxWeight();
    }
    public float getMinWeight(){
        return DBInteractor.getInstance(mainPresenter.getContext(), new InjectableGnomeCreator()).getMinWeight();
    }
}
