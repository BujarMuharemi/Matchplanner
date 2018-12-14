package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	public String toStringDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		System.out.println(this.date);
		String datum = formatter.format(this.date);

		return datum;

	}
	
	public void fromStringtoDate(String datum) {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		Date date = null;
		try {
			date = format.parse(datum);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.date=date;

	}
}
