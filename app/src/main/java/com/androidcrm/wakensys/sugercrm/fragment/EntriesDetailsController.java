package com.androidcrm.wakensys.sugercrm.fragment;

import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.DELETED;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.GET_ENTRY;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.GET_RELATIONSHIPS;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.ID;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.INPUT_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.JSON;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.LIMIT;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.LINK_FIELD_NAME;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.LINK_NAME_TO_FIELDS_ARRAY;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.METHOD;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MODULE_ID;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MODULE_NAME;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.OFFSET;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RELATED_FIELDS;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RELATED_MODULE_LINK_NAME_TO_FIELDS_ARRAY;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RELATED_MODULE_QUERY;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RESPONSE_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.ENTRY_LIST;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.REST_DATA;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SELECT_FIELDS;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SESSION;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import com.androidcrm.wakensys.sugercrm.AddNewEntry.AddNewAccount;
import com.androidcrm.wakensys.sugercrm.AddNewEntry.AddNewCall;
import com.androidcrm.wakensys.sugercrm.AddNewEntry.AddNewCampaign;
import com.androidcrm.wakensys.sugercrm.AddNewEntry.AddNewCases;
import com.androidcrm.wakensys.sugercrm.AddNewEntry.AddNewLeadsAndContactsAndTarget;
import com.androidcrm.wakensys.sugercrm.AddNewEntry.AddNewMeeting;
import com.androidcrm.wakensys.sugercrm.AddNewEntry.AddNewOpportunity;
import com.androidcrm.wakensys.sugercrm.AddNewEntry.AddNewTargetList;
import com.androidcrm.wakensys.sugercrm.AddNewEntry.AddNewTasks;
import com.androidcrm.wakensys.sugercrm.R;

import com.androidcrm.wakensys.sugercrm.AdapterClass.EntryListAdapter;
import com.androidcrm.wakensys.sugercrm.data_sync.CrmDatabaseAdapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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

public class EntriesDetailsController extends Fragment implements
        OnClickListener, OnItemClickListener {

    public final static String TAG = EntriesDetailsController.class
            .getSimpleName();
    private Button call, text, email, edit, fav, more, next;
    private TextView detail_1, detail_2, detail_3, detail_4, detail_5;
    private ListView acc_info_list;
    private String[] selectFields;
    private Map<String, List<String>> linkNameToFieldsArray;
    private int[] photo;
    private String[] listItem;
    private String phone_office, website, phone_fax, email1, sessionId;
    private ProgressDialog dialog;
    private String entry_id = null;
    private String module_label = null;
    private String restUrl = null;
    private JSONObject name_value_list = null;
    private CrmDatabaseAdapter databaseAdapter;

    public static EntriesDetailsController newInstance() {
        return new EntriesDetailsController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Initialize layout
        View rootView = inflater.inflate(R.layout.entries_details_layout, container,
                false);

        try {
            //declare all layout Items
            databaseAdapter = new CrmDatabaseAdapter(getActivity());
            call = (Button) rootView.findViewById(R.id.btn_call);
            text = (Button) rootView.findViewById(R.id.btn_text);
            email = (Button) rootView.findViewById(R.id.btn_email);
            edit = (Button) rootView.findViewById(R.id.btn_edit);
            more = (Button) rootView.findViewById(R.id.btn_more);
            more.setOnClickListener(this);
            call.setOnClickListener(this);
            text.setOnClickListener(this);
            email.setOnClickListener(this);
            edit.setOnClickListener(this);
            detail_1 = (TextView) rootView.findViewById(R.id.txt_detail_1);
            detail_2 = (TextView) rootView.findViewById(R.id.txt_detail_2);
            detail_3 = (TextView) rootView.findViewById(R.id.txt_detail_3);
            detail_4 = (TextView) rootView.findViewById(R.id.txt_detail_4);
            detail_5 = (TextView) rootView.findViewById(R.id.txt_detail_5);
            acc_info_list = (ListView) rootView.findViewById(R.id.lv_info_acc);
            databaseAdapter = new CrmDatabaseAdapter(getActivity());
            //Add list item in to Relationship listView
            listItem = new String[]{"Calls", "Meetings", "Tasks",
                    "Contacts", "Opportunities", "Leads",
                    "Cases"};
            //Add Icon in to Relationship listview
            photo = new int[]{R.drawable.btn_cl, R.drawable.btn_me,
                    R.drawable.btn_ts,
                    R.drawable.btn_co, R.drawable.btn_op,
                    R.drawable.btn_le, R.drawable.btn_ca,};
            //Add icon array and list item array to Relationship list adapter
            EntryListAdapter menuAdapter = new EntryListAdapter(getActivity()
                    .getApplicationContext(), listItem, photo);
            //Add Relationship array adapter in to listView
            acc_info_list.setAdapter(menuAdapter);

            //Get EntryId from Fragment_module_Accounts
            Bundle b = getArguments();
            sessionId = b.getString("sessionId");
            entry_id = b.getString("entry_id");
            restUrl = b.getString("restUrl");
            module_label = b.getString("module_label");

            //Set onItem click listner to Relationship List
            acc_info_list.setOnItemClickListener(this);
        } catch (Exception e) {
            //Log.e(TAG, e.toString());
            e.printStackTrace();
        }
        //Call GettingEntryDetails Method
        new EntryDetails().execute(entry_id, module_label, restUrl, sessionId);
        return rootView;

    }

    @Override
    public void onClick(View v) {
        try {
            boolean fromEdit = true;
            switch (v.getId()) {
                case R.id.btn_more:
                    try {
                        switch (module_label) {
                            case "Accounts":
                                Bundle bundle = new Bundle();
                                String accountDetails = name_value_list.toString();
                                bundle.putBoolean("from_edit", fromEdit);
                                bundle.putString("sessionId", sessionId);
                                bundle.putString("module_name", module_label);
                                bundle.putString("accountDetails", accountDetails);
                                Intent ih = new Intent(getActivity(), EntryDetails_accounts.class);
                                ih.putExtras(bundle);
                                startActivity(ih);

                                break;
                            case "Contacts":
                                Bundle bundle1 = new Bundle();
                                String contactDetails = name_value_list.toString();
                                bundle1.putBoolean("from_edit", fromEdit);
                                bundle1.putString("sessionId", sessionId);
                                bundle1.putString("module_name", module_label);
                                bundle1.putString("contactDetails", contactDetails);
                                Intent ij = new Intent(getActivity(), EntryDetails_contacts.class);
                                ij.putExtras(bundle1);
                                startActivity(ij);

                                break;
                            case "Calls":
                                Bundle bundle2 = new Bundle();
                                String callDetails = name_value_list.toString();
                                bundle2.putString("callDetails", callDetails);
                                bundle2.putBoolean("from_edit", fromEdit);
                                bundle2.putString("module_name", module_label);
                                bundle2.putString("sessionId", sessionId);
                                Intent iq = new Intent(getActivity(), EntryDetails_calls.class);
                                iq.putExtras(bundle2);
                                startActivity(iq);

                                break;
                            case "Leads":
                                Bundle bundle3 = new Bundle();
                                String leadsDetails = name_value_list.toString();
                                bundle3.putBoolean("from_edit", fromEdit);
                                bundle3.putString("leadsDetails", leadsDetails);
                                bundle3.putString("sessionId", sessionId);
                                bundle3.putString("module_name", module_label);
                                Intent iw = new Intent(getActivity(), EntryDetails_leads.class);
                                iw.putExtras(bundle3);
                                startActivity(iw);

                                break;
                            case "Meetings":
                                Bundle bundle4 = new Bundle();
                                String meetingDetails = name_value_list.toString();
                                bundle4.putBoolean("from_edit", fromEdit);
                                bundle4.putString("meetingDetails", meetingDetails);
                                bundle4.putString("sessionId", sessionId);
                                bundle4.putString("module_name", module_label);
                                Intent ie = new Intent(getActivity(), EntryDetails_meetings.class);
                                ie.putExtras(bundle4);
                                startActivity(ie);

                                break;
                            case "Cases":
                                Bundle bundle5 = new Bundle();
                                String caseDetails = name_value_list.toString();
                                bundle5.putBoolean("from_edit", fromEdit);
                                bundle5.putString("caseDetails", caseDetails);
                                bundle5.putString("sessionId", sessionId);
                                bundle5.putString("module_name", module_label);
                                Intent ir = new Intent(getActivity(), EntryDetails_cases.class);
                                ir.putExtras(bundle5);
                                startActivity(ir);

                                break;
                            case "Opportunities":
                                Bundle bundle6 = new Bundle();
                                String opportunutyDetails = name_value_list.toString();
                                bundle6.putBoolean("from_edit", fromEdit);
                                bundle6.putString("sessionId", sessionId);
                                bundle6.putString("module_name", module_label);
                                bundle6.putString("opportunutyDetails", opportunutyDetails);
                                Intent it = new Intent(getActivity(), EntryDetails_opportunity.class);
                                it.putExtras(bundle6);
                                startActivity(it);

                                break;
                            case "Campaigns":
                                Bundle bundle7 = new Bundle();
                                String campaignsDetails = name_value_list.toString();
                                bundle7.putBoolean("from_edit", fromEdit);
                                bundle7.putString("sessionId", sessionId);
                                bundle7.putString("module_name", module_label);
                                bundle7.putString("campaignsDetails", campaignsDetails);
                                Intent its = new Intent(getActivity(), EntryDetails_opportunity.class);
                                its.putExtras(bundle7);
                                startActivity(its);

                                break;
                            case "ProspectLists":
                                Bundle bundle8 = new Bundle();
                                String prospectsListDetails = name_value_list.toString();
                                bundle8.putBoolean("from_edit", fromEdit);
                                bundle8.putString("sessionId", sessionId);
                                bundle8.putString("module_name", module_label);
                                bundle8.putString("prospectsListDetails", prospectsListDetails);
                                Intent itw = new Intent(getActivity(), EntryDetails_opportunity.class);
                                itw.putExtras(bundle8);
                                startActivity(itw);

                                break;
                            case "Prospects":
                                Bundle bundle9 = new Bundle();
                                String prospectsDetails = name_value_list.toString();
                                bundle9.putBoolean("from_edit", fromEdit);
                                bundle9.putString("sessionId", sessionId);
                                bundle9.putString("module_name", module_label);
                                bundle9.putString("prospectsDetails", prospectsDetails);
                                Intent itr = new Intent(getActivity(), EntryDetails_opportunity.class);
                                itr.putExtras(bundle9);
                                startActivity(itr);

                                break;
                            case "Bugs":
                                Bundle bundle10 = new Bundle();
                                String response = name_value_list.toString();
                                bundle10.putBoolean("from_edit", fromEdit);
                                bundle10.putString("sessionId", sessionId);
                                bundle10.putString("response", response);
                                bundle10.putString("module_name", module_label);
                                Intent ite = new Intent(getActivity(), EntryDetails_opportunity.class);
                                ite.putExtras(bundle10);
                                startActivity(ite);

                                break;
                            case "Tasks":
                                Bundle bundle11 = new Bundle();
                                String taskResponse = name_value_list.toString();
                                bundle11.putBoolean("from_edit", fromEdit);
                                bundle11.putString("sessionId", sessionId);
                                bundle11.putString("module_name", module_label);
                                bundle11.putString("response", taskResponse);
                                Intent item = new Intent(getActivity(), EntryDetails_opportunity.class);
                                item.putExtras(bundle11);
                                startActivity(item);

                                break;
                            case "Project":
                                Bundle bundle12 = new Bundle();
                                String projectResponse = name_value_list.toString();
                                bundle12.putBoolean("from_edit", fromEdit);
                                bundle12.putString("sessionId", sessionId);
                                bundle12.putString("module_name", module_label);
                                bundle12.putString("response", projectResponse);
                                Intent items = new Intent(getActivity(), EntryDetails_opportunity.class);
                                items.putExtras(bundle12);
                                startActivity(items);

                                break;
                            default:
                                break;
                        }

                    } catch (Exception e) {
                        Log.e("error", e.toString());
                    }

                    break;
                case R.id.btn_call:
                    try {
                        Intent i = new Intent(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel: " + phone_office));
                        startActivity(i);

                    } catch (Exception e) {
                        Log.e("error", e.toString());
                    }

                    break;
                case R.id.btn_email:
                    try {

                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", email1, null));
                        //intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                        //intent.putExtra(Intent.EXTRA_TEXT, message);
                        startActivity(Intent.createChooser(intent, "Choose an Email client :"));

                    } catch (Exception e) {
                        Log.e("error", e.toString());
                    }


                    break;
                case R.id.btn_text:
                    try {
                        Log.d("click", "txt button clicked");
                        Intent ig = new Intent(Intent.ACTION_VIEW);
                        ig.putExtra("address", phone_office);
                        //intent.putExtra("sms_body", "messageBody");
                        ig.setData(Uri.parse("smsto:" + phone_office));
                        startActivity(ig);

                    } catch (Exception e) {
                        Log.e("error", e.toString());

                    }
                    break;
                case R.id.btn_edit:
                    try {
                        switch (module_label) {
                            case "Accounts":
                                Bundle bundle = new Bundle();
                                String accountDetails = name_value_list.toString();
                                bundle.putString("accountDetails", accountDetails);
                                bundle.putString("sessionId", sessionId);
                                bundle.putString("restUrl", restUrl);
                                bundle.putString("module_name", module_label);
                                fromEdit = true;
                                bundle.putBoolean("from_edit", fromEdit);
                                AddNewAccount addNewAccount = new AddNewAccount();
                                addNewAccount.setArguments(bundle);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, addNewAccount, TAG).commit();
                                break;
                            case "Contacts":
                                Bundle bundle1 = new Bundle();
                                String contactDetails = name_value_list.toString();
                                bundle1.putString("response", contactDetails);
                                bundle1.putString("sessionId", sessionId);
                                bundle1.putString("restUrl", restUrl);
                                bundle1.putString("module_name", module_label);
                                fromEdit = true;
                                bundle1.putBoolean("from_edit", fromEdit);
                                AddNewLeadsAndContactsAndTarget addNewcontact = new AddNewLeadsAndContactsAndTarget();
                                addNewcontact.setArguments(bundle1);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, addNewcontact, TAG).commit();

                                break;
                            case "Calls":
                                Bundle bundle2 = new Bundle();
                                String callDetails = name_value_list.toString();
                                bundle2.putString("callDetails", callDetails);
                                bundle2.putString("sessionId", sessionId);
                                bundle2.putString("restUrl", restUrl);
                                bundle2.putString("module_name", module_label);
                                fromEdit = true;
                                bundle2.putBoolean("from_edit", fromEdit);
                                AddNewCall addNewCall = new AddNewCall();
                                addNewCall.setArguments(bundle2);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, addNewCall, TAG).commit();

                                break;
                            case "Leads":
                                Bundle bundle3 = new Bundle();
                                String leadsDetails = name_value_list.toString();
                                bundle3.putString("response", leadsDetails);
                                bundle3.putString("sessionId", sessionId);
                                bundle3.putString("restUrl", restUrl);
                                bundle3.putString("module_name", module_label);
                                fromEdit = true;
                                bundle3.putBoolean("from_edit", fromEdit);
                                AddNewLeadsAndContactsAndTarget addNewLeads = new AddNewLeadsAndContactsAndTarget();
                                addNewLeads.setArguments(bundle3);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, addNewLeads, TAG).commit();
                                break;
                            case "Meetings":
                                Bundle bundle4 = new Bundle();
                                String meetingDetails = name_value_list.toString();
                                bundle4.putString("meetingDetails", meetingDetails);
                                bundle4.putString("sessionId", sessionId);
                                bundle4.putString("module_name", module_label);
                                bundle4.putString("restUrl", restUrl);
                                fromEdit = true;
                                bundle4.putBoolean("from_edit", fromEdit);
                                AddNewMeeting addNewMeeting = new AddNewMeeting();
                                addNewMeeting.setArguments(bundle4);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, addNewMeeting, TAG).commit();

                                break;
                            case "Cases":
                                Bundle bundle5 = new Bundle();
                                String caseDetails = name_value_list.toString();
                                bundle5.putString("caseDetails", caseDetails);
                                bundle5.putString("sessionId", sessionId);
                                bundle5.putString("restUrl", restUrl);
                                bundle5.putString("module_name", module_label);
                                fromEdit = true;
                                bundle5.putBoolean("from_edit", fromEdit);
                                AddNewCases addNewCases = new AddNewCases();
                                addNewCases.setArguments(bundle5);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, addNewCases, TAG).commit();

                                break;
                            case "Opportunities":
                                Bundle bundle6 = new Bundle();
                                String opportunutyDetails = name_value_list.toString();
                                bundle6.putString("opportunutyDetails", opportunutyDetails);
                                bundle6.putString("sessionId", sessionId);
                                bundle6.putString("restUrl", restUrl);
                                bundle6.putString("module_name", module_label);
                                fromEdit = true;
                                bundle6.putBoolean("from_edit", fromEdit);
                                AddNewOpportunity addNewOpportunity = new AddNewOpportunity();
                                addNewOpportunity.setArguments(bundle6);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, addNewOpportunity, TAG).commit();

                                break;
                            case "Campaigns":
                                Bundle bundle41 = new Bundle();
                                String campaignsDetails = name_value_list.toString();
                                bundle41.putString("campaignsDetails", campaignsDetails);
                                bundle41.putString("sessionId", sessionId);
                                bundle41.putString("restUrl", restUrl);
                                bundle41.putString("module_name", module_label);
                                fromEdit = true;
                                bundle41.putBoolean("from_edit", fromEdit);
                                AddNewCampaign addNewCampaign = new AddNewCampaign();
                                addNewCampaign.setArguments(bundle41);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, addNewCampaign, TAG).commit();

                                break;
                            case "ProspectLists":
                                Bundle bundle51 = new Bundle();
                                String prospectsListDetails = name_value_list.toString();
                                bundle51.putString("prospectListsDetails", prospectsListDetails);
                                bundle51.putString("sessionId", sessionId);
                                bundle51.putString("restUrl", restUrl);
                                bundle51.putString("module_name", module_label);
                                fromEdit = true;
                                bundle51.putBoolean("from_edit", fromEdit);
                                AddNewTargetList addNewTargetList = new AddNewTargetList();
                                addNewTargetList.setArguments(bundle51);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, addNewTargetList, TAG).commit();

                                break;
                            case "Prospects":
                                Bundle bundle9 = new Bundle();
                                String prospectsDetails = name_value_list.toString();
                                bundle9.putString("response", prospectsDetails);
                                bundle9.putString("sessionId", sessionId);
                                bundle9.putString("restUrl", restUrl);
                                bundle9.putString("module_name", module_label);
                                fromEdit = true;
                                bundle9.putBoolean("from_edit", fromEdit);
                                AddNewLeadsAndContactsAndTarget addNewTarget = new AddNewLeadsAndContactsAndTarget();
                                addNewTarget.setArguments(bundle9);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, addNewTarget, TAG).commit();

                                break;
                            case "Tasks":
                                Bundle bundle10 = new Bundle();
                                String taskDetails = name_value_list.toString();
                                bundle10.putString("response", taskDetails);
                                bundle10.putString("sessionId", sessionId);
                                bundle10.putString("restUrl", restUrl);
                                bundle10.putString("module_name", module_label);
                                fromEdit = true;
                                bundle10.putBoolean("from_edit", fromEdit);
                                AddNewTasks addNewTask = new AddNewTasks();
                                addNewTask.setArguments(bundle10);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, addNewTask, TAG).commit();

                                break;
                            default:
                                break;
                        }

                    } catch (Exception e) {
                        Log.e("error", e.toString());
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add click listner for listView
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        try {
            //variable for getRelationship Details
            String linkFieldName;
            String relatedModuleQuery;

            switch (position) {
                case 0:
                    Log.d("Item click", "" + "ListView " + position + " click");

                    linkFieldName = "calls";
                    relatedModuleQuery = "";
                    new getRelationship(linkFieldName, relatedModuleQuery, entry_id).execute();


                    break;
                case 1:
                    Log.d("Item click", "" + "ListView " + position + " click");

                    linkFieldName = "meetings";
                    relatedModuleQuery = "";
                    new getRelationship(linkFieldName, relatedModuleQuery, entry_id).execute();

                    break;
                case 2:
                    Log.d("Item click", "" + "ListView " + position + " click");

                    linkFieldName = "tasks";
                    relatedModuleQuery = "tasks.name IS NOT NULL";
                    new getRelationship(linkFieldName, relatedModuleQuery, entry_id).execute();

                    break;
                case 3:
                    Log.d("Item click", "" + "ListView " + position + " click");

                    linkFieldName = "contacts";
                    relatedModuleQuery = "";

                    new getRelationship(linkFieldName, relatedModuleQuery, entry_id).execute();

                    break;
                case 4:
                    Log.d("Item click", "" + "ListView " + position + " click");

                    linkFieldName = "opportunities";
                    relatedModuleQuery = "";
                    new getRelationship(linkFieldName, relatedModuleQuery, entry_id).execute();

                    break;
                case 5:
                    Log.d("Item click", "" + "ListView " + position + " click");

                    linkFieldName = "leads";
                    relatedModuleQuery = "";
                    new getRelationship(linkFieldName, relatedModuleQuery, entry_id).execute();

                    break;
                case 6:
                    Log.d("Item click", "" + "ListView " + position + " click");

                    linkFieldName = "cases";
                    relatedModuleQuery = "";
                    new getRelationship(linkFieldName, relatedModuleQuery, entry_id).execute();

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Getting AccountEntry Details
    class EntryDetails extends AsyncTask<String, Void, Boolean> {
        private String entry_id = null;
        private String restUrl = null;
        private JSONArray jArray = null;
        private String module_label = null;
        private String response = null;
        private HttpResponse res = null;
        private String id, name, assigned_user_name, created_by_name, date_entered, date_modified, deleted, annual_revenue, phone_fax, billing_address_street,
                billing_address_street_2, billing_address_street_3, billing_address_street_4, billing_address_city, billing_address_state, billing_address_postalcode,
                billing_address_country, phone_office, website, employees, ticker_symbol, shipping_address_street, shipping_address_street_2, shipping_address_street_3,
                shipping_address_street_4, shipping_address_city, shipping_address_state, shipping_address_postalcode, shipping_address_country, parent_name,
                date_start, direction, status, duration_hours, duration_minutes, call_time, parent_type, description, opportunity_type, account_name, amount_usdollar, amountUSdollar, sales_stage,
                title, phone_work, phone_mobile, duration, industry, department, state, case_number, type, priority, date_closed, probability;

        @Override
        protected void onPreExecute() {
            try {
                super.onPreExecute();
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Please Wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {
            //Get entry id
            entry_id = params[0];
            module_label = params[1];
            restUrl = params[2];
            sessionId = params[3];
            boolean successful = false;

            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, module_label != null ? module_label : "");
            data.put(ID, entry_id != null ? entry_id : "");
            data.put(
                    SELECT_FIELDS,
                    (selectFields != null && selectFields.length != 0) ? new JSONArray(
                            Arrays.asList(selectFields)) : "");
            try {
                JSONArray nameValueArray = new JSONArray();
                if (linkNameToFieldsArray != null
                        && linkNameToFieldsArray.size() != 0) {
                    for (Entry<String, List<String>> entry : linkNameToFieldsArray
                            .entrySet()) {
                        JSONObject nameValue = new JSONObject();
                        nameValue.put("name", entry.getKey());
                        nameValue.put("value", new JSONArray(entry.getValue()));
                        nameValueArray.put(nameValue);
                    }
                }
                data.put(LINK_NAME_TO_FIELDS_ARRAY, nameValueArray);

                String restData = org.json.simple.JSONValue.toJSONString(data);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost req = new HttpPost(restUrl);
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD, GET_ENTRY));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, restData));
                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Send POST request
                httpClient.getParams().setBooleanParameter(
                        CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                res = httpClient.execute(req);
                response = EntityUtils.toString(res.getEntity());
                Log.d("Response", response);
                JSONObject responseObj = new JSONObject(response);
                jArray = responseObj.getJSONArray(ENTRY_LIST);
                Log.d("nameValuePairs", nameValuePairs.toString());
                if (response == null) {
                    Log.e("Error !", "Failed to connect !");
                    successful = true;
                }

            } catch (JSONException e) {

            } catch (UnsupportedEncodingException e) {
                successful = true;
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                successful = true;
                e.printStackTrace();
            } catch (IOException e) {
                successful = true;
                e.printStackTrace();
            }
            return successful;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //Disable Dialog box
            dialog.dismiss();
            if (result != true) {
                try {
                    switch (module_label) {
                        case "Accounts":
                            try {
                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject object = jArray.getJSONObject(i);

                                    id = object.getString("id");

                                    name_value_list = object.getJSONObject("name_value_list");

                                    JSONObject assigned_user_name_ = name_value_list.getJSONObject("assigned_user_name");
                                    assigned_user_name = assigned_user_name_.getString("value");

                                    JSONObject created_by_name_ = name_value_list.getJSONObject("created_by_name");
                                    created_by_name = created_by_name_.getString("value");

                                    JSONObject date_entered_ = name_value_list.getJSONObject("date_entered");
                                    date_entered = date_entered_.getString("value");

                                    JSONObject date_modified_ = name_value_list.getJSONObject("date_modified");
                                    date_modified = date_modified_.getString("value");

                                    JSONObject deleted_ = name_value_list.getJSONObject("created_by_name");
                                    deleted = deleted_.getString("value");

                                    JSONObject annual_revenue_ = name_value_list.getJSONObject("annual_revenue");
                                    annual_revenue = annual_revenue_.getString("value");

                                    JSONObject phone_fax_ = name_value_list.getJSONObject("phone_fax");
                                    phone_fax = phone_fax_.getString("value");

                                    JSONObject billing_address_street_ = name_value_list.getJSONObject("billing_address_street");
                                    billing_address_street = billing_address_street_.getString("value");

                                    JSONObject billing_address_street_2_ = name_value_list.getJSONObject("billing_address_street_2");
                                    billing_address_street_2 = billing_address_street_2_.getString("value");

                                    JSONObject billing_address_street_3_ = name_value_list.getJSONObject("billing_address_street_3");
                                    billing_address_street_3 = billing_address_street_3_.getString("value");

                                    JSONObject billing_address_street_4_ = name_value_list.getJSONObject("billing_address_street_4");
                                    billing_address_street_4 = billing_address_street_4_.getString("value");

                                    JSONObject billing_address_city_ = name_value_list.getJSONObject("billing_address_city");
                                    billing_address_city = billing_address_city_.getString("value");

                                    JSONObject billing_address_state_ = name_value_list.getJSONObject("billing_address_state");
                                    billing_address_state = billing_address_state_.getString("value");

                                    JSONObject billing_address_postalcode_ = name_value_list.getJSONObject("billing_address_postalcode");
                                    billing_address_postalcode = billing_address_postalcode_.getString("value");

                                    JSONObject billing_address_country_ = name_value_list.getJSONObject("billing_address_country");
                                    billing_address_country = billing_address_country_.getString("value");

                                    JSONObject phone_office_ = name_value_list.getJSONObject("phone_office");
                                    phone_office = phone_office_.getString("value");

                                    JSONObject website_ = name_value_list.getJSONObject("website");
                                    website = website_.getString("value");

                                    JSONObject employees_ = name_value_list.getJSONObject("employees");
                                    employees = employees_.getString("value");

                                    JSONObject ticker_symbol_ = name_value_list.getJSONObject("ticker_symbol");
                                    ticker_symbol = ticker_symbol_.getString("value");

                                    JSONObject shipping_address_street_ = name_value_list.getJSONObject("shipping_address_street");
                                    shipping_address_street = shipping_address_street_.getString("value");

                                    JSONObject shipping_address_street_2_ = name_value_list.getJSONObject("shipping_address_street_2");
                                    shipping_address_street_2 = shipping_address_street_2_.getString("value");

                                    JSONObject shipping_address_street_3_ = name_value_list.getJSONObject("shipping_address_street_3");
                                    shipping_address_street_3 = shipping_address_street_3_.getString("value");

                                    JSONObject shipping_address_street_4_ = name_value_list.getJSONObject("shipping_address_street_4");
                                    shipping_address_street_4 = shipping_address_street_4_.getString("value");

                                    JSONObject shipping_address_city_ = name_value_list.getJSONObject("shipping_address_city");
                                    shipping_address_city = shipping_address_city_.getString("value");

                                    JSONObject shipping_address_state_ = name_value_list.getJSONObject("shipping_address_state");
                                    shipping_address_state = shipping_address_state_.getString("value");

                                    JSONObject shipping_address_postalcode_ = name_value_list.getJSONObject("shipping_address_postalcode");
                                    shipping_address_postalcode = shipping_address_postalcode_.getString("value");

                                    JSONObject shipping_address_country_ = name_value_list.getJSONObject("shipping_address_country");
                                    shipping_address_country = shipping_address_country_.getString("value");

                                    JSONObject email1_ = name_value_list.getJSONObject("email1");
                                    email1 = email1_.getString("value");

                                    JSONObject parent_name_ = name_value_list.getJSONObject("parent_name");
                                    parent_name = parent_name_.getString("value");

                                    JSONObject name_ = name_value_list.getJSONObject("name");
                                    name = name_.getString("value");

                                    JSONObject industry_ = name_value_list
                                            .getJSONObject("industry");
                                    industry = industry_.getString("value");

                                    long get_id = databaseAdapter.InsertAccountTable(id, name, email1, parent_name, phone_office, phone_fax, website, employees,
                                            ticker_symbol, annual_revenue, billing_address_street, billing_address_street_2, billing_address_street_3, billing_address_street_4, billing_address_city,
                                            billing_address_state, billing_address_postalcode, billing_address_country, shipping_address_street, shipping_address_street_2, shipping_address_street_3, shipping_address_street_4, shipping_address_city, shipping_address_state, shipping_address_postalcode, shipping_address_country, assigned_user_name, created_by_name, date_entered, date_modified, deleted);
                                    //Set TextView to values
                                    detail_1.setText(name);
                                    detail_2.setText(website);
                                    detail_3.setText(email1);
                                    detail_4.setText(phone_office);
                                    detail_5.setText(billing_address_street + ", " + billing_address_street_2 + ", " + billing_address_street_3 + ", " + billing_address_street_4 + ", " + billing_address_city + ", " + billing_address_state + ", " + billing_address_postalcode + ", " + billing_address_country);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Contacts":
                            try {
                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj = jArray.getJSONObject(i);
                                    id = obj.getString("id");
                                    name_value_list = obj.getJSONObject("name_value_list");

                                    JSONObject name_ = name_value_list.getJSONObject("name");
                                    name = name_.getString("value");
                                    Log.d("name", name);

                                    JSONObject email1_ = name_value_list.getJSONObject("email1");
                                    email1 = email1_.getString("value");

                                    JSONObject title_ = name_value_list.getJSONObject("title");
                                    title = title_.getString("value");
                                    Log.d("title", title);

                                    JSONObject phone_work_ = name_value_list
                                            .getJSONObject("phone_work");
                                    phone_work = phone_work_.getString("value");

                                    //Set TextView to values
                                    detail_1.setText(name);
                                    detail_2.setText(title);
                                    detail_3.setText(email1);
                                    detail_4.setText(phone_work);
                                    // detail_5.setText(industry);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Calls":
                            try {
                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj = jArray.getJSONObject(i);

                                    id = obj.getString("id");

                                    name_value_list = obj.getJSONObject("name_value_list");

                                    JSONObject name_ = name_value_list.getJSONObject("name");
                                    name = name_.getString("value");
                                    Log.d("name", name);

                                    JSONObject date_start_ = name_value_list
                                            .getJSONObject("date_start");
                                    date_start = date_start_.getString("value");
                                    Log.d("date_start", date_start);

                                    JSONObject status_ = name_value_list
                                            .getJSONObject("status");
                                    status = status_.getString("value");
                                    Log.d("status", status);

                                    JSONObject duration_hours_ = name_value_list
                                            .getJSONObject("duration_hours");
                                    duration_hours = duration_hours_.getString("value");
                                    JSONObject duration_minutes_ = name_value_list
                                            .getJSONObject("duration_minutes");
                                    duration_minutes = duration_minutes_.getString("value");

                                    call_time = duration_hours + ":" + duration_minutes;

                                    //Set TextView to values
                                    detail_1.setText(name);
                                    detail_5.setText(industry);
                                    detail_2.setText(date_start);
                                    detail_3.setText(call_time);
                                    detail_4.setText(status);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Leads":
                            try {
                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj = jArray.getJSONObject(i);
                                    id = obj.getString("id");
                                    Log.d("c_id", id);

                                    name_value_list = obj.getJSONObject("name_value_list");

                                    JSONObject name_ = name_value_list.getJSONObject("name");
                                    name = name_.getString("value");
                                    Log.d("name", name);

                                    JSONObject title_ = name_value_list.getJSONObject("title");
                                    title = title_.getString("value");
                                    Log.d("title", title);

                                    JSONObject phone_work_ = name_value_list
                                            .getJSONObject("phone_work");
                                    phone_work = phone_work_.getString("value");
                                    Log.d("phone_work", phone_work);

                                    JSONObject email1_ = name_value_list
                                            .getJSONObject("email1");
                                    email1 = email1_.getString("value");

                                    JSONObject department_ = name_value_list
                                            .getJSONObject("department");
                                    department = department_.getString("value");

                                    //Set TextView to values
                                    detail_1.setText(name);
                                    detail_2.setText(title);
                                    detail_3.setText(department);
                                    detail_5.setText(phone_office);
                                    detail_4.setText(email1);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Meetings":
                            try {
                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj = jArray.getJSONObject(i);
                                    id = obj.getString("id");
                                    Log.d("c_id", id);

                                    name_value_list = obj.getJSONObject("name_value_list");

                                    JSONObject name_ = name_value_list.getJSONObject("name");
                                    name = name_.getString("value");

                                    JSONObject description_ = name_value_list
                                            .getJSONObject("description");
                                    description = description_.getString("value");

                                    JSONObject duration_hours_ = name_value_list
                                            .getJSONObject("duration_hours");
                                    duration_hours = duration_hours_.getString("value");

                                    JSONObject duration_minutes_ = name_value_list
                                            .getJSONObject("duration_minutes");
                                    duration_minutes = duration_minutes_.getString("value");

                                    duration = duration_hours + ":" + duration_minutes;

                                    JSONObject date_start_ = name_value_list
                                            .getJSONObject("date_start");
                                    date_start = date_start_.getString("value");

                                    JSONObject status_ = name_value_list
                                            .getJSONObject("date_start");
                                    status = status_.getString("value");

                                    //Set TextView to values
                                    detail_1.setText(name);
                                    detail_2.setText(date_start);
                                    detail_3.setText(duration);
                                    detail_4.setText(status);
                                    detail_5.setText(description);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Cases":
                            try {
                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj = jArray.getJSONObject(i);

                                    id = obj.getString("id");

                                    name_value_list = obj.getJSONObject("name_value_list");

                                    JSONObject name_ = name_value_list.getJSONObject("name");
                                    name = name_.getString("value");
                                    Log.d("name", name);

                                    JSONObject state_ = name_value_list.getJSONObject("state");
                                    state = state_.getString("value");

                                    JSONObject status_ = name_value_list
                                            .getJSONObject("status");
                                    status = status_.getString("value");

                                    JSONObject case_number_ = name_value_list
                                            .getJSONObject("case_number");
                                    case_number = case_number_.getString("value");

                                    JSONObject priority_ = name_value_list
                                            .getJSONObject("priority");
                                    priority = priority_.getString("value");

                                    //Set TextView to values
                                    detail_1.setText(name);
                                    detail_2.setText(case_number);
                                    detail_3.setText(state);
                                    detail_4.setText(status);
                                    detail_5.setText(priority);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Opportunities":
                            try {
                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj = jArray.getJSONObject(i);
                                    id = obj.getString("id");

                                    name_value_list = obj.getJSONObject("name_value_list");

                                    JSONObject name_ = name_value_list.getJSONObject("name");
                                    name = name_.getString("value");

                                    JSONObject probability_ = name_value_list.getJSONObject("probability");
                                    probability = name_.getString("value");

                                    JSONObject amount_usdollar_ = name_value_list
                                            .getJSONObject("amount_usdollar");
                                    amount_usdollar = amount_usdollar_.getString("value");

                                    JSONObject account_name_ = name_value_list
                                            .getJSONObject("account_name");
                                    account_name = account_name_.getString("value");

                                    amountUSdollar = amount_usdollar + " $";

                                    JSONObject sales_stage_ = name_value_list
                                            .getJSONObject("sales_stage");
                                    sales_stage = sales_stage_.getString("value");

                                    JSONObject date_closed_ = name_value_list
                                            .getJSONObject("date_closed");
                                    date_closed = date_closed_.getString("value");

                                    //Set TextView to values
                                    detail_1.setText(name);
                                    detail_2.setText(amountUSdollar);
                                    detail_3.setText(sales_stage);
                                    detail_4.setText(account_name);
                                    detail_5.setText(date_closed);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Prospects":
                            try {
                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj = jArray.getJSONObject(i);
                                    id = obj.getString("id");

                                    name_value_list = obj.getJSONObject("name_value_list");

                                    JSONObject name_ = name_value_list.getJSONObject("name");
                                    name = name_.getString("value");

                                    JSONObject title_ = name_value_list.getJSONObject("title");
                                    String title = title_.getString("value");

                                    JSONObject department_ = name_value_list.getJSONObject("department");
                                    String department = department_.getString("value");

                                    JSONObject phone_mobile_ = name_value_list.getJSONObject("phone_mobile");
                                    String phone_mobile = phone_mobile_.getString("value");

                                    JSONObject email1_ = name_value_list.getJSONObject("email1");
                                    String email1 = email1_.getString("value");


                                    //Set TextView to values
                                    detail_1.setText(name);
                                    detail_2.setText(title);
                                    detail_3.setText(department);
                                    detail_4.setText(phone_mobile);
                                    detail_5.setText(email1);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "ProspectLists":
                            try {
                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj = jArray.getJSONObject(i);
                                    id = obj.getString("id");

                                    name_value_list = obj.getJSONObject("name_value_list");

                                    JSONObject name_ = name_value_list.getJSONObject("name");
                                    name = name_.getString("value");

                                    JSONObject list_type_ = name_value_list.getJSONObject("list_type");
                                    String list_type = list_type_.getString("value");

                                    JSONObject description_ = name_value_list.getJSONObject("description");
                                    String description = description_.getString("value");

                                    //Set TextView to values
                                    detail_1.setText(name);
                                    detail_2.setText(list_type);
                                    detail_3.setText(description);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Campaigns":
                            try {
                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj = jArray.getJSONObject(i);
                                    id = obj.getString("id");

                                    name_value_list = obj.getJSONObject("name_value_list");

                                    JSONObject name_ = name_value_list.getJSONObject("name");
                                    name = name_.getString("value");

                                    JSONObject start_date_ = name_value_list.getJSONObject("start_date");
                                    String start_date = start_date_.getString("value");

                                    JSONObject end_date_ = name_value_list.getJSONObject("end_date");
                                    String end_date = end_date_.getString("value");

                                    JSONObject status_ = name_value_list.getJSONObject("status");
                                    String status = status_.getString("value");

                                    JSONObject description_ = name_value_list.getJSONObject("description");
                                    String description = description_.getString("value");

                                    //Set TextView to values
                                    detail_1.setText(name);
                                    detail_2.setText(start_date + " - " + end_date);
                                    detail_3.setText(status);
                                    detail_4.setText(description);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Project":
                            try {
                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj = jArray.getJSONObject(i);
                                    id = obj.getString("id");

                                    name_value_list = obj.getJSONObject("name_value_list");

                                    JSONObject name_ = name_value_list.getJSONObject("name");
                                    name = name_.getString("value");

                                    JSONObject estimated_start_date_ = name_value_list.getJSONObject("estimated_start_date");
                                    String estimated_start_date = estimated_start_date_.getString("value");

                                    JSONObject estimated_end_date_ = name_value_list.getJSONObject("estimated_end_date");
                                    String estimated_end_date = estimated_end_date_.getString("value");

                                    JSONObject priority_ = name_value_list.getJSONObject("priority");
                                    String priority = priority_.getString("value");

                                    JSONObject status_ = name_value_list.getJSONObject("status");
                                    String status = status_.getString("value");

                                    JSONObject description_ = name_value_list.getJSONObject("description");
                                    String description = description_.getString("value");

                                    //Set TextView to values
                                    detail_1.setText(name);
                                    detail_2.setText(estimated_start_date + " - " + estimated_end_date);
                                    detail_3.setText(status);
                                    detail_4.setText(priority);
                                    detail_5.setText(description);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Tasks":
                            try {
                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj = jArray.getJSONObject(i);
                                    id = obj.getString("id");

                                    name_value_list = obj.getJSONObject("name_value_list");

                                    JSONObject name_ = name_value_list.getJSONObject("name");
                                    name = name_.getString("value");

                                    JSONObject date_due_ = name_value_list.getJSONObject("date_due");
                                    String date_due = date_due_.getString("value");

                                    JSONObject priority_ = name_value_list.getJSONObject("priority");
                                    String priority = priority_.getString("value");

                                    JSONObject status_ = name_value_list.getJSONObject("status");
                                    String status = status_.getString("value");

                                    JSONObject description_ = name_value_list.getJSONObject("description");
                                    String description = description_.getString("value");

                                    JSONObject contact_email_ = name_value_list.getJSONObject("contact_email");
                                    String contact_email = contact_email_.getString("value");

                                    //Set TextView to values
                                    detail_1.setText(name);
                                    detail_2.setText(date_due);
                                    detail_3.setText(priority);
                                    detail_4.setText(status);
                                    detail_5.setText(contact_email);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Bugs":
                            try {
                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj = jArray.getJSONObject(i);
                                    id = obj.getString("id");

                                    name_value_list = obj.getJSONObject("name_value_list");

                                    JSONObject name_ = name_value_list.getJSONObject("name");
                                    name = name_.getString("value");

                                    JSONObject bug_number_ = name_value_list.getJSONObject("bug_number");
                                    String bug_number = bug_number_.getString("value");

                                    JSONObject priority_ = name_value_list.getJSONObject("priority");
                                    String priority = priority_.getString("value");

                                    JSONObject status_ = name_value_list.getJSONObject("status");
                                    String status = status_.getString("value");

                                    JSONObject product_category_ = name_value_list.getJSONObject("product_category");
                                    String product_category = product_category_.getString("value");

                                    //Set TextView to values
                                    detail_1.setText(name);
                                    detail_2.setText(bug_number);
                                    detail_3.setText(priority);
                                    detail_4.setText(status);
                                    detail_5.setText(product_category);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //----------------------------------------------------- Not added Campaign,tasks,target,task,bug,projects and target list to SQLite------------------------------
            else {
                switch (module_label) {
                    case "Accounts":
                        try {
                            /*Cursor cursor = databaseAdapter.getAccount(entry_id);
                            while (cursor.moveToNext()) {
                                String account_id = cursor.getString(0);
                                name = cursor.getString(1);
                                website = cursor.getString(2);
                                email1 = cursor.getString(3);
                                phone_office = cursor.getString(4);
                                billing_address_street = cursor.getString(5);
                                billing_address_street_2 = cursor.getString(6);
                                billing_address_street_3 = cursor.getString(7);
                                billing_address_street_4 = cursor.getString(8);
                                billing_address_city = cursor.getString(9);
                                billing_address_state = cursor.getString(10);
                                billing_address_postalcode = cursor.getString(11);
                                billing_address_country = cursor.getString(12);

                            }*/
                            Cursor csr = databaseAdapter.getAccount(entry_id);
                            int i = 0;
                            String[] moduleId = new String[(csr.getCount() - 1)];
                            String[] names = new String[(csr.getCount() - 1)];
                            csr.moveToFirst();

                            while (csr.moveToNext()) {
                                moduleId[i] = csr.getString(0);
                                //  moduleLabel[i] = csr.getString(1);
                                //   entry_ids.add(moduleId[i]);
                                //   entry_names.add( moduleLabel[i]);
                                i++;
                            }
                            //Set TextView to values
                            detail_1.setText(name);
                            detail_2.setText(website);
                            detail_3.setText(email1);
                            detail_4.setText(phone_office);
                            detail_5.setText(billing_address_street + ", " + billing_address_street_2 + ", " + billing_address_street_3 + ", " + billing_address_street_4 + ", " + billing_address_city + ", " + billing_address_state + ", " + billing_address_postalcode + ", " + billing_address_country);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                 /*  case "Calls":
                        try {

                            Cursor csr = databaseAdapter.getCall();
                            int i = 0;
                            String[] moduleId = new String[(csr.getCount()-1)];
                            String[] moduleLabel = new String[(csr.getCount()-1)];
                            csr.moveToFirst();

                            while (csr.moveToNext()) {
                                moduleId[i] = csr.getString(0);
                                moduleLabel[i] = csr.getString(1);
                                entry_ids.add(moduleId[i]);
                                entry_names.add( moduleLabel[i]);
                                i++;
                            }
                            //Set TextView to values
                            detail_1.setText(name);
                            detail_5.setText(industry);
                            detail_2.setText(date_start);
                            detail_3.setText(call_time);
                            detail_4.setText(status);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    case "Cases":
                        try {

                            Cursor csr = databaseAdapter.getCases();
                            int i = 0;
                            String[] moduleId = new String[(csr.getCount()-1)];
                            String[] moduleLabel = new String[(csr.getCount()-1)];
                            csr.moveToFirst();

                            while (csr.moveToNext()) {
                                moduleId[i] = csr.getString(0);
                                moduleLabel[i] = csr.getString(1);
                                entry_ids.add(moduleId[i]);
                                entry_names.add( moduleLabel[i]);
                                i++;
                            }
                            //Set TextView to values
                            detail_1.setText(name);
                            detail_2.setText(case_number);
                            detail_3.setText(state);
                            detail_4.setText(status);
                            detail_5.setText(priority);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    case "Contacts":
                        try {

                            Cursor csr = databaseAdapter.getContacts();
                            int i = 0;
                            String[] moduleId = new String[(csr.getCount()-1)];
                            String[] firstName = new String[(csr.getCount()-1)];
                            String[] last_name = new String[(csr.getCount()-1)];
                            csr.moveToFirst();

                            while (csr.moveToNext()) {
                                moduleId[i] = csr.getString(0);
                                firstName[i] = csr.getString(1);
                                last_name[i] = csr.getString(2);
                                String name = firstName[i] +" "+last_name[i];
                                entry_ids.add(moduleId[i]);
                                entry_names.add( name);
                                i++;
                            }
                            //Set TextView to values
                            detail_1.setText(name);
                            detail_2.setText(title);
                            detail_3.setText(email1);
                            detail_4.setText(phone_work);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    case "Leads":
                        try {

                            Cursor csr = databaseAdapter.getLeads();
                            int i = 0;
                            String[] moduleId = new String[(csr.getCount()-1)];
                            String[] firstName = new String[(csr.getCount()-1)];
                            String[] last_name = new String[(csr.getCount()-1)];
                            csr.moveToFirst();

                            while (csr.moveToNext()) {
                                moduleId[i] = csr.getString(0);
                                firstName[i] = csr.getString(1);
                                last_name[i] = csr.getString(2);
                                String name = firstName[i] +" "+last_name[i];
                                entry_ids.add(moduleId[i]);
                                entry_names.add( name);
                                i++;
                            }
                            //Set TextView to values
                            detail_1.setText(name);
                            detail_2.setText(title);
                            detail_3.setText(department);
                            detail_5.setText(phone_office);
                            detail_4.setText(email1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    case "Meetings":
                        try {

                            Cursor csr = databaseAdapter.getMeetings();
                            int i = 0;
                            String[] moduleId = new String[(csr.getCount()-1)];
                            String[] moduleLabel = new String[(csr.getCount()-1)];
                            csr.moveToFirst();

                            while (csr.moveToNext()) {
                                moduleId[i] = csr.getString(0);
                                moduleLabel[i] = csr.getString(1);
                                entry_ids.add(moduleId[i]);
                                entry_names.add( moduleLabel[i]);
                                i++;
                            }
                            //Set TextView to values
                            detail_1.setText(name);
                            detail_2.setText(date_start);
                            detail_3.setText(duration);
                            detail_4.setText(status);
                            detail_5.setText(description);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    case "Opportunities":
                        try {

                            Cursor csr = databaseAdapter.getOpportunities();
                            int i = 0;
                            String[] moduleId = new String[(csr.getCount()-1)];
                            String[] moduleLabel = new String[(csr.getCount()-1)];
                            csr.moveToFirst();

                            while (csr.moveToNext()) {
                                moduleId[i] = csr.getString(0);
                                moduleLabel[i] = csr.getString(1);
                                entry_ids.add(moduleId[i]);
                                entry_names.add( moduleLabel[i]);
                                i++;
                            }
                            //Set TextView to values
                            detail_1.setText(name);
                            detail_2.setText(amountUSdollar);
                            detail_3.setText(sales_stage);
                            detail_4.setText(account_name);
                            detail_5.setText(date_closed);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;*/
                    default:
                        break;
                }
            }
        }
    }

    class getRelationship extends AsyncTask<String, String, String> {

        private String entry_id;
        private String response;
        private String key;
        private String linkFieldName, relatedModuleQuery;
        private String[] relatedFields = {"id", "name"};
        private String deleted;
        private String order_by;
        private String offset;
        private int limit;

        getRelationship(String linkFieldName, String relatedModuleQuery, String entry_id) {
            this.linkFieldName = linkFieldName;
            this.entry_id = entry_id;
            this.relatedModuleQuery = relatedModuleQuery;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Please Wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            Log.e("Link_field_name", linkFieldName);
            Log.e("MODULE_NAME", module_label);
            Log.e("MODULE_ID", entry_id);
            Log.e("relatedModuleQuery", relatedModuleQuery);

            Map<String, List<String>> relatedModuleLinkNameToFieldsArray = null;

            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);

            data.put(MODULE_NAME, module_label);
            data.put(MODULE_ID, entry_id != null ? entry_id : "");
            data.put(LINK_FIELD_NAME, linkFieldName != null ? linkFieldName : "");
            data.put(RELATED_MODULE_QUERY, relatedModuleQuery != null ? relatedModuleQuery : "");
            data.put(RELATED_FIELDS, (relatedFields != null && relatedFields.length != 0) ? new JSONArray(Arrays.asList(relatedFields))
                    : "");

            try {
                JSONArray linkNametoFieldJson = new JSONArray();
                if (relatedModuleLinkNameToFieldsArray != null
                        && relatedModuleLinkNameToFieldsArray.size() != 0) {
                    for (Entry<String, List<String>> entry : relatedModuleLinkNameToFieldsArray.entrySet()) {
                        JSONObject nameValue = new JSONObject();
                        nameValue.put("name", entry.getKey());
                        nameValue.put("value", new JSONArray(entry.getValue()));
                        linkNametoFieldJson.put(nameValue);
                    }
                }

                data.put(RELATED_MODULE_LINK_NAME_TO_FIELDS_ARRAY, linkNametoFieldJson);
                data.put(DELETED, deleted != null ? deleted : 0);
                // data.put(ORDER_BY, order_by != null ? order_by :"opportunities.name");
                data.put(OFFSET, offset != null ? offset : 0);
                data.put(LIMIT, limit = 200);

                String restData = org.json.simple.JSONValue.toJSONString(data);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost req = new HttpPost(restUrl);
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD, GET_RELATIONSHIPS));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, restData));
                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Send POST request
                httpClient.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse res = httpClient.execute(req);
                if (res.getEntity() == null) {
                    Log.i(TAG, "FAILED TO CONNECT!");

                }
                response = EntityUtils.toString(res.getEntity());
                Log.e(TAG, "getRelationships response : " + response);


            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                super.onPostExecute(s);
                dialog.dismiss();

                Bundle be = new Bundle();
                be.putString("contacts", linkFieldName);
                be.putString("message", response);
                be.putString("restUrl", restUrl);
                be.putString("linkFieldName", linkFieldName);
                be.putString("entryId_DetailController", entry_id);
                boolean relationship = true;
                be.putBoolean("relationship", relationship);
                Fragment_Entries fragment = new Fragment_Entries();
                fragment.setArguments(be);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();


            } catch (Exception e) {
                //Log.e(TAG, e.toString());
                e.printStackTrace();
            }


        }
    }

}
