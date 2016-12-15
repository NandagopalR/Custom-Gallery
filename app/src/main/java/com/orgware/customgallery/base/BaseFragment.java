package com.orgware.customgallery.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by nandagopal on 12/14/16.
 */
public class BaseFragment extends Fragment {

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public static final String TAG = "BaseFragment";
}
