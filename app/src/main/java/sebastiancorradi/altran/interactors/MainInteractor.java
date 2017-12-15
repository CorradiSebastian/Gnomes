package sebastiancorradi.altran.interactors;

import java.util.ArrayList;

import sebastiancorradi.altran.model.Gnome;
import sebastiancorradi.altran.presenters.MainPresenter;
import sebastiancorradi.altran.repository.GnomeRepository;

/**
 * Created by Gregorio on 12/15/2017.
 */

public class MainInteractor {

    MainPresenter mainPresenter;

    public MainInteractor(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter;
    }

    public ArrayList<Gnome> getGnomesByHairColor(String hairColor){
        DBInteractor interactor = DBInteractor.getInstance(mainPresenter.getContext());
        ArrayList<Gnome> list = interactor.getGnomesByHairColor(hairColor);
        return list;
    }

    public ArrayList<Gnome> getAllGnomes(){
        return GnomeRepository.getInstance().getGnomeList();
    }

    public boolean canFilter(){
        return DBInteractor.getInstance(mainPresenter.getContext()).isDataBaseReady();
    }
}
