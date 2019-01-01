package tjava.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class TMainFrame extends JFrame {



	public static String[] panelNames =
		{"mainPanel", "siritoriAddPanel"};
	public static int frameH = 500;
	public static int frameW = 1000;

	static SiritoriAddPanel smp;
	static TestPanel tp;



	/**
	 * Launch the application.
	 */
	public static void guiRun() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TMainFrame tFrame = new TMainFrame();
					tFrame.setTitle("tosisoft");
					tFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public TMainFrame() {
		smp = new SiritoriAddPanel();
		tp = new TestPanel();
		this.add(smp);smp.setVisible(false);
		this.add(tp);tp.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);


	}

}
