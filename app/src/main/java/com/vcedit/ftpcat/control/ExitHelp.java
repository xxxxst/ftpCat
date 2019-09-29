package com.vcedit.ftpcat.control;

import android.app.Activity;
import android.view.KeyEvent;

import com.vcedit.ftpcat.MainActivity;

/**
 * 双击退出
 */
public class ExitHelp {
	public Activity act = null;
	public OnPreExit onPreExit = null;
	public OnExit onExit = null;

	private long exitClickTime = 0;

	public boolean keydown(int keyCode){
		final int checkTime = 2000;

		//双击退出
		if(keyCode == KeyEvent.KEYCODE_BACK){
			long time = System.currentTimeMillis();
			long gap = time - exitClickTime;
			exitClickTime = time;

			if(gap > checkTime){
				MainActivity.toast("再按一次退出程序！");
				return true;
			}else{
				if(onPreExit != null){
					boolean isHandle = onPreExit.onPreExit();
					if(isHandle){
						return false;
					}
				}

				act.finish();

				if(onExit != null) {
					onExit.onExit();
				}
				//Stack<Activity> arr = MainActivity.arrActivity;
				//for(int i = arr.size() - 1; i >= 0; --i){
				//	arr.get(i).finish();
				//}
				//System.exit(0);
			}
		}

		return false;
	}

	public interface OnExit{
		void onExit();
	}

	public interface OnPreExit{
		boolean onPreExit();
	}
}
