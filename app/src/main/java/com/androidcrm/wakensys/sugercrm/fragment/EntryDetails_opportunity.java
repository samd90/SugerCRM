package com.androidcrm.wakensys.sugercrm.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.androidcrm.wakensys.sugercrm.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.widget.TextView;

public class EntryDetails_opportunity extends ActionBarActivity {

    private TextView a_name, amount, close_date, type, lead_s, sale_s,
            campaign, prob, assgn_to, crt_by, date_crtd, date_modi;

    private String acc_name;
    private String opp_amount;
    private String c_date;
    private String lead;
    private String o_type;
    private String sales_st;
    private String camp;
    private String probl;
    private String assing_to;
    private String creat_by;
    private String date_c;
    private String date_m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_details_opportunity_layout);

        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            a_name = (TextView) findViewById(R.id.text_subje);
            amount = (TextView) findViewById(R.id.text_amount);
            close_date = (TextView) findViewById(R.id.text_close_date);
            type = (TextView) findViewById(R.id.text_type);
            lead_s = (TextView) findViewById(R.id.text_lead_s);
            sale_s = (TextView) findViewById(R.id.text_sale_s);
            campaign = (TextView) findViewById(R.id.text_camp);
            prob = (TextView) findViewById(R.id.text_prob);
            assgn_to = (TextView) findViewById(R.id.text_assign);
            crt_by = (TextView) findViewById(R.id.text_crtd_by);
            date_crtd = (TextView) findViewById(R.id.text_date_crtd);
            date_modi = (TextView) findViewById(R.id.text_date_mod);
            setSupportActionBar(toolbar);
            Intent ij = getIntent();
            Bundle b = ij.getExtras();
            String s = b.getString("opportunutyDetails");

            JSONObject name_value_list = new JSONObject(s);
            Log.d("jsonObj", "" + name_value_list.toString());

            JSONObject m_name_ = name_value_list.getJSONObject("name");
            acc_name = m_name_.getString("value");
            Log.d("acc_name", acc_name);

            JSONObject opportunity_type = name_value_list
                    .getJSONObject("opportunity_type");
            o_type = opportunity_type.getString("value");
            Log.d("o_type", o_type);

            JSONObject amount_usdollar_ = name_value_list
                    .getJSONObject("amount_usdollar");
            opp_amount = amount_usdollar_.getString("value");
            Log.d("opp_amount", opp_amount);

            JSONObject date_closed_ = name_value_list
                    .getJSONObject("date_closed");
            c_date = date_closed_.getString("value");
            Log.d("c_date", c_date);

            JSONObject lead_source_ = name_value_list
                    .getJSONObject("lead_source");
            lead = lead_source_.getString("value");
            Log.d("lead", lead);

            JSONObject sales_stage_ = name_value_list
                    .getJSONObject("sales_stage");
            sales_st = sales_stage_.getString("value");
            Log.d("sales_st", sales_st);

            JSONObject campaign_name_ = name_value_list
                    .getJSONObject("campaign_name");
            camp = campaign_name_.getString("value");
            Log.d("camp", camp);

            JSONObject probability = name_value_list
                    .getJSONObject("probability");
            probl = probability.getString("value");
            Log.d("probl", probl);

            JSONObject assigned_user_name_ = name_value_list
                    .getJSONObject("assigned_user_name");
            assing_to = assigned_user_name_.getString("value");
            Log.d("assing_to", assing_to);

            JSONObject created_by_name = name_value_list
                    .getJSONObject("created_by_name");
            creat_by = created_by_name.getString("value");
            Log.d("creat_by", creat_by);

            JSONObject date_entered = name_value_list
                    .getJSONObject("date_entered");
            date_c = date_entered.getString("value");
            Log.d("date_c", date_c);

            JSONObject date_modified_ = name_value_list
                    .getJSONObject("date_modified");
            date_m = date_modified_.getString("value");
            Log.d("date_m", date_m);


            a_name.setText(acc_name);
            amount.setText(opp_amount);
            close_date.setText(c_date);
            type.setText(o_type);
            lead_s.setText(lead);
            sale_s.setText(sales_st);
            campaign.setText(camp);
            prob.setText(probl);
            assgn_to.setText(assing_to);
            crt_by.setText(creat_by);
            date_crtd.setText(date_c);
            date_modi.setText(date_m);

        } catch (JSONException e) {
            Log.e("My App", "Could not parse JSON");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
