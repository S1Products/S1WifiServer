package info.s1products.server.util;

public class SleepUtil {

	public static void waitMilli(int ms){
		
		try{ Thread.sleep(ms); }catch(Exception ex){}
	}
}
