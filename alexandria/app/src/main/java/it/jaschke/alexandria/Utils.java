package it.jaschke.alexandria;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mrasulava on 12/4/2015.
 */
public class Utils {

    public static boolean haveInternetConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
