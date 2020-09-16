package AppServeur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServeurFenetre extends JFrame {
	Serveur serveur;
	JPanel mainPanel;
	JPanel northPanel;
	JPanel clientsListPanel;
	JPanel clientInfo;
	JPanel gridPanel;
	JPanel southPanel;
	JLabel usernameLabel,statutLabel,noteLabel,fullnameLabel;
	JTextField clientSelectionne;
	JButton decBtn,infoBtn;
	
	ServeurFenetre(Serveur x){
		serveur=x;
		initFrame();
	}
	
	private void initFrame() {
		this.setSize(800,400);
		this.setLocationRelativeTo(null);
		this.setTitle ("À communiquer aux clients [ IP:"+serveur.ip+" , Port: "+serveur.port+" ]") ;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainPanel=new JPanel(new BorderLayout());
		
		JLabel label=new JLabel("Liste des clients : ");
		label.setFont(new Font("Arial", Font.PLAIN, 24));
		label.setForeground(Color.white);
		northPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
		northPanel.setBackground(new Color(10,10,50));
		northPanel.add(label);
		
		clientInfo= new JPanel(new GridLayout(4, 2));
		clientInfo.setBackground(new Color(10,10,50));
		
		label=new JLabel("Nom d'utlisateur");
		label.setFont(new Font("Arial", 0, 20));
		label.setForeground(Color.white);
		label.setBorder(BorderFactory.createLineBorder(new Color(50,150,250),2));
		clientInfo.add(label);
		usernameLabel=new JLabel("");
		usernameLabel.setFont(new Font("Arial", 0, 20));
		usernameLabel.setForeground(Color.white);
		usernameLabel.setBorder(BorderFactory.createLineBorder(new Color(50,150,250),2));
		clientInfo.add(usernameLabel);
		
		label=new JLabel("Nom complet");
		label.setFont(new Font("Arial", 0, 20));
		label.setForeground(Color.white);
		label.setBorder(BorderFactory.createLineBorder(new Color(50,150,250),2));
		clientInfo.add(label);
		fullnameLabel=new JLabel("");
		fullnameLabel.setFont(new Font("Arial", 0, 20));
		fullnameLabel.setForeground(Color.white);
		fullnameLabel.setBorder(BorderFactory.createLineBorder(new Color(50,150,250),2));
		clientInfo.add(fullnameLabel);
		
		label=new JLabel("Statut");
		label.setFont(new Font("Arial", 0, 20));
		label.setForeground(Color.white);
		label.setBorder(BorderFactory.createLineBorder(new Color(50,150,250),2));
		clientInfo.add(label);
		statutLabel=new JLabel("");
		statutLabel.setFont(new Font("Arial", 0, 20));
		statutLabel.setForeground(Color.white);
		statutLabel.setBorder(BorderFactory.createLineBorder(new Color(50,150,250),2));
		clientInfo.add(statutLabel);
		
		label=new JLabel("Note");
		label.setFont(new Font("Arial", 0, 20));
		label.setForeground(Color.white);
		label.setBorder(BorderFactory.createLineBorder(new Color(50,150,250),2));
		clientInfo.add(label);
		noteLabel=new JLabel("");
		noteLabel.setFont(new Font("Arial", 0, 20));
		noteLabel.setForeground(Color.white);
		noteLabel.setBorder(BorderFactory.createLineBorder(new Color(50,150,250),2));
		clientInfo.add(noteLabel);
		
		clientsListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
		clientsListPanel.setBackground(new Color(10,10,50));
		clientsListPanel.setBorder(BorderFactory.createLineBorder(new Color(50,150,250),2));

		gridPanel=new JPanel(new GridLayout(1,2));
		gridPanel.setBackground(new Color(10,10,50));
		gridPanel.add(clientsListPanel);
		gridPanel.add(clientInfo);
		
		clientSelectionne=new JTextField(20);
		clientSelectionne.setPreferredSize(new Dimension(300,30));
		clientSelectionne.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		ImageIcon decIcon=new ImageIcon("./ressources/warning.png");
		decBtn=new JButton("Déconnecter");
		decBtn.setPreferredSize(new Dimension(150,30));
		decBtn.setBackground(new Color(220, 50, 50));
		decBtn.setIcon(decIcon);
		decBtn.setForeground(Color.white);
		decBtn.setBorder(null);
		
		decBtn.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				serveur.deconnecteClient(clientSelectionne.getText());
			}
		});
		
		ImageIcon infoIcon=new ImageIcon("./ressources/info.png");
		infoBtn=new JButton("Informations");
		infoBtn.setPreferredSize(new Dimension(150,30));
		infoBtn.setBackground(new Color(50, 220, 50));
		infoBtn.setIcon(infoIcon);
		infoBtn.setForeground(Color.white);
		infoBtn.setBorder(null);
		
		infoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				serveur.infoClient(clientSelectionne.getText());
			}
		});
		
		southPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
		southPanel.setBackground(new Color(10,10,50));
		southPanel.add(clientSelectionne);
		southPanel.add(infoBtn);
		southPanel.add(decBtn);
	
		mainPanel.add(northPanel,"North");
		mainPanel.add(gridPanel,"Center");
		mainPanel.add(southPanel,"South");
		this.setContentPane(mainPanel);
		this.setVisible(true);
	}
}
