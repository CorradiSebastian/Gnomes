package sebastiancorradi.altran.Utils;

import java.util.List;

/**
 * Created by Gregorio on 12/7/2017.
 */

public class Utils {
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
}
