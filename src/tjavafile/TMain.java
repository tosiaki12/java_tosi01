package tjavafile;

import tjava.GUI.TMainFrame;
import tjava.sysUtil.TUtil;
import tjava.test.TestWriter;

public class TMain {

    public static final String SOFTNAME = PropertyLoad.getProp("SoftName");
    public static final String VIRSION = PropertyLoad.getProp("SoftVirsion");
    public static final String JAVAVIRSION = PropertyLoad.getProp("JavaVirsion");

    public static boolean isDebug;
    public static TestWriter tw = new TestWriter();

    public static final String[] SOFTS =
        {
        "0:tKeisan ",
        "1:morse_converter ",
        "2:keisan ",
        "3:keisan_old ",
        "4:siritori ",
        "5:GUI"
        };

    public static void main(String[] args) {
        System.out.println("Hello World");

        String execDir  = tjava.sysUtil.SysUtil.execDir;
        String sep      = tjava.sysUtil.SysUtil.dirSeparator;
        String runDir   = tjava.sysUtil.SysUtil.runDirBin;

        printd(new String("execDir :"+execDir));
        printd(new String("runDir  :"+runDir));
        printd(new String("fileSep :"+sep));

        printd("start");
        printd("SOFTNAME    : " + SOFTNAME);
        printd("VIRSION     : " + VIRSION);
        printd("JAVAVIRSION : " + JAVAVIRSION);
        run();
        //devRun();
    }

    public static void printdMain(Object o, boolean isDebug, String callClass){
        String rog = null;
        if(isDebug == true){
            if(tjavafile.TMain.isDebug == true) {
                rog = o + "       --"+callClass+"--debug";
            }
        } else {
            rog = o + "   --"+callClass;
        }
        System.out.println(rog);
        tw.logtestWrite(rog);
    }

    private static void printd(Object o, boolean isDebug){
        TMain.printdMain(o, isDebug, "TMain");
    }
    private static void printd(Object o){
        TMain.printdMain(o, false, "TMain");
    }



    public static void run() {
        printd("startrun",false);
        if(PropertyLoad.getProp("IsDebug").equals("true")){
            isDebug = true;
        } else {
            isDebug = false;
        }
        printd("normalStart == 0, devStart == 1, stop == 2");
        int startType = tjava.sysUtil.TUtil.tScanInt();
        switch (startType) {
        case 0:
            normalRun();
            break;
        case 1:
            devRun();
            break;
        case 2:
            printd("stop");
        }
        printd("STOP");
    }

    public static void normalRun() {
        String softList = TUtil.AryConvStr(SOFTS);
        printd("softs : " + softList);
        int startType = TUtil.tScanInt();
        printd(startType);
        switch (startType) {
        case 0:
            printd("not found");
            break;
        case 1:
            printd("not found");
            break;
        case 2:
            printd("not found");
            break;
        case 3:
            printd("not found");
            break;
        case 4:
            //printd("not found");
            printd("run");
            tjava.siritori.Siritori.siritoriRunner();
            break;
        case 5:
            //printd("not found");
            TMainFrame.guiRun();
            break;
        case 6:
            printd("not found");
            break;
        }
    }

    public static void devRun() {
        tjava.siritori2.SiritoriWordTest.swTest();
        //tosi.test.SQLTester.DBTest();
        //TestRunner.testRun();
    }

}
