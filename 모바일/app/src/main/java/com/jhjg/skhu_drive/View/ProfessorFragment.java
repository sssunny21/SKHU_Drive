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
import com.jhjg.skhu_drive.Model.ProfessorDriveData;
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

public class ProfessorFragment extends Fragment {

    public static final String TAG = "ProfessorFragment";
    private Context context;
    private RecyclerView recyclerView;
    private ProfessorAdapter adapter;
    private List<ProfessorDriveData> list;
    private ProfessorDriveData data;
    private ProgressDialog progressDialog;
    private String[] parameterName,parameter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.fragment_professor,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.professor_RecyclerView);

        //decoration
        recyclerView.setHasFixedSize(true);

        setHasOptionsMenu(true);
        //gridView
        if(SettingManager.getInstance().getBoolean(SettingManager.getActionView())){
            recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        }else{
        //listview
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(context,R.drawable.divider));
        }


        recyclerView.setItemAnimator(new DefaultItemAnimator());

        list = new ArrayList<>();
        //list를 db에서 불러와서 넣어주면 될듯  일단은 됨
        //notify도 넣어주기

        //Data를 가져온다.
        getData();

        adapter = new ProfessorAdapter(context,list);
        recyclerView.setAdapter(adapter);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setProfessorItemClickListener(new ProfessorAdapter.ProfessorItemClickListener() {
            @Override
            public void onItemClicked(long dr_id) {
                Log.e(TAG,"dr_id : "+dr_id);
                //이런식으로 fragment 교체가능
                FolderFragment newFragment = new FolderFragment();
                //bundle 생성
                Bundle args = new Bundle();
                args.putLong("dr_id",dr_id);
                //번들넣기
                newFragment.setArguments(args);

                //transaction 생성
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                //fragment animation
                transaction.setCustomAnimations(R.anim.layout_leftin,R.anim.layout_leftout,R.anim.popup_enter,R.anim.popup_exit);


                //프레그먼트교체
                transaction.replace(R.id.main_fragment,newFragment);
                //이전화면으로 돌아올수있게 해준다.
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.e(TAG, "onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_view);
        if (SettingManager.getInstance().getBoolean(SettingManager.getActionView())) {
            //true 이면 그리드니까 list로 바꿔준다
            item.setIcon(R.drawable.view_list_w);
        } else {
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

    private void getData(){

        parameterName = new String[2];
        parameter = new String[2];
        parameterName[0] = "d_id";
        parameter[0] = "1";

        progressDialog = progressDialog.show(context, "Loading...","");

        NetworkManager.getInstance().getList(new BaseJsonHttpResponseHandler<JSONArray>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONArray response) {
                Log.e(TAG,"onSuccess"+response.length());
                try {
                    for (int i = 0; i < response.length(); i++) {
                        Log.e(TAG,"i: "+i);
                        JSONObject jsonObj = response.getJSONObject(i);
                        //json을 가져와 item에 넣어준다.
                        data = new ProfessorDriveData();
                        data.setDrive_id(jsonObj.getInt("drive_id"));
                        data.setDrive_name(jsonObj.getString("drive_name"));
                        data.setD_id(jsonObj.getInt("d_id"));
                        data.setP_id(jsonObj.getInt("p_id"));
                        data.setP_name(jsonObj.getString("u_name"));
                        //list에 추가

                        list.add(data);

                        //adapter notify
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Log.e(TAG,"error : "+e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONArray errorResponse) {
                Log.e(TAG,"onFailure "+throwable.getMessage());
            }

            @Override
            protected JSONArray parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                progressDialog.dismiss();
                Log.e(TAG,"rawJsonData"+rawJsonData);
                if (!isFailure) {
                    return new JSONArray(rawJsonData);
                }
                return null;
            }
        }, Address.getInstance().getProfessorType(), parameterName, parameter);

    }



}
