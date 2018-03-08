package es.pymasde.SIS;

/**
 * Created by Tomer on 08-Mar-18.
 */

public class API_calls {
    private final static String DB_NAME ="sis";
    private static String COLLECTION_NAME = "Users";
    private final static  String API_KEY = "RrO6IxPO0tgU_yS7QSMdfVioiSTdlgCO";

           public static String getAddressSingle(String user){
                String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,COLLECTION_NAME);
                StringBuilder stringBuilder = new StringBuilder(baseUrl);
                //stringBuilder.append("/"+user.get_id().getOid()+"?apiKey="+API_KEY);
                return stringBuilder.toString();
            }

           public static String Get_Users(){
               String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,COLLECTION_NAME);
                StringBuilder stringBuilder = new StringBuilder(baseUrl);
                stringBuilder.append("?apiKey="+API_KEY);
                return stringBuilder.toString();
           }
}
