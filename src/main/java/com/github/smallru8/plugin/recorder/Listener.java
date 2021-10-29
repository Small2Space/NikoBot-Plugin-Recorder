package com.github.smallru8.plugin.recorder;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter{
	
	public void onMessageReceived(MessageReceivedEvent event) {
		new Dialogue().WriteMsg(event.getMessage());
	}
	
}
