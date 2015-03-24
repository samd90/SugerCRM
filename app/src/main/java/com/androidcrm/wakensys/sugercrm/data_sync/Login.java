package com.androidcrm.wakensys.sugercrm.data_sync;

/**
 * Created by Wakensys on 3/20/2015.
 */
public class Login {

    int _id;
    String _user_id;
    String _user_name;

    public Login(){

    }

    // Constructor
    public Login(int id, String user_id, String user_name){
        this._id = id;
        this._user_id = user_id;
        this._user_name = user_name;

    }

    public Login(String user_id, String user_name){
        this._user_id = user_id;
        this._user_name = user_name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public String get_user_name() {
        return _user_name;
    }

    public void set_user_name(String user_name) {
        this._user_name = user_name;
    }

    public String get_user_id() {
        return _user_id;
    }

    public void set_user_id(String _user_id) {
        this._user_id = _user_id;
    }
}
