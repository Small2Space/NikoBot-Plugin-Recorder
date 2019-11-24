package com.github.smallru8.plugin.recorder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;

public class Dialogue {
	
	public Dialogue() {
		File recorderDir = new File("records");
		if(!recorderDir.exists())
			recorderDir.mkdir();
	}
	public void WriteMsg(Message msg) {
		String server = msg.getGuild().getName();
		String channel = msg.getChannel().getName();
		String message = msg.getContentRaw();
		String usrName = msg.getAuthor().getName();
		String uuid = msg.getAuthor().getId();
		Date dt = new Date();
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String da = sdf.format(dt);
		boolean saveSuccess = false;
		while(!saveSuccess) {
			try
		    {
			    FileWriter record=new FileWriter("records/" + server + "/" + channel + ".log",true);
			    String saveStr = "[" + da + "][" + uuid + "][" + usrName + "][";
			    saveStr+=attachment(msg,da);
			    saveStr+= "] : " + message + "\n";
			    System.out.print(saveStr);
		        record.write(saveStr);
		        record.flush();
		        record.close();
		        saveSuccess = true;
		    }catch(FileNotFoundException e1){///找不到檔案就新建
		    	File dir_file = new File("records/" + server + "/attachments");
		        dir_file.mkdirs();
		        e1.printStackTrace();
		    }catch(IOException e1){
		        e1.printStackTrace();
		    }
		}
	}
	private String attachment(Message m,String da) throws IOException {
		String fileName = "";
		if(!m.getAuthor().isBot()&&!m.getAttachments().isEmpty()) {
			fileName+="|";
			for(Attachment a:m.getAttachments()) {
				fileName+=(a.getFileName()+"|");
				File f = new File("records/" + m.getGuild().getName() + "/attachments/" + da + "-"+ a.getFileName());
				if(!(new File("records/" + m.getGuild().getName() + "/attachments").exists()))
					new File("records/" + m.getGuild().getName() + "/attachments").mkdir();
				URL a_url = new URL(a.getUrl());
				HttpURLConnection con = (HttpURLConnection) a_url.openConnection();
				con.setRequestProperty("User-agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
				BufferedInputStream in = new BufferedInputStream(con.getInputStream());
				byte[] data = new byte[1024];
				f.createNewFile();
				FileOutputStream fos = new FileOutputStream(f);
				int n = 0;
				while((n=in.read(data,0,1024))>=0) 
					fos.write(data,0,n);
				fos.flush();
				fos.close();
				in.close();
			}
		}
		return fileName;
	}
}