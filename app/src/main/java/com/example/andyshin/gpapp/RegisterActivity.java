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

public class RegisterActivity extends MainActivity implements View.OnClickListener {

    //EditText and Button objects
    EditText register;
    EditText regPassword;
    EditText confirmPass;
    EditText email;

    Button confirm;
    Button reset;

    boolean correct = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //EditText objects set to their respective layout edittexts
        register = (EditText) findViewById(R.id.regUserIDEditText);
        register.setOnClickListener(this);
        regPassword = (EditText) findViewById(R.id.regPasswordEditText);
        regPassword.setOnClickListener(this);
        confirmPass = (EditText) findViewById(R.id.regConfirmPassEditText);
        confirmPass.setOnClickListener(this);
        email = (EditText) findViewById(R.id.regEmailEditText);
        email.setOnClickListener(this);

        //Buttons with their on click listeners
        confirm = (Button) findViewById(R.id.confirmButton);
        confirm.setOnClickListener(this);

        reset = (Button) findViewById(R.id.resetButton);
        reset.setOnClickListener(this);

        getSupportActionBar().setElevation(0);

    }

    //On Click method
    @Override
    public void onClick(View v) {
        //Checking which button is pressed
        switch (v.getId()) {
            //if either text fields are clicked they become blank
            case R.id.regUserIDEditText:
                if (register.getText().toString().matches("User ID")) {
                    register.setText("");
                    register.getText().clear();
                }
                break;
            case R.id.regPasswordEditText:
                if (regPassword.getText().toString().matches("Password")) {
                    regPassword.setText("");
                    regPassword.getText().clear();
                }
                break;
            case R.id.regConfirmPassEditText:
                if (confirmPass.getText().toString().matches("Confirm Password")) {
                    confirmPass.setText("");
                    confirmPass.getText().clear();
                }
                break;
            case R.id.regEmailEditText:
                if (email.getText().toString().matches("E-mail")) {
                    email.setText("");
                    email.getText().clear();
                }
                break;

            //case for confirm button click
            case R.id.confirmButton:
                //saving edit text fields to shared preferences
                SharedPreferences sharedPrefs = getApplicationContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("username", register.getText().toString());

                //Strings for text field info
                String pass = regPassword.getText().toString();
                String cPass = confirmPass.getText().toString();
                //password and confirm password need to be the same otherwise it will not be saved
                if (pass.contentEquals(cPass)) {
                    editor.putString("password", regPassword.getText().toString());
                    editor.putString("confirm password", confirmPass.getText().toString());
                    correct = true;
                } else {
                    Toast.makeText(this, "Please ensure Passwords match", Toast.LENGTH_SHORT).show();
                    correct = false;
                }
                editor.putString("email", email.getText().toString());

                //go back to the login page after registering
                if (correct == true) {
                    Intent confirmIntent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(confirmIntent);
                    Toast.makeText(this, "Register Completed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please Complete all Fields", Toast.LENGTH_SHORT).show();
                }
                editor.commit();
                break;

            //if reset button is clicked, all text fields are set to be blank
            case R.id.resetButton:
                register.setText("");
                regPassword.setText("");
                confirmPass.setText("");
                email.setText("");
                Toast.makeText(this, "Field Entries Reset", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
