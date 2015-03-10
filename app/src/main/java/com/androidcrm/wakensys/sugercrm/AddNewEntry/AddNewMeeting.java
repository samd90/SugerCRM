package com.androidcrm.wakensys.sugercrm.AddNewEntry;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

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
public class AddNewMeeting extends Fragment implements View.OnClickListener {
    public static final String TAG = AddNewMeeting.class.getSimpleName();

    public static AddNewMeeting newInstance() {
        return new AddNewMeeting();
    }

    private String sessionId, restUrl, module_name,startDate_, startTime_, enddate_, endTime_, id;
    private EditText txt_subject, txt_start_date, txt_start_time, txt_end_date, txt_end_time, txt_entry_name, txt_location;
    private Button btn_save, btn_cancel, btn_start_date, btn_start_time, btn_end_date, btn_end_time;
    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Spinner duration, status_2, relatedTo_moduleName, spinner_popup, spinner_email;
    private List<String> durationList, status2, relatedModuleNames, timePeriodList;
    private ArrayAdapter<String> durationAdapter, status2Adapter, relatedModuleNameAdapter, timePeriodAdapter;
    private CheckBox popup, email_all_invites;
    private String response, popupRemainder, emailRemainder, durationMinutes, status_1_name, status_2_name, reminder_time_period, related_module_name, email_reminder_time_period;
    private boolean from_edit = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_new_meetings, container, false);

        // Get sessionId and RestUrl from AddNewItem_selectMenu class
        Bundle b = getArguments();
        sessionId = b.getString("sessionId");
        restUrl = b.getString("restUrl");
        response = b.getString("meetingDetails");
        from_edit = b.getBoolean("from_edit");
        module_name = b.getString("module_name");

        txt_subject = (EditText) rootView.findViewById(R.id.txt_subject);
        txt_start_date = (EditText) rootView.findViewById(R.id.txt_start_date);
        txt_start_time = (EditText) rootView.findViewById(R.id.txt_start_time);
        txt_end_date = (EditText) rootView.findViewById(R.id.txt_end_date);
        txt_end_time = (EditText) rootView.findViewById(R.id.txt_end_time);
        txt_entry_name = (EditText) rootView.findViewById(R.id.txt_entry_name);
        txt_location = (EditText) rootView.findViewById(R.id.txt_location);
        // Get the reference of buttons
        btn_save = (Button) rootView.findViewById(R.id.btn_save);
        btn_cancel = (Button) rootView.findViewById(R.id.btn_cancel);
        btn_start_date = (Button) rootView.findViewById(R.id.btn_start_date);
        btn_start_time = (Button) rootView.findViewById(R.id.btn_start_time);
        btn_end_date = (Button) rootView.findViewById(R.id.btn_end_date);
        btn_end_time = (Button) rootView.findViewById(R.id.btn_end_time);
        // Get the reference of Spinners
        status_2 = (Spinner) rootView.findViewById(R.id.spinner_in_out);
        relatedTo_moduleName = (Spinner) rootView.findViewById(R.id.spinner_entry_name);
        duration = (Spinner) rootView.findViewById(R.id.spinner_duration);
        spinner_email = (Spinner) rootView.findViewById(R.id.spinner_email);
        spinner_popup = (Spinner) rootView.findViewById(R.id.spinner_popup);
        // Get Reference for CheckBoxes
        popup = (CheckBox) rootView.findViewById(R.id.checkbox_popup);
        email_all_invites = (CheckBox) rootView.findViewById(R.id.checkbox_email);

            // Set on ClickListener on buttons
            btn_save.setOnClickListener(this);
            btn_cancel.setOnClickListener(this);
            btn_start_date.setOnClickListener(this);
            btn_start_time.setOnClickListener(this);
            btn_end_date.setOnClickListener(this);
            btn_end_time.setOnClickListener(this);
            // Array list for DurationMinutes
            durationList = new ArrayList<String>();
            durationList.add("None");
            durationList.add("15 minutes");
            durationList.add("30 minutes");
            durationList.add("45 minutes");
            durationList.add("1 hour");
            durationList.add("1.5 hours");
            durationList.add("2 hours");
            durationList.add("3 hours");
            durationList.add("6 hours");
            durationList.add("1 day");
            durationList.add("2 days");
            durationList.add("3 days");
            durationList.add("1 week");
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
            status2Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, status2);
            relatedModuleNameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, relatedModuleNames);
            timePeriodAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, timePeriodList);
            durationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, durationList);

            durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            status2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            relatedModuleNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            timePeriodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Set Array Adapter to Spinner
            status_2.setAdapter(status2Adapter);
            relatedTo_moduleName.setAdapter(relatedModuleNameAdapter);
            duration.setAdapter(durationAdapter);
            spinner_popup.setAdapter(timePeriodAdapter);
            spinner_email.setAdapter(timePeriodAdapter);
            // Set OnItemSelectListeners to Spinners
            duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    durationMinutes = parent.getItemAtPosition(position).toString();
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
            spinner_popup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    reminder_time_period = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spinner_email.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    if (popup.isChecked() == true) {
                        spinner_popup.setVisibility(View.VISIBLE);
                        popupRemainder = "true";
                    } else if (popup.isChecked() == false) {
                        spinner_popup.setVisibility(View.GONE);
                        popupRemainder = "false";
                    }
                }
            });
            email_all_invites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Set Visibility for reminder Checkboxes
                    if (email_all_invites.isChecked() == true) {
                        spinner_email.setVisibility(View.VISIBLE);
                        emailRemainder = "true";
                    } else if (email_all_invites.isChecked() == false) {
                        spinner_email.setVisibility(View.GONE);
                        emailRemainder = "false";
                    }
                }
            });

        if (from_edit == true) {
            try {
                JSONObject name_value_list = new JSONObject(response);
                Log.d("jsonObj", "" + name_value_list.toString());

                JSONObject m_name_ = name_value_list.getJSONObject("name");
                String name = m_name_.getString("value");

                JSONObject location_ = name_value_list.getJSONObject("location");
                String location = location_.getString("value");

                JSONObject stuts_ = name_value_list.getJSONObject("status");
                String status = stuts_.getString("value");

                JSONObject duration_minutes_ = name_value_list.getJSONObject("duration_minutes");
                String duration_mins = duration_minutes_.getString("value");

                JSONObject description = name_value_list.getJSONObject("direction");
                String direction = description.getString("value");

                JSONObject parent_type_ = name_value_list
                        .getJSONObject("parent_type");
                String parent_type = parent_type_.getString("value");

                JSONObject parent_name_ = name_value_list
                        .getJSONObject("parent_name");
                String parent_name = parent_name_.getString("value");

                JSONObject date_start_ = name_value_list.getJSONObject("date_start");
                String date_start = date_start_.getString("value");

                JSONObject date_end_ = name_value_list
                        .getJSONObject("date_end");
                String date_end = date_end_.getString("value");

                JSONObject reminder_checked_ = name_value_list
                        .getJSONObject("reminder_checked");
                String reminder_checked = reminder_checked_.getString("value");

                JSONObject email_reminder_checked_ = name_value_list
                        .getJSONObject("email_reminder_checked");
                String email_reminder_checked = email_reminder_checked_.getString("value");

                JSONObject email_reminder_time_ = name_value_list
                        .getJSONObject("email_reminder_time");
                String email_reminder_time = email_reminder_time_.getString("value");

                JSONObject reminder_time_ = name_value_list
                        .getJSONObject("reminder_time");
                String reminder_time = reminder_time_.getString("value");

                JSONObject id_ = name_value_list
                        .getJSONObject("id");
                id = id_.getString("value");

                try {
                    // Split Date and Time in to two Strings
                    String a[] = date_start.split(" ");     // split start date and time using Space
                    String c[] = date_end.split(" ");
                    for (int i = 0; i < a.length; i++) {      // Get Start Date and Time
                        startDate_ = a[0];
                        startTime_ = a[1];

                    }
                    for (int j = 0; j < c.length; j++) {
                        enddate_ = c[0];
                        endTime_ = c[1];
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                txt_subject.setText(name);
                txt_start_date.setText(startDate_);
                txt_start_time.setText(startTime_);
                txt_end_date.setText(enddate_);
                txt_end_time.setText(endTime_);
                txt_entry_name.setText(parent_name);
                txt_location.setText(location);

                relatedTo_moduleName.setSelection(((ArrayAdapter<String>)relatedTo_moduleName.getAdapter()).getPosition(parent_type));
                status_2.setSelection(((ArrayAdapter<String>)status_2.getAdapter()).getPosition(status));
                duration.setSelection(((ArrayAdapter<String>)duration.getAdapter()).getPosition(duration_mins));
                spinner_popup.setSelection(((ArrayAdapter<String>)spinner_popup.getAdapter()).getPosition(reminder_time));
                spinner_email.setSelection(((ArrayAdapter<String>)spinner_email.getAdapter()).getPosition(email_reminder_time));

                if (reminder_checked.equals("true")) {
                    spinner_popup.setVisibility(View.VISIBLE);
                    popup.setChecked(true);
                    popupRemainder = "true";
                } else if (reminder_checked.equals("false")) {
                    popup.setChecked(false);
                    spinner_popup.setVisibility(View.GONE);
                    popupRemainder = "false";
                }

                if (email_reminder_checked.equals("true")) {
                    spinner_email.setVisibility(View.VISIBLE);
                    email_all_invites.setChecked(true);
                    emailRemainder = "true";
                } else if (email_reminder_checked.equals("false")) {
                    spinner_email.setVisibility(View.GONE);
                    email_all_invites.setChecked(false);
                    emailRemainder = "false";
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            return rootView;
        }

        @Override
        public void onClick (View v){
            switch (v.getId()) {
                case R.id.btn_start_date:
         /*           // Process to get Current Date
                    final Calendar cal = Calendar.getInstance();
                    mYear = cal.get(Calendar.YEAR);
                    mMonth = cal.get(Calendar.MONTH);
                    mDay = cal.get(Calendar.DAY_OF_MONTH);

                    // Launch Date Picker Dialog
                    DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // Display Selected date in textbox
                                    txt_start_date.setText(year + "-"
                                            + (monthOfYear + 1) + "-" + dayOfMonth);

                                }
                            }, mYear, mMonth, mDay);
                    dpd.show();*/
                    final View dialogView = View.inflate(getActivity(), R.layout.date_time_picker, null);
                    final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

                    dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                            TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                            Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                    datePicker.getMonth(),
                                    datePicker.getDayOfMonth(),
                                    timePicker.getCurrentHour(),
                                    timePicker.getCurrentMinute());

                            long time = calendar.getTimeInMillis();

                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                            String start_time = format.format(time);
                            txt_start_time.setText(start_time);
                            SimpleDateFormat frmDate =new SimpleDateFormat("yyyy-MM-dd");
                            String start_date=frmDate.format(time);
                            txt_start_date.setText(start_date);

                            alertDialog.dismiss();
                        }});
                    alertDialog.setView(dialogView);
                    alertDialog.show();
                    break;

                case R.id.btn_end_date:
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
                            txt_end_time.setText(end_time);
                            SimpleDateFormat frmDate1 =new SimpleDateFormat("yyyy-MM-dd");
                            String end_date=frmDate1.format(time1);
                            txt_end_date.setText(end_date);

                            alertDialog1.dismiss();
                        }});
                    alertDialog1.setView(dialogView1);
                    alertDialog1.show();
                    break;

                case R.id.btn_save:
                    if(from_edit == true) {
                        new EditRecord().execute(popupRemainder, emailRemainder, durationMinutes, status_2_name, related_module_name, restUrl, sessionId, email_reminder_time_period, reminder_time_period, id);
                    }
                    else{
                        new AddNewEntryAccount().execute(popupRemainder, emailRemainder, durationMinutes, status_2_name, related_module_name, restUrl, sessionId, email_reminder_time_period, reminder_time_period);

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
    class EditRecord extends AsyncTask<String, Void, Boolean> {
        private String durationMinutes, status_1_name, status_2_name, reminder_time_period, related_module_name, email_reminder_time_period;

        private Map<String, String> nameValueList;
        private ProgressDialog dialog;
        private String subject = txt_subject.getText().toString();
        private String startTime = txt_start_time.getText().toString();
        private String start_date = txt_start_date.getText().toString();
        private String end_date = txt_end_date.getText().toString();
        private String endTime = txt_end_time.getText().toString();
        private String entry_name = txt_entry_name.getText().toString();
        private String location = txt_location.getText().toString();

        private JSONObject subjectValue = new JSONObject();
        private JSONObject startDateTimeValue = new JSONObject();
        private JSONObject endDateTimeValue = new JSONObject();
        private JSONObject durationMinutesValue = new JSONObject();
        private JSONObject statusValue = new JSONObject();
        private JSONObject locationValue = new JSONObject();
        private JSONObject popupRemainderValue = new JSONObject();
        private JSONObject emailRemainderValue = new JSONObject();
        private JSONObject RemainderTimeValue = new JSONObject();
        private JSONObject emailRemainderTimeValue = new JSONObject();
        private JSONObject relatedModulenameValue = new JSONObject();
        private JSONObject moduleName = new JSONObject();
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
            status_2_name = params[3];
            related_module_name = params[4];
            String restUrl = params[5];
            String sessionId = params[6];
            email_reminder_time_period = params[7];
            reminder_time_period = params[8];
            id = params[9];
            Log.e(TAG,"id " + id);

            boolean successful = false;
            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, "Meetings");

            try {
                JSONArray nameValueArray = new JSONArray();
                idValue.put("name", "id");
                idValue.put("value", id);
                nameValueArray.put(idValue);
                subjectValue.put("name", "name");
                subjectValue.put("value", subject);
                nameValueArray.put(subjectValue);
                relatedModulenameValue.put("name", "parent_type");
                relatedModulenameValue.put("value", related_module_name);
                nameValueArray.put(relatedModulenameValue);
                moduleName.put("name", "parent_name");
                moduleName.put("value", entry_name);
                nameValueArray.put(moduleName);
                durationMinutesValue.put("name", "duration_minutes");
                durationMinutesValue.put("value", durationMinutes);
                nameValueArray.put(durationMinutesValue);
                locationValue.put("name", "location");
                locationValue.put("value", location);
                nameValueArray.put(locationValue);
                statusValue.put("name", "status");
                statusValue.put("value", status_2_name);
                nameValueArray.put(statusValue);
                endDateTimeValue.put("name", "date_end");
                endDateTimeValue.put("value", end_date + " " + endTime);
                nameValueArray.put(endDateTimeValue);
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
                startDateTimeValue.put("name", "date_start");
                startDateTimeValue.put("value", start_date + " " + startTime);
                nameValueArray.put(startDateTimeValue);

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

                }
                String response = EntityUtils.toString(res.getEntity()).toString();
                JSONObject jsonResponse = new JSONObject(response);
                Log.i(TAG, "setEntry response : " + response);
                Log.d(TAG, "Request " + nameValuePairs.toString());

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
                                b.putString("module_name", module_name);
                                fragment.setArguments(b);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        }

    }

        class AddNewEntryAccount extends AsyncTask<String, Void, Boolean> {
            private String durationMinutes, status_1_name, status_2_name, reminder_time_period, related_module_name, email_reminder_time_period;

            private Map<String, String> nameValueList;
            private ProgressDialog dialog;
            private String subject = txt_subject.getText().toString();
            private String startTime = txt_start_time.getText().toString();
            private String start_date = txt_start_date.getText().toString();
            private String end_date = txt_end_date.getText().toString();
            private String endTime = txt_end_time.getText().toString();
            private String entry_name = txt_entry_name.getText().toString();
            private String location = txt_location.getText().toString();

            private JSONObject subjectValue = new JSONObject();
            private JSONObject startDateTimeValue = new JSONObject();
            private JSONObject endDateTimeValue = new JSONObject();
            private JSONObject durationMinutesValue = new JSONObject();
            private JSONObject statusValue = new JSONObject();
            private JSONObject locationValue = new JSONObject();
            private JSONObject popupRemainderValue = new JSONObject();
            private JSONObject emailRemainderValue = new JSONObject();
            private JSONObject RemainderTimeValue = new JSONObject();
            private JSONObject emailRemainderTimeValue = new JSONObject();
            private JSONObject relatedModulenameValue = new JSONObject();
            private JSONObject idValue = new JSONObject();
            private JSONObject moduleName = new JSONObject();

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
                status_2_name = params[3];
                related_module_name = params[4];
                String restUrl = params[5];
                String sessionId = params[6];
                email_reminder_time_period = params[7];
                reminder_time_period = params[8];

                boolean successful = false;
                Map<String, Object> data = new LinkedHashMap<String, Object>();
                data.put(SESSION, sessionId);
                data.put(MODULE_NAME, "Meetings");
                try {
                    JSONArray nameValueArray = new JSONArray();
                    idValue.put("name", "id");
                    idValue.put("value", id);
                    nameValueArray.put(idValue);
                    subjectValue.put("name", "name");
                    subjectValue.put("value", subject);
                    nameValueArray.put(subjectValue);
                    relatedModulenameValue.put("name", "parent_type");
                    relatedModulenameValue.put("value", related_module_name);
                    nameValueArray.put(relatedModulenameValue);
                    moduleName.put("name", "parent_name");
                    moduleName.put("value", entry_name);
                    nameValueArray.put(moduleName);
                    durationMinutesValue.put("name", "duration_minutes");
                    durationMinutesValue.put("value", durationMinutes);
                    nameValueArray.put(durationMinutesValue);
                    locationValue.put("name", "location");
                    locationValue.put("value", location);
                    nameValueArray.put(locationValue);
                    statusValue.put("name", "status");
                    statusValue.put("value", status_2_name);
                    nameValueArray.put(statusValue);
                    endDateTimeValue.put("name", "date_end");
                    endDateTimeValue.put("value", end_date + " " + endTime);
                    nameValueArray.put(endDateTimeValue);
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
                    startDateTimeValue.put("name", "date_start");
                    startDateTimeValue.put("value", start_date + " " + startTime);
                    nameValueArray.put(startDateTimeValue);

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

                    }
                    String response = EntityUtils.toString(res.getEntity()).toString();
                    JSONObject jsonResponse = new JSONObject(response);
                    Log.i(TAG, "setEntry response : " + response);
                    Log.d(TAG, nameValuePairs.toString());


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
                                    b.putString("module_name", module_name);
                                    fragment.setArguments(b);
                                    getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert).show();
                }
            }

        }
    }
