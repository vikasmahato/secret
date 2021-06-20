package com.vikas.secret.ui.angrymode;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vikas.secret.data.AngrymodeRepository;
import com.vikas.secret.data.models.AngerModel;

import java.util.ArrayList;
import java.util.List;

public class AngryModeViewModel extends ViewModel implements AngerCallbacks{

    private MutableLiveData<List<AngerModel>> angerList;
    private AngrymodeRepository repository;

    public AngryModeViewModel() {
        angerList = new MutableLiveData<>();
        angerList.setValue(new ArrayList<>());
        repository = AngrymodeRepository.getInstance();
    }

    public void fetchAngryList() {
        repository.getAngerList("P2Wk1cKz3k0NzWWbiuwD", this);
    }

    public LiveData<List<AngerModel>> getAngerList() {
        return angerList;
    }

    @Override
    public void onGetAngerList(List<AngerModel> messages) {
        angerList.setValue(messages);
    }
}