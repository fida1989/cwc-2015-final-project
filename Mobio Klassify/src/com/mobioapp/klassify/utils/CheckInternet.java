package com.mobioapp.klassify.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.util.Log;

public class CheckInternet {
	 public static boolean isInternetOn(Context c) {

	        // get Connectivity Manager object to check connection
	        ConnectivityManager connec =
	                (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

	        // Check for network connections
	        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
	                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
	                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
	                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

	            // if connected with internet

	            //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
	            return true;

	        } else if (
	                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
	                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

	           // Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
	            return false;
	        }
	        return false;
	    }
	 
	 public static boolean isNetworkPresent(Context context) {
	        boolean isNetworkAvailable = false;
	        ConnectivityManager cm = (ConnectivityManager) context
	                .getSystemService(Context.CONNECTIVITY_SERVICE);
	        try {

	            if (cm != null) {
	                NetworkInfo netInfo = cm.getActiveNetworkInfo();
	                if (netInfo != null) {
	                    isNetworkAvailable = netInfo.isConnectedOrConnecting();
	                }
	            }
	        } catch (Exception ex) {
	            Log.e("Network Avail Error", ex.getMessage());
	        }
	        //check for wifi also
	        if(!isNetworkAvailable){
	            WifiManager connec = (WifiManager) context
	                    .getSystemService(Context.WIFI_SERVICE);
	            State wifi = cm.getNetworkInfo(1).getState();
	            if (connec.isWifiEnabled()
	                    && wifi.toString().equalsIgnoreCase("CONNECTED")) {
	                isNetworkAvailable = true;
	            } else {

	                isNetworkAvailable = false;
	            }

	        }
	        return isNetworkAvailable;
	    }
}
