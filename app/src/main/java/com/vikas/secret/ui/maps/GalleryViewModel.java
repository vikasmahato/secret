package com.vikas.secret.ui.maps;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.vikas.secret.data.GalleryRepository;

public class GalleryViewModel extends ViewModel implements GalleryCallbacks{

    private final MutableLiveData<LatLng> location;
    private GalleryRepository galleryRepository;

    public GalleryViewModel() {
        location = new MutableLiveData<>();
        galleryRepository = new GalleryRepository();
    }

    public LiveData<LatLng> getLocation() {
        return location;
    }

    @Override
    public void onGetChatPersonLocation(LatLng targetLocation) {
        location.setValue(targetLocation);
    }

    public void requestLastLocation() {
        galleryRepository.getChatPersonLocation("T6BskHp17BWNlFZ68pokmqEGfv12", this);
    }
}