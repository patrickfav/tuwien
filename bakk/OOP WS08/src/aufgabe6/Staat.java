package aufgabe6;

import java.util.Calendar;


public class Staat {
	public String state_name;
	public List list_persons;
	
	/**
	 * Generates a State with name
	 * 
	 * @param state_name
	 */
	public Staat(String state_name) {
		this.state_name = state_name;
		this.list_persons = new List();
	}
	
	/**
	 * Inserts a Person to list
	 * 
	 * @param p
	 * @return
	 */
	public boolean addPerson(Person p) {
		// inserts a Person p to list
		list_persons.add(p);
		return true;
	}
	
	/**
	 * Removes a Person from list
	 * 
	 * @param firstname
	 * @param surname
	 * @return
	 */
	public boolean removePerson(String firstname, String surname) {
		// searches a person by name and surname
		Person p = searchPerson(firstname, surname);
		// removes Person p from list
		list_persons.remove(p);		
		return true;
	}
	
	/**
	 * Searches a Person in list
	 * 
	 * @param firstname
	 * @param surname
	 * @return
	 */
	public Person searchPerson(String firstname, String surname) {
		for(Iterator i = list_persons.iterator(); i.hasNext();) {		      
			Object s = i.next();
			if (s instanceof Person) {
				Person p = (Person) s;
				if(p.getFirstname().equals(firstname) && p.getSurname().equals(surname))
					return p;
			}
		}
		return null;
	}
	
	/**
	 * Returns the percentage of graduates
	 * 
	 * @param g -> 0: Males & Females, 1: Males, 2: Females
	 * @return
	 */
	public float getPercentageOfGraduates(int g) {
		float numPersons = 0;
		float numGraduates = 0;
		float percentage = 0;
		List genderlist = getGenderList(g);
		for(Iterator i = genderlist.iterator(); i.hasNext();) {		      
			Object s = i.next();
			if (s instanceof Person) {
				Person p = (Person) s;
				
				Calendar toDay = Calendar.getInstance();
				int year = toDay.get(Calendar.YEAR);
				// select all persons age over 30

				if(p.getBirthyear() < year-30 ) {

					numPersons++;
					if(p instanceof Student) {
						Student stud = (Student)p;
						// if student has a grade and study end
						if(stud.getGrade() != 0 && stud.getStudy_end() != 0)
							numGraduates++;						
					}
				}

			}
		}
		// return the percentage
		if(numPersons == 0)  {
			return 0;
		} else {
			percentage = (numGraduates/numPersons)*100;		
			return percentage;
		}
	}
	
	/**
	 * Returns the years of study duration
	 * 
	 * @param g -> 0: Males & Females, 1: Males, 2: Females
	 * @return
	 */
	public float getYearsOfStudyDuration(int g) {
		float numStudents = 0;
		float numDuration = 0;
		float years = 0;
		List genderlist = getGenderList(g);
		for(Iterator i = genderlist.iterator(); i.hasNext();) {		      
			Object s = i.next();
			if (s instanceof Person) {
				Person p = (Person) s;	
				if(p instanceof Student) {				
					Student stud = (Student)p;
					// if student has a grade and study end 
					if(stud.getGrade() != 0 && stud.getStudy_end() != 0) {
						numStudents++;						
						numDuration += (stud.getStudy_end() - stud.getStudy_start());
					}
				}
			}
		}
		// return years
		if(numStudents == 0) {
			return 0;
		} else {
			years = (numDuration/numStudents);
			return years;
		}	
	}
	
	/**
	 * Returns the percentage of dropouts
	 * 
	 * @param g -> 0: Males & Females, 1: Males, 2: Females
	 * @return
	 */
	public float getPercentageOfDropout(int g) {
		float numStudents = 0;
		float numDropouts = 0;
		float percentage = 0;
		List genderlist = getGenderList(g);
		for(Iterator i = genderlist.iterator(); i.hasNext();) {		      
			Object s = i.next();
			if (s instanceof Person) {
				Person p = (Person) s;	
				if(p instanceof Student) {				
					Student stud = (Student)p;
					numStudents++;
					// if student has a 0 grade and study end -> dropout
					if(stud.getGrade() == 0 && stud.getStudy_end() != 0) {
						numDropouts++;
					}
				}
			}
		}
		// return percentage
		if(numStudents == 0) {
			return 0;
		} else {
			percentage = (numDropouts/numStudents)*100;
			return percentage;
		}	
	}
	
	/**
	 * Returns the list of Genders
	 * 
	 * @param g -> 0: Males & Females, 1: Males, 2: Females
	 * @return
	 */
	protected List getGenderList(int g) {
		List males = new List();
		List females = new List();
		// divides males and females 
		for(Iterator i = list_persons.iterator(); i.hasNext();) {		      
			Object s = i.next();
			if (s instanceof Person) {
				Person p = (Person) s;
				if(p.getGender() instanceof Male){
					males.add(p);
				} else if(p.getGender() instanceof Female) {
					females.add(p);
				}
			}
		}
		// return the asked list of genders
		switch(g) {
			case 0:
				return list_persons;
			case 1:
				return males;
			case 2:
				return females;
			default:
				return list_persons;
		}
	}
}
