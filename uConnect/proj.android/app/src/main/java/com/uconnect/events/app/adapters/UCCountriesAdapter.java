//*************************************************************************************************
//*************************************************************************************************
//      Project name                    	    : Alesco Happy
//      Class Name                              : CategoriesListAdapter
//      Author                                  : [x]cube LABS
//*************************************************************************************************
//*************************************************************************************************
package com.uconnect.events.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uconnect.events.R;
import com.uconnect.events.model.UCCountry;

import java.util.ArrayList;

public class UCCountriesAdapter extends BaseAdapter {


    private final ArrayList<UCCountry> countryList;
    private LayoutInflater inflater = null;

    public UCCountriesAdapter(Context context, ArrayList<UCCountry> countryList) {
        this.countryList = countryList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Object getItem(int position) {
        return countryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.country_id_list_row, parent, false);
            viewHolder.countryName = (TextView) convertView.findViewById(R.id.tv_country_name);
            viewHolder.countryId = (TextView) convertView.findViewById(R.id.tv_country_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        UCCountry country = countryList.get(position);
        viewHolder.countryName.setText(country.getCountryName());
        viewHolder.countryId.setText(country.getCountryId());

        return convertView;
    }

    class ViewHolder {
        TextView countryName;
        TextView countryId;
    }

}
