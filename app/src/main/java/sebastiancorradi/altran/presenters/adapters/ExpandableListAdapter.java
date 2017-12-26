package sebastiancorradi.altran.presenters.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private float minHeight;
    private float maxHeight;
    private float minWeight;
    private float maxWeight;


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
        ImageView ivGnome = (ImageView) convertView.findViewById(R.id.ivGnome);
        final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.pbGnomeImage);
        progressBar.setVisibility(View.VISIBLE);


        tvHeight.setText(String.format("%.2f", gnome.getHeight()));
        tvWeight.setText(String.format("%.2f", gnome.getWeight()));
        tvHairColor.setText(gnome.getHair_color());
        tvProfessions.setText(Utils.join(",  ", gnome.getProfessions()));
        tvFriends.setText(Utils.join(",  ", gnome.getFriends()));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.warning);
        Glide.with(_context)
                .setDefaultRequestOptions(requestOptions)
                .load(gnome.getThumbnail())
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(ivGnome);

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
        ivHair.setColorFilter(mapColors.get(gnome.getHair_color()));

        ImageView ivGnome = (ImageView) convertView.findViewById(R.id.ivGnome);
        float scaleX = getScaleX(gnome);
        float scaleY = getScaleY(gnome);
        ivGnome.setScaleX(scaleX);
        ivHair.setScaleX(scaleX);

        ivGnome.setScaleY(scaleY);
        ivHair.setScaleY(scaleY);
        //ivGnome.setScaleY(getScaleY(gnome));
        return convertView;
    }

    public float getScaleX(Gnome gnome){
        float ref = gnome.getWeight() - minWeight;
        float scale = (ref / (maxWeight - minWeight));
        scale = (scale * 0.5f) + 0.5f;


        return scale;
    }

    public float getScaleY(Gnome gnome){
        float ref = gnome.getHeight() - minHeight;
        float scale = (ref / (maxHeight - minHeight));
        scale = (scale * 0.5f) + 0.5f;

        return scale;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setEnvironmentValues(float maxHeight, float minHeight, float maxWeigh, float minWeight) {
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.maxWeight = maxWeigh;
        this.minWeight = minWeight;
    }

    public void sortByName(final boolean sortAsc){
        Collections.sort(_listDataHeader, new Comparator<Gnome>() {

            @Override
            public int compare(Gnome gnome, Gnome t1) {
                if (sortAsc) {
                    return gnome.getName().toUpperCase().compareTo(t1.getName().toUpperCase());
                }
                return t1.getName().toUpperCase().compareTo(gnome.getName().toUpperCase());
            }
        });

    }
}
