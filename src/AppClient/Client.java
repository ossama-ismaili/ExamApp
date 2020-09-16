package AppClient;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/*!!!!!!!! Important !!!!!!!!!!!!
 * Pour tester l'application : 
 * espace d'étudiant : 
 * 		nom d'utilisateur : etudiantuser1
 * 		mot de passe : testuser2020
 * 
 * 
 * espace de prof : 
 *  	nom d'utilisateur : profuser1
 * 		mot de passe : testuser2020
 * */

public class Client{
	String clientName;
	String clientMdp;
	String ipServer;
	int port;
	Socket socketClient;
	DataOutputStream dataOut;
	DataInputStream dataIn;
	
	ArrayList<Question> questionsList;
	ArrayList<Reponse> reponsesList;
	ArrayList<Resultat> resultatsList;
	ArrayList<Etudiant> etudiantsList;
	ArrayList<Question> profQuestionsList;
	
	EtudiantExamenForm etudiantExamenForm;
	ProfExamenForm profExamenForm;
	ProfResultatForm resultatForm;
	ProfEtudiantsListForm etudiantsListForm;
	ProfQuestionsListForm questionsListForm;
	ProfForm profForm;
	
	String clientResultat;
	boolean clientPasseExamen;
	
	public Client() {
		new ClientForm(this);
		this.reponsesList=new ArrayList<Reponse>();
	}

	public static void main(String[] args) {
		new Client();
	}

	public void openConnexion(String statut) {
		try {
			System.out.println("Etape 1 : Se connecter au serveur");
			socketClient=new Socket(ipServer,port);
			System.out.println("Etape 1 -> OK ");
			
			System.out.println("Etape 2 : Creation des cannaux Out et In");
			dataIn=new DataInputStream(socketClient.getInputStream());
			dataOut=new DataOutputStream(socketClient.getOutputStream());
			System.out.println("Etape 2 -> OK ");
			
			System.out.println("Etape 3 : Envoyer le nom et le mot de passe de client au serveur");
			dataOut.writeUTF(clientName);
			dataOut.writeUTF(clientMdp);
			dataOut.writeUTF(statut);
			System.out.println("Etape 3 -> OK ");
			
		}  catch (IOException e) {
			System.out.println("Probleme de connexion au serveur");
			System.exit(0);
		}
	}
	
	public void deconnecte() {
		try {
			socketClient.close();
		} catch (IOException e) {
			System.out.println("Probleme de connexion au serveur");
		}
	}

	public void fetchQuestionsFromServer(String statut) {
		try {
			//récupérer les données de serveur
			if(statut.equals("prof-admin")) {
				this.profQuestionsList=new ArrayList<Question>();
				int questionNum=dataIn.readInt();
				for(int i=0;i<questionNum;i++) {
					int questionId=dataIn.readInt();
					String questionStr=dataIn.readUTF();
					String choix =dataIn.readUTF();
					String reponse =dataIn.readUTF();
					Question question=new Question(this,questionId, questionStr, choix,reponse);
					profQuestionsList.add(question);
				}
			}
			else {
				this.questionsList=new ArrayList<Question>();
				for(int i=1;i<=5;i++) {
					int questionId=dataIn.readInt();
					String questionStr= "Q"+i+" : "+dataIn.readUTF();
					String choix =dataIn.readUTF();
					String[] choixTab=choix.split("-");
					Question question=new Question(questionId, questionStr, choixTab);
					questionsList.add(question);
				}
			}
		} catch (IOException e1) {
			System.out.println("Probleme de connexion au serveur");
		}
		
		//Afficher les questions en examen form
		if(statut.equals("etudiant")) {
			int index=1;
			for(Question q:questionsList) {
				Color questionColor;
				if(index%2==0) {
					questionColor=new Color(15,170,200);
				}
				else {
					questionColor=new Color(35,225,255);
				}
				etudiantExamenForm.questionsPanel.add(q.questionPanel(questionColor));
				index++;
			}
		}
		else if(statut.equals("prof")) {
			int index=1;
			for(Question q:questionsList) {
				Color questionColor;
				if(index%2==0) {
					questionColor=new Color(15,170,200);
				}
				else {
					questionColor=new Color(35,225,255);
				}
				profExamenForm.questionsPanel.add(q.questionPanel(questionColor));
				index++;
			}
		}
		else if(statut.equals("prof-admin")) {
			for(Question q:profQuestionsList) {
				questionsListForm.questionsPanel.add(q.profQuestionPanel());
			}
		}
		else {
			System.out.println("Statut indéfinie");
		}
	}
	
	public void fetchResultsFromServer() {
		this.resultatsList=new ArrayList<Resultat>();
		try {
			//récupérer les données de serveur
			int resultsNum=dataIn.readInt();
			for(int i=1;i<=resultsNum;i++) {
				int resultatId=dataIn.readInt();
				String username=dataIn.readUTF();
				int note=dataIn.readInt();
				Resultat resultat=new Resultat(this,resultatId,username, note);
				this.resultatsList.add(resultat);
			}
		} catch (IOException e) {
			System.out.println("Probleme de connexion au serveur");
		}
		
		for(Resultat r:resultatsList) {
			resultatForm.resultatsPanel.add(r.resultatPanel());
		}
	}
	
	public void fetchStudentsFromServer() {
		this.etudiantsList=new ArrayList<Etudiant>();
		try {
			//récupérer les données de serveur
			int etudiantsNum=dataIn.readInt();
			for(int i=1;i<=etudiantsNum;i++) {
				int etudiantId=dataIn.readInt();
				String username=dataIn.readUTF();
				String fullname=dataIn.readUTF();
				
				Etudiant etudiant=new Etudiant(this, etudiantId, username, fullname);
				this.etudiantsList.add(etudiant);
			}
		} catch (IOException e) {
			System.out.println("Probleme de connexion au serveur");
		}
		
		for(Etudiant e:etudiantsList) {
			etudiantsListForm.etudiantsPanel.add(e.etudiantPanel());
		}
	}
	
	public void sendAnswersToServer() {
		try {
			//Envoyer les reponses a serveur
			for(Reponse a:reponsesList) {
				dataOut.writeInt(a.getQuestionId());
				dataOut.writeUTF(a.getReponses());
			}
			handleAction("resultat");//demander le resultat au serveur
			clientPasseExamen=dataIn.readBoolean();//recuperer un boolean pour verfier si l'etudiant passe l'examen ou non
			clientResultat=dataIn.readUTF();//le message de resultat
		}catch (IOException e1) {
			System.out.println("Probleme de connexion au serveur");
		}
	}
	
	public void handleAction(String action) {
		try {
			dataOut.writeUTF(action);
		} catch (IOException e) {
			System.out.println("Probleme de connexion au serveur");
		}
	}
}
