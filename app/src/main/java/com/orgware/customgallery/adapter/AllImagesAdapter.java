package com.orgware.customgallery.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.orgware.customgallery.R;
import com.orgware.customgallery.model.ImageDataModel;
import java.util.List;

/**
 * Created by nandagopal on 12/11/16.
 */
public class AllImagesAdapter extends ArrayAdapter<ImageDataModel> {

  private Context context;
  private List<ImageDataModel> dataModelList;
  private LayoutInflater inflater;

  public AllImagesAdapter(Context context, int resource, List<ImageDataModel> objects) {
    super(context, resource, objects);
    this.context = context;
    this.dataModelList = objects;
    inflater = LayoutInflater.from(context);
  }

  @Override public int getCount() {
    return dataModelList.size();
  }

  @NonNull @Override public View getView(int position, View convertView, ViewGroup parent) {

    if (convertView == null) {
      convertView = inflater.inflate(R.layout.item_gallery_view, parent, false);
    }

    ImageDataModel imageDataModel = dataModelList.get(position);

    Bitmap thumbnail = null;
    try {
      thumbnail = getThumbnail(context.getContentResolver(), imageDataModel.getImagePath());
    } catch (Exception e) {
      e.printStackTrace();
    }

    ImageView path = (ImageView) convertView.findViewById(R.id.imgPath);

    Glide.with(context)
        .load(imageDataModel.getImagePath())
        .asBitmap()
        .placeholder(R.drawable.ic_image_place_holder)
        .error(R.drawable.ic_image_place_holder)
        .centerCrop()
        .animate(R.anim.slide_up)
        .into(path);

    return convertView;
  }

  public static Bitmap getThumbnail(ContentResolver cr, String path) throws Exception {

    Cursor ca = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        new String[] { MediaStore.MediaColumns._ID }, MediaStore.MediaColumns.DATA + "=?",
        new String[] { path }, null);
    if (ca != null && ca.moveToFirst()) {
      int id = ca.getInt(ca.getColumnIndex(MediaStore.MediaColumns._ID));
      ca.close();
      return MediaStore.Images.Thumbnails.getThumbnail(cr, id,
          MediaStore.Images.Thumbnails.MICRO_KIND, null);
    }

    ca.close();
    return null;
  }
}
