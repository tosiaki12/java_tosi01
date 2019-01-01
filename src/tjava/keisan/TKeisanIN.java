package tjava.keisan;


public class TKeisanIN {
	static String KeisanString;
	static String[] KeisanArray;
	static String[] KeisanStrAry;
	public static void IN () {
		System.out.println("入力");
		KeisanString = tjava.sysUtil.TUtil.tScanStr();
		System.out.println("式=" + KeisanString);
	}
	public static String[] ArrayConvert () {


		KeisanStrAry = KeisanString.split("");
		//List<String> splStrList = Arrays.asList(KeisanStrAry);
		//List<String> newList = new ArrayList<>(splStrList);



		//KeisanArray = KeisanString.split(" ");
		for (String v : KeisanArray) {
			System.out.println(v);
		}
		return KeisanArray;
	}
}
