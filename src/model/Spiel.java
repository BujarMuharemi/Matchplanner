package model;
import java.util.Date;

public class Spiel {

	private Mannschaften m1, m2;
	private Ergebnisse erg;
	private Date date;

	public Spiel(Mannschaften m1, Mannschaften m2, Ergebnisse er, Date date) {
		this.m1 = m1;
		this.m2 = m2;
		this.erg = er;
		this.date = date;
	}
	
	public Mannschaften getM1() {
		return m1;
	}

	public void setM1(Mannschaften m1) {
		this.m1 = m1;
	}

	public Mannschaften getM2() {
		return m2;
	}

	public void setM2(Mannschaften m2) {
		this.m2 = m2;
	}

	public Ergebnisse getErg() {
		return erg;
	}

	public void setErg(Ergebnisse erg) {
		this.erg = erg;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
