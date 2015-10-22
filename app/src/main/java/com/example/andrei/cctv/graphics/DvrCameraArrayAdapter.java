package com.example.andrei.cctv.graphics;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.andrei.cctv.R;

import java.util.ArrayList;

public class DvrCameraArrayAdapter extends ArrayAdapter<DvrCamera> {
    private int layoutResourceId;
    private LayoutInflater layoutInflater;
    private Context context;

    private ArrayList<DvrCamera> items = new ArrayList<>();

    public DvrCameraArrayAdapter(Context context, int layoutResourceId, ArrayList<DvrCamera> items) {
        super(context, layoutResourceId, items);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.items = items;

        layoutInflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public DvrCamera getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = layoutInflater.from(context).inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_camera_placeholder);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress_camera_connection);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DvrCamera item = getItem(position);

        if (item != null) {
            holder.progressBar.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }
}
