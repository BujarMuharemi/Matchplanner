package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

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
import java.io.File;

/*
	6 Teams
	10 Spieltage
	3 Spiele pro Tag
	30 Spiele insgesammt
*/

/* FIXME: Bug#2: Hin/Rückspiele algo muss überarbeitet werden  
 * FIXME: Bug#3: Save öffnet sich ein zweites mal nach öffnen  
 * 
 * TODO: Feature#1: Anzahl der Spieltage auf JLabel in ZentralAnsicht zeigen
 * TODO: Feature-Teil2#1: Über die Menüeinträge edit/bearbeiten, es ermöglichen die Spieltag tabelle zu bearbeiten(und speichern flag=false)
 * */

public class Hauptview {

	int tabelleNum = 0;
	private JFrame frame;

	boolean gespeichert = false;
	boolean geoffnet = false;

	boolean dataeiGespeichert = false;

	private Mannschaften[] teams;
	TabelleSpielTage spieltageData = new TabelleSpielTage(teams);
	public Daten b = new Daten(5);
	public BeendenView closeView = new BeendenView();
	NewView newView = new NewView();
	Tabelle table;

	JMenu menuDatei = new JMenu("Datei");
	JMenu mnextra = new JMenu("Edit/Bearbeiten");

	JMenuItem mnitNeu = new JMenuItem("Neu");
	JMenuItem mnitOffnen = new JMenuItem("Öffnen");
	JMenuItem mnitSpeichern = new JMenuItem("Speichern");
	JMenuItem mnitSpeichernunter = new JMenuItem("Speichern unter");
	JMenuItem mnitBeenden = new JMenuItem("Beenden");
	JMenuItem mnitDrucken = new JMenuItem("Drucken");
	JMenuItem mnitClose = new JMenuItem("Schließen");

	JMenuItem mnitMannschaftba = new JMenuItem("Mannschaft bearbeiten");
	JMenuItem mnitSpieltag = new JMenuItem("Spieltag bearbeiten");

	JPanel Zentralesicht = new JPanel();
	JPanel SpieltagAnsicht = new JPanel();

	public void closedMatchplan() {
		setMatchplan(false);
	}

	public void openedMatchplan() {
		setMatchplan(true);
	}

	private void setMatchplan(boolean b) {
		mnitNeu.setEnabled(!b);
		mnitOffnen.setEnabled(!b);
		mnitSpeichern.setEnabled(b);
		mnitSpeichernunter.setEnabled(b);
		mnitDrucken.setEnabled(b);
		mnitClose.setEnabled(b);

		mnitMannschaftba.setEnabled(b);
		mnitSpieltag.setEnabled(b);

		Zentralesicht.setVisible(b);
		SpieltagAnsicht.setVisible(b);
	}

	private boolean openWindow() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setApproveButtonText("Open");
		FileFilter filter = new FileNameExtensionFilter("XLS", "xls");
		FileFilter filter2 = new FileNameExtensionFilter("CSV", "csv");
		FileFilter filter3 = new FileNameExtensionFilter("XLSX", "xlsx");
		jfc.addChoosableFileFilter(filter);
		jfc.addChoosableFileFilter(filter2);
		jfc.addChoosableFileFilter(filter3);

		jfc.setFileFilter(filter2);
		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
		}
		
		return 
	}

	private void saveWindow() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Choose a directory to save your file: ");
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			if (jfc.getSelectedFile() != null) {
				dataeiGespeichert = true;
				System.out.println("You selected the directory: " + jfc.getSelectedFile());
			}
		}
	}

	// Mannschaften setten
	public void setMan(Mannschaften[] m) {
		teams = m;
		// System.out.println("setMan"+getSpieltage());
		// tabel.addSize(getSpieltage());
		// spielTage = getSpieltage();

		// table.addSize(spielTage);
		// frame.repaint();

		updateTeams();
		// initialize();

	}

	public void updateTeams() {
		spieltageData = new TabelleSpielTage(teams);
		// Doesnt get called in real event

	}

	// spieltage werden berrechnet und weiter gegeben
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
				// for (int i = 0; i < newView.getSpieltage(); i++) {
				// spieltageData.addRow();//
				// }
				//
				if (newView.getErstellt()) {
					spieltageData.addTeam(newView.getTeams());
					// spieltageData = new TabelleSpielTage(newView.getTeams());
					spieltageData.createSpieltage();
				}

				// .println("Close: gespeichert-" + gespeichert + ",buttonchoice: " +
				// closeView.getButtonChoice());
				// System.out.println("beendenwas: " + closeView.getBeendenWas());

				if (closeView.getButtonChoice() == 1) {
					gespeichert = true;
					closedMatchplan();
					closeView.setButtonChoice(0);
				} else {

				}
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

		menubar.add(menuDatei);
		menubar.add(mnextra);

		mnitNeu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newView.setVisible(true);
				openedMatchplan();
				geoffnet = true;
				// gespeichert = false;
				if (gespeichert) {
					spieltageData.resetSpielTage();

				}
			}
		});
		menuDatei.add(mnitNeu);

		mnitOffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openedMatchplan();
				geoffnet = true;
				gespeichert = false;
				openWindow();
			}
		});
		menuDatei.add(mnitOffnen);

		mnitSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!dataeiGespeichert) {
					saveWindow();
				}
				gespeichert = true;

			}
		});
		menuDatei.add(mnitSpeichern);

		mnitSpeichernunter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveWindow();
			}
		});

		mnitClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// System.out
				// .println("Close: gespeichert-" + gespeichert + ",buttonchoice: " +
				// closeView.getButtonChoice());
				// System.out.println("beendenwas: " + closeView.getBeendenWas());
				if (gespeichert == true) {
					geoffnet = false;
					closedMatchplan();
				} else {
					closeView.setVisible(true);
				}
			}
		});

		mnitMannschaftba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, " Manschaften verändern ");
			}
		});
		mnextra.add(mnitMannschaftba);

		mnitSpieltag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, " Spieltage festlegen / verändern ");
			}
		});

		mnitBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gespeichert == true) {
					// frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				} else {
					closeView.setVisible(true);
				}
			}
		});

		menuDatei.add(mnitSpeichernunter);
		menuDatei.add(mnitDrucken);
		menuDatei.add(mnitClose);

		mnextra.add(mnitSpieltag);
		menuDatei.add(mnitBeenden);

		closedMatchplan(); // ist default wenn nix geöffnet ist

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 700, 100, 30 };
		gridBagLayout.rowHeights = new int[] { 550, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

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
		// spieltageData = new TabelleSpielTage(newView.getTeams());
		// spieltageData.addTeam(newView.getTeams());

		JTable tabelle = new JTable(table);

		JTable spielTageTabelle = new JTable(spieltageData);

		JScrollPane SpielTagScrollen = new JScrollPane(tabelle);
		JScrollPane ZentralScrollen = new JScrollPane(spielTageTabelle);

		frame.getContentPane().add(SpieltagAnsicht, gbc_SpieltagAnsicht);
		SpieltagAnsicht.setLayout(new BorderLayout(0, 0));

		SpieltagAnsicht.add(SpielTagScrollen);

		Zentralesicht.add(ZentralScrollen);

		System.out.println("DatenGet" + b.getN());
		frame.repaint();

		tabelle.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (tabelle.getSelectedRow() > -1) {
					spieltageData.updateSpieltage();
					spieltageData.showSpieltag(tabelle.getSelectedRow());
				}
				if (teams != null) {
					for (int i = 0; i < teams.length; i++) {
						// System.out.println(teams[i].getName());
					}
				}
			}
		});

	}

	public void setGespeichert(boolean n) {
		geoffnet = n;
	}

	public boolean getGespeichert() {
		return geoffnet;
	}

	public void setGeoffnet(boolean n) {
		geoffnet = n;
	}

}
