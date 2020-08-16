package com.jamaatna.jamaatnaapp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.jamaatna.jamaatnaapp.Fragments.FavoiritFragment;
import com.jamaatna.jamaatnaapp.Fragments.HomeFragment;
import com.jamaatna.jamaatnaapp.Fragments.MenuFragment;
import com.jamaatna.jamaatnaapp.Fragments.SearchFragment;
import com.jamaatna.jamaatnaapp.Model.AppConstants;
import com.jamaatna.jamaatnaapp.R;
import com.jamaatna.jamaatnaapp.Utlities.UtilityApp;

public class HomeActivity extends BaseActivity {

    private FrameLayout mMainContainer;
    private ImageView mNavigationHome;
    private ImageView mNavigationSearch;
    private ImageView mNavigationAdd;
    private ImageView mNavigationFavorite;
    private ImageView mNavigationMenu;
    private LinearLayout mNavView;
    private RelativeLayout mContainer;
    Dialog dialog;

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
  getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(),
          "HomeFragment").commit();


        mNavigationHome.setImageDrawable(getResources().getDrawable(R.drawable.homeclick));

        mNavigationHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigationHome.setImageDrawable(getResources().getDrawable(R.drawable.homeclick));
                mNavigationSearch.setImageDrawable(getResources().getDrawable(R.drawable.search));
                mNavigationMenu.setImageDrawable(getResources().getDrawable(R.drawable.menu));
                mNavigationFavorite.setImageDrawable(getResources().getDrawable(R.drawable.favorite));

                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,
                        new HomeFragment(), "HomeFragment").commit();

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
                if(UtilityApp.isLogin()) {
                    Intent intent = new Intent(HomeActivity.this, AddServiceActivity.class);
                    intent.putExtra(AppConstants.KEY_TYPE, AppConstants.ADD_SERVICE_FOR_MENU);
                    startActivity(intent);
                }

                else {
                    Toast(getString(R.string.you_must_login_toadd));
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);

                }


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

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.mainContainer);

        if (currentFragment instanceof FavoiritFragment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(), "AdvisorNotificationsFragment").commit();
            mNavigationMenu.setImageDrawable(getResources().getDrawable(R.drawable.menu));
            mNavigationFavorite.setImageDrawable(getResources().getDrawable(R.drawable.favorite));
            mNavigationSearch.setImageDrawable(getResources().getDrawable(R.drawable.search));
            mNavigationHome.setImageDrawable(getResources().getDrawable(R.drawable.homeclick));

        }
        if (currentFragment instanceof MenuFragment) {

            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(), "ConsoultAdvisorFragment").commit();
            mNavigationMenu.setImageDrawable(getResources().getDrawable(R.drawable.menu));
            mNavigationFavorite.setImageDrawable(getResources().getDrawable(R.drawable.favorite));
            mNavigationSearch.setImageDrawable(getResources().getDrawable(R.drawable.search));
            mNavigationHome.setImageDrawable(getResources().getDrawable(R.drawable.homeclick));


        }
        if (currentFragment instanceof SearchFragment) {

            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(), "ConsoultAdvisorFragment").commit();
            mNavigationSearch.setImageDrawable(getResources().getDrawable(R.drawable.search));
            mNavigationMenu.setImageDrawable(getResources().getDrawable(R.drawable.menu));
            mNavigationFavorite.setImageDrawable(getResources().getDrawable(R.drawable.favorite));
            mNavigationHome.setImageDrawable(getResources().getDrawable(R.drawable.homeclick));

        }
        if (currentFragment instanceof HomeFragment) {
            {
                mNavigationHome.setImageDrawable(getResources().getDrawable(R.drawable.homeclick));
                mNavigationSearch.setImageDrawable(getResources().getDrawable(R.drawable.search));
                mNavigationMenu.setImageDrawable(getResources().getDrawable(R.drawable.menu));
                mNavigationFavorite.setImageDrawable(getResources().getDrawable(R.drawable.favorite));

                dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.custome_dialog_exit);
                Button yes = dialog.findViewById(R.id.yes);
                Button no = dialog.findViewById(R.id.no);
                ImageView close = dialog.findViewById(R.id.imageexitgame);
                dialog.setCancelable(true);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();

                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();


                    }
                });


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                dialog.show();

                //super.onBackPressed();
            }

        }

        //super.onBackPressed();
    }
}
