package info.s1products.midi;

public class NativeDeviceUtil {

	native private void jni_initialize();
	native private void jni_finalize();
	
	native private int jni_getInDeviceCount();
	native private String jni_getInDeviceName(int index);

	public NativeDeviceUtil(String libraryName) {
		System.loadLibrary(libraryName);
		jni_initialize();
	}
	
	public void finalize(){
		jni_finalize();
	}

	public int getInDeviceCount(){
		return jni_getInDeviceCount();
	}
	
	public String getInDeviceName(int index){
		return jni_getInDeviceName(index);
	}
}
