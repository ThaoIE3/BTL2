package Bomberman.game.multiplayer.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

import Bomberman.Options;
import Bomberman.game.Player;
import Bomberman.game.multiplayer.Message;

public abstract class Client{
	private ObjectInputStream objectReader;
	private ObjectOutputStream objectWritter;
	
	private Socket socket;
	private boolean connected;
	private boolean readerIsRunning = true;
	
	protected HashMap<String, Player> players = new HashMap<String, Player>(); 
	
	public Client() {
		try {
			socket = new Socket("localhost", Options.PORT);

			objectWritter = new ObjectOutputStream(socket.getOutputStream());
			objectWritter.flush();
			objectReader = new ObjectInputStream(socket.getInputStream());
			
			connected = true;
			System.out.println("client sa ˙speöne spojil zo serverom");
			write(Message.TS_PLAYER_NAME + " Juro");
		} catch (IOException e) {
			System.out.println("nepodarilo sa pripojiù na server");
		}
		
		if(connected)
			listen();
	}
	
	private void listen() {
		Thread listenThread = new Thread(new Runnable(){
			public void run() {
				while(readerIsRunning){
					Object o = read();
					if(o != null){
						if(o instanceof String)
							proccesMsg((String)o);
						else
							proccesObject(o);
					}
						
				}
			}
		});
		listenThread.start();
	}
	
	public void cleanUp(){
		readerIsRunning = false;
		
		try {
			if(socket != null)
				socket.close();
			
			objectReader.close();
			objectWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object read(){
		try {
			//objectReader.reset();
			return objectReader.readObject();
		} catch (ClassNotFoundException | IOException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public void write(Object o){
		try {
			objectWritter.writeObject(o);
//			System.out.println("client odoslal spr·vu " + o);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void checkHosts(String subnet){
		Thread checker = new Thread(new Runnable(){
			@Override
			public void run() {
				int timeout = 100;
				for (int i=1 ; i<255 ; i++){
					String host = subnet + "." + i;
					try {
						if (InetAddress.getByName(host).isReachable(timeout)){
							System.out.println(host + " is reachable");
						}
						else{
							System.out.println(host + " is not reachable");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		checker.start();
	}

	public void proccesObject(Object o){};
	
	public void proccesMsg(String msg){};
	
	public boolean isConnected() {
		return connected;
	}

}
