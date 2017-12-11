package sebastiancorradi.altran.Utils;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sebastiancorradi.altran.R;

/**
 * Created by Gregorio on 12/7/2017.
 */

public class Utils {
    private static Utils instance;
    private String Token;
    private Utils(){

    }

    public static Utils getInstance(){
        if (instance == null){
            instance = new Utils();
        }
        return instance;
    }

    public static String join(String delimiter, List<String> stringList){
        String result = "";
        for (int i = 0; i < stringList.size(); i++){
            result = result + stringList.get(i);
            if (i < stringList.size() -1){
                result += delimiter;
            }
        }
        return result;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public Map<String, Integer> getMapColor(Context context){
        Map<String, Integer> result = new HashMap<String, Integer>();
        result.put(context.getResources().getString(R.string.color_black), context.getResources().getColor(R.color.colorBlack));
        result.put(context.getResources().getString(R.string.color_red), context.getResources().getColor(R.color.colorRed));
        result.put(context.getResources().getString(R.string.color_gray), context.getResources().getColor(R.color.colorGray));
        result.put(context.getResources().getString(R.string.color_green), context.getResources().getColor(R.color.colorGreen));
        result.put(context.getResources().getString(R.string.color_pink), context.getResources().getColor(R.color.colorPink));

        return result;
    }
}
