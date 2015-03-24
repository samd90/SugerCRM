package com.androidcrm.wakensys.sugercrm;

import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.APPLICATION;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.ID;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.INPUT_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.JSON;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.LOGIN;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.METHOD;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MODULES;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.NAME_VALUE_LIST;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.PASSWORD;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RESPONSE_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.REST_DATA;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.USER_AUTH;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.USER_NAME;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.client.HttpClient;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcrm.wakensys.sugercrm.data_sync.DatabaseHandler;
import com.androidcrm.wakensys.sugercrm.data_sync.Login;
import com.androidcrm.wakensys.sugercrm.data_sync.Module;
import com.androidcrm.wakensys.sugercrm.data_sync.SessionManagement;

public class LoginController extends ActionBarActivity implements OnClickListener {

    private String sessionId = "";
    public static final String MODULES = "modules";
    private EditText userName, pass, url;
    private TextView txt_logo;
    private Button login;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private static HttpClient httpClient = new DefaultHttpClient();
    DatabaseHandler db;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        session = new SessionManagement(getApplicationContext());

        db = new DatabaseHandler(this);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        userName = (EditText) findViewById(R.id.et_user);
        pass = (EditText) findViewById(R.id.et_pass);
        url = (EditText) findViewById(R.id.et_url);
        login = (Button) findViewById(R.id.btn_login);
        txt_logo = (TextView) findViewById(R.id.logo_txt);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        if(session.isLoggedIn() == true){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");
        txt_logo.setTypeface(type);

        login.setOnClickListener(this);

        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_login:
                    Log.d("click", "login button clicked");

                    String restUrl = url.getText().toString();
                    String username = userName.getText().toString();
                    String password = pass.getText().toString();



                   if (TextUtils.isEmpty(restUrl) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {

                        Toast.makeText(this, "Please Enter Login Details !", Toast.LENGTH_LONG).show();

                    } else {

                        new AttemptLogin().execute(restUrl, username, password);

                    }


                    break;
                default:
                    break;
            }
        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // Create md5 password
    public static final String md5(final String toEncrypt) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(toEncrypt.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return "";
        }
    }

    // Send Login details to server
    class AttemptLogin extends AsyncTask<String, Void, Boolean> {

        JSONObject credentials = new JSONObject();
        String response;
        String restUrl = null;
        String username = null;
        String password = null;
        String str_error = null;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginController.this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Boolean doInBackground(String... params) {

            boolean successful = false;

            try {

/*
            restUrl = params[0];
            username = params[1];
            password = params[2];
*/

            username = "sameera";
            password = "wakensys123";
            restUrl = "http://crm2.demoplease.com/service/v4_1/rest.php";


            Log.d("restUrl", restUrl + " " + username + " " + password);

                credentials.put(USER_NAME, username);

                password = md5(password);
                credentials.put(PASSWORD, password);

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(credentials);

                JSONObject userAuth = new JSONObject();
                userAuth.put(USER_AUTH, credentials);

                Log.d("user", username);
                Log.d("pass", password);
                Log.d("restURL", restUrl);

                HttpPost reqLogin = new HttpPost(restUrl);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD, LOGIN));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, userAuth
                        .toString()));
                nameValuePairs.add(new BasicNameValuePair(APPLICATION, ""));
                nameValuePairs.add(new BasicNameValuePair(NAME_VALUE_LIST, ""));

                Log.d("nameValuePairs", nameValuePairs.toString());

                reqLogin.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Log.i(LOG_TAG, EntityUtils.toString(reqLogin.getEntity()));
                httpClient.getParams().setBooleanParameter(
                        CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                // Send POST request
                HttpResponse resLogin = httpClient.execute(reqLogin);

                response = EntityUtils.toString(resLogin.getEntity());

            } catch (JSONException e) {

                successful = true;
                str_error = e.toString();

            } catch (ClientProtocolException e) {

                e.printStackTrace();
                successful = true;
                str_error = e.toString();

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
                successful = true;
                str_error = e.toString();

            } catch (IOException e) {

                e.printStackTrace();
                str_error = e.toString();
                successful = true;
            }catch (Exception e) {

                e.printStackTrace();
                str_error = e.toString();
                successful = true;
            }

            return successful;

        }

        protected void onPostExecute(Boolean result) {

            progressDialog.dismiss();

            if (result != true) {

                Log.d("Login - response", response);
                JSONObject responseObj = null;

                try {
                    responseObj = new JSONObject(response);

                    sessionId = responseObj.get(ID).toString();
                    Log.d("sessionId", sessionId);
                    Log.d("Login", "ok");

                    JSONObject modulesArray = responseObj.getJSONObject(NAME_VALUE_LIST);

                    JSONObject _user_id = modulesArray.getJSONObject("user_id");
                    String user_id = _user_id.getString("value");
                    JSONObject _user_name = modulesArray.getJSONObject("user_name");
                    String user_name = _user_name.getString("value");

                    db.addLogin(new Login(user_id, user_name));

                    Log.d("TAG",user_id +"       " + user_name );
                    Log.d("Reading: ", "Reading all login..");
                    List<Login> login = db.getAllLogin();

                    for(Login lo : login){
                        String log = "ID : " + lo.get_user_id() + " name : " + lo.get_user_name();
                        Log.d("Reading" , log);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                    str_error = e.toString();

                }

                session.createLoginSession(restUrl, username, password, sessionId);

                Bundle b = new Bundle();
                b.putString("sessionId", sessionId);
                b.putString("restUrl", restUrl);

                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.putExtras(b);

                startActivity(i);

            }else
            {

                Toast.makeText(getBaseContext(),"Error !! " + str_error, Toast.LENGTH_LONG).show();

            }


        }
    }

}