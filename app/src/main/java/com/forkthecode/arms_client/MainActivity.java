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

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Firebase rootRef;
    Firebase userRef;
    EditText emailEditText;
    EditText passwordEditText;
    ProgressDialog mDialog;
    String uid;
    SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getSharedPreferences("ARMS",MODE_PRIVATE);
        if(mSharedPreferences.contains(Constant.SHARED_PREF_UID_KEY)){
            uid = mSharedPreferences.getString(Constant.SHARED_PREF_UID_KEY,"");
        }
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        rootRef = new Firebase("https://arms.firebaseio.com/");
        userRef = rootRef.child("users");
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Loading...");
        emailEditText = (EditText)this.findViewById(R.id.emailTextView);
        passwordEditText = (EditText)this.findViewById(R.id.passwordTextView);
    }

    public void signUp(View view){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void signin(View view){
        mDialog.show();
        rootRef.authWithPassword(emailEditText.getEditableText().toString(), passwordEditText.getEditableText().toString(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                mDialog.dismiss();
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(Constant.SHARED_PREF_UID_KEY,authData.getUid());
                Toast.makeText(getApplication(),"User ID: " + authData.getUid() + ", Provider: " + authData.getProvider(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                mDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
