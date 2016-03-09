package com.forkthecode.arms_client;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class AddMenu extends AppCompatActivity {

    EditText nameEditText;
    EditText priceEditText;
    EditText categoryEditText;
    EditText descEditText;
    Firebase rootRef,userRef,restaurantRef,menuRef;
    String uid;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);
        Firebase.setAndroidContext(this);
        nameEditText = (EditText)this.findViewById(R.id.addNameEditText);
        priceEditText = (EditText)this.findViewById(R.id.addPriceEditText);
        categoryEditText = (EditText)this.findViewById(R.id.addCategoryEditText);
        descEditText = (EditText)this.findViewById(R.id.addDescEditText);
        sharedPreferences = getSharedPreferences("ARMS", MODE_PRIVATE);
        uid = sharedPreferences.getString(Constant.SHARED_PREF_UID_KEY, "");
        rootRef = new Firebase(Constant.ROOT_URL);
        userRef = rootRef.child("users").child(uid);
        restaurantRef = userRef.child("restaurant");
        menuRef = restaurantRef.child("menu");
    }

    public void save(View view){
        com.forkthecode.arms_client.MenuItem menuItem = new com.forkthecode.arms_client.MenuItem(
                nameEditText.getEditableText().toString(),descEditText.getEditableText().toString(),
                categoryEditText.getEditableText().toString(),priceEditText.getEditableText().toString(),"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSWl_RX0aXBjpYcLIjiMtudJpysZMH4iHi5dLhTV6OAz9k0IpV-rVqmDVY");
        menuRef.push().setValue(menuItem);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_menu, menu);
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
