package com.freeloaers.hikeathon.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.jivesoftware.smack.PacketListener;

import java.util.LinkedList;


public class RegisterActivity extends Activity implements XmppClient.MyConnectionListener {

    private static final String TAG = "RegisterActivity";
    private XmppClient xmppClient;
    private PacketListener messageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        xmppClient = new XmppClient(this);

        init();
    }

    private EditText userNameTextView;
    private EditText passwordTextView;

    private void init() {
        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameTextView = (EditText) findViewById(R.id.register_username);
                passwordTextView = (EditText) findViewById(R.id.register_password);

                register(userNameTextView.getText().toString(), passwordTextView.getText().toString());
            }
        });
        Button login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameTextView = (EditText) findViewById(R.id.register_username);
                passwordTextView = (EditText) findViewById(R.id.register_password);

                xmppClient.logIn(userNameTextView.getText().toString(), passwordTextView.getText().toString());
            }
        });
    }

    private void register(String username, String password) {
        String server = "hackathon.hike.in";
        xmppClient.register(username + server, password);

    }

    @Override
    public void onAuthenticationSuccess() {
        Log.e(TAG, "RegisterSuccessful");

        AppPreferences.setPreference(Constants.PASSWORD_KEY, passwordTextView.getText().toString());
        AppPreferences.setPreference(Constants.USERNAME_KEY, userNameTextView.getText().toString());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "RegisterSuccessful", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onAuthenticationFailure() {

    }

    @Override
    public void onGetFriendList(LinkedList<Contacts> friendList) {

    }
}
