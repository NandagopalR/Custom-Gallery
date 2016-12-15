package com.orgware.customgallery.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.orgware.customgallery.R;
import com.orgware.customgallery.adapter.folder.FolderAdapter;
import com.orgware.customgallery.app.AppConstants;
import com.orgware.customgallery.base.BaseActivity;
import com.orgware.customgallery.model.FolderItem;
import java.util.ArrayList;
import java.util.List;

public class FolderActivity extends BaseActivity implements FolderAdapter.ClickManager {

  private List<FolderItem> folderItemList = new ArrayList<>();
  private FolderAdapter folderAdapter;

  @BindView(R.id.recyclerview) RecyclerView recyclerView;

  public static void getCallingInstance(Context context) {
    context.startActivity(new Intent(context, FolderActivity.class));
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setItemAnimator(new DefaultItemAnimator());

    folderAdapter = new FolderAdapter(this, this);

    recyclerView.setAdapter(folderAdapter);

    getImageFolders();
  }

  private void getImageFolders() {

     /* For get Folder name from sdcrd & memory card*/
    final String[] projection = {
        MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
        MediaStore.Images.Media.DATA
    };

    String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";
    String BUCKET_ORDER_BY = "MAX(datetaken) DESC";

    try {
      Cursor cursor =
          getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
              BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);
      if (cursor == null) {
        return;
      }

      if (cursor.moveToFirst()) {

        int bucketColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        int idColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
        int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        folderItemList.clear();
        do {
          // Get the field values
          String folderName = cursor.getString(bucketColumn);
          String id = cursor.getString(idColumn);
          String folderIcon = cursor.getString(dataColumn);
            /*For get particular id of each Folder*/
          String folderId = MediaStore.Images.Media.BUCKET_ID + "=" + id;
          folderItemList.add(new FolderItem(folderName, folderIcon, folderId));
        } while (cursor.moveToNext());
      }
      folderAdapter.setFolderItemList(folderItemList);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override public void onItemClick(FolderItem folderItem) {
    startActivity(new Intent(this, ImageListActivity.class).putExtra(AppConstants.FOLDER_ID,
        folderItem.getFolderId()));
  }
}
