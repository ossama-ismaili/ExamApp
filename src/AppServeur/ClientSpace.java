package AppServeur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;


public class ClientSpace extends Thread{
	public Serveur serveur;
	Socket clientSocket;
	String clientName, clientMdp,fullname;
	boolean examen=false;
	String statut="none";
	int note=0;
	public DataInputStream dataIn;
	public DataOutputStream dataOut;
	private UtilisateurBD userManager;
	private QuestionBD questionManager;
	private ResultatBD resultatManager;
	ArrayList<Reponse> clientReponses;

	public ClientSpace(Socket clientSocket, Serveur serveur) {
		this.serveur=serveur;
		this.clientSocket=clientSocket;
		this.clientReponses=new ArrayList<Reponse>();
		this.userManager=new UtilisateurBD();
		this.questionManager=new QuestionBD();
		this.resultatManager=new ResultatBD();
		try {
			dataIn=new DataInputStream(this.clientSocket.getInputStream());
			dataOut=new DataOutputStream(this.clientSocket.getOutputStream());
			clientName=dataIn.readUTF();
			clientMdp=dataIn.readUTF();
			statut=dataIn.readUTF();
			this.start();
			try {
				if(userManager.testUser(clientName, clientMdp)) {
					if(statut.equals("etudiant")) {
						fullname=userManager.getFullname(clientName);
						dataOut.writeBoolean(true);
						System.out.println("Bonjour Etudiant "+clientName);
					}
					else if(statut.equals("prof")) {
						//tester si l'utilisateur est un professeur(Admin)
						if(userManager.testAdmin(clientName)) {
							fullname=userManager.getFullname(clientName);
							dataOut.writeBoolean(true);
							System.out.println("Bonjour Professeur "+clientName);
						}
						else {
							dataOut.writeBoolean(false);
							serveur.listClients.remove(this);
						}
					}
					else {
						dataOut.writeBoolean(false);
						serveur.listClients.remove(this);
					}
				}
				else {
					dataOut.writeBoolean(false);
					serveur.listClients.remove(this);
				}
			} catch (SQLException e) {
				System.out.println("Problème de connexion au base de données");
			}
		} catch (IOException e) {
			System.out.println("Problème dans le reception du nom d'un client");
			serveur.listClients.remove(this);
		}
	}
	
	public int[] randomNumbers(int N,int Max) {
		Random r=new Random();
		ArrayList<Integer> arrayRandom=new ArrayList<Integer>();
		for(int i=0;i<N;i++) {
			int num=r.nextInt(Max);
			while(arrayRandom.contains(num)) {
				num=r.nextInt(Max);
			}
			arrayRandom.add(num);
		}
		int[] arrayInt=new int[N];
		for(int i=0;i<N;i++) {
			arrayInt[i]=arrayRandom.get(i);
		}
		return arrayInt;
	}
	
	public void run() {
		try {
			while(true) {
				if(statut.equals("etudiant")) {
					String action=dataIn.readUTF();
					System.out.println("Etudiant : "+action);
					
					if(action.equals("questions")) {
						try {
							int[] questionIds=randomNumbers(5,questionManager.getQuestions().size());
							for(int i=0;i<questionIds.length;i++) {
								Question q;
								q = questionManager.getQuestions().get(questionIds[i]);
								dataOut.writeInt(q.getQuestionId());
								dataOut.writeUTF(q.getQuestion());
								dataOut.writeUTF(q.getChoix());
							}
						} catch (SQLException e) {
							System.out.println("Problème de connexion au base de données");
						}
					}
					
					else if(action.equals("reponses")){
						for(int i=0;i<5;i++) {
							int questionId=dataIn.readInt();
							String reponse=dataIn.readUTF();
							Reponse rep=new Reponse(questionId, reponse);
							System.out.println(rep);
							clientReponses.add(rep);
						}
					}
					else if(action.equals("resultat")) {
						this.examen=true;
						
						//traiter le resultat d'Etudiant
						String resultat;
						boolean passeExamen;
						try {
							for(Reponse a:clientReponses) {
								if(questionManager.testAnswer(a.getQuestionId(), a.getReponses())){
									note+=4;
								}
							}
						} catch (SQLException e) {
							System.out.println("Problème de connexion au base de données");
						}
						if(note>=10) {
							passeExamen=true;
							resultat="Félicitations tu passe l'examen avec "+note+"/20";
						}
						else {
							passeExamen=false;
							resultat="Tu échoué l'examen votre note est "+note+"/20";
						}
						dataOut.writeBoolean(passeExamen);
						dataOut.writeUTF(resultat);
						System.out.println("Client "+clientName+" resultat : "+note+"/20");
					}
					else if(action.equals("sauveguarder")) {
							try {
								/* Si le client est deja exister dans la base de donnees 
								des client changer son resultat, sinon ajouter le client au base de donnees */
								if(resultatManager.testResultatName(clientName)) {
									resultatManager.editResultat(clientName, note);
								}
								else {
									resultatManager.insertResultat(clientName, note);
								}
							} catch (SQLException e) {
								System.out.println("Problème de connexion au base de données");
							}
							System.out.println("Sauveguarder les donnees");
						clientSocket.close();//deconnecter le client
					}
					else {
						System.out.println("Erreur : Action indéfinie");
						clientSocket.close();//deconnecter le client
					}
				}
				else if(statut.equals("prof")) {
					String action=dataIn.readUTF();
					System.out.println("Professeur : "+action);
					if(action.equals("afficher resultats")) {
						try {
							int resultsNum=resultatManager.getResultats().size();
							dataOut.writeInt(resultsNum);
							for(Resultat r:resultatManager.getResultats()) {
								int id=r.getResultatId();
								String username=r.getUsername();
								int note=r.getNote();
								dataOut.writeInt(id);
								dataOut.writeUTF(username);
								dataOut.writeInt(note);
							}
						} catch (SQLException e) {
							System.out.println("Problème de connexion au base de données");
						}
					}
					else if(action.contains("supprimer resultat")){
						int startIndex=action.indexOf('=')+1;//obtenir l'index de character après le '='
						int resultatId=Integer.parseInt(action.substring(startIndex, action.length()));//obtenir le nombre String après le '=' et convertir en int
						try {
							resultatManager.deleteResultat(resultatId);
						} catch (SQLException e) {
							System.out.println("Problème de connexion au base de données");
						}
					}
					else if(action.equals("tester examen")) {
						try {
							int[] questionIds=randomNumbers(5,questionManager.getQuestions().size());
							for(int i=0;i<questionIds.length;i++) {
								Question q;
								q = questionManager.getQuestions().get(questionIds[i]);
								dataOut.writeInt(q.getQuestionId());
								dataOut.writeUTF(q.getQuestion());
								dataOut.writeUTF(q.getChoix());
							}
						}catch (SQLException e) {
							System.out.println("Problème de connexion au base de données");
						}
					}
					else if(action.equals("afficher etudiants")) {
						try {
							int etudiantsNum=userManager.getEtudiants().size();
							dataOut.writeInt(etudiantsNum);
							for(Etudiant e:userManager.getEtudiants()) {
								int id=e.getEtudiantId();
								String username=e.getUsername();
								String fullname=e.getFullname();
								dataOut.writeInt(id);
								dataOut.writeUTF(username);
								dataOut.writeUTF(fullname);
							}
						} catch (SQLException e) {
							System.out.println("Problème de connexion au base de données");
						}
					}else if(action.contains("editer etudiant")) {
						int startIndex=action.indexOf('=')+1;//obtenir l'index de character après le '='
						int etudiantId=Integer.parseInt(action.substring(startIndex, action.length()));//obtenir le nombre String après le '=' et convertir en int
						String username=dataIn.readUTF();
						String fullname=dataIn.readUTF();
						try {
							userManager.editUser(etudiantId, username, fullname);
						} catch (SQLException e) {
							System.out.println("Problème de connexion au base de données");
						}
					}else if(action.contains("supprimer etudiant")) {
						int startIndex=action.indexOf('=')+1;//obtenir l'index de character après le '='
						int etudiantId=Integer.parseInt(action.substring(startIndex, action.length()));//obtenir le nombre String après le '=' et convertir en int
						try {
							userManager.deleteUser(etudiantId);
						} catch (SQLException e) {
							System.out.println("Problème de connexion au base de données");
						}
					}else if(action.equals("ajouter etudiant")) {
						String username=dataIn.readUTF();
						String fullname=dataIn.readUTF();
						String password=dataIn.readUTF();
						
						try {
							userManager.insertEtudiant(username, fullname, password);
						} catch(SQLException e){
							System.out.println("Problème de connexion au base de données");
						}
					}else if(action.equals("afficher questions")) {
						try {
							dataOut.writeInt(questionManager.getQuestions().size());
							for(Question q:questionManager.getQuestions()) {
								dataOut.writeInt(q.getQuestionId());
								dataOut.writeUTF(q.getQuestion());
								dataOut.writeUTF(q.getChoix());
								dataOut.writeUTF(q.getReponse());
							}
						} catch (SQLException e) {
							System.out.println("Problème de connexion au base de données");
						}
					}else if(action.contains("editer question")) {
						int startIndex=action.indexOf('=')+1;//obtenir l'index de character après le '='
						int questionId=Integer.parseInt(action.substring(startIndex, action.length()));//obtenir le nombre String après le '=' et convertir en int
						String question=dataIn.readUTF();
						String choix=dataIn.readUTF();
						String reponse=dataIn.readUTF();
						
						try {
							questionManager.editQuestion(questionId, question, choix, reponse);
						} catch (SQLException e) {
							System.out.println("Problème de connexion au base de données");
						}
					}
					else if(action.contains("supprimer question")) {
						int startIndex=action.indexOf('=')+1;//obtenir l'index de character après le '='
						int questionId=Integer.parseInt(action.substring(startIndex, action.length()));//obtenir le nombre String après le '=' et convertir en int
						
						try {
							questionManager.deleteQuestion(questionId);
						} catch (SQLException e) {
							System.out.println("Problème de connexion au base de données");
						}
					}else if(action.contains("ajouter question")) {
						String question=dataIn.readUTF();
						String choix=dataIn.readUTF();
						String reponse=dataIn.readUTF();
						
						try {
							questionManager.insertQuestion(question, choix, reponse);
						} catch (SQLException e) {
							System.out.println("Problème de connexion au base de données");
						}
					}
					else {
						System.out.println("Erreur : Action indéfinie");
						clientSocket.close();//deconnecter le client
					}
				}
				else {
					System.out.println("Erreur : Statut indéfinie");
					clientSocket.close();//deconnecter le client
				}
			}
		}catch (IOException e) {
			System.out.println("Client deconnecté "+clientName);
			serveur.serveurFenetre.clientsListPanel.remove(serveur.serveurFenetre.clientsListPanel.getComponent(serveur.listClients.indexOf(this)));//supprimer le client deconnecter de la fenetre de liste des clients
			serveur.serveurFenetre.clientsListPanel.repaint();//rafraîchir le fenetre de liste de clients
			serveur.listClients.remove(this);
			System.out.println("Il reste "+serveur.listClients.size()+" clients");
			return;
		}
	}
}
