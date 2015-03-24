package com.androidcrm.wakensys.sugercrm.data_sync;

/**
 * Created by Wakensys on 3/24/2015.
 */
public class Account {

    int _id;
    String _user_id, _name, _assigned_user_name, _created_by_name, _date_entered, _date_modified, _deleted, _annual_revenue, _phone_fax, _billing_address_street,
            _billing_address_street_2, _billing_address_street_3, _billing_address_street_4, _billing_address_city, _billing_address_state, _billing_address_postalcode,
            _billing_address_country, _phone_office, _website, _employees, _ticker_symbol, _shipping_address_street, _shipping_address_street_2, _shipping_address_street_3,
            _shipping_address_street_4, _shipping_address_city, _shipping_address_state, _shipping_address_postalcode, _shipping_address_country, _parent_name,
            _date_start, _industry;
    public Account(){

    }
    public Account(int id, String user_id, String assigned_user_name, String created_by_name, String date_entered, String date_modified, String deleted, String annual_revenue, String phone_fax, String billing_address_street,
                   String billing_address_street_2, String billing_address_street_3, String billing_address_street_4, String billing_address_city, String billing_address_state, String billing_address_postalcode,
                   String billing_address_country, String phone_office, String website, String employees, String ticker_symbol, String shipping_address_street, String shipping_address_street_2, String shipping_address_street_3,
                   String shipping_address_street_4, String shipping_address_city, String shipping_address_state, String shipping_address_postalcode, String shipping_address_country, String parent_name,
                   String name, String industry){
        this._id = id;
        this._user_id = user_id;
        this._name = name;
        this._assigned_user_name = assigned_user_name;
        this._created_by_name = created_by_name;
        this._date_entered = date_entered;
        this._date_modified = date_modified;
        this._deleted = deleted;
        this._annual_revenue = annual_revenue;
        this._phone_fax = phone_fax;
        this._billing_address_street = billing_address_street;
        this._billing_address_street_2 = billing_address_street_2;
        this._billing_address_street_3 = billing_address_street_3;
        this._billing_address_street_4 = billing_address_street_4;
        this._billing_address_city = billing_address_city;
        this._billing_address_state = billing_address_state;
        this._billing_address_postalcode = billing_address_postalcode;
        this._billing_address_country = billing_address_country;
        this._phone_office = phone_office;
        this._website = website;
        this._employees = employees;
        this._ticker_symbol = ticker_symbol;
        this._shipping_address_street = shipping_address_street;
        this._shipping_address_street_2 = shipping_address_street_2;
        this._shipping_address_street_3 = shipping_address_street_3;
        this._shipping_address_street_4 = shipping_address_street_4;
        this._shipping_address_city = shipping_address_city;
        this._shipping_address_state = shipping_address_state;
        this._shipping_address_postalcode = shipping_address_postalcode;
        this._shipping_address_country = shipping_address_country;
        this._parent_name = parent_name;
        this._industry = industry;

    }

    public Account(String user_id,String name ,String assigned_user_name, String created_by_name, String date_entered, String date_modified, String deleted, String annual_revenue, String phone_fax, String billing_address_street,
                   String billing_address_street_2, String billing_address_street_3, String billing_address_street_4, String billing_address_city, String billing_address_state, String billing_address_postalcode,
                   String billing_address_country, String phone_office, String website, String employees, String ticker_symbol, String shipping_address_street, String shipping_address_street_2, String shipping_address_street_3,
                   String shipping_address_street_4, String shipping_address_city, String shipping_address_state, String shipping_address_postalcode, String shipping_address_country, String parent_name,
                  String industry){

        this._user_id = user_id;
        this._name = name;
        this._assigned_user_name = assigned_user_name;
        this._created_by_name = created_by_name;
        this._date_entered = date_entered;
        this._date_modified = date_modified;
        this._deleted = deleted;
        this._annual_revenue = annual_revenue;
        this._phone_fax = phone_fax;
        this._billing_address_street = billing_address_street;
        this._billing_address_street_2 = billing_address_street_2;
        this._billing_address_street_3 = billing_address_street_3;
        this._billing_address_street_4 = billing_address_street_4;
        this._billing_address_city = billing_address_city;
        this._billing_address_state = billing_address_state;
        this._billing_address_postalcode = billing_address_postalcode;
        this._billing_address_country = billing_address_country;
        this._phone_office = phone_office;
        this._website = website;
        this._employees = employees;
        this._ticker_symbol = ticker_symbol;
        this._shipping_address_street = shipping_address_street;
        this._shipping_address_street_2 = shipping_address_street_2;
        this._shipping_address_street_3 = shipping_address_street_3;
        this._shipping_address_street_4 = shipping_address_street_4;
        this._shipping_address_city = shipping_address_city;
        this._shipping_address_state = shipping_address_state;
        this._shipping_address_postalcode = shipping_address_postalcode;
        this._shipping_address_country = shipping_address_country;
        this._parent_name = parent_name;
        this._industry = industry;

    }



    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_user_id() {
        return _user_id;
    }

    public void set_user_id(String _user_id) {
        this._user_id = _user_id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_assigned_user_name() {
        return _assigned_user_name;
    }

    public void set_assigned_user_name(String _assigned_user_name) {
        this._assigned_user_name = _assigned_user_name;
    }

    public String get_created_by_name() {
        return _created_by_name;
    }

    public void set_created_by_name(String _created_by_name) {
        this._created_by_name = _created_by_name;
    }

    public String get_date_entered() {
        return _date_entered;
    }

    public void set_date_entered(String _date_entered) {
        this._date_entered = _date_entered;
    }

    public String get_date_modified() {
        return _date_modified;
    }

    public void set_date_modified(String _date_modified) {
        this._date_modified = _date_modified;
    }

    public String get_deleted() {
        return _deleted;
    }

    public void set_deleted(String _deleted) {
        this._deleted = _deleted;
    }

    public String get_annual_revenue() {
        return _annual_revenue;
    }

    public void set_annual_revenue(String _annual_revenue) {
        this._annual_revenue = _annual_revenue;
    }

    public String get_phone_fax() {
        return _phone_fax;
    }

    public void set_phone_fax(String _phone_fax) {
        this._phone_fax = _phone_fax;
    }

    public String get_billing_address_street() {
        return _billing_address_street;
    }

    public void set_billing_address_street(String _billing_address_street) {
        this._billing_address_street = _billing_address_street;
    }

    public String get_billing_address_street_2() {
        return _billing_address_street_2;
    }

    public void set_billing_address_street_2(String _billing_address_street_2) {
        this._billing_address_street_2 = _billing_address_street_2;
    }

    public String get_billing_address_street_3() {
        return _billing_address_street_3;
    }

    public void set_billing_address_street_3(String _billing_address_street_3) {
        this._billing_address_street_3 = _billing_address_street_3;
    }

    public String get_billing_address_street_4() {
        return _billing_address_street_4;
    }

    public void set_billing_address_street_4(String _billing_address_street_4) {
        this._billing_address_street_4 = _billing_address_street_4;
    }

    public String get_billing_address_city() {
        return _billing_address_city;
    }

    public void set_billing_address_city(String _billing_address_city) {
        this._billing_address_city = _billing_address_city;
    }

    public String get_billing_address_state() {
        return _billing_address_state;
    }

    public void set_billing_address_state(String _billing_address_state) {
        this._billing_address_state = _billing_address_state;
    }

    public String get_billing_address_postalcode() {
        return _billing_address_postalcode;
    }

    public void set_billing_address_postalcode(String _billing_address_postalcode) {
        this._billing_address_postalcode = _billing_address_postalcode;
    }

    public String get_billing_address_country() {
        return _billing_address_country;
    }

    public void set_billing_address_country(String _billing_address_country) {
        this._billing_address_country = _billing_address_country;
    }

    public String get_phone_office() {
        return _phone_office;
    }

    public void set_phone_office(String _phone_office) {
        this._phone_office = _phone_office;
    }

    public String get_website() {
        return _website;
    }

    public void set_website(String _website) {
        this._website = _website;
    }

    public String get_employees() {
        return _employees;
    }

    public void set_employees(String _employees) {
        this._employees = _employees;
    }

    public String get_ticker_symbol() {
        return _ticker_symbol;
    }

    public void set_ticker_symbol(String _ticker_symbol) {
        this._ticker_symbol = _ticker_symbol;
    }

    public String get_shipping_address_street() {
        return _shipping_address_street;
    }

    public void set_shipping_address_street(String _shipping_address_street) {
        this._shipping_address_street = _shipping_address_street;
    }

    public String get_shipping_address_street_2() {
        return _shipping_address_street_2;
    }

    public void set_shipping_address_street_2(String _shipping_address_street_2) {
        this._shipping_address_street_2 = _shipping_address_street_2;
    }

    public String get_shipping_address_street_3() {
        return _shipping_address_street_3;
    }

    public void set_shipping_address_street_3(String _shipping_address_street_3) {
        this._shipping_address_street_3 = _shipping_address_street_3;
    }

    public String get_shipping_address_street_4() {
        return _shipping_address_street_4;
    }

    public void set_shipping_address_street_4(String _shipping_address_street_4) {
        this._shipping_address_street_4 = _shipping_address_street_4;
    }

    public String get_shipping_address_city() {
        return _shipping_address_city;
    }

    public void set_shipping_address_city(String _shipping_address_city) {
        this._shipping_address_city = _shipping_address_city;
    }

    public String get_shipping_address_state() {
        return _shipping_address_state;
    }

    public void set_shipping_address_state(String _shipping_address_state) {
        this._shipping_address_state = _shipping_address_state;
    }

    public String get_shipping_address_postalcode() {
        return _shipping_address_postalcode;
    }

    public void set_shipping_address_postalcode(String _shipping_address_postalcode) {
        this._shipping_address_postalcode = _shipping_address_postalcode;
    }

    public String get_shipping_address_country() {
        return _shipping_address_country;
    }

    public void set_shipping_address_country(String _shipping_address_country) {
        this._shipping_address_country = _shipping_address_country;
    }

    public String get_parent_name() {
        return _parent_name;
    }

    public void set_parent_name(String _parent_name) {
        this._parent_name = _parent_name;
    }

    public String get_date_start() {
        return _date_start;
    }

    public void set_date_start(String _date_start) {
        this._date_start = _date_start;
    }

    public String get_industry() {
        return _industry;
    }

    public void set_industry(String _industry) {
        this._industry = _industry;
    }
}
