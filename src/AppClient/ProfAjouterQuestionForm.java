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

public class ProfAjouterQuestionForm extends JFrame {
	JLabel questionLabel,choixLabel,reponseLabel,labelNorth;
	JTextField questionText,choixText, reponseText;
	JButton ajouter, annuler;
	JPanel borderPanel,gridPanel;
	Border borderLine;
	Client client;
	
	public ProfAjouterQuestionForm(Client x)
	{
		client=x;
		initFrame();
	}
	
	private void initFrame() {
		this.setTitle ("Ajouter Question") ;
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
		labelNorth= new JLabel("Ajouter Question",SwingConstants.CENTER);
		labelNorth.setFont(new Font("Georgia",Font.BOLD, 20));
		labelNorth.setForeground(new Color(35,150,220));
		questionLabel  = new JLabel("Question : ", SwingConstants.CENTER);
		questionLabel.setForeground(Color.white);
		choixLabel = new JLabel("Choix (exp : c1-c2-c3) :", SwingConstants.CENTER);
		choixLabel.setForeground(Color.white);
		reponseLabel = new JLabel("Reponse(s) :", SwingConstants.CENTER);
		reponseLabel.setForeground(Color.white);
		 
		questionText   = new JTextField(18);
		questionText.setPreferredSize(new Dimension(160,30));
		questionText.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		
		choixText  = new JTextField(18);
		choixText.setPreferredSize(new Dimension(160,30));
		choixText.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		reponseText  = new JTextField(18);
		reponseText.setPreferredSize(new Dimension(160,30));
		reponseText.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
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
				if(questionText.getText().equals("") || choixText.getText().equals("") || reponseText.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Veuillez saisie tous les champs", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else {
					//Envoyer infos au serveur
					client.handleAction("ajouter question");
					client.handleAction(questionText.getText());
					client.handleAction(choixText.getText());
					client.handleAction(reponseText.getText());
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
		gridPanel.add(questionLabel,container);
		container.gridx++;
		gridPanel.add(questionText,container);
		container.gridy++;
		container.gridx=0;
		gridPanel.add(choixLabel,container);
		container.gridx++;
		gridPanel.add(choixText,container);
		container.gridy++;
		container.gridx = 0;
		gridPanel.add(reponseLabel,container);
		container.gridx++;
		gridPanel.add(reponseText,container);
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
