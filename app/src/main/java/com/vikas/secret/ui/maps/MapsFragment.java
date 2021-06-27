package com.vikas.secret.ui.maps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vikas.secret.MainActivity;
import com.vikas.secret.R;
import com.vikas.secret.databinding.FragmentGalleryBinding;

import org.jetbrains.annotations.NotNull;

public class MapsFragment extends Fragment {

    private MapsViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    private GoogleMap googleMapView = null;
    private MainActivity activity;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMapView = googleMap;
            galleryViewModel.requestLastLocation(activity.getChatPersonID());
            googleMapView.setOnInfoWindowClickListener(marker -> launchDirections(marker.getPosition()));
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(MapsViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        activity = ((MainActivity) requireActivity());

        galleryViewModel.requestLastLocation(activity.getChatPersonID());

        galleryViewModel.getLocation().observe(getViewLifecycleOwner(), latLng -> {
            if(googleMapView!= null && latLng != null) {
                googleMapView.addMarker(new MarkerOptions().position(latLng).title("Click for directions"));
                googleMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void launchDirections(LatLng latLng) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latLng.latitude+","+latLng.longitude + "&mode=b");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}