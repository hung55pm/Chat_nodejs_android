package ngochung.app.chat_nodejs_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ngochung.app.Applications.MyApplication;
import ngochung.app.Constants.Constants;
import ngochung.app.Fragments.FriendsFragment;
import ngochung.app.Fragments.MessageFragment;
import ngochung.app.Fragments.SearchFragment;
import ngochung.app.Models.Message;
import ngochung.app.Untils.SharedConfig;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener{

    public static Boolean FLAG=false;


    public static String MAIN_LOG="MainActivity";
    private  Toolbar toolbar;
    private TextView tollbarTitle;
    private EditText ed_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
//        mSocket.on("new message", onNewMessage);
//        mSocket.connect();

    }
    public void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ed_search=(EditText)findViewById(R.id.ed_search_friend);
        setSupportActionBar(toolbar);
        tollbarTitle=(TextView)findViewById(R.id.toolbar_title) ;

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tollbarTitle.setText(getResources().getString(R.string.app_name));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        displayView(R.id.nav_message);

    }

    private void displayView(int position) {
        tollbarTitle.setVisibility(View.VISIBLE);
        ed_search.setVisibility(View.GONE);
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case R.id.nav_message:
                fragment = new MessageFragment();
                tollbarTitle.setText(getResources().getString(R.string.message));
                break;
            case R.id.nav_friend:
                fragment = new FriendsFragment();
                tollbarTitle.setText(getResources().getString(R.string.friend));
                break;
            case R.id.nav_search_friend:
                fragment = new SearchFragment();
                tollbarTitle.setText(getResources().getString(R.string.search));
                break;
            case R.id.nav_logout:
                SharedConfig sh= new SharedConfig(this);
                sh.setValueBoolean(SharedConfig.LOGIN,false);
                sh.setValueString(SharedConfig.ACCESS_TOKEN,"");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(title);
        }
    }
    public void showToast(String msg){
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();

    }
    private void attemptSend(EditText mInputMessageView ) {
        String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            return;
        }

        mInputMessageView.setText("");
        SharedConfig share= new SharedConfig(getBaseContext());
        String name=share.getValueString(SharedConfig.ACCESS_TOKEN);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        Message msg= new Message(name,message,date);
        Gson gson = new Gson();
        String jsonInString = gson.toJson(msg);
       // mSocket.emit("new message", jsonInString);
    }
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            JSONObject data = (JSONObject) args[0];
            String username;
            String message;
            try {
                username = data.getString("name");
                message = data.getString("message");
            } catch (JSONException e) {
                return;
            }

            Log.i(MAIN_LOG,username+"   "+message);
            //addMessage(username, message);
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
//        mSocket.disconnect();
//        mSocket.off("new message", onNewMessage);
        FLAG=false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item= menu.findItem(R.id.action_search);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (FLAG==false) {
                    tollbarTitle.setVisibility(View.GONE);
                    ed_search.setVisibility(View.VISIBLE);
                    FLAG=true;
                }else {
                    FLAG=false;
                    if(!ed_search.getText().toString().equals("")){
                        MyApplication.CHECK_SEARCH=true;
                        MyApplication.PHONE_KEY_SEARCH_FRIEND=ed_search.getText().toString();
                        displayView(R.id.nav_search_friend);
                        ed_search.setText("");
                    }else {
                        tollbarTitle.setVisibility(View.VISIBLE);
                        ed_search.setVisibility(View.GONE);

                    }

                }
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        displayView(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

    }


}
