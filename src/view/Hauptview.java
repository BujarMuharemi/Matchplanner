package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import FileIO.OpenCSV;
import FileIO.OpenXLS;
import FileIO.OpenXLSX;
import FileIO.SaveXLS;
import model.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.WindowFocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import FileIO.SaveXLSX;
/*
 * TODO-Feature#1: Anzahl der Spieltage auf JLabel in ZentralAnsicht zeigen
 * FIXME#3-Anzahl der Spieltage updates ich nicht wenn man csv lädt
 * */

public class Hauptview {

	int tabelleNum = 0;
	private JFrame frame;

	boolean gespeichert = false;
	boolean dateiGeoffnet = false;

	boolean dateiPathChoosen = false;

	boolean closeNoSave = false;
	boolean beendeProgramm = false;

	boolean teamsErstellt = false;

	public String openedFilePath = "";

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

	private void resetFlags() {
		gespeichert = false;
		dateiGeoffnet = false;
		dateiPathChoosen = false;
		closeNoSave = false;
		beendeProgramm = false;
		teamsErstellt = false;
	}

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
		boolean a = false;

		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setApproveButtonText("Open");
		FileFilter filter = new FileNameExtensionFilter("XLS", "xls");
		FileFilter filter2 = new FileNameExtensionFilter("CSV", "csv");
		FileFilter filter3 = new FileNameExtensionFilter("XLSX", "xlsx");
		jfc.addChoosableFileFilter(filter);
		jfc.addChoosableFileFilter(filter2);
		jfc.addChoosableFileFilter(filter3);

		jfc.setFileFilter(filter2);
		int returnValue = (!dateiPathChoosen) ? jfc.showOpenDialog(null) : 0;

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			System.out.println("You selected the directory: " + jfc.getSelectedFile());
			File selectedFile = jfc.getSelectedFile();

			String path = jfc.getSelectedFile().getName();

			if (path.substring(path.lastIndexOf("."), path.length()).equals(".xls")) {
				OpenXLS openXLS = new OpenXLS(jfc.getSelectedFile().toString());
				spieltageData.setSpielplan(openXLS.getSpieltabelle());

			} else if (path.substring(path.lastIndexOf("."), path.length()).equals(".xlsx")) {
				OpenXLSX openXLSX = new OpenXLSX(jfc.getSelectedFile().toString());
				spieltageData.setSpielplan(openXLSX.getSpieltabelle());

			} else if (path.substring(path.lastIndexOf("."), path.length()).equals(".csv")) {
				OpenCSV csvFile = new OpenCSV(selectedFile);
				teams = csvFile.returnTeams();
				spieltageData.addTeam(teams);
				spieltageData.createSpieltage();
			}

			dateiPathChoosen = true;
			openedMatchplan();

			a = true;
		}

		return a;
	}

	private boolean saveWindow() {
		boolean a = false;
		if (!dateiPathChoosen) {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Choose a directory to save your file: ");
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			// fillter für speichertypen
			FileFilter filter = new FileNameExtensionFilter("XLS", "xls");
			FileFilter filter2 = new FileNameExtensionFilter("CSV", "csv");
			FileFilter filter3 = new FileNameExtensionFilter("XLSX", "xlsx");
			FileFilter filter4 = new FileNameExtensionFilter("PDF", "pdf");
			jfc.addChoosableFileFilter(filter);
			jfc.addChoosableFileFilter(filter2);
			jfc.addChoosableFileFilter(filter3);
			jfc.addChoosableFileFilter(filter4);
			jfc.setFileFilter(filter);

			int returnValue = jfc.showSaveDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				if (jfc.getSelectedFile() != null) {
					dateiPathChoosen = true;
					System.out.println("You selected the directory: " + jfc.getSelectedFile());
					String path = jfc.getSelectedFile().getName();
					if (path.substring(path.lastIndexOf("."), path.length()).equals(".xls")) {
						System.out.println("ist drin");
						SaveXLS savexls = new SaveXLS(jfc.getSelectedFile().toString(), spieltageData);
					} else if (path.substring(path.lastIndexOf("."), path.length()).equals(".xlsx")) {
						SaveXLSX savexls = new SaveXLSX(jfc.getSelectedFile().toString(), spieltageData);
					} else {
						// TODO: pdf hier einbinden!
					}

					a = true;

				}
			}
		}

		return a;
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

				if (newView.getErstellt() && !teamsErstellt) {
					spieltageData.addTeam(newView.getTeams());
					spieltageData.createSpieltage();
					teamsErstellt = true;
					// FIXME#2: Bearbeiten von Teams resetet die tabelle
				}

				if (closeView.getButtonChoice() == 1 && closeNoSave && !gespeichert) {

					boolean a = saveWindow();

					if (a) {
						gespeichert = true;
						closedMatchplan();
						if (beendeProgramm) {
							System.exit(0);
						}
					} else {
						closeNoSave = false;
					}
				} else if (closeView.getButtonChoice() == 0) {
					if (beendeProgramm) {
						System.exit(0);
					} else if (closeNoSave) {
						closedMatchplan();
						closeNoSave = false;
					}
				}
				if (closeView.getButtonChoice() == 1 && !gespeichert && dateiPathChoosen) {
					gespeichert = true;
					closedMatchplan();
				}
				// System.out.println("saved:"+gespeichert+"\tcloseNoSave "+closeNoSave+"\tpath
				// "+dateiPathChoosen);
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
				dateiGeoffnet = true;
				// gespeichert = false;
				if (gespeichert) {
					// spieltageData.resetSpielTage();
				}
				resetFlags();
			}
		});
		menuDatei.add(mnitNeu);

		mnitOffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (openWindow()) {
					// FIXME#1: wenn eine datei ausgewählt wurde, müssen ihre werte in die haupt
					// tabelle geladen werden
					openedMatchplan();
					dateiGeoffnet = true;
					gespeichert = false;
				}
			}
		});
		menuDatei.add(mnitOffnen);

		mnitSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!gespeichert && !dateiPathChoosen) {
					gespeichert = true;
					saveWindow();
				} else {
					gespeichert = true;
				}

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
				if (gespeichert == true && dateiPathChoosen) {
					dateiGeoffnet = false;
					closedMatchplan();
					resetFlags();
				} else {
					closeView.setVisible(true);
					closeNoSave = true;
				}
			}
		});

		mnitMannschaftba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// spieltageData.editTeams();
				newView.setVisible(true);
				spieltageData.setEditTeam(true);
				teamsErstellt = false;
				gespeichert = false;
			}
		});
		mnextra.add(mnitMannschaftba);

		mnitSpieltag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spieltageData.editDates();
				gespeichert = false;
			}
		});

		mnitBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!gespeichert && !dateiGeoffnet) {
					System.exit(0);
				} else if (gespeichert) {
					System.exit(0);
				} else if (!gespeichert && dateiGeoffnet) {
					closeView.setVisible(true);
					closeNoSave = true;
					beendeProgramm = true;
				}
			}
			// else if (!gespeichert && dateiGeoffnet && gespeichert) {
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

		// System.out.println("DatenGet" + b.getN());
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

		// Überprügt ob der Nutzer etwas eingegeben hatt
		spielTageTabelle.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				gespeichert = false;
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				gespeichert = false;
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				gespeichert = false;
			}
		});

	}

	public void setGespeichert(boolean n) {
		dateiGeoffnet = n;
	}

	public boolean getGespeichert() {
		return dateiGeoffnet;
	}

	public void setGeoffnet(boolean n) {
		dateiGeoffnet = n;
	}

}
