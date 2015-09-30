package com.dailyselfie.selfie;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by angelr on 30-Sep-15.
 */
public class SelfieAdapter extends ArrayAdapter {
    private Context context;
    private int resourseId;
    private ArrayList<Uri> dataList;

    public SelfieAdapter(Context context, int resource, ArrayList<Uri> objects) {
        super(context, resource, objects);
        this.context = context;
        resourseId = resource;
        this.dataList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View rowView = inflater.inflate(resourseId, parent, false);
        ImageView img = (ImageView) rowView.findViewById(R.id.selfie_picture);
        TextView time = (TextView) rowView.findViewById(R.id.timestamp);
        img.setImageURI(dataList.get(position));
        time.setText(String.valueOf(System.currentTimeMillis()));
//        username = (TextView) rowView.findViewById(R.id.text_username);
//        post = (TextView) rowView.findViewById(R.id.text_post);
//        date = (TextView) rowView.findViewById(R.id.text_date);
//        username.setText(dataList.get(position).getUsername());
//        post.setText(dataList.get(position).getNote());
//        date.setText(dataList.get(position).getDateCreated());

        return rowView;
    }
}
