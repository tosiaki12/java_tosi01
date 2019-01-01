package tjava.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public TestPanel() {
		this.setName("tp");
		this.setLayout(null);
		this.setSize(TMainFrame.frameW, TMainFrame.frameH);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(40, 30, 109, 21);
		add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				changePanel(TMainFrame.smp);
			}
		});

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(161, 34, 88, 13);
		lblNewLabel.setText("eee");
		long iu = 2;
		for(int i = 0;i<30000;i++){
			iu = iu*2;
		}
		System.out.println(iu);
		lblNewLabel.setText("ssdfdfdsa");
		add(lblNewLabel);

	}

	public void changePanel(JPanel panel) {
		this.setVisible(false);
		panel.setVisible(true);
	}
}
