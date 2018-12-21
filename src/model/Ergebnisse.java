package model;

public class Ergebnisse {
	int erg1,erg2;
	
	public Ergebnisse(int erg1, int erg2) {
		this.erg1 = erg1;
		this.erg2 = erg2;

	}
	public Ergebnisse(String erg) {
		String[] parts = erg.split(":");
		this.erg1 = Integer.parseInt(parts[0]);
		this.erg2 = Integer.parseInt(parts[1]);
	}

	public int getErg1() {
		return erg1;
	}

	public void setErg1(int erg1) {
		this.erg1 = erg1;
	}

	public int getErg2() {
		return erg2;
	}

	public void setErg2(int erg2) {
		this.erg2 = erg2;
	}
	
	public String toString() {
		return this.erg1+":"+this.erg2;
	}
	//TODO#3.1: diese methode fixen/oder einen string constructer erstellen
	public void setErg(String e) {
		erg1=Integer.parseInt(e);
		erg2=Integer.parseInt(e);
	}

}
