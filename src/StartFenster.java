import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class StartFenster extends JFrame {

	private JPanel contentPane;
	ServerFenster server = new ServerFenster();
	VerbindungsFenster client = new VerbindungsFenster();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartFenster frame = new StartFenster();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartFenster() {
		setTitle("Wirtschaftssim by BDM & Co.");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 122);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnServerHosten = new JButton("Server hosten");
		btnServerHosten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				server.setVisible(true);//Serverfenster zeigen
			}
		});
		btnServerHosten.setBounds(10, 45, 120, 23);
		contentPane.add(btnServerHosten);

		JButton btnClientStarten = new JButton("Client starten");
		btnClientStarten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.setVisible(true); //Clientverbindungsfenster zeigen
			}
		});
		btnClientStarten.setBounds(10, 11, 120, 23);
		contentPane.add(btnClientStarten);

		JLabel lblImNetzwerkNach = new JLabel("Test");
		lblImNetzwerkNach.setBounds(154, 15, 213, 14);
		contentPane.add(lblImNetzwerkNach);

		JButton btnSchnellstart = new JButton("Schnellstart");
		btnSchnellstart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				server.setVisible(true);
					new Thread() {
						public void run() {
							try {
								server.runServer(23554, false);
								server.btnSpielStarten.setEnabled(false);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}.start();
					new Thread() {
						public void run() {
							try {
								client.connectToServer("127.0.0.1", 23554, "DBM_Quickstarter", false);
								server.btnNaechsteRunde.doClick();
								this.interrupt();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}.start();

			}
		});
		btnSchnellstart.setBounds(140, 45, 120, 23);
		btnSchnellstart.setEnabled(false);
		contentPane.add(btnSchnellstart);
	}
}
