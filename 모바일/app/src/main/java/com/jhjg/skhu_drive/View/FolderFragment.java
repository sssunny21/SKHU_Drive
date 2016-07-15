package com.jhjg.skhu_drive.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jhjg.skhu_drive.Controller.NetworkManager;
import com.jhjg.skhu_drive.Controller.SettingManager;
import com.jhjg.skhu_drive.Model.FileData;
import com.jhjg.skhu_drive.Model.FolderData;
import com.jhjg.skhu_drive.Model.ParentFile;
import com.jhjg.skhu_drive.R;
import com.jhjg.skhu_drive.Support.Address;
import com.jhjg.skhu_drive.Support.DividerItemDecoration;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kangjungu1 on 2016. 4. 10..
 */
public class FolderFragment extends Fragment {
    public static final String TAG = "FolderFragment";
    private Context context;
    private RecyclerView recyclerView;
    private FolderAdapter adapter;
    private List<ParentFile> list;
    private FolderData data;
    private FileData fileData;
    private ProgressDialog progressDialog;
    private long dr_id;
    private String[] parameterName, parameter;

    private JSONObject jsonObj;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach");
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //어뎁터 등등 생성하기
        Log.e(TAG, "ProfessorFragment");

        //번들로 넘긴 데이터 가져오기
        dr_id = getArguments().getLong("dr_id", -1);

        View v = inflater.inflate(R.layout.fragment_professor, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.professor_RecyclerView);

        recyclerView.setHasFixedSize(true);

        setHasOptionsMenu(true);
        //gridView
        if(SettingManager.getInstance().getBoolean(SettingManager.getActionView())){
            recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        }else {
            //listview
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(context, R.drawable.divider));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        list = new ArrayList<>();
        //notify도 넣어주기

        Log.e(TAG, "getFolderData " + dr_id);
        getFolderData();

        adapter = new FolderAdapter(context, list);

        adapter.setFolderItemClickListener(new FolderAdapter.FolderItemClickListener() {
            @Override
            public void onItemClicked(long folder_id) {
                Log.e(TAG, "folder_id " + folder_id);

                Folder_FileFragment newFragment = new Folder_FileFragment();
                //bundle 생성
                Bundle args = new Bundle();
                //fd_id 넣어줌
                args.putLong("fd_id", folder_id);
                //번들넣기
                newFragment.setArguments(args);

                //transaction 생성
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                //fragment animation
                transaction.setCustomAnimations(R.anim.layout_leftin, R.anim.layout_leftout, R.anim.popup_enter, R.anim.popup_exit);
                //프레그먼트교체
                transaction.replace(R.id.main_fragment, newFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }

        });

        recyclerView.setAdapter(adapter);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
            }
        });

        return v;
    }

    @Override
    public void onDetach() {

        super.onDetach();
        Log.e(TAG, "onDetach");
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.e(TAG,"onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_view);
        if(SettingManager.getInstance().getBoolean(SettingManager.getActionView())){
            //true 이면 그리드니까 list로 바꿔준다
            item.setIcon(R.drawable.view_list_w);
        }else{
            //else 이면 list니까 grid로 변경
            item.setIcon(R.drawable.view_grid_w);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_view) {
            if (!SettingManager.getInstance().getBoolean(SettingManager.getActionView())) {
                Log.e(TAG, "grid로 변경 ");
                SettingManager.getInstance().setBoolean(SettingManager.getActionView(), true);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
            } else {
                Log.e(TAG, "list로 변경");
                SettingManager.getInstance().setBoolean(SettingManager.getActionView(), false);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void getFolderData() {

        progressDialog = progressDialog.show(context, "Loading...", "");
        parameterName = new String[2];
        parameter = new String[2];
        parameterName[0] = "dr_id";
        parameter[0] = dr_id + "";

        //1부분에 folder id 넣어주기
        NetworkManager.getInstance().getList(new BaseJsonHttpResponseHandler<JSONArray>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONArray response) {

                Log.e(TAG, "onSuccess");
                try {
                    for (int i = 0; i < response.length(); i++) {
                        jsonObj = response.getJSONObject(i);

                        //json을 가져와 item에 넣어준다.
                        data = new FolderData();
                        data.setDrive_id(jsonObj.getInt("drive_id"));
                        data.setDrive_name(jsonObj.getString("drive_name"));
                        data.setD_id(jsonObj.getInt("d_id"));
                        data.setFolder_id(jsonObj.getInt("folder_id"));
                        data.setFolder_name(jsonObj.getString("folder_name"));
                        data.setId2(jsonObj.getInt("id2"));
                        data.setParent_id(jsonObj.getInt("parent_id"));
                        data.setSfolder_id(jsonObj.getInt("sfolder_id"));
                        data.setSfolder_name(jsonObj.getString("sfolder_name"));
                        data.setSfolder_pw(jsonObj.getString("sfolder_pw"));
                        //folder면 0 파일이면 type1 추가
                        data.setType(0);
                        //list에 추가
                        list.add(data);

                        //adapter notify
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.e(TAG, "error" + e.getMessage());
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONArray errorResponse) {
                Log.e(TAG, "onFailure" + throwable.getMessage());
            }

            @Override
            protected JSONArray parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                progressDialog.dismiss();
                if (!isFailure) {
                    Log.e(TAG, "parseResponse" + rawJsonData);
                    return new JSONArray(rawJsonData);
                }
                return null;
            }
        }, Address.getInstance().getMainFolderType(), parameterName, parameter);
    }
}
