package com.vikas.secret.ui.angrymode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vikas.secret.adapters.AngryModeAdapter;
import com.vikas.secret.databinding.FragmentAngerBinding;

import java.util.ArrayList;

public class AngryModeFragment extends Fragment {

    private AngryModeViewModel angryModeViewModel;
    private FragmentAngerBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        angryModeViewModel =
                new ViewModelProvider(this).get(AngryModeViewModel.class);

        binding = FragmentAngerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final AngryModeAdapter angryModeAdapter = new AngryModeAdapter((adapterPosition, angerModel, listItem) -> {

        }, new ArrayList<>(), root.getContext());

        binding.angerRecyclerView.setAdapter(angryModeAdapter);
        binding.angerRecyclerView.setHasFixedSize(true);
        binding.angerRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        angryModeViewModel.fetchAngryList();

        angryModeViewModel.getAngerList().observe(getViewLifecycleOwner(), angryModeAdapter::update);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}