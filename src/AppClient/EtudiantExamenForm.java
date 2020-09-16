package AppClient;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;


public class EtudiantExamenForm extends JFrame 
{
	JScrollPane defil; 
	JLabel formTitle, questionLabel, chronoPanel;
	JButton submit;
	JPanel borderPanel,flowPanel1, flowPanel2,questionsPanel;
	Timer horloge;
	Client client;
	Chrono chrono;
	int duree=15;//la durée d'examen est 15 min
	
	public EtudiantExamenForm(Client x) 
	{
		client=x;
		initFrame();
	}
	
	private void initFrame()
	{
		this.setSize(800,600);
		this.setLocationRelativeTo(null);
		this.setTitle ("Connecté au Serveur [ IP:"+client.ipServer+" , Port: "+client.port+" ]") ;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.chrono=new Chrono(0, 0, 0);
		
		submit = new JButton("Submit");
		submit.setPreferredSize(new Dimension(150,30));
		submit.setBackground(new Color(59, 89, 182));
		submit.setForeground(Color.white);
		submit.setBorder(null);
		
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Question q:client.questionsList) {
					String answersList="";
					for(JCheckBox cb:q.cbox) {
						if(cb.isSelected()) {
							if(answersList.contentEquals("")) {
								answersList+=cb.getText();
							}
							else {
								answersList+="-"+cb.getText();
							}
						}
					}
					if(answersList.equals("")) {
						JOptionPane.showMessageDialog(null, "Question "+q.getQuestion().substring(0, 2)+" n'est pas sélectionnée!", "Warning",JOptionPane.WARNING_MESSAGE);
					}
					else {
						Reponse rep=new Reponse(q.getQuestionId(), answersList);
						client.reponsesList.add(rep);
					}
				}
				if(client.reponsesList.size()==5) {
					client.handleAction("reponses");
					client.sendAnswersToServer();//envoyer les reponses au serveur
					new EtudiantResultat(client);//afficher le resultat d'utilisateur
					dispose();//fermer le formulaire d'examen
				}
			}
		});
		
		formTitle = new JLabel(client.clientName+" - Examen Form");
		formTitle.setFont(new Font("Georgia", 0, 22));
		formTitle.setForeground(new Color(35,185,185));

		questionsPanel = new JPanel();
		questionsPanel.setBackground(new Color(10,10,50));
		questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
		
		JScrollPane defil = new JScrollPane(questionsPanel);
		defil.setBorder(null);
		
		flowPanel1=new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
		flowPanel1.add(submit);
		flowPanel1.setBackground(new Color(10,10,50));
		
		chronoPanel=new JLabel("00:00");
		chronoPanel.setForeground(Color.white);
		chronoPanel.setFont(new Font("Arial", Font.PLAIN, 24));
		ImageIcon chronoIcon=new ImageIcon("./ressources/time.png");
		chronoPanel.setIcon(chronoIcon);
		
		flowPanel2=new JPanel(new FlowLayout(FlowLayout.CENTER,25,10));
		flowPanel2.add(formTitle);
		flowPanel2.add(chronoPanel);
		flowPanel2.setBackground(new Color(10,10,50));
		
		borderPanel = new JPanel(new BorderLayout());
		borderPanel.add(defil, "Center");
		borderPanel.add(flowPanel1, "South");
		borderPanel.add(flowPanel2, "North");
		
		horloge=new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chrono.tiktak();
				if(chrono.getMinutes()>=duree) {
					JOptionPane.showMessageDialog(null, "Fin du temps.", "Warning",JOptionPane.INFORMATION_MESSAGE);
					for(Question q:client.questionsList) {
						String answersList="";
						for(JCheckBox cb:q.cbox) {
							if(cb.isSelected()) {
								if(answersList.contentEquals("")) {
									answersList+=cb.getText();
								}
								else {
									answersList+="-"+cb.getText();
								}
							}
						}
						Reponse rep=new Reponse(q.getQuestionId(), answersList);
						client.reponsesList.add(rep);
					}
					if(client.reponsesList.size()==5) {
						client.handleAction("reponses");
						client.sendAnswersToServer();//envoyer les reponses au serveur
						new EtudiantResultat(client);//afficher le resultat d'utilisateur
						dispose();//fermer le formulaire d'examen
					}
					horloge.stop();
				}
				else {
					String clock="";
					if(chrono.getMinutes()<10) {
						clock+="0"+chrono.getMinutes()+":";
					}
					else {
						clock+=chrono.getMinutes()+":";
					}
					if(chrono.getSeconds()<10) {
						clock+="0"+chrono.getSeconds();
					}
					else {
						clock+=chrono.getSeconds();
					}
					chronoPanel.setText(clock);
					chronoPanel.repaint();
				}
			}
		});
		
		horloge.start();
		
		this.setContentPane(borderPanel);
		setVisible(true);
	}		
}
