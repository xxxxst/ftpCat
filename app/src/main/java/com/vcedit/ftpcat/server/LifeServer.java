package com.vcedit.ftpcat.server;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.vcedit.ftpcat.R;

public class LifeServer extends Service {

	public static final String CHAN_ID = "com.vcedit.ftpCat.LifeServer";
	public static final String CHAN_NAME = "com.vcedit.ftpCat";
	private int notifyId = 0;

	private static Context ctx = null;
	private static Intent intent = null;

	public static void log(String info) {
		Log.i("<ftpCat>", info);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		log("aaa,onCreate");

		notifyId = (int)System.currentTimeMillis();

		Notification notification = createNotify();
		startForeground(notifyId, notification);
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		log("aaa,onBind");
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		log("aaa,onStartCommand");
		//return super.onStartCommand(intent, flags, startId);
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		log("aaa,onDestroy");

		stopForeground(true);
		super.onDestroy();
	}

	private Notification createNotify() {
		NotificationManager notifyManage = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// 消息通道
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel notifyChan = new NotificationChannel(CHAN_ID, CHAN_NAME, NotificationManager.IMPORTANCE_HIGH);
			notifyChan.setDescription("Channel description");
			//LED灯
			notifyChan.enableLights(true);
			notifyChan.setLightColor(Color.RED);
			//震动
			notifyChan.setVibrationPattern(new long[]{0, 1000, 500, 1000});
			notifyChan.enableVibration(true);

			if (notifyManage != null) {
				notifyManage.createNotificationChannel(notifyChan);
			}
		}

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHAN_ID);
		builder.setSmallIcon(R.mipmap.ic_launcher_round);
		builder.setContentTitle(getResources().getString(R.string.app_name));
		builder.setContentText("aaa");
		builder.setWhen(System.currentTimeMillis());
		//设定启动的内容
		//Intent activityIntent = new Intent(this, NotificationActivity.class);
		//PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		//builder.setContentIntent(pendingIntent);

		//创建通知并返回
		return builder.build();
	}

	public static void run(Context _ctx) {
		if(intent != null){
			return;
		}
		ctx = _ctx;
		intent = new Intent(ctx, LifeServer.class);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			ctx.startForegroundService(intent);
		} else {
			ctx.startService(intent);
		}
		log("ccc:" + (intent != null) + "," + Build.VERSION.SDK_INT);
	}

	public static void stop() {
		if(intent == null){
			return;
		}
		ctx.stopService(intent);
		ctx = null;
		intent = null;
	}

}
