package com.example.qiyamuddinahmadzai.sms006;

/**
 * Created by Qk Ahamdzai on 9/10/2015.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Custom_Number_List_One extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] number;
    private final String[] description;

    //private final Integer[] imageId;

    public Custom_Number_List_One(Activity context, String[] num, String[] desc) {

        super(context, R.layout.custom_number_list, num);
        this.context = context;
        this.number = num;
        this.description = desc;

      //  this.imageId = imageId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.custom_number_list, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtNumber);
        TextView txtBody = (TextView) rowView.findViewById(R.id.txtDesc);

        txtTitle.setText(number[position]);
        txtBody.setText(description[position]);

        return rowView;
    }


}
