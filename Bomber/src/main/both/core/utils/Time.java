package main.both.core.utils;

public class Time {
	private static final long SECOND = 1000000000L;
	private static double delta;
	
	public static double GetTime()
	{
		return (double)System.nanoTime()/(double)SECOND;
	}
	public static void setDelta(double delta){
		Time.delta=delta;
	}
	
	public static double getDelta(){
		return Time.delta;
	}
}
