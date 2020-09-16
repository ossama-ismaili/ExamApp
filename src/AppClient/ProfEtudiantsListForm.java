package AppClient;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ProfEtudiantsListForm extends JFrame {
	JScrollPane defil; 
	JLabel formTitle;
	JButton annuler,ajouter;
	JPanel borderPanel,flowPanel1, flowPanel2,etudiantsPanel;
	Client client;
	
	public ProfEtudiantsListForm(Client x) 
	{
		client=x;
		initFrame();
	}

	private void initFrame() {
		this.setSize(700, 650);;
		this.setLocationRelativeTo(null);
		this.setTitle ("Connecté au Serveur [ IP:"+client.ipServer+" , Port: "+client.port+" ]") ;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		ajouter = new JButton("Ajouter");
		ajouter.setPreferredSize(new Dimension(150,30));
		ajouter.setBackground(new Color(55, 55, 255));
		ajouter.setForeground(Color.white);
		ajouter.setBorder(null);
		
		ajouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ProfAjouterEtudiantForm(client);
			}
		});
		
		annuler = new JButton("Annuler");
		annuler.setPreferredSize(new Dimension(150,30));
		annuler.setBackground(new Color(255, 55, 55));
		annuler.setForeground(Color.white);
		annuler.setBorder(null);
		
		annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		formTitle = new JLabel(client.clientName+" -  Liste des etudiants");
		formTitle.setFont(new Font("Georgia", 0, 22));
		formTitle.setForeground(new Color(35,185,185));

		etudiantsPanel = new JPanel();
		etudiantsPanel.setBackground(new Color(10,10,50));
		etudiantsPanel.setLayout(new BoxLayout(etudiantsPanel, BoxLayout.Y_AXIS));
		
		JScrollPane defil = new JScrollPane(etudiantsPanel);
		defil.setBorder(null);
		
		flowPanel1=new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
		flowPanel1.add(ajouter);
		flowPanel1.add(annuler);
		flowPanel1.setBackground(new Color(10,10,50));
		flowPanel2=new JPanel(new FlowLayout(FlowLayout.CENTER));
		flowPanel2.add(formTitle);
		flowPanel2.setBackground(new Color(10,10,50));
		
		borderPanel = new JPanel(new BorderLayout());
		borderPanel.add(defil, "Center");
		borderPanel.add(flowPanel1, "South");
		borderPanel.add(flowPanel2, "North");
		
		this.setContentPane(borderPanel);
		
		setVisible(true);
	}
}
