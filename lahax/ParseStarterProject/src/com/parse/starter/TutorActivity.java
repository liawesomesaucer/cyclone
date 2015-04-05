package com.parse.starter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TutorActivity extends Activity {

    ExpandableListView expListView;
    ExpandableListAdapter expandableListAdapter;
    ArrayList<PlaceNum> listGroupTitles;
    HashMap<String, List<PlaceNum>> listDataMembers;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        // Get the expandable list
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        // Inflate header view
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.list_header, expListView, false);
        // Add header view to the expandable list
        expListView.addHeaderView(headerView);
        // Setting up list
        setUpExpList();
        expandableListAdapter = new ExpandableListAdapter(this, listGroupTitles, listDataMembers);
        // Setting list adapter
        expListView.setAdapter(expandableListAdapter);
        /*TextView secondary_subjects = (TextView) findViewById(R.id.txtdistrict);
        secondary_subjects.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //DO you work here
            }
        });*/
    }


    private void setUpExpList() {

        listGroupTitles = new ArrayList<PlaceNum>();
        listDataMembers = new HashMap<String, List<PlaceNum>>();
        // Adding province names and number of population as groups
        listGroupTitles.add(new PlaceNum("Math"));
        listGroupTitles.add(new PlaceNum("Science"));
        // Adding district names and number of population as children
        List<PlaceNum> p1Districts = new ArrayList<PlaceNum>();
        p1Districts.add(new PlaceNum("Algebra"));
        p1Districts.add(new PlaceNum("Geometry"));
        p1Districts.add(new PlaceNum("Calculus"));
        List<PlaceNum> p2Districts = new ArrayList<PlaceNum>();
        p2Districts.add(new PlaceNum("Chemistry"));
        p2Districts.add(new PlaceNum("Physics"));
        p2Districts.add(new PlaceNum("Biology"));
        listDataMembers.put(listGroupTitles.get(0).getPlace(), p1Districts);
        listDataMembers.put(listGroupTitles.get(1).getPlace(), p2Districts);

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, LocalTutorActivity.class);
        startActivity(intent);
    }
}