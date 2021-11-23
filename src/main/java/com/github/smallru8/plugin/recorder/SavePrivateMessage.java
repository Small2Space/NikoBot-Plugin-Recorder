package com.github.smallru8.plugin.recorder;

import java.io.File;
import java.util.List;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;

public class SavePrivateMessage extends SaveMessage{

	public SavePrivateMessage() {
		super("privateMSG/");
	}

	public void save(Message msg) {
		String subPath = msg.getAuthor().getId();
		saveMessageToFile(msg,subPath);
	}
	
	@Override
	protected String saveAttachments(Message msg,String date,File dir) {//Don't download
		String ret = "";
		List<Attachment> attLs = msg.getAttachments();
		if(!msg.getAuthor().isBot()&&!attLs.isEmpty()) {
			ret = "|";
			for(Attachment att:attLs) {
				String fileName = att.getFileName();
				ret+=fileName+"|";
			}
		}
		return ret;
	}
	
}
