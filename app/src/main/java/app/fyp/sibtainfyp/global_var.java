package app.fyp.sibtainfyp;

import android.app.Application;

public class global_var extends Application {



    public static String latitude;
    public static String longitude;

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String lat) {
            latitude = lat;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String lon) {
            longitude = lon;
        }

}
