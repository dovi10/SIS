package es.pymasde.SIS;

import es.pymasde.SIS.user_data.User;

/**
 * Created by Tomer on 08-Mar-18.
 */

public class Mongo_Auth {
    private final static String DB_NAME ="sis";
    private static String collection_name = "Users";
    private final static  String API_KEY = "RrO6IxPO0tgU_yS7QSMdfVioiSTdlgCO";

    /**
     * set collection for api calls
     * @param collection_name - collection name
     */
    public static void setCollection_name(String collection_name) {
        Mongo_Auth.collection_name = collection_name;
    }

    /**
     * to get the user the collection name must be set to Users
     * the collection is by default Users
     * url for GET functions
     * @param user - user to be retrived
     * @return
     */
    public static String get_singel_user(User user){
                String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME, collection_name);
                StringBuilder stringBuilder = new StringBuilder(baseUrl);
                stringBuilder.append("/"+user.getOid().getOid()+"?apiKey="+API_KEY);
                return stringBuilder.toString();
            }

    /**
     *url for GET functions
     * @return return api url to get all users
     */
           public static String Get_Users(){
               String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME, collection_name);
                StringBuilder stringBuilder = new StringBuilder(baseUrl);
                stringBuilder.append("?apiKey="+API_KEY);
                return stringBuilder.toString();
           }
}
