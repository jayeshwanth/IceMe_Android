/**
 * Created by jaggu on 4/18/2015.
 */
package com.uconnect.events.app.usersetup;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.uconnect.events.R;
import com.uconnect.events.app.UCAppConstants;
import com.uconnect.events.app.UCSuperFragmentActivity;
import com.uconnect.events.network.services.BaseService;
import com.uconnect.events.utils.UCAppUtils;

/**
 * UCCustomUserCreate is to create the user based on the user preference
 * by asking email or phone no.
 */
public class UCCustomUserCreate extends UCSuperFragmentActivity implements View.OnClickListener {

    //private Spinner spr_choose_country;
    private EditText et_country;
    private EditText et_country_code;
    private EditText et_phone_no;
    private Button btn_ok;
    //private ArrayAdapter<String> countriesAdapter;
    private String email;
    private String mobile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uc_create_user);
        initViews();
    }

    private void initViews() {
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString(UCAppConstants.BundleConstants.UC_USER_PRIMARY_EMAIL);
        mobile = bundle.getString(UCAppConstants.BundleConstants.UC_USER_PHONE_NO);

        //spr_choose_country = (Spinner) findViewById(R.id.spr_choose_country);
        et_country = (EditText) findViewById(R.id.et_country);
        et_country_code = (EditText) findViewById(R.id.et_country_code);
        et_phone_no = (EditText) findViewById(R.id.et_phone_no);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);

        ((TextView) findViewById(R.id.tv_email)).setText(email);
        //setCountriesList();
        et_country_code.addTextChangedListener(mTextChangeListener);
        et_phone_no.setText(mobile);
        et_country.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(app, UCSelectCountry.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(UCAppConstants.SELECTED_COUNTRY, "");
                    startActivityForResult(intent, UCAppConstants.RequestCode.START_COUNTRY_LIST);
                }
                return false;
            }
        });
    }

    /*private void setCountriesList() {
        String [] countries = UCAppUtils.getInstance().getCountries();

        countriesAdapter = new ArrayAdapter<>(app, R.layout.spinner_item, countries);
        countriesAdapter.setDropDownViewResource(R.layout.spinner_item_pop_up);
        spr_choose_country.setAdapter(countriesAdapter);
        spr_choose_country.setSelection(0);
        spr_choose_country.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(app, UCSelectCountry.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(UCAppConstants.SELECTED_COUNTRY, "");
                    startActivityForResult(intent, UCAppConstants.RequestCode.START_COUNTRY_LIST);
                }
                return false;
            }
        });
    }*/

    /**
     * In button click listener read the user input and validate it by
     * sending mail/message based on the input.
     * if its validates navigating to Custom user validate screen.
     */
    private void checkUserInput() {
        String deviceUniqueId = Build.SERIAL;
        if(deviceUniqueId == null || deviceUniqueId.trim().equals("")) {
            deviceUniqueId = email;
        }
        mobile = et_phone_no.getText().toString();
        app.getUCUserServices().validatePhone(mobile, deviceUniqueId, new BaseService.DataUpdateCallback() {
            @Override
            public void dataLoaded(byte status, String responseString, boolean loadStatus, String statusTitle) {
                Log.v("UC_TAG", "U connect responseString : " + responseString);
                Log.v("UC_TAG", "U connect statusTitle : " + statusTitle);
                Log.v("UC_TAG", "U connect status : " + status);
            }
        });
    }

    /*@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        et_country_code.setText(UCAppUtils.getInstance().getCountryCode(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/

    private TextWatcher mTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String country = UCAppUtils.getInstance().getCountryFromCode(s.toString());

            //spr_choose_country.setSelection(countriesAdapter.getPosition(country));
            et_country.setText(country);
        }

        @Override
        public void afterTextChanged(Editable s) { }
    };

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_ok) {
            checkUserInput();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == UCAppConstants.RequestCode.START_COUNTRY_LIST && resultCode == UCAppConstants.ResultCode.FINISH_COUNTRY_LIST) {
            String countryId = data.getStringExtra(UCAppConstants.SELECTED_COUNTRY_ID);
            et_country_code.setText(countryId);
        }
    }
}
