package sebastiancorradi.altran.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

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
        if (stringList != null) {
            for (int i = 0; i < stringList.size(); i++) {
                result = result + stringList.get(i);
                if (i < stringList.size() - 1) {
                    result += delimiter;
                }
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

    public AlertDialog showInformationDialog(Context context, String message, String title) {
        return new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                }).show();
    }
}
