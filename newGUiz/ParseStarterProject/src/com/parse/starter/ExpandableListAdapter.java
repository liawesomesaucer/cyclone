package com.parse.starter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by weird_000 on 4/4/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {


    private Context _context;
    private List<PlaceNum> _listGroupTitle; // header titles
    private HashMap<String, List<PlaceNum>> _listDataMembers;

    public ExpandableListAdapter(Context context, List<PlaceNum> listGroupTitle,
                                 HashMap<String, List<PlaceNum>> listDataMembers) {
        this._context = context;
        this._listGroupTitle = listGroupTitle;
        this._listDataMembers = listDataMembers;
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataMembers.get(this._listGroupTitle.get(groupPosition).getPlace())
                .get(childPosititon);
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        PlaceNum memData = (PlaceNum) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_member, null);
        }

        TextView txtDisName = (TextView) convertView.findViewById(R.id.txtdistrict);
        txtDisName.setText(Html.fromHtml("<b>" + memData.getPlace() + "</b>"));
        TextView txtNum = (TextView) convertView.findViewById(R.id.txtdnum);

        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataMembers.get(this._listGroupTitle.get(groupPosition).getPlace()).size();
    }


    @Override
    public Object getGroup(int groupPosition) {
        return this._listGroupTitle.get(groupPosition);
    }


    @Override
    public int getGroupCount() {
        return this._listGroupTitle.size();
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        PlaceNum gData = (PlaceNum) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);

        }

        TextView txtProName = (TextView) convertView.findViewById(R.id.txtprovince);
        txtProName.setText(Html.fromHtml("<b>" + gData.getPlace() + "</b>"));
        TextView txtNum = (TextView) convertView.findViewById(R.id.txtpnum);

        return convertView;
    }


    @Override

    public boolean hasStableIds() {

        return false;

    }


    @Override

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}