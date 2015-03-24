package com.androidcrm.wakensys.sugercrm.data_sync;

/**
 * Created by Wakensys on 3/20/2015.
 */
public class AllRecords {

    int _id;
    String _name;
    String _module_name;
    String _bug_number;

    public AllRecords(){

    }

    // Constructor
    public AllRecords(int id, String name, String module_name){
        this._id = id;
        this._name = name;
        this._module_name = module_name;

    }

    public AllRecords(String name, String module_name){
        this._name = name;
        this._module_name = module_name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int id) {
        this._id = id;
    }



    public String get_module_name() {
        return _module_name;
    }

    public void set_module_name(String module_name) {
        this._module_name = module_name;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String name) {
        this._name = name;
    }
}
