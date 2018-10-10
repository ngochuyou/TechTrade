import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) {	
		try {
			List<String> hashtags = new ArrayList<>();
			BufferedReader br = new BufferedReader(new FileReader("D:\\description.txt"));
			String s = "";
			PrintWriter writer = new PrintWriter(new File("D:\\description2.txt"));
			String temp = "";
			
			while ((s=br.readLine()) != null) {
				writer.println(s.replaceAll("^ ", ""));
			}
			
			br.close();
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
