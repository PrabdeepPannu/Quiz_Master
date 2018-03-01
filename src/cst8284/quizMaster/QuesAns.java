package cst8284.quizMaster;

import java.io.Serializable;

public abstract class QuesAns implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public abstract String getQuestion();
	public abstract void setQuestion(String question);
	
	public abstract String getExplanation();
	public abstract void setExplanation(String explanation);
	
	public abstract String getAuthor();
	public abstract void setAuthor(String author);
	
	public abstract int getCorrectAnswerNumber();
	public abstract void setCorrectAnswerNumber(int correctAnswer);
	
	public abstract int getDifficulty();
	public abstract void setDifficulty(int difficulty);
	
	public abstract int getPoints();
	public abstract void setPoints(int points);
	
	public abstract String[] getAnswers();
	public abstract void setAnswers(String[] answers);
	
	public abstract boolean isCorrect();
	public abstract void setResult(boolean b);

	
}
