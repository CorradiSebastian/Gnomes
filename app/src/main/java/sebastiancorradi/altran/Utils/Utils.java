package sebastiancorradi.altran.Utils;

import java.util.List;

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
}
