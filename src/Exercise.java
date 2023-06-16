
public class Exercise {
	int time;
	int startNumber;
	int endNumber;
	int questionNumber;
	int trueNumber;
	
	
	public Exercise(int startNumber, int endNumber, int questionNumber) {
		super();
		this.startNumber = startNumber;
		this.endNumber = endNumber;
		this.questionNumber = questionNumber;
	}
	
	
	
	public int getTime() {
		return time;
	}


	public void setTime(int seconds) {
		this.time = seconds;
	}


	public int getStartNumber() {
		return startNumber;
	}


	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
	}


	public int getEndNumber() {
		return endNumber;
	}


	public void setEndNumber(int endNumber) {
		this.endNumber = endNumber;
	}


	public int getQuestionNumber() {
		return questionNumber;
	}


	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}


	public int getTrueNumber() {
		return trueNumber;
	}


	public void setTrueNumber(int trueNumber) {
		this.trueNumber = trueNumber;
	}


	


	@Override
	public String toString() {
		return "Exercise [time=" + time + ", startNumber=" + startNumber + ", endNumber=" + endNumber
				+ ", questionNumber=" + questionNumber + ", trueNumber=" + trueNumber + "]";
	}
	
	public String toStringFile() {
		return "0," + startNumber + "," + endNumber+ "," + questionNumber + ",0";
	}
	public String toStringUpdatedFile() {
		return time + "," + startNumber + "," + endNumber+ "," + questionNumber + "," + trueNumber;
	}
	
}
