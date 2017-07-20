package com.example.rushpe.rushe;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

//import static com.example.rushpe.confirmpassword;
//import static com.example.rushpe.app1.R.id.email;
//import static com.example.rushpe.app1.R.id.username;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by alejandro on 5/16/2017.
 */

public class RegisterActivity extends LoginActivity {

    private Button mRegister = null;
    private EditText mEmailView;
    private EditText mNameView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    @Override
    /** As soon as the page loads create button instance, give
     *  message if account created successfully
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        mEmailView = (EditText) findViewById(R.id.email);
        mNameView = (EditText) findViewById(R.id.name);
        mPasswordView = (EditText) findViewById(R.id.password);
        mConfirmPasswordView = (EditText) findViewById(R.id.confirmpassword);

        mRegister = (Button) findViewById(R.id.goMainMenuActivity);
        mRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(attemptRegister() && attemptInsert()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                    alertDialog.setTitle("Success!");
                    alertDialog.setMessage("Account successfully created");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(view.getContext(),MainMenuActivity.class);
                                    startActivity(i);
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });

    }

    private boolean attemptRegister() {

        mEmailView.setError(null);
        mNameView.setError(null);
        mPasswordView.setError(null);
        mConfirmPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String username = mNameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmpassword = mConfirmPasswordView.getText().toString();

        View focusView = null;

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            focusView.requestFocus();
            return false;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            focusView.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(username)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            focusView.requestFocus();
            return false;
        } else if(!isNameValid(username)) {
            mNameView.setError(getString(R.string.error_invalid_username));
            focusView = mNameView;
            focusView.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            focusView.requestFocus();
            return false;
        } else if(!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            focusView.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(confirmpassword)) {
            mConfirmPasswordView.setError(getString(R.string.error_field_required));
            focusView = mConfirmPasswordView;
            focusView.requestFocus();
            return false;
        } else if(!isPasswordValid(confirmpassword)) {
            mConfirmPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mConfirmPasswordView;
            focusView.requestFocus();
            return false;
        }

        if (!password.equals(confirmpassword)) {
            mPasswordView.setError(getString(R.string.error_different_password));
            mConfirmPasswordView.setError(getString(R.string.error_different_password));
            focusView = mPasswordView;
            focusView = mConfirmPasswordView;
            focusView.requestFocus();
            return false;
        }

        return true;
    }

    private boolean attemptInsert() {
        mEmailView.setError(null);

        View focusView = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String email = mEmailView.getText().toString();
        String username = mNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        try {
            URL url = new URL("http://parallel.gg/rags-to-riches/register-account.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode("" + email,"UTF-8")+"&";
            post_data += URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode("" + username,"UTF-8")+"&";
            post_data += URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode("" + password,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="";
            String line="";
            while((line = bufferedReader.readLine())!= null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            if(result.equals("FALSE")) {
                mEmailView.setError(getString(R.string.error_duplicate_email));
                focusView = mEmailView;
                focusView.requestFocus();
                return false;
            } else if(result.equals("TRUE")) {
                return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if the user actually entered an email
     * @param email the user's entered email
     * @return  true if valid
     */
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * Checks if the user entered a long enough password
     * @param password  the user's entered password
     * @return  true if valid
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Checks if the user entered a long enough username
     * @param username  the user's entered username
     * @return  true if valid
     */
    private boolean isNameValid(String username) {
        return username.length() > 4;
    }

    public boolean onCreateOptionsmenu(Menu menu){
        return false;
    }


}
