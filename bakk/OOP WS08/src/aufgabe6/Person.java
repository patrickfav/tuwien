package aufgabe6;

public class Person {
	protected String firstname;
	protected String surname;
	protected int birthyear;
	protected Geschlecht gender;
	
	/**
	 * creates an instance of person with the given values
	 * @param firstname
	 * @param surname
	 * @param birthyear
	 * @param gender
	 */
	public Person(String firstname, String surname, int birthyear,
			Geschlecht gender) {
		this.firstname = firstname;
		this.surname = surname;
		this.birthyear = birthyear;
		this.gender = gender;
	}

	/**
	 * 
	 * @return the firstname of the person
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * 
	 * @return the surname of the person
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 * 
	 * @return return the genderobject of the person
	 */
	public Geschlecht getGender() {
		return gender;
	}
	
	/**
	 * 
	 * @return returns the birthyear of the person
	 */
	public int getBirthyear() {
		return birthyear;
	}
	
}
