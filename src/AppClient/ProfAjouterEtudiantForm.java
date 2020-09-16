package AppClient;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class ProfAjouterEtudiantForm extends JFrame {
	JLabel usernameLabel,fullnameLabel,labelMdp,labelNorth;
	JTextField usernameText,fullnameText, mdpUser;
	JButton ajouter, annuler;
	JPanel borderPanel,gridPanel;
	Border borderLine;
	Client client;
	
	public ProfAjouterEtudiantForm(Client x)
	{
		client=x;
		initFrame();
	}
	
	private void initFrame() {
		this.setTitle ("Ajouter Étudiant") ;
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
		labelNorth= new JLabel("Ajouter Étudiant",SwingConstants.CENTER);
		labelNorth.setFont(new Font("Georgia",Font.BOLD, 20));
		labelNorth.setForeground(new Color(35,150,220));
		usernameLabel  = new JLabel("Nom d'tilisateur : ", SwingConstants.RIGHT);
		usernameLabel.setForeground(Color.white);
		fullnameLabel = new JLabel("Nom d'Etudiant :", SwingConstants.RIGHT);
		fullnameLabel.setForeground(Color.white);
		labelMdp = new JLabel("Mot de passe :", SwingConstants.RIGHT);
		labelMdp.setForeground(Color.white);
		 
		usernameText   = new JTextField(18);
		usernameText.setPreferredSize(new Dimension(160,30));
		usernameText.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		
		fullnameText  = new JTextField(18);
		fullnameText.setPreferredSize(new Dimension(160,30));
		fullnameText.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		mdpUser  = new JTextField(18);
		mdpUser.setPreferredSize(new Dimension(160,30));
		mdpUser.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		ajouter   = new JButton("Ajouter");
		ajouter.setPreferredSize(new Dimension(150,30));
		ajouter.setBackground(new Color(59, 89, 182));
		ajouter.setForeground(Color.white);
		ajouter.setBorder(null);
		annuler   = new JButton("Annuler");
		annuler.setPreferredSize(new Dimension(150,30));
		annuler.setBackground(new Color(59, 89, 182));
		annuler.setForeground(Color.white);
		annuler.setBorder(null);
		
		//programmation de btn annuler
		annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		//programmation de btn valider
		ajouter.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(usernameText.getText().equals("") || fullnameText.getText().equals("") || mdpUser.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Veuillez saisie tous les champs", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else {
					//Envoyer infos au serveur
					client.handleAction("ajouter etudiant");
					client.handleAction(usernameText.getText());
					client.handleAction(fullnameText.getText());
					client.handleAction(mdpUser.getText());
					dispose();
				}
			}
		});
		
		borderLine= BorderFactory.createLineBorder(new Color(20,100,100));
		
		gridPanel = new JPanel(new GridBagLayout());
		gridPanel.setBackground(new Color(10,10,50));
		gridPanel.setBorder(borderLine);
		
		GridBagConstraints container = new GridBagConstraints();
		container.gridx = 0;
		container.gridy = 0;
		container.weightx=10;
		container.weighty=5;
		gridPanel.add(usernameLabel,container);
		container.gridx++;
		gridPanel.add(usernameText,container);
		container.gridy++;
		container.gridx=0;
		gridPanel.add(fullnameLabel,container);
		container.gridx++;
		gridPanel.add(fullnameText,container);
		container.gridy++;
		container.gridx = 0;
		gridPanel.add(labelMdp,container);
		container.gridx++;
		gridPanel.add(mdpUser,container);
		container.gridx=0;
		container.gridy++;
		gridPanel.add(ajouter, container);
		container.gridx++;
		gridPanel.add(annuler,container);
		
		borderPanel = new JPanel(new BorderLayout(10,10));		
		borderPanel.add(labelNorth, "North");
		borderPanel.add(gridPanel);
		borderPanel.setBackground(new Color(10,10,50));
		
		this.setContentPane(borderPanel);
				
		setVisible(true);
	}

}
