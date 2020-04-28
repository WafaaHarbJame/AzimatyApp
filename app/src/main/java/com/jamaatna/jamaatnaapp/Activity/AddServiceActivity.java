package com.jamaatna.jamaatnaapp.Activity;

import android.os.Bundle;
import android.util.Log;

import com.jamaatna.jamaatnaapp.Fragments.AddServiceFragment;
import com.jamaatna.jamaatnaapp.Model.AppConstants;
import com.jamaatna.jamaatnaapp.R;

public class AddServiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String type = bundle.getString(AppConstants.KEY_TYPE);
            int my_service_id=bundle.getInt(AppConstants.my_service_id);
            Log.e("WAFAA", "AddServiceActivity"+my_service_id);


            if (type != null) {
                AddServiceFragment addServiceFragment = new AddServiceFragment();
                Bundle addBundle = new Bundle();
                addBundle.putString(AppConstants.KEY_TYPE, type);
                addBundle.putInt(AppConstants.my_service_id,my_service_id);
                Log.e("WAFAA", "AddServiceActivity"+my_service_id);

                addServiceFragment.setArguments(addBundle);
                // Put anything what you want
                getSupportFragmentManager().beginTransaction().replace(R.id.addserviceContainer,
                        addServiceFragment, "AddServiceFragment").commit();

            }

        }

    }
}
