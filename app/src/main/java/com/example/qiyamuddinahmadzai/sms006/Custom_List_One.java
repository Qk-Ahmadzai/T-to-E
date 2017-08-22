package com.example.qiyamuddinahmadzai.sms006;

/**
 * Created by Qk Ahamdzai on 9/10/2015.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_List_One extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] number;
    private final String[] body;
    private final String[] date_time;
    //private final Integer[] imageId;

    public Custom_List_One(Activity context, String[] num, String[] bodys, String[] _date) {
        super(context, R.layout.custom_list, num);
        this.context = context;
        this.number = num;
        this.body = bodys;
        this.date_time = _date;
      //  this.imageId = imageId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.custom_list, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtNumber);
        TextView txtBody = (TextView) rowView.findViewById(R.id.txtSmsBody);
        TextView txtDateTime = (TextView) rowView.findViewById(R.id.txtDateTime);

        txtTitle.setText(number[position]);
        txtBody.setText(body[position]);
        txtDateTime.setText(date_time[position]);

        return rowView;
    }


}
