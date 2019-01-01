package tjava.keisan;


public class TKeisan {


	public static String[] keisanBefore;
	public static int runint;

	public static void TKeisanRun () {
		System.out.println("start");
		tjava.keisan.TKeisanIN.IN();
		String[] KeisanArray = tjava.keisan.TKeisanIN.ArrayConvert();
		tjava.keisan.TKeisan.keisanBefore = KeisanArray;
		tjava.keisan.TKeisan.Keisan();
	}



	public static void Keisan () {
		for (int i=0;i<keisanBefore.length;i++) {


			System.out.println("length=" + keisanBefore.length);
			System.out.println("1stfor run" + i);



			if (keisanBefore[i].equals("*") || keisanBefore[i].equals("/")) {
				int x = i;
				x--;//************************************************-1
				float aa = Float.parseFloat(keisanBefore[x]);
				x++;//*************************************************0
				x++;//************************************************+1
				float ab = Float.parseFloat(keisanBefore[x]);
				x--;//*************************************************0
				float ans = 0;
				if (keisanBefore[i].equals("*")) {
					ans = aa * ab;
				}
				if (keisanBefore[i].equals("/")) {
					ans = aa / ab;
				}
				String ansstring = String.valueOf(ans);
				x--;//************************************************-1
				keisanBefore[x] = ansstring;
				System.out.println("1stfor ka" + x);
				x++;//*************************************************0
				System.out.println("1stfor long" + keisanBefore.length);
				keisanBefore[x] = "none";
				x++;//************************************************+1
				keisanBefore[x] = "none";
				System.out.println("1stfor end");
				KeisanLeset ();
			} else {
				System.out.println("next");
			}
			System.out.println("mainrun");
			i = 0;
		}
	}
	public static void KeisanLeset () {
		int notnone = 0;
		for (String rpA : keisanBefore) {
			if (!(rpA.equals("none"))) {
				notnone++;
			}
		}
		System.out.println("1stforEnd word " + notnone);
		String[] keisanAfter = new String[notnone];
		int d = 0;
		for (int g=0;g<keisanBefore.length;g++) {
			if (keisanBefore[g].equals("none")) {
				System.out.println("noned");
				//g++;
			} else {
				keisanAfter[d] = keisanBefore[g];
				System.out.println("addarray " + g);
				d++;
			}
		}
		keisanBefore = keisanAfter;
		System.out.println("keisansendrun");
		//keisanAfter = null;
		// Before 表示 ////////////////////////////////////////////////
		for (String show : keisanBefore) {
			System.out.println(show);
		}
		////////////////////////////////////////////////////////////////
		runint++;
		System.out.println("runned to " + runint);
	}
}
