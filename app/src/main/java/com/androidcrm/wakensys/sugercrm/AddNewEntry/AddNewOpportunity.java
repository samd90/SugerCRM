package com.androidcrm.wakensys.sugercrm.AddNewEntry;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
public class AddNewOpportunity extends Fragment implements View.OnClickListener {

    public static final String TAG = AddNewOpportunity.class.getSimpleName();
    private String sessionId, restUrl, leadSource, typeName, salesStage, id, currency, module_name, response, name, currency_name, amount, account_name, description, campaign_name, lead_source, opportunity_type, date_closed, next_step, probability, sales_stage;
    private boolean from_edit = false;
    private EditText txt_name, txt_amount, txt_probability, txt_next_step, txt_description, txt_account_name, txt_expected_close_date, txt_campaign;
    private Button btn_save, btn_cancel, btn_dateSelect;
    private Spinner currencySpinner_, salesStageSpinner_, typeSpinner_, sourceLeadSpinner_;
    private List<String> currencyItem_, salesStageItem_, sourceLeadItem_, typeItem_;
    private ArrayAdapter<String> currencyDataAdapter_, salesDataAdapter_, leadSourceDataAdapter_, typeDataAdapter_;
    private int mYear, mMonth, mDate;

    public static AddNewOpportunity newInstance() {
        return new AddNewOpportunity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.add_new_opportunites, container, false);

        // Get sessionId and RestUrl from AddNewItem_selectMenu class
        Bundle b = getArguments();
        sessionId = b.getString("sessionId");
        restUrl = b.getString("restUrl");
        module_name = b.getString("module_name");
        from_edit = b.getBoolean("from_edit");
        response = b.getString("opportunutyDetails");

        txt_name = (EditText) myView.findViewById(R.id.txt_opportunity_name);
        txt_amount = (EditText) myView.findViewById(R.id.txt_opportunity_amount);
        txt_probability = (EditText) myView.findViewById(R.id.txt_probability);
        txt_next_step = (EditText) myView.findViewById(R.id.txt_next_step);
        txt_description = (EditText) myView.findViewById(R.id.txt_description);
        txt_account_name = (EditText) myView.findViewById(R.id.txt_account_name);
        txt_expected_close_date = (EditText) myView.findViewById(R.id.txt_expected_close_date);
        txt_campaign = (EditText) myView.findViewById(R.id.txt_campaign);
        currencySpinner_ = (Spinner) myView.findViewById(R.id.spinner_currency);
        salesStageSpinner_ = (Spinner) myView.findViewById(R.id.spinner_sales_stage);
        typeSpinner_ = (Spinner) myView.findViewById(R.id.spinner_type);
        sourceLeadSpinner_ = (Spinner) myView.findViewById(R.id.spinner_lead_source);

        // Array list for Priority Spinner
        currencyItem_ = new ArrayList<String>();
        currencyItem_.add("Rs.");
        currencyItem_.add("$");

        // Array list for Status Spinner
        salesStageItem_ = new ArrayList<String>();
        salesStageItem_.add("Prospecting");
        salesStageItem_.add("Qualification");
        salesStageItem_.add("Needs Analysis");
        salesStageItem_.add("Value Proposition");
        salesStageItem_.add("Id. Decision Makers");
        salesStageItem_.add("Perception Analysis");
        salesStageItem_.add("Proposal/Price Quote");
        salesStageItem_.add("Negotiation/Review");
        salesStageItem_.add("Closed Won");
        salesStageItem_.add("Closed Lost");

        // Array list for State Spinner
        sourceLeadItem_ = new ArrayList<String>();
        sourceLeadItem_.add(" ");
        sourceLeadItem_.add("Cold Call");
        sourceLeadItem_.add("Existing Customer ");
        sourceLeadItem_.add("Self Generated");
        sourceLeadItem_.add("Employee");
        sourceLeadItem_.add("Partner");
        sourceLeadItem_.add("Public Relations");
        sourceLeadItem_.add("Direct Mail");
        sourceLeadItem_.add("Conference");
        sourceLeadItem_.add("Trade Show");
        sourceLeadItem_.add("Web Site");
        sourceLeadItem_.add("Word of mouth");
        sourceLeadItem_.add("Email");
        sourceLeadItem_.add("Campaign");
        sourceLeadItem_.add("Other");

        // Array list for Type Spinner
        typeItem_ = new ArrayList<String>();
        typeItem_.add(" ");
        typeItem_.add("New Business");
        typeItem_.add("Existing Business");

        currencyDataAdapter_ = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, currencyItem_);
        salesDataAdapter_ = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, salesStageItem_);
        leadSourceDataAdapter_ = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sourceLeadItem_);
        typeDataAdapter_ = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, typeItem_);

        currencyDataAdapter_.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        salesDataAdapter_.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leadSourceDataAdapter_.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeDataAdapter_.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        currencySpinner_.setAdapter(currencyDataAdapter_);          // Set adapter to Status spinner
        salesStageSpinner_.setAdapter(salesDataAdapter_);           // Set adapter to State spinner
        typeSpinner_.setAdapter(typeDataAdapter_);                  // Set adapter to type spinner
        sourceLeadSpinner_.setAdapter(leadSourceDataAdapter_);      // Set adapter to priority spinner

        // Set onclick listners to Spinners
        currencySpinner_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currency = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        salesStageSpinner_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                salesStage = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        typeSpinner_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sourceLeadSpinner_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leadSource = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_save = (Button) myView.findViewById(R.id.btn_save);
        btn_cancel = (Button) myView.findViewById(R.id.btn_cancel);
        btn_dateSelect = (Button) myView.findViewById(R.id.date);

        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_dateSelect.setOnClickListener(this);

        if (from_edit == true) {
            try {
                JSONObject name_value_list = new JSONObject(response);

                JSONObject m_name_ = name_value_list.getJSONObject("name");
                name = m_name_.getString("value");

                JSONObject currency_name_ = name_value_list
                        .getJSONObject("currency_name");
                currency_name = currency_name_.getString("value");

                JSONObject amount_ = name_value_list
                        .getJSONObject("amount");
                amount = amount_.getString("value");

                JSONObject sales_stage_ = name_value_list
                        .getJSONObject("sales_stage");
                sales_stage = sales_stage_.getString("value");

                JSONObject probability_ = name_value_list
                        .getJSONObject("probability");
                probability = probability_.getString("value");

                JSONObject next_step_ = name_value_list
                        .getJSONObject("next_step");
                next_step = next_step_.getString("value");

                JSONObject date_closed_ = name_value_list
                        .getJSONObject("date_closed");
                date_closed = date_closed_.getString("value");

                JSONObject opportunity_type_ = name_value_list
                        .getJSONObject("opportunity_type");
                opportunity_type = opportunity_type_.getString("value");

                JSONObject lead_source_ = name_value_list
                        .getJSONObject("lead_source");
                lead_source = lead_source_.getString("value");

                JSONObject campaign_name_ = name_value_list
                        .getJSONObject("campaign_name");
                campaign_name = campaign_name_.getString("value");

                JSONObject description_ = name_value_list
                        .getJSONObject("description");
                description = description_.getString("value");

                JSONObject account_name_ = name_value_list
                        .getJSONObject("account_name");
                account_name = account_name_.getString("value");

                JSONObject id_ = name_value_list
                        .getJSONObject("id");
                id = id_.getString("value");

                txt_name.setText(name);
                txt_amount.setText(amount);
                txt_probability.setText(probability);
                txt_next_step.setText(next_step);
                txt_description.setText(description);
                txt_account_name.setText(account_name);
                txt_expected_close_date.setText(date_closed);
                txt_campaign.setText(campaign_name);

                currencySpinner_.setSelection(((ArrayAdapter<String>) currencySpinner_.getAdapter()).getPosition(currency_name));
                salesStageSpinner_.setSelection(((ArrayAdapter<String>) salesStageSpinner_.getAdapter()).getPosition(sales_stage));
                typeSpinner_.setSelection(((ArrayAdapter<String>) typeSpinner_.getAdapter()).getPosition(opportunity_type));
                sourceLeadSpinner_.setSelection(((ArrayAdapter<String>) sourceLeadSpinner_.getAdapter()).getPosition(lead_source));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return myView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if (from_edit == true) {
                    new EditRecord().execute(sessionId, restUrl, leadSource, typeName, salesStage, currency, id);
                } else {
                    new AddNewEntryAccount().execute(sessionId, restUrl, leadSource, typeName, salesStage, currency);

                }
                break;
            case R.id.btn_cancel:
                Fragment_Entries fragment = new Fragment_Entries();
                Bundle b = new Bundle();
                b.putString("restUrl", restUrl);
                b.putString("sessionId", sessionId);
                b.putString("module_name", module_name);
                fragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                break;
            case R.id.date:
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

                        SimpleDateFormat frmDate1 = new SimpleDateFormat("yyyy-MM-dd");
                        String end_date = frmDate1.format(time1);
                        txt_expected_close_date.setText(end_date);

                        alertDialog1.dismiss();
                    }
                });
                alertDialog1.setView(dialogView1);
                alertDialog1.show();
                break;
            default:
                break;
        }
    }

    class EditRecord extends AsyncTask<String, Void, Boolean> {
        private String sessionId, moduleName, leadSource, typeName, salesStage, currency;
        private Map<String, String> nameValueList;
        private ProgressDialog pDialog;
        private String name = txt_name.getText().toString();
        private String amount = txt_amount.getText().toString();
        private String probability = txt_probability.getText().toString();
        private String next_step = txt_next_step.getText().toString();
        private String description = txt_description.getText().toString();
        private String account_name = txt_account_name.getText().toString();
        private String expected_close_date = txt_expected_close_date.getText().toString();
        private String campaign = txt_campaign.getText().toString();
        private boolean successful = false;
        private JSONObject nameValue = new JSONObject();
        private JSONObject amountValue = new JSONObject();
        private JSONObject probabilityValue = new JSONObject();
        private JSONObject next_stepValue = new JSONObject();
        private JSONObject descriptionValue = new JSONObject();
        private JSONObject account_nameValue = new JSONObject();
        private JSONObject expected_close_dateValue = new JSONObject();
        private JSONObject campaignValue = new JSONObject();
        private JSONObject leadSourceValue = new JSONObject();
        private JSONObject typeNameValue = new JSONObject();
        private JSONObject currencyValue = new JSONObject();
        private JSONObject idValue = new JSONObject();
        private JSONObject salesStageValue = new JSONObject();

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            sessionId = params[0];
            restUrl = params[1];
            leadSource = params[2];
            typeName = params[3];
            salesStage = params[4];
            currency = params[5];
            id = params[6];

            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, module_name);

            try {
                JSONArray nameValueArray = new JSONArray();
                idValue.put("name", "id");
                idValue.put("value", id);
                nameValueArray.put(idValue);
                nameValue.put("name", "name");
                nameValue.put("value", name);
                nameValueArray.put(nameValue);
                amountValue.put("name", "amount");
                amountValue.put("value", amount);
                nameValueArray.put(amountValue);
                probabilityValue.put("name", "probability");
                probabilityValue.put("value", probability);
                nameValueArray.put(probabilityValue);
                next_stepValue.put("name", "next_step");
                next_stepValue.put("value", next_step);
                nameValueArray.put(next_stepValue);
                descriptionValue.put("name", "description");
                descriptionValue.put("value", description);
                nameValueArray.put(descriptionValue);
                account_nameValue.put("name", "account_name");
                account_nameValue.put("value", account_name);
                nameValueArray.put(account_nameValue);
                expected_close_dateValue.put("name", "date_closed");
                expected_close_dateValue.put("value", expected_close_date);
                nameValueArray.put(expected_close_dateValue);
                campaignValue.put("name", "campaign_name");
                campaignValue.put("value", campaign);
                nameValueArray.put(campaignValue);
                leadSourceValue.put("name", "lead_source");
                leadSourceValue.put("value", leadSource);
                nameValueArray.put(leadSourceValue);
                typeNameValue.put("name", "opportunity_type");
                typeNameValue.put("value", typeName);
                nameValueArray.put(typeNameValue);
                currencyValue.put("name", "currency_symbol");
                currencyValue.put("value", currency);
                nameValueArray.put(currencyValue);
                salesStageValue.put("name", "sales_stage");
                salesStageValue.put("value", salesStage);
                nameValueArray.put(salesStageValue);

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
                Log.e(TAG, nameValuePairs.toString());
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
                Log.i(TAG, "" + nameValuePairs);

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
            pDialog.dismiss();
            if (result != true) {
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
        private String sessionId, moduleName, leadSource, typeName, salesStage, currency;
        private Map<String, String> nameValueList;
        private ProgressDialog pDialog;
        private String name = txt_name.getText().toString();
        private String amount = txt_amount.getText().toString();
        private String probability = txt_probability.getText().toString();
        private String next_step = txt_next_step.getText().toString();
        private String description = txt_description.getText().toString();
        private String account_name = txt_account_name.getText().toString();
        private String expected_close_date = txt_expected_close_date.getText().toString();
        private String campaign = txt_campaign.getText().toString();
        private boolean successful = false;
        private JSONObject nameValue = new JSONObject();
        private JSONObject amountValue = new JSONObject();
        private JSONObject probabilityValue = new JSONObject();
        private JSONObject next_stepValue = new JSONObject();
        private JSONObject descriptionValue = new JSONObject();
        private JSONObject account_nameValue = new JSONObject();
        private JSONObject expected_close_dateValue = new JSONObject();
        private JSONObject campaignValue = new JSONObject();
        private JSONObject leadSourceValue = new JSONObject();
        private JSONObject typeNameValue = new JSONObject();
        private JSONObject currencyValue = new JSONObject();
        private JSONObject salesStageValue = new JSONObject();


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            sessionId = params[0];
            restUrl = params[1];
            leadSource = params[2];
            typeName = params[3];
            salesStage = params[4];
            currency = params[5];
            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, module_name);

            try {
                JSONArray nameValueArray = new JSONArray();
                nameValue.put("name", "name");
                nameValue.put("value", name);
                nameValueArray.put(nameValue);
                amountValue.put("name", "amount");
                amountValue.put("value", amount);
                nameValueArray.put(amountValue);
                probabilityValue.put("name", "probability");
                probabilityValue.put("value", probability);
                nameValueArray.put(probabilityValue);
                next_stepValue.put("name", "next_step");
                next_stepValue.put("value", next_step);
                nameValueArray.put(next_stepValue);
                descriptionValue.put("name", "description");
                descriptionValue.put("value", description);
                nameValueArray.put(descriptionValue);
                account_nameValue.put("name", "account_name");
                account_nameValue.put("value", account_name);
                nameValueArray.put(account_nameValue);
                expected_close_dateValue.put("name", "date_closed");
                expected_close_dateValue.put("value", expected_close_date);
                nameValueArray.put(expected_close_dateValue);
                campaignValue.put("name", "campaign_name");
                campaignValue.put("value", campaign);
                nameValueArray.put(campaignValue);
                leadSourceValue.put("name", "lead_source");
                leadSourceValue.put("value", leadSource);
                nameValueArray.put(leadSourceValue);
                typeNameValue.put("name", "opportunity_type");
                typeNameValue.put("value", typeName);
                nameValueArray.put(typeNameValue);
                currencyValue.put("name", "currency_symbol");
                currencyValue.put("value", currency);
                nameValueArray.put(currencyValue);
                salesStageValue.put("name", "sales_stage");
                salesStageValue.put("value", salesStage);
                nameValueArray.put(salesStageValue);

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
                Log.e(TAG, nameValuePairs.toString());
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
                Log.i(TAG, "" + nameValuePairs);

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
            pDialog.dismiss();
            if (result != true) {
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
