package AppClient;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ProfExamenForm extends JFrame{
	JScrollPane defil; 
	JLabel formTitle;
	JLabel questionLabel;
	JButton annuler;
	JPanel borderPanel,flowPanel1, flowPanel2,questionsPanel;
	Client client;
	
	public ProfExamenForm(Client x) 
	{
		client=x;
		initFrame();
	}
	
	private void initFrame()
	{
		this.setSize(800,600);
		this.setLocationRelativeTo(null);
		this.setTitle ("Connecté au Serveur [ IP:"+client.ipServer+" , Port: "+client.port+" ]") ;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		annuler = new JButton("Annuler");
		annuler.setPreferredSize(new Dimension(150,30));
		annuler.setBackground(new Color(59, 89, 182));
		annuler.setForeground(Color.white);
		annuler.setBorder(null);
		
		annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		formTitle = new JLabel(client.clientName+" - Examen Form");
		formTitle.setFont(new Font("Georgia", 0, 22));
		formTitle.setForeground(new Color(35,185,185));

		questionsPanel = new JPanel();
		questionsPanel.setBackground(Color.cyan);
		questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
		
		JScrollPane defil = new JScrollPane(questionsPanel);
		defil.setBorder(null);
		
		flowPanel1=new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
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
