package com.example.dynamicplanner.Calendar;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dynamicplanner.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            String event=intent.getStringExtra("event");
            String time=intent.getStringExtra("time");
            int notId=intent.getIntExtra("id",0);
            Intent activityIntent=new Intent(context, MainActivity.class);
            PendingIntent pendingIntent= PendingIntent.getActivity(context,0,activityIntent, PendingIntent.FLAG_ONE_SHOT);

            String channelId="Channel_Id";
            CharSequence name= "Channel_name";
            String description="description";
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel=new NotificationChannel(channelId,name, NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription(description);
                NotificationManager notificationManager=context.getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
        }

            Notification notification=new NotificationCompat.Builder(context,channelId)
                    .setSmallIcon(R.mipmap.timeicon)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentTitle(event)
                    .setContentText(time)
                    .setDeleteIntent(pendingIntent)
                    .setGroup("Group_Calendar_view")
                    .build();
        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notId,notification);


    }
}
