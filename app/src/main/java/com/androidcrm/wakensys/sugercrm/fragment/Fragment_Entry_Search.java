package com.androidcrm.wakensys.sugercrm.fragment;


import com.androidcrm.wakensys.sugercrm.R;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.ENTRY_LIST;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RECORDS;


public class Fragment_Entry_Search extends Fragment implements View.OnClickListener {

    private ProgressDialog dialog;

    private LinearLayout linearLayout_ac, linearLayout_co, linearLayout_op,
            linearLayout_le, linearLayout_cl, linearLayout_me, linearLayout_ts,
            linearLayout_ca;

    private TextView text_ac, text_co, text_op, text_le, text_me, text_ts, text_ca, text_cl;

    private String searchObj;

    private List<String> nameValues_ac;
    private List<String> nameValues_co;
    private List<String> nameValues_op;
    private List<String> nameValues_le;
    private List<String> nameValues_cl;
    private List<String> nameValues_me;
    private List<String> nameValues_ts;
    private List<String> nameValues_ca;


    public static Fragment_Entry_Search newInstance() {
        return new Fragment_Entry_Search();
    }

    public final static String TAG = Fragment_Entry_Search.class
            .getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_entry_search,
                container, false);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Wait..");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();


        text_ac = (TextView) rootView.findViewById(R.id.tx1);
        text_co = (TextView) rootView.findViewById(R.id.tx2);
        text_op = (TextView) rootView.findViewById(R.id.tx3);
        text_le = (TextView) rootView.findViewById(R.id.tx4);
        text_cl = (TextView) rootView.findViewById(R.id.tx5);
        text_me = (TextView) rootView.findViewById(R.id.tx6);
        text_ts = (TextView) rootView.findViewById(R.id.tx7);
        text_ca = (TextView) rootView.findViewById(R.id.tx8);

        linearLayout_ca = (LinearLayout) rootView.findViewById(R.id.ln8);
        linearLayout_ac = (LinearLayout) rootView.findViewById(R.id.ln1);
        linearLayout_ts = (LinearLayout) rootView.findViewById(R.id.ln7);
        linearLayout_me = (LinearLayout) rootView.findViewById(R.id.ln6);
        linearLayout_cl = (LinearLayout) rootView.findViewById(R.id.ln5);
        linearLayout_le = (LinearLayout) rootView
                .findViewById(R.id.ln4);
        linearLayout_op = (LinearLayout) rootView.findViewById(R.id.ln3);
        linearLayout_co = (LinearLayout) rootView
                .findViewById(R.id.ln2);

        linearLayout_ca.setOnClickListener(this);
        linearLayout_ac.setOnClickListener(this);
        linearLayout_co.setOnClickListener(this);
        linearLayout_ts.setOnClickListener(this);
        linearLayout_me.setOnClickListener(this);
        linearLayout_cl.setOnClickListener(this);
        linearLayout_le.setOnClickListener(this);
        linearLayout_op.setOnClickListener(this);

        nameValues_ac = new ArrayList<String>();
        nameValues_ca = new ArrayList<String>();
        nameValues_cl = new ArrayList<String>();
        nameValues_co = new ArrayList<String>();
        nameValues_le = new ArrayList<String>();
        nameValues_me = new ArrayList<String>();
        nameValues_op = new ArrayList<String>();
        nameValues_ts = new ArrayList<String>();

        Bundle b = this.getArguments();
        searchObj = b.getString("searchResult");
        Log.d("TAG", searchObj);

        try {
            JSONObject responseJObj = new JSONObject(searchObj);
            JSONArray jsonArray = responseJObj.getJSONArray(ENTRY_LIST);
            // Log.d("searchObj", responseJObj.toString());
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObj = jsonArray.getJSONObject(i);
                JSONArray records = jsonObj.getJSONArray(RECORDS);
                String module = jsonObj.getString("name");

                if (module.equals("Accounts")) {

                    for (int j = 0; j < records.length(); j++) {

                        JSONObject jObj = records.getJSONObject(j);
                        JSONObject name = jObj.getJSONObject("name");
                        String nameValue = name.getString("value");

                        nameValues_ac.add(nameValue);


                    }
                } else if (module.equals("Contacts")) {

                    for (int j = 0; j < records.length(); j++) {

                        JSONObject jObj = records.getJSONObject(j);
                        JSONObject name = jObj.getJSONObject("name");
                        String nameValue = name.getString("value");


                        nameValues_co.add(nameValue);

                    }

                } else if (module.equals("Opportunities")) {

                    for (int j = 0; j < records.length(); j++) {

                        JSONObject jObj = records.getJSONObject(j);
                        JSONObject name = jObj.getJSONObject("name");
                        String nameValue = name.getString("value");


                        nameValues_op.add(nameValue);

                    }

                } else if (module.equals("Leads")) {

                    for (int j = 0; j < records.length(); j++) {

                        JSONObject jObj = records.getJSONObject(j);
                        JSONObject name = jObj.getJSONObject("name");
                        String nameValue = name.getString("value");


                        nameValues_le.add(nameValue);

                    }

                } else if (module.equals("Calls")) {

                    for (int j = 0; j < records.length(); j++) {

                        JSONObject jObj = records.getJSONObject(j);
                        JSONObject name = jObj.getJSONObject("name");
                        String nameValue = name.getString("value");


                        nameValues_cl.add(nameValue);

                    }

                } else if (module.equals("Meetings")) {

                    for (int j = 0; j < records.length(); j++) {

                        JSONObject jObj = records.getJSONObject(j);
                        JSONObject name = jObj.getJSONObject("name");
                        String nameValue = name.getString("value");


                        nameValues_me.add(nameValue);

                    }

                } else if (module.equals("Tasks")) {

                    for (int j = 0; j < records.length(); j++) {

                        JSONObject jObj = records.getJSONObject(j);
                        JSONObject name = jObj.getJSONObject("name");
                        String nameValue = name.getString("value");


                        nameValues_ts.add(nameValue);

                    }

                }


            }
            int size_ac = nameValues_ac.size();
            int size_co = nameValues_co.size();
            int size_op = nameValues_op.size();
            int size_me = nameValues_me.size();
            int size_ts = nameValues_ts.size();
            int size_ca = nameValues_ca.size();
            int size_cl = nameValues_cl.size();
            int size_le = nameValues_le.size();

            text_ac.setText(String.valueOf(size_ac));
            text_co.setText(String.valueOf(size_co));
            text_op.setText(String.valueOf(size_op));
            text_me.setText(String.valueOf(size_me));
            text_ts.setText(String.valueOf(size_ts));
            text_ca.setText(String.valueOf(size_ca));
            text_cl.setText(String.valueOf(size_cl));
            text_le.setText(String.valueOf(size_le));


        } catch (JSONException e) {
            Log.e("My App", "Could not parse JSON");
            e.printStackTrace();
        }


        dialog.dismiss();

        return rootView;

    }


    @Override
    public void onClick(View v) {

        Fragment_Search_result fragment = new Fragment_Search_result();

        switch (v.getId()) {
            case R.id.ln1:
                Log.d("Layout Clicked ", "Account");
                Log.d("searchResult", searchObj);
                Fragment_Search_result fragment1 = new Fragment_Search_result();
                Bundle bundle = new Bundle();
                bundle.putString("moduleName", "Accounts");
                bundle.putString("result_list", searchObj);

                fragment1.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment1, TAG).addToBackStack(TAG).commit();


                break;
            case R.id.ln2:
                Log.d("Layout Clicked ", "Contacts");


                Bundle bundle1 = new Bundle();
                bundle1.putString("moduleName", "Contacts");
                bundle1.putString("result_list", searchObj);
                fragment.setArguments(bundle1);

                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();

                break;
            case R.id.ln3:
                Log.d("Layout Clicked ", "Opportunities");


                Bundle bundle2 = new Bundle();
                bundle2.putString("moduleName", "Opportunities");
                bundle2.putString("result_list", searchObj);
                fragment.setArguments(bundle2);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, fragment, TAG).addToBackStack("null")
                        .commit();

                break;
            case R.id.ln4:
                Log.d("Layout Clicked ", "Leads");


                Bundle bundle3 = new Bundle();
                bundle3.putString("moduleName", "Leads");
                bundle3.putString("result_list", searchObj);
                fragment.setArguments(bundle3);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame,
                                fragment, TAG).addToBackStack("null")
                        .commit();

                break;
            case R.id.ln5:
                Log.d("Layout Clicked ", "Calls");


                Bundle bundle4 = new Bundle();
                bundle4.putString("moduleName", "Calls");
                bundle4.putString("result_list", searchObj);
                fragment.setArguments(bundle4);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame,
                                fragment, TAG).addToBackStack("null")
                        .commit();

                break;
            case R.id.ln6:
                Log.d("Layout Clicked ", "Meetings");

                Bundle bundle5 = new Bundle();
                bundle5.putString("moduleName", "Meetings");
                bundle5.putString("result_list", searchObj);
                fragment.setArguments(bundle5);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame,
                                fragment, TAG).addToBackStack("null")
                        .commit();

                break;
            case R.id.ln7:
                Log.d("Layout Clicked ", "Tasks");

                Bundle bundle7 = new Bundle();
                bundle7.putString("moduleName", "Tasks");
                bundle7.putString("result_list", searchObj);
                fragment.setArguments(bundle7);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame,
                                fragment, TAG).addToBackStack("null")
                        .commit();

                break;
            case R.id.ln8:
                Log.d("Layout Clicked ", "Cases");

                Bundle bundle8 = new Bundle();
                bundle8.putString("moduleName", "Cases");
                bundle8.putString("result_list", searchObj);
                fragment.setArguments(bundle8);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame,
                                fragment, TAG).addToBackStack("null")
                        .commit();

                break;
            default:
                break;
        }
    }
}

