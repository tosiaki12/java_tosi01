package tjava.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tjava.siritori.Siritori;

public class SiritoriAddPanel extends JPanel {

	public String[] sWords = new String[3];
	public String[] fieldName = {"firstTxtField, wordTxtField, lastTxtField"};
	Siritori siritori1 = new Siritori();

	JTextField firstTxtField;
	JTextField wordTxtField;
	JTextField lastTxtField;
	/**
	 * Create the panel.
	 */
	public SiritoriAddPanel() {
		this.setName("smp");
		this.setLayout(null);
		this.setSize(TMainFrame.frameW, TMainFrame.frameH);   //パネルの大きさ

		JLabel titleLabel = new JLabel("SiritoriAddWord");
		titleLabel.setBounds(31, 25, 145, 13);
		add(titleLabel);

		firstTxtField = new JTextField();
		firstTxtField.setBounds(38, 97, 77, 19);
		firstTxtField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				SiritoriAddPanel.getTextField(firstTxtField);
			}
		});
		add(firstTxtField);
		firstTxtField.setColumns(10);

		wordTxtField = new JTextField();
		wordTxtField.setBounds(175, 97, 96, 19);
		wordTxtField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				SiritoriAddPanel.getTextField(wordTxtField);
			}
		});
		add(wordTxtField);
		wordTxtField.setColumns(10);

		lastTxtField = new JTextField();
		lastTxtField.setBounds(336, 97, 77, 19);
		lastTxtField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				SiritoriAddPanel.getTextField(lastTxtField);
			}
		});
		add(lastTxtField);
		lastTxtField.setColumns(10);

		JLabel lblNewLabela = new JLabel("First Char");
		lblNewLabela.setBounds(48, 126, 67, 13);
		add(lblNewLabela);

		JLabel lblNewLabel_2 = new JLabel("Word");
		lblNewLabel_2.setBounds(185, 126, 86, 13);
		add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Last Char");
		lblNewLabel_3.setBounds(346, 126, 86, 13);
		add(lblNewLabel_3);

		JButton btnNewButton = new JButton("button");
		btnNewButton.setBounds(31, 165, 86, 21);

		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				changePanel(TMainFrame.tp);
			}
		});
		add(btnNewButton);


		JButton btnAdd = new JButton("add");
		btnAdd.setBounds(180, 165, 91, 21);
		btnAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addWords();
			}
		});
		add(btnAdd);

	}
	public static String getTextField(JTextField f){
		String text = f.getText();
		System.out.println(text);
		f.setText("");
		return text;
	}
	public void addWords(){
		sWords[0] = getTextField(firstTxtField);
		sWords[1] = getTextField(wordTxtField);
		sWords[2] = getTextField(lastTxtField);
		siritori1.addManual(sWords);
	}
	public void changePanel(JPanel panel) {
		this.setVisible(false);
		panel.setVisible(true);
	}
}
