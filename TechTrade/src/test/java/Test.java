public class Test {	
	public static void main(String[] args) {
		String s = "ngochuyou";
		String []ray = null;
		
		ray = s.replaceFirst(",", "").split(",");
		System.out.println(ray.length);
		for (String sub: ray) {
			System.out.println(sub);
		}
	}
}
