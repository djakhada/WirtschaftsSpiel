import java.util.ArrayList;
import java.util.List;

public class Markt {

	public int preis=25, //Startpreis des Markts
			preisMax=100, //Maximalpreis
			preisMin=5, //Minimalpreis
			Erzbestand = 1000000,
			Runde = 1,
			maxAbbauStart = 5000,
			marktBedarf = 0,
			verkaufLetzteRunde = 0;
	boolean GameStart = true;

	public List<Spieler> spieler = new ArrayList<>();

	public Markt(List<Spieler> spieler){
		this.spieler = spieler;
	}

	public void SetErzbestand(int Erzbestand){
		this.Erzbestand = Erzbestand;
	}


	public void NaechsteRunde() {
		MaximaleAbbaumengeBerechnen();
		MarktBerechnen();
		KapitaleBerechnen();
		Runde++;
	}

	//Maximale Erzmenge die noch vorhanden ist betrachten

	public int SpielerKapital(Spieler sp) {
		int Kapital = (sp.Lager * preis) + sp.Geldkapital;
		return Kapital;
	}

	public void KapitaleBerechnen(){
		//Neuen Marktpreis berechnen
		if(!GameStart) {
			preis = 150;
			for(int i=0; i<spieler.size();i++){
				Spieler s = spieler.get(i);

				s.Lager = s.Lager + s.Lagermenge;
				s.Lagermenge = 0;


				int Kreditrate = 0;
				int Kapitaleinfluss;
				if(s.KreditLaufzeit>0){
					if(!s.isKreditNeu) {
						Kreditrate = s.Kredit / s.KreditLaufzeit;
						s.Kredit = s.Kredit - Kreditrate;
						if(s.KreditLaufzeit>0)s.KreditLaufzeit--;
					}else s.isKreditNeu=false;
				}
				Kapitaleinfluss = s.Verkaufsmenge * preis;
				Kapitaleinfluss = Kapitaleinfluss - Kreditrate;
				s.Verkaufsmenge = 0;


				//1. Rüchzahlungswert = KREDIT + Kredit*Kredit.Zinssatz
				//2. Rüchkzahlng / Laufzeit = Rate
				//3. Kredit = Kredit-Rate


				//ohne berücksichtigung von Forschung
				s.Geldkapital = s.Geldkapital + Kapitaleinfluss;

				s.Geldaenderung = Kapitaleinfluss;
				s.Kapital = (s.Lager * preis) + s.Geldkapital;
				}
		}
	}



	public void MaximaleAbbaumengeBerechnen() {
		for(int i=0; i<spieler.size();i++) {
			Spieler s = spieler.get(i);
			s.maxAbbaumenge = 5000;
		}
	}




	public void MarktBerechnen(){
		int spielerAnzahl = spieler.size();
		int marktBedarfLetzteRunde = marktBedarf;
		if(Runde==1){
			marktBedarf = (int)(spielerAnzahl * maxAbbauStart * 0.75 * Runde * 0.15 - verkaufLetzteRunde + (spielerAnzahl*maxAbbauStart*2));
		}else{
			marktBedarf = (int)(spielerAnzahl * maxAbbauStart * 0.75 * Runde * 0.15 - verkaufLetzteRunde + marktBedarf);
		}
		//deltaMarktBedarf = marktBedarf-marktBedarfLetzteRunde			(5000+5500)=
		preis = (marktBedarf-marktBedarfLetzteRunde)/2000+preis;
	}

}