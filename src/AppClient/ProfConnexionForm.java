package AppClient;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class ProfConnexionForm extends JFrame 
{
	JLabel labelAdr,labelUser,labelMdp,labelPort,labelNorth, labelSouth;
	JTextField textAdr,textUser, textPort;
	JPasswordField mdpUser;
	JButton valider, annuler;
	JPanel borderPanel,gridPanel,flowPanel1,flowPanel2;;
	Border borderLine;
	
	String ip;
	int port;
	Client client;
	
	public ProfConnexionForm(Client x)
	{
		client=x;
		initFrame();
	}
	
	private void initFrame() {
		this.setTitle ("Espace de Professeur") ;
		this.setSize(800, 275);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
		labelNorth= new JLabel("Plateforme d'examen en ligne");
		labelNorth.setFont(new Font("Georgia",Font.BOLD, 18));
		labelNorth.setForeground(new Color(35,185,185));
		flowPanel1=new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		flowPanel1.setBackground(new Color(10,10,50));
		flowPanel1.add(labelNorth);
		
		labelSouth= new JLabel("R�alis� par : Ossama Ismaili 26/06/2020",SwingConstants.CENTER);
		labelSouth.setFont(new Font("Arial",Font.ITALIC, 12));
		labelSouth.setForeground(Color.white);
		flowPanel2=new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		flowPanel2.setBackground(new Color(10,10,50));
		flowPanel2.add(labelSouth);
		
		labelAdr  = new JLabel("Adresse IP du serveur : ", SwingConstants.RIGHT);
		labelAdr.setForeground(Color.white);
		labelUser = new JLabel("Nom Utilisateur :", SwingConstants.RIGHT);
		labelUser.setForeground(Color.white);
		labelMdp = new JLabel("Mot de passe :", SwingConstants.RIGHT);
		labelMdp.setForeground(Color.white);
		labelPort = new JLabel("Numero de Port :", SwingConstants.RIGHT);
		labelPort.setForeground(Color.white);
		 
		try {
			ip = InetAddress.getLocalHost().getHostAddress().toString();
			port=3000;
		} catch (UnknownHostException e1) {
			System.out.println("Impossible de resoudre l'adresse du serveur!");
		}
		textAdr   = new JTextField(ip, 18);
		textAdr.setPreferredSize(new Dimension(160,30));
		textAdr.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		textPort  = new JTextField(String.valueOf(port), 18);
		textPort.setPreferredSize(new Dimension(160,30));
		textPort.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		textUser  = new JTextField(18);
		textUser.setPreferredSize(new Dimension(160,30));
		textUser.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		mdpUser  = new JPasswordField(18);
		mdpUser.setPreferredSize(new Dimension(160,30));
		mdpUser.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		valider   = new JButton("Valider");
		valider.setPreferredSize(new Dimension(150,30));
		valider.setBackground(new Color(59, 89, 182));
		valider.setForeground(Color.white);
		valider.setBorder(null);
		annuler   = new JButton("Annuler");
		annuler.setPreferredSize(new Dimension(150,30));
		annuler.setBackground(new Color(59, 89, 182));
		annuler.setForeground(Color.white);
		annuler.setBorder(null);
		
		//programmation de btn annuler
		annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		//programmation de btn valider
		valider.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textAdr.getText().equals("") || textPort.getText().equals("") || textUser.getText().equals("") || String.valueOf(mdpUser.getPassword()).equals("")) {
					JOptionPane.showMessageDialog(null, "Veuillez saisie tous les champs", "Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					client.clientName=textUser.getText();
					client.clientMdp= String.valueOf(mdpUser.getPassword());
					client.ipServer=textAdr.getText();
					client.port=Integer.parseInt(textPort.getText());
					client.openConnexion("prof"); //connecte au serveur avec message 'prof' pour acc�der � espace de prof
					try {
						if(client.dataIn.readBoolean()) {
							dispose();//fermer le formulaire de connexion
							client.profForm = new ProfForm(client);//lancer form de prof
						}
						else {
							JOptionPane.showMessageDialog(null, "Nom d'utilisateur ou le mot de passe est incorrect", "Error",JOptionPane.ERROR_MESSAGE);
							client.deconnecte();
						}
					} catch (IOException e1) {
						System.out.println("Probleme de connexion au serveur");
					}
					
				}
			}
		});
		
		borderLine= BorderFactory.createLineBorder(new Color(20,100,150));
		
		gridPanel = new JPanel(new GridBagLayout());
		gridPanel.setBackground(new Color(10,10,50));
		gridPanel.setBorder(borderLine);
		
		GridBagConstraints container = new GridBagConstraints();
		container.gridx = 0;
		container.gridy = 0;
		container.weightx=10;
		container.weighty=5;
		gridPanel.add(labelUser,container);
		container.gridx++;
		gridPanel.add(textUser,container);
		container.gridx++;
		gridPanel.add(labelAdr,container);
		container.gridx++;
		gridPanel.add(textAdr,container);
		container.gridy++;
		container.gridx = 0;
		gridPanel.add(labelMdp,container);
		container.gridx++;
		gridPanel.add(mdpUser,container);
		container.gridx++;
		gridPanel.add(labelPort,container);
		container.gridx++;
		gridPanel.add(textPort,container);
		container.fill = GridBagConstraints.NONE;
		container.gridx=0;
		container.gridy++;
		gridPanel.add(valider, container);
		container.gridx++;
		gridPanel.add(annuler,container);
		
		borderPanel = new JPanel(new BorderLayout(10,10));		
		borderPanel.add(gridPanel);
		borderPanel.add(flowPanel1, "North");
		borderPanel.add(flowPanel2, "South");
		borderPanel.setBackground(new Color(10,10,50));
		
		this.setContentPane(borderPanel);
				
		setVisible(true);
	}
}
