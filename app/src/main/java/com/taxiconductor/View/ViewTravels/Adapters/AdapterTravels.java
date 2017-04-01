package com.taxiconductor.View.ViewTravels.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.taxiconductor.R;
import com.taxiconductor.RetrofitAPI.Model.ModelStatus;
import com.taxiconductor.RetrofitAPI.Model.ModelTravels;

import java.util.ArrayList;

/**
 * Created by carlos on 31/03/17.
 */

public class AdapterTravels extends BaseAdapter{
    private Context mContext;
    private ArrayList<ModelTravels> lista;

    public AdapterTravels(Context c, ArrayList<ModelTravels> lista) {
        this.mContext = c;
        this.lista = lista;
    }

    public int getCount() {
        return lista.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.adapter_travels,parent,false);
            // if it's not recycled, initialize some attributes

        }
        TextView origin= (TextView) convertView.findViewById(R.id.textViewOrigin);
        TextView destination= (TextView) convertView.findViewById(R.id.textViewDestination);
        TextView date = (TextView) convertView.findViewById(R.id.textViewDate);


        origin.setText(lista.get(position).getOrigin());
        destination.setText(lista.get(position).getDestination());
        date.setText(lista.get(position).getDate());

        return convertView;
    }
}
