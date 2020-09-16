package AppServeur;

public class Resultat {
	private int resultatId;
	private String username;
	private int note;
	
	public Resultat(int resultatId, String username, int note) {
		this.resultatId = resultatId;
		this.username = username;
		this.note = note;
	}

	public int getResultatId() {
		return resultatId;
	}

	public void setResultatId(int resultatId) {
		this.resultatId = resultatId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Resultat [resultatId=" + resultatId + ", username=" + username + ", note=" + note + "]";
	}
}
