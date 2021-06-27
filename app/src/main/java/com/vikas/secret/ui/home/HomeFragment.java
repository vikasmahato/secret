package com.vikas.secret.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.vikas.secret.R;
import com.vikas.secret.adapters.HomeAdapter;
import com.vikas.secret.data.models.HomeItem;
import com.vikas.secret.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private final String TAG = "HomeFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        List<HomeItem> homeItemList = new ArrayList<>();

        int hour = hourOfDay();

        String[] drawables = {
                "https://i3.ytimg.com/vi/2aFxUFSt2XI/hqdefault.jpg", // Dehleez pe flute
                "https://i3.ytimg.com/vi/Trjrj_fQnIM/hqdefault.jpg", // Beautiful in White
                "https://i3.ytimg.com/vi/jsuY96Ca4R0/hqdefault.jpg", // Wo chali vo chali
                "https://i3.ytimg.com/vi/qEd6QUbK2Mw/hqdefault.jpg", // Love out of nothing at all
                "https://i3.ytimg.com/vi/6zoD2FNvCIo/hqdefault.jpg", // Mai bola hey
                "https://i3.ytimg.com/vi/te9IWhXJvQo/hqdefault.jpg", // Lady in red
                "https://i3.ytimg.com/vi/qLCLvzTGFVM/hqdefault.jpg", // Dil Mere
                "https://i3.ytimg.com/vi/npwHNcGqueE/hqdefault.jpg", // Can't help falling in love
                "https://i3.ytimg.com/vi/AVM4J0qvdKw/hqdefault.jpg", // Dhaaga
                "https://i3.ytimg.com/vi/N30_bXhc9Hc/hqdefault.jpg", // Fool again
                "https://i3.ytimg.com/vi/uB5bf7LQPVU/hqdefault.jpg", // Choo loo
                "https://i3.ytimg.com/vi/w_Rut4qm33g/hqdefault.jpg" // Words
        };

        String[] videoId = {
                "2aFxUFSt2XI", // Dehleez pe flute
                "Trjrj_fQnIM", // Beautiful in White
                "jsuY96Ca4R0", // Wo chali vo chali
                "qEd6QUbK2Mw", // Love out of nothing at all
                "6zoD2FNvCIo", // Mai bola hey
                "te9IWhXJvQo", // Lady in red
                "qLCLvzTGFVM", // Dil Mere
                "npwHNcGqueE", // Can't help falling in love
                "AVM4J0qvdKw", // Dhaaga
                "N30_bXhc9Hc", // Fool again
                "uB5bf7LQPVU", // Choo loo
                "w_Rut4qm33g" // Words
        };

        for(int i = 0; i< drawables.length; i++) {
            homeItemList.add(new HomeItem("name" + i, videoId[i],drawables[i] , hour/2 >= i));
        }

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        final RecyclerView recyclerView = binding.homeRecylearView;
        recyclerView.setAdapter(new HomeAdapter(homeItemList, (adapterPosition, homeItem, imageView) -> {
            Fragment recyclerViewFragment = RecyclerViewFragment.newInstance(homeItem, homeItem.getName());
            getParentFragmentManager()
                    .beginTransaction()
                    .addSharedElement(imageView, Objects.requireNonNull(ViewCompat.getTransitionName(imageView)))
                    .addToBackStack(TAG)
                    .replace(R.id.content, recyclerViewFragment)
                    .commit();
        }, root.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setLayoutManager(gridLayoutManager);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private int hourOfDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.HOUR_OF_DAY);
    }
}