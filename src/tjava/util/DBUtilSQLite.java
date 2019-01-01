package tjava.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBUtilSQLite {

    public Connection con;
    public ResultSet rs;
    public ResultSetMetaData rsmd;
    public PreparedStatement psSet;
    public PreparedStatement psGet;
    private String[] convStr;

    private static void printd(Object o, boolean isDebug) {
        if(isDebug == true){
            if(tjavafile.TMain.isDebug == true) {
                System.out.println(o + " --DBUtilSQLite--debug");
            }
        } else {
            System.out.println(o + " --DBUtilSQLite");
        }
    }

    public  void DBConnect (String url) {
        this.con  = null;
        this.rs   = null;
        this.rsmd = null;
        this.psSet   = null;
        this.psGet   = null;
        try {
            String execDir    = System.getProperty("user.dir");
            String jarPath    = "tfile\\jarRib\\sqlite-jdbc-3.21.0.jar";
            String eclipseDir = "bin\\"+jarPath;
            String cmdDir     = "tfile\\jarRib\\sqlite-jdbc-3.21.0.jar";
            String path       = "";
            if (execDir.endsWith("\\bin")){               //後方一致
                path = cmdDir;                 //cmdからの実行だったら
            } else {
                path = eclipseDir;
            }
            printd("execDir :"+execDir,false);
            printd("jarPath :"+path,false);

            File file = new File(path);
            URLClassLoader load = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});
            //クラスをロード
            Class<?> cl = load.loadClass("org.sqlite.JDBC");
            Driver dr = (Driver)cl.newInstance(); //https://symfoware.blog.fc2.com/blog-entry-922.html
            Properties pr = new Properties();
            pr.setProperty("user", "");
            pr.setProperty("password", "");
            this.con = dr.connect(url, pr);
            //Class.forName("org.sqlite.JDBC");
            //this.con = DriverManager.getConnection(url);
            System.out.println("接続成功");
            //this.rsmd = rs.getMetaData();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
        }
    }

    public void getData( String sql ) {
        try {
            this.psGet = con.prepareStatement(sql);
            this.rs = psGet.executeQuery();
            printd("getSQL execute  --getData", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //https://java-reference.com/java_db_select.html
    }



    public void setData( String sql ) {
        try {
            System.out.println("DBset");
            psSet = con.prepareStatement(sql);
            psSet.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                psSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //https://java-reference.com/java_db_select.html
    }

    public String[] rsConvStr(String clmName) {
        List<String> rsList = new ArrayList<>();
        try {
            while(this.rs.next()){
                /* 行からデータを取得 */
                String clmdata = this.rs.getString(clmName);/*行名*/
                System.out.println("rsGet" + clmdata);
                rsList.add(clmdata);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.convStr = rsList.toArray(new String[rsList.size()]);
        for (int i = 0; i < this.convStr.length; i++) {
            if (this.convStr[i] == null) {
                this.convStr[i] = "NONE";
            }
        }
        int along = this.convStr.length;
        printd(along + "rrr", true);
        return this.convStr;
    }


    public Object[] getrsAll(String sql, String clmname){
        List<Object> resList = new ArrayList<>();
        Object[] resObj;
        PreparedStatement getps = null;
        ResultSet res = null;
        try {
            //SQLを実行
            getps = con.prepareStatement(sql);
            res = getps.executeQuery();
        } catch (SQLException e) {
            printd("SQL Failed --getrsAll", false);
            e.printStackTrace();
        }
        try {
            //データを取得
            while(res.next()){
                int rowint = res.getRow();
                printd(rowint + "     --getrsAll", true);
                Object o = res.getObject(clmname);
                resList.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int DataSize = resList.size();
        printd("DataSize is "+DataSize+"   --getrsAll", true);
        resObj = resList.toArray(new Object[resList.size()]);
        for (int i = 0; i < resObj.length; i++) {
            if (resObj[i] == null) {
                resObj[i] = "NONE";
            } else {
                System.out.println(resObj[i] + "get");
            }
        }

        return resObj;
    }

    public String[] getrsAllStr(String sql, String clmname){
        Object[] rsObj = this.getrsAll(sql, clmname);
        String[] rsStr = new String[rsObj.length];
        for(int i=0;i<rsObj.length;i++){
            rsStr[i] = rsObj[i].toString();
            printd(rsStr[i] + " StrConverted    --getrsAllStr", true);
        }
        return rsStr;
    }

    /**
     * 呼び出すときに代入するListにcastする
     *
     * @param sql
     * @param clmname
     * @param t
     * @param nullEscape
     * @return Listが入った総称型
     */
    public <T> T getResGenList(String sql, String clmname, Class<T> t, Object nullEscape){
        System.out.println("eslelsla");
        Object[] rsObj = this.getrsAll(sql, clmname);
        if (rsObj.length==0){
            Object[] ted = new Object[1];
            ted[0] = nullEscape;
            rsObj = ted;
        }
        Object dataObj = rsObj[0];
        List<T> rsObjs = new ArrayList<>();
        Class<? extends Object> classtype = dataObj.getClass();
        System.out.println(classtype.getName());
        for(int i=0;i<rsObj.length;i++){
            System.out.println("castary");
            T gdata = t.cast(rsObj[i]);
            rsObjs.add(gdata);
            System.out.println(rsObjs.get(i));
        }
        T genedata = t.cast(rsObjs);
        System.out.println("cast");
        return genedata;
    }

    public <T> T getResGen(String sql, String clmname, Class<T> t, Object nullEscape){
        printd("getResgen",true);
        Object[] rsObj = this.getrsAll(sql, clmname);
        if (rsObj.length==0 || rsObj[0] == null){
            Object[] nullesc = new Object[1];
            nullesc[0] = nullEscape;
            rsObj = nullesc;
        }
        Object dataObj = rsObj[0];
        Class<? extends Object> ssss = dataObj.getClass();
        printd(ssss.getName()+" type    --getResGen",true);
        String tes ="test";
        if( t.isInstance(tes)){
            dataObj = dataObj.toString();
            printd("StringCast",true);
        }
        T r = t.cast(dataObj);
        printd("castAfter  "+r+"  --getResGen",true);
        return r;
    }

    public String[] searchDB ( String table, String column, Object ser ) {
        String searchSQL =
                "SELECT * FROM '"+table+"' WHERE '"+column+"' ='"+ser+"'";
        this.getData(searchSQL);
        String[]searchRes = this.rsConvStr(column);
        return searchRes;
    }

    public void getcolumn() {
        try {
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                //行名を取得
                String sss = rsmd.getColumnName(i);
                System.out.println(sss);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public int getclmCount(String table) {
        String clmCount = "SELECT COUNT(*) FROM '"+table+"'";
        int count = 0;
        this.getData(clmCount);
        try {
            count = this.rs.getInt(1);
            this.getClose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("column " + count);
        return count;
    }


    public String[] getLine(String[] where) {
        String[] line = {"ddd", "rtt"};

        return line;
    }

    public void close() {
        try {
            this.psGet.close();
            this.psSet.close();
            this.rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void allClose() {
        try {
            this.con.close();
            this.psGet.close();
            this.psSet.close();
            this.rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setClose() {
        try {
            this.psSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getClose() {
        try {
            this.psGet.close();
            this.rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rsClose() {

        try {
            this.rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
