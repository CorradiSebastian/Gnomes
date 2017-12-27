package sebastiancorradi.altran.presenters.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Map;

import sebastiancorradi.altran.R;
import sebastiancorradi.altran.Utils.Utils;
import sebastiancorradi.altran.model.Gnome;

/**
 * Created by Gregorio on 12/27/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter
        <GnomeViewHolder> {
    private ArrayList<Gnome> gnomes;
    private Map<String, Integer> mapColors;
    private float minHeight;
    private float maxHeight;
    private float minWeight;
    private float maxWeight;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<Gnome> gnomes){
        super();
        if (gnomes == null) {
            throw new IllegalArgumentException(
                    "modelData must not be null");
        }
        this.gnomes = gnomes;
        this.context = context;
        mapColors = Utils.getInstance().getMapColor(context);

    }
    @Override
    public GnomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.list_group,
                        parent,
                        false);
        return new GnomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GnomeViewHolder holder, int position) {
        final Gnome gnome = gnomes.get(position);
        holder.setHeader(gnome.getName());
        String headerSubTitle = holder.getContext().getResources().getString(R.string.label_age) + gnome.getAge();
        holder.setSubHeader(headerSubTitle);

        ImageView ivGnome = holder.getIvGnome();
        ImageView ivHair = holder.getIvHair();

        float scaleX = getScaleX(gnome);
        float scaleY = getScaleY(gnome);
        ivGnome.setScaleX(scaleX);
        ivHair.setScaleX(scaleX);

        ivGnome.setScaleY(scaleY);
        ivHair.setScaleY(scaleY);

        ivHair.setColorFilter(mapColors.get(gnome.getHair_color()));

        holder.getView().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //select(paymentMethod);
                Log.d("ReciclerViewAdapter", "Click");
            }
        });
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
    public int getItemCount() {
        return gnomes.size();
    }

    public void setEnvironmentValues(float maxHeight, float minHeight, float maxWeigh, float minWeight) {
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.maxWeight = maxWeigh;
        this.minWeight = minWeight;
    }

}
