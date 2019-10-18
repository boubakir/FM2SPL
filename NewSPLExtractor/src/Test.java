import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> liste = new ArrayList<String>();
		liste.add("Bonjou");
		liste.add("bonjour");
		String str1 = "bonjour";
		String str2 = new String("bonjour");
		String str3 = new String("Bonjour");
		
		System.out.println(liste.contains(str1));
		System.out.println(liste.contains(str2));
		System.out.println(liste.contains(str3));

	}

}
