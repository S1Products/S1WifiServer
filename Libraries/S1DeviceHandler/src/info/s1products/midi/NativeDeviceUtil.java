package info.s1products.midi;

public class NativeDeviceUtil {

	native private void jni_initialize();
	native private void jni_finalize();

	native private int jni_getOutDeviceCount();
	native private String jni_getOutDeviceName(int index);
	native private String jni_getOutDeviceManufacturer(int index);
	
	native private int jni_getInDeviceCount();
	native private String jni_getInDeviceName(int index);
	native private String jni_getInDeviceManufacturer(int index);

	public NativeDeviceUtil(String libraryName) {
		System.loadLibrary(libraryName);
		jni_initialize();
	}
	
	public void finalize(){
		jni_finalize();
	}

	public int getOutDeviceCount(){
		return jni_getOutDeviceCount();
	}

	public String getOutDeviceName(int index){
		return jni_getOutDeviceName(index);
	}

	public String getOutDeviceManufacturer(int index){
		return jni_getOutDeviceManufacturer(index);
	}

	public int getInDeviceCount(){
		return jni_getInDeviceCount();
	}
	
	public String getInDeviceName(int index){
		return jni_getInDeviceName(index);
	}
	
	public String getInDeviceManufacturer(int index){
		return jni_getInDeviceManufacturer(index);
	}
}
