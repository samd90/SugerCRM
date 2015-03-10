package com.androidcrm.wakensys.sugercrm.AddNewEntry;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidcrm.wakensys.sugercrm.R;
import com.androidcrm.wakensys.sugercrm.fragment.Fragment_Entries;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.INPUT_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.JSON;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.METHOD;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MODULE_NAME;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.NAME_VALUE_LIST;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RESPONSE_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.REST_DATA;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SESSION;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SET_ENTRY;

/**
 * Created by Sameera on 2/16/2015.
 */
public class AddNewCall extends Fragment implements View.OnClickListener {

    public static final String TAG = AddNewCall.class.getSimpleName();

    public static AddNewCall newInstance(){
        return new AddNewCall();
    }

    private String sessionId, restUrl, response,id,start_date, start_time;
    private EditText txt_subject, txt_duration, txt_parent_name,txt_date, txt_time_;
    private Button btn_save, btn_cancel, btn_date, btn_time;
    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Spinner durationMin, status_1, status_2, relatedTo_moduleName, reminder_time, email_reminder_time;
    private List<String> durationList, status1,status2, relatedModuleNames, timePeriodList;
    private ArrayAdapter<String> durationAdapter, status1Adapter, status2Adapter, relatedModuleNameAdapter, RemindertimePeriodAdapter;
    private CheckBox popup, email_all_invites;
    private String popupRemainder, emailRemainder,durationMinutes, status_1_name, status_2_name, reminder_time_period, related_module_name, email_reminder_time_period;
    private boolean fromEdit = false;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_new_calls, container, false);

        // Get sessionId and RestUrl from AddNewItem_selectMenu class
        Bundle b = getArguments();
        sessionId = b.getString("sessionId");
        restUrl = b.getString("restUrl");
        response = b.getString("callDetails");
        // Check where request came from
        fromEdit = b.getBoolean("from_edit");   // if fromEdit Boolean true request came from Edit Button

        txt_subject = (EditText) rootView.findViewById(R.id.txt_subject);
        txt_duration = (EditText) rootView.findViewById(R.id.txt_duraion);
        txt_parent_name = (EditText) rootView.findViewById(R.id.txt_entry_name);
        txt_date = (EditText) rootView.findViewById(R.id.txt_start_date);
        txt_time_ = (EditText) rootView.findViewById(R.id.txt_time);
        // Get the reference of buttons
        btn_save = (Button) rootView.findViewById(R.id.btn_save);
        btn_cancel = (Button) rootView.findViewById(R.id.btn_cancel);
        btn_date = (Button) rootView.findViewById(R.id.date);
        btn_time = (Button) rootView.findViewById(R.id.time);
        // Get the reference of Spinners
        status_1 = (Spinner) rootView.findViewById(R.id.spinner_in_out);
        status_2 = (Spinner) rootView.findViewById(R.id.spinner_plan);
        relatedTo_moduleName = (Spinner) rootView.findViewById(R.id.spinner_entry_name);
        reminder_time = (Spinner) rootView.findViewById(R.id.spinner_popup);
        email_reminder_time = (Spinner) rootView.findViewById(R.id.spinner_email);
        durationMin = (Spinner) rootView.findViewById(R.id.spinner_duration);
        // Get Reference for CheckBoxes
        popup = (CheckBox) rootView.findViewById(R.id.checkbox_popup);
        email_all_invites = (CheckBox) rootView.findViewById(R.id.checkbox_email);

        // Set on ClickListener on buttons
        btn_save.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        btn_time.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        if(fromEdit == true){
            try{
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Please Wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();

                JSONObject name_value_list = new JSONObject(response);
                Log.d("jsonObj", "" + name_value_list.toString());

                JSONObject name_ = name_value_list.getJSONObject("name");
                String name = name_.getString("value");

                JSONObject date_start_ = name_value_list
                        .getJSONObject("date_start");
                String date_start = date_start_.getString("value");

                JSONObject duration_hours_ = name_value_list.getJSONObject("duration_hours");
                String duration_hours = duration_hours_.getString("value");

                JSONObject duration_minutes_ = name_value_list.getJSONObject("duration_minutes");
                String duration_minutes = duration_minutes_.getString("value");

                JSONObject status_ = name_value_list.getJSONObject("status");
                String status = status_.getString("value");

                JSONObject direction_ = name_value_list
                        .getJSONObject("direction");
                String direction = direction_.getString("value");

                JSONObject parent_type_ = name_value_list
                        .getJSONObject("parent_type");
                String parent_type = parent_type_.getString("value");

                JSONObject parent_name_ = name_value_list
                        .getJSONObject("parent_name");
                String parent_name = parent_name_.getString("value");

                JSONObject reminder_checked_ = name_value_list
                        .getJSONObject("reminder_checked");
                boolean reminder_checked = reminder_checked_.getBoolean("value");

                JSONObject email_reminder_checked_ = name_value_list
                        .getJSONObject("email_reminder_checked");
                boolean email_reminder_checked = email_reminder_checked_.getBoolean("value");

                JSONObject reminder_time_ = name_value_list
                        .getJSONObject("reminder_time");
                String reminder_time_value = reminder_time_.getString("value");

                JSONObject email_reminder_time_ = name_value_list
                        .getJSONObject("email_reminder_time");
                String email_reminder_time_value = email_reminder_time_.getString("value");

                JSONObject id_ = name_value_list
                        .getJSONObject("id");
                id = id_.getString("value");

                // Split date start in to date and time two string
                String a[] = date_start.split(" ");
                for(int i = 0;i < a.length ; i++){
                    start_date = a[i];
                    start_time = a[i];
                }

                // Put String values to Textviews
                txt_subject.setText(name);
                txt_duration.setText(duration_hours);
                txt_parent_name.setText(parent_name);
                txt_date.setText(start_date);
                txt_time_.setText(start_time);
                if(reminder_checked == true){
                    popup.setChecked(true);
                    reminder_time.setVisibility(View.VISIBLE);
                    popupRemainder = "true";
                }else if(reminder_checked == false){
                    reminder_time.setVisibility(View.GONE);
                    popup.setChecked(false);
                    popupRemainder = "false";
                }

                if(email_reminder_checked == true){
                    email_all_invites.setChecked(true);
                    email_reminder_time.setVisibility(View.VISIBLE);
                    emailRemainder = "true";
                }else if(email_reminder_checked == false){
                    email_all_invites.setChecked(false);
                    email_reminder_time.setVisibility(View.GONE);
                    emailRemainder = "false";
                }

                durationMin.setSelection(((ArrayAdapter<String>)durationMin.getAdapter()).getPosition(duration_minutes));
                status_1.setSelection(((ArrayAdapter<String>)status_1.getAdapter()).getPosition(direction));
                status_2.setSelection(((ArrayAdapter<String>)status_2.getAdapter()).getPosition(status));
                reminder_time.setSelection(((ArrayAdapter<String>)reminder_time.getAdapter()).getPosition(reminder_time_value));
                email_reminder_time.setSelection(((ArrayAdapter<String>)email_reminder_time.getAdapter()).getPosition(email_reminder_time_value));
                relatedTo_moduleName.setSelection(((ArrayAdapter<String>)relatedTo_moduleName.getAdapter()).getPosition(parent_type));

            } catch (JSONException e) {
                Log.e("My App", "Could not parse JSON");
                e.printStackTrace();
            }	catch (Exception e){
                e.printStackTrace();
            }
            dialog.dismiss();
        }

        // Array list for DurationMinutes
        durationList = new ArrayList<String>();
        durationList.add("00");
        durationList.add("15");
        durationList.add("30");
        durationList.add("45");
        // Array list for Status Spinner 1
        status1 = new ArrayList<String>();
        status1.add("Indound");
        status1.add("Outbound");
        // Array list for Status Spinner 2
        status2 = new ArrayList<String>();
        status2.add("Planned");
        status2.add("Held");
        status2.add("Not Held");
        // Array list for Related to Modle names
        relatedModuleNames = new ArrayList<String>();
        relatedModuleNames.add("Account");
        relatedModuleNames.add("Bug");
        relatedModuleNames.add("Case");
        relatedModuleNames.add("Contact");
        relatedModuleNames.add("Lead");
        relatedModuleNames.add("Opportunity");
        relatedModuleNames.add("Project");
        relatedModuleNames.add("Project Task");
        relatedModuleNames.add("Target");
        relatedModuleNames.add("Task");
        // Array List for Time Periods
        timePeriodList = new ArrayList<String>();
        timePeriodList.add("1 minutes prior");
        timePeriodList.add("5 minutes prior");
        timePeriodList.add("10 minutes prior");
        timePeriodList.add("15 minutes prior");
        timePeriodList.add("30 minutes prior");
        timePeriodList.add("1 hour prior");
        timePeriodList.add("2 hour prior");
        timePeriodList.add("3 hour prior");
        timePeriodList.add("5 hour prior");
        timePeriodList.add("1 day prior");
        // Set array lists to Array adapters
        status1Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, status1);
        status2Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, status2);
        relatedModuleNameAdapter =  new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, relatedModuleNames);
        RemindertimePeriodAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, timePeriodList);
        durationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, durationList);

        status1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relatedModuleNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RemindertimePeriodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set Array Adapter to Spinner
        status_1.setAdapter(status1Adapter);
        status_2.setAdapter(status2Adapter);
        relatedTo_moduleName.setAdapter(relatedModuleNameAdapter);
        reminder_time.setAdapter(RemindertimePeriodAdapter);
        email_reminder_time.setAdapter(RemindertimePeriodAdapter);
        durationMin.setAdapter(durationAdapter);
        // Set OnItemSelectListeners to Spinners
        durationMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                durationMinutes = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        status_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status_1_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        status_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status_2_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        reminder_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reminder_time_period = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        email_reminder_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                email_reminder_time_period = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        relatedTo_moduleName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                related_module_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Set Onclick Listners for checkboxes
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popup.isChecked() == true){
                   reminder_time.setVisibility(View.VISIBLE);
                    popupRemainder = "true";
                }else if(popup.isChecked() == false){
                    reminder_time.setVisibility(View.GONE);
                    popupRemainder = "false";
                }
            }
        });
        email_all_invites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set Visibility for reminder Checkboxes
                if(email_all_invites.isChecked() == true){
                    email_reminder_time.setVisibility(View.VISIBLE);
                    emailRemainder = "true";
                }else if(email_all_invites.isChecked() == false){
                    email_reminder_time.setVisibility(View.GONE);
                    emailRemainder = "false";
                }
            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.time:
                final View dialogView1 = View.inflate(getActivity(), R.layout.date_time_picker, null);
                final AlertDialog alertDialog1 = new AlertDialog.Builder(getActivity()).create();

                dialogView1.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker1 = (DatePicker) dialogView1.findViewById(R.id.date_picker);
                        TimePicker timePicker1 = (TimePicker) dialogView1.findViewById(R.id.time_picker);

                        Calendar calendar = new GregorianCalendar(datePicker1.getYear(),
                                datePicker1.getMonth(),
                                datePicker1.getDayOfMonth(),
                                timePicker1.getCurrentHour(),
                                timePicker1.getCurrentMinute());

                        long time1 = calendar.getTimeInMillis();

                        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
                        String end_time = format1.format(time1);
                        txt_time_.setText(end_time);
                        SimpleDateFormat frmDate1 =new SimpleDateFormat("yyyy-MM-dd");
                        String end_date=frmDate1.format(time1);
                        txt_date.setText(end_date);

                        alertDialog1.dismiss();
                    }});
                alertDialog1.setView(dialogView1);
                alertDialog1.show();
                break;
            case R.id.btn_save:
                if(fromEdit == true){
                    new UpdateEntryCall().execute(popupRemainder, emailRemainder, durationMinutes, status_1_name, status_2_name, related_module_name, restUrl, sessionId, email_reminder_time_period, reminder_time_period, id);
                }else {
                    new AddNewEntryAccount().execute(popupRemainder, emailRemainder, durationMinutes, status_1_name, status_2_name, related_module_name, restUrl, sessionId, email_reminder_time_period, reminder_time_period);
                }
                break;
            case R.id.btn_cancel:
                AddNewItem_SelectMenu fragment = new AddNewItem_SelectMenu();
                Bundle b = new Bundle();
                b.putString("restUrl", restUrl);
                b.putString("sessionId", sessionId);
                fragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                break;
            default:
                break;
        }

    }
    class UpdateEntryCall extends AsyncTask<String, Void, Boolean>{
        private String durationMinutes, status_1_name, status_2_name, reminder_time_period, related_module_name,email_reminder_time_period;

        private Map<String, String> nameValueList;
        private ProgressDialog dialog;
        private String subject = txt_subject.getText().toString();
        private String date = txt_date.getText().toString();
        private String time = txt_time_.getText().toString();
        private String durationHours = txt_duration.getText().toString();
        private String parentName = txt_parent_name.getText().toString();

        private JSONObject subjectValue = new JSONObject();
        private JSONObject dateTimeValue = new JSONObject();
        private JSONObject durationHoursValue = new JSONObject();
        private JSONObject durationMinutesValue = new JSONObject();
        private JSONObject directionValue = new JSONObject();
        private JSONObject statusValue = new JSONObject();
        private JSONObject parent_typeValue = new JSONObject();
        private JSONObject parent_nameValue = new JSONObject();
        private JSONObject popupRemainderValue = new JSONObject();
        private JSONObject emailRemainderValue = new JSONObject();
        private JSONObject RemainderTimeValue = new JSONObject();
        private JSONObject emailRemainderTimeValue = new JSONObject();
        private JSONObject idValue = new JSONObject();

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please Wait..");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String popupRemainder = params[0];
            String emailRemainder = params[1];
            durationMinutes = params[2];
            status_1_name = params[3];
            status_2_name = params[4];
            related_module_name = params[5];
            String restUrl = params[6];
            String sessionId = params[7];
            email_reminder_time_period = params[8];
            reminder_time_period = params[9];
            id = params[10];

            boolean successful = false;
            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, "Calls");

            try {
                JSONArray nameValueArray = new JSONArray();
                idValue.put("name", "id");
                idValue.put("value", id);
                nameValueArray.put(idValue);
                subjectValue.put("name", "name");
                subjectValue.put("value", subject);
                nameValueArray.put(subjectValue);
                dateTimeValue.put("name", "date_start");
                dateTimeValue.put("value", date+" "+ time);
                nameValueArray.put(dateTimeValue);
                durationHoursValue.put("name", "duration_hours");
                durationHoursValue.put("value", durationHours);
                nameValueArray.put(durationHoursValue);
                durationMinutesValue.put("name", "duration_minutes");
                durationMinutesValue.put("value", durationMinutes);
                nameValueArray.put(durationMinutesValue);
                directionValue.put("name", "direction");
                directionValue.put("value", status_1_name);
                nameValueArray.put(directionValue);
                statusValue.put("name", "status");
                statusValue.put("value", status_2_name);
                nameValueArray.put(statusValue);
                parent_nameValue.put("name", "parent_name");
                parent_nameValue.put("value", parentName);
                nameValueArray.put(parent_nameValue);
                parent_typeValue.put("name", "parent_type");
                parent_typeValue.put("value", related_module_name);
                nameValueArray.put(parent_typeValue);
                emailRemainderValue.put("name", "email_reminder_checked");
                emailRemainderValue.put("value", emailRemainder);
                nameValueArray.put(emailRemainderValue);
                popupRemainderValue.put("name", "reminder_checked");
                popupRemainderValue.put("value", popupRemainder);
                nameValueArray.put(popupRemainderValue);
                emailRemainderTimeValue.put("name", "email_reminder_time");
                emailRemainderTimeValue.put("value", email_reminder_time_period);
                nameValueArray.put(emailRemainderTimeValue);
                RemainderTimeValue.put("name", "reminder_time");
                RemainderTimeValue.put("value", reminder_time_period);
                nameValueArray.put(RemainderTimeValue);

                data.put(NAME_VALUE_LIST, nameValueArray);

                String restData = org.json.simple.JSONValue.toJSONString(data);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost req = new HttpPost(restUrl);
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD, SET_ENTRY));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, restData));
                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Send POST request
                httpClient.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse res = httpClient.execute(req);
                if (res.getEntity() == null) {
                    Log.e(TAG, "FAILED TO CONNECT!");
                    successful = true;

                }
                String response = EntityUtils.toString(res.getEntity()).toString();
                JSONObject jsonResponse = new JSONObject(response);
                Log.i(TAG, "setEntry response : " + response);
                Log.d(TAG,nameValuePairs.toString());


            } catch (IOException ioe) {
                ioe.printStackTrace();
                successful = true;

            } catch (JSONException jsone) {
                jsone.printStackTrace();
                successful = true;

            }
            return successful;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();
            if(result != true){
                new AlertDialog.Builder(getActivity())
                        .setTitle("Alert")
                        .setMessage("Record Successfully Added.")
                        .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Fragment_Entries fragment = new Fragment_Entries();
                                Bundle b = new Bundle();
                                b.putString("restUrl", restUrl);
                                b.putString("sessionId", sessionId);
                                b.putString("moduleName", "Calls");
                                fragment.setArguments(b);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        }

    }

    class AddNewEntryAccount extends AsyncTask<String , Void, Boolean> {
        private String durationMinutes, status_1_name, status_2_name, reminder_time_period, related_module_name,email_reminder_time_period;

        private Map<String, String> nameValueList;
        private ProgressDialog dialog;
        private String subject = txt_subject.getText().toString();
        private String date = txt_date.getText().toString();
        private String time = txt_time_.getText().toString();
        private String durationHours = txt_duration.getText().toString();
        private String parentName = txt_parent_name.getText().toString();

        private JSONObject subjectValue = new JSONObject();
        private JSONObject dateTimeValue = new JSONObject();
        private JSONObject durationHoursValue = new JSONObject();
        private JSONObject durationMinutesValue = new JSONObject();
        private JSONObject directionValue = new JSONObject();
        private JSONObject statusValue = new JSONObject();
        private JSONObject parent_typeValue = new JSONObject();
        private JSONObject parent_nameValue = new JSONObject();
        private JSONObject popupRemainderValue = new JSONObject();
        private JSONObject emailRemainderValue = new JSONObject();
        private JSONObject RemainderTimeValue = new JSONObject();
        private JSONObject emailRemainderTimeValue = new JSONObject();

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please Wait..");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String popupRemainder = params[0];
            String emailRemainder = params[1];
            durationMinutes = params[2];
            status_1_name = params[3];
            status_2_name = params[4];
            related_module_name = params[5];
            String restUrl = params[6];
            String sessionId = params[7];
            email_reminder_time_period = params[8];
            reminder_time_period = params[9];

            boolean successful = false;
            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, "Calls");

            try {
                JSONArray nameValueArray = new JSONArray();
                        subjectValue.put("name", "name");
                        subjectValue.put("value", subject);
                        nameValueArray.put(subjectValue);
                        dateTimeValue.put("name", "date_start");
                        dateTimeValue.put("value", date+" "+ time);
                        nameValueArray.put(dateTimeValue);
                        durationHoursValue.put("name", "duration_hours");
                        durationHoursValue.put("value", durationHours);
                        nameValueArray.put(durationHoursValue);
                        durationMinutesValue.put("name", "duration_minutes");
                        durationMinutesValue.put("value", durationMinutes);
                        nameValueArray.put(durationMinutesValue);
                        directionValue.put("name", "direction");
                        directionValue.put("value", status_1_name);
                        nameValueArray.put(directionValue);
                        statusValue.put("name", "status");
                        statusValue.put("value", status_2_name);
                        nameValueArray.put(statusValue);
                        parent_nameValue.put("name", "parent_name");
                        parent_nameValue.put("value", parentName);
                        nameValueArray.put(parent_nameValue);
                        parent_typeValue.put("name", "parent_type");
                        parent_typeValue.put("value", related_module_name);
                        nameValueArray.put(parent_typeValue);
                        emailRemainderValue.put("name", "email_reminder_checked");
                        emailRemainderValue.put("value", emailRemainder);
                        nameValueArray.put(emailRemainderValue);
                        popupRemainderValue.put("name", "reminder_checked");
                        popupRemainderValue.put("value", popupRemainder);
                        nameValueArray.put(popupRemainderValue);
                        emailRemainderTimeValue.put("name", "email_reminder_time");
                        emailRemainderTimeValue.put("value", email_reminder_time_period);
                        nameValueArray.put(emailRemainderTimeValue);
                        RemainderTimeValue.put("name", "reminder_time");
                        RemainderTimeValue.put("value", reminder_time_period);
                        nameValueArray.put(RemainderTimeValue);

                data.put(NAME_VALUE_LIST, nameValueArray);

                String restData = org.json.simple.JSONValue.toJSONString(data);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost req = new HttpPost(restUrl);
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD, SET_ENTRY));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, restData));
                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Send POST request
                httpClient.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse res = httpClient.execute(req);
                if (res.getEntity() == null) {
                    Log.e(TAG, "FAILED TO CONNECT!");
                    successful = true;

                }
                String response = EntityUtils.toString(res.getEntity()).toString();
                JSONObject jsonResponse = new JSONObject(response);
                Log.i(TAG, "setEntry response : " + response);
                Log.d(TAG,nameValuePairs.toString());


            } catch (IOException ioe) {
                ioe.printStackTrace();
                successful = true;

            } catch (JSONException jsone) {
                jsone.printStackTrace();
                successful = true;

            }
            return successful;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();
            if(result != true){
                new AlertDialog.Builder(getActivity())
                        .setTitle("Alert")
                        .setMessage("Record Successfully Added.")
                        .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Fragment_Entries fragment = new Fragment_Entries();
                                Bundle b = new Bundle();
                                b.putString("restUrl", restUrl);
                                b.putString("sessionId", sessionId);
                                b.putString("moduleName", "Calls");
                                fragment.setArguments(b);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        }

    }
}
