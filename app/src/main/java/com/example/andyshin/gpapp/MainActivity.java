package com.example.andyshin.gpapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //button and text objects
    Button lButton;
    Button rButton;

    EditText userID;
    EditText password;

    public static final String DEFAULT = "not available";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //EditTexts and their on click listeners
        userID = (EditText) findViewById(R.id.idEditText);
        userID.setOnClickListener(this);
        password = (EditText) findViewById(R.id.passEditText);
        password.setOnClickListener(this);

        //Buttons with their on click listeners
        lButton = (Button) findViewById(R.id.loginButton);
        lButton.setOnClickListener(this);

        rButton = (Button) findViewById(R.id.registerButton);
        rButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.loginButton:
                SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

                //String values of edittext text and value from shared preferences
                String id = userID.getText().toString();
                String pass = password.getText().toString();
                String user = sharedPrefs.getString("username", DEFAULT);
                String pWord = sharedPrefs.getString("password", DEFAULT);
                //Toast.makeText(this, "" + user, Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "" + pWord, Toast.LENGTH_SHORT).show();

                //this would be where code to go to main activity would be
                if (id == user) {
                    Toast.makeText(this, "Incorrect User ID or Password", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(this, "Welcome Back " + user, Toast.LENGTH_SHORT);
                }

                Intent mainIntent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(mainIntent);

                break;

            //register button case, go to register activity
            case R.id.registerButton:
            Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
            break;

            //if either text fields are clicked they become blank
            case R.id.idEditText:
                if (userID.getText().toString().matches("User ID")) {
                    userID.setText("");
                }
                break;
            case R.id.passEditText:
                if (password.getText().toString().matches("Password")) {
                    password.setText("");
                }
                break;
        }
    }
}
