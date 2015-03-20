package com.androidcrm.wakensys.sugercrm.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.widget.TextView;

import com.androidcrm.wakensys.sugercrm.R;

public class EntryDetailsView extends ActionBarActivity {

    private TextView txt_order_number, txt_due_time,txt_date_finish,txt_time_finishr,txt_price_usdollar,txt_cost_usdollar,dataView_maincode,txt_product_total_price_usdollar, txt_vat_amt_usdollar, txt_product_discount_amount_us, txt_product_discount_usdollar,txt_product_list_price_usdollar,txt_product_cost_price_usdollar, txt_product_qty, txt_assistant_phone, txt_assistant, txt_salutationt,txt_tax_shipping_amount,txt_shipping_amount,txt_discount_amount, txt_total_amt,txt_number, txt_billing_contact, txt_billing_account_name, txt_success, txt_error, txt_amount_usd, txt_case_number, txt_source, txt_resolution, txt_priority, txt_bug_number, txt_name, txt_origin, txt_subject, txt_opportunity_name, txt_product_name, txt_file_name, txt_part_number, txt_product_category, txt_opportunity_amount, txt_first_name, txt_last_name, txt_title, txt_contract_title, txt_invoice_number, txt_quote_number, txt_quote_stage, txt_document_name, txt_location, txt_destination, txt_website, txt_status, txt_invoice_status, txt_revision, txt_valid_until, txt_contract_manager, txt_document_type, txt_template, txt_billing_address, txt_start_Date_and_time, txt_start_Date, txt_depart, txt_publish_Date, txt_expected_close_date, txt_end_date, txt_expectedCloseDate, txt_return, txt_due_date, txt_quote_Date, txt_invoice_Date, txt_class, txt_flightName, txt_created_by, txt_category, txt_sub_category, txt_expiration_Date, txt_reference_code, txt_renewal_reminder_date, txt_opportunity, txt_contractValue, txt_contract, txt_contractType, txt_customer_signed_date, txt_company_signed_date, txt_amount, txt_sales_stage, txt_email, txt_module_name, txt_type, txt_product_type, txt_next_step, txt_lead_source, txt_date_created, txt_adults, txt_children, txt_departure_time, txt_arrival_time, txt_probability, txt_campaign, txt_assigned_to, txt_payment_terms, txt_approval_status, txt_date_modified, txt_off_phone, txt_duration, txt_email_invite_template, txt_decline_redirect_url, txt_mobile_phone, txt_impression, txt_department, txt_budget, txt_actual_cost, txt_expected_cost, txt_expected_revenue, txt_reminders, txt_fax, txt_account_name, txt_primary_address, txt_other_address, txt_shipping_address, txt_objective, txt_salary, txt_flightFairs, txt_description, txt_related_document, txt_related_document_revision, txt_price, txt_currency, txt_cost, txt_contact, txt_url, txt_attachment, txt_note, txt_is_parent_category, txt_parent_category, txt_product_image;

    private TextView dataView_order_number, dataView_due_time,dataView_date_finish,dataView_time_finishr,dataView_price_usdollar,dataview_cost_usdollar,txt_maincode,dataView_product_total_price_usdollar, dataView_vat_amt_usdollar, dataView_product_discount_amount_us, dataView_product_discount_usdollar,dataView_product_list_price_usdollar,dataView_product_cost_price_usdollar, dataView_product_qty, dataView_assistant_phone, dataView_assistant, dataView_salutation, dataView_tax_shipping_amount,dataView_shipping_amount,dataView_discount_amount, dataView_total_amt,dataView_number, dataView_success, dataView_error,dataView_amount_usd, dataView_case_number,dataView_billing_address, dataView_resolution, dataView_priority, dataView_name_subject_fileName_origin_opportunityName, dataView_type, dataView_bug_number, dataView_part_number, dataView_product_category, dataView_opportunityAmount, dataView_first_name, dataView_last_name, dataView_title_contractTitle, dataView_invoice_number, dataView_quote_number, dataView_quote_stage, dataView_duration_phone_office, dataView_document_name, dataView_location_destination, dataView_website_status_invoiceStatus, dataView_revision, dataView_valid_until, dataView_contractManager, dataView_document_type, dataView_startDateAndTime_startDate_expectedCloseDate_publishDate_depart_billing_address, dataView_endDate_return_due_date_expected_close_date, dataView_quote_date, dataView_invoice_date, dataView_class, dataView_flightName, dataView_created_by, dataView_category, dataView_sub_category, dataView_expiration_Date, dataView_referenceCode, dataView_renewalReminderDate, dataView_opportunity, dataView_contractValue, dataView_contract, dataView_contractType, dataView_customer_signed_date, dataView_company_signed_date, dataView_amount, dataView_sales_stage, dataView_moduleName_email, dataView_type_product_type, dataView_next_step, dataView_lead_source, dataView_date_created, dataView_adults, dataView_children, dataView_departure_time, dataView_arrival_time, dataView_probability, dataView_campaign, dataView_assigned_to, dataView_payment_terms, dataView_approval_status, dataView_date_modified, dataView_duration, dataView_email_invite_template, dataView_decline_redirect_url, dataView_mobile_phone, dataView_impression, dataView_department, dataView_budget, dataView_actual_cost, dataView_expected_cost, dataView_expected_revenue, dataView_fax, dataView_account_name, dataView_primary_address, dataView_other_address, dataView_shipping_address, dataView_objective, dataView_salary, dataView_flight_fairs, dataView_description, dataView_related_document, dataView_related_document_revision, dataView_price, dataView_currency, dataView_cost, dataView_contact, dataView_url, dataView_attachment, dataView_note, dataView_parent_category, dataView_product_image;

    private String duraion, module_name, name, account_type, industry, annual_revenue, rating, phone_office, phone_alternate, ownership, employees, ticker_symbol, deleted, bug_number, found_in_release, fixed_in_release, source, product_category,
            date_entered, date_modified, duration_hours, duration_minutes, direction, outlook_id, reschedule_history, reschedule_count, tracker_key, tracker_count, refer_url, tracker_text, impressions, expected_cost, actual_cost, expected_revenue, campaign_type, objective, content, frequency, case_number, priority, resolution, work_log, state, update_text, internal, wak_commision_type, first_name, last_name, title, phone_home, phone_mobile, birthdate, reference_code, start_date, end_date, total_contract_value, contract_type, renewal_reminder_date, contract_account, opportunity, discount_amount_usdollar, tax_amount, tax_amount_usdollar, shipping_amount_usdollar, shipping_tax, shipping_tax_amt, shipping_tax_amt_usdollar, total_amount, total_amount_usdollar, active_date, document_name, exp_date, category_id, subcategory_id, status_id, document_revision_id, is_template, template_type, user_name, user_hash, budget, invite_templates, accept_redirect, origin, destination, pricein, arrivaltime, departuretime, effectivedate, cabinclass, depart, return1, children, adults, traveltime, customername, billing_account, billing_contact, billing_address_street, billing_address_city, billing_address_state, billing_address_postalcode, billing_address_country, shipping_address_street, shipping_address_city, shipping_address_state, shipping_address_postalcode, shipping_address_country, number, total_amt, total_amt_usdollar, subtotal_amount, subtotal_amount_usdollar, discount_amount, shipping_amount, quote_number, quote_date, invoice_date, due_date, template_ddown_c, salutation, department, do_not_call, phone_work, phone_other, phone_fax, email1, primary_address_street, primary_address_city, primary_address_state, primary_address_postalcode, primary_address_country, alt_address_street, alt_address_city, alt_address_state, alt_address_postalcode, alt_address_country, assistant, assistant_phone, converted, refered_by, lead_source_description, status_description, account_name, account_description, opportunity_name, opportunity_amount, website, location, date_start, date_end, status, reminder_time, email_reminder_time, file_mime_type, filename, parent_type, portal_flag, embed_flag, description, opportunity_type, lead_source, amount, amount_usdollar, date_closed, next_step, sales_stage, probability, maincode, part_number, category, type, cost, cost_usdollar, currency_id, price, price_usdollar, url, contact, product_image, aos_product_category_name, billing_address, shipping_address
              , flightinfo, error, success, item_description, product_qty, product_cost_price_usdollar, product_list_price_usdollar, product_discount_usdollar, product_discount_amount_us, discount, vat_amt_usdollar, product_total_price_usdollar, time_finish, date_finish, date_due, time_due, order_number, task_number, estimated_start_date, estimated_end_date, approval_issue, expiration, approval_status, invoice_status, report_module,key_salary_type, basic_salary;
    private CharSequence mTitle;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_details_layout);

        txt_order_number = (TextView) findViewById(R.id.txt_order_number);
        dataView_order_number = (TextView) findViewById(R.id.dataView_order_number);
        txt_due_time = (TextView) findViewById(R.id.txt_due_time);
        dataView_due_time = (TextView) findViewById(R.id.dataView_due_time);
        txt_date_finish = (TextView) findViewById(R.id.txt_date_finish);
        dataView_date_finish = (TextView) findViewById(R.id.dataView_date_finish);
        txt_time_finishr = (TextView) findViewById(R.id.txt_time_finishr);
        dataView_time_finishr = (TextView) findViewById(R.id.dataView_time_finish);
        txt_price_usdollar = (TextView) findViewById(R.id.txt_price_usdollar);
        dataView_price_usdollar = (TextView) findViewById(R.id.dataView_price_usdollar);
        txt_cost_usdollar = (TextView) findViewById(R.id.txt_cost_usdollar);
        dataview_cost_usdollar = (TextView) findViewById(R.id.dataView_cost_usdollar);
        txt_maincode = (TextView) findViewById(R.id.txt_maincode);
        dataView_maincode = (TextView) findViewById(R.id.dataView_maincode);
        txt_product_total_price_usdollar = (TextView) findViewById(R.id.txt_product_total_price_usdollar);
        dataView_product_total_price_usdollar = (TextView) findViewById(R.id.dataView_product_total_price_usdollar);
        txt_vat_amt_usdollar = (TextView) findViewById(R.id.txt_vat_amt_usdollar);
        dataView_vat_amt_usdollar = (TextView) findViewById(R.id.dataView_vat_amt_usdollar);
        txt_product_discount_amount_us = (TextView) findViewById(R.id.txt_product_discount_amount_us);
        dataView_product_discount_amount_us = (TextView) findViewById(R.id.dataView_product_discount_amount_us);
        txt_product_discount_usdollar = (TextView) findViewById(R.id.txt_product_discount_usdollar);
        dataView_product_discount_usdollar = (TextView) findViewById(R.id.dataView_product_discount_usdollar);
        txt_product_list_price_usdollar = (TextView) findViewById(R.id.txt_product_list_price_usdollar);
        dataView_product_list_price_usdollar = (TextView) findViewById(R.id.dataView_product_list_price_usdollar);
        txt_product_cost_price_usdollar = (TextView) findViewById(R.id.txt_product_cost_price_usdollar);
        dataView_product_cost_price_usdollar = (TextView) findViewById(R.id.dataView_product_cost_price_usdollar);
        txt_product_qty = (TextView) findViewById(R.id.txt_product_qty);
        dataView_product_qty = (TextView) findViewById(R.id.dataView_product_qty);
        txt_assistant_phone = (TextView) findViewById(R.id.txt_assistant_phone);
        dataView_assistant_phone = (TextView) findViewById(R.id.dataView_assistant_phone);
        txt_assistant = (TextView) findViewById(R.id.txt_assistant);
        dataView_assistant = (TextView) findViewById(R.id.dataView_assistant);
        txt_salutationt = (TextView) findViewById(R.id.txt_salutationt);
        dataView_salutation = (TextView) findViewById(R.id.dataView_salutation);
        txt_tax_shipping_amount = (TextView) findViewById(R.id.txt_shipping_tax_amount);
        dataView_tax_shipping_amount = (TextView) findViewById(R.id.dataView_tax_shipping_amount);
        txt_shipping_amount = (TextView) findViewById(R.id.txt_shipping_amount);
        dataView_shipping_amount = (TextView) findViewById(R.id.dataView_shipping_amount);
        txt_discount_amount = (TextView) findViewById(R.id.txt_discount_amount);
        dataView_discount_amount = (TextView) findViewById(R.id.dataView_discount_amount);
        txt_total_amt = (TextView) findViewById(R.id.txt_total_amt);
        dataView_total_amt = (TextView) findViewById(R.id.dataView_total_amt);
        txt_number = (TextView) findViewById(R.id.txt_number);
        dataView_number = (TextView) findViewById(R.id.dataView_number);
        txt_billing_contact = (TextView) findViewById(R.id.txt_billing_contact);
        txt_source = (TextView) findViewById(R.id.txt_source);
        txt_resolution = (TextView) findViewById(R.id.txt_resolution);
        dataView_resolution = (TextView) findViewById(R.id.dataView_resolution);
        txt_priority = (TextView) findViewById(R.id.txt_priority);
        dataView_priority = (TextView) findViewById(R.id.dataView_priority);
        txt_bug_number = (TextView) findViewById(R.id.txt_bug_number);
        dataView_bug_number = (TextView) findViewById(R.id.dataView_bug_number);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_origin = (TextView) findViewById(R.id.txt_origin);
        txt_subject = (TextView) findViewById(R.id.txt_subject);
        txt_opportunity_name = (TextView) findViewById(R.id.txt_opportunity_name);
        txt_product_name = (TextView) findViewById(R.id.txt_product_name);
        txt_file_name = (TextView) findViewById(R.id.txt_file_name);
        txt_part_number = (TextView) findViewById(R.id.txt_part_number);
        txt_product_category = (TextView) findViewById(R.id.txt_product_category);
        txt_opportunity_amount = (TextView) findViewById(R.id.txt_opportunity_amount);
        txt_first_name = (TextView) findViewById(R.id.txt_first_name);
        txt_last_name = (TextView) findViewById(R.id.txt_last_name);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_contract_title = (TextView) findViewById(R.id.txt_contract_title);
        txt_invoice_number = (TextView) findViewById(R.id.txt_invoice_number);
        txt_quote_number = (TextView) findViewById(R.id.txt_quote_number);
        txt_quote_stage = (TextView) findViewById(R.id.txt_quote_stage);
        txt_document_name = (TextView) findViewById(R.id.txt_document_name);
        txt_location = (TextView) findViewById(R.id.txt_location);
        txt_destination = (TextView) findViewById(R.id.txt_destination);
        txt_website = (TextView) findViewById(R.id.txt_website);
        txt_status = (TextView) findViewById(R.id.txt_status);
        txt_invoice_status = (TextView) findViewById(R.id.txt_invoice_status);
        txt_revision = (TextView) findViewById(R.id.txt_revision);
        txt_valid_until = (TextView) findViewById(R.id.txt_valid_until);
        txt_contract_manager = (TextView) findViewById(R.id.txt_contract_manager);
        txt_document_type = (TextView) findViewById(R.id.txt_document_type);
        txt_template = (TextView) findViewById(R.id.txt_template);
        txt_billing_address = (TextView) findViewById(R.id.txt_billing_address);
        txt_start_Date_and_time = (TextView) findViewById(R.id.txt_start_Date_and_time);
        txt_start_Date = (TextView) findViewById(R.id.txt_start_Date);
        txt_depart = (TextView) findViewById(R.id.txt_depart);
        txt_publish_Date = (TextView) findViewById(R.id.txt_publish_Date);
        txt_expected_close_date = (TextView) findViewById(R.id.txt_expected_close_date);
        txt_end_date = (TextView) findViewById(R.id.txt_end_date);
        txt_expectedCloseDate = (TextView) findViewById(R.id.txt_expectedCloseDate);
        txt_return = (TextView) findViewById(R.id.txt_return);
        txt_due_date = (TextView) findViewById(R.id.txt_due_date);
        txt_quote_Date = (TextView) findViewById(R.id.txt_quote_Date);
        txt_invoice_Date = (TextView) findViewById(R.id.txt_invoice_Date);
        txt_class = (TextView) findViewById(R.id.txt_class);
        txt_flightName = (TextView) findViewById(R.id.txt_flightName);
        txt_created_by = (TextView) findViewById(R.id.txt_created_by);
        txt_category = (TextView) findViewById(R.id.txt_category);
        txt_sub_category = (TextView) findViewById(R.id.txt_sub_category);
        txt_expiration_Date = (TextView) findViewById(R.id.txt_expiration_Date);
        txt_reference_code = (TextView) findViewById(R.id.txt_reference_code);
        txt_renewal_reminder_date = (TextView) findViewById(R.id.txt_renewal_reminder_date);
        txt_opportunity = (TextView) findViewById(R.id.txt_opportunity);
        txt_contractValue = (TextView) findViewById(R.id.txt_contractValue);
        txt_contract = (TextView) findViewById(R.id.txt_contract);
        txt_contractType = (TextView) findViewById(R.id.txt_contractType);
        txt_customer_signed_date = (TextView) findViewById(R.id.txt_customer_signed_date);
        txt_company_signed_date = (TextView) findViewById(R.id.txt_company_signed_date);
        txt_amount = (TextView) findViewById(R.id.txt_amount);
        txt_sales_stage = (TextView) findViewById(R.id.txt_sales_stage);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_module_name = (TextView) findViewById(R.id.txt_module_name);
        txt_type = (TextView) findViewById(R.id.txt_type);
        txt_product_type = (TextView) findViewById(R.id.txt_product_type);
        txt_next_step = (TextView) findViewById(R.id.txt_next_step);
        txt_lead_source = (TextView) findViewById(R.id.txt_lead_source);
        txt_date_created = (TextView) findViewById(R.id.txt_date_created);
        txt_adults = (TextView) findViewById(R.id.txt_adults);
        txt_children = (TextView) findViewById(R.id.txt_children);
        txt_departure_time = (TextView) findViewById(R.id.txt_departure_time);
        txt_arrival_time = (TextView) findViewById(R.id.txt_arrival_time);
        txt_probability = (TextView) findViewById(R.id.txt_probability);
        txt_campaign = (TextView) findViewById(R.id.txt_campaign);
        txt_assigned_to = (TextView) findViewById(R.id.txt_assigned_to);
        txt_payment_terms = (TextView) findViewById(R.id.txt_payment_terms);
        txt_approval_status = (TextView) findViewById(R.id.txt_approval_status);
        txt_date_modified = (TextView) findViewById(R.id.txt_date_modified);
        txt_off_phone = (TextView) findViewById(R.id.txt_off_phone);
        txt_duration = (TextView) findViewById(R.id.txt_duration);
        txt_email_invite_template = (TextView) findViewById(R.id.txt_email_invite_template);
        txt_decline_redirect_url = (TextView) findViewById(R.id.txt_decline_redirect_url);
        txt_mobile_phone = (TextView) findViewById(R.id.txt_mobile_phone);
        txt_impression = (TextView) findViewById(R.id.txt_impression);
        txt_department = (TextView) findViewById(R.id.txt_department);
        txt_budget = (TextView) findViewById(R.id.txt_budget);
        txt_actual_cost = (TextView) findViewById(R.id.txt_actual_cost);
        txt_expected_cost = (TextView) findViewById(R.id.txt_expected_cost);
        txt_expected_revenue = (TextView) findViewById(R.id.txt_expected_revenue);
        txt_reminders = (TextView) findViewById(R.id.txt_reminders);
        txt_fax = (TextView) findViewById(R.id.txt_fax);
        txt_account_name = (TextView) findViewById(R.id.txt_account_name);
        txt_primary_address = (TextView) findViewById(R.id.txt_primary_address);
        txt_other_address = (TextView) findViewById(R.id.txt_other_address);
        txt_shipping_address = (TextView) findViewById(R.id.txt_shipping_address);
        txt_objective = (TextView) findViewById(R.id.txt_objective);
        txt_salary = (TextView) findViewById(R.id.txt_salary);
        txt_flightFairs = (TextView) findViewById(R.id.txt_flightFairs);
        txt_description = (TextView) findViewById(R.id.txt_description);
        txt_related_document = (TextView) findViewById(R.id.txt_related_document);
        txt_related_document_revision = (TextView) findViewById(R.id.txt_related_document_revision);
        txt_price = (TextView) findViewById(R.id.txt_price);
        txt_currency = (TextView) findViewById(R.id.txt_currency);
        txt_cost = (TextView) findViewById(R.id.txt_cost);
        txt_contact = (TextView) findViewById(R.id.txt_contact);
        txt_url = (TextView) findViewById(R.id.txt_url);
        txt_attachment = (TextView) findViewById(R.id.txt_attachment);
        txt_note = (TextView) findViewById(R.id.txt_note);
        txt_is_parent_category = (TextView) findViewById(R.id.txt_is_parent_category);
        txt_parent_category = (TextView) findViewById(R.id.txt_parent_category);
        txt_product_image = (TextView) findViewById(R.id.txt_product_image);
        dataView_name_subject_fileName_origin_opportunityName = (TextView) findViewById(R.id.dataView_name_subject_fileName_origin_opportunityName);
        dataView_part_number = (TextView) findViewById(R.id.dataView_part_number);
        dataView_product_category = (TextView) findViewById(R.id.dataView_product_category);
        dataView_opportunityAmount = (TextView) findViewById(R.id.dataView_opportunityAmount);
        dataView_first_name = (TextView) findViewById(R.id.dataView_first_name);
        dataView_last_name = (TextView) findViewById(R.id.dataView_last_name);
        dataView_title_contractTitle = (TextView) findViewById(R.id.dataView_title_contractTitle);
        dataView_invoice_number = (TextView) findViewById(R.id.dataView_invoice_number);
        dataView_quote_number = (TextView) findViewById(R.id.dataView_quote_number);
        dataView_quote_stage = (TextView) findViewById(R.id.dataView_quote_stage);
        dataView_duration_phone_office = (TextView) findViewById(R.id.dataView_duration_phone_office);
        dataView_document_name = (TextView) findViewById(R.id.dataView_document_name);
        dataView_location_destination = (TextView) findViewById(R.id.dataView_location_destination);
        dataView_website_status_invoiceStatus = (TextView) findViewById(R.id.dataView_website_status_invoiceStatus);
        dataView_revision = (TextView) findViewById(R.id.dataView_revision);
        dataView_valid_until = (TextView) findViewById(R.id.dataView_valid_until);
        dataView_contractManager = (TextView) findViewById(R.id.dataView_contractManager);
        dataView_document_type = (TextView) findViewById(R.id.dataView_document_type);
        dataView_startDateAndTime_startDate_expectedCloseDate_publishDate_depart_billing_address = (TextView) findViewById(R.id.dataView_startDateAndTime_startDate_expectedCloseDate_publishDate_depart_billing_address);
        dataView_endDate_return_due_date_expected_close_date = (TextView) findViewById(R.id.dataView_endDate_return_due_date_expected_close_date);
        dataView_quote_date = (TextView) findViewById(R.id.dataView_quote_date);
        dataView_invoice_date = (TextView) findViewById(R.id.dataView_invoice_date);
        dataView_class = (TextView) findViewById(R.id.dataView_class);
        dataView_flightName = (TextView) findViewById(R.id.dataView_flightName);
        dataView_created_by = (TextView) findViewById(R.id.dataView_created_by);
        dataView_category = (TextView) findViewById(R.id.dataView_category);
        dataView_sub_category = (TextView) findViewById(R.id.dataView_sub_category);
        dataView_expiration_Date = (TextView) findViewById(R.id.dataView_expiration_Date);
        dataView_referenceCode = (TextView) findViewById(R.id.dataView_referenceCode);
        dataView_renewalReminderDate = (TextView) findViewById(R.id.dataView_renewalReminderDate);
        dataView_opportunity = (TextView) findViewById(R.id.dataView_opportunity);
        dataView_contractValue = (TextView) findViewById(R.id.dataView_contractValue);
        dataView_contract = (TextView) findViewById(R.id.dataView_contract);
        dataView_contractType = (TextView) findViewById(R.id.dataView_contractType);
        dataView_customer_signed_date = (TextView) findViewById(R.id.dataView_customer_signed_date);
        dataView_company_signed_date = (TextView) findViewById(R.id.dataView_company_signed_date);
        dataView_amount = (TextView) findViewById(R.id.dataView_amount);
        dataView_sales_stage = (TextView) findViewById(R.id.dataView_sales_stage);
        dataView_moduleName_email = (TextView) findViewById(R.id.dataView_moduleName_email);
        dataView_type_product_type = (TextView) findViewById(R.id.dataView_type_product_type);
        dataView_next_step = (TextView) findViewById(R.id.dataView_next_step);
        dataView_lead_source = (TextView) findViewById(R.id.dataView_lead_source);
        dataView_date_created = (TextView) findViewById(R.id.dataView_date_created);
        dataView_adults = (TextView) findViewById(R.id.dataView_adults);
        dataView_children = (TextView) findViewById(R.id.dataView_children);
        dataView_departure_time = (TextView) findViewById(R.id.dataView_departure_time);
        dataView_arrival_time = (TextView) findViewById(R.id.dataView_arrival_time);
        dataView_probability = (TextView) findViewById(R.id.dataView_probability);
        dataView_campaign = (TextView) findViewById(R.id.dataView_campaign);
        dataView_assigned_to = (TextView) findViewById(R.id.dataView_assigned_to);
        dataView_payment_terms = (TextView) findViewById(R.id.dataView_payment_terms);
        dataView_approval_status = (TextView) findViewById(R.id.dataView_approval_status);
        dataView_date_modified = (TextView) findViewById(R.id.dataView_date_modified);
        dataView_duration = (TextView) findViewById(R.id.dataView_duration_phone_office);
        dataView_email_invite_template = (TextView) findViewById(R.id.dataView_email_invite_template);
        dataView_decline_redirect_url = (TextView) findViewById(R.id.dataView_decline_redirect_url);
        dataView_mobile_phone = (TextView) findViewById(R.id.dataView_mobile_phone);
        dataView_impression = (TextView) findViewById(R.id.dataView_impression);
        dataView_department = (TextView) findViewById(R.id.dataView_department);
        dataView_budget = (TextView) findViewById(R.id.dataView_budget);
        dataView_actual_cost = (TextView) findViewById(R.id.dataView_actual_cost);
        dataView_expected_cost = (TextView) findViewById(R.id.dataView_expected_cost);
        dataView_expected_revenue = (TextView) findViewById(R.id.dataView_expected_revenue);
        dataView_fax = (TextView) findViewById(R.id.dataView_fax);
        dataView_account_name = (TextView) findViewById(R.id.dataView_account_name);
        dataView_primary_address = (TextView) findViewById(R.id.dataView_primary_address);
        dataView_other_address = (TextView) findViewById(R.id.dataView_other_address);
        dataView_shipping_address = (TextView) findViewById(R.id.dataView_shipping_address);
        dataView_objective = (TextView) findViewById(R.id.dataView_objective);
        dataView_salary = (TextView) findViewById(R.id.dataView_salary);
        dataView_flight_fairs = (TextView) findViewById(R.id.dataView_flight_fairs);
        dataView_description = (TextView) findViewById(R.id.dataView_description);
        dataView_related_document = (TextView) findViewById(R.id.dataView_related_document);
        dataView_related_document_revision = (TextView) findViewById(R.id.dataView_related_document_revision);
        dataView_price = (TextView) findViewById(R.id.dataView_price);
        dataView_currency = (TextView) findViewById(R.id.dataView_currency);
        dataView_cost = (TextView) findViewById(R.id.dataView_cost);
        dataView_contact = (TextView) findViewById(R.id.dataView_contact);
        dataView_url = (TextView) findViewById(R.id.dataView_url);
        dataView_attachment = (TextView) findViewById(R.id.dataView_attachment);
        dataView_note = (TextView) findViewById(R.id.dataView_note);
        dataView_parent_category = (TextView) findViewById(R.id.dataView_parent_category);
        dataView_product_image = (TextView) findViewById(R.id.dataView_product_image);
        dataView_billing_address = (TextView) findViewById(R.id.dataView_billing_address);
        txt_case_number = (TextView) findViewById(R.id.txt_case_number);
        dataView_case_number = (TextView) findViewById(R.id.dataView_case_number);
        txt_amount_usd = (TextView) findViewById(R.id.txt_amount_usd);
        dataView_amount_usd = (TextView) findViewById(R.id.dataView_amount_usd);
        txt_error = (TextView) findViewById(R.id.txt_error);
        dataView_error = (TextView) findViewById(R.id.dataView_error);
        txt_success = (TextView) findViewById(R.id.txt_success);
        dataView_success = (TextView) findViewById(R.id.dataView_success);
        txt_billing_account_name = (TextView) findViewById(R.id.txt_billing_account_name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent ij = getIntent();
        Bundle b = ij.getExtras();
        String response = b.getString("jsonResponse");
        module_name = b.getString("module_name");

        module_name = getTitle().toString();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait..");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        JSONObject name_value_list = null;
        try {
            name_value_list = new JSONObject(response);
            Log.d("jsonObj", "" + name_value_list.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject name_ = name_value_list
                    .getJSONObject("name");
            name = name_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject phone_office_ = name_value_list
                    .getJSONObject("phone_office");
            phone_office = phone_office_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject website_ = name_value_list.getJSONObject("website");
            website = website_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject phone_fax_ = name_value_list
                    .getJSONObject("phone_fax");
            phone_fax = phone_fax_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject billing_address_street_ = name_value_list
                    .getJSONObject("billing_address_street");
            billing_address_street = billing_address_street_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject billing_address_city_ = name_value_list
                    .getJSONObject("billing_address_city");
            billing_address_city = billing_address_city_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject billing_address_state_ = name_value_list.getJSONObject("billing_address_state");
            billing_address_state = billing_address_state_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject billing_address_postalcode_ = name_value_list
                    .getJSONObject("billing_address_postalcode");
            billing_address_postalcode = billing_address_postalcode_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject billing_address_country_ = name_value_list.getJSONObject("billing_address_country");
            billing_address_country = billing_address_country_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject shipping_address_street_ = name_value_list
                    .getJSONObject("shipping_address_street");
            shipping_address_street = shipping_address_street_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject shipping_address_city_ = name_value_list
                    .getJSONObject("shipping_address_city");
            shipping_address_city = shipping_address_city_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject shipping_address_state_ = name_value_list
                    .getJSONObject("shipping_address_state");
            shipping_address_state = shipping_address_state_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject shipping_address_postalcode_ = name_value_list
                    .getJSONObject("shipping_address_postalcode");
            shipping_address_postalcode = shipping_address_postalcode_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject shipping_address_country_ = name_value_list
                    .getJSONObject("shipping_address_country");
            shipping_address_country = shipping_address_country_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject email1_ = name_value_list
                    .getJSONObject("email1");
            email1 = email1_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject description_ = name_value_list
                    .getJSONObject("description");
            description = description_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject date_entered_ = name_value_list.getJSONObject("date_entered");
            date_entered = date_entered_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject date_modified_ = name_value_list
                    .getJSONObject("date_modified");
            date_modified = date_modified_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject bug_number_ = name_value_list
                    .getJSONObject("bug_number");
            bug_number = bug_number_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject type_ = name_value_list
                    .getJSONObject("type");
            type = type_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject status_ = name_value_list
                    .getJSONObject("status");
            status = status_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject priority_ = name_value_list
                    .getJSONObject("priority");
            priority = priority_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject resolution_ = name_value_list
                    .getJSONObject("resolution");
            resolution = resolution_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject source_ = name_value_list
                    .getJSONObject("source");
            source = source_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject product_category_ = name_value_list
                    .getJSONObject("product_category");
            product_category = product_category_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject duration_hours_ = name_value_list
                    .getJSONObject("duration_hours");
            duration_hours = duration_hours_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject duration_minutes_ = name_value_list
                    .getJSONObject("duration_minutes");
            duration_minutes = duration_minutes_
                    .getString("value");
            duraion = duration_hours + ":" + duration_minutes;
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject date_start_ = name_value_list
                    .getJSONObject("date_start");
            date_start = date_start_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject date_end_ = name_value_list
                    .getJSONObject("date_end");
            date_end = date_end_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject parent_type_ = name_value_list
                    .getJSONObject("parent_type");
            parent_type = parent_type_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject direction_ = name_value_list
                    .getJSONObject("direction");
            direction = direction_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject reminder_time_ = name_value_list
                    .getJSONObject("reminder_time");
            reminder_time = reminder_time_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject email_reminder_time_ = name_value_list
                    .getJSONObject("email_reminder_time");
            email_reminder_time = email_reminder_time_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject impressions_ = name_value_list
                    .getJSONObject("impressions");
            impressions = impressions_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject budget_ = name_value_list
                    .getJSONObject("budget");
            budget = budget_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject expected_cost_ = name_value_list
                    .getJSONObject("expected_cost");
            expected_cost = expected_cost_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject actual_cost_ = name_value_list
                    .getJSONObject("actual_cost");
            actual_cost = actual_cost_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject expected_revenue_ = name_value_list
                    .getJSONObject("expected_revenue");
            expected_revenue = expected_revenue_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject campaign_type_ = name_value_list
                    .getJSONObject("campaign_type");
            campaign_type = campaign_type_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject objective_ = name_value_list
                    .getJSONObject("objective");
            objective = objective_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject content_ = name_value_list
                    .getJSONObject("content");
            content = content_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject case_number_ = name_value_list
                    .getJSONObject("case_number");
            case_number = case_number_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject wak_commision_type_ = name_value_list
                    .getJSONObject("wak_commision_type");
            wak_commision_type = wak_commision_type_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject lead_source_ = name_value_list
                    .getJSONObject("lead_source");
            lead_source = lead_source_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject amount_ = name_value_list
                    .getJSONObject("amount");
            amount = amount_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject amount_usdollar_ = name_value_list
                    .getJSONObject("amount_usdollar");
            amount_usdollar = amount_usdollar_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject next_step_ = name_value_list
                    .getJSONObject("next_step");
            next_step = next_step_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject sales_stage_ = name_value_list
                    .getJSONObject("sales_stage");
            sales_stage = sales_stage_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject probability_ = name_value_list
                    .getJSONObject("probability");
            probability = probability_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONObject first_name_ = name_value_list
                    .getJSONObject("first_name");
            first_name = first_name_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject last_name_ = name_value_list
                    .getJSONObject("last_name");
            last_name = last_name_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject title_ = name_value_list
                    .getJSONObject("title");
            title = title_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject department_ = name_value_list
                    .getJSONObject("department");
            department = department_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }
//-------------------------------------------------------
        try {
            JSONObject phone_home_ = name_value_list
                    .getJSONObject("phone_home");
            phone_home = phone_home_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject phone_mobile_ = name_value_list
                    .getJSONObject("phone_mobile");
            phone_mobile = phone_mobile_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {

            JSONObject phone_work_ = name_value_list
                    .getJSONObject("phone_work");
            phone_work = phone_work_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject phone_other_ = name_value_list
                    .getJSONObject("phone_other");
            phone_other = phone_other_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject phone_fax_ = name_value_list
                    .getJSONObject("phone_fax");
            phone_fax = phone_fax_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject email1_ = name_value_list
                    .getJSONObject("email1");
            email1 = email1_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject primary_address_street_ = name_value_list
                    .getJSONObject("primary_address_street");
            primary_address_street = primary_address_street_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject primary_address_city_ = name_value_list
                    .getJSONObject("primary_address_city");
            primary_address_city = primary_address_city_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject primary_address_state_ = name_value_list
                    .getJSONObject("primary_address_state");
            primary_address_state = primary_address_state_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject primary_address_postalcode_ = name_value_list
                    .getJSONObject("primary_address_postalcode");
            primary_address_postalcode = primary_address_postalcode_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject primary_address_country_ = name_value_list
                    .getJSONObject("primary_address_country");
            primary_address_country = primary_address_country_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject currency_id_ = name_value_list
                    .getJSONObject("currency_id");
            currency_id = currency_id_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject contract_type_ = name_value_list
                    .getJSONObject("contract_type");
            contract_type = contract_type_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject document_name_ = name_value_list
                    .getJSONObject("document_name");
            document_name = document_name_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject category_id_ = name_value_list
                    .getJSONObject("category_id");
            category_id = category_id_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject status_id_ = name_value_list
                    .getJSONObject("status_id");
            status_id = status_id_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject is_template_ = name_value_list
                    .getJSONObject("is_template");
            is_template = is_template_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject template_type_ = name_value_list
                    .getJSONObject("template_type");
            template_type = template_type_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject origin_ = name_value_list
                    .getJSONObject("origin");
            origin = origin_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject pricein_ = name_value_list
                    .getJSONObject("pricein");
            pricein = pricein_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject arrivaltime_ = name_value_list
                    .getJSONObject("arrivaltime");
            arrivaltime = arrivaltime_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject departuretime_ = name_value_list
                    .getJSONObject("departuretime");
            departuretime = departuretime_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject effectivedate_ = name_value_list
                    .getJSONObject("effectivedate");
            effectivedate = effectivedate_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject cabinclass_ = name_value_list
                    .getJSONObject("cabinclass");
            cabinclass = cabinclass_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject depart_ = name_value_list
                    .getJSONObject("depart");
            depart = depart_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject return1_ = name_value_list
                    .getJSONObject("return1");
            return1 = return1_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject children_ = name_value_list
                    .getJSONObject("children");
            children = children_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject adults_ = name_value_list
                    .getJSONObject("adults");
            adults = adults_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONObject traveltime_ = name_value_list
                    .getJSONObject("traveltime");
            traveltime = traveltime_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject customername_ = name_value_list
                    .getJSONObject("customername");
            customername = customername_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject flightinfo_ = name_value_list
                    .getJSONObject("flightinfo");
            flightinfo = flightinfo_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject location_ = name_value_list
                    .getJSONObject("location");
            location = location_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //-------------------------------------------------------
        try {
            JSONObject error_ = name_value_list
                    .getJSONObject("error");
            error = error_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject success_ = name_value_list
                    .getJSONObject("success");
            success = success_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {

            JSONObject billing_account_ = name_value_list
                    .getJSONObject("billing_account");
            billing_account = billing_account_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject billing_contact_ = name_value_list
                    .getJSONObject("billing_contact");
            billing_contact = billing_contact_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject number_ = name_value_list
                    .getJSONObject("number");
            number = number_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject total_amt_ = name_value_list
                    .getJSONObject("total_amt");
            total_amt = total_amt_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject total_amt_usdollar_ = name_value_list
                    .getJSONObject("total_amt_usdollar");
            total_amt_usdollar = total_amt_usdollar_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject discount_amount_ = name_value_list
                    .getJSONObject("discount_amount");
            discount_amount = discount_amount_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject shipping_amount_ = name_value_list
                    .getJSONObject("shipping_amount");
            shipping_amount = shipping_amount_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject shipping_amount_usdollar_ = name_value_list
                    .getJSONObject("shipping_amount_usdollar");
            shipping_amount_usdollar = shipping_amount_usdollar_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject shipping_tax_ = name_value_list
                    .getJSONObject("shipping_tax");
            shipping_tax = shipping_tax_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject shipping_tax_amt_usdollar_ = name_value_list
                    .getJSONObject("shipping_tax_amt_usdollar");
            shipping_tax_amt_usdollar = shipping_tax_amt_usdollar_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();

        }try {
            JSONObject quote_number_ = name_value_list
                    .getJSONObject("quote_number");
            quote_number = quote_number_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject quote_date_ = name_value_list
                    .getJSONObject("quote_date");
            quote_date = quote_date_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject invoice_date_ = name_value_list
                    .getJSONObject("invoice_date");
            invoice_date = invoice_date_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject due_date_ = name_value_list
                    .getJSONObject("due_date");
            due_date = due_date_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject salutation_ = name_value_list
                    .getJSONObject("salutation");
            salutation = salutation_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject account_name_ = name_value_list
                    .getJSONObject("account_name");
            account_name = account_name_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject opportunity_name_ = name_value_list
                    .getJSONObject("opportunity_name");
            opportunity_name = opportunity_name_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject opportunity_amount_ = name_value_list
                    .getJSONObject("opportunity_amount");
            opportunity_amount = opportunity_amount_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject assistant_ = name_value_list
                    .getJSONObject("assistant");
            assistant = assistant_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject assistant_phone_ = name_value_list
                    .getJSONObject("assistant_phone");
            assistant_phone = assistant_phone_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject part_number_ = name_value_list
                    .getJSONObject("part_number");
            part_number = part_number_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject item_description_ = name_value_list // description
                    .getJSONObject("item_description");
            item_description = item_description_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject number_ = name_value_list    // Number:
                    .getJSONObject("number");
            number = number_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject product_qty_ = name_value_list   // Quntity
                    .getJSONObject("product_qty");
            product_qty = product_qty_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject product_cost_price_usdollar_ = name_value_list   //Cost Price (Default Currency)
                    .getJSONObject("product_cost_price_usdollar");
            product_cost_price_usdollar = product_cost_price_usdollar_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONObject product_list_price_usdollar_ = name_value_list       //List Price (Default Currency)
                    .getJSONObject("product_list_price_usdollar");
            product_list_price_usdollar = product_list_price_usdollar_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject product_discount_usdollar_ = name_value_list         // Discount (Default Currency)
                    .getJSONObject("product_discount_usdollar");
            product_discount_usdollar = product_discount_usdollar_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject product_discount_amount_us_ = name_value_list        // Discount Amount (Default Currency)
                    .getJSONObject("product_discount_amount_us");
            product_discount_amount_us = product_discount_amount_us_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject discount_ = name_value_list      // Discount Type
                    .getJSONObject("discount");
            discount = discount_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject vat_amt_usdollar_ = name_value_list          // Tax Amount (Default Currency)
                    .getJSONObject("vat_amt_usdollar");
            vat_amt_usdollar = vat_amt_usdollar_.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject product_total_price_usdollar_ = name_value_list          // Total Price (Default Currency)
                    .getJSONObject("product_total_price_usdollar");
            product_total_price_usdollar = product_total_price_usdollar_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {

            JSONObject date_closed_ = name_value_list           // Expected Close Date
                    .getJSONObject("date_closed");
            date_closed = date_closed_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject maincode_ = name_value_list          // Product Code
                    .getJSONObject("maincode");
            maincode = maincode_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject part_number_ = name_value_list       // Part Number
                    .getJSONObject("part_number");
            part_number = part_number_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject category_ = name_value_list
                    .getJSONObject("category");
            category = category_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject cost_ = name_value_list  //Cost (Default Currency)
                    .getJSONObject("cost_usdollar");
            cost_usdollar = cost_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject price_ = name_value_list             //Price (Default Currency)
                    .getJSONObject("price_usdollar");
            price_usdollar = price_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject url_ = name_value_list           //URL
                    .getJSONObject("url");
            url = url_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject contact_ = name_value_list
                    .getJSONObject("contact");
            contact = contact_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject time_finish_ = name_value_list       //Finish Time:
                    .getJSONObject("time_finish");
            time_finish = time_finish_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject date_finish_ = name_value_list       //Finish Date:
                    .getJSONObject("date_finish");
            date_finish = date_finish_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject date_due_ = name_value_list
                    .getJSONObject("date_due");
            date_due = date_due_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject time_due_ = name_value_list      // Due Time:
                    .getJSONObject("time_due");
            time_due = time_due_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject priority_ = name_value_list
                    .getJSONObject("priority");
            priority = priority_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject order_number_ = name_value_list
                    .getJSONObject("order_number");
            order_number = order_number_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject task_number_ = name_value_list
                    .getJSONObject("task_number");
            task_number = task_number_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject estimated_start_date_ = name_value_list          //Start Date
                    .getJSONObject("estimated_start_date");
            estimated_start_date = estimated_start_date_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject estimated_end_date_ = name_value_list
                    .getJSONObject("estimated_end_date");               //End Date
            estimated_end_date = estimated_end_date_
                    .getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        billing_address = billing_address_street + ", "
                + billing_address_city + ", "
                + billing_address_state + ", "
                + billing_address_postalcode + ", " + billing_address_country;

        shipping_address = shipping_address_street + ", "
                + shipping_address_city + ", "
                + shipping_address_state + ", "
                + shipping_address_postalcode + ", " + shipping_address_country;

        try {
            JSONObject approval_issue_ = name_value_list            // Approval Issues
                    .getJSONObject("approval_issue");
            approval_issue = approval_issue_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject expiration_ = name_value_list            // Valid Until
                                                                // Quote Number
                    .getJSONObject("expiration");
            expiration = expiration_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject shipping_amount_usdollar_ = name_value_list      // Shipping (Default Currency)
                    .getJSONObject("shipping_amount_usdollar");
            shipping_amount_usdollar = shipping_amount_usdollar_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject shipping_tax_amt_usdollar_ = name_value_list         //Shipping Tax (Default Currency)
                    .getJSONObject("shipping_tax_amt_usdollar");
            shipping_tax_amt_usdollar = shipping_tax_amt_usdollar_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject approval_status_ = name_value_list
                    .getJSONObject("approval_status");
            approval_status = approval_status_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject invoice_status_ = name_value_list
                    .getJSONObject("invoice_status");
            invoice_status = invoice_status_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject report_module_ = name_value_list             //Report Module
                    .getJSONObject("report_module");
            report_module = report_module_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject key_salary_type_ = name_value_list           //Type
                    .getJSONObject("key_salary_type");
            key_salary_type = key_salary_type_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject basic_salary_ = name_value_list
                    .getJSONObject("basic_salary");
            basic_salary = basic_salary_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONObject traveltime_ = name_value_list
                    .getJSONObject("traveltime");
            traveltime = traveltime_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            JSONObject customername_ = name_value_list
                    .getJSONObject("customername");
            customername = customername_
                    .getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }


            // Add Values to TextViews
        if (!"".equals(department) && department != null) {
            txt_department.setVisibility(View.VISIBLE);
            dataView_department.setVisibility(View.VISIBLE);
            dataView_department.setText(department);
        }
        if (!"".equals(title) && title != null) {
            txt_title.setVisibility(View.VISIBLE);
            dataView_title_contractTitle.setVisibility(View.VISIBLE);
            dataView_title_contractTitle.setText(title);
        }
        if (!"".equals(last_name) && last_name != null) {
            txt_last_name.setVisibility(View.VISIBLE);
            dataView_last_name.setVisibility(View.VISIBLE);
            dataView_last_name.setText(last_name);
        }
        if (!"".equals(first_name) && first_name != null) {
            txt_first_name.setVisibility(View.VISIBLE);
            dataView_first_name.setVisibility(View.VISIBLE);
            dataView_first_name.setText(first_name);
        }
        if (!"".equals(probability) && probability != null) {
            txt_probability.setVisibility(View.VISIBLE);
            dataView_probability.setVisibility(View.VISIBLE);
            dataView_probability.setText(probability);
        }
        if (!"".equals(sales_stage) && sales_stage != null) {
            txt_sales_stage.setVisibility(View.VISIBLE);
            dataView_sales_stage.setVisibility(View.VISIBLE);
            dataView_sales_stage.setText(sales_stage);
        }
        if (!"".equals(next_step) && next_step != null) {
            txt_next_step.setVisibility(View.VISIBLE);
            dataView_next_step.setVisibility(View.VISIBLE);
            dataView_next_step.setText(next_step);
        }
        if (!"".equals(amount) && amount != null) {
            txt_amount.setVisibility(View.VISIBLE);
            dataView_amount.setVisibility(View.VISIBLE);
            dataView_amount.setText(amount);
        }
        if (!"".equals(amount_usdollar) && amount_usdollar != null) {
            txt_amount_usd.setVisibility(View.VISIBLE);
            dataView_amount_usd.setVisibility(View.VISIBLE);
            dataView_amount_usd.setText(amount_usdollar);
        }
        if (!"".equals(lead_source) && lead_source != null) {
            txt_lead_source.setVisibility(View.VISIBLE);
            dataView_lead_source.setVisibility(View.VISIBLE);
            dataView_lead_source.setText(lead_source);
        }
        if (!"".equals(wak_commision_type) && wak_commision_type != null) {
            txt_type.setVisibility(View.VISIBLE);
            dataView_type.setVisibility(View.VISIBLE);
            dataView_type.setText(wak_commision_type);
        }
        if (!"".equals(case_number) && case_number != null) {
            txt_case_number.setVisibility(View.VISIBLE);
            dataView_case_number.setVisibility(View.VISIBLE);
            dataView_case_number.setText(case_number);
        }
            if (!"".equals(impressions) && impressions != null) {
                txt_impression.setVisibility(View.VISIBLE);
                dataView_impression.setVisibility(View.VISIBLE);
                dataView_impression.setText(impressions);
            }
            if (!"".equals(budget) && budget != null) {
                txt_budget.setVisibility(View.VISIBLE);
                dataView_budget.setVisibility(View.VISIBLE);
                dataView_budget.setText(budget);
            }
            if (!"".equals(expected_cost) && expected_cost != null) {
                txt_expected_cost.setVisibility(View.VISIBLE);
                dataView_expected_cost.setVisibility(View.VISIBLE);
                dataView_expected_cost.setText(expected_cost);
            }
            if (!"".equals(actual_cost) && actual_cost != null) {
                txt_actual_cost.setVisibility(View.VISIBLE);
                dataView_actual_cost.setVisibility(View.VISIBLE);
                dataView_actual_cost.setText(actual_cost);
            }

            if (!"".equals(expected_revenue) && expected_revenue != null) {
                txt_expected_revenue.setVisibility(View.VISIBLE);
                dataView_expected_revenue.setVisibility(View.VISIBLE);
                dataView_expected_revenue.setText(expected_revenue);
            }
            if (!"".equals(campaign_type) && campaign_type != null) {
                txt_campaign.setVisibility(View.VISIBLE);
                dataView_campaign.setVisibility(View.VISIBLE);
                dataView_campaign.setText(campaign_type);
            }
            if (!"".equals(objective) && objective != null) {
                txt_objective.setVisibility(View.VISIBLE);
                dataView_objective.setVisibility(View.VISIBLE);
                dataView_objective.setText(objective);
            }
            if (!"".equals(content) && content != null) {
                txt_description.setVisibility(View.VISIBLE);
                dataView_description.setVisibility(View.VISIBLE);
                dataView_description.setText(content);
            }

            if (!"".equals(name) && name != null) {
                switch (module_name) {
                    case "Accounts":
                        txt_name.setVisibility(View.VISIBLE);
                        dataView_name_subject_fileName_origin_opportunityName.setVisibility(View.VISIBLE);
                        dataView_name_subject_fileName_origin_opportunityName.setText(name);
                        break;
                    case "Bugs":
                        txt_subject.setVisibility(View.VISIBLE);
                        dataView_name_subject_fileName_origin_opportunityName.setVisibility(View.VISIBLE);
                        dataView_name_subject_fileName_origin_opportunityName.setText(name);
                        break;
                    default:
                        break;
                }

            }
            if (!"".equals(duraion) && duraion != null) {
                txt_duration.setVisibility(View.VISIBLE);
                dataView_duration.setVisibility(View.VISIBLE);
                dataView_duration.setText(duraion);
            }
            if (!"".equals(date_modified) && date_modified != null) {
                txt_start_Date.setVisibility(View.VISIBLE);
                dataView_startDateAndTime_startDate_expectedCloseDate_publishDate_depart_billing_address.setVisibility(View.VISIBLE);
                dataView_startDateAndTime_startDate_expectedCloseDate_publishDate_depart_billing_address.setText(date_modified);
            }
            if(!"".equals(date_end) && date_end != null) {
                txt_end_date.setVisibility(View.VISIBLE);
                dataView_endDate_return_due_date_expected_close_date.setVisibility(View.VISIBLE);
                dataView_endDate_return_due_date_expected_close_date.setText(date_end);
            }
            if (!"".equals(parent_type) && parent_type != null) {
                txt_parent_category.setVisibility(View.VISIBLE);
                dataView_parent_category.setVisibility(View.VISIBLE);
                dataView_parent_category.setText(parent_type);
            }

            if (!"".equals(date_entered) && date_entered != null) {
                txt_date_created.setVisibility(View.VISIBLE);
                dataView_date_created.setVisibility(View.VISIBLE);
                dataView_date_created.setText(date_entered);
            }
            if (!"".equals(date_modified) && date_modified != null) {
                txt_date_modified.setVisibility(View.VISIBLE);
                dataView_date_modified.setVisibility(View.VISIBLE);
                dataView_date_modified.setText(date_modified);
            }
            if (!"".equals(date_entered) && date_entered != null) {
                txt_description.setVisibility(View.VISIBLE);
                dataView_description.setVisibility(View.VISIBLE);
                dataView_description.setText(date_entered);
            }
            if (!"".equals(bug_number) && bug_number != null) {
                txt_bug_number.setVisibility(View.VISIBLE);
                dataView_bug_number.setVisibility(View.VISIBLE);
                dataView_bug_number.setText(bug_number);
            }
            if (!"".equals(type) && type != null) {
                txt_type.setVisibility(View.VISIBLE);
                dataView_type_product_type.setVisibility(View.VISIBLE);
                dataView_type_product_type.setText(type);
            }
            if (!"".equals(status) && status != null) {
                txt_status.setVisibility(View.VISIBLE);
                dataView_website_status_invoiceStatus.setVisibility(View.VISIBLE);
                dataView_website_status_invoiceStatus.setText(status);
            }
            if (!"".equals(priority) && priority != null) {
                txt_priority.setVisibility(View.VISIBLE);
                dataView_priority.setVisibility(View.VISIBLE);
                dataView_priority.setText(priority);
            }
            if (!"".equals(resolution) && resolution != null) {
                txt_resolution.setVisibility(View.VISIBLE);
                dataView_resolution.setVisibility(View.VISIBLE);
                dataView_resolution.setText(resolution);
            }
            if (!"".equals(source) && source != null) {
                txt_source.setVisibility(View.VISIBLE);
                dataView_lead_source.setVisibility(View.VISIBLE);
                dataView_lead_source.setText(source);
            }
            if (!"".equals(product_category) && product_category != null) {
                txt_product_category.setVisibility(View.VISIBLE);
                dataView_product_category.setVisibility(View.VISIBLE);
                dataView_product_category.setText(product_category);
            }
            if (!"".equals(website) && website != null) {
                txt_website.setVisibility(View.VISIBLE);
                dataView_website_status_invoiceStatus.setVisibility(View.VISIBLE);
                dataView_website_status_invoiceStatus.setText(website);
            }
            if (!"".equals(phone_fax) && phone_fax != null) {
                txt_fax.setVisibility(View.VISIBLE);
                dataView_fax.setVisibility(View.VISIBLE);
                dataView_fax.setText(phone_fax);
            }
            if (!"".equals(billing_address) && billing_address != null) {
                txt_billing_address.setVisibility(View.VISIBLE);
                dataView_billing_address.setVisibility(View.VISIBLE);
                dataView_billing_address.setText(billing_address);
            }
            if (!"".equals(shipping_address) && shipping_address != null) {
                txt_shipping_address.setVisibility(View.VISIBLE);
                dataView_shipping_address.setVisibility(View.VISIBLE);
                dataView_shipping_address.setText(shipping_address);
            }
            if (!"".equals(email1) && email1 != null) {
                txt_email.setVisibility(View.VISIBLE);
                dataView_moduleName_email.setVisibility(View.VISIBLE);
                dataView_moduleName_email.setText(email1);
            }
        //=============================================================================
        if (!"".equals(error) && error != null) {
            txt_error.setVisibility(View.VISIBLE);
            dataView_error.setVisibility(View.VISIBLE);
            dataView_error.setText(error);
        }
        if (!"".equals(success) && success != null) {
            txt_success.setVisibility(View.VISIBLE);
            dataView_success.setVisibility(View.VISIBLE);
            dataView_success.setText(success);
        }
        if (!"".equals(billing_account) && billing_account != null) {
            txt_account_name.setVisibility(View.VISIBLE);
            dataView_account_name.setVisibility(View.VISIBLE);
            dataView_account_name.setText(billing_account);
        }
        if (!"".equals(billing_contact) && billing_contact != null) {
            txt_billing_contact.setVisibility(View.VISIBLE);
            dataView_contact.setVisibility(View.VISIBLE);
            dataView_contact.setText(billing_contact);
        }
        if (!"".equals(number) && number != null) {
            txt_number.setVisibility(View.VISIBLE);
            dataView_number.setVisibility(View.VISIBLE);
            dataView_number.setText(number);
        }
        if (!"".equals(total_amt) && total_amt != null) {
            txt_total_amt.setVisibility(View.VISIBLE);
            dataView_total_amt.setVisibility(View.VISIBLE);
            dataView_total_amt.setText(total_amt);
        }
        if (!"".equals(discount_amount) && discount_amount != null) {
            txt_discount_amount.setVisibility(View.VISIBLE);
            dataView_discount_amount.setVisibility(View.VISIBLE);
            dataView_discount_amount.setText(discount_amount);
        }
        if (!"".equals(shipping_amount_usdollar) && shipping_amount_usdollar != null) {
            txt_shipping_amount.setVisibility(View.VISIBLE);
            dataView_shipping_amount.setVisibility(View.VISIBLE);
            dataView_shipping_amount.setText(shipping_amount_usdollar);
        }
        if (!"".equals(shipping_tax_amt_usdollar) && shipping_tax_amt_usdollar != null) {
            txt_tax_shipping_amount.setVisibility(View.VISIBLE);
            dataView_tax_shipping_amount.setVisibility(View.VISIBLE);
            dataView_tax_shipping_amount.setText(shipping_tax_amt_usdollar);
        }
        if (!"".equals(quote_number) && quote_number != null) {
            txt_quote_number.setVisibility(View.VISIBLE);
            dataView_quote_number.setVisibility(View.VISIBLE);
            dataView_quote_number.setText(quote_number);
        }
        if (!"".equals(quote_date) && quote_date != null) {
            txt_quote_Date.setVisibility(View.VISIBLE);
            dataView_quote_date.setVisibility(View.VISIBLE);
            dataView_quote_date.setText(quote_date);
        }
        if (!"".equals(invoice_date) && invoice_date != null) {
            txt_invoice_Date.setVisibility(View.VISIBLE);
            dataView_invoice_date.setVisibility(View.VISIBLE);
            dataView_invoice_date.setText(invoice_date);
        }
        if (!"".equals(due_date) && due_date != null) {
            txt_due_date.setVisibility(View.VISIBLE);
            dataView_endDate_return_due_date_expected_close_date.setVisibility(View.VISIBLE);
            dataView_endDate_return_due_date_expected_close_date.setText(due_date);
        }
        if (!"".equals(salutation) && salutation != null) {
            txt_salutationt.setVisibility(View.VISIBLE);
            dataView_salutation.setVisibility(View.VISIBLE);
            dataView_salutation.setText(salutation);
        }
        if (!"".equals(account_name) && account_name != null) {
            txt_account_name.setVisibility(View.VISIBLE);
            dataView_account_name.setVisibility(View.VISIBLE);
            dataView_account_name.setText(account_name);
        }
        if (!"".equals(opportunity_name) && opportunity_name != null) {
            txt_opportunity_name.setVisibility(View.VISIBLE);
            dataView_name_subject_fileName_origin_opportunityName.setVisibility(View.VISIBLE);
            dataView_name_subject_fileName_origin_opportunityName.setText(opportunity_name);
        }

        if (!"".equals(opportunity_amount) && opportunity_amount != null) {
            txt_opportunity_amount.setVisibility(View.VISIBLE);
            dataView_opportunityAmount.setVisibility(View.VISIBLE);
            dataView_opportunityAmount.setText(opportunity_amount);
        }
        if (!"".equals(assistant) && assistant != null) {
            txt_assistant.setVisibility(View.VISIBLE);
            dataView_assistant.setVisibility(View.VISIBLE);
            dataView_assistant.setText(assistant);
        }
        if (!"".equals(objective) && objective != null) {
            txt_objective.setVisibility(View.VISIBLE);
            dataView_objective.setVisibility(View.VISIBLE);
            dataView_objective.setText(objective);
        }
        if (!"".equals(assistant_phone) && assistant_phone != null) {
            txt_assistant_phone.setVisibility(View.VISIBLE);
            dataView_assistant_phone.setVisibility(View.VISIBLE);
            dataView_assistant_phone.setText(assistant_phone);
        }

        if (!"".equals(part_number) && part_number != null) {
            txt_part_number.setVisibility(View.VISIBLE);
            dataView_part_number.setVisibility(View.VISIBLE);
            dataView_part_number.setText(part_number);
        }
        if (!"".equals(item_description) && item_description != null) {
            txt_description.setVisibility(View.VISIBLE);
            dataView_description.setVisibility(View.VISIBLE);
            dataView_description.setText(item_description);
        }
        if(!"".equals(product_qty) && product_qty != null) {
            txt_product_qty.setVisibility(View.VISIBLE);
            dataView_product_qty.setVisibility(View.VISIBLE);
            dataView_product_qty.setText(product_qty);
        }
        if (!"".equals(product_cost_price_usdollar) && product_cost_price_usdollar != null) {
            txt_product_cost_price_usdollar.setVisibility(View.VISIBLE);
            dataView_product_cost_price_usdollar.setVisibility(View.VISIBLE);
            dataView_product_cost_price_usdollar.setText(product_cost_price_usdollar);
        }

        if (!"".equals(product_list_price_usdollar) && product_list_price_usdollar != null) {
            txt_product_list_price_usdollar.setVisibility(View.VISIBLE);
            dataView_product_list_price_usdollar.setVisibility(View.VISIBLE);
            dataView_product_list_price_usdollar.setText(product_list_price_usdollar);
        }
        if (!"".equals(product_discount_usdollar) && product_discount_usdollar != null) {
            txt_product_discount_usdollar.setVisibility(View.VISIBLE);
            dataView_product_discount_usdollar.setVisibility(View.VISIBLE);
            dataView_product_discount_usdollar.setText(product_discount_usdollar);
        }
        if (!"".equals(product_discount_amount_us) && product_discount_amount_us != null) {
            txt_product_discount_amount_us.setVisibility(View.VISIBLE);
            dataView_product_discount_amount_us.setVisibility(View.VISIBLE);
            dataView_product_discount_amount_us.setText(product_discount_amount_us);
        }
        if (!"".equals(discount) && discount != null) {
            txt_discount_amount.setVisibility(View.VISIBLE);
            dataView_discount_amount.setVisibility(View.VISIBLE);
            dataView_discount_amount.setText(discount);
        }
        if (!"".equals(vat_amt_usdollar) && vat_amt_usdollar != null) {
            txt_vat_amt_usdollar.setVisibility(View.VISIBLE);
            dataView_vat_amt_usdollar.setVisibility(View.VISIBLE);
            dataView_vat_amt_usdollar.setText(vat_amt_usdollar);
        }
        if (!"".equals(product_total_price_usdollar) && product_total_price_usdollar != null) {
            txt_product_total_price_usdollar.setVisibility(View.VISIBLE);
            dataView_product_total_price_usdollar.setVisibility(View.VISIBLE);
            dataView_product_total_price_usdollar.setText(product_total_price_usdollar);
        }
        if (!"".equals(date_closed) && date_closed != null) {
            txt_expectedCloseDate.setVisibility(View.VISIBLE);
            dataView_startDateAndTime_startDate_expectedCloseDate_publishDate_depart_billing_address.setVisibility(View.VISIBLE);
            dataView_startDateAndTime_startDate_expectedCloseDate_publishDate_depart_billing_address.setText(date_closed);
        }
        if (!"".equals(maincode) && maincode != null) {
            txt_maincode.setVisibility(View.VISIBLE);
            dataView_maincode.setVisibility(View.VISIBLE);
            dataView_maincode.setText(maincode);
        }
        if (!"".equals(category) && category != null) {
            txt_category.setVisibility(View.VISIBLE);
            dataView_category.setVisibility(View.VISIBLE);
            dataView_category.setText(category);
        }
        if (!"".equals(cost_usdollar) && cost_usdollar != null) {
            txt_cost_usdollar.setVisibility(View.VISIBLE);
            dataview_cost_usdollar.setVisibility(View.VISIBLE);
            dataview_cost_usdollar.setText(cost_usdollar);
        }
        if (!"".equals(price_usdollar) && price_usdollar != null) {
            txt_price_usdollar.setVisibility(View.VISIBLE);
            dataView_price_usdollar.setVisibility(View.VISIBLE);
            dataView_price_usdollar.setText(price_usdollar);
        }
        if (!"".equals(url) && url != null) {
            txt_url.setVisibility(View.VISIBLE);
            dataView_url.setVisibility(View.VISIBLE);
            dataView_url.setText(url);
        }
        if (!"".equals(contact) && contact != null) {
            txt_contact.setVisibility(View.VISIBLE);
            dataView_contact.setVisibility(View.VISIBLE);
            dataView_contact.setText(contact);
        }
        if (!"".equals(time_finish) && time_finish != null) {
            txt_time_finishr.setVisibility(View.VISIBLE);
            dataView_time_finishr.setVisibility(View.VISIBLE);
            dataView_time_finishr.setText(time_finish);
        }
        if (!"".equals(date_finish) && date_finish != null) {
            txt_date_finish.setVisibility(View.VISIBLE);
            dataView_date_finish.setVisibility(View.VISIBLE);
            dataView_date_finish.setText(date_finish);
        }
//=============================================================================
        if (!"".equals(date_due) && date_due != null) {
            txt_due_date.setVisibility(View.VISIBLE);
            dataView_endDate_return_due_date_expected_close_date.setVisibility(View.VISIBLE);
            dataView_endDate_return_due_date_expected_close_date.setText(date_due);
        }
        if (!"".equals(time_due) && time_due != null) {
            txt_due_time.setVisibility(View.VISIBLE);
            dataView_due_time.setVisibility(View.VISIBLE);
            dataView_due_time.setText(time_due);
        }
        if (!"".equals(priority) && priority != null) {
            txt_priority.setVisibility(View.VISIBLE);
            dataView_priority.setVisibility(View.VISIBLE);
            dataView_priority.setText(priority);
        }
        if (!"".equals(order_number) && order_number != null) {
            txt_order_number.setVisibility(View.VISIBLE);
            dataView_order_number.setVisibility(View.VISIBLE);
            dataView_order_number.setText(order_number);
        }

        if (!"".equals(estimated_start_date) && estimated_start_date != null) {
            txt_start_Date.setVisibility(View.VISIBLE);
            dataView_startDateAndTime_startDate_expectedCloseDate_publishDate_depart_billing_address.setVisibility(View.VISIBLE);
            dataView_startDateAndTime_startDate_expectedCloseDate_publishDate_depart_billing_address.setText(estimated_start_date);
        }
        if (!"".equals(estimated_end_date) && estimated_end_date != null) {
            txt_end_date.setVisibility(View.VISIBLE);
            dataView_endDate_return_due_date_expected_close_date.setVisibility(View.VISIBLE);
            dataView_endDate_return_due_date_expected_close_date.setText(estimated_end_date);
        }
        if (!"".equals(approval_status) && approval_status != null) {
            txt_approval_status.setVisibility(View.VISIBLE);
            dataView_approval_status.setVisibility(View.VISIBLE);
            dataView_approval_status.setText(approval_status);
        }
        if (!"".equals(expiration) && expiration != null) {
            txt_expiration_Date.setVisibility(View.VISIBLE);
            dataView_expiration_Date.setVisibility(View.VISIBLE);
            dataView_expiration_Date.setText(expiration);
        }
        if (!"".equals(shipping_amount_usdollar) && shipping_amount_usdollar != null) {
            txt_shipping_amount.setVisibility(View.VISIBLE);
            dataView_shipping_amount.setVisibility(View.VISIBLE);
            dataView_shipping_amount.setText(shipping_amount_usdollar);
        }
        if (!"".equals(shipping_tax_amt_usdollar) && shipping_tax_amt_usdollar != null) {
            txt_tax_shipping_amount.setVisibility(View.VISIBLE);
            dataView_tax_shipping_amount.setVisibility(View.VISIBLE);
            dataView_tax_shipping_amount.setText(shipping_tax_amt_usdollar);
        }
        if (!"".equals(invoice_status) && invoice_status != null) {
            txt_invoice_status.setVisibility(View.VISIBLE);
            dataView_website_status_invoiceStatus.setVisibility(View.VISIBLE);
            dataView_website_status_invoiceStatus.setText(invoice_status);
        }
        

        dialog.dismiss();


    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(module_name);

    }

}
