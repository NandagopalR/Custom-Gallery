package com.orgware.customgallery.adapter.folderdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.orgware.customgallery.R;
import com.orgware.customgallery.model.FolderDetailItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandagopal on 12/14/16.
 */
public class FolderDetailAdapter
    extends RecyclerView.Adapter<FolderDetailAdapter.FolderDetailViewHolder> {

  private Context context;
  private List<FolderDetailItem> folderDetailItemList;
  private LayoutInflater inflater;

  public FolderDetailAdapter(Context context) {
    this.context = context;
    folderDetailItemList = new ArrayList<>();
    inflater = LayoutInflater.from(context);
  }

  public void setFolderItem(List<FolderDetailItem> itemList) {
    if (folderDetailItemList == null) return;

    folderDetailItemList.clear();
    folderDetailItemList.addAll(itemList);
    notifyDataSetChanged();
  }

  @Override public FolderDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = inflater.inflate(R.layout.item_folder_detail, parent, false);
    return new FolderDetailViewHolder(view);
  }

  @Override public void onBindViewHolder(FolderDetailViewHolder holder, int position) {
    FolderDetailItem item = folderDetailItemList.get(position);
    holder.setDataToView(item);
  }

  @Override public int getItemCount() {
    return folderDetailItemList.size();
  }

  class FolderDetailViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_folder_detail) ImageView imageView;

    public FolderDetailViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setDataToView(FolderDetailItem item) {
      Glide.with(context)
          .load(item.getFilePath())
          .placeholder(R.drawable.folder)
          .error(R.drawable.folder)
          .centerCrop()
          .animate(R.anim.slide_up)
          .into(imageView);
    }
  }
}
