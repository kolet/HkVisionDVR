package com.example.andrei.cctv.hikvision;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.andrei.cctv.R;

import java.util.ArrayList;

public class DvrCameraArrayAdapter extends ArrayAdapter<DvrCamera> {
    private int layoutResourceId;
    private LayoutInflater layoutInflater;
    private Context context;
    //private Drawable noVideoImage;

    private ArrayList<DvrCamera> cameras = new ArrayList<>();

    public DvrCameraArrayAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        layoutInflater = ((Activity) context).getLayoutInflater();
    }

    public DvrCameraArrayAdapter(Context context, int layoutResourceId, ArrayList<DvrCamera> cameras) {
        super(context, layoutResourceId, cameras);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.cameras = cameras;

        layoutInflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public int getCount() {
        return cameras.size();
    }

    @Override
    public DvrCamera getItem(int position) {

        return cameras.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            //row = LayoutInflater.from(context).inflate(layoutResourceId, null);
            row = layoutInflater.inflate(layoutResourceId, parent, false); // inflater.inflate(R.layout.rowlayout, null, true);
//  mInflater.inflate(R.layout.item_objects, null);  LayoutInflater.from(context).inflate(R.layout.layout_city,null);
            holder = new ViewHolder();
            //holder.imageView = (ImageView) row.findViewById(R.id.grid_item_camera_view);
            //holder.progressBar = (ProgressBar) row.findViewById(R.id.progress_camera_connection);
//            holder.cameraName = (TextView) row.findViewById(R.id.grid_item_camera_name);
            holder.imageNoVideo = (ImageView)row.findViewById(R.id.grid_item_image_no_video);
            holder.cameraView = (DvrCameraSurfaceView) row.findViewById(R.id.grid_item_camera_view);

            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        DvrCamera camera = getItem(position);

        if (camera != null  && camera.isConnected() && !camera.isInitialised()) {
            //holder.cameraView.setZOrderOnTop(true);
            camera.setCameraView(holder.cameraView);
            //holder.cameraName.setText(item.getName());

            camera.setInitialised(true);
            camera.play();
        }

        holder.imageNoVideo.setVisibility(camera.isConnected() ? View.GONE : View.VISIBLE);

        return row;
    }


    private static class ViewHolder {
        ImageView imageNoVideo;
        //ProgressBar progressBar;
        //TextView cameraName;
        DvrCameraSurfaceView cameraView;
    }
}
