package com.example.project_android;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_android.Response.DatabaseHandler;
import com.example.project_android.Service.ApiService;
import com.example.project_android.UI.ArticlesFragment;
import com.example.project_android.UI.GamesFragment;
import com.example.project_android.UI.HomeFragment;
import com.example.project_android.UI.ProfileSettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

        DatabaseHandler dH;
        DrawerLayout dL;
        BottomNavigationView btn_nav;
        boolean isLoggedIn;
        Toolbar toolbar;
        SwitchCompat sMode;
        boolean nightMode;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            dH = new DatabaseHandler(MainActivity.this);
            sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();

            isLoggedIn = checkLoginStatus();

            if (isLoggedIn) {
                loadMainContent();
            } else {
                showLoginScreen();
            }

        }

    private void showLoginScreen() {
        setContentView(R.layout.login_layout);

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);
        TextView singUpText = findViewById(R.id.signupText);

        if(!sharedPreferences.getString("name", " ").equals(" ")){
            usernameEditText.setText(sharedPreferences.getString("name", " "));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                dH.loginHandler(username,password);

                if (checkLoginStatus()) {
                    isLoggedIn = true;
                    loadMainContent();
                } else {
                    Toast.makeText(MainActivity.this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show();
                }
            }
        });
        singUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = getApplicationContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_register, null);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                if (dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
                EditText username = dialogView.findViewById(R.id.regUsername);
                EditText email = dialogView.findViewById(R.id.regEmail);
                EditText password = dialogView.findViewById(R.id.regPassword);
                Button btn = dialogView.findViewById(R.id.regSubmit);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dH.registerHandler(username.getText().toString(),email.getText().toString(),password.getText().toString());
                        dialog.hide();
                    }
                });
            }
        });
    }

    private boolean checkLoginStatus() {
        return !sharedPreferences.getString("user_id", " ").equals(" ");
    }

    protected void loadMainContent() {
        setContentView(R.layout.activity_main);
        btn_nav = findViewById(R.id.bottomNavigationView);
        dL = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sMode =findViewById(R.id.switchMode);

        checkNightMode();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dL, toolbar, R.string.open_nav, R.string.close_nav);
        dL.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView nV = findViewById(R.id.nav_view);
        nV.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());

        btn_nav.setBackground(null);
        btn_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.bt_home) {
                    replaceFragment(new HomeFragment());
                }
                if (itemId == R.id.bt_profile) {
                    replaceFragment(new ProfileSettingsFragment());
                }
                if (itemId == R.id.bt_games) {
                    replaceFragment(new GamesFragment());
                }
                if (itemId == R.id.bt_article) {
                    replaceFragment(new ArticlesFragment());
                }
                return true;
            }
        });
    }

    private void checkNightMode() {

        nightMode = sharedPreferences.getBoolean("nightMode",false);
        if(nightMode){
            sMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        sMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nightMode){
                    sMode.setChecked(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("nightMode",false);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("nightMode",true);
                }
                editor.apply();
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_layout, fragment);
            fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.nav_home){
            replaceFragment(new HomeFragment());
        }
        if(itemId==R.id.nav_profile){
            replaceFragment(new ProfileSettingsFragment());
        }
        if(itemId==R.id.nav_game){
            replaceFragment(new GamesFragment());
        }
        if(itemId==R.id.nav_article){
            replaceFragment(new ArticlesFragment());
        }
        if(itemId==R.id.nav_logout){
            editor.remove("name");
            editor.remove("password");
            editor.remove("email");
            editor.remove("user_id");

            editor.apply();
            showLoginScreen();
        }
        dL.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
            if(dL.isDrawerOpen(GravityCompat.START)){
               dL.closeDrawer(GravityCompat.START);
            }else{
                super.onBackPressed();
            }
    }
}