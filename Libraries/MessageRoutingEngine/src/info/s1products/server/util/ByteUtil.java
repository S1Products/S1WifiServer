package info.s1products.server.util;

import java.awt.Color;

public class ByteUtil {

	public static byte[] appendNullToLeft(byte[] data, int place){

		int appendCount = place - data.length % place;
		byte[] appendData = new byte[data.length + appendCount];
		
		for(int i = appendCount; i < appendData.length; i++){
			
			appendData[i] = data[i - appendCount];
		}
		
		return appendData;
	}

	public static byte[] appendNullToRight(byte[] data, int place){

		int appendCount = place - data.length % place;
		byte[] appendData = new byte[data.length + appendCount];
		
		for(int i = 0; i < data.length; i++){
			
			appendData[i] = data[i];
		}
		
		return appendData;
	}

	public static byte[] append(byte[] bytes1, byte[] bytes2) {
		
		byte appendedBytes[] = new byte[bytes1.length + bytes2.length];
		
		System.arraycopy(bytes1, 0, appendedBytes, 0, bytes1.length);
		System.arraycopy(bytes2, 0, appendedBytes, bytes1.length, bytes2.length);
		
		return appendedBytes;
	}
	
	public static int align(int index) {
		
	    return 4 - (index % 4);
	}
	
	/**
	 * Copy byte array from start position
	 * @param bytes Source byte array
	 * @param start Start position
	 * @return Copied byte array
	 */
	public static byte[] copy(byte[] bytes, int start) {
		
		return copy(bytes, start, bytes.length - start);
	}
	
	/**
	 * Copy byte array from start to end position
	 * @param bytes Source byte array
	 * @param start Start position
	 * @param end End position
	 * @return Copied byte array
	 */
	public static byte[] copy(byte[] bytes, int start, int end) {
		
		byte copiedBytes[] = new byte[end];
		System.arraycopy(bytes, start, copiedBytes, 0, end);
		return copiedBytes;
	}
	
	public static int toInt(byte[] bytes) {
		
		return (bytes[3] & 0xff) + ( (bytes[2] & 0xff) << 8)
		        + ( (bytes[1] & 0xff) << 16) + ( (bytes[0] & 0xff) << 24);
	}
	
	public static long toLong(byte[] bytes) {
		
		return ( (long) bytes[7] & 255L) + ( ( (long) bytes[6] & 255L) << 8)
				+ ( ( (long) bytes[5] & 255L) << 16)
		        + ( ( (long) bytes[4] & 255L) << 24)
		        + ( ( (long) bytes[3] & 255L) << 32)
		        + ( ( (long) bytes[2] & 255L) << 40)
		        + ( ( (long) bytes[1] & 255L) << 48)
		        + ( ( (long) bytes[0] & 255L) << 56);
	}
	
	public static float toFloat(byte[] bytes) {
	
		int intValue = toInt(bytes);
		return Float.intBitsToFloat(intValue);
	}
	
	public static double toDouble(byte[] bytes) {
		
		long longValue = toLong(bytes);
		return Double.longBitsToDouble(longValue);
	}
	
	public static Color toColor(byte[] bytes){
		
		return new Color((int)bytes[0], (int)bytes[1], 
				(int)bytes[2], (int)bytes[3]);
	}
	
    public static byte[] toBytes(int value) {
    	
    	byte[] bs = new byte[4];
        bs[3] = (byte) (0x000000ff & (value));
        bs[2] = (byte) (0x000000ff & (value >>> 8));
        bs[1] = (byte) (0x000000ff & (value >>> 16));
        bs[0] = (byte) (0x000000ff & (value >>> 24));
        return bs;
    }
    
    public static byte[] toBytes(long value) {

    	byte[] bs = new byte[8];
        bs[7] = (byte) (0x000000ff & (value));
        bs[6] = (byte) (0x000000ff & (value >>> 8));
        bs[5] = (byte) (0x000000ff & (value >>> 16));
        bs[4] = (byte) (0x000000ff & (value >>> 24));
        bs[3] = (byte) (0x000000ff & (value >>> 32));
        bs[2] = (byte) (0x000000ff & (value >>> 40));
        bs[1] = (byte) (0x000000ff & (value >>> 48));
        bs[0] = (byte) (0x000000ff & (value >>> 56));
        return bs;
    }

    public static byte[] toBytes(float value) {
    	
    	int bits = Float.floatToIntBits(value);
    	return toBytes(bits);
    }

    public static byte[] toBytes(double value) {
    	
    	long bits = Double.doubleToLongBits(value);
    	return toBytes(bits);
    }

}
