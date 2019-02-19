import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatenbankHandler extends Thread{
	private final ServerFenster sf;
	private final String ip;
	private final int port;
	private final String Datenbankname;
	private final String Nutzer;
	private final String Passwort;
	public List<Spieler> spieler = new ArrayList<>();
	Connection con;


	public DatenbankHandler(ServerFenster sf, List<Spieler> spieler, String ip, int port, String Datenbankname, String Nutzer, String Passwort) {
		this.sf = sf;
		this.ip = ip;
		this.port = port;
		this.Datenbankname = Datenbankname;
		this.Nutzer = Nutzer;
		this.Passwort = Passwort;
		this.spieler = spieler;
	}

	@Override
	public void run() {
		System.out.println("DatenbankHandler: Versuche nun zu Datenbank zu verbinden.");
		System.out.println("->Werte: "+ip+":"+port+" DB: "+Datenbankname);

		try {
			this.con = DriverManager.getConnection("jdbc:mysql://" +ip+":"+port+"/"+Datenbankname, Nutzer, Passwort);
			System.out.println("DatenbankHandler: Erfolgreich zur Datenbank verbunden.");
			clearAlles();
		} catch (SQLException e) {
			System.out.println("DatenbankHandler: "+e.getMessage());
		}
	}

	public void sendQuery(String sqlQuery) {
		try {
			this.con.createStatement().execute(sqlQuery);
		} catch (SQLException e) {
			System.out.println("DatenbankHandler: "+e.getMessage());
		}
	}

	public void SpielerHinzufuegen() {
		String msg;
		for(int i=0; i<spieler.size();i++){
			Spieler s = spieler.get(i);
			msg="INSERT INTO spieler VALUES (NULL, '"+s.Name+"', NULL, NULL, NULL, NULL, NULL, NULL,NULL, NULL, NULL)";
			System.out.println("DatenbankHandler: SQL Query:\""+msg+'"');
			sendQuery(msg);
		}
	}

	public void RundeLoggen() {
		String msg;
		for(int i=0; i<spieler.size();i++){
			Spieler s = spieler.get(i);
			msg="INSERT into runden VALUES (NULL,"+sf.Runde +','+ i + ',' +s.Kapital + ',' + s.Geldkapital +','+ s.Geldaenderung +',' +s.Lager +','+ s.Lagermenge+ ','+ s.Verkaufsmenge+ ',' +s.Zinssatz+',' +s.GenommenerKredit+")";
			System.out.println("DatenbankHandler: SQL Query:\""+msg+'"');
			sendQuery(msg);


			/*
			 * this.Runde = Runde;
			 * this.Kapital = Kapital;
				this.Geldkapital = Geldkapital;
				this.Geldaenderung = Geldaenderung;
				this.Lager = Lager;
				this.Lagermenge = Lagermenge;
				this.Verkaufsmenge = Verkaufsmenge;
				this.Kreditprozentsatz = Kreditprozentsatz;
				this.Kreditnahme = Kreditnahme;
			 *
			 */
			msg = "-RundenDaten:"+sf.Runde+'/'+s.Kapital+'/'+s.Geldaenderung+'/'+s.Geldkapital+'/'+s.Lager+'/'+s.Lagermenge+'/'+s.Verkaufsmenge+'/'+s.Zinssatz+'/'+s.GenommenerKredit;
			sf.sendToOut(s.Socket, msg);
		}
	}

	public void clearAlles() {
		String msg;
		msg = "TRUNCATE TABLE runden";
		sendQuery(msg);
		System.out.println("DatenbankHandler: SQL Query:\""+msg+'"');
		msg = "TRUNCATE TABLE spieler";
		sendQuery(msg);
		System.out.println("DatenbankHandler: SQL Query:\""+msg+'"');
	}
}
