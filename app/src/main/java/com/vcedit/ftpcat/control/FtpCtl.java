package com.vcedit.ftpcat.control;

import android.util.Log;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.util.ArrayList;
import java.util.List;

public class FtpCtl {
	private int ftpPort = 9123;

	private FtpServerFactory ftpServerFactory = null;
	private ListenerFactory listenerFactory = null;
	private FtpServer ftpServer = null;

	public static void log(String info) {
		Log.i("<ftpCat>", info);
	}

	public void initFtpServer(String path, String strIp, int port) {
		try {
			if(ftpServer!=null){
				return;
			}

			ftpServerFactory = new FtpServerFactory();

			//ip & port
			listenerFactory = new ListenerFactory();
			listenerFactory.setServerAddress(strIp);
			listenerFactory.setPort(port);
			ftpServerFactory.addListener("default", listenerFactory.createListener());

			//user
			BaseUser user = new BaseUser();
			user.setName("anonymous");
			user.setPassword("anonymous");
			//root path
			user.setHomeDirectory(path);
			//permission
			List<Authority> authorities = new ArrayList<>();
			authorities.add(new WritePermission());
			user.setAuthorities(authorities);
			//
			ftpServerFactory.getUserManager().save(user);

			//start
			ftpServer = ftpServerFactory.createServer();
			ftpServer.start();
		} catch (Exception ex) {
			log(ex.toString());
		}
	}

	public void stop() {
		try{
			if (ftpServer != null) {
				ftpServer.stop();
				ftpServer = null;
			}
		} catch (Exception ex) {
			log(ex.toString());
		}
	}

}
