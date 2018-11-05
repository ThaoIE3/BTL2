package main2.server.multiplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import main.both.core.utils.Logs;
import main2.server.component.Player;

public class Server implements Connectable{
	private final static int PORT = 8888;
	
	private ServerSocket serverSocket;
	private boolean readerIsRunning = true;
	private boolean accepterIsRunning = true;
	private ArrayList<Player> players ;
	
	public Server(){
		players = new ArrayList<Player>();
		try {
			serverSocket = new ServerSocket(PORT);
			Logs.write("vytvoril sa server socket na porte "+serverSocket.getLocalPort());
		} catch (IOException e) {e.printStackTrace(); }
		
		listen();
		read();
	}
	
	private void read() {
		Thread readThread = new Thread(new Runnable(){
			public void run(){
				while(readerIsRunning){
					//vytvor� kopiu zoznamu hr��ov
					ArrayList<Player> tempPlayers = new ArrayList<Player>(players);
					for(Player p:tempPlayers){
						if(p!=null && p.isWritting()){
							//prijatu spr�vu rozseka do pola stringov podla medzier
							String[] data = p.readLine().split(" ");
							
							//podla prv�ho bytu vyberie o ak� typ spr�vy sa jedn�
							switch(Byte.valueOf(data[0])){
								case G_CLIENT_CONNECT:
									break;
								case G_CLIENT_DISCONNECT:
									break;
							}
						}
					}
				}
			}
		});
		readThread.start();
	}

	private void listen(){
		Thread listenThread = new Thread(new Runnable(){
			public void run(){
				while(accepterIsRunning){
					try {
						//�ak� k�m sa nepripoj� nov� hr��
						Socket client = serverSocket.accept();
					} catch (IOException e) { Logs.write("ukon�il sa server.accept() preto�e sa socket ukon�il"); }
				}
			}
		});
		listenThread.start();
	}

	public void write(String msg){
		ArrayList<Player> tempPlayers = new ArrayList<Player>(players);
		for(Player p : tempPlayers){
			p.write(msg);
		}
	}
}
