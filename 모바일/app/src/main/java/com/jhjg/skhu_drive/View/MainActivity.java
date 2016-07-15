package com.jhjg.skhu_drive.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jhjg.skhu_drive.Controller.NetworkManager;
import com.jhjg.skhu_drive.R;
import com.jhjg.skhu_drive.Support.Address;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = "MainActivity";
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FrameLayout mainFragment;
    private FragmentTransaction transaction;
    private String id;
    private Menu menu;

    private final static int PROFESSOR_FRAGMENT = 1;
    private final static int FOLDER_FRAGMENT = 2;
    private final static int FILE_FRAGMENT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //id를 가져온다.
        id = getIntent().getStringExtra("id");

        mainFragment = (FrameLayout) findViewById(R.id.main_fragment);

        //맨처음에 시작할때는 교수 fragment를 보여준다.
        fragmentReplace(PROFESSOR_FRAGMENT);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //원래 있던 헤더뷰 삭제 후
        navigationView.removeHeaderView(navigationView.getHeaderView(0));
        //헤더뷰 다시 넣어 줌
        TextView view = (TextView) navigationView.inflateHeaderView(R.layout.nav_header_main).findViewById(R.id.textView);
        view.setText(id);

        //메뉴 추가
        menu = navigationView.getMenu();
        getFavoriteList();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // fragment 영역에 fragment를 set 해주는 function
    public void fragmentReplace(int reqNewFragmentIndex) {
        Fragment newFragment = null;
        if (reqNewFragmentIndex == PROFESSOR_FRAGMENT) {
            newFragment = new ProfessorFragment();  // replace fragment
        }

        transaction = getSupportFragmentManager().beginTransaction(); // FragmentTransaction 생성
        transaction.setCustomAnimations(R.anim.layout_leftin, R.anim.layout_leftout);
        transaction.replace(R.id.main_fragment, newFragment); // fragment를 remove 후 add,
        transaction.commit();   // Commit the transaction
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //즐겨 찾기에서 눌렀을 경우
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //이런식으로 fragment 교체가능
        Folder_FileFragment newFragment = new Folder_FileFragment();
        //bundle 생성
        Bundle args = new Bundle();
        args.putLong("fd_id", id);
        //번들넣기
        newFragment.setArguments(args);

        //transaction 생성
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //fragment animation
        transaction.setCustomAnimations(R.anim.layout_leftin, R.anim.layout_leftout, R.anim.popup_enter, R.anim.popup_exit);
        //프레그먼트교체
        transaction.replace(R.id.main_fragment, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();//이런식으로 fragment 교체가능

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    public void getFavoriteList(){

        BaseJsonHttpResponseHandler<JSONArray> handler = new BaseJsonHttpResponseHandler<JSONArray>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONArray response) {
                JSONObject jsonObj;
                Log.e(TAG, "onSuccess");
                try {
                    for (int i = 0; i < response.length(); i++) {
                        Log.e(TAG, "1");
                        jsonObj = response.getJSONObject(i);
                        //json을 가져와 item에 넣어준다.
                        menu.add(0,jsonObj.getInt("folder_id"),0,jsonObj.getString("folder_name"));

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
                    Log.e(TAG, "parseResponse" + rawJsonData);
                    return new JSONArray(rawJsonData);
                }
                return null;
            }
        };

        NetworkManager.getInstance().getList(handler, Address.getInstance().getMY_FAVORITE_FOLDER_TYPE(),null,null);
    }


}

