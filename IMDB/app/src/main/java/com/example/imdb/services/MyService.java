package com.example.imdb.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.imdb.MainActivity;
import com.example.imdb.R;
import com.example.imdb.model.User;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Myservice","onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle b= intent.getExtras();
        User user= (User)b.get("user");
        sendNotification(user);

        return START_NOT_STICKY;

    }

    private void sendNotification(User user) {
       // Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        Intent intent= new Intent(this, MainActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        String head_noti="Tạo thành công tài khoản ";
        SimpleDateFormat sdf= new SimpleDateFormat("hh:mm:ss");
        String time= sdf.format(new Date());
        RemoteViews remoteViews= new RemoteViews(getPackageName(),R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.tvNotiRegistrySuccess,head_noti+ user.getUsername());
        remoteViews.setTextViewText(R.id.tvTimeNoti,time);


        Notification notification= new NotificationCompat.Builder(this,MyApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_star_24)
                .setCustomContentView(remoteViews)
                .setContentIntent(pendingIntent)


//                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
//                .setLargeIcon(bitmap)
                //.setColor(getResources().getColor(R.color.design_default_color_surface))
                .build();
//        NotificationManagerCompat managerCompat= NotificationManagerCompat.from(this);
//        managerCompat.notify(getNotiID(),notification);

        startForeground(1,notification);
    }

    private int getNotiID() {
        return (int) new Date().getTime();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Myservice","onDestroy");
    }
}
