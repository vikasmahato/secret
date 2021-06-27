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

        int[] drawables = {
                R.drawable.cake,
                R.drawable.balloon,
                R.drawable.rose,
                R.drawable.heart,
                R.drawable.code,
                R.drawable.pen,
                R.drawable.rain,
                R.drawable.girl,
                R.drawable.cat1,
                R.drawable.chocolate,
                R.drawable.queen,
                R.drawable.queen
        };

        String[] videoId = {
                "Trjrj_fQnIM",
                "J43Z9XKj4DA",
                "Trjrj_fQnIM",
                "J43Z9XKj4DA",
                "Trjrj_fQnIM",
                "J43Z9XKj4DA",
                "Trjrj_fQnIM",
                "J43Z9XKj4DA",
                "Trjrj_fQnIM",
                "J43Z9XKj4DA",
                "Trjrj_fQnIM",
                "J43Z9XKj4DA",
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