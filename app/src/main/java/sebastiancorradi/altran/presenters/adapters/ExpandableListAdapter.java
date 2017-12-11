package sebastiancorradi.altran.presenters.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sebastiancorradi.altran.R;
import sebastiancorradi.altran.Utils.Utils;
import sebastiancorradi.altran.model.Gnome;

/**
 * Created by Gregorio on 12/4/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<Gnome> _listDataHeader; // header titles
    private Map<String, Integer> mapColors;

    public ExpandableListAdapter(Context context, ArrayList<Gnome> listDataHeader) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        mapColors = Utils.getInstance().getMapColor(context);
    }

    public void setList(ArrayList<Gnome> newGnomesList){
        this._listDataHeader = newGnomesList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        //con el name del group busco en el hashmap de childs
        Gnome gnome = _listDataHeader.get(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView tvHeight = (TextView) convertView.findViewById(R.id.tvHeight);
        TextView tvWeight = (TextView) convertView.findViewById(R.id.tvWeight);
        TextView tvHairColor = (TextView) convertView.findViewById(R.id.tvHairColor);
        TextView tvProfessions = (TextView) convertView.findViewById(R.id.tvProfessions);
        TextView tvFriends = (TextView) convertView.findViewById(R.id.tvFriends);


        tvHeight.setText(String.format("%.2f", gnome.getHeight()));
        tvWeight.setText(String.format("%.2f", gnome.getWeight()));
        tvHairColor.setText(gnome.getHair_color());
        tvProfessions.setText(Utils.join(",  ", gnome.getProfessions()));
        tvFriends.setText(Utils.join(",  ", gnome.getFriends()));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;//en cada row muestro 1 solo hijo
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Gnome gnome = (Gnome) getGroup(groupPosition);
        String headerTitle = gnome.getName();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }
        String headerSubTitle = convertView.getResources().getString(R.string.label_age) + String.valueOf(((Gnome) getGroup(groupPosition)).getAge());

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setText(headerTitle);

        TextView lblListSubHeader = (TextView) convertView
                .findViewById(R.id.lblListSubHeader);
        lblListSubHeader.setText(headerSubTitle);

        ImageView ivHair = (ImageView) convertView.findViewById(R.id.ivHairColor);
        try {
            ivHair.setColorFilter(mapColors.get(gnome.getHair_color()));
        }
        catch (Exception e){
            Log.d("ExpAdapter", "map: " + mapColors);
            Log.d("ExpAdapter", "gnome.getHair_color(): " + gnome.getHair_color());
            Log.d("ExpAdapter", "mapColors.get(gnome.getHair_color()): " + mapColors.get(gnome.getHair_color()));

        }
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
