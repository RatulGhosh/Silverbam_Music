package com.example.sdread;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ReceiveBroadcast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle b=intent.getExtras();
		String song=b.getString("songn");
		String path=b.getString("pathn");
		String msg="Song Name : "+song;
		
		Intent i=new Intent(context,MetaData.class);
		i.putExtra("path",path );
		i.putExtra("song", song);
		PendingIntent pi=PendingIntent.getActivity(context, 0, i, 0);
		
		//building a notification
		Notification.Builder nb=new Notification.Builder(context);
		nb.setContentTitle("Music");
		nb.setContentText("Now Playing : "+song);
		nb.setSmallIcon(android.R.drawable.ic_media_play);
		nb.setContentIntent(pi);
		
		//launching notification
		Notification noti=nb.build();
		
		//hide the notification after its selected
		//noti.flags |=Notification.FLAG_AUTO_CANCEL;
		
		String nsn=Context.NOTIFICATION_SERVICE;
		NotificationManager nm=(NotificationManager)context.getSystemService(nsn);
		nm.notify(0, noti);
	}

}
