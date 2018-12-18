package FileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Document;

import model.Spiel;
import model.TabelleSpielTage;
import com.itextpdf.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;


public class SavePDF {

	String path;
	List<Spiel> daten = new ArrayList<>();
	public SavePDF(String path, TabelleSpielTage tabelle) {
		this.path = path;
		this.daten =tabelle.getSpielTageOutput();
		writePDF(tabelle);
	}

	public void writePDF(TabelleSpielTage tabelle) {
		try {
			PdfWriter writer = new PdfWriter(new File(path));
			PdfDocument pdfDoc = new PdfDocument(writer);
			com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDoc);
			Table tabel = new Table(daten.size());
			tabel.addHeaderCell("Team1");
			tabel.addHeaderCell("Team2");
			tabel.addHeaderCell("Ergebnis");
			tabel.addHeaderCell("Datum");
			
			for (Spiel spiel : daten) {
				tabel.addCell(spiel.getM1().getName());
				tabel.addCell(spiel.getM2().getName());
				tabel.addCell(spiel.getErg().getErg1() +":"+ spiel.getErg().getErg2());
				tabel.addCell(spiel.toStringDate());
				tabel.startNewRow();
			}
			document.add(tabel);
			document.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
