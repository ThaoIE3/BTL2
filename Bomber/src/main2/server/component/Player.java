package main2.server.component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Player {
	BufferedWriter writer;
	BufferedReader reader;
	private Socket socket;
	
	public Player(){
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {e.printStackTrace(); }
	}
	
	public boolean isWritting() {
		try {
			return reader.ready();
		} catch (IOException e) {e.printStackTrace();}
		return false;
	}

	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {e.printStackTrace();}
		return null;
	}

	public void write(String msg) {
		try {
			writer.write(msg+"\n");
			writer.flush();
		} catch (IOException e) {e.printStackTrace(); }
	}
}
