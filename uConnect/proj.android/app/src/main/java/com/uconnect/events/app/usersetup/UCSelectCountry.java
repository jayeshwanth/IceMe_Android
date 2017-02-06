package com.uconnect.events.app.usersetup;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.uconnect.events.R;
import com.uconnect.events.app.UCAppConstants;
import com.uconnect.events.app.UCSuperFragmentActivity;
import com.uconnect.events.app.adapters.UCCountriesAdapter;
import com.uconnect.events.model.UCCountry;
import com.uconnect.events.utils.UCAppUtils;

import java.util.ArrayList;

/**
 * Created by jagadeesh on 2/5/15.
 */
public class UCSelectCountry extends UCSuperFragmentActivity implements AdapterView.OnItemClickListener {
    private EditText et_search_country;
    private ArrayList<UCCountry> listCountries;
    private UCCountriesAdapter countriesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uc_select_country);

        initViews();
        getCountries("");
    }

    private void initViews() {
        et_search_country = (EditText) findViewById(R.id.et_search_country);
        listCountries = new ArrayList<>();
        ListView listView = (ListView) findViewById(R.id.lv_countries);
        countriesAdapter = new UCCountriesAdapter(app, listCountries);
        listView.setAdapter(countriesAdapter);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(this);

        et_search_country.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getCountries(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getCountries(String namePrefix) {
        listCountries.clear();
        String[] countryNameList = UCAppUtils.getInstance().getCountries();
        String[] countryIdList = UCAppUtils.getInstance().getCountryCodes();

        for(int i = 0; i < countryNameList.length; i++) {
            UCCountry country = new UCCountry();
            country.setCountryName(countryNameList[i]);
            country.setCountryId(countryIdList[i]);
            if(namePrefix == null || namePrefix.equals("")) {
                listCountries.add(country);
            } else if(countryNameList[i].toLowerCase().startsWith(namePrefix.toLowerCase())) {
                listCountries.add(country);
            }
        }

        countriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.v("UC_TAG", "U connect country is selected...");
        Intent intent = new Intent();
        intent.putExtra(UCAppConstants.SELECTED_COUNTRY_ID, listCountries.get(position).getCountryId());
        setResult(UCAppConstants.ResultCode.FINISH_COUNTRY_LIST, intent);
        finish();
    }
}
