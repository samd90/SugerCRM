package com.androidcrm.wakensys.sugercrm.AddNewEntry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.androidcrm.wakensys.sugercrm.R;

/**
 * Created by Sameera on 2/16/2015.
 */
public class AddNewItem_SelectMenu extends Fragment implements View.OnClickListener {
    private LinearLayout addAccount, addCall, addCases, addContact, addLead, addMeeting, addNewCampaign,addOpportunity, addNewTarget, addNewTargetList;
    private String sessionId, restUrl;

    public static final String TAG = AddNewItem_SelectMenu.class.getSimpleName();
    public static AddNewItem_SelectMenu newInstance(){
        return new AddNewItem_SelectMenu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_new_entry_select, container, false);
        addAccount = (LinearLayout) rootView.findViewById(R.id.addNewAccount);
        addCall = (LinearLayout) rootView.findViewById(R.id.addNewCall);
        addCases = (LinearLayout) rootView.findViewById(R.id.addNewCase);
        addContact = (LinearLayout) rootView.findViewById(R.id.addNewContact);
        addLead = (LinearLayout) rootView.findViewById(R.id.addNewLead);
        addMeeting = (LinearLayout) rootView.findViewById(R.id.addNewMeeting);
        addOpportunity = (LinearLayout) rootView.findViewById(R.id.addNewOpportunity);
        addNewTarget = (LinearLayout) rootView.findViewById(R.id.addNewTarget);
        addNewTargetList = (LinearLayout) rootView.findViewById(R.id.addNewTargetList);
        addNewCampaign = (LinearLayout) rootView.findViewById(R.id.addNewCampaign);

        //Set on click listeners to Linear layouts
        addAccount.setOnClickListener(this);
        addCall.setOnClickListener(this);
        addNewCampaign.setOnClickListener(this);
        addCases.setOnClickListener(this);
        addContact.setOnClickListener(this);
        addLead.setOnClickListener(this);
        addMeeting.setOnClickListener(this);
        addOpportunity.setOnClickListener(this);
        addNewTarget.setOnClickListener(this);
        addNewTargetList.setOnClickListener(this);

        Bundle b = getArguments();
        sessionId = b.getString("sessionId");
        restUrl = b.getString("restUrl");
        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNewAccount:
                Bundle b = new Bundle();
                b.putString("restUrl", restUrl);
                b.putString("sessionId", sessionId);
                b.putString("module_name","Accounts");
                AddNewAccount fragment = new AddNewAccount();
                fragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).commit();

                break;
            case R.id.addNewCall:
                Bundle b_1 = new Bundle();
                b_1.putString("restUrl", restUrl);
                b_1.putString("sessionId", sessionId);
                b_1.putString("module_name","Calls");
                AddNewCall fragment_1 = new AddNewCall();
                fragment_1.setArguments(b_1);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment_1, TAG).commit();

                break;
            case R.id.addNewCase:
                Bundle b_2 = new Bundle();
                b_2.putString("restUrl", restUrl);
                b_2.putString("sessionId", sessionId);
                b_2.putString("module_name","Cases");
                AddNewCases fragment_2 = new AddNewCases();
                fragment_2.setArguments(b_2);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment_2, TAG).commit();

                break;
            case R.id.addNewContact:
                Bundle b_3 = new Bundle();
                b_3.putString("restUrl", restUrl);
                b_3.putString("sessionId", sessionId);
                b_3.putString("module_name", "Contacts");
                AddNewLeadsAndContactsAndTarget fragment_3 = new AddNewLeadsAndContactsAndTarget();
                fragment_3.setArguments(b_3);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment_3, TAG).commit();

                break;
            case R.id.addNewLead:
                Bundle b_4 = new Bundle();
                b_4.putString("restUrl", restUrl);
                b_4.putString("sessionId", sessionId);
                b_4.putString("module_name", "Leads");
                AddNewLeadsAndContactsAndTarget fragment_4 = new AddNewLeadsAndContactsAndTarget();
                fragment_4.setArguments(b_4);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment_4, TAG).commit();

                break;
            case R.id.addNewMeeting:
                Bundle b_5 = new Bundle();
                b_5.putString("restUrl", restUrl);
                b_5.putString("sessionId", sessionId);
                b_5.putString("module_name","Meetings");
                AddNewMeeting fragment_5 = new AddNewMeeting();
                fragment_5.setArguments(b_5);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment_5, TAG).commit();

                break;
            case R.id.addNewOpportunity:
                Bundle b_6 = new Bundle();
                b_6.putString("restUrl", restUrl);
                b_6.putString("sessionId", sessionId);
                b_6.putString("module_name","Opportunities");
                AddNewOpportunity fragment_6 = new AddNewOpportunity();
                fragment_6.setArguments(b_6);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment_6, TAG).commit();

                break;
            case R.id.addNewCampaign:
                Bundle b_7 = new Bundle();
                b_7.putString("restUrl", restUrl);
                b_7.putString("sessionId", sessionId);
                b_7.putString("module_name","Campaigns");
                AddNewCampaign fragment_7 = new AddNewCampaign();
                fragment_7.setArguments(b_7);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment_7, TAG).commit();

                break;
            case R.id.addNewNote:
                Bundle b_8 = new Bundle();
                b_8.putString("restUrl", restUrl);
                b_8.putString("sessionId", sessionId);
                b_8.putString("module_name","Notes");
                AddNewOpportunity fragment_8 = new AddNewOpportunity();
                fragment_8.setArguments(b_8);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment_8, TAG).commit();

                break;
            case R.id.addNewTarget:
                Bundle b_9 = new Bundle();
                b_9.putString("restUrl", restUrl);
                b_9.putString("sessionId", sessionId);
                b_9.putString("module_name","Prospects");
                AddNewLeadsAndContactsAndTarget fragment_9 = new AddNewLeadsAndContactsAndTarget();
                fragment_9.setArguments(b_9);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment_9, TAG).commit();

                break;
            case R.id.addNewTargetList:
                Bundle b_10 = new Bundle();
                b_10.putString("restUrl", restUrl);
                b_10.putString("sessionId", sessionId);
                b_10.putString("module_name","ProspectLists");
                AddNewTargetList fragment_10 = new AddNewTargetList();
                fragment_10.setArguments(b_10);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment_10, TAG).commit();

                break;

            default:
                break;
        }

    }

}
