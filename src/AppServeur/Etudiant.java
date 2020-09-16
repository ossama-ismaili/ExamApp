package AppServeur;

public class Etudiant {
	private int etudiantId;
	private String username;
	private String fullname;
	
	public Etudiant(int etudiantId, String username, String fullname) {
		this.etudiantId = etudiantId;
		this.username = username;
		this.fullname = fullname;
	}

	public int getEtudiantId() {
		return etudiantId;
	}

	public void setEtudiantId(int etudiantId) {
		this.etudiantId = etudiantId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Override
	public String toString() {
		return "Etudiant [etudiantId=" + etudiantId + ", username=" + username + ", fullname=" + fullname + "]";
	}
}
