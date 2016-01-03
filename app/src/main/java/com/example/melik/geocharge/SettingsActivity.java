package com.example.melik.geocharge;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by Melik on 08/11/2015.
 */
public class SettingsActivity extends AppCompatActivity  {

    //Classe pour les Réglages

    private int ID_NOTIFICATION =1;
    private static final String PREF_FILE="fichier_preference";
    private Switch notif;
    private  BroadcastReceiver batteryChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(notif.isChecked())
                createNotification();
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Réglages");
        setContentView(R.layout.activity_settings);

        SharedPreferences settings = getSharedPreferences(PREF_FILE, 0);
        boolean notifictaion = settings.getBoolean("notification", false);

        notif = (Switch) findViewById(R.id.settings_notifications);
        notif.setChecked(notifictaion);
        notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//notif activé
                    Toast.makeText(getApplicationContext(), "Notification activé", Toast.LENGTH_SHORT).show();

                } else {//pas notif
                    Toast.makeText(getApplicationContext(), "Notification désactivé", Toast.LENGTH_SHORT).show();
                    deleteNotification();
                }
            }
        });
        this.registerReceiver(this.batteryChangeReceiver,
                new IntentFilter(Intent.ACTION_BATTERY_LOW));

        Button a_propos=(Button) findViewById(R.id.settings_about);
        a_propos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about=new Intent(SettingsActivity.this,A_propos.class);
                startActivity(about);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("notification", notif.isChecked());

        // Commit the edits!
        editor.apply();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private final void createNotification(){
        Intent notificationIntent=new Intent(this,MapsActivity.class); //quand on appui dessus
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification=new Notification.Builder(this) //crée la notification
                .setWhen(System.currentTimeMillis())
                .setTicker("Géocharge")
                .setContentTitle("GéoCharge Alerte")
                .setSmallIcon(R.drawable.ic_battery_alert_black_24dp)
                .setContentIntent(contentIntent)
                .setContentText("Votre batterie est faible, pensez à rechargez votre appareil à l'aide d'une borne près de vous !")
                .setStyle(new Notification.BigTextStyle().bigText("Votre batterie est faible, pensez à rechargez votre appareil à l'aide d'une borne près de vous !"))
                .build();

        notification.flags= Notification.FLAG_AUTO_CANCEL ;
        notification.defaults|= Notification.DEFAULT_SOUND;
        notification.defaults|= Notification.DEFAULT_LIGHTS;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(ID_NOTIFICATION, notification);
    }

    private void deleteNotification(){
        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //la suppression de la notification se fait grâce a son ID
        notificationManager.cancel(ID_NOTIFICATION);
    }




}