package util;

import java.io.File;

public class FileUtil {

    public static String getExtension(File file){
	    
    	String suffixe = "";
        String str = file.getName();
        int i = str.lastIndexOf('.');
        
        if (i >= 0 &&  i < str.length() - 1) {
            suffixe = str.substring(i+1).toLowerCase();
        }
        return suffixe;
    }
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String path = "umlModel/GPL/B/Notepad-FH-Java_v.00002_ja.va.xmi";
		String path = "umlModel/GPL/B/.project";
		File f = new File(path);
		System.out.println(getExtension(f));
		System.out.println(f.getPath());
		//String str = " gfbkgfbgfb";
		//System.out.println(str.lastIndexOf('.'));
		
	}
}
