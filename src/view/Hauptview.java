package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.*;
import model.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.WindowFocusListener;

public class Hauptview {

	int tabelleNum = 0;
	private JFrame frame;
	boolean gespeichert = false;
	boolean geoffnet = false;
	private Mannschaften[] teams;

	public Daten b = new Daten(5);
	beendenview beendenview = new beendenview();
	NewView newView = new NewView();
	Tabelle table;

	TabelleSpielTage spieltageData = new TabelleSpielTage(newView.getTeams());

	// Mannschaften setten
		
	public void setMan(Mannschaften[] m) {
		teams = m;
		
		for (int i = 0; i < m.length; i++) {
			// System.out.println(m[i].getName());
		}
		// System.out.println("setMan"+getSpieltage());
		// tabel.addSize(getSpieltage());
		// spielTage = getSpieltage();

		// table.addSize(spielTage);
		// frame.repaint();
		updateTeams();
		// initialize();

	}

	public void showTeams() {
		for (Mannschaften m : teams) {
			System.out.println(m.getName());
		}
	}

	public void updateTeams() {
		System.out.println("updated Teams");
		spieltageData = new TabelleSpielTage(newView.getTeams());
		spieltageData.addTeam(newView.getTeams());
		spieltageData = new TabelleSpielTage(teams);
		frame.repaint();
	}

	// spieltage wird berrechnet und weiter gegeben
	public int getSpieltage() {
		int n = (teams != null) ? teams.length : 4;
		return (n * (n - 1)) / (n / 2);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hauptview window = new Hauptview();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Hauptview() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {
				table.addSize(newView.getSpieltage());
			}

			public void windowLostFocus(WindowEvent arg0) {
				table.addSize(newView.getSpieltage());
			}
		});
		frame.setResizable(false);
		frame.setBounds(600, 200, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Menueleiste wird erstellt mit den bestimmten Komponenten!
		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);

		// Menubar Datei erstellt
		JMenu mndatei = new JMenu("Datei");
		menubar.add(mndatei);

		// Menubar Extra erstellt
		JMenu mnextra = new JMenu("Extra");
		menubar.add(mnextra);

		// Elemente der Menubar werden erstellt und den jewaligen Menu elemente
		// hinzugefuegt
		JMenuItem mnitNeu = new JMenuItem("Neu");
		mnitNeu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame neuframe = new JFrame();
				
				newView.setVisible(true);
			}
		});
		mndatei.add(mnitNeu);

		JMenuItem mnitOffnen = new JMenuItem("Öffnen");
		mnitOffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mndatei.add(mnitOffnen);

		if (geoffnet) {
			JMenuItem mnitSpeichern = new JMenuItem("Speichern");
			mnitSpeichern.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					/*
					 * JFileChooser fc = new JFileChooser(); FileFilter filter = new
					 * FileNameExtensionFilter("XLS File","xls"); fc.setFileFilter(filter);
					 */
				}
			});
			mndatei.add(mnitSpeichern);

			JMenuItem mnitSpeichernunter = new JMenuItem("Speichern unter");
			mnitSpeichernunter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, " geÃ¶ffneten Spielplan als neue Datei speichern ");
				}
			});
			mndatei.add(mnitSpeichernunter);

			JMenuItem mnitMannschaftba = new JMenuItem("Mannschaft bearbeiten");
			mnitMannschaftba.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, " Manschaften verï¿½ndern ");
				}
			});
			mnextra.add(mnitMannschaftba);

			JMenuItem mnitSpieltag = new JMenuItem("Spieltag bearbeiten");
			mnitSpieltag.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, " Spieltage festlegen / verï¿½ndern ");
				}
			});

			mnextra.add(mnitSpieltag);
			frame.repaint();
		}

		JMenuItem mnitBeenden = new JMenuItem("Beenden");
		mnitBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gespeichert == true) {
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				} else {
					beendenview.setVisible(true);
				}
			}
		});
		mndatei.add(mnitBeenden);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 700, 80, 0 };
		gridBagLayout.rowHeights = new int[] { 539, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JPanel Zentralesicht = new JPanel();
		GridBagConstraints gbc_Zentralesicht = new GridBagConstraints();
		gbc_Zentralesicht.fill = GridBagConstraints.BOTH;
		gbc_Zentralesicht.insets = new Insets(0, 0, 0, 5);
		gbc_Zentralesicht.gridx = 0;
		gbc_Zentralesicht.gridy = 0;
		frame.getContentPane().add(Zentralesicht, gbc_Zentralesicht);

		GridBagConstraints gbc_SpieltagAnsicht = new GridBagConstraints();
		gbc_SpieltagAnsicht.fill = GridBagConstraints.BOTH;
		gbc_SpieltagAnsicht.gridx = 1;
		gbc_SpieltagAnsicht.gridy = 0;

		// tabel.addSize(spielTage);

		// Tabellen werden erstellt
		table = new Tabelle(b.getN());
		spieltageData = new TabelleSpielTage(newView.getTeams());
		spieltageData.addTeam(newView.getTeams());
		System.out.println("row count"+spieltageData.getRowCount());

		JTable tabelle = new JTable(table);

		JTable spielTageTabelle = new JTable(spieltageData);

		JScrollPane SpielTagScrollen = new JScrollPane(tabelle);
		JScrollPane ZentralScrollen = new JScrollPane(spielTageTabelle);

		JPanel SpieltagAnsicht = new JPanel();

		frame.getContentPane().add(SpieltagAnsicht, gbc_SpieltagAnsicht);
		SpieltagAnsicht.setLayout(new BorderLayout(0, 0));

		SpieltagAnsicht.add(SpielTagScrollen);

		Zentralesicht.add(ZentralScrollen);

//		System.out.println("DatenGet" + b.getN());
		int a = b.getN();
		table.addSize(a);		
		frame.repaint();
		// System.out.println("Folgende Teams bekommen:");
		// if (teams != null) {
		// for (int i = 0; i < teams.length; i++) {
		// System.out.println("\t" + teams[i].getName());
		// }
		// }

		tabelle.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (tabelle.getSelectedRow() > -1) {
					Zentralesicht.repaint();
				}
				// System.out.println(">" + spielTage);
				// System.out.println("DatenGet->"+b.getN());
				// table.addSize(newView.getSpieltage());
				spieltageData = new TabelleSpielTage(newView.getTeams());
				spieltageData.addTeam(newView.getTeams());
			}
		});

		// tabelle.setTableHeader(null);

	}

	public void setgespeichert(boolean n) {
		geoffnet = n;
	}

	public boolean getgespeichert() {
		return geoffnet;
	}

}
