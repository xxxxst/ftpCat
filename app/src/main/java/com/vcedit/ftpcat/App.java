package com.vcedit.ftpcat;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class App extends Application {
	public static int actAount = 0;
	//private boolean isForeground = true;

	public static boolean isForeground() {
		return actAount > 0;
	}

	ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
		@Override
		public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		}

		@Override
		public void onActivityStarted(Activity activity) {
			++actAount;
		}

		@Override
		public void onActivityResumed(Activity activity) {
		}
		@Override
		public void onActivityPaused(Activity activity) {
		}

		@Override
		public void onActivityStopped(Activity activity) {
			--actAount;
		}

		@Override
		public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
		}
		@Override
		public void onActivityDestroyed(Activity activity) {
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
	}

}
