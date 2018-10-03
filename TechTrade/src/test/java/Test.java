import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Test {
	public static void main(String[] args) {

		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream("D:\\ward-nameonly-part4.txt"), "UTF-16"));
			String s = "";
			StringBuilder url = null;
			Connection con = null;
			Response res = null;
			Document doc = null;
			Elements ele = null;
			PrintWriter pw = new PrintWriter("D:\\ward-withCords-part10.txt", "UTF-16");
			
			while ((s = br.readLine()) != null) {
				url = new StringBuilder("https://vi.wikipedia.org/wiki/");
				url.append(s);
				System.out.println(s);
				con = Jsoup.connect(url.toString());
				con = Util.mask(con);
				try {
					res = con.timeout(10*1000).execute();
				} catch(HttpStatusException ex) {
					pw.println(s);
					continue;
				}
				doc = res.parse();
				ele = doc.getElementsByClass("geo-dms");
				
				if (ele.isEmpty()) {
					pw.println(s);
				} else {
					pw.println(s + " " + ele.get(0).text().replaceAll("B", "N").replaceAll("Đ", "E"));
				}
				pw.flush();
			}
			
			br.close();
			pw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Util {
    public static Connection mask(Connection c) {
        return c.header("Host", "brickseek.com")
                .header("Connection", "keep-alive")
                .header("Cache-Control", "max-age=0")
                .header("Origin", "https://brickseek.com/")
                .header("Upgrade-Insecure-Requests", "1")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.48 Safari/537.36")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .referrer("http://brickseek.com/walmart-inventory-checker/")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "en-US,en;q=0.8");
    }
}
