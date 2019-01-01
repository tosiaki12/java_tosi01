package tjava.test;

import java.util.List;

import tjava.siritori.Siritori;
import tjava.util.DBUtilSQLite;

public class SQLTester {

	public static DBUtilSQLite dbutest = new DBUtilSQLite();

	public static void DBTest(){
		String url = "jdbc:sqlite:A:/1A/siritoridb.db";
		String sql = "SELECT * FROM 'り'";
		dbutest.DBConnect(url);

		System.out.println("getstart");
		System.out.println("RE");


		@SuppressWarnings("unchecked")
		List<String> ttes = (List<String>) dbutest.getResGenList(sql, "Word", Object.class, "null");
		System.out.println("checkddddd");
		System.out.println(ttes.get(0));
		for(int i = 0;i<ttes.size();i++){
			System.out.println("tessss "+ttes.get(i));
		}
		Siritori stest = new Siritori();
		stest.siritoriLogWordChk("り");


	}
}
