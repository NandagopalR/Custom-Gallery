package com.orgware.customgallery.adapter.folder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.orgware.customgallery.model.FolderItem;
import com.orgware.customgallery.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandagopal on 12/14/16.
 */
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

  private List<String> folderNameList = new ArrayList<>();
  private List<String> folderIconList = new ArrayList<>();
  private List<FolderItem> folderItemList;
  private Context context;
  private LayoutInflater inflater;
  private ClickManager clickManager;

  public FolderAdapter(Context context, ClickManager clickManager) {
    this.context = context;
    this.clickManager = clickManager;
    inflater = LayoutInflater.from(context);
    folderItemList = new ArrayList<>();
  }

  public void setFolderItemList(List<FolderItem> itemList) {
    if (folderItemList == null) return;
    folderItemList.clear();
    folderItemList.addAll(itemList);
    notifyDataSetChanged();
  }

  public interface ClickManager {
    void onItemClick(FolderItem folderItem);
  }

  @Override public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.item_folder, parent, false);
    return new FolderViewHolder(view);
  }

  @Override public void onBindViewHolder(FolderViewHolder holder, int position) {

    FolderItem folderItem = folderItemList.get(position);
    holder.setFolderDataToView(folderItem);
  }

  @Override public int getItemCount() {
    return folderItemList.size();
  }

  class FolderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.folder_icon) ImageView folderIcon;
    @BindView(R.id.folder_name) TextView folderName;

    public FolderViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
    }

    public void setFolderDataToView(FolderItem item) {
      folderName.setText(item.getFolderName());

      Glide.with(context)
          .load(item.getFolderIcon())
          .placeholder(R.drawable.folder)
          .error(R.drawable.folder)
          .centerCrop()
          .animate(R.anim.slide_up)
          .into(folderIcon);
    }

    @Override public void onClick(View view) {

      int position = getAdapterPosition();

      if (position < 0) return;

      FolderItem folderItem = folderItemList.get(position);

      if (clickManager != null) {
        clickManager.onItemClick(folderItem);
      }
    }
  }
}
