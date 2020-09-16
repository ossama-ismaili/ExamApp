package AppServeur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuestionBD {
	Connection myConnection; 
	String user="root";
	String pass="";
	String url="jdbc:mysql://localhost:3308/appdb";
	
	public QuestionBD() {
		//etape 1 : tester l'accessibilite de driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Changement du driver est OK");
		} 
		catch (ClassNotFoundException e) {
			System.out.println("Changement du driver est ... Non");
			System.exit(0);
		}
		
		//etape 2 : connecter a la Base de donnees
		try {
			myConnection=DriverManager.getConnection(url,user,pass);
			System.out.println("Base de donnees accessible Ok");
		}catch(SQLException e) {
			System.out.println("Base de donnees accessible Non");
		}
	}
	
	//tester le reponse donnee par utilisateur d'un question avec le vrai reponse au base de donnees
	public boolean testAnswer(int questionId,String answer) throws SQLException {
		String sql="SELECT * FROM questions";
		Statement st = myConnection.createStatement();
		ResultSet rs=st.executeQuery(sql);
			
		while(rs.next()) {
			if(rs.getInt(1)==questionId && rs.getString(4).equals(answer)) {
				return true;
			}
		}
			
		return false;
	}
	
	//retourner liste des questions
	public ArrayList<Question> getQuestions() throws SQLException{
		ArrayList<Question> questionsList=new ArrayList<Question>();
		String sql="SELECT * FROM questions";
		Statement st = myConnection.createStatement();
		ResultSet rs=st.executeQuery(sql);
			
		while(rs.next()) {
			Question question=new Question(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4));
			questionsList.add(question);
		}
			
		return questionsList;
	}
	
	//Ajouter un question au base de donnees
	public void insertQuestion(String question,String choix,String reponse) throws SQLException {
		String sql="INSERT INTO questions (question,choices,answer) VALUES(?,?,?)";
		PreparedStatement st=myConnection.prepareStatement(sql);
		st.setString(1,question);
		st.setString(2, choix);
		st.setString(3, reponse);
		
		int nb=st.executeUpdate();
		System.out.println(nb+" inserted");
	}
	
	//Editer le question en utilisant le id
	public void editQuestion(int id,String question,String choix,String reponse) throws SQLException {
		String sql="UPDATE questions SET question=?, choices=?, answer=? WHERE id=?";
		PreparedStatement st=myConnection.prepareStatement(sql);
		st.setString(1, question);
		st.setString(2,choix);
		st.setString(3, reponse);
		st.setInt(4, id);
		int nb=st.executeUpdate();
		System.out.println(nb+" updated");
	}
		
	//supprimer un question de base de donnees
	public void deleteQuestion(int id) throws SQLException {
		String sql="DELETE FROM questions WHERE id=?";
		PreparedStatement st=myConnection.prepareStatement(sql);
		st.setInt(1, id);
				
		int nb=st.executeUpdate();
		System.out.println(nb+" Deleted");
	}
}
