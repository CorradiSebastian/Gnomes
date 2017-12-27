package sebastiancorradi.altran.presenters.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sebastiancorradi.altran.R;

/**
 * Created by Gregorio on 12/27/2017.
 */

public class GnomeViewHolder  extends RecyclerView.ViewHolder{


    private View rootView ;

    TextView lblListHeader;
    TextView lblListSubHeader;
    ImageView ivHair;
    ImageView ivGnome;

    public GnomeViewHolder(View convertView) {
        super(convertView);

        this.rootView = convertView;

        lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListSubHeader = (TextView) convertView.findViewById(R.id.lblListSubHeader);
        ivHair = (ImageView) convertView.findViewById(R.id.ivHairColor);
        ivGnome = (ImageView) convertView.findViewById(R.id.ivGnome);
    }

    public void setHeader(String text){
        lblListHeader.setText(text);
    }

    public void setSubHeader(String text){
        lblListSubHeader.setText(text);
    }

    public void setHairImage(ImageView view){
        this.ivHair = view;
    }

    public void setIvGnome(ImageView view){
        this.ivGnome = view;
    }

    public Context getContext(){
        return this.rootView.getContext();
    }

    public ImageView getIvHair() {
        return ivHair;
    }

    public ImageView getIvGnome() {
        return ivGnome;
    }

    public View getView(){
        return this.rootView;
    }
}
