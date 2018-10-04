import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String a = "$2a$10$oE0u8YbLHqT/ktyUcH0RJ.9NYGfOiwu33qJhT309FlOw7Zav5qP8m";
		
		if (a.equals(encoder.encode("myelt1998"))) {
			System.out.println("yes");
		} else {
			System.out.println("no");
		}
		
//
//		try {
//			BufferedReader br = new BufferedReader(
//					new InputStreamReader(new FileInputStream("D:\\ward-nameonly-part4.txt"), "UTF-16"));
//			String s = "";
//			StringBuilder url = null;
//			Connection con = null;
//			Response res = null;
//			Document doc = null;
//			Elements ele = null;
//			PrintWriter pw = new PrintWriter("D:\\ward-withCords-part10.txt", "UTF-16");
//			
//			while ((s = br.readLine()) != null) {
//				url = new StringBuilder("https://vi.wikipedia.org/wiki/");
//				url.append(s);
//				System.out.println(s);
//				con = Jsoup.connect(url.toString());
//				con = Util.mask(con);
//				try {
//					res = con.timeout(10*1000).execute();
//				} catch(HttpStatusException ex) {
//					pw.println(s);
//					continue;
//				}
//				doc = res.parse();
//				ele = doc.getElementsByClass("geo-dms");
//				
//				if (ele.isEmpty()) {
//					pw.println(s);
//				} else {
//					pw.println(s + " " + ele.get(0).text().replaceAll("B", "N").replaceAll("Đ", "E"));
//				}
//				pw.flush();
//			}
//			
//			br.close();
//			pw.close();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
}
