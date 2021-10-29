package com.github.smallru8.plugin.recorder;

import com.github.smallru8.NikoBot.plugins.PluginsInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.github.smallru8.NikoBot.Core;


public class Recorder implements PluginsInterface{

	private String pluginName = "Recorder(Backgroung plugin)";
	private Properties pro;
	private boolean useGithub = false;
	private String PAT = "PersonalAccessToken";
	private String repo = "name/repo";
	
	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
		setProperties();
		
		
		
		
		Core.botAPI.addEventListener(new Listener());
		
	}
	
	@Override
	public String pluginsName() {
		return pluginName;
	}
	
	
	private void setProperties() {
		pro = new Properties();
		File f = new File("conf.d/recorder.conf");
		if(!f.exists()) {
			pro.setProperty("Github", "false");
			pro.setProperty("PAT", PAT);
			pro.setProperty("Repo", repo);
			try {
				pro.store(new FileOutputStream(f), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			pro.clear();
		}else {
			try {
				pro.load(new FileInputStream(f));
				useGithub = pro.getProperty("Github", "false").equalsIgnoreCase("true");
				PAT = pro.getProperty("PAT", "pat");
				repo = pro.getProperty("Repo", "name/repo");
				pro.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
}
