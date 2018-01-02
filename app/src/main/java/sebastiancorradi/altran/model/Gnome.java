package sebastiancorradi.altran.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gregorio on 12/3/2017.
 */

public class Gnome implements Parcelable {

    int id;

    @SerializedName("name")
    String name;

    @SerializedName("thumbnail")
    String thumbnail;

    @SerializedName("age")
    int age;

    @SerializedName("weight")
    float weight;

    @SerializedName("height")
    float height;

    @SerializedName("hair_color")
    String hair_color;

    @SerializedName("professions")
    List<String> professions;

    @SerializedName("friends")
    List<String> friends;

    boolean visible = true;
    private String defaultHairColor = "Pink";

    public static final Creator<Gnome> CREATOR = new Creator<Gnome>() {
        @Override
        public Gnome createFromParcel(Parcel in) {
            return new Gnome(in);
        }

        @Override
        public Gnome[] newArray(int size) {
            return new Gnome[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.thumbnail);
        parcel.writeInt(this.age);
        parcel.writeFloat(this.weight);
        parcel.writeFloat(this.height);
        parcel.writeString(this.hair_color);
        parcel.writeStringList(this.professions);
        parcel.writeStringList(this.friends);
    }

    public Gnome(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.thumbnail =  in.readString();
        this.age = in.readInt();
        this.weight = in.readFloat();
        this.height = in.readFloat();
        this.hair_color = in.readString();
        this.professions = new ArrayList<String>();
        in.readStringList(this.professions);
        this.friends = new ArrayList<String>();
        in.readStringList(this.friends);
        //in.readStringList(this.professions);
        //in.readStringList(this.friends);
    }

    public Gnome(){
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getHair_color() {
        return hair_color;
    }

    public void setHair_color(String hair_color) {

        this.hair_color = hair_color.equals("")? defaultHairColor : hair_color;
    }

    public List<String> getProfessions() {
        return professions;
    }

    public void setProfessions(List<String> professions) {
        this.professions = professions;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}