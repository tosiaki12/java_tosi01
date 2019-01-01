package tjava.util;

public class SQLUtil {

	public static void rogp(Object rogText) {
		if(tjavafile.TMain.isDebug == true) {
			System.out.println(rogText);
		}
	}

	/**
	 * makeSELECT
	 * @param column 列名
	 * @param table テーブル名
	 * @param where 条件
	 * @param wildBefore ワイルドカードを使用してレコードを検索する
	 * @param wildAfter ワイルドカードを使用してレコードを検索する
	 * @return SQL
	 */
	public static String makeSELECT
	(String column, String table, String where, String wildBefore, String wildAfter) {

		String and = "%";

		if (wildBefore ==null) {
			wildBefore = "";
		}
		if (wildAfter == null) {
			wildAfter = "";
		}
		if (!(wildBefore == null || wildAfter == null)){
			and = "";
		}

		String sqlafter = ""
				+ "LIKE   '"+ wildBefore +and+ wildAfter +"'";
		String sql = ""
				+ "SELECT "+ column +" "
				+ "FROM "+ table  +" "
				+ "WHERE "+ where  +" ";
		String sqlfinal = "";
		if (!(wildBefore == null || wildAfter == null)){
			sqlfinal = sql + sqlafter;
		}
		sqlfinal = sql;
		System.out.println(sqlfinal + " -sql maked  --makeSELECT");
		rogp("output" + sqlfinal);
		return sqlfinal;
	}

	public static String makeINSERT
	(String table, String column, Object ... data ) {
		String qu = "'";
		String ka = ",";
		String da = "";

		for(int i = 0;i<data.length;i++){

			da = da + qu + data[i] + qu;
			if(i < data.length-1){
				da = da + ka;
			}
		}
		System.out.println(da);
		String sql = ""
				+ "INSERT "
				+ "INTO   "+table+" ("+column+")"
				+ "VALUES ("+da+")";
		System.out.println(sql);
		return sql;
	}



	public static String makeINSERTOther
	(String addTable, String addColumn, String beforeColumn, String beforeTable, String where ) {

		String sql = ""
				+ "INSERT "
				+ "INTO  '"+addTable+"' ( "+addColumn+" ) "
				+ "SELECT "+beforeColumn+" "
				+ "FROM  '"+beforeTable+"' "
				+ "WHERE  "+where+"";
		return sql;
	}


	public static String makeUPDATE
	(String table, String data, String where) {
		String sql = ""
				+ "UPDATE "+table+" "
				+ "SET    "+data+" "
				+ "WHERE  "+where+" ";
		return sql;

	}

	public static String makeDELETE
	(String table, String where) {
		String sql = "sql";

		if(where.equals(null)) {
			sql = "DELETE FROM "+table;

		} else {

			sql = ""
					+ "DELETE FROM "+table+" "
					+ ""+where+"";
		}

		return sql;
	}


}
