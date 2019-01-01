package tjava.keisan;

public class Keisan {
	int keisanX;
	int keisanY;
	int keisanAns;
	int keisanHow;

	void input () {
		System.out.println("start");
		try {
			/////////////////////////////////////////////////////////////////////////////
			System.out.println("足し算=1,引き算=2,掛け算=3,割り算=4,終了=0");
			this.keisanHow = new java.util.Scanner ( System.in ).nextInt ();
			System.out.println("計算 =" + keisanHow);
			if (this.keisanHow == 0) {
				this.keisanstop();
			}
			/////////////////////////////////////////////////////////////////////////////
			//---------------------------------------------------------------------------
			/////////////////////////////////////////////////////////////////////////////
			System.out.println("1つめ");
			this.keisanX = new java.util.Scanner ( System.in ).nextInt ();
			System.out.println("1つめ = " + keisanX);
			/////////////////////////////////////////////////////////////////////////////
			//---------------------------------------------------------------------------
			/////////////////////////////////////////////////////////////////////////////
			System.out.println("2つめ");
			this.keisanY = new java.util.Scanner ( System.in ).nextInt();
			System.out.println("2つめ = " + keisanY);
			/////////////////////////////////////////////////////////////////////////////

		}  catch ( Exception e ) { }
		this.keisan();
	}

	void keisan () {
		switch (keisanHow) {
		case 0:
			System.out.println("計算プログラムストップ");
			break;
		case 1:
			System.out.println("------tasizan開始------");
			keisanAns = keisanX + keisanY;
			System.out.println("答え =" + keisanAns);
			this.input();
			break;
		case 2:
			System.out.println("------hikizan開始------");
			keisanAns = keisanX - keisanY;
			System.out.println("答え =" + keisanAns);
			this.input();
			break;
		case 3:
			System.out.println("------kakezan開始------");
			keisanAns = keisanX * keisanY;
			System.out.println("答え =" + keisanAns);
			this.input();
			break;
		case 4:
			System.out.println("------warizan開始------");
			keisanAns = keisanX / keisanY;
			System.out.println("答え =" + keisanAns);
			this.input();
			break;
		}
	}

	void keisanstop () {
		System.out.println("STOP");
	}
}
