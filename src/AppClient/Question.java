package AppClient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Question{
	private int questionId;
	private String question;
	private String[] choixArray;
	private String choix;
	private String reponse;
	public JCheckBox[] cbox;
	public Client questionsController;

	public Question(int questionId, String question, String[] choixArray) {
		this.questionId = questionId;
		this.question = question;
		this.choixArray = choixArray;
		this.reponse="none";
		choix="";
		//creer un String de choix en format c1-c2-c3 tel que c1,c2,c3 des choix d'un question
		for(int i=0;i<choixArray.length-1;i++) {
			if(i==0) {
				choix=choixArray[i];
			}
			else {
				choix+="-"+choixArray[i];
			}
		}
	}
	
	public Question(Client x,int questionId, String question, String choix, String reponse) {
		this.questionsController=x;
		this.questionId = questionId;
		this.question = question;
		this.reponse=reponse;
		this.choix = choix;
		choixArray=choix.split("-");
	}
	
	public JPanel questionPanel(Color questionColor) {
		JPanel questionPanel=new JPanel(new GridLayout(2,1));
		questionPanel.setBackground(questionColor);
		JLabel questionLabel=new JLabel(question);
		questionPanel.add(questionLabel);
		JPanel choicesPanel=new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
		choicesPanel.setBackground(questionColor);
		cbox=new JCheckBox[choixArray.length];
		
		for(int i=0;i<choixArray.length;i++) {
			cbox[i]=new JCheckBox(choixArray[i]);
			cbox[i].setBackground(questionColor);
			choicesPanel.add(cbox[i]);
		}
		questionPanel.add(choicesPanel);
		
		return questionPanel;
	}
	
	public JPanel profQuestionPanel() {
		JPanel questionPanel=new JPanel(new GridLayout(4,2,10,10));
		questionPanel.setBorder(BorderFactory.createLineBorder(new Color(50,80,250), 2));
		questionPanel.setBackground(new Color(10,10,50));
		
		JLabel questionLabel  = new JLabel("Question : ", SwingConstants.LEFT);
		questionLabel.setForeground(Color.white);
		questionPanel.add(questionLabel);
		
		JTextField questionText=new JTextField(question,18);
		questionText.setFont(new Font("Arial", 0, 18));
		questionText.setForeground(Color.white);
		questionText.setBackground(new Color(15,15,75));
		questionText.setBorder(BorderFactory.createLineBorder(new Color(50,50,100)));
		questionPanel.add(questionText);
		
		JLabel choixLabel  = new JLabel("Choix : ", SwingConstants.LEFT);
		choixLabel.setForeground(Color.white);
		questionPanel.add(choixLabel);
		
		JTextField choixText=new JTextField(choix,18);
		choixText.setFont(new Font("Arial", 0, 18));
		choixText.setForeground(Color.white);
		choixText.setBackground(new Color(15,15,75));
		choixText.setBorder(BorderFactory.createLineBorder(new Color(50,50,100)));
		questionPanel.add(choixText);
		
		JLabel repLabel  = new JLabel("Reponse : ", SwingConstants.LEFT);
		repLabel.setForeground(Color.white);
		questionPanel.add(repLabel);
		
		JTextField reponseText=new JTextField(reponse,18);
		reponseText.setFont(new Font("Arial", 0, 18));
		reponseText.setForeground(Color.white);
		reponseText.setBackground(new Color(15,15,75));
		reponseText.setBorder(BorderFactory.createLineBorder(new Color(50,50,100)));
		questionPanel.add(reponseText);
		
		JLabel optionsLabel  = new JLabel("Options : ", SwingConstants.LEFT);
		optionsLabel.setForeground(Color.white);
		questionPanel.add(optionsLabel);
		
		JPanel optionsPanel=new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
		optionsPanel.setBackground(new Color(10,10,50));
		
		ImageIcon editerIcon=new ImageIcon("./ressources/edit.png");
		JButton editerButton=new JButton("editer");
		editerButton.setPreferredSize(new Dimension(100,30));
		editerButton.setBackground(new Color(50, 120, 120));
		editerButton.setForeground(Color.white);
		editerButton.setBorder(null);
		editerButton.setIcon(editerIcon);
		editerButton.setFocusable(false);
		optionsPanel.add(editerButton);
		
		editerButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(questionText.getText().equals("") || choixText.getText().equals("") || reponseText.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Veuillez saisie tous les champs", "Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					questionsController.handleAction("editer question="+questionId);
					questionsController.handleAction(questionText.getText());
					questionsController.handleAction(choixText.getText());
					questionsController.handleAction(reponseText.getText());
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
		optionsPanel.add(supprimerButton);
		
		supprimerButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				questionsController.handleAction("supprimer question="+questionId);
				int order=questionsController.profQuestionsList.indexOf(Question.this);
				questionsController.profQuestionsList.remove(Question.this);
				questionsController.questionsListForm.questionsPanel.remove(questionsController.questionsListForm.questionsPanel.getComponent(order));
				questionsController.questionsListForm.questionsPanel.repaint();
			}
		});
		
		questionPanel.add(optionsPanel);
		
		return questionPanel;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getChoixArray() {
		return choixArray;
	}

	public void setChoixArray(String[] choixArray) {
		this.choixArray = choixArray;
	}

	public String getChoix() {
		return choix;
	}

	public void setChoix(String choix) {
		this.choix = choix;
	}
	
	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", question=" + question + ", choix="+ Arrays.toString(choixArray) + "]";
	}
}
