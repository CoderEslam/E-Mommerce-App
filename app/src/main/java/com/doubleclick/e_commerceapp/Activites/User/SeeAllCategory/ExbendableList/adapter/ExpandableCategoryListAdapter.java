package com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.doubleclick.e_commerceapp.Model.Category;
import com.doubleclick.e_commerceapp.R;

import java.util.List;
import java.util.Map;

public class ExpandableCategoryListAdapter extends BaseExpandableListAdapter {


    private Context mContext;
    private List<String> mExpandableListCategory;
    private Map<String, List<String>> mExpandableListDetail;
    private LayoutInflater mLayoutInflater;

    public ExpandableCategoryListAdapter(Context mContext, List<String> mExpandableListCategory, Map<String, List<String>> mExpandableListDetail) {
        this.mContext = mContext;
        this.mExpandableListCategory = mExpandableListCategory;
        this.mExpandableListDetail = mExpandableListDetail;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getGroupCount() {
        return mExpandableListCategory.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mExpandableListDetail
                .get(mExpandableListCategory.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mExpandableListCategory.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mExpandableListDetail
                .get(mExpandableListCategory.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String  ListCategory =  getGroup(groupPosition).toString();
        if (convertView==null){
            convertView = mLayoutInflater.inflate(R.layout.list_group,null);
        }
        TextView listTitleTextView = convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(ListCategory);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String expandedListText = getChild(groupPosition, childPosition).toString();
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView =  convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
