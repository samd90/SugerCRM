package com.androidcrm.wakensys.sugercrm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.ENTRY_LIST;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RECORDS;

import com.androidcrm.wakensys.sugercrm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.androidcrm.wakensys.sugercrm.AdapterClass.EntriesListAdapter;


/**
 * Created by SAYANTHAN on 1/14/2015.
 */
public class Fragment_Search_result extends Fragment implements AdapterView.OnItemClickListener {

    private ListView search_list;
    private TextView module_name;
    private String moduleName;
    private List<String> nameValues;
    private List<String> idValues;


    private JSONObject searchObj;


    public final static String TAG = Fragment_Search_result.class.getSimpleName();

    public static Fragment_Search_result newInstance() {
        return new Fragment_Search_result();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_result, container,
                false);
        try {
            search_list = (ListView) rootView.findViewById(R.id.li1);
            module_name = (TextView) rootView.findViewById(R.id.tv1);

            nameValues = new ArrayList<String>();
            idValues = new ArrayList<String>();


            Bundle b = this.getArguments();
            String s = b.getString("result_list");
            moduleName = b.getString("moduleName");
            Log.d("TAG", s);
            Log.d("TAG", moduleName);

            module_name.setText(moduleName.toString());

            JSONObject responseJObj = new JSONObject(s);
            JSONArray jsonArray = responseJObj.getJSONArray(ENTRY_LIST);
            // Log.d("searchObj", responseJObj.toString());
            for (int i = 0; i < jsonArray.length(); i++) {

                searchObj = jsonArray.getJSONObject(i);
                JSONArray records = searchObj.getJSONArray(RECORDS);
                String module = searchObj.getString("name");

                if (moduleName.equals(module)) {

                    for (int j = 0; j < records.length(); j++) {

                        JSONObject jObj = records.getJSONObject(j);
                        JSONObject name = jObj.getJSONObject("name");
                        String nameValue = name.getString("value");
                        JSONObject id = jObj.getJSONObject("id");
                        String idValue = id.getString("value");

                        nameValues.add(nameValue);
                        idValues.add(idValue);

                    }
                }


            }

            EntriesListAdapter draweradapter = new EntriesListAdapter(
                    getActivity().getApplicationContext(), nameValues);
            search_list.setAdapter(draweradapter);

            search_list.setOnItemClickListener(this);

        } catch (JSONException e) {
            Log.e("My App", "Could not parse JSON");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            switch (moduleName) {
                case "Accounts":
                   /* String entry_id = idValues.get(position);
                    Entry_Relationship_Account fragment1 = new Entry_Relationship_Account();
                    Bundle bundle = new Bundle();
                    bundle.putString("module_id", entry_id);

                    fragment1.setArguments(bundle);

                    getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment1, TAG).addToBackStack(TAG).commit();
                    */
                    break;
                case "Contacts":

                    break;
                case "Opportunities":


                    break;
                case "Leads":

                    break;
                case "Calls":


                    break;
                case "Meetings":


                    break;
                case "Tasks":

                    break;
                case "Cases":


                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
