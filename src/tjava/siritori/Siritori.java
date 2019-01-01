package tjava.siritori;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import tjava.sysUtil.TUtil;
import tjava.test.TestWriter;
import tjava.util.DBUtilSQLite;
import tjava.util.SQLUtil;
import tjavafile.TMain;



public class Siritori {


	//Start    2018_04_19
	//Complete 2018_05_03
	public static final String VERSION  = "TosiSiritori_R_1_1";
	public static final String START    = "Start    2018_04_19";
	public static final String COMPLETE = "Complete 2018_05_03";
	public static  Map<String, Integer> changeLog = new HashMap<String, Integer>();



	public static final String ADDTABLE = "AddWords";
	public static final String LOGTABLE = "WordLog";

	public static final String USER = "User";
	public static final String CP   = "CP";

	public static final String FKEY = "firstWordKey";
	public static final String WKEY = "wordKey";
	public static final String LKEY = "lastWordKey";
	public static final String IKEY = "wordOldIdKey";

	DBUtilSQLite db1;
	private int logId = 0;
	private int addId = 0;

	public static Siritori siritori1;
	static TestWriter tw = new TestWriter();
	Map<String, String> wordMap = new HashMap<String, String>();
	//Siritori.Siritoridata words = new Siritori.Siritoridata();

	private static void printd(Object o, boolean isDebug) {
		TMain.printdMain(o, isDebug, "Siritori");
	}

	public static void siritoriRunner() {
		run();
	}

	public static void siritoriChangeLog(){
		changeLog.put("start", 2018_04_19);
		changeLog.put("1.0 complete", 2018_05_03);
		changeLog.put("2.0(map) complete", 2018_07_07);
		changeLog.put("version", 2_0);
	}

	public static void run() {
		printd("SiritoriStart", false);
		printd("VERSION  : " + VERSION ,  false);
		printd("START    : " + START ,    false);
		printd("COMPLETE : " + COMPLETE , false);
		siritoriChangeLog();
		printd("DEVELOPMENT",false);
		tjava.sysUtil.TUtil.tScanStr();

		siritori1 = new tjava.siritori.Siritori();
		String sysNext = siritori1.SiritoriStarter();
		siritori1.nomalRun(sysNext);

		printd("roop out  --run",false);
		siritori1.siritoriDicAdd();
		siritori1.deleteTableData(Siritori.LOGTABLE);
		siritori1.deleteTableData(Siritori.ADDTABLE);
		System.out.println("stop");
	}


	public static void test() {
		printd("STest",false);
		Siritori sctest = new tjava.siritori.Siritori();
		sctest.dicLongChk("あ");
		sctest.addManual();
	}

	////////////////////////////////////////////////////////////////////////////////////

	public Siritori() {
		String url = "jdbc:sqlite:A:/1A/siritoridb.db";
		printd(url,true);
		this.db1 = new tjava.util.DBUtilSQLite();
		this.db1.DBConnect(url);
	}

	public void nomalRun(String word){
		int i;
		for(i = 0;i<1000;i++){
			word = this.siritoriUser(word);
			word = this.siritoriCP(word);
		}
		i++;
		printd(i, true);
	}

	public String SiritoriStarter() {
		Map<String, String> sMap = new HashMap<String, String>();
		printd("start",false);
		printd("しりとり,り", false);
		this.setWords(sMap, "し","しりとり","り", 1);
		String nextChar = sMap.get(LKEY);
		this.SiritoriDBLogger(sMap);
		return nextChar;
	}


	public String siritoriUser(String nextChar) {
		assert nextChar != "ん";

		Map<String, String> userMap = new HashMap<String, String>();

		System.out.println("次は :" + nextChar);
		System.out.println("example: リソース,す");
		String[] userWords = TUtil.tScanStrSpl(",");
		if (userWords[0].equals("stop")) {
			this.Stopper();
		}
		this.setWords(userMap, nextChar, userWords[0], userWords[1], -10);
		this.printWords(userMap);
		Integer chk = this.DBLogChecker(userMap.get(WKEY));
		if(chk != 0){
			printd("reStart " + nextChar, false);
			this.siritoriUser(nextChar);
		}
		Integer dicChk = this.dicChecker(nextChar, userWords[0]);

		if(dicChk != 0){   //単語表にあったら

			String strid = dicChk.toString();
			/*----*/printd(strid+" strid  --SiritoriUser", true);
			userMap.put(IKEY, strid);

		} else {              //単語表になかったら
			userMap.put(IKEY, "-1");
			printd("newWords", false);
			this.SiritoriDBAdd(userMap);
		}
		this.SiritoriDBLogger(userMap);
		return userMap.get(LKEY);
	}


	/**
	 * 2018/06/28
	 * @param nextChar
	 * @return
	 */
	public String siritoriCP(String nextChar){
		Map<String, String> cpMap = new HashMap<String, String>();

		List<Integer> wordList = this.siritoriLogWordChk(nextChar);
		int wordCount= wordList.size();
		wordCount--;
		printd(wordCount+" wordCount", true);
		if(wordCount == 0){
			printd("word not found", false);
			TUtil.tScanStr();
			this.Stopper();
		}
		int rundomword = this.random(wordCount);
		printd(wordCount+ " aa  " + rundomword,true);
		for(Integer i : wordList){
			printd(i,true);
		}
		int worddicId = wordList.get(rundomword);
		printd("wordId  "+worddicId,true);

		String getWordSQL = SQLUtil.makeSELECT("*", nextChar, "id = '"+worddicId+"'", null, null);
		printd(getWordSQL, true);
		String word      = db1.getResGen(getWordSQL, "Word", String.class, "wordNull");
		String lastWord  = db1.getResGen(getWordSQL, "LastChar", String.class, "LastNull");
		Integer wordid    = db1.getResGen(getWordSQL, "id", Integer.class, "idNull");
		this.setWords(cpMap, nextChar, word, lastWord, wordid);
		this.SiritoriDBLogger(cpMap);
		printd("word : "+word+" : "+lastWord+"  ",false);
		return lastWord;
	}


	public void setWords(Map<String, String> map, String fWord, String word, String lWord, Integer dicId){
		if(fWord.length() != 1 || lWord.length() != 1){
			throw new ClassCastException("invalid word count");
		}
		String strId = dicId.toString();
		map.put(FKEY, fWord);
		map.put(WKEY, word);
		map.put(LKEY, lWord);
		map.put(IKEY, strId);
		printd("fWord : "+fWord+" Word : "+word+" lWord : "+lWord+" dicId : "+strId+" ", false);
	}

	public void setWords(Map<String, String> addmap, Map<String, String> map){
		String fWord = map.get(FKEY);
		String lWord = map.get(LKEY);
		String strId = map.get(IKEY);
		Integer id = Integer.parseInt(strId);
		this.setWords(addmap, fWord, map.get(WKEY), lWord, id);
	}

	public void printWords(Map<String, String> map){
		printd(
				"fWord : "+map.get (FKEY)+
				" Word : "+map.get (WKEY)+
				" lWord : "+map.get(LKEY)+
				" dicId : "+map.get(IKEY)+
				" ", false);
	}



	public void SiritoriDBLogger(Map<String, String> map) {
		this.logId++;
		this.setWords(this.wordMap, map);
		String addLogSQL = SQLUtil.makeINSERT(
				LOGTABLE, "id, dicId, FirstChar, LastChar, Word",
				this.logId, map.get(IKEY), map.get(FKEY), map.get(LKEY), map.get(WKEY));
		printd(addLogSQL, true);
		printd("id = " + this.logId,false);
		printd("logSQL  :"+addLogSQL+"  --SiritoriDBLogger", true);
		this.db1.setData(addLogSQL);
	}

	public void SiritoriDBAdd(Map<String, String> map) {
		this.addId++;
		String addDBSQL = ""
				+ "INSERT INTO '"+ADDTABLE+"' (id, FirstChar, LastChar, Word) "
				+ "VALUES ('"+this.addId+"', '"+map.get(FKEY)+"', '"+map.get(LKEY)+"', '"+map.get(WKEY)+"')";
		this.db1.setData(addDBSQL);
	}


	public void siritoriDicAdd() {
		int tes = 0;
		tes = this.dicLongChk(ADDTABLE);
		printd("column " + tes + " --siritoriDicAdd", false);
		int cval = tes;
		cval++;
		for ( int i = 1 ; i < cval ; i++) {
			String checkSQL ="SELECT * FROM '"+ADDTABLE+"' WHERE rowid ='"+i+"'";
			db1.getData(checkSQL);
			String[] sss = db1.rsConvStr("FirstChar");
			String sa = sss[0];
			int tcun = db1.getclmCount(sa);
			tcun++;
			String conid = "UPDATE '"+ADDTABLE+"' SET id = '"+tcun+"' WHERE rowid = '"+i+"'";
			db1.setData(conid);
			String copySQL = ""
					+ "INSERT INTO '"+sa+"' (id, FirstChar, LastChar, Word, IsLastFirst, Other) "
					+ "SELECT * "
					+ "FROM '"+ADDTABLE+"' "
					+ "WHERE rowid = '"+i+"'";
			db1.setData(copySQL);
			db1.getClose();
		}
	}


	public void addManual(String[] words){
		printd("firstChar,word,lastChar  --addManual", false);

		Integer isNew = this.dicChecker(words[0], words[1]);
		if (isNew == 0) {
			this.setWords(this.wordMap, words[0], words[1], words[2], -1);
			this.addDic(wordMap);
		} else {
			printd("old  --addManual", false);
		}
	}

	public void addManual() {
		String[] words = TUtil.tScanStrSpl(",");
		this.addManual(words);
	}

	public int dicLongChk(String table) {
		String clmCount =
				"SELECT COUNT(*) FROM '"+table+"'";
		int clmLong = 0;
		db1.getData(clmCount);
		try {
			clmLong = db1.rs.getInt(1);
			db1.rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		printd("columnLong " + clmLong,false);
		return clmLong;
	}


	/**
	 *
	 * @param tableLong 追加するテーブルの単語の数
	 *
	 */
	public void addDic(Map<String, String> map) {
		int tableLong = this.dicLongChk(map.get(FKEY));
		int tableId = tableLong;
		tableId++;
		String addSQL =""
				+ "INSERT INTO '"+map.get(FKEY)+"' (id, FirstChar, LastChar, Word) "
				+ "VALUES ('"+tableId+"', '"+map.get(FKEY)+"', '"+map.get(LKEY)+"', '"+map.get(WKEY)+"')";
		db1.setData(addSQL);
		System.out.println("dicSet --addDic");
	}


	/**
	 * 2018/06/28
	 * @param nextChar
	 * @return
	 *
	 * logWordCount
	 * dicWordCount
	 */
	public List<Integer> siritoriLogWordChk(String nextChar){
		String getrogidSQL = SQLUtil.makeSELECT
				("dicId", LOGTABLE, "FirstChar = '"+nextChar+"' AND dicId > 0 ", null, null);
		String getidSQL    = SQLUtil.makeSELECT
				("id", nextChar, "FirstChar = '"+nextChar+"'", null, null);
		printd(getrogidSQL,true);
		printd(getidSQL,true);
		@SuppressWarnings("unchecked")
		List<Integer> logWordCount = (List<Integer>) db1.getResGenList(getrogidSQL, "dicId", Object.class, 0);
		@SuppressWarnings("unchecked")
		List<Integer> dicWordCount = (List<Integer>) db1.getResGenList(getidSQL,    "id", Object.class, 0);
		logWordCount.add(0, 0);
		dicWordCount.add(0, 0);

		for(int i = 0;i<logWordCount.size();i++){
			Object lo = logWordCount.get(i);
			boolean isDelete = dicWordCount.remove(lo);
			printd(isDelete,true);


			for(int i2 = 0;i2<dicWordCount.size();i2++){
				printd("rooplog2    "+i2+"log2 "+dicWordCount.get(i2),true);
			}

		}

		return dicWordCount;
	}

	public int random(int ranLong) {
		Random ran = new Random();
		int ran2 = ran.nextInt(ranLong)+1;
		ran = null;
		return ran2;
	}




	public Integer dicChecker(String table, String word) {
		/*-----------------------------------------------------*/printd(table + word + "   --DBChecker", true);
		String checkSQL    = "SELECT * FROM '"+table+"' WHERE Word ='"+word+"'";
		Integer dicResult = db1.getResGen(checkSQL, "id", Integer.class, 0);
		printd(checkSQL, true);
		if(dicResult != 0){
			printd("dicTrue", false); //trueなら重複
		}
		return dicResult;
	}

	public Integer DBLogChecker(String word) {
		String checkLogSQL = "SELECT * FROM '"+LOGTABLE+"' WHERE Word ='"+word+"'";
		System.out.println("DBLogCheck");
		Integer rogResult = db1.getResGen(checkLogSQL, "id", Integer.class, 0);
		if (rogResult.equals(0)) {
			printd("Find log Old words    --DBLogChecker",false);
			printd("rogTrue", false);  //trueなら重複
		} else{
			printd("Not found log         --DBLogChecker",false);
		}
		return rogResult;
	}



	public void deleteTableData(String table) {
		printd("deleteFrom"+table,true);
		String delTableSQL =
				"DELETE FROM '"+table+"'";
		db1.setData(delTableSQL);
	}




	public void Stopper() {
		System.out.println("isStop");
		System.out.println("stop == 0, continue == 1, save == 2, delete == 3, saveexit == 4");
		int ans = TUtil.tScanInt();
		if (!(ans == 0 || ans == 1 || ans == 2 || ans == 3 || ans == 4)) {
			this.Stopper();
		}
		switch (ans) {
		case 0:
			System.out.println("EXIT");
			System.exit(0);
			break;
		case 1:
			System.out.println("RESTART");
			//db1.allClose();
			db1 = null;
			System.gc();
			run();
			break;
		case 2:
			System.out.println("SAVE newWords");
			this.siritoriDicAdd();
			System.out.println("DELETE logData");
			this.deleteTableData(LOGTABLE);
			this.deleteTableData(ADDTABLE);
			System.out.println("EXIT");
			System.exit(0);
			break;
		case 3:
			System.out.println("DELETE logData");
			this.deleteTableData(LOGTABLE);
			this.deleteTableData(ADDTABLE);
			System.out.println("EXIT");
			System.exit(0);
			break;
		case 4:
			System.out.println("SAVE newWords");
			this.siritoriDicAdd();
			System.out.println("DELETE logData");
			this.deleteTableData(LOGTABLE);
			this.deleteTableData(ADDTABLE);
			db1 = null;
			tjavafile.TMain.run();
			break;
		}
	}


	/**
	 * @author user
	 *
	 */
	class Siritoridata{

		public Map<String, String> wMap = new HashMap<String, String>();

		String firstW;
		String word;
		String lastW;
		Integer dicId;
		Integer count;
		String fnull = "FirstNull";
		String wnull = "WordNull";
		String lnull = "LastNull";
		String dnull = "-10";

		Siritoridata(){
			wMap.put(FKEY, fnull);
			wMap.put(WKEY,  wnull);
			wMap.put(LKEY,  lnull);
			wMap.put(IKEY, dnull);
			this.apply();
		}

		public void apply(){
			wordMap.putAll(wMap);
		}

		public void dataLeset(){
			wMap.put(FKEY, fnull);
			wMap.put(WKEY,  wnull);
			wMap.put(LKEY,  lnull);
			wMap.put(IKEY, dnull);
			this.apply();
		}

		public String[] rData(String ... rkey){
			List<String> tempList = new ArrayList<>();
			for(String r : rkey){
				tempList.add(wMap.get(r));
				printd(r, true);
			}
			String[] rw = tempList.toArray(new String[tempList.size()]);
			this.apply();
			return rw;
		}

		public String getWord(String wKey){
			String rWord = this.wMap.get(wKey);

			return rWord;
		}

		public void setWord(String wKey, String addWord){
			if(this.wMap.get(wKey) == null){
				printd("null", true);
			}
			this.apply();
			this.wMap.put(wKey, addWord);
		}


		public void setWords(String fWord, String word, String lWord){
			if(fWord.length() != 1 || lWord.length() != 1){
				throw new ClassCastException("invalid word count");

			}
			this.apply();
			this.wMap.put(FKEY, fWord);
		}

		public void setIds(String wKey, int id){
			if(wKey == IKEY){
				this.dicId = id;
				this.wMap.put(IKEY, this.dicId.toString());
				this.apply();
			}
		}

		public int getIds(String wKey){
			int rId = -1;
			if(wKey.equals(IKEY)){
				rId = this.dicId;
			}
			return rId;
		}

		public Map<String, String> getMap(){

			return this.wMap;
		}
	}




}
