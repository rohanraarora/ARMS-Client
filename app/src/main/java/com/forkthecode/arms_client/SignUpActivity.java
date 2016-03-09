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
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    Firebase rootRef;
    Firebase userRef;
    EditText emailEditText;
    EditText passwordEditText;
    EditText nameEditText;
    EditText phoneEditText;
    ProgressDialog mDialog;
    String uid;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Firebase.setAndroidContext(this);
        rootRef = new Firebase(Constant.ROOT_URL);
        userRef = rootRef.child("users");
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Loading...");
        emailEditText = (EditText)this.findViewById(R.id.signUpEmailTextView);
        passwordEditText = (EditText)this.findViewById(R.id.signUpPasswordTextView);
        nameEditText = (EditText)this.findViewById(R.id.signUpNameEditText);
        phoneEditText = (EditText)this.findViewById(R.id.signUpPhoneEditText);
        mSharedPreferences = getSharedPreferences("ARMS",MODE_PRIVATE);
    }

    public void login(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void gotoRestaurant(){
        Intent intent = new Intent(this,RestaurantDetail.class);
        intent.putExtra(Constant.SHARED_PREF_UID_KEY,uid);
        startActivity(intent);
        this.finish();
    }


    public void signup(View view){
        mDialog.show();
        final String email = emailEditText.getEditableText().toString();
        rootRef.createUser(email, passwordEditText.getEditableText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                mDialog.dismiss();
                uid = result.get("uid").toString();
                User user = new User();
                user.setEmail(email);
                user.setName(nameEditText.getEditableText().toString());
                user.setPhone(phoneEditText.getEditableText().toString());
                userRef.child(uid).setValue(user);
                Toast.makeText(getApplicationContext(), "Successfully created user account with uid: " + result.get("uid"), Toast.LENGTH_LONG).show();
                gotoRestaurant();
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                mDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Sign Up Error",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
