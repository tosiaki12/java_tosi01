package tjava.sysUtil;

public class SysUtil {
    public static final String execDir = System.getProperty("user.dir");
    public static final String runDirBin = getExecBinDir();
    public static final String dirSeparator = System.getProperty("file.separator");

    public static String getExecBinDir(){
        String runDir = execDir;
        if (!(execDir.endsWith("\\bin"))){              //後方一致
            runDir = execDir+"\\bin";               //eclipseからの実行だったら
        }
        return runDir;
    }

}
