package info.s1products.server.util;

public class OSDetector {

	public static final String PROP_OS_NAME = "os.name";
	public static final String OS_PREFIX_AIX     = "AIX";
	public static final String OS_PREFIX_HP_UX = "HP-UX";
	public static final String OS_PREFIX_IRIX = "Irix";
	public static final String OS_PREFIX_LINUX_1 = "Linux";
	public static final String OS_PREFIX_LINUX_2 = "LINUX";
	public static final String OS_PREFIX_MAC = "Mac";
	public static final String OS_PREFIX_MAC_OSX = "Mac OS X";
	public static final String OS_PREFIX_FREE_BSD = "FreeBSD";
	public static final String OS_PREFIX_OPEN_BSD = "OpenBSD";
	public static final String OS_PREFIX_NET_BSD = "NetBSD";
	public static final String OS_PREFIX_OS_2 = "OS/2";
	public static final String OS_PREFIX_SOLARIS = "Solaris";
	public static final String OS_PREFIX_SUN_OS = "SunOS";
	public static final String OS_PREFIX_WINDOWS = "Windows";
	
	public enum OSType {
	    AIX,
	    HP_UX,
	    Irix,
	    Linux,
	    Mac,
	    MacOSX,
	    FreeBSD,
	    OpenBSD,
	    NetBSD,
	    OS2,
	    Solaris,
	    SunOS,
	    Windows,
	    Unknown
	}

	public static OSType getExecutingOSType(){
		
		String osName = System.getProperty(PROP_OS_NAME);
		
		if(osName == null){
			return OSType.Unknown;
		}
		
		if(osName.startsWith(OS_PREFIX_AIX)){
			return OSType.AIX;
			
		}else if(osName.startsWith(OS_PREFIX_HP_UX)){
			return OSType.HP_UX;
			
		}else if(osName.startsWith(OS_PREFIX_IRIX)){
			return OSType.Irix;
			
		}else if(osName.startsWith(OS_PREFIX_LINUX_1)){
			return OSType.Linux;
			
		}else if(osName.startsWith(OS_PREFIX_LINUX_2)){
			return OSType.Linux;
			
		}else if(osName.startsWith(OS_PREFIX_MAC_OSX)){
			return OSType.MacOSX;

		}else if(osName.startsWith(OS_PREFIX_MAC)){
			return OSType.Mac;
			
		}else if(osName.startsWith(OS_PREFIX_FREE_BSD)){
			return OSType.FreeBSD;
			
		}else if(osName.startsWith(OS_PREFIX_NET_BSD)){
			return OSType.NetBSD;
			
		}else if(osName.startsWith(OS_PREFIX_OS_2)){
			return OSType.OS2;
			
		}else if(osName.startsWith(OS_PREFIX_SOLARIS)){
			return OSType.Solaris;
			
		}else if(osName.startsWith(OS_PREFIX_SUN_OS)){
			return OSType.SunOS;
			
		}else if(osName.startsWith(OS_PREFIX_WINDOWS)){
			return OSType.Windows;
		}
		
		return OSType.Unknown;
	}
}
