package AppClient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClientForm extends JFrame {
	Client client;
	JPanel gridPanel;
	JPanel formPanel;
	JPanel imagePanel;
	JButton etudiantBtn,profBtn;
	JLabel titleLabel;
	
	public ClientForm(Client x) {
		client=x;
		initFrame();
	}
	
	private void initFrame() {
		this.setTitle ("JExamen") ;
		this.setSize(700, 450);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		gridPanel=new JPanel(new GridLayout(1, 2));
		gridPanel.setBackground(new Color(10,10,50));
		
		titleLabel=new JLabel("JExamen");
		titleLabel.setFont(new Font("Century Gothic",Font.BOLD, 32));
		titleLabel.setForeground(new Color(35,185,185));
		
		ImageIcon etudiantIcon=new ImageIcon("./ressources/student.png");
		etudiantBtn = new JButton("Étudiant");
		etudiantBtn.setPreferredSize(new Dimension(250,50));
		etudiantBtn.setBackground(new Color(59, 89, 182));
		etudiantBtn.setForeground(Color.white);
		etudiantBtn.setFont(new Font("Arial",Font.BOLD, 18));
		etudiantBtn.setIcon(etudiantIcon);
		etudiantBtn.setFocusable(false);
		etudiantBtn.setBorder(null);
		
		etudiantBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EtudiantConnexionForm(client);
				dispose();
			}
		});
		
		ImageIcon profIcon=new ImageIcon("./ressources/teacher.png");
		profBtn = new JButton("Professeur");
		profBtn.setPreferredSize(new Dimension(250,50));
		profBtn.setBackground(new Color(59, 89, 182));
		profBtn.setForeground(Color.white);
		profBtn.setFont(new Font("Arial",Font.BOLD, 18));
		profBtn.setIcon(profIcon);
		profBtn.setFocusable(false);
		profBtn.setBorder(null);
		
		profBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ProfConnexionForm(client);
				dispose();
			}
		});
		
		formPanel= new JPanel(new GridBagLayout());
		formPanel.setBackground(new Color(10,10,50));
		
		GridBagConstraints container = new GridBagConstraints();
		container.gridx = 0;
		container.gridy = 0;
		container.weightx=10;
		container.weighty=5;
		formPanel.add(titleLabel,container);
		container.gridy--;
		formPanel.add(etudiantBtn,container);
		container.gridy--;
		formPanel.add(profBtn,container);
		
		gridPanel.add(formPanel);
		
		ImageIcon img=new ImageIcon("./ressources/background.jpg");;
		imagePanel=new JPanel(){
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.drawImage(img.getImage(), 0,0,this.getWidth(),this.getHeight(),this);
			}
		};
		
		gridPanel.add(imagePanel);
		
		this.setContentPane(gridPanel);
		this.setVisible(true);
	}
}
