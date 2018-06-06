package es.pymasde.SIS.user_data;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Measurements {
    private String Time;
    private long UserId ;
    private String UserName;


    private double Humidityval ;
    private double HumidityPer ;
    private double IdrVal ;
    private double WaterVal ;
    private double BuzzerVal;

    public Measurements(String time, long userId, String userName, double hudimityVal, double hudimityPrecent, double idrVal, double waterVal, double buzzerVal) {
        Time = time;
        UserId = userId;
        UserName = userName;
        Humidityval = hudimityVal;
        HumidityPer = hudimityPrecent;
        IdrVal = idrVal;
        WaterVal = waterVal;
        BuzzerVal = buzzerVal;
    }

    public Measurements() throws ParseException {
       this.UserId = 400;
       this.UserName = "wasnt Initialized";
       Calendar calendar = GregorianCalendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        fmt.setCalendar(calendar);
        String dateFormatted = fmt.format(calendar.getTime());
       this.Time = dateFormatted;
       this.Humidityval = 0;
       this.HumidityPer = 0;
       this.IdrVal = 0;
       this.WaterVal = 0;
       this. BuzzerVal = 0;
    }
    public JSONObject ExtractJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("Time",getTime());
        obj.put("UserId",getUserId());
        obj.put("UserName",getUserName());
        obj.put("HudimityVal",getHudimityVal());
        obj.put("HudimityPrecent",getHumidityPer());
        obj.put("IdrVal",getIdrVal());
        obj.put("WaterVal",getWaterVal());
        obj.put("BuzzerVal",getBuzzerVal());
        return obj;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public double getHudimityVal() {
        return Humidityval;
    }

    public void setHudimityVal(double hudimityVal) {
        Humidityval = hudimityVal;
    }

    public double getHumidityPer() {
        return HumidityPer;
    }

    public void setHumidityPer(double hudimityPrecent) {
        HumidityPer = hudimityPrecent;
    }

    public double getIdrVal() {
        return IdrVal;
    }

    public void setIdrVal(double idrVal) {
        IdrVal = idrVal;
    }

    public double getWaterVal() {
        return WaterVal;
    }

    public void setWaterVal(double waterVal) {
        WaterVal = waterVal;
    }

    public double getBuzzerVal() {
        return BuzzerVal;
    }

    public void setBuzzerVal(double buzzerVal) {
        BuzzerVal = buzzerVal;
    }
}
