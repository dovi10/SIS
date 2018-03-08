package es.pymasde.SIS.user_data;

/**
 * Created by Tomer on 08-Mar-18.
 */

public class User {
    private Id _id;
    private String name;
    private int Password;

    public Id getOid() {
        return _id;
    }

    public void setOid(Id oid) {
        this._id = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassword() {
        return Password;
    }

    public void setPassword(int password) {
        Password = password;
    }
}
