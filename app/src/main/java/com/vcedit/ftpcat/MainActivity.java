package com.vcedit.ftpcat;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.vcedit.ftpcat.control.ExitHelp;
import com.vcedit.ftpcat.control.FtpCtl;
import com.vcedit.ftpcat.view.BaseActivity;

public class MainActivity extends BaseActivity {
	private int ftpPort = 9123;
	private ExitHelp exitHelp = new ExitHelp();

	private TextView txtInfo = null;

	//private FtpServerFactory ftpServerFactory = null;
	//private ListenerFactory listenerFactory = null;
	//private FtpServer ftpServer = null;
	public static FtpCtl ftpCtl = new FtpCtl();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtInfo = findViewById(R.id.txtInfo);

		//log("create:" + App.actAount + "," + App.isForeground());

		permisssionCheck.checkPermission(new String[]{
			Manifest.permission.INTERNET,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.CHANGE_WIFI_STATE,
			Manifest.permission.ACCESS_WIFI_STATE,
			Manifest.permission.ACCESS_NETWORK_STATE,
			//Manifest.permission.RECORD_AUDIO,
		}, () -> {
			if (!permisssionCheck.isOk) {
				BaseActivity.toast(R.string.permissionDenied);
				txtInfo.setText(R.string.permissionDenied);
				return;
			}

			if(!isWifiOpened(getBaseContext())) {
				BaseActivity.toast(R.string.wifiNoOpen);
				txtInfo.setText(R.string.wifiNoOpen);
				return;
			}

			String strIp = getLocalIpAddress(getBaseContext());
			showIp(strIp);

			String sdPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
			sdPath = sdPath.replaceAll("[/\\\\]+", "/");

			ftpCtl.initFtpServer(sdPath, strIp, ftpPort);
			//LifeServer.run(this);
		});

	}

	private void showIp(String strIp) {
		//String strIp = getLocalIpAddress(getBaseContext());
		String str = "ftp://" + strIp + ":" + ftpPort;
		txtInfo.setText(str);
	}

	//private void initFtpServer(String path, String strIp, int port) {
	//	try {
	//		ftpServerFactory = new FtpServerFactory();
	//
	//		//ip & port
	//		listenerFactory = new ListenerFactory();
	//		listenerFactory.setServerAddress(strIp);
	//		listenerFactory.setPort(port);
	//		ftpServerFactory.addListener("default", listenerFactory.createListener());
	//
	//		//user
	//		BaseUser user = new BaseUser();
	//		user.setName("anonymous");
	//		user.setPassword("anonymous");
	//		//root path
	//		user.setHomeDirectory(path);
	//		//permission
	//		List<Authority> authorities = new ArrayList<>();
	//		authorities.add(new WritePermission());
	//		user.setAuthorities(authorities);
	//		//
	//		ftpServerFactory.getUserManager().save(user);
	//
	//		//start
	//		ftpServer = ftpServerFactory.createServer();
	//		ftpServer.start();
	//	} catch (Exception ex) {
	//		log(ex.toString());
	//	}
	//}

	//private void stop() {
	//	try{
	//		if (ftpServer != null) {
	//			ftpServer.stop();
	//			ftpServer = null;
	//		}
	//	} catch (Exception ex) {
	//		log(ex.toString());
	//	}
	//}

	@Override
	protected void onResume() {
		//log("onResume:" + App.actAount + "," + App.isForeground());
		//LifeServer.stop();

		super.onResume();
	}

	@Override
	protected void onPause() {
		//log("onPause:" + App.actAount + "," + App.isForeground());

		super.onPause();
	}

	@Override
	protected void onStop() {
		//log("onStop:" + App.actAount + "," + App.isForeground());

		super.onStop();
	}

	@Override
	protected void onDestroy() {
		//log("onDestroy:" + App.actAount + "," + App.isForeground());

		//LifeServer.stop();
		//stop();
		ftpCtl.stop();

		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean isHandle = false;

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//双击退出
			isHandle = isHandle || exitHelp.keydown(keyCode);
		}

		return isHandle || super.onKeyDown(keyCode, event);
	}

	//检查网络是否可用
	public static boolean isWifiOpened(Context context) {
		try {
			NetworkInfo localNetworkInfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
			if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable())) {
				return true;
			}
		}catch (Exception ex) { }
		return false;
	}

	//将ip的整数形式转换成ip形式
	public static String int2ip(int ipInt) {
		String rst = "";
		rst += (ipInt & 0xFF) + ".";
		rst += ((ipInt >> 8) & 0xFF) + ".";
		rst += ((ipInt >> 16) & 0xFF) + ".";
		rst += ((ipInt >> 24) & 0xFF);
		return  rst;
	}

	//获取当前ip地址
	public static String getLocalIpAddress(Context context) {
		try {
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			int ip = wifiInfo.getIpAddress();
			return int2ip(ip);
		} catch (Exception ex) { }
		return "";
	}
}
