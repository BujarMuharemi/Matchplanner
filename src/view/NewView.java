package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import model.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;

public class NewView extends JDialog {

	private static final long serialVersionUID = 1L;	
	private JPanel contentPane;
	private JTextField textField;
	private int nTeams = 0;
	static NewView frame;
	static Hauptview main = new Hauptview();

	NewViewTabelle tabelle = new NewViewTabelle(4);
	
	private boolean erstellt = false;
	
	public boolean getErstellt() {
		return erstellt;
	}
	
	public void setErstellt(boolean b) {
		erstellt=b;
	}
	
	public Mannschaften[] getTeams() {
		return tabelle.getTeams();
	}

	public int getSpieltage() {
		return tabelle.getSpieltage();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new NewView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewView() {
		setResizable(false);
		setDefaultCloseOperation(1); // schließt nur dieses Fenster
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 250, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.NORTH);

		JSplitPane splitPane = new JSplitPane();
		panel_2.add(splitPane);

		JLabel lblAnzahlDerTeams = new JLabel("Anzahl der Teams");
		splitPane.setLeftComponent(lblAnzahlDerTeams);

		// ------------------------------
		JPanel panel_1 = new JPanel();

		tabelle = new NewViewTabelle(4);

		JTable table = new JTable(tabelle);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setViewportView(table);

		panel_1.add(scrollPane);

		table.setToolTipText("Namen der Teams eingeben");

		// Text Feld
		textField = new JTextField();
		textField.setText("4");
		textField.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Eingabe vom Nutzer überprüfen
				try {
					nTeams = Integer.parseInt(textField.getText());

					if (nTeams % 2 == 0 && nTeams >= 4) {
						tabelle.addSize(nTeams);
					} else {
						JOptionPane.showMessageDialog(null,
								(nTeams < 4) ? "Mindestwert 4 Teams" : "Nur gerade Zahlen eingeben !");

						textField.setText("");
					}

				} catch (NumberFormatException excep) {
					JOptionPane.showMessageDialog(null, "Nur ganze Zahlen eingeben !");
					textField.setText("");
				}

			}
		});
		splitPane.setRightComponent(textField);
		textField.setColumns(10);

		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridheight = 2;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		contentPane.add(panel_1, gbc_panel_1);

		// btnErstellen
		JButton btnErstellen = new JButton("Erstellen");
		btnErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Tabelle wird weitergegeben
				// und in Hauptview neue Tabelle erstellt!

				// tabelle.sendTeams();
				erstellt=true;
				main.updateTeams();
				dispose();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		contentPane.add(btnErstellen, gbc_btnNewButton);
	}
}
