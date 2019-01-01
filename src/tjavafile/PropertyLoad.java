package tjavafile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoad {
    private static final String RUN_PATH = tjava.sysUtil.SysUtil.runDirBin;
    private static final String sep = tjava.sysUtil.SysUtil.dirSeparator;
    private static final String PROP_PATH = RUN_PATH+sep+"tfile"+sep+"property"+sep+"tjava.properties";


    public static String getProp(String key){
        String prop = null;
        try {
            Properties properties = new Properties();
            InputStream inputStream = new FileInputStream(PROP_PATH);
            properties.load(inputStream);
            inputStream.close();

            // 値の取得
            prop = properties.getProperty(key);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }
}
