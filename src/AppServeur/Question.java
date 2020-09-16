package AppServeur;

public class Question {
	private int questionId;
	private String question;
	private String choix;
	private String reponse;
	
	public Question(int questionId, String question, String choix,String reponse) {
		this.questionId = questionId;
		this.question = question;
		this.choix = choix;
		this.reponse=reponse;
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
		return "Question [questionId=" + questionId + ", question=" + question + ", choix=" + choix + ", reponse="
				+ reponse + "]";
	}
}
