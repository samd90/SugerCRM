package com.androidcrm.wakensys.sugercrm.data_sync;

/**
 * Created by Wakensys on 3/20/2015.
 */
public class Module {

    int _id;
    String _module_key = null;    // get module key
    String _module_label = null;  // get module label
    int _viewAccess ;      // get user can access the module

    // Empty constructor
    public Module(){
    }

    // Constructor
    public Module(int id, String module_key, String module_label, int viewAccess){
        this._id = id;
        this._module_key = module_key;
        this._module_label = module_label;
        this._viewAccess = viewAccess;

    }

    // Constructor
    public Module(String module_key, String module_label, int viewAccess){
        this._module_label = module_label;
        this._module_key = module_key;
        this._viewAccess = viewAccess;
    }

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting ID
    public void setID(int id){
        this._id = id;
    }

    // getting module key
    public String getModuleKey(){
        return this._module_key;
    }

    // setting module label
    public void setModuleKey(String moduleKey){
        this._module_key = moduleKey;
    }

    // getting module label
    public String get_module_label(){
        return this._module_label;
    }

    public void set_module_label(String module_label){
        this._module_label = module_label;
    }

    // getting View Access
    public int getViewAccess(){
        return this._viewAccess;
    }

    // setting View access
    public void setViewAccess(int viewAccess){
        this._viewAccess = viewAccess;
    }
}
