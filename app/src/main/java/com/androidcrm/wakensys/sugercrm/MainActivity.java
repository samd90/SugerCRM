package com.androidcrm.wakensys.sugercrm;

import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SESSION;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.FILTER;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MODULES;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.INPUT_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.JSON;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.LOGOUT;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.METHOD;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RESPONSE_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.REST_DATA;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.GET_AVAILABLE_MODULES;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.androidcrm.wakensys.sugercrm.AdapterClass.DrawerListAdapter;
import com.androidcrm.wakensys.sugercrm.AdapterClass.MenuListAdapter;
import com.androidcrm.wakensys.sugercrm.AddNewEntry.AddNewItem_SelectMenu;
import com.androidcrm.wakensys.sugercrm.data_sync.CrmDatabaseAdapter;
import com.androidcrm.wakensys.sugercrm.fragment.Fragment_Calendar;
import com.androidcrm.wakensys.sugercrm.fragment.Fragment_Entries;
import com.androidcrm.wakensys.sugercrm.fragment.Fragment_home;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static List<String> moduleNames;
    public static String module_name;
    private static HttpClient httpClient = new DefaultHttpClient();
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    String[] mDrawerListItems;
    String sessionId = null;
    String restUrl = null;
    String response = null;
    String moduleLabel = null;
    ArrayList<String> moduleName = new ArrayList<String>();
    List<String> cannotViewModules = new ArrayList<String>();
    List<String> moduleKeyList = new ArrayList<String>();
    CrmDatabaseAdapter databaseAdapter;
    private ProgressDialog dialog;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDrawerItmes;
    private List<String> module_labels = new ArrayList<String>();
    private int[] photo = null;
    private String menuItem;
    private boolean canViewModules = false;
    private String module_name_value;
    private String module_key_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        module_name_value = getTitle().toString();

        databaseAdapter = new CrmDatabaseAdapter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);

        mDrawerItmes = getResources().getStringArray(R.array.drawer_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerList = (ListView) findViewById(android.R.id.list);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        sessionId = b.getString("sessionId");
        restUrl = b.getString("restUrl");

        new LoadingMenuModules().execute(sessionId, restUrl);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this,

                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View v) {
                getSupportActionBar().setTitle(module_name_value);
                invalidateOptionsMenu();
                syncState();

            }

            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
                getSupportActionBar().setTitle(module_name_value);
                invalidateOptionsMenu();
                syncState();

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        // Set Default Layout to App
        if (savedInstanceState == null) {
            Fragment_home fragment_home = new Fragment_home();
            Bundle fb = new Bundle();
            fb.putString("sessionId", sessionId);
            fragment_home.setArguments(fb);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment_home,
                            TAG).commit();

            mDrawerLayout.closeDrawers();
        }


        // Array for Drawer Listview photos
        photo = new int[]{R.drawable.btn_home, R.drawable.ic_launcher,
                R.drawable.btn_ac, R.drawable.btn_co, R.drawable.btn_op,
                R.drawable.btn_le, R.drawable.btn_cl, R.drawable.btn_me,
                R.drawable.btn_ts, R.drawable.btn_ca, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher,};
        moduleNames = new ArrayList<String>();

    }

    /**
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(module_name_value);

    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();

        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {

            case android.R.id.home: {
                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else {
                    mDrawerLayout.openDrawer(mDrawerList);

                }
                return true;
            }

            case R.id.action_settings: {
                Toast.makeText(getApplicationContext(), "Option Item clicked", Toast.LENGTH_LONG).show();
                return true;
            }

            case R.id.action_addNewAccount: {
                //Put session id and resturl into bundle
                Bundle b = new Bundle();
                b.putString("sessionId", sessionId);
                b.putString("restUrl", restUrl);
                //Start AddNewItem class
                AddNewItem_SelectMenu fragment = new AddNewItem_SelectMenu();
                fragment.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).commit();
                return true;
            }

            case R.id.action_logout: {
                //Execute logout class
                new LogoutUser().execute(sessionId, restUrl);
                return true;
            }


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class LoadingMenuModules extends AsyncTask<String, Void, Boolean> {

        private String sessionId = null;
        private String restUrl = null;
        private boolean access = false;
        private String errorMessage = null;
        private int action_edit = 0;
        private int action_delete = 0;
        private int action_list = 0;
        private int action_view = 0;
        private int action_import = 0;
        private int action_export = 0;
        private JSONObject responseObj = null;
        private JSONArray modulesArray = null;
        private JSONArray aclsArray = null;
        private String module_key = "";
        private String module_label = "";
        private Boolean favorite_enabled = false;
        private List<String> module_keys = new ArrayList<String>();
        private List<String> actions = new ArrayList<String>();
        private List<Boolean> favorite_enables = new ArrayList<Boolean>();

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Please Wait..");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            String filter = "mobile";
            boolean successful = false;

            sessionId = params[0];
            restUrl = params[1];

            try {
                JSONObject data = new JSONObject();
                data.put(SESSION, sessionId);
                data.put(FILTER, filter);

                HttpPost req = new HttpPost(restUrl);
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair(METHOD,
                        GET_AVAILABLE_MODULES));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, data
                        .toString()));

                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                Log.e(TAG + " nameValuePairs", nameValuePairs.toString());

                // Send POST request
                httpClient.getParams().setBooleanParameter(
                        CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse res = httpClient.execute(req);

                response = EntityUtils.toString(res.getEntity());
                Log.e(TAG + " response ", response.toString());

            } catch (JSONException jo) {

                successful = true;
                errorMessage = jo.toString();

            } catch (IOException ioe) {

                successful = true;
                errorMessage = ioe.toString();

            } catch (Exception e) {

                successful = true;
                errorMessage = e.toString();

            }

            return successful;

        }

        protected void onPostExecute(Boolean result) {

            dialog.dismiss();

            if (result != true) {

                JSONArray modulesArray = null;

                try {

                    JSONObject responseObj = new JSONObject(response);
                    modulesArray = responseObj.getJSONArray(MODULES);

                    final int numberOfItemsInResp = modulesArray.length();
                    for (int i = 0; i < numberOfItemsInResp; i++) {
                        JSONObject jObj = modulesArray.getJSONObject(i);
                        module_key = jObj.getString("module_key");
                        module_label = jObj.getString("module_label");
                        favorite_enabled = jObj.getBoolean("favorite_enabled");
                        aclsArray = jObj.getJSONArray("acls");

                        module_keys.add(module_key);
                        module_labels.add(module_label);
                        favorite_enables.add(favorite_enabled);

                        for (int j = 0; j < aclsArray.length(); j++) {
                            JSONObject Jobj = aclsArray.getJSONObject(j);

                            String action = Jobj.getString("action");
                            Boolean value = Jobj.getBoolean("access");

                            if (action.equals("delete")) {
                                if (value == false)
                                    action_delete = 0;
                                else if (value == true) {
                                    action_delete = 1;
                                }

                            } else if (action.equals("edit")) {

                                if (value == false)
                                    action_edit = 0;
                                else if (value == true) {
                                    action_edit = 1;
                                }

                            } else if (action.equals("list")) {

                                if (value == false)
                                    action_list = 0;
                                else if (value == true) {
                                    action_list = 1;
                                }

                            } else if (action.equals("view")) {

                                if (value == false) {
                                    action_view = 0;
                                    // cannotViewModules.add(module_key);
                                    // canViewModules = false;
                                } else if (value == true && (!"".equals(module_label) && module_label != null)) {
                                    //Add module name only User Access True

                                    moduleName.add(module_label);
                                    moduleKeyList.add(module_key);
                                    // canViewModules = true;
                                    action_view = 1;
                                }

                            } else if (action.equals("import")) {

                                if (value == false)
                                    action_import = 0;
                                else if (value == true) {
                                    action_import = 1;
                                }

                            } else if (action.equals("export")) {

                                if (value == false)
                                    action_export = 0;
                                else if (value == true) {
                                    action_export = 1;
                                }
                            }
                        }
                        // long id = databaseAdapter.InsertAvailableModules(module_key, module_label, favorite_enabled, action_edit, action_delete, action_list, action_view, action_import, action_export);
                    }


                    MenuListAdapter menuAdapter = new MenuListAdapter(
                            getApplicationContext(), moduleName, photo);
                    mDrawerList.setAdapter(menuAdapter);
                    mDrawerList.setAdapter(menuAdapter);
                } catch (JSONException e) {

                    e.printStackTrace();
                    errorMessage = e.toString();

                } catch (Exception e) {

                    e.printStackTrace();
                    errorMessage = e.toString();

                }
            } else {
            //  Do the SQLite
            }
        }
    }

    class DrawerItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            //  if (canViewModules == true) {
            Bundle b = new Bundle();
            //get moduleName for display entries list
            module_key_value = moduleKeyList.get(position);
            module_name_value = moduleName.get(position);
            b.putString("restUrl", restUrl);
            b.putString("sessionId", sessionId);
            b.putString("module_name", module_key_value);

            Log.d(TAG + " restUrl", restUrl);
            Log.d(TAG + " sessionId", sessionId);
            Log.d(TAG + " module_name", module_key_value);

            switch (module_key_value) {
                case "Home":
                    Fragment_home fragment1 = new Fragment_home();
                    fragment1.setArguments(b);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment1, TAG).commit();
                    mDrawerLayout.closeDrawers();

                    break;
                case "Calendar":
                    Fragment_Calendar fragment2 = new Fragment_Calendar();
                    fragment2.setArguments(b);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment2, TAG).commit();
                    mDrawerLayout.closeDrawers();

                    break;
                default:
                    Fragment_Entries fragment = new Fragment_Entries();
                    fragment.setArguments(b);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).commit();

                    getSupportActionBar().setTitle(module_name_value);
                    mDrawerLayout.closeDrawers();

                    break;
            }

        }

        private void logout() {

            Log.d("click", "logout");

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);

            builder.setMessage("Logout the Application?");

            builder.setPositiveButton("LOGOUT",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            new LogoutUser().execute(sessionId, restUrl);

                            dialog.dismiss();

                        }

                    });

            builder.setNegativeButton("CANCEL",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builder.show();
        }

    }

    class LogoutUser extends AsyncTask<String, Void, Boolean> {

        boolean failure = false;

        String sessionId = null;
        String restUrl = null;
        String response = null;
        String err_msg = null;

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Please Wait..");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            boolean successful = false;
            sessionId = params[0];
            restUrl = params[1];

            try {
                JSONObject data = new JSONObject();
                data.put(SESSION, sessionId);

                HttpPost req = new HttpPost(restUrl);
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD, LOGOUT));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, data
                        .toString()));
                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Send POST request
                httpClient.getParams().setBooleanParameter(
                        CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse res = httpClient.execute(req);

                Log.d("Modules", res.toString());

            } catch (JSONException jo) {

                successful = true;
                err_msg = jo.toString();


            } catch (IOException ioe) {

                successful = true;
                err_msg = ioe.toString();


            }

            return successful;

        }

        protected void onPostExecute(Boolean result) {

            dialog.dismiss();

            if (result != true) {

                Intent i = new Intent(getApplicationContext(), LoginController.class);
                startActivity(i);

            } else {

                Toast.makeText(getBaseContext(), "Error !! " + err_msg, Toast.LENGTH_LONG).show();

            }


        }
    }
}
