package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.azimaty.azimatyapp.Fragments.AddServiceFragment;
import com.azimaty.azimatyapp.Fragments.FavoiritFragment;
import com.azimaty.azimatyapp.Fragments.HomeFragment;
import com.azimaty.azimatyapp.Fragments.MenuFragment;
import com.azimaty.azimatyapp.Fragments.SearchFragment;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.R;

public class HomeActivity extends BaseActivity {

    private FrameLayout mMainContainer;
    private ImageView mNavigationHome;
    private ImageView mNavigationSearch;
    private ImageView mNavigationAdd;
    private ImageView mNavigationFavorite;
    private ImageView mNavigationMenu;
    private LinearLayout mNavView;
    private RelativeLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mMainContainer = findViewById(R.id.mainContainer);
        mNavigationHome = findViewById(R.id.navigation_home);
        mNavigationSearch = findViewById(R.id.navigation_search);
        mNavigationAdd = findViewById(R.id.navigation_add);
        mNavigationFavorite = findViewById(R.id.navigation_favorite);
        mNavigationMenu = findViewById(R.id.navigation_menu);
        mNavView = findViewById(R.id.nav_view);
        mContainer = findViewById(R.id.container);
  getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(), "HomeFragment").commit();


        mNavigationHome.setImageDrawable(getResources().getDrawable(R.drawable.homeclick));

        mNavigationHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigationHome.setImageDrawable(getResources().getDrawable(R.drawable.homeclick));
                mNavigationSearch.setImageDrawable(getResources().getDrawable(R.drawable.search));
                mNavigationMenu.setImageDrawable(getResources().getDrawable(R.drawable.menu));
                mNavigationFavorite.setImageDrawable(getResources().getDrawable(R.drawable.favorite));

                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(), "HomeFragment").commit();

            }
        });
        mNavigationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigationSearch.setImageDrawable(getResources().getDrawable(R.drawable.searchblue));
                mNavigationMenu.setImageDrawable(getResources().getDrawable(R.drawable.menu));
                mNavigationFavorite.setImageDrawable(getResources().getDrawable(R.drawable.favorite));
                mNavigationHome.setImageDrawable(getResources().getDrawable(R.drawable.home));

                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new SearchFragment(), "SearchFragment").commit();

            }
        });
        mNavigationAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigationSearch.setImageDrawable(getResources().getDrawable(R.drawable.search));
                mNavigationMenu.setImageDrawable(getResources().getDrawable(R.drawable.menu));
                mNavigationFavorite.setImageDrawable(getResources().getDrawable(R.drawable.favorite));
                mNavigationHome.setImageDrawable(getResources().getDrawable(R.drawable.home));

                Intent intent = new Intent(HomeActivity.this, AddServiceActivity.class);
                intent.putExtra(AppConstants.KEY_TYPE, AppConstants.ADD_SERVICE_FOR_MENU);
                startActivity(intent);


            }
        });
        mNavigationMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mNavigationMenu.setImageDrawable(getResources().getDrawable(R.drawable.menuafterclick));
                mNavigationFavorite.setImageDrawable(getResources().getDrawable(R.drawable.favorite));
                mNavigationSearch.setImageDrawable(getResources().getDrawable(R.drawable.search));
                mNavigationHome.setImageDrawable(getResources().getDrawable(R.drawable.home));

                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MenuFragment(), "MenuFragment").commit();

            }
        });
        mNavigationFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigationFavorite.setImageDrawable(getResources().getDrawable(R.drawable.favoritefill));
                mNavigationMenu.setImageDrawable(getResources().getDrawable(R.drawable.menu));
                mNavigationSearch.setImageDrawable(getResources().getDrawable(R.drawable.search));
                mNavigationHome.setImageDrawable(getResources().getDrawable(R.drawable.home));


                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new FavoiritFragment(), "FavoiritFragment").commit();

            }
        });


    }

}
