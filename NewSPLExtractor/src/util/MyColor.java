package util;

import java.awt.Color;

public class MyColor {
	
	public static Color getColor(int num){
		Color res = null;
		
		switch(num) {
		   case 0 :
			   res =Color.black; 
		   break;
		   case 1 :
			   res =Color.blue; 
		   break;
		   case 2 :
			   res =Color.green; 
		   break;
		   case 3:
			   res =Color.gray; 
		   break;
		   case 4:
			   res =Color.orange; 
		   break;
		   case 5:
			   res =Color.red; 
		   break;
		   case 6:
			   res =Color.pink; 
		   break;
		   case 7:
			   res =Color.yellow; 
		   break;
		   case 8:
			   res =Color.magenta; 
		   break;
		   case 9:
			   res =Color.lightGray; 
		   break;
		   case 10:
			   res =Color.darkGray; 
		   break;
		   case 11:
			   res =Color.cyan; 
		   break;
		   case 12:
			   res =Color.white; 
		   break;
		   //default : res = Color.black; 
		   default : res = Color.getHSBColor((float)num/4*num, (float)2*num/5*num, (float)3*num/9*num); 
		}
		
		
		
		return res;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	

	}

}
