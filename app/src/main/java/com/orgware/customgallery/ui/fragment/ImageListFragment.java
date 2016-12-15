package com.orgware.customgallery.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.orgware.customgallery.R;
import com.orgware.customgallery.base.BaseFragment;

/**
 * Created by nandagopal on 12/14/16.
 */
public class ImageListFragment extends BaseFragment {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public static void getCallingInstance(Context context) {

  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_recyclerview, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    recyclerView.setItemAnimator(null);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
  }
}
