import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//Netzwerk libs
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;


class RundenDaten {
	final int Runde;
	final int Kapital;
	final int Geldkapital;
	final int Geldaenderung;
	final int Lager;
	final int Lagermenge;
	final int Verkaufsmenge;
	final float Kreditprozentsatz;
	final int Kreditnahme;

	public RundenDaten(int Runde, int Kapital, int Geldkapital, int Geldaenderung,int Lager, int Lagermenge, int Verkaufsmenge, float Kreditprozentsatz, int Kreditnahme) {
		this.Runde = Runde;
		this.Kapital = Kapital;
		this.Geldkapital = Geldkapital;
		this.Geldaenderung = Geldaenderung;
		this.Lager = Lager;
		this.Lagermenge = Lagermenge;
		this.Verkaufsmenge = Verkaufsmenge;
		this.Kreditprozentsatz = Kreditprozentsatz;
		this.Kreditnahme = Kreditnahme;
	}
}

class ResponseHandler extends Thread {
	final DataInputStream dis;
	final DataOutputStream dos;
	final Socket s;
	final VerbindungsFenster cw;

	public ResponseHandler(VerbindungsFenster cw, Socket s, DataInputStream dis, DataOutputStream dos) {
		this.cw = cw;
		this.dis = dis;
		this.dos = dos;
		this.s = s;
	}

	@Override
	public void run()
	{
		//Dem Server ständig zuhören und auf Antworten bzw. Nachrichten warten um diese zu verarbeiten
		//Diesen Nachrichtenarten eine ID zuweisen
		System.out.println("Client: ResponseHandler wurde gestartet und hört dem Server jetzt zu.");
		String received;
		while (true) //Stetig versuchen den unteren Teil auszufuehren
		{
			try {

				received = dis.readUTF(); //Die Nachrichten vom Servers lesen
				if(received.contains("Kapital")){
					cw.ResponseBearbeiten(0,received);
				}else if(received.contains("Zinssatz")) {
					cw.ResponseBearbeiten(1,received);
				}else if(received.contains("Kredit_")) {
					cw.ResponseBearbeiten(2, received);
				}else if(received.contains("Marktpreis")) {
					cw.ResponseBearbeiten(3, received);
				}else if(received.contains("MaxKredit:")) {
					cw.ResponseBearbeiten(4, received);
				}else if(received.contains("Geldaenderung")) {
					cw.ResponseBearbeiten(5, received);
				}else if(received.contains("MaxAbbau")) {
					cw.ResponseBearbeiten(6, received);
				}else if(received.contains("LagerNeu")) {
					cw.ResponseBearbeiten(7, received);
				}else if(received.contains("KreditLaufzeitNeu")) {
					cw.ResponseBearbeiten(8, received);
				}else if(received.contains("9Runde")) {
					cw.ResponseBearbeiten(9, received);
				}else if(received.contains("10NaechsteRunde:")) {
					cw.ResponseBearbeiten(10, received);
				}else if(received.equals("gameStart")) {
					cw.ResponseBearbeiten(11);
				}else if(received.equals("!lastRound!")) {
					cw.ResponseBearbeiten(12);
				}else if(received.equals("!gameEnd!")) {
					cw.ResponseBearbeiten(13);
				}else if(received.contains("ID:")) {
					cw.ResponseBearbeiten(14, received);
				}else if(received.contains("-RundenDaten:")) {
					cw.ResponseBearbeiten(15, received);
				}else if(received.equals("!winner!")) {
					cw.ResponseBearbeiten(16);
				}else if(received.equals("!loser")) {
					cw.ResponseBearbeiten(17);
				}else if(received.contains("Abbaukosten:")) {
					cw.ResponseBearbeiten(18, received);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}

public class VerbindungsFenster extends JFrame {

	Endscreen es = new Endscreen();
	private JPanel contentPane;
	private JTextField txtIP;
	private JTextField txtPORT;
	private JTextField txtNAME;
	JButton btnVerbindungTrennen = new JButton("Verbindung Trennen");
	JButton btnVerbinden = new JButton("Verbinden");

	private Socket s = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;



	protected GameInterface GI;



	ResponseHandler rp;

	public ArrayList<RundenDaten> rundenDaten = new ArrayList<>();



	//Clientvariablen
	private int ID;
	private int AbbauKosten;
	private boolean lastRound;
	private int runde;
	private int marktPreis;
	private int maxKredit;
	private boolean amWinner;
	private Spieler spieler = new Spieler();

	public int getGeldKapital() {
		return spieler.Geldkapital;
	}
	private void setKapital(int kapital) {
		spieler.Geldkapital = kapital;
	}
	public int getKapital() {
		return spieler.Kapital;
	}


	public int getLager() {
		return spieler.Lager;
	}

	public void setLagermenge(int lagermenge) {
		spieler.Lagermenge = lagermenge;
	}

	public void setAbbaumenge(int abbaumenge) {
		spieler.Abbaumenge = abbaumenge;
	}

	public void setVerkaufsmenge(int verkauf) {
		spieler.Verkaufsmenge = verkauf;
	}

	public int getVerkaufsmenge() {
		return spieler.Verkaufsmenge;
	}

	public int getLagermenge() {
		return spieler.Lagermenge;
	}

	public void setGenommenerKredit(int kredit) {
		spieler.GenommenerKredit = kredit;
	}

	public int getAbbauKosten(){
		return AbbauKosten;
	}

	public void setKreditLaufzeit(int laufzeit) {
		spieler.KreditLaufzeit = laufzeit;
	}

	public int getKreditLaufzeit() {
		return spieler.KreditLaufzeit;
	}

	public int getMaxKredit() {
		return maxKredit;
	}

	public int getGeldaenderung() {
		return spieler.Geldaenderung;
	}

	public int getKredit() {
		return spieler.Kredit;
	}

	public float getZinssatz() {
		return spieler.Zinssatz;
	}
	public int getMarktPreis() {
		return marktPreis;
	}

	public int getLagerMax() {
		return spieler.LagerMax;
	}

	public int getMaxAbbau() {
		return spieler.maxAbbaumenge;
	}

	public int getRound() {
		return runde;
	}

	public void setForschungskosten(int kosten)
	{
		spieler.Forschungskosten = kosten;
	}

	public void nextRound() { //Vom Server die Nachricht dass eine neue Nachricht beginnt.
		sendToOut(s,"updateme");
		Thread t = new Thread() {
			public void run() {
				GI.naechsteRound(runde);
				this.interrupt();
			}
		};
		t.start();

	}

	public void confirmRound() { //Werte des Clients bestätigen
		//Lagermenge, Verkaufsmenge, Kreditmenge, Kreditlaufzeit
			System.out.println("Client: Fange an Daten an den Server zu übermitteln.");
			String msg;

			msg = "Lagermenge:"+spieler.Lagermenge;
			spieler.Lagermenge = 0;
			sendToOut(s,msg);

			msg = "Abbaumenge:"+spieler.Abbaumenge;
			spieler.Abbaumenge = 0;
			sendToOut(s,msg);

			msg = "Verkaufsmenge:"+spieler.Verkaufsmenge;
			spieler.Verkaufsmenge = 0;
			sendToOut(s,msg);

			if(spieler.GenommenerKredit>0) {
				msg = "Kreditmenge:"+spieler.GenommenerKredit;
				sendToOut(s,msg);
				spieler.GenommenerKredit = 0;
			}
			msg = "Kreditlaufzeit:"+spieler.KreditLaufzeit;
			sendToOut(s,msg);
			msg = "ready";
			sendToOut(s,msg);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerbindungsFenster frame = new VerbindungsFenster();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public void sendToOut(Socket s, String msg) {
		try {
			DataOutputStream d = new DataOutputStream(s.getOutputStream());
			d.writeUTF(msg);
			System.out.println("Client[ich]->Server["+s.getRemoteSocketAddress().toString()+"]: "+"\""+msg+"\"");

		} catch (IOException e) {
			System.out.println("Fehler bei sendToOut. Nachricht an Server["+s.getRemoteSocketAddress().toString()+"]: "+"\""+msg+"\"");
		}
	}

	public void connectToServer(String ip, int port, String name, boolean showMessage) throws UnknownHostException, IOException {
		try {
			s = new Socket (InetAddress.getByName(ip), port);
			dis = new DataInputStream(s.getInputStream());
			System.out.println("Client: Erfolgreich zum Server: ["+s+"] verbunden.");
			sendToOut(s, "join:"+name);
			String received;
			while(true) {
				try {
					received = dis.readUTF();

					if(received.equals("joined")) {
						if(showMessage)JOptionPane.showMessageDialog(null, "Erfolgreich dem Spiel beigetreten. Warte nun darauf, dass der Host das Spiel startet.");
						rp = new ResponseHandler(this, s, dis, dos);
						btnVerbindungTrennen.setEnabled(true);
						Thread t = rp;
						t.start();
						sendToOut(s,"updateme");

						GI = new GameInterface(this);

						break;

					}else if(received.equals("fullgame")) {
						if(showMessage)JOptionPane.showMessageDialog(null, "Konnte dem Spiel nicht beitreten, da das Spiel voll ist.");
						break;
					}
				}catch (IOException e){
					System.out.println("Client: Verbindug zum Server fehlgeschlagen");
					break;
				}
			}
			}catch(ConnectException e) {
				JOptionPane.showMessageDialog(null, "Verbindung zum Server fehlgeschlagen");
				btnVerbinden.setEnabled(true);
			}

	}

	/**
	 * Create the frame.
	 */
	public VerbindungsFenster() {
		setTitle("Wirtschaftssim by BDM & Co.");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 240, 237);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel connectPanel = new JPanel();
		contentPane.add(connectPanel, BorderLayout.CENTER);
		connectPanel.setLayout(null);

		txtIP = new JTextField();
		txtIP.setText("127.0.0.1");
		txtIP.setBounds(74, 11, 133, 20);
		connectPanel.add(txtIP);
		txtIP.setColumns(10);

		JLabel lblIpadresse = new JLabel("IP-Adresse");
		lblIpadresse.setBounds(10, 14, 74, 14);
		connectPanel.add(lblIpadresse);

		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(10, 42, 74, 14);
		connectPanel.add(lblPort);

		txtPORT = new JTextField();
		txtPORT.setText("23554");
		txtPORT.setColumns(10);
		txtPORT.setBounds(74, 39, 133, 20);
		connectPanel.add(txtPORT);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 67, 74, 14);
		connectPanel.add(lblName);

		txtNAME = new JTextField();
		txtNAME.setText("Spieler");
		txtNAME.setColumns(10);
		txtNAME.setBounds(74, 64, 133, 20);
		connectPanel.add(txtNAME);


		btnVerbinden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnVerbinden.setEnabled(false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							connectToServer(txtIP.getText(),Integer.parseInt(txtPORT.getText()),txtNAME.getText(), true);
						} catch (NumberFormatException | IOException e1) {
							e1.printStackTrace();
						}
					}
				}).start();

			}
		});
		btnVerbinden.setBounds(10, 92, 197, 23);
		connectPanel.add(btnVerbinden);


		btnVerbindungTrennen.setEnabled(false);
		btnVerbindungTrennen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Client: Trennen");
					sendToOut(s, "quit");

					rp.interrupt();
					dis.close();
			        dos.close();
			        s.close();

			        System.out.println("Client: Erfolgreich getrennt");
			        btnVerbinden.setEnabled(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnVerbindungTrennen.setBounds(10, 126, 197, 23);					//
		connectPanel.add(btnVerbindungTrennen);
	}

	//static public void ResponseBearbeiten(String response) {
	/*	https://jc-downloads.s3.amazonaws.com/git-team-cheatsheet.pdf
	 * *ResponseIDs:
		 *0: Kapital:
		 *1: Zinssatz:
		 *2: Kredit:
		 *3: Marktpreis:
		*/

	public void EndGame() { //16 winner 17 loser

		/*if(amWinner) es= new Endscreen(rundenDaten, true);
		else es = new Endscreen(rundenDaten, false);*/
		es.setWinner(amWinner);
		es.setRundenDaten(rundenDaten);
		es.getStats(ID);
		es.setVisible(true);
		GI.setVisible(false);
	}


	public void ResponseBearbeiten(int responseID) {
		System.out.println("Client[Ich]<-Server: ResponseID: "+responseID);
		switch(responseID) {
			case 11:
				this.setVisible(false);
				new Thread() {
					public void run() {
						GI.Updaten();
						this.interrupt();
					}
				}.start();
				GI.setVisible(true);
				break;
			case 12:
				JOptionPane.showMessageDialog(null, "Der Host hat die nächste Runde als die Letzte bestimmt. Wähle deine letzten Schritte weise!");
				break;
			case 13:
				EndGame();
				break;
			case 16:
				amWinner=true;
				break;
			case 17:
				amWinner=false;
				break;
		}
	}

	public void ResponseBearbeiten(int responseID, String response) {
		System.out.println("Client[Ich]<-Server: '"+response+"' - ResponseID: "+responseID);
		switch(responseID) {
			case 0:
				int Kapital = Integer.parseInt(response.substring(response.lastIndexOf(":")+1));
				setKapital(Kapital);
				System.out.println("Kapital Neu:"+ getGeldKapital());
				break;
			case 1:
				float Zinssatz = Float.parseFloat(response.substring(response.lastIndexOf(":")+1));
				spieler.Zinssatz = Zinssatz;
				break;
			case 2:
				int Kredit = Integer.parseInt(response.substring(response.lastIndexOf(":")+1));
				spieler.Kredit = Kredit;
				break;
			case 3:
				int Marktpreis = Integer.parseInt(response.substring(response.lastIndexOf(":")+1));
				marktPreis = Marktpreis;
				break;
			case 4:
				int MaxKredit = Integer.parseInt(response.substring(response.lastIndexOf(":")+1));
				maxKredit = MaxKredit;
				break;
			case 5:
				int Geldaenderung = Integer.parseInt(response.substring(response.lastIndexOf(":")+1));
				spieler.Geldaenderung = Geldaenderung;
				break;
			case 6:
				int MaxAbbau = Integer.parseInt(response.substring(response.lastIndexOf(":")+1));
				spieler.maxAbbaumenge = MaxAbbau;
				break;
			case 7:
				int LagerNeu = Integer.parseInt(response.substring(response.lastIndexOf(":")+1));
				spieler.Lager = LagerNeu;
				break;
			case 8:
				int KreditLaufzeitNeu = Integer.parseInt(response.substring(response.lastIndexOf(":")+1));
				spieler.KreditLaufzeit = KreditLaufzeitNeu;
				break;
			case 9:
				int Runde = Integer.parseInt(response.substring(response.lastIndexOf(":")+1));
				this.runde = Runde;
				break;
			case 10:
				int NaechsteRunde = Integer.parseInt(response.substring(response.lastIndexOf(":")+1));
				runde=NaechsteRunde;
				nextRound();
				break;
			case 14:
				int ID = Integer.parseInt(response.substring(response.lastIndexOf(":")+1));
				this.ID = ID;
				break;
			case 15:
				//     -RundenDaten:1/250000/250000/0/5000/0/20.0/0
				String RundenDaten = response.substring(response.lastIndexOf(":")+1);
				//     1/250000/250000/0/5000/0/20.0/0
				int RRunde = Integer.parseInt(RundenDaten.substring(0, RundenDaten.indexOf('/')));
				System.out.println("Rundendaten: Runde: "+RRunde);

				RundenDaten = RundenDaten.substring(RundenDaten.indexOf('/')+1);
				//     250000/250000/0/5000/0/20.0/0
				int RKapital = Integer.parseInt(RundenDaten.substring(0, RundenDaten.indexOf('/')));
				System.out.println("Rundendaten: Kapital: "+RKapital);

				RundenDaten = RundenDaten.substring(RundenDaten.indexOf('/')+1);
				//     250000/250000/0/5000/0/20.0/0
				int RGeldaenderung = Integer.parseInt(RundenDaten.substring(0, RundenDaten.indexOf('/')));
				System.out.println("Rundendaten: Geldaenderung: "+RGeldaenderung);

				RundenDaten = RundenDaten.substring(RundenDaten.indexOf('/')+1);
				//     250000/0/5000/0/20.0/0
				int RGeldkapital = Integer.parseInt(RundenDaten.substring(0, RundenDaten.indexOf('/')));
				System.out.println("Rundendaten: Geldkapital: "+RGeldkapital);

				RundenDaten = RundenDaten.substring(RundenDaten.indexOf('/')+1);
				//     0/5000/0/20.0/0
				int RLager = Integer.parseInt(RundenDaten.substring(0, RundenDaten.indexOf('/')));
				System.out.println("Rundendaten: Lager: "+RLager);

				RundenDaten = RundenDaten.substring(RundenDaten.indexOf('/')+1);
				//     5000/0/20.0/0
				int RLagermenge = Integer.parseInt(RundenDaten.substring(0, RundenDaten.indexOf('/')));
				System.out.println("Rundendaten: Lagermenge: "+RLagermenge);

				RundenDaten = RundenDaten.substring(RundenDaten.indexOf('/')+1);
				//     0/20.0/0
				int RVerkaufsmenge = Integer.parseInt(RundenDaten.substring(0, RundenDaten.indexOf('/')));
				System.out.println("Rundendaten: Verkaufsmenge: "+RVerkaufsmenge);

				RundenDaten = RundenDaten.substring(RundenDaten.indexOf('/')+1);
				//     20.0/0
				float RZinssatz = Float.parseFloat(RundenDaten.substring(0, RundenDaten.indexOf('/')));
				System.out.println("Rundendaten: Zinssatz: "+RZinssatz);

				RundenDaten = RundenDaten.substring(RundenDaten.indexOf('/')+1);
				//     0
				int RGenommenerKredit = Integer.parseInt(RundenDaten);
				System.out.println("Rundendaten: GenommenerKredit: "+RGenommenerKredit);
				//public RundenDaten(int Runde, int Kapital, int Geldkapital, int Geldaenderung,int Lager, int Lagermenge, int Verkaufsmenge, float Kreditprozentsatz, int Kreditnahme)
				RundenDaten rd = new RundenDaten(RRunde, RKapital, RGeldkapital, RGeldaenderung, RLager, RLagermenge, RVerkaufsmenge, RZinssatz, RGenommenerKredit);
				rundenDaten.add(rd);
				break;
			case 18:
				//}else if(received.contains("Abbaukosten:")) {
				//	cw.ResponseBearbeiten(18, received);
				//}
				AbbauKosten = Integer.parseInt(response.substring(response.lastIndexOf(":")+1));
				break;
		}
	}

}
