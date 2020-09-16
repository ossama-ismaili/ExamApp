package AppServeur;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import Security.HashPassword;

public class UtilisateurBD {
	Connection myConnection; 
	String user="root";
	String pass="";
	String url="jdbc:mysql://localhost:3308/appdb";
	
	
	public UtilisateurBD() {
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
	
	//tester si l'utilisateur est existe en base de données
	public boolean testUser(String username,String password) throws SQLException {
		String sql="SELECT * FROM users";
		Statement st;
		st = myConnection.createStatement();
		ResultSet rs=st.executeQuery(sql);
		boolean isUser=false;
		
		while(rs.next()) {
			try {
				//tester le nom d'utilisateur et le hash de mot de passe entre par l'utilisateur avec les infos dans le base de donnees 
				if(rs.getString(3).equals(username) && rs.getString(4).equals(HashPassword.hashPassword(password))) { 
					isUser=true;
				}
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Problem d'algorithme de Securite");
				isUser=false;
			}
		}
		
		return isUser;
	}
	
	//tester si l'utilisateur est un admin ou non
	public boolean testAdmin(String username) throws SQLException {
		String sql="SELECT * FROM users";
		Statement st;
		st = myConnection.createStatement();
		ResultSet rs=st.executeQuery(sql);
		boolean isAdmin=false;
		
		while(rs.next()) {
			//tester le nom d'utilisateur et le boolean admin
			if(rs.getString(3).equals(username) && rs.getBoolean(5)) { 
				isAdmin=true;
			}
		}
		
		return isAdmin;
	}
	
	//retourne fullname en utilisant le nom d'utilisateur
	public String getFullname(String username) throws SQLException {
		String sql="SELECT * FROM users";
		Statement st;
		st = myConnection.createStatement();
		ResultSet rs=st.executeQuery(sql);
		
		while(rs.next()) {
			//tester le nom d'utilisateur et retourne fullname
			if(rs.getString(3).equals(username)) { 
				return rs.getString(2);
			}
		}
		
		return "None";
	}
	
	//retourner liste des etudiants
	public ArrayList<Etudiant> getEtudiants() throws SQLException{
		ArrayList<Etudiant> etudiantsList=new ArrayList<Etudiant>();
		String sql="SELECT id,username,fullname FROM users where admin=0";
		Statement st = myConnection.createStatement();
		ResultSet rs=st.executeQuery(sql);
			
		while(rs.next()) {
			Etudiant etudiant=new Etudiant(rs.getInt(1), rs.getString(2), rs.getString(3));
			etudiantsList.add(etudiant);
		}
				
		return etudiantsList;
	}
	
	//inserer nouveau Etudiant au base de donnees
	public void insertEtudiant(String username,String fullname,String password) throws SQLException {
		String hashPassword;
		
		try {
			hashPassword=HashPassword.hashPassword(password);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Probleme d'algorithme de Securite");
			return;
		}
		
		String sql="INSERT INTO users (username,fullname,password,admin) VALUES(?,?,?,0)";
		PreparedStatement st=myConnection.prepareStatement(sql);
		st.setString(1,username);
		st.setString(2, fullname);
		st.setString(3, hashPassword);
		
		int nb=st.executeUpdate();
		System.out.println(nb+" inserted");
	}
	
	//Editer le nom d'utilisateur et comptet nom en utilisant le id
	public void editUser(int id,String username,String fullname) throws SQLException {
		String sql="UPDATE users SET fullname=?, username=? WHERE id=?";
		PreparedStatement st=myConnection.prepareStatement(sql);
		st.setString(1, fullname);
		st.setString(2,username);
		st.setInt(3, id);
		int nb=st.executeUpdate();
		System.out.println(nb+" updated");
	}
	
	//supprimer un utilisateur de base de donnees
	public void deleteUser(int id) throws SQLException {
		String sql="DELETE FROM users WHERE id=?";
		PreparedStatement st=myConnection.prepareStatement(sql);
		st.setInt(1, id);
				
		int nb=st.executeUpdate();
		System.out.println(nb+" Deleted");
	}
}

