package AppServeur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ResultatBD {
	Connection myConnection; 
	String user="root";
	String pass="";
	String url="jdbc:mysql://localhost:3308/appdb";
	
	
	public ResultatBD(){
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
	
	//retourner liste des resultats
	public ArrayList<Resultat> getResultats() throws SQLException {
		ArrayList<Resultat> resultatsList=new ArrayList<Resultat>();
		String sql="SELECT * FROM results";
		Statement st = myConnection.createStatement();
		ResultSet rs=st.executeQuery(sql);
			
		while(rs.next()) {
			Resultat res=new Resultat(rs.getInt(1), rs.getString(2), rs.getInt(3));
			resultatsList.add(res);
		}
			
		return resultatsList;
	}
	
	//retourne true si le nom d'utilisateur deja exist au tableau de resultats
	public boolean testResultatName(String username) throws SQLException {
		String sql="SELECT * FROM results";
		Statement st;
		st = myConnection.createStatement();
		ResultSet rs=st.executeQuery(sql);
		boolean isUser=false;
		
		while(rs.next()) {
			//tester si le nom d'utilisateur est deja existe dans tableau de resultats
			if(rs.getString(2).equals(username)) { 
				isUser=true;
			}
			
		}
		
		return isUser;
	}
	
	//Ajouter un resultat au base de données
	public void insertResultat(String username, int note) throws SQLException {
		String sql="INSERT INTO results (username,note) VALUES(?,?)";
		PreparedStatement st=myConnection.prepareStatement(sql);
		st.setString(1,username);
		st.setInt(2, note);
		int nb=st.executeUpdate();
		System.out.println(nb+" inserted");
	}
	
	//editer la note en utilisant le nom d'utilisateur
	public void editResultat(String username, int note) throws SQLException {
		String sql="UPDATE results SET note=? WHERE username=?";
		PreparedStatement st=myConnection.prepareStatement(sql);
		st.setInt(1, note);
		st.setString(2,username);
		int nb=st.executeUpdate();
		System.out.println(nb+" updated");
	}
	
	//supprimer une resultat en utilisant le id
	public void deleteResultat(int id) throws SQLException {
		String sql="DELETE FROM results WHERE id=?";
		PreparedStatement st=myConnection.prepareStatement(sql);
		st.setInt(1, id);
			
		int nb=st.executeUpdate();
		System.out.println(nb+" Deleted");
	}
}
