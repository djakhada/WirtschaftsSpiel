import java.net.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.io.*;

public class ClientHandler extends Thread
{
	final DataInputStream dis;
	final DataOutputStream dos;
	final Socket s;

	private final ServerFenster sw;

	final int startKapital;
	final int maximalerKredit;
	final boolean startkapitalKredit;
	final int maximaleSpielerAnzahl;
	final float startKreditZinssatz;
	int spielerAnzahl;
	int marktPreis = 500;
	int runde;
	String name;
	List<Spieler> spieler = new ArrayList<>();
	Spieler aktuellerSpieler;

	public ClientHandler(ServerFenster sw, Socket s, DataInputStream dis, DataOutputStream dos) {
		this.sw = sw;
		this.s = s;
		this.dis = dis;
		this.dos = dos;
		this.spieler = this.sw.spieler;
		this.startKapital = sw.startKapital;
		this.maximalerKredit = sw.maximalerKredit;
		this.startkapitalKredit = sw.startKapitalKredit;
		this.maximaleSpielerAnzahl = sw.maximaleSpielerAnzahl;
		this.startKreditZinssatz = sw.startKreditZinssatz;
	}

	//Neueste Werte an Spieler uebergeben
	public void spielerUpdaten(Spieler spieler){
		runde = sw.Runde;
		String msg;
		msg = "ID:"+sw.spieler.indexOf(spieler);
		sendToOut(spieler.Socket, msg);
		msg = "Kapital:"+spieler.Geldkapital;
		sendToOut(spieler.Socket, msg);
		msg = "Zinssatz:"+spieler.Zinssatz;
		sendToOut(spieler.Socket, msg);
		msg = "Kredit_:"+spieler.Kredit;
		sendToOut(spieler.Socket, msg);
		msg = "Marktpreis:"+sw.markt.preis;
		sendToOut(spieler.Socket, msg);
		msg = "MaxKredit:"+maximalerKredit;
		sendToOut(spieler.Socket, msg);
		msg = "Geldaenderung:"+spieler.Geldaenderung;
		sendToOut(spieler.Socket, msg);
		msg = "MaxAbbau:"+spieler.maxAbbaumenge;
		sendToOut(spieler.Socket, msg);
		msg = "LagerNeu:"+spieler.Lager;
		sendToOut(spieler.Socket, msg);
		msg = "KreditLaufzeitNeu:"+spieler.KreditLaufzeit;
		sendToOut(spieler.Socket, msg);
		msg = "9Runde:"+runde;
		sendToOut(spieler.Socket, msg);
		msg = "Abbaukosten:"+sw.markt.Abbaukosten;
		sendToOut(spieler.Socket, msg);
	}

	//Nachricht an Sockel s (Client) senden
	public void sendToOut(Socket s, String msg) {
		try {
			DataOutputStream d = new DataOutputStream(s.getOutputStream());
			d.writeUTF(msg);
			System.out.println("Server[ich]->Client["+s.getRemoteSocketAddress().toString()+"]: "+"\""+msg+"\"");

		} catch (IOException e) {
			System.out.println("Fehler bei sendToOut. Nachricht an Client["+s.getRemoteSocketAddress().toString()+"]: "+"\""+msg+"\"");
		}
	}

	//Neuen Spieler der Spielerliste hinzufuegen
	public void addSpieler(List<Spieler> spielerListe, String Name,  int startKapital, boolean startkapitalKredit) {
			Spieler s;
			if(startkapitalKredit) {//Wenn das Startkapital als Kredit gilt
				 int Kredit = (int)(startKapital + (startKapital * (startKreditZinssatz/100)));
				 s = new Spieler(Name, startKapital, Kredit, 0);
				 s.Zinssatz = startKreditZinssatz;
				 s.KreditLaufzeit = sw.startKreditLaufzeit;
				 s.isKreditNeu = true;
			}else {
				s = new Spieler(Name, startKapital, 0, 0);
			}
			s.Kapital = startKapital;
			s.Socket = this.s;
			spielerListe.add(s);
			System.out.println("Spieler "+s.Name+" wurde der Spielerliste hinzugefügt.("+spielerListe.size()+"/"+maximaleSpielerAnzahl+")");
			sw.updateTable();
			aktuellerSpieler = s;
	}


	//Verbindung schließen
	private void CloseThread() {
		try {
			//Alle Streams und Sockel schließen, dann Thread beenden
			s.close();
			dis.close();
			dos.close();
			this.interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.interrupt();
	}

	//Was passiert wenn ClientHandler gestartet wird
	@Override
	public void run()
	{
		String IP = s.getRemoteSocketAddress().toString();
		System.out.println("Server: Höre Client: "+IP+" jetzt zu.");
		String received;
		while (true)
		{
			try {
				received = dis.readUTF();
				if(received.equals("updateme")) {
					spielerUpdaten(aktuellerSpieler);
				}
				if(received.equals("quit")) {
					System.out.println("Server: Client " + IP +" (" +name+") möchte trennen.");
					System.out.println("Server: Client " + IP +" (" +name+") wurde getrennt.");
					CloseThread();
					sw.spieler.remove(aktuellerSpieler); //Spieler aus der Spielerliste entfernen
					break;
				}else if(received.contains("join:")){
					name = received.substring(5);
					System.out.println("Server: Spieler '"+name+"' ist dem Spiel beigetreten.");
					if(spieler.size()<maximaleSpielerAnzahl) {
						addSpieler(spieler, name, startKapital, startkapitalKredit);
						dos.writeUTF("joined");
					}else {
						System.out.println("Spieler "+name+" wurde der Spielerliste nicht hinzugefügt, da das Spiel voll ist.");
						dos.writeUTF("fullgame");
					}
				}
				else if(received.contains("Lagermenge:"))
				{
					int Lagermenge = Integer.parseInt(received.substring(received.lastIndexOf(":")+1));
					System.out.println("Server<-Client: Lagermenge: "+Lagermenge);
					aktuellerSpieler.Lagermenge = Lagermenge;
				}
				else if(received.contains("Abbaumenge:"))
				{
					int Abbaumenge = Integer.parseInt(received.substring(received.lastIndexOf(":")+1));
					System.out.println("Server<-Client: Lagermenge: "+Abbaumenge);
					aktuellerSpieler.Abbaumenge = Abbaumenge;
				}
				else if(received.contains("Verkaufsmenge:"))
				{
					int Verkaufsmenge = Integer.parseInt(received.substring(received.lastIndexOf(":")+1));
					System.out.println("Server<-Client: Verkaufsmenge: "+Verkaufsmenge);
					aktuellerSpieler.Verkaufsmenge = Verkaufsmenge;
				}
				else if(received.contains("Kreditmenge:"))
				{
					int Kreditmenge = Integer.parseInt(received.substring(received.lastIndexOf(":")+1));
					System.out.println("Server<-Client: Genommene Kreditmenge: "+Kreditmenge);
					aktuellerSpieler.Kredit = Kreditmenge;
					aktuellerSpieler.isKreditNeu = true;
				}
				else if(received.contains("Kreditlaufzeit:"))
				{
					int Kreditlaufzeit = Integer.parseInt(received.substring(received.lastIndexOf(":")+1));
					System.out.println("Server<-Client: Kreditlaufzeit: "+Kreditlaufzeit);
					aktuellerSpieler.KreditLaufzeit = Kreditlaufzeit;
				}else if(received.equals("ready")) {
					System.out.println("Server<-Client: Spieler ist bereit.");
					aktuellerSpieler.isReady = true;
					sw.updateTable();
				}
			}catch(IOException e) {
				System.out.println("Server: Client " + IP +" (" +name+") kann nicht mehr erreicht werden.");
				CloseThread();
				break;
			}
		}

		try {
			this.dos.close();
			this.dis.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
