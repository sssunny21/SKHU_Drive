package com.jhjg.skhu_drive.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhjg.skhu_drive.Controller.FileManager;
import com.jhjg.skhu_drive.Controller.SettingManager;
import com.jhjg.skhu_drive.Model.FileData;
import com.jhjg.skhu_drive.Model.FolderData;
import com.jhjg.skhu_drive.Model.ParentFile;
import com.jhjg.skhu_drive.R;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.List;

/**
 * Created by kangjungu1 on 2016. 4. 10..
 */
public class Folder_FileAdapter extends RecyclerView.Adapter<Folder_FileAdapter.ViewHolder>{
    public static final String TAG = "Folder_FileAdapter";
    public List<ParentFile> items;
    private FolderData folderData;
    private FileData fileData;
    private Context context;
    private long fileSize;

    private static final int FOLDER_TYPE = 0;
    private static final int FILE_TYPE = 1;
    private boolean type;

    public static int getFolderType() {
        return FOLDER_TYPE;
    }

    public static int getFileType() {
        return FILE_TYPE;
    }

    public Folder_FileAdapter(Context context, List<ParentFile> items) {
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
            type = true;
        }else {
            //listView
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.professor_list_item, parent, false);
            type = false;
        }
        return new ViewHolder(view);
    }

    //데이터와 뷰를 묶어줌
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //폴더이면
        if(items.get(position).getType() == 0){
            Log.e(TAG,"폴더");
            folderData = (FolderData) items.get(position);

            holder.name.setText(folderData.getFolder_name());
            holder.folderSize.setText("");

        }else{
            //파일이면
            Log.e(TAG,"파일");
            fileData = (FileData) items.get(position);
            holder.name.setText(fileData.getFiles_name());
            if (!type) {
                //list인 경우
                //이미 다운로드한 파일
                if (FileManager.getInstance().checkExistFile(fileData.getFiles_name())) {
                    holder.nextIcon.setVisibility(View.INVISIBLE);
                } else {
                    //다운로드 안한파일
                    holder.nextIcon.setIcon(MaterialDrawableBuilder.IconValue.DOWNLOAD);
                }
            }
            holder.icon.setIcon(MaterialDrawableBuilder.IconValue.FILE);
            if(fileData.getFiles_size()>1024*1024){
                fileSize = fileData.getFiles_size()/(1024*1024);
                holder.folderSize.setText("크기 : "+fileSize+" MB");
            }else if(fileData.getFiles_size()>1024){
                fileSize = fileData.getFiles_size()/(1024);
                holder.folderSize.setText("크기 : "+fileSize+" KB");
            }else{
                fileSize = fileData.getFiles_size();
                holder.folderSize.setText("");
            }


        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    //폴더이면
                    if(items.get(position).getType() == FOLDER_TYPE ) {
                        Log.e(TAG,"폴더 클릭");
                        folderData = (FolderData) items.get(position);
                        mListener.onItemClicked(folderData.getFolder_name(),folderData.getFolder_id(),FOLDER_TYPE);
                    }else{
                        Log.e(TAG,"파일 클릭");
                        //TODO: 파일 클릭시 파일다운로드 넣기
                        fileData = (FileData) items.get(position);
                        mListener.onItemClicked(fileData.getFiles_name(),fileData.getFiles_id(),FILE_TYPE);
                    }
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
        if(items.get(position).getType() == 0){
            folderData = (FolderData) items.get(position);
            return folderData.getParent_id();
        }else{
            fileData = (FileData) items.get(position);
            return fileData.getFolder_id();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, folderSize;
        public LinearLayout item;
        public MaterialIconView icon,nextIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            folderSize = (TextView) itemView.findViewById(R.id.professor_name);
            name = (TextView) itemView.findViewById(R.id.professor_content);
            item = (LinearLayout) itemView.findViewById(R.id.professor_item);
            icon = (MaterialIconView)itemView.findViewById(R.id.professor_icon);
            if(!type){
                //list view
                nextIcon = (MaterialIconView)itemView.findViewById(R.id.professor_nextIcon);
            }

        }
    }


    public interface FolderItemClickListener {
        void onItemClicked(String name,long fd_id,int type);
    }

    private FolderItemClickListener mListener;

    public void setFolderItemClickListener(FolderItemClickListener listener){
        mListener = listener;
    }

}
