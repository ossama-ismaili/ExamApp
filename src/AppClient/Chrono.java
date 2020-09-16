package AppClient;

public class Chrono {
	private int seconds;
	private int minutes;
	private int heures;
	
	public Chrono(int seconds, int minutes, int heures) {
		this.seconds = seconds;
		this.minutes = minutes;
		this.heures = heures;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getHeures() {
		return heures;
	}

	public void setHeures(int heures) {
		this.heures = heures;
	}
	
	public void tiktak() {
		this.seconds++;
		if(this.seconds>=60) {
			this.seconds-=60;
			this.minutes++;
		}
		if(this.minutes>=60) {
			this.minutes-=60;
			this.heures++;
		}
		if(this.heures>=24) {
			this.seconds=0;
			this.minutes=0;
			this.heures=0;
		}
	}

	@Override
	public String toString() {
		return "Chrono [seconds=" + seconds + ", minutes=" + minutes + ", heures=" + heures + "]";
	}
}
