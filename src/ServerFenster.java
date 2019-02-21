import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


//libs ben�tigt f�r serverzeugs
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;



////https://jameschambers.co/writing/git-team-workflow-cheatsheet/


public class ServerFenster extends JFrame {

    /**
     *
     */

    private DatenbankHandler db;
    private static final long serialVersionUID = 1L;
    JLabel lblRunde = new JLabel("Runde: 0");
    private JPanel contentPane;
    private JTextField txtStartkapital;
    private JTextField txtKreditzinssatz;
    private JTextField txtMaximalKredit;
    JLabel lblAktuellerMarktbedarft = new JLabel("Aktueller Marktbedarf: 0t");
    private JTextField txtMaximaleSpieler;
    JLabel lblAktuellerMarktpreis = new JLabel("Aktueller Marktpreis: 25€");
    JButton btnSpielStarten = new JButton("Spiel eröffnen");
    private JTable table;
    private int Spieleranzahl = 0;

    public List<Spieler> spieler = new ArrayList<>();
    public Markt markt = new Markt(spieler);
    private JTextField textField;
    private JLabel lblNachdemDasSpiel;
    JButton btnNaechsteRunde = new JButton("Erste Runde");

    private int letzteRunde = 0;
    private boolean spielGestartet=false;


    public int Runde=0,
    startKapital=250000,
    maximalerKredit=500000,
    maximaleSpielerAnzahl=5,
    startKreditLaufzeit=4,
    maxErzmenge=1000000;
    boolean startKapitalKredit=true;
    public float startKreditZinssatz=20.0f;
    private JTextField txtErzmenge;
    private JTextField textField_1;
    private JTextField txtBenutzer;
    private JTextField txtPass;
    private JButton btnSpielBeenden;
    private JLabel lblIP;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ServerFenster frame = new ServerFenster();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void NaechsteRunde() {
        db.RundeLoggen();
        Runde++;
        lblRunde.setText("Runde: "+Runde);
        markt.NaechsteRunde();
        lblAktuellerMarktpreis.setText("Aktueller Marktpreis: "+markt.preis+'€');
        //JLabel lblAktuellerMarktbedarft = new JLabel("Aktueller Marktbedarf: 0t");
        lblAktuellerMarktbedarft.setText("Aktueller Marktbedarf: "+markt.marktBedarf+'t');

        for(int i=0;i<spieler.size();i++) {
            Spieler s = spieler.get(i);
            s.isReady = false;
            if(letzteRunde!=2)sendToOut(s.Socket, "10NaechsteRunde:"+Runde);
        }
        updateTable();
        btnNaechsteRunde.setEnabled(false);
        btnNaechsteRunde.setToolTipText("Noch nicht jeder Spieler ist bereit.");
    }

    public void runServer(int port, boolean message) throws IOException{
        ServerSocket ss = new ServerSocket(port); //Neuen Server auf Port port eröffnen
        btnNaechsteRunde.setEnabled(true); //NaechsteRunde Knopf aktivieren
        if(message)JOptionPane.showMessageDialog(null, "Der Server wurde erfolgreich gestartet und wartet nun auf Spieler.");
        System.out.println("Server: Server gestartet. Werte: Startkapital: "+startKapital+" Maximaler Kredit: "+maximalerKredit+" "
                + "Kreditzinssatz: " + startKreditZinssatz + "% Maximale Spieler: "+maximaleSpielerAnzahl); //Debugging
        markt.SetErzbestand(maxErzmenge); //Maximalen Erzbestand an Markt uebergeben
        while (true) { //Unendlicher Loop um Clients zu akzeptieren
            Socket s = null; //Neuen Sockel fuer neue Clients erstellen
            System.out.println("Server: Halte Ausschau nach Clients..");
            try { //Versuchen mit Client zu kommunizieren
                s = ss.accept(); //Client akzeptieren
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); //Datenoutputstream zu Client erstellen
                if(!spielGestartet) { //wenn das Spiel noch nicht gestartet wurde client akzeptieren
                    System.out.println("Server: Neuer Client verbunden: " + s);

                    DataInputStream dis = new DataInputStream(s.getInputStream()); //Dateninputstream von client erstellen

                    System.out.println("Server: Neuen Thread für diesen Client erstellen");

                    Thread t = new ClientHandler(this, s, dis, dos); //Thread fuer diesen Client erstellen
                    t.start(); //Thread starten

                }else { // wenn das spiel schon gestartet wurde client abweisen
                    sendToOut(s, "spielgestartet");
                    System.out.println("Server: Verbundener Client " + s + " wird gekickt weil das Spiel bereits begonnen hat.");
                    s.close(); //Clientverbindung schließen
                    dos.close(); //Datenoutputstream schließen
                }
            }catch(Exception e) {
                s.close(); //Clientverbindung schließen, wenn Kommunikation nicht möglich ist
                e.printStackTrace();
            }
        }
    }


    //Methode um die Servertabelle mit den aktuellsten Werten upzudaten.
    public void updateTable() {
        int bereiteSpieler = 0;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for(int i=0;i<spieler.size();i++) {
            Spieler s = spieler.get(i);
            model.addRow(new Object[] {i, s.isReady, s.Name, s.Kapital, s.Geldkapital, s.Geldaenderung, s.LagerMax, s.Lager, s.Lagermenge, s.Verkaufsmenge, s.Kredit, s.Zinssatz, s.KreditLaufzeit, s.Socket.getRemoteSocketAddress().toString()});
            if(s.isReady)bereiteSpieler++;
        }
        //Wenn alle Spieler bereit sind den Nächste-Runde-Knopf aktivieren
        if(bereiteSpieler==spieler.size()) {
            btnNaechsteRunde.setEnabled(true);
            btnNaechsteRunde.setToolTipText("");
            bereiteSpieler=0;
        }

    }

    //Eine Nachricht an alle verbundenen Spieler senden
    public void sendeAnAlle(String msg) {
        for(int i=0;i<spieler.size();i++) {
            Spieler s = spieler.get(i);
            sendToOut(s.Socket,msg);
        }
    }

    public ServerFenster() {
        setResizable(false);
        setTitle("Wirtschaftssim by BDM & Co.");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 762, 419);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblStartkapital = new JLabel("Startkapital");
        lblStartkapital.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblStartkapital.setBounds(10, 11, 84, 14);
        contentPane.add(lblStartkapital);

        txtStartkapital = new JTextField();
        txtStartkapital.setText("250000");
        txtStartkapital.setBounds(104, 8, 59, 20);
        contentPane.add(txtStartkapital);
        txtStartkapital.setColumns(10);

        JCheckBox checkStartkapitalKredit = new JCheckBox("Startkapital ist Kredit");
        checkStartkapitalKredit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(checkStartkapitalKredit.isSelected()) {
                    txtKreditzinssatz.setEnabled(true);
                    textField_1.setEnabled(true);
                }else {
                    txtKreditzinssatz.setEnabled(false);
                    textField_1.setEnabled(false);
                }
            }
        });
        checkStartkapitalKredit.setSelected(true);
        checkStartkapitalKredit.setFont(new Font("Tahoma", Font.PLAIN, 11));
        checkStartkapitalKredit.setBounds(10, 58, 177, 23);
        contentPane.add(checkStartkapitalKredit);

        JLabel lblZinssatz = new JLabel("Kreditzinssatz");
        lblZinssatz.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblZinssatz.setBounds(10, 115, 98, 14);
        contentPane.add(lblZinssatz);

        txtKreditzinssatz = new JTextField();
        txtKreditzinssatz.setText("20");
        txtKreditzinssatz.setColumns(10);
        txtKreditzinssatz.setBounds(104, 112, 59, 20);
        contentPane.add(txtKreditzinssatz);

        JLabel lblMaximalerKreditIn = new JLabel("Max. Kredit");
        lblMaximalerKreditIn.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblMaximalerKreditIn.setBounds(10, 36, 112, 14);
        contentPane.add(lblMaximalerKreditIn);

        JLabel label = new JLabel("\u20AC");
        label.setBounds(167, 11, 16, 14);
        contentPane.add(label);

        txtMaximalKredit = new JTextField();
        txtMaximalKredit.setText("500000");
        txtMaximalKredit.setColumns(10);
        txtMaximalKredit.setBounds(104, 33, 59, 20);
        contentPane.add(txtMaximalKredit);

        JLabel label_1 = new JLabel("\u20AC");
        label_1.setBounds(167, 36, 16, 14);
        contentPane.add(label_1);

        JLabel label_2 = new JLabel("%");
        label_2.setBounds(167, 115, 16, 14);
        contentPane.add(label_2);

        txtMaximaleSpieler = new JTextField();
        txtMaximaleSpieler.setText("5");
        txtMaximaleSpieler.setColumns(10);
        txtMaximaleSpieler.setBounds(117, 289, 47, 20);
        contentPane.add(txtMaximaleSpieler);

        JLabel lblMaximaleSpieler = new JLabel("Maximale Spieler");
        lblMaximaleSpieler.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblMaximaleSpieler.setBounds(11, 292, 98, 14);
        contentPane.add(lblMaximaleSpieler);


        btnSpielStarten.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSpielStarten.setEnabled(false);
                System.out.println("Server: Versuche Server zu starten..");
                //Neuen Thread fuer den Serverstart erstellen
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //Textfelder nach Spielstart deaktivieren, da Werte nun feststehen
                        textField.setEnabled(false);
                        txtErzmenge.setEnabled(false);
                        textField_1.setEnabled(false);
                        txtStartkapital.setEnabled(false);
                        txtMaximalKredit.setEnabled(false);
                        txtKreditzinssatz.setEnabled(false);
                        txtMaximaleSpieler.setEnabled(false);
                        checkStartkapitalKredit.setEnabled(false);
                        //Werte zu Variablen uebergeben
                        int port = Integer.parseInt(textField.getText());
                        startKreditLaufzeit = Integer.parseInt(textField_1.getText());
                        maxErzmenge = Integer.parseInt(txtErzmenge.getText());
                        startKapital = Integer.parseInt(txtStartkapital.getText());
                        maximalerKredit = Integer.parseInt(txtMaximalKredit.getText());
                        startKreditZinssatz = Float.parseFloat(txtKreditzinssatz.getText());
                        startKapitalKredit = checkStartkapitalKredit.isSelected();
                        maximaleSpielerAnzahl = Integer.parseInt(txtMaximaleSpieler.getText());
                        try {
                            runServer(port, true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                //Jetzt wo der Server erfolgreich gestartet werden kann, kann eine Verbindung zur Datenbank hergestellt werden.
                System.out.println("Server: Server erfolgreich gestartet.");
                System.out.println("Server: Versuche zur Datenbank zu verbinden. (127.0.0.1:3306 - Wirtschaftssimulation)");
                //Datenbankverbindung anhand der im ServerFenster angegebenen Daten herstellen (zu localhost)
                db = new DatenbankHandler(ServerFenster.this, spieler, "127.0.0.1", 3306, "WirtschaftsSimulation",txtBenutzer.getText(), txtPass.getText());
                //Neuen Thread fuer den Datenbankhandler erstellen und starten
                Thread t = db;
                db.start();
            }
        });
        btnSpielStarten.setBounds(6, 317, 158, 23);
        contentPane.add(btnSpielStarten);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane.setBounds(236, 4, 506, 207);
        contentPane.add(scrollPane);


        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
            new Object[][] {
            },
            new String[] {
                "ID", "Bereit", "Name", "Kapital", "Geldkapital", "Geld\u00E4nderung", "Max. Lager", "Lagerbestand", "Lagermenge", "Verkaufsmenge", "Kredit", "Zinssatz", "Kreditlaufzeit", "IP"
            }
        ));
        table.getColumnModel().getColumn(0).setPreferredWidth(26);
        table.getColumnModel().getColumn(1).setPreferredWidth(39);
        table.getColumnModel().getColumn(5).setPreferredWidth(82);
        table.getColumnModel().getColumn(7).setPreferredWidth(84);
        table.getColumnModel().getColumn(9).setPreferredWidth(89);
        table.getColumnModel().getColumn(13).setPreferredWidth(111);


        btnNaechsteRunde.setEnabled(false);
        btnNaechsteRunde.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!spielGestartet) {
                    db.SpielerHinzufuegen();
                    Runde = 1;
                    lblRunde.setText("Runde: 1");
                    lblNachdemDasSpiel.setVisible(false);
                    spielGestartet=true;
                    btnNaechsteRunde.setText("Runde beenden und nächste Runde starten.");
                    btnNaechsteRunde.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Die erste Runde wurde gestartet!");
                    btnSpielBeenden.setEnabled(true);
                    btnNaechsteRunde.setEnabled(false);
                    btnNaechsteRunde.setToolTipText("Noch nicht jeder Spieler ist bereit.");
                    markt.GameStart = false;
                    //Spieler GUIs öffnen
                    for(int i=0;i<spieler.size();i++) {
                        Spieler s = spieler.get(i);
                        try {
                            DataOutputStream dos = new DataOutputStream(s.Socket.getOutputStream());
                            dos.writeUTF("gameStart");
                        } catch (IOException ex) {
                            System.out.println("Server: Konnte GameStart nicht an Client: "+s.Name+" senden.");
                        }
                    }
                }else {
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Möchten Sie die Runde wirklich beenden?");
                    if(dialogResult == JOptionPane.YES_OPTION) {
                        NaechsteRunde();
                        if(letzteRunde==1) {
                            letzteRunde=2;
                            btnNaechsteRunde.setText("Diese Runde und damit das Spiel beenden.");
                        }else if(letzteRunde==2) {
                            Spieler winner = new Spieler();
                            int max = Integer.MIN_VALUE;
                            for(int i=0;i<spieler.size();i++) {
                                Spieler s = spieler.get(i);
                                if(s.Kapital > max) {
                                    max = s.Kapital;
                                    winner = s;
                                }
                            }
                            db.RundeLoggen();

                            for(int i=0;i<spieler.size();i++) {
                                if(spieler.get(i)==winner) {
                                    sendToOut(spieler.get(i).Socket, "!winner!");
                                }else sendToOut(spieler.get(i).Socket, "!loser!");
                            }

                            sendeAnAlle("!gameEnd!");
                            JOptionPane.showMessageDialog(null, "Das Spiel ist vorbei.");
                        }
                    }
                }
            }
        });
        btnNaechsteRunde.setBounds(7, 351, 512, 23);
        contentPane.add(btnNaechsteRunde);

        textField = new JTextField();
        textField.setText("23554");
        textField.setColumns(10);
        textField.setBounds(116, 263, 47, 20);
        contentPane.add(textField);

        JLabel lblPort = new JLabel("Port");
        lblPort.setFont(new Font("Dialog", Font.PLAIN, 11));
        lblPort.setBounds(10, 266, 98, 14);
        contentPane.add(lblPort);

        lblNachdemDasSpiel = new JLabel("Nachdem die erste Runde gestartet wurde werden keine Verbindungen mehr angenommen.");
        lblNachdemDasSpiel.setFont(new Font("Dialog", Font.PLAIN, 11));
        lblNachdemDasSpiel.setBounds(10, 238, 591, 16);
        contentPane.add(lblNachdemDasSpiel);


        lblRunde.setBounds(181, 320, 84, 16);
        contentPane.add(lblRunde);

        txtErzmenge = new JTextField();
        txtErzmenge.setText("1000000");
        txtErzmenge.setColumns(10);
        txtErzmenge.setBounds(104, 138, 83, 20);
        contentPane.add(txtErzmenge);

        JLabel lblErzmenge = new JLabel("Erzmenge");
        lblErzmenge.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblErzmenge.setBounds(10, 141, 98, 14);
        contentPane.add(lblErzmenge);

        JLabel lblT = new JLabel("t");
        lblT.setBounds(202, 141, 16, 14);
        contentPane.add(lblT);


        lblAktuellerMarktpreis.setBounds(181, 266, 237, 14);
        contentPane.add(lblAktuellerMarktpreis);

        JLabel lblKreditlaufzeit = new JLabel("Kreditlaufzeit");
        lblKreditlaufzeit.setFont(new Font("Dialog", Font.PLAIN, 11));
        lblKreditlaufzeit.setBounds(10, 89, 98, 14);
        contentPane.add(lblKreditlaufzeit);

        textField_1 = new JTextField();
        textField_1.setText("4");
        textField_1.setColumns(10);
        textField_1.setBounds(104, 86, 59, 20);
        contentPane.add(textField_1);

        JLabel lblRunden = new JLabel("Runden");
        lblRunden.setBounds(167, 89, 59, 14);
        contentPane.add(lblRunden);

        txtBenutzer = new JTextField();
        txtBenutzer.setText("root");
        txtBenutzer.setBounds(628, 289, 114, 20);
        contentPane.add(txtBenutzer);
        txtBenutzer.setColumns(10);

        txtPass = new JTextField();
        txtPass.setText("");
        txtPass.setColumns(10);
        txtPass.setBounds(628, 318, 114, 20);
        contentPane.add(txtPass);

        JLabel lblMysqlbenutzer = new JLabel("MySQL-Benutzer");
        lblMysqlbenutzer.setBounds(456, 291, 145, 14);
        contentPane.add(lblMysqlbenutzer);

        JLabel lblPasswort = new JLabel("Passwort");
        lblPasswort.setBounds(456, 320, 145, 14);
        contentPane.add(lblPasswort);

        btnSpielBeenden = new JButton("Spiel beenden");
        btnSpielBeenden.setEnabled(false);
        btnSpielBeenden.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(null, "Das Spiel wird nach der nächsten Runde beendet.");
                letzteRunde=1;
                sendeAnAlle("!lastRound!");
                btnSpielBeenden.setEnabled(false);
            }
        });
        btnSpielBeenden.setBounds(531, 351, 211, 23);
        contentPane.add(btnSpielBeenden);

        try {
            lblIP = new JLabel("IP-Adresse: "+InetAddress.getLocalHost().getHostAddress().toString());
        } catch (UnknownHostException e1) {
            lblIP = new JLabel("IP-Adresse: unbestimmbar");
        }
        lblIP.setBounds(456, 265, 286, 14);
        contentPane.add(lblIP);


        lblAktuellerMarktbedarft.setBounds(181, 292, 237, 14);
        contentPane.add(lblAktuellerMarktbedarft);


    }

    public void sendToOut(Socket s, String msg) {
        try {
            DataOutputStream d = new DataOutputStream(s.getOutputStream());
            d.writeUTF(msg);
            System.out.println("Server[ich]->Client["+s.getRemoteSocketAddress().toString()+"]: "+"\""+msg+"\"");

        } catch (IOException e) {
            System.out.println("Fehler bei sendToOut. Nachricht an Client["+s.getRemoteSocketAddress().toString()+"]: "+"\""+msg+"\"");
        }
    }
}
