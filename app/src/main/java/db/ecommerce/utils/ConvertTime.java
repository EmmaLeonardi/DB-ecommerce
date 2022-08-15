package db.ecommerce.utils;

import java.util.Date;

public class ConvertTime {

    @SuppressWarnings("deprecation")
    public static Date convertIntoTime(Date d, double t) {
        if (d != null) {
            int h = Math.toIntExact(Math.round(Math.floor(t)));
            int m = Math.toIntExact(Math.round(Math.floor((t - h * 1.0) * 100)));
            d.setHours(h);
            d.setMinutes(m);
            return d;
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public static double convertIntoDouble(Date d) {
        if (d != null) {
            return d.getHours() + d.getMinutes() * 0.01;
        }
        return 0;
    }

}
