package AppClient;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProfForm extends JFrame {
	Client client;
	JPanel mainPanel;
	JButton questionsBtn, etudiantsBtn, resultatsBtn, testerExamBtn;
	
	public ProfForm(Client x) {
		client=x;
		initFrame();
	}
	
	private void initFrame() {
		this.setSize(500,600);
		this.setLocationRelativeTo(null);
		this.setTitle ("Connecté au Serveur [ IP:"+client.ipServer+" , Port: "+client.port+" ]") ;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JLabel label=new JLabel(client.clientName);
		label.setForeground(Color.white);
		label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
		
		mainPanel= new JPanel(new GridBagLayout());
		mainPanel.setBackground(new Color(10,10,50));
		
		ImageIcon qstIcon=new ImageIcon("./ressources/question.png");
		questionsBtn = new JButton("Questions");
		questionsBtn.setPreferredSize(new Dimension(250,50));
		questionsBtn.setBackground(new Color(59, 89, 182));
		questionsBtn.setForeground(Color.white);
		questionsBtn.setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 18));
		questionsBtn.setIcon(qstIcon);
		questionsBtn.setFocusable(false);
		questionsBtn.setBorder(null);
		
		questionsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.handleAction("afficher questions");
				client.questionsListForm=new ProfQuestionsListForm(client);
				client.fetchQuestionsFromServer("prof-admin");
			}
		});
		
		ImageIcon etudiantIcon=new ImageIcon("./ressources/book.png");
		etudiantsBtn = new JButton("Étudiants");
		etudiantsBtn.setPreferredSize(new Dimension(250,50));
		etudiantsBtn.setBackground(new Color(59, 89, 182));
		etudiantsBtn.setForeground(Color.white);
		etudiantsBtn.setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 18));
		etudiantsBtn.setIcon(etudiantIcon);
		etudiantsBtn.setFocusable(false);
		etudiantsBtn.setBorder(null);
		
		etudiantsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.handleAction("afficher etudiants");
				client.etudiantsListForm=new ProfEtudiantsListForm(client);
				client.fetchStudentsFromServer();
			}
		});
		
		ImageIcon resultatIcon=new ImageIcon("./ressources/research.png");
		resultatsBtn = new JButton("Resultats");
		resultatsBtn.setPreferredSize(new Dimension(250,50));
		resultatsBtn.setBackground(new Color(59, 89, 182));
		resultatsBtn.setForeground(Color.white);
		resultatsBtn.setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 18));
		resultatsBtn.setIcon(resultatIcon);
		resultatsBtn.setFocusable(false);
		resultatsBtn.setBorder(null);
		
		resultatsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.handleAction("afficher resultats");
				client.resultatForm=new ProfResultatForm(client);
				client.fetchResultsFromServer();
			}
		});
		
		ImageIcon testIcon=new ImageIcon("./ressources/test.png");
		testerExamBtn = new JButton("Tester l'Examen");
		testerExamBtn.setPreferredSize(new Dimension(250,50));
		testerExamBtn.setBackground(new Color(59, 89, 182));
		testerExamBtn.setForeground(Color.white);
		testerExamBtn.setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 18));
		testerExamBtn.setIcon(testIcon);
		testerExamBtn.setFocusable(false);
		testerExamBtn.setBorder(null);
		
		testerExamBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.handleAction("tester examen");
				client.profExamenForm=new ProfExamenForm(client);
				client.fetchQuestionsFromServer("prof");
			}
		});
		
		GridBagConstraints container = new GridBagConstraints();
		container.gridx = 0;
		container.gridy = 0;
		container.weightx=10;
		container.weighty=5;
		mainPanel.add(label,container);
		container.gridy--;
		mainPanel.add(questionsBtn,container);
		container.gridy--;
		mainPanel.add(etudiantsBtn,container);
		container.gridy--;
		mainPanel.add(resultatsBtn,container);
		container.gridy--;
		mainPanel.add(testerExamBtn,container);
		
		this.setContentPane(mainPanel);
		this.setVisible(true);
	}
}
