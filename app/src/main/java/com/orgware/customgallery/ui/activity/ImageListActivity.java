package com.orgware.customgallery.ui.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.orgware.customgallery.R;
import com.orgware.customgallery.adapter.folderdetail.FolderDetailAdapter;
import com.orgware.customgallery.model.FolderDetailItem;
import com.orgware.customgallery.app.AppConstants;
import com.orgware.customgallery.base.BaseActivity;
import com.orgware.customgallery.util.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandagopal on 12/14/16.
 */
public class ImageListActivity extends BaseActivity {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;

  private String folderID;
  private List<FolderDetailItem> fileList = new ArrayList<>();
  private FolderDetailAdapter adapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_recyclerview);
    ButterKnife.bind(this);

    if (getIntent().getExtras() != null) {
      folderID = getIntent().getExtras().getString(AppConstants.FOLDER_ID);
    }

    recyclerView.setLayoutManager(
        new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
    recyclerView.setItemAnimator(null);

    adapter = new FolderDetailAdapter(this);
    recyclerView.setAdapter(adapter);

    getImagesFromFolderID();
  }

  private void getImagesFromFolderID() {
    final String[] projection = {
        MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.BUCKET_ID
    };
    Cursor cursor =
        getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
            folderID,  /* put particular ID of folder */
            null, MediaStore.Images.Media.DATE_TAKEN + " DESC");

    if (cursor == null) {
      return;
    }

    if (cursor.moveToFirst()) {

      int imgId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

      int imgData = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

      do {
        String id = cursor.getString(imgId);
        String data = cursor.getString(imgData);
        File file = new File(data);
        String fileSize = FileUtils.getSizeFromFile(file.length());
        fileList.add(new FolderDetailItem(id, file, "", fileSize, file.getAbsolutePath()));
      } while (cursor.moveToNext());
    }

    adapter.setFolderItem(fileList);
  }
}
