package com.vikas.secret;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vikas.lib.GlideImageLoader;
import com.vikas.secret.databinding.ActivityMainBinding;
import com.vikas.secret.ui.chat.ChatFragment;

public class MainActivity extends AppCompatActivity implements BaseActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private final String TAG = "MAIN_ACTIVITY";
    private MainViewModel mainViewModel;
    private SharedPreferences sharedPreferences;

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_content_main,new ChatFragment()); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
                hideMessageButton();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        setUserInfo(FirebaseAuth.getInstance().getCurrentUser());

        sharedPreferences = this.getSharedPreferences("com.vikas.secret", MODE_PRIVATE);

        new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    setUserInfo(user);

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    public String getChatPersonID() {
        return sharedPreferences.getString(Constants.PREFS_CHAT_PERSON_ID, "");
    }

    public String getMessageID() {
        return sharedPreferences.getString(Constants.PREFS_MESSAGE_ID, "");
    }

    public void setChatPersonId(String chatPersonId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PREFS_CHAT_PERSON_ID, chatPersonId);
        editor.apply();
    }

    public void setMessageId(String messageId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PREFS_MESSAGE_ID, messageId);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    public void showMessageButton() {
        binding.appBarMain.fab.setVisibility(View.VISIBLE);
    }

    public void hideMessageButton() {
        binding.appBarMain.fab.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    private void setUserInfo(FirebaseUser user) {
        if(user != null) {
            //TODO: put this in observer
            saveUserToFireStore(user);

            NavigationView mNavigationView = findViewById(R.id.nav_view);
            View headerView = mNavigationView.getHeaderView(0);

            TextView userName = headerView.findViewById(R.id.name);
            TextView userEmail = headerView.findViewById(R.id.email);
            ImageView userProfile = headerView.findViewById(R.id.imageView);

            userName.setText(user.getDisplayName());
            userEmail.setText(user.getEmail());

            GlideImageLoader imageLoader = new GlideImageLoader(getApplicationContext());
            imageLoader.load(userProfile, user.getPhotoUrl().toString());
        }
    }

    private void saveUserToFireStore(FirebaseUser user) {
        mainViewModel.saveUser(user);
    }
}