package com.forkthecode.arms_client;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Firebase rootRef,userRef,restaurantRef,menuRef;
    String uid;
    RecyclerView recyclerView;
    MenuRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Firebase.setAndroidContext(this);
        sharedPreferences = getSharedPreferences("ARMS", MODE_PRIVATE);
        uid = sharedPreferences.getString(Constant.SHARED_PREF_UID_KEY, "");
        rootRef = new Firebase("https://arms.firebaseio.com/");
        userRef = rootRef.child("users").child(uid);
        restaurantRef = userRef.child("restaurant");
        menuRef = restaurantRef.child("menu");
        recyclerView = (RecyclerView)this.findViewById(R.id.recyclerView);
        mAdapter = new MenuRecyclerAdapter(new MenuRecyclerAdapter.ItemClickListener() {
            @Override
            public void onClick(int position) {
                com.forkthecode.arms_client.MenuItem menuItem = mAdapter.getItem(position);
                Toast.makeText(getApplicationContext(),menuItem.getName(),Toast.LENGTH_LONG).show();
            }
        },
                com.forkthecode.arms_client.MenuItem.class,
                R.layout.row_menu_item, MenuRecyclerAdapter.MenuViewHolder.class,menuRef);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addMenu(View view){
        Map<String,String> map = new HashMap<>();
        map.put("name","Chicken Tikka");
        map.put("price","450");
        map.put("category","Tikka");
        map.put("description","san  fsi ni iuf afifhia ifah iahf aihfiahf aifh aiafi hi ai fhai");
        map.put("imageUrl", "jsajfaijf");
        menuRef.push().setValue(map);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
