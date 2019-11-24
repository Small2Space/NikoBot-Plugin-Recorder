package com.github.smallru8.plugin.recorder;

import com.github.smallru8.NikoBot.plugins.PluginsInterface;
import com.github.smallru8.NikoBot.Core;


public class Recorder implements PluginsInterface{

	public void onDisable() {
		// TODO Auto-generated method stub
		
	}

	public void onEnable() {
		// TODO Auto-generated method stub
		Core.botAPI.addEventListener(new Listener());
		
	}
	
	public String pluginsName() {
		// TODO Auto-generated method stub
		return "Recorder";
	}
	
}
