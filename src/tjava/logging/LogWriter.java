package tjava.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import tjava.sysUtil.SysUtil;

public class LogWriter {

	/**
	 *
	 */
    String lineCd = System.getProperty("line.separator");
    String logDir = SysUtil.getExecBinDir()+lineCd+"tjavaLog.log";
    File logFile = new File(logDir);
    FileWriter filewriter;

    LogWriter(){
        this.logWrite("");
        this.logWrite("");
        this.logWrite("");
        Date newThisDate = new Date();
        String thisTime = newThisDate.toString();
        this.logWrite(thisTime);
        this.logWrite("LOGSTART");
    }

    public void logWrite(Object o){
        if(o == null){
            o = "null";
            return;
        }
        String s = o.toString();
        try{
            filewriter = new FileWriter(logFile,true);
            filewriter.write(s + lineCd);
            filewriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
