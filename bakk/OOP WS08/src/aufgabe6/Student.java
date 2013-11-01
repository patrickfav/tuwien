package aufgabe6;

/**
 * 
 * the class student extends the class person with the methodes which are needed
 * for a student. the attribute study_state sets the start point of the study. If the
 * study_end attribute has another value than 0 and the attribute grade is 0 then the 
 * student has aborted the study. If the attribute has another value than 0 than the student
 * has finished his study
 *
 */

public class Student extends Person {
	private int study_start;
	private int study_end = 0;
	private float grade = 0;
	
	public Student(String firstname, String surname, int birthyear,
			Geschlecht gender, int study_start) {
		super(firstname, surname, birthyear, gender);
		this.study_start = study_start;
	}
	/**
	 * 
	 * @return the start of the study
	 */
	public int getStudy_start() {
		return study_start;
	}
	
	/**
	 * 
	 * @param study_start changes the start of the study to the given date
	 */
	public void setStudy_start(int study_start) {
		this.study_start = study_start;
	}
	
	/**
	 * 
	 * @return the end of the study
	 */
	public int getStudy_end() {
		return study_end;
	}
	
	/**
	 * 
	 * @param study_end changes the end of the study to the given date
	 */
	public void setStudy_end(int study_end) {
		this.study_end = study_end;
	}
	
	/**
	 * 
	 * @return the Grade of the student
	 */
	public float getGrade() {
		return grade;
	}
	
	/**
	 * 
	 * @param grade sets the given value as grade of the study
	 */
	public void setGrade(float grade) {
		this.grade = grade;
	}
	
	
}
