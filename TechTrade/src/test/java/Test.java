import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;

public class Test {	
	public static void main(String[] args) {
		String url = "https://weatherwidget.io/";	
		try {
			Connection.Response resp = Jsoup.connect(url)
									        .timeout(30000)
									        .method(Connection.Method.GET)
									        .execute();
			
			Document responseDocument = resp.parse();
			Element potentialForm = responseDocument.select("[name$=searchForm]").first();
			FormElement form = (FormElement) potentialForm;
			if (potentialForm == null) {
				System.out.println("null");
			}
			Element addressField = form.select("[name$=autocompleteField]").first();

			if (addressField == null) {
				System.out.println("null 2");
			}
			addressField.attr("value", "Hà Nội, Việt Nam");
			
			Document searchResults = form.submit().cookies(resp.cookies()).get();
			Element embededdfield = searchResults.select("textarea.distance-container").first();
			System.out.println(embededdfield.text());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
