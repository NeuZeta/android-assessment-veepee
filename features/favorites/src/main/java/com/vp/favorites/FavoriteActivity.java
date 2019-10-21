package com.vp.favorites;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new FavoriteListFragment())
                    .commit();
        }

    }
}
