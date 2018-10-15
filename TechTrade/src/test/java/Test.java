public class Test {
	public static void main(String[] args) {	
		String s = "";
		
		s = s.replaceAll("#", ",#").replaceFirst(",", "");
		System.out.println(s);
	}
}
