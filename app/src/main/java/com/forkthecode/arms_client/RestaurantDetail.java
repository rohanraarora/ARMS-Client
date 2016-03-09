package com.forkthecode.arms_client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class RestaurantDetail extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText nameEditText;
    EditText descriptionEditText;
    EditText addressEditView;
    String uid;
    Firebase rootRef;
    Firebase userRef;
    Firebase restaurantRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        uid = getIntent().getExtras().getString(Constant.SHARED_PREF_UID_KEY, "");
        sharedPreferences = getSharedPreferences("ARMS", MODE_PRIVATE);
        rootRef = new Firebase("https://arms.firebaseio.com/");
        userRef = rootRef.child("users").child(uid);
        restaurantRef = userRef.child("restaurant");
        nameEditText = (EditText)this.findViewById(R.id.restaurantNameEditText);
        descriptionEditText = (EditText)this.findViewById(R.id.restaurantDescriptionEditText);
        addressEditView = (EditText)this.findViewById(R.id.restaurantLocationEditText);
    }

    public void goToHomeActivity(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void saveRestaurant(View view){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.show();
        Map<String,String> map = new HashMap<>();
        map.put("name",nameEditText.getEditableText().toString());
        map.put("desc",descriptionEditText.getEditableText().toString());
        map.put("address",addressEditView.getEditableText().toString());
        restaurantRef.setValue(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                dialog.dismiss();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constant.SHARED_PREF_UID_KEY,uid);
                editor.commit();
                goToHomeActivity();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant_detail, menu);
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
