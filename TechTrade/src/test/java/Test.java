import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) {	
		Pattern p = Pattern.compile("(createAt|nearest)(:desc|:asc)");
		Matcher m = p.matcher("abc:asc");
		
		System.out.println(m.matches());
	}
}
