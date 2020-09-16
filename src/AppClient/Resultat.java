package AppClient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Resultat {
	private int resultatId;
	private String username;
	private int note;
	Client resultatController;
	
	public Resultat(Client x,int resultatId,String username,int note) {
		this.resultatController=x;
		this.resultatId=resultatId;
		this.username=username;
		this.note=note;
	}
	
	public JPanel resultatPanel() {
		JPanel resultatPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,50,20));
		resultatPanel.setBackground(new Color(10,10,50));
		resultatPanel.setBorder(BorderFactory.createLineBorder(new Color(50,180,255),1));
		
		JLabel usernameLabel=new JLabel(username);
		usernameLabel.setFont(new Font("Arial", 0, 18));
		usernameLabel.setForeground(Color.white);
		resultatPanel.add(usernameLabel);
		
		JLabel noteLabel=new JLabel(String.valueOf(note));
		noteLabel.setFont(new Font("Arial", 0, 18));
		noteLabel.setForeground(Color.white);
		resultatPanel.add(noteLabel);
		
		ImageIcon supprimerIcon=new ImageIcon("./ressources/warning.png");
		JButton supprimerButton=new JButton("Supprimer");
		supprimerButton.setPreferredSize(new Dimension(150,30));
		supprimerButton.setBackground(new Color(200, 50, 50));
		supprimerButton.setForeground(Color.white);
		supprimerButton.setBorder(null);
		supprimerButton.setIcon(supprimerIcon);
		supprimerButton.setFocusable(false);
		resultatPanel.add(supprimerButton);
		
		supprimerButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				resultatController.handleAction("supprimer resultat="+resultatId);
				int order=resultatController.resultatsList.indexOf(Resultat.this);
				resultatController.resultatsList.remove(Resultat.this);
				resultatController.resultatForm.resultatsPanel.remove(resultatController.resultatForm.resultatsPanel.getComponent(order));
				resultatController.resultatForm.resultatsPanel.repaint();
			}
		});
		
		return resultatPanel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}
	
	public int getResultatId() {
		return resultatId;
	}

	public void setResultatId(int resultatId) {
		this.resultatId = resultatId;
	}

	@Override
	public String toString() {
		return "Resultat [resultatId=" + resultatId + ", username=" + username + ", note=" + note + "]";
	}
}
