package tjava.test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestWriter {


	File file = new File("test.txt");
	FileWriter filewriter;

	String lineCd = System.getProperty("line.separator");


	public void logtestWrite(Object o){
		if(o == null){
			o = "null";
		}
		String s = o.toString();
		try{
			filewriter = new FileWriter(file,true);
			filewriter.write(s + lineCd);
			filewriter.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
