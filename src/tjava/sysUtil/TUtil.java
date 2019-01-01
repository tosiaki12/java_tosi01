package tjava.sysUtil;
import java.util.Scanner;

public class TUtil {
    //private static Scanner Scana = new Scanner(System.in);

    public static String tScanStr () {
        String ScanStr = null;
        try {
            ScanStr = new Scanner(System.in).nextLine();
        } catch (Exception e ){
            e.printStackTrace();
        }
        return ScanStr;
    }

    public static String[] tScanStrSpl(String s) {
        String ScanStr = tScanStr();
        String[] SplitStrAry = ScanStr.split(s);
        return SplitStrAry;
    }

    public static int tScanInt () {
        //int in
        int scanInt = -1;
        try {
            scanInt =  new Scanner(System.in).nextInt();
        } catch (Exception e ){
            e.printStackTrace();
        }
        return scanInt;
    }


    public static String AryConvStr( Object[] convAry ) {
        String convStr = "";
        for (Object v : convAry) {
            convStr = convStr + String.valueOf(v);
        }
        return convStr;
    }




}
