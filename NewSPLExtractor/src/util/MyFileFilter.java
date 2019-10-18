package util;


import java.io.File;
import java.util.ArrayList;
import javax.swing.filechooser.*;

 

/**
 * @author  Administrateur
 */
public class MyFileFilter extends FileFilter {
   
   ArrayList <String> acceptedSuffixes;
   boolean acceptDirectory;
   /**
 * @uml.property  name="description"
 */
String  description;


   public MyFileFilter(String []suffixes, boolean acceptDirectory ,String description){
        
	    acceptedSuffixes = new ArrayList<String>();
	    for( int i = 0; i<suffixes.length; ++i){
	      acceptedSuffixes.add(suffixes[i]);
	    }
	    
	    this.acceptDirectory = acceptDirectory;
        this.description = description;
   }    
        
   int exist( String suffixe ){
	    
	   int x = -1; 
	   for(int i=0;i<acceptedSuffixes.size();i++){
	       
	    	if(suffixe.equals(acceptedSuffixes.get(i))){
	    		x = i; break;
	    	}
		}
	return x;
   
   }


   public boolean accept(File f) {

     if ((f.isDirectory())&&(acceptDirectory)) {

         return true;

     }

     String suffixe = null;

     String s = f.getName();

     int i = s.lastIndexOf('.');

     if (i > 0 &&  i < s.length() - 1) {

         suffixe = s.substring(i+1).toLowerCase();

     }

     return ((suffixe != null) && (exist(suffixe)!=-1));

   }

   // la description du filtre

   /**
 * @return
 * @uml.property  name="description"
 */
public String getDescription() {

       return description;

   }
   
   public void add ( String suffixe ){
        if(exist(suffixe)==-1){
        	acceptedSuffixes.add(suffixe);
        }
   }
   public void remove ( String suffixe ){
       int i = exist(suffixe);
	   if(i != -1){
       	acceptedSuffixes.remove(i);
       }
  }
   /**
 * @param accept
 * @uml.property  name="acceptDirectory"
 */
public void setAcceptDirectory ( boolean accept){
       this. acceptDirectory = accept;
   }   
}



