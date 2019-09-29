package com.vcedit.ftpcat.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import sdk.androidHelp.util.PermissionCheck;

public class BaseActivity extends AppCompatActivity {
	//public static Stack<Activity> arrActivity = new Stack<>();
	public static BaseActivity nowActivity = null;

	protected PermissionCheck permisssionCheck = new PermissionCheck(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		nowActivity = this;
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		nowActivity = this;
	}

	public static void toast(int rId) {
		toast(rId, Toast.LENGTH_SHORT);
	}

	public static void toast(int rId, int type) {
		toast(BaseActivity.nowActivity.getResources().getString(rId), type);
	}

	public static void toast(String info) {
		toast(info, Toast.LENGTH_SHORT);
	}

	public static void toast(String info, int type) {
		Activity ins = BaseActivity.nowActivity;
		Toast.makeText(ins, info, type).show();
	}

	public static void log(String info) {
		Log.i("<ftpCat>", info);
	}

	public static void err(Exception ex) {
		Log.i("<ftpCat>", ex.toString());
		ex.printStackTrace();
	}

	public static void err(String info, Exception ex) {
		Log.i("<ftpCat>", info + " " + ex.toString());
		ex.printStackTrace();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		permisssionCheck.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
}
