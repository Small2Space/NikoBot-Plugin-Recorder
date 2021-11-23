package com.github.smallru8.plugin.recorder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.skunion.smallru8.util.SHA;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;

public class GuildMessage {
	
	public static void saveMessage(Message msg) {
		File f = new File("servers/" + msg.getGuild().getId() + "/records");
		if(!f.exists())
			f.mkdir();
		File chDir = new File(f,msg.getChannel().getId());
		if(!chDir.exists())
			chDir.mkdir();
		File attDir = new File(chDir,"attachments");
		if(!attDir.exists())
			attDir.mkdir();
		
		//Save format
		//[Date][ID][Name][|attachments|] :
		
		Date dt = new Date();
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		SimpleDateFormat sdfY =  new SimpleDateFormat("yyyy");
		String year = sdfY.format(dt);
		String da = sdf.format(dt);
		
		boolean saveSuccess = false;
		while(!saveSuccess) {
			try
		    {
			    FileWriter record=new FileWriter(new File(chDir,year+".log"),true);
			    String saveStr = "[" + da + "][" + msg.getAuthor().getId() + "][" + msg.getAuthor().getName() + "][";
			    saveStr+=saveAttachments(msg,da,attDir);
			    saveStr+= "] : " + msg.getContentRaw() + "\n";
		        record.write(saveStr);
		        record.flush();
		        record.close();
		        saveSuccess = true;
		    }catch(IOException e1){
		        e1.printStackTrace();
		    }
		}
	}
	
	private static String saveAttachments(Message msg,String date,File dir) {
		String ret = "";
		List<Attachment> attLs = msg.getAttachments();
		if(!msg.getAuthor().isBot()&&!attLs.isEmpty()) {
			ret = "|";
			for(Attachment att:attLs) {
				String fileName = date+att.getFileName();
				if(fileName.length()>255){//Filename too long
					fileName = date+SHA.SHA1(fileName);
					fileName += att.getFileExtension().length()<20 ? "."+att.getFileExtension() : "";
				}
				ret+=fileName+"|";
				att.downloadToFile(new File(dir,fileName)).thenAccept(File -> {}).exceptionally(t ->
		        	{ // handle failure
		        		t.printStackTrace();
		        		return null;
		        	});
			}
		}
		return ret;
	}
	
}
