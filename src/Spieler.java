import java.net.*;

public class Spieler {
	public String Name = "Unbenannter Spieler";
	public int Geldkapital = 0;
	public int Kapital = 0;
	public int Kredit = 0;
	public int GenommenerKredit = 0;
	public int KreditLaufzeit = 0;
	public int Geldaenderung = 0; //Gewinn/Verlust
	public int Lager = 0; //Menge im Lager
	public int Lagermenge = 0; //Zu lagernde Menge
	public int LagerMax = 15000;
	//public int Abbaumenge = 0; //Zu abbauende Menge
	public int maxAbbaumenge = 5000; //Die maximale Abbaumenge des Spielers
	public int Verkaufsmenge = 0;
	public int Forschungskosten = 0;
	public float Zinssatz =  20.0f;
	public boolean isReady = false;
	public boolean isKreditNeu = true;
	public Socket Socket = new Socket();
	
	
	public Spieler(String Name, int Kapital, int Kredit, int Lagermenge) {
		this.Name = Name;
		this.Geldkapital = Kapital;
		this.Kredit = Kredit;
		this.Lagermenge = Lagermenge;
	}
	
	public Spieler() {
		
	}
	

}
