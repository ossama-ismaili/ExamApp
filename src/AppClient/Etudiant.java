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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Etudiant {
	private int etudiantId;
	private String username;
	private String fullname;
	Client etudiantsController;
	
	public Etudiant(Client x,int etudiantId,String username,String fullname) {
		this.etudiantsController=x;
		this.etudiantId=etudiantId;
		this.username=username;
		this.fullname=fullname;
	}
	
	public JPanel etudiantPanel() {
		JPanel etudiantPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,50,20));
		etudiantPanel.setBackground(new Color(10,10,50));
		etudiantPanel.setBorder(BorderFactory.createLineBorder(new Color(50,180,255),1));
		
		JTextField fullnameText=new JTextField(fullname);
		fullnameText.setFont(new Font("Arial", 0, 18));
		fullnameText.setForeground(Color.white);
		fullnameText.setBackground(new Color(10,10,50));
		fullnameText.setBorder(BorderFactory.createLineBorder(new Color(200,200,250),1));
		etudiantPanel.add(fullnameText);
		
		JTextField usernameText=new JTextField(username);
		usernameText.setFont(new Font("Arial", 0, 18));
		usernameText.setForeground(Color.white);
		usernameText.setBackground(new Color(10,10,50));
		usernameText.setBorder(BorderFactory.createLineBorder(new Color(200,200,250),1));
		etudiantPanel.add(usernameText);
		
		ImageIcon editerIcon=new ImageIcon("./ressources/edit.png");
		JButton editerButton=new JButton("editer");
		editerButton.setPreferredSize(new Dimension(100,30));
		editerButton.setBackground(new Color(50, 120, 120));
		editerButton.setForeground(Color.white);
		editerButton.setBorder(null);
		editerButton.setIcon(editerIcon);
		editerButton.setFocusable(false);
		etudiantPanel.add(editerButton);
		
		editerButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(usernameText.getText().equals("") || fullnameText.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Veuillez saisie tous les champs", "Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					etudiantsController.handleAction("editer etudiant="+etudiantId);
					etudiantsController.handleAction(usernameText.getText());
					etudiantsController.handleAction(fullnameText.getText());
				}
			}
		});
		
		ImageIcon supprimerIcon=new ImageIcon("./ressources/warning.png");
		JButton supprimerButton=new JButton("Supprimer");
		supprimerButton.setPreferredSize(new Dimension(100,30));
		supprimerButton.setBackground(new Color(200, 50, 50));
		supprimerButton.setForeground(Color.white);
		supprimerButton.setBorder(null);
		supprimerButton.setIcon(supprimerIcon);
		supprimerButton.setFocusable(false);
		etudiantPanel.add(supprimerButton);
		
		supprimerButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				etudiantsController.handleAction("supprimer etudiant="+etudiantId);
				int order=etudiantsController.etudiantsList.indexOf(Etudiant.this);
				etudiantsController.etudiantsList.remove(Etudiant.this);
				etudiantsController.etudiantsListForm.etudiantsPanel.remove(etudiantsController.etudiantsListForm.etudiantsPanel.getComponent(order));
				etudiantsController.etudiantsListForm.etudiantsPanel.repaint();
			}
		});
		
		return etudiantPanel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getEtudiantId() {
		return etudiantId;
	}

	public void setEtudiantId(int etudiantId) {
		this.etudiantId = etudiantId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Override
	public String toString() {
		return "Etudiant [etudiantId=" + etudiantId + ", username=" + username + ", fullname=" + fullname + "]";
	}
}
