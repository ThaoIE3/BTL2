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
					//vytvorý kopiu zoznamu hráèov
					ArrayList<Player> tempPlayers = new ArrayList<Player>(players);
					for(Player p:tempPlayers){
						if(p!=null && p.isWritting()){
							//prijatu správu rozseka do pola stringov podla medzier
							String[] data = p.readLine().split(" ");
							
							//podla prvého bytu vyberie o aký typ správy sa jedná
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
						//èaká kým sa nepripojí nový hráè
						Socket client = serverSocket.accept();
					} catch (IOException e) { Logs.write("ukonèil sa server.accept() pretože sa socket ukonèil"); }
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
