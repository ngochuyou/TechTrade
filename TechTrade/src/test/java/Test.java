import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
	public static void main(String[] args) {
		int n = 10;

		String[] desArray = new String[41];

		try {
			BufferedReader bf = new BufferedReader(new FileReader("D:\\description.txt"));
			String s;

			for (int i = 0; (s = bf.readLine()) != null; i++) {
				desArray[i] = s;
			}
			bf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Random ran = new Random();
		int noOfRows;
		List<String> desList = new ArrayList<String>();
		StringBuffer des = new StringBuffer();

		for (int i = 0; i < n; i++) {
			noOfRows = ran.nextInt((10 - 0) + 1) + 0;
			des = new StringBuffer();
			
			for (int o = ran.nextInt((30 - 0) + 1) + 0, j = 0; j < noOfRows; j++, o++) {
				des.append(desArray[o]);
			}
			desList.add(des.toString());
		}
		
	}
}
