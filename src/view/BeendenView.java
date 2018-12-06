package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BeendenView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static BeendenView frame = new BeendenView();
	private int buttonChoice = 0;
	
	private boolean beendenWas=false;
	
	public boolean getBeendenWas() {
		return beendenWas;
	}

	public int getButtonChoice() {
		return buttonChoice;
	}
	
	public int setButtonChoice(int n) {
		return buttonChoice=n;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BeendenView() {
		setDefaultCloseOperation(1);
		setBounds(100, 100, 500, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblIhrMatchplanIst = new JLabel("Ihr Matchplan ist nicht gespeichert !");
		panel.add(lblIhrMatchplanIst);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JLabel lblWollenSieSpeichern = new JLabel("Wollen Sie speichern ?");
		lblWollenSieSpeichern.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblWollenSieSpeichern, BorderLayout.NORTH);

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnJa = new JButton("Ja");
		btnJa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonChoice = 1;
				beendenWas=true;
				dispose();
				// Methode aufrufen um flaggs zurückzu setzten!!
			}
		});
		panel_2.add(btnJa);

		JButton btnNein = new JButton("Nein");
		btnNein.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				System.exit(0);
				buttonChoice = 0;
				beendenWas=true;
				dispose();
			}
		});
		panel_2.add(btnNein);

		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Das Fenster beendenview schließen und programm weiter arbeiten können!
				buttonChoice = -1;
				beendenWas=true;
				dispose();
			}
		});
		panel_2.add(btnAbbrechen);

	}

}
