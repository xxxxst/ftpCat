package sdk.androidHelp.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

//Android 6.0+ 权限检查
public class PermissionCheck {
	private Activity win = null;
	public boolean isOk = false;
	private PermissionCheckCallback callback = null;

	@FunctionalInterface
	public interface PermissionCheckCallback {
		void callBack();
	}

	public PermissionCheck(Activity _win) {
		this.win = _win;
	}

	public void checkPermission(String permissionName, PermissionCheckCallback _callback) {
		isOk = false;
		callback = _callback;

		if (ContextCompat.checkSelfPermission(win, permissionName) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(win, new String[]{permissionName}, 3);
		} else {
			isOk = true;
			if (callback != null) {
				callback.callBack();
			}
		}
	}

	public void checkPermission(String[] arrPermissionName, PermissionCheckCallback _callback) {
		isOk = false;
		callback = _callback;

		ArrayList<String> arr = new ArrayList<>();
		for (int i = 0; i < arrPermissionName.length; ++i) {
			if (ContextCompat.checkSelfPermission(win, arrPermissionName[i]) != PackageManager.PERMISSION_GRANTED) {
				arr.add(arrPermissionName[i]);
			}
		}

		if (arr.size() > 0) {
			ActivityCompat.requestPermissions(win, arr.toArray(new String[0]), 3);
		} else {
			isOk = true;
			if (callback != null) {
				callback.callBack();
			}
		}
	}

	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (3 == requestCode) {
			//Log.i("[poicollect]", "" + grantResults.length);

			isOk = true;
			for (int i = 0; i < grantResults.length; ++i) {
				if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
					isOk = false;
				}
			}
		}

		if (callback != null) {
			callback.callBack();
		}
	}

}
