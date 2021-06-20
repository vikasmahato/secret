package com.vikas.secret.ui.maps;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.vikas.secret.data.MapsRepository;

public class MapsViewModel extends ViewModel implements MapsCallbacks {

    private final MutableLiveData<LatLng> location;
    private final MapsRepository mapsRepository;

    public MapsViewModel() {
        location = new MutableLiveData<>();
        mapsRepository = MapsRepository.getInstance();
    }

    public LiveData<LatLng> getLocation() {
        return location;
    }

    @Override
    public void onGetChatPersonLocation(LatLng targetLocation) {
        location.setValue(targetLocation);
    }

    public void requestLastLocation(String chatPersonID) {
        mapsRepository.getChatPersonLocation(chatPersonID, this);
    }
}