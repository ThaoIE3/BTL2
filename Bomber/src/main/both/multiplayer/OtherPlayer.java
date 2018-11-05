package main.both.multiplayer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import main.both.components.Player;

public class OtherPlayer extends Player{
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public OtherPlayer(Socket socket){
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.name = reader.readLine().split(" ")[1]; 
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

	public void close() {
		try {
			reader.close();
			writer.close();
			if(socket!=null)
				socket.close();
		} catch (IOException e) {e.printStackTrace();}
		
	}

	public void write(String msg){
		try {
			writer.write(msg+"\n");
			writer.flush();
		} catch (IOException e) {e.printStackTrace(); }
	}
}
