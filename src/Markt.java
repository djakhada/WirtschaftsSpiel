import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Markt {

	public int preis=25, 			//Startpreis des Markts
			preisMax=100, 			//Maximalpreis
			preisMin=5, 			//Minimalpreis
			Erzbestand = 1000000,
			Runde = 1,
			maxAbbauStart = 5000,
			marktBedarf = 0,
			verkaufLetzteRunde = 0,
			Abbaukosten=15;
	boolean GameStart = true;

	public List<Spieler> spieler = new ArrayList<>();			//erstellt eine array liste

	public Markt(List<Spieler> spieler){
		this.spieler = spieler;
	}

	public void SetErzbestand(int Erzbestand){
		this.Erzbestand = Erzbestand;
	}


	public void NaechsteRunde() {
		AbbauKostenBerechnen();					//Ruft die einzeneln funktionen auf
		MaximaleAbbaumengeBerechnen();
		MarktBerechnen();
		KapitaleBerechnen();
		Runde++;								//Zählt die Runde Hoch
	}

	//Maximale Erzmenge die noch vorhanden ist betrachten

	public int SpielerKapital(Spieler sp) {
		int Kapital = (sp.Lager * preis) + sp.Geldkapital;
		return Kapital;
	}

	public void AbbauKostenBerechnen(){				//Die Kosten mit einer RAndom Zahl addieren um mehr zufall zu erzeugen
		Abbaukosten = 5 + new Random().nextInt(3);
	}

	public void KapitaleBerechnen(){
		//Neuen Marktpreis berechnen
		if(!GameStart) {
			for(int i=0; i<spieler.size();i++){
				Spieler s = spieler.get(i);

				s.Lager = s.Lager + s.Lagermenge;	//gib die menge die eingelagert werden soll an das Lager weiter
				s.Lagermenge = 0;


				int abbauKosten = s.Abbaumenge * this.Abbaukosten;
				s.Abbaumenge = 0;

				int Kreditrate = 0;
				int Kapitaleinfluss;
				if(s.KreditLaufzeit>0){					//berechnet das neue Kapital
					if(!s.isKreditNeu) {			//wenn der kredit neu ist
						Kreditrate = s.Kredit / s.KreditLaufzeit; 	//berechnet man die Kreditrate zum abzahlen
						s.Kredit = s.Kredit - Kreditrate;			//und zieht diese ab
						if(s.KreditLaufzeit>0)s.KreditLaufzeit--;   //und setzt die Laufzeit runter
					}else s.isKreditNeu=false;
				}
				Kapitaleinfluss = s.Verkaufsmenge * preis;							  //Berechnet den kaputaleinfluss
				Kapitaleinfluss = Kapitaleinfluss - Kreditrate - abbauKosten - 15000; //Fixkosten = 15000
				s.Verkaufsmenge = 0;


				//1. Rüchzahlungswert = KREDIT + Kredit*Kredit.Zinssatz
				//2. Rüchkzahlng / Laufzeit = Rate
				//3. Kredit = Kredit-Rate


				//+Fixkosten



				//ohne berücksichtigung von Forschung
				s.Geldkapital = s.Geldkapital + Kapitaleinfluss;				//berechnent das neue Geldkapital

				s.Geldaenderung = Kapitaleinfluss;								//sezt die geldänderung fest
				s.Kapital = (s.Lager * preis) + s.Geldkapital;					//berechnet da neue Kapital
				}
		}
	}



	public void MaximaleAbbaumengeBerechnen() {
		for(int i=0; i<spieler.size();i++) {
			Spieler s = spieler.get(i);				//holst sich die Anzahl der Spieler
			s.maxAbbaumenge = 5000;					//und berechnet die insgesammte abbaumenge (immer 5000) ist fix
		}
	}


	public void MarktBerechnen(){
		int spielerAnzahl = spieler.size();						//holt sich die anzahl der spieler
		int marktBedarfLetzteRunde = marktBedarf;				//holt den bedarf der letzten runde
		if(Runde==1){														//wenn es die erstze Runde ist
			verkaufLetzteRunde = 0;											//ist der verkauf in der letzten runde 0
			marktBedarf = (int)(spielerAnzahl * maxAbbauStart * 0.75 * Runde * 0.15 - verkaufLetzteRunde + (spielerAnzahl*maxAbbauStart*2)); 	// unsere formel zur Marktberechnung
		}else{   	//wenn es nicht die erste runde ist
			for(int i=0; i<spieler.size();i++){
				Spieler s = spieler.get(i);
				verkaufLetzteRunde=verkaufLetzteRunde+s.Verkaufsmenge;			//holst er sich alle verkaufsmengen
			}
			marktBedarf = (int)(spielerAnzahl * maxAbbauStart * 0.75 * Runde * 0.15 - verkaufLetzteRunde + marktBedarf);	//berechnet dadurch den Marktbedarf
			verkaufLetzteRunde=0;
		}
		preis = (marktBedarf-marktBedarfLetzteRunde)/2000+preis;				//Und erechnet den preis für die nächste runde
	}

}