package com.github.smallru8.plugin.recorder;

import com.github.smallru8.NikoBot.plugins.PluginsInterface;


import com.github.smallru8.NikoBot.Core;


public class Recorder implements PluginsInterface{

	private String pluginName = "Recorder(Backgroung plugin)";
	public static SaveGuildMessage SGM = new SaveGuildMessage();
	public static SavePrivateMessage SPM = new SavePrivateMessage();
	
	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
		Core.botAPI.addEventListener(new Listener());
	}
	
	@Override
	public String pluginsName() {
		return pluginName;
	}
	
}
