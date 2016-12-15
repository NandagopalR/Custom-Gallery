package com.orgware.customgallery.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import com.orgware.customgallery.R;
import com.orgware.customgallery.app.AppConstants;
import com.orgware.customgallery.base.BaseActivity;

/**
 * Created by nandagopal on 12/15/16.
 */
public class SplashActivity extends BaseActivity {

  private Handler handler;
  private String[] mPermission = { Manifest.permission.READ_EXTERNAL_STORAGE };

  private Runnable runnable = new Runnable() {
    @Override public void run() {
      startActivity(new Intent(SplashActivity.this, FolderActivity.class));
      finish();
    }
  };

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      boolean allPermissionsGranted = true;
      for (int i = 0, mPermissionLength = mPermission.length; i < mPermissionLength; i++) {
        String permission = mPermission[i];
        if (ActivityCompat.checkSelfPermission(this, permission)
            != PackageManager.PERMISSION_GRANTED) {
          allPermissionsGranted = false;
          break;
        }
      }
      if (!allPermissionsGranted) {
        ActivityCompat.requestPermissions(this, mPermission, AppConstants.REQUEST_PERMISSION_CODE);
      } else {
        navigateToFolderActivity();
      }
    } else {
      navigateToFolderActivity();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == AppConstants.REQUEST_PERMISSION_CODE) {
      boolean allPermissionGranted = true;
      if (grantResults.length == permissions.length) {
        for (int i = 0; i < permissions.length; i++) {
          if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
            allPermissionGranted = false;
            break;
          }
        }
      } else {
        allPermissionGranted = false;
      }
      if (allPermissionGranted) {
        navigateToFolderActivity();
      } else {
        navigateToFolderActivity();
      }
    }
  }

  private void navigateToFolderActivity() {
    handler = new Handler();
    handler.postDelayed(runnable, 1000);
  }
}
