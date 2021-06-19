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
        homeItemList.add(new HomeItem("name1", "detail",R.drawable.cake ));
        homeItemList.add(new HomeItem("name2", "detail",R.drawable.balloon ));
        homeItemList.add(new HomeItem("name3", "detail",R.drawable.rose ));
        homeItemList.add(new HomeItem("name4", "detail",R.drawable.heart ));
        homeItemList.add(new HomeItem("name5", "detail",R.drawable.code ));
        homeItemList.add(new HomeItem("name6", "detail",R.drawable.pen ));
        homeItemList.add(new HomeItem("name7", "detail",R.drawable.rain ));
        homeItemList.add(new HomeItem("name8", "detail",R.drawable.girl ));
        homeItemList.add(new HomeItem("name9", "detail",R.drawable.cat1 ));
        homeItemList.add(new HomeItem("name10", "detail",R.drawable.chocolate ));
        homeItemList.add(new HomeItem("name11", "detail",R.drawable.queen ));
        homeItemList.add(new HomeItem("name12", "detail",R.drawable.queen ));

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

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
        //recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setLayoutManager(gridLayoutManager);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}