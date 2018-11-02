import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;

public class Test {	
	public static void main(String[] args) {
		String test = "ngochuyou1234";
		
		System.out.println(test.split("\\D+")[1]);
	}
}
