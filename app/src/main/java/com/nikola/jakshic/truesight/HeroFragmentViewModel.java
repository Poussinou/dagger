package com.nikola.jakshic.truesight;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nikola.jakshic.truesight.model.Hero;

import java.util.List;

public class HeroFragmentViewModel extends ViewModel {

    private MutableLiveData<List<Hero>> list = new MutableLiveData<>();
    private HeroRepository heroRepository;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public HeroFragmentViewModel() {
        heroRepository = new HeroRepository();
        loading.setValue(false);
    }

    public void fetchHeroes(long id) {
        heroRepository.fetchHeroes(list, loading, id);
    }

    public MutableLiveData<List<Hero>> getHeroes() {
        return list;
    }

    public MutableLiveData<Boolean> isLoading() {
        return loading;
    }
}