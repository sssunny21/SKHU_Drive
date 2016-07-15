package com.jhjg.skhu_drive.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhjg.skhu_drive.Controller.SettingManager;
import com.jhjg.skhu_drive.Model.FolderData;
import com.jhjg.skhu_drive.Model.ParentFile;
import com.jhjg.skhu_drive.R;

import java.util.List;

/**
 * Created by kangjungu1 on 2016. 4. 10..
 */
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder>{
    public static final String TAG = "FolderAdapter";
    public List<ParentFile> items;
    private FolderData folderData;
    private Context context;

    public FolderAdapter(Context context, List<ParentFile> items) {
        this.items = items;
        this.context = context;
    }

    //뷰홀더를 그려서 리턴
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        //gridView
        if(SettingManager.getInstance().getBoolean(SettingManager.getActionView())){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.professor_grid_list_item, parent, false);
        }else {
            //listView
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.professor_list_item, parent, false);
        }

        return new ViewHolder(view);
    }

    //데이터와 뷰를 묶어줌
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        folderData = (FolderData)items.get(position);

        holder.name.setText(folderData.getFolder_name());
        holder.folderSize.setText("");
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    folderData = (FolderData)items.get(position);
                    Log.e(TAG,"click "+folderData.getFolder_id());
                    mListener.onItemClicked(folderData.getFolder_id());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        folderData = (FolderData) items.get(position);
        return folderData.getParent_id();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, folderSize;
        public LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            folderSize = (TextView) itemView.findViewById(R.id.professor_name);
            name = (TextView) itemView.findViewById(R.id.professor_content);
            item = (LinearLayout) itemView.findViewById(R.id.professor_item);
        }
    }


    public interface FolderItemClickListener {
        void onItemClicked(long folder_id);
    }

    private FolderItemClickListener mListener;

    public void setFolderItemClickListener(FolderItemClickListener listener){
        mListener = listener;
    }

}
