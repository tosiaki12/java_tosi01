package tjavafile;

import java.io.IOException;

public class Test {
	public static void main(String[] args){

		try {
			Runtime.getRuntime().exec("java -cp A:\\1A\\1\\dev\\EclipseWorkspace\\EP-4.4-tosi1-workspace\\java_tosi1\\bin\\ tjavafile.TosiFileMain");
		} catch (IOException ex) {
			ex.printStackTrace();
			return;
		}
		System.exit(0);
	}
}
