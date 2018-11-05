package main.both.multiplayer;

import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import main.MainBomber;
import main.both.Bomberman;
import main.both.core.Game;
import main.both.core.GameObject;
import main.both.core.utils.Logs;
import main.both.core.utils.Vector2f;

public class Server {
	public static final String START_CODE = "CONNECT";
	public static final String END_CODE = "THEEND";
	public static final String PLAYER_POSITION = "PP";
	public static final String ADD_NEW_PLAYER = "ANP";
	public static final String MAP_NAME = "MN";
	private final static int PORT = 8888;
	private ServerSocket serverSocket;
	private Game game;
	private boolean readerIsRunning = true;
	private boolean accepterIsRunning = true;
	
	public Server(Game game){
		this.game = game;
		try {
			serverSocket = new ServerSocket(PORT);
			Logs.write("vytvoril sa server socket na porte "+serverSocket.getLocalPort());
			listen();
		} catch (IOException e) {e.printStackTrace(); }
	}
	
	private void listen(){
		Thread acceptThread = new Thread(new Runnable(){
			public void run(){
				while(accepterIsRunning){
					try {
						//toto  èaká kým sa nepripojí nový hráè
						Socket client = serverSocket.accept();
						//vytvorí hráèa
						OtherPlayer player = new OtherPlayer(client);
						//pridá hráèa na vykreslovanie
						game.addObject(new GameObject().addComponent(player));
						
						Logs.write("na server sa pripojil nový hráè "+player.getName());
						//pošlne názov mapy hráèovy
						player.write(MAP_NAME+" "+game.mapa.fileName);
						
						player.setPosition(new Vector2f(60,60));
						player.setColor(new Color(255,0,255));
						//pošle mu farbu a súradnice
						player.write(Server.START_CODE+" "+player.getPosition().getX()+" "+player.getPosition().getY()+" "+255+" "+0+" "+255);
						
						Logs.write("jeho position je: "+player.getPosition()+" jeho color je "+player.getColor());
						//pošle mu udaje o mojom hráèovy
						player.write(Server.ADD_NEW_PLAYER+" "+game.player.getName()+" "+game.player.getPosition().getX()+" "+game.player.getPosition().getY()+" "+game.player.getColor().getRed()+" "+game.player.getColor().getGreen()+" "+game.player.getColor().getBlue());
						
						//pošle mu udaje o ostatných hráèoch a ostanýhm hráèom pošle jeho údaje
						ArrayList<OtherPlayer> tempPlayers = new ArrayList<OtherPlayer>(((Bomberman)game).players);
						for(OtherPlayer p : tempPlayers){
							p.write(Server.ADD_NEW_PLAYER+" "+player.getName()+" "+player.getPosition().getX()+" "+player.getPosition().getY()+" "+
									player.getColor().getRed()+" "+player.getColor().getGreen()+" "+player.getColor().getBlue());
							player.write(Server.ADD_NEW_PLAYER+" "+p.getName()+" "+p.getPosition().getX()+" "+p.getPosition().getY()+" "+p.getColor().getRed()+" "+p.getColor().getGreen()+" "+p.getColor().getBlue());
						}
						//pridá hráèa k ostatným hráèom
						((Bomberman)game).players.add(player);
					} catch (IOException e) { Logs.write("okonèilo sa server.accept() pretože sa socket ukonèil")/*e.printStackTrace()*/; }
				}
			}
		});
		acceptThread.start();
		
		Thread readThread = new Thread(new Runnable(){
			public void run(){
				while(readerIsRunning){
					ArrayList<OtherPlayer> tempPlayers = new ArrayList<OtherPlayer>(((Bomberman)game).players);
					for(OtherPlayer p : tempPlayers){
						try {
							Thread.sleep(1000 / MainBomber.FPS / Math.max(1, tempPlayers.size()));
						} catch (InterruptedException e) {e.printStackTrace(); }
						if(p!=null && p.isWritting()){
							String line = p.readLine();
							String[] data = line.split(" ");
							if(line.startsWith(END_CODE)){
								System.out.println(p.getName()+" sa odpojil");
								p.close();
								((Bomberman)game).players.remove(p);
//								System.exit(1);
								continue;
							}
							else if(line.startsWith(PLAYER_POSITION)){
								((Bomberman)game).players.get(((Bomberman)game).players.indexOf(p)).setPosition(new Vector2f(Float.valueOf(data[1]),Float.valueOf(data[2])));
								write(PLAYER_POSITION+" "+p.getName()+" "+p.getPosition().getX()+" "+p.getPosition().getY());
							}
						}
					}
				}
			}
		});
		readThread.start();
		
	}
	
	public void close(){
		accepterIsRunning = false;
		readerIsRunning = false;
		for(OtherPlayer p: ((Bomberman)game).players){
			p.close();
		}
		try {
			serverSocket.close();
		} catch (IOException e) {e.printStackTrace(); }
	}
	
	public void write(String msg){
		ArrayList<OtherPlayer> tempPlayers = new ArrayList<OtherPlayer>(((Bomberman)game).players);
		for(OtherPlayer p : tempPlayers){
			p.write(msg);
		}
	}
	
	public void write(String msg,String except){
		ArrayList<OtherPlayer> tempPlayers = new ArrayList<OtherPlayer>(((Bomberman)game).players);
		for(OtherPlayer p : tempPlayers){
			if(p.getName().equals(except))
				continue;
			p.write(msg);
		}
	}
	
}
