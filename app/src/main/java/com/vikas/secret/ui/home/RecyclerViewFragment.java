package com.vikas.secret.ui.home;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.vikas.secret.MainActivity;
import com.vikas.secret.R;
import com.vikas.secret.data.models.HomeItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecyclerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecyclerViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String EXTRA_HOME_ITEM = "EXTRA_HOME_ITEM";
    private static final String EXTRA_TRANSITION_NAME = "EXTRA_TRANSITION_NAME";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MainActivity activity;
    YouTubePlayerView youTubePlayerView;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {

        if (activity instanceof FragmentActivity) {
            activity = ((MainActivity) requireActivity());
        }

        super.onAttach(activity);
    }

    public static RecyclerViewFragment newInstance(HomeItem param1, String param2) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_HOME_ITEM, param1);
        args.putString(EXTRA_TRANSITION_NAME, param2);
        fragment.setArguments(args);
        return fragment;
    }

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.youtube_fragment, container, false);

        youTubePlayerView = rootView.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.enterFullScreen();

        assert getArguments() != null;
        HomeItem homeItem = getArguments().getParcelable(EXTRA_HOME_ITEM);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = homeItem.getVideoId();
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.play();
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }
    /*@Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        HomeItem homeItem = getArguments().getParcelable(EXTRA_HOME_ITEM);
        String transitionName = getArguments().getString(EXTRA_TRANSITION_NAME);

        TextView detailTextView = view.findViewById(R.id.detail_text);
        detailTextView.setText(homeItem.getDetail());

        ImageView heroImageView = view.findViewById(R.id.hero_image);
        ViewCompat.setTransitionName(heroImageView, transitionName);

        Glide.with(requireContext())
                .load(homeItem.getImageUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(heroImageView)
        ;

    }*/
}