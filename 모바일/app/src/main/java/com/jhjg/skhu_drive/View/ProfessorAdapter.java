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
import com.jhjg.skhu_drive.Model.ProfessorDriveData;
import com.jhjg.skhu_drive.R;

import java.util.List;

/**
 * Created by kangjungu1 on 2016. 4. 7..
 */
public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.ViewHolder> {

    public static final String TAG = "ProfessorAdapter";
    public List<ProfessorDriveData> items;
    private ProfessorDriveData item;
    private Context context;
    //false이면 리스트, true이면 그리드

    public ProfessorAdapter(Context context, List<ProfessorDriveData> items) {
        Log.e(TAG,"adapter 생성");
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
        item = items.get(position);

        holder.name.setText(item.getP_name());
        holder.content.setText(item.getDrive_name());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListenr != null){
                    item = items.get(position);
                    mListenr.onItemClicked(item.getDrive_id());
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
        return items.get(position).getDrive_id();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, content;
        public LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.professor_name);
            content = (TextView) itemView.findViewById(R.id.professor_content);
            item = (LinearLayout) itemView.findViewById(R.id.professor_item);
        }
    }

    public interface ProfessorItemClickListener {
        void onItemClicked(long dr_id);
    }

    private ProfessorItemClickListener mListenr;

    public void setProfessorItemClickListener(ProfessorItemClickListener listener){
        mListenr = listener;
    }
}
