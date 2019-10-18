package util;



public class Directory {
	
	
	public static String getCurrentDirectory() {

		return TextFile.readString("util/currentDirectory.txt", "");  

	}
//__________________________________________________________________________________________
	
	public static void setCurrentDirectory(String path) {
		
		TextFile.write(path, "util/currentDirectory.txt", "", true);
	}
}
