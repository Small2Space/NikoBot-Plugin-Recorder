package com.github.smallru8.plugin.recorder;

import net.dv8tion.jda.api.entities.Message;

public class SaveGuildMessage extends SaveMessage{

	public SaveGuildMessage() {
		super("servers/");
	}

	public void save(Message msg) {
		String subPath = msg.getGuild().getId()+"/records";
		saveMessageToFile(msg,subPath);
	}
	
}
