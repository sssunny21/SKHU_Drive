package com.jhjg.skhu_drive.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

import com.jhjg.skhu_drive.Controller.FileManager;
import com.jhjg.skhu_drive.Controller.NetworkManager;
import com.jhjg.skhu_drive.Controller.SettingManager;
import com.jhjg.skhu_drive.Model.FileData;
import com.jhjg.skhu_drive.Model.FolderData;
import com.jhjg.skhu_drive.Model.ParentFile;
import com.jhjg.skhu_drive.R;
import com.jhjg.skhu_drive.Support.Address;
import com.jhjg.skhu_drive.Support.DividerItemDecoration;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kangjungu1 on 2016. 4. 10..
 */
public class Folder_FileFragment extends Fragment {

    public static final String TAG = "Folder_FileFragment";
    private Context context;
    private RecyclerView recyclerView;
    private Folder_FileAdapter adapter;
    private List<ParentFile> list;
    private FolderData data;
    private FileData fileData;
    private ProgressDialog progressDialog;
    private long fd_id;
    private String[] parameterName, parameter;
    private View v;
    private JSONObject jsonObj;


    public void notiAdapter(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parameterName = new String[2];
        parameter = new String[2];
        //번들로 넘긴 데이터 가져오기
        fd_id = getArguments().getLong("fd_id", -1);
        Log.e(TAG, "fd_id " + fd_id);

        v = inflater.inflate(R.layout.fragment_professor, container, false);
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

        //animator 등록
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        list = new ArrayList<>();

        //folderData를 가져옴
        getFolderData();

        //adpater 등록
        adapter = new Folder_FileAdapter(context, list);


        adapter.setFolderItemClickListener(new Folder_FileAdapter.FolderItemClickListener() {
            @Override
            public void onItemClicked(String name,long fd_id, int type) {
                Log.e(TAG, "fd_id " + fd_id);

                //폴더 클릭인 경우
                if (type == adapter.getFolderType()) {
                    //이런식으로 fragment 교체가능
                    Folder_FileFragment newFragment = new Folder_FileFragment();
                    //bundle 생성
                    Bundle args = new Bundle();
                    args.putLong("fd_id", fd_id);
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
                } else {
                    //파일 클릭인 경우
                    //파일이존재한 경우 파일 열기
                    if (FileManager.getInstance().checkExistFile(name)){
                        FileManager.getInstance().viewFile(context,name);
                    }else {
                        //파일 다운로드
                        downloadFile(name, (int) fd_id);

                    }
                }
            }

        });

        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        parameterName[0] = "fd_id";
        parameter[0] = fd_id + "";

        NetworkManager.getInstance().getList(new BaseJsonHttpResponseHandler<JSONArray>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONArray response) {
                Log.e(TAG, "onSuccess");
                try {
                    for (int i = 0; i < response.length(); i++) {
                        Log.e(TAG, "1");
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
                if (!isFailure) {
                    Log.e(TAG, "parseResponse " + rawJsonData);
                    return new JSONArray(rawJsonData);
                }

                return null;
            }
        }, Address.getInstance().getFolderFileType(), parameterName, parameter);

        //파일데이터를 가져온다.
        getFileData();
    }

    private void getFileData() {


        parameterName[0] = "fd_id";
        parameter[0] = fd_id + "";

        //1부분에 folder id 넣어주기
        //handler,리턴타입,파라미터이름,파라미터순으로 넣어준다.
        NetworkManager.getInstance().getList(new BaseJsonHttpResponseHandler<JSONArray>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        jsonObj = response.getJSONObject(i);

                        //파일인경우
                        fileData = new FileData();

                        fileData.setFiles_date(jsonObj.getString("files_date"));
                        fileData.setFiles_id(jsonObj.getInt("files_id"));
                        fileData.setFiles_name(jsonObj.getString("files_name"));
                        fileData.setFiles_size(jsonObj.getInt("files_size"));
                        fileData.setFolder_id(jsonObj.getInt("folder_id"));
                        fileData.setType(1);
                        list.add(fileData);
                        //adapter notify
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
        }, Address.getInstance().getFILE_TYPE(), parameterName, parameter);
    }


    private void downloadFile(final String name, int id) {

        progressDialog = progressDialog.show(context, "Downloading...", "");
        parameterName[0] = "id";
        parameter[0] = id + "";

        BinaryHttpResponseHandler b = new BinaryHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {

                Log.e(TAG, "binary data " + binaryData.length);
                progressDialog.dismiss();
                fileData = new FileData();

                try {
                    FileManager.getInstance().downloadGFileToJFolder(Folder_FileFragment.this,name,binaryData);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                progressDialog.dismiss();
                Snackbar.make(v,"파일 다운로드 실패",Snackbar.LENGTH_LONG).show();
            }
        };
        //handler,리턴타입,파라미터이름,파라미터순으로 넣어준다.
        NetworkManager.getInstance().getList(b, Address.getInstance().getDOWNLOAD_TYPE(), parameterName, parameter);


    }

}
