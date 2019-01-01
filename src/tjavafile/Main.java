package tjavafile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;


public class Main {
	public static void mainn(String[] argss){ //NOUSE
		System.out.println("Hello World");
		String execDir = System.getProperty("user.dir");
		String sep = System.getProperty("file.separator");
		String runDir = execDir;
		if (!(execDir.endsWith("\\bin"))){              //後方一致
			runDir = execDir+"\\bin";                   //eclipseからの実行だったら
		}
		printd("execDir :"+execDir,true);
		printd("runDir  :"+runDir ,true);
		printd("fileSep :"+sep    ,true);

		String[] cmds = {"java","tjavafile.TosiFileMain"};
		File dirFile = new File(runDir);
		ProcessBuilder pb = null;
		Process proc = null;
		String str = "d";
		BufferedReader brstd = null;
		PrintStream out = null;
		try{                                            //https://qiita.com/shintaness/items/6dd91260726e555c49e5
			pb = new ProcessBuilder(cmds);
			pb = pb.directory(dirFile);
			System.out.println(pb.directory());
			proc = pb.start();


			pb.redirectErrorStream(true);// 標準出力と標準エラー出力をMIX

			brstd = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while((str = brstd.readLine()) != null){
				printd(str,false);
			}




			while(true){
				printd("INPUt",true);
				out = new PrintStream( proc.getOutputStream() );
				int a = 0;
				out.println(a);
				out.flush();
			}




		}catch(Exception e){
			printd(e,false);
			e.printStackTrace();
		} finally {
			try {
				brstd.close();
				proc.destroy();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void printd(Object o, boolean isDebug){
		System.out.println(o);
	}

}
