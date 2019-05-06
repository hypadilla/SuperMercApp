package com.softwo.supermercapp.Servicios;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.softwo.supermercapp.R;
import com.softwo.supermercapp.RecoleccionInformacionActivity;

public class ServicioFireBaseCloudMessage extends FirebaseMessagingService {
    public static int NOTIFICATION_ID = 1;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        generateNotification( remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle() );
    }

    private void generateNotification(String body, String title) {
        Intent intent = new Intent( this, RecoleccionInformacionActivity.class );
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );

        Context context;
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT );

        Uri sounduri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder( this )
                .setSmallIcon( R.mipmap.ic_launcher )
                .setContentTitle(title)
                .setContentText( body )
                .setAutoCancel( true )
                .setSound( sounduri )
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (NOTIFICATION_ID > 1073741824){
            NOTIFICATION_ID = 0;
        }

        notificationManager.notify( NOTIFICATION_ID++, notificationBuilder.build() );
    }
}
