package aufgabe6;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List states = new List();

		Staat austria = new Staat("Austria");
		Staat germany = new Staat("Germany");
		Staat switzerland = new Staat("Switzerland");
		Staat belgium = new Staat("Belgium");
		Staat netherlands = new Staat("Netherlands");
		
		states.add(austria);
		states.add(germany);
		states.add(switzerland);
		states.add(belgium);
		states.add(netherlands);
		
		Geschlecht m1 = new Male(true, true, true);
		Geschlecht m2 = new Male(false, true, true);
		Geschlecht m3 = new Male(false, false, true);
		Geschlecht m4 = new Male(false, false, false);
		
		Geschlecht w = new Female();

		Person p1 = new Person("Otto", "Bolster", 1965, m1);
		Person p2 = new Person("Michael", "Kandorf", 1934, m2);
		Person p3 = new Person("Nikki", "Bachman", 1967, m3);
		Person p4 = new Person("Lukas", "Karner", 1988, m3);
		Person p5 = new Person("Jimmy", "Eber", 1996, m4);
		Person p6 = new Person("Olaf", "Kunz", 1945, m2);
		Person p7 = new Person("Klaus", "Laus", 1974, m3);
		Person p8 = new Person("Samir", "Wurz", 1978, m4);
		Person p9 = new Person("Gerald", "Poller", 1999, m4);
		Person p0 = new Person("Ingolf", "Sack", 2001, m4);
		
		Person p11 = new Person("Susanne", "Unterberg", 1965, w);
		Geschlecht test = p11.getGender();
		if(test instanceof Female)
			((Female) test).setFormer_name("Passer");
		
		Person p12 = new Person("Michaela", "Oberberg", 1934, w);
		Person p13 = new Person("Nora", "Bergmann", 1967, w);
		Person p14 = new Person("Luise", "Langlauf", 1988, w);
		Person p15 = new Person("Rosmarie", "Füller", 1996, w);
		Person p16 = new Person("Siegried", "Ernst", 1945, w);
		Person p17 = new Person("Klaudia", "Newman", 1974, w);
		Person p18 = new Person("Pauline", "Peterson", 1978, w);
		Person p19 = new Person("Charlotte", "Karotte", 1999, w);
		Person p10 = new Person("Hannelore", "Elner", 1954, w);
	
		austria.addPerson(p1);
		austria.addPerson(p11);
		austria.addPerson(p2);
		austria.addPerson(p12);
		
		germany.addPerson(p3);
		germany.addPerson(p13);
		germany.addPerson(p4);
		germany.addPerson(p14);
		
		switzerland.addPerson(p5);
		switzerland.addPerson(p15);
		switzerland.addPerson(p6);
		switzerland.addPerson(p16);
		
		belgium.addPerson(p7);
		belgium.addPerson(p17);
		belgium.addPerson(p8);
		belgium.addPerson(p18);
		
		netherlands.addPerson(p9);
		netherlands.addPerson(p19);
		netherlands.addPerson(p0);
		netherlands.addPerson(p10);
		
		
		Student s1 = new Student("Anton", "Saaber", 1965, m1, 1975);
		s1.setStudy_end(1980);
		s1.setGrade(4.5f);
		
		Student s2 = new Student("Paul", "Pizza", 1975, m2, 1990);
		s2.setStudy_end(1995);
		s2.setGrade(3.5f);		
		
		Student s3 = new Student("Gabriel", "Erz", 1995, m3, 2005);		
		
		Student s4 = new Student("Joschka", "Toll", 1955, m4, 1975);
		s4.setStudy_end(1983);
		s4.setGrade(2.5f);
		
		Student s5 = new Student("Sepp", "Depp", 1975, m2, 1987);
		s5.setStudy_end(1993);
		s5.setGrade(1.5f);	
		
		Student s6 = new Student("Antonia", "Daum", 1965, w, 1999);
		s6.setStudy_end(2001);

		Student s7 = new Student("Toni", "Lümmel", 1960, m1, 1979);
		s7.setStudy_end(1985);
		s7.setGrade(2.48f);
		
		Student s8 = new Student("Armin", "Ass", 1970, m2, 1992);
		s8.setStudy_end(1998);
		s8.setGrade(1.89f);
		
		Student s9 = new Student("Andi", "Latte", 1955, m3, 1973);
		s9.setStudy_end(1980);
		s9.setGrade(2.87f);
		
		Student s10 = new Student("Kathi", "Pult", 1961, w, 1998);
		s10.setStudy_end(2004);
		s10.setGrade(3.04f);
		
		Student s11 = new Student("Karl", "Ünzel", 1965, m1, 1999);
		s11.setStudy_end(2003);
		s11.setGrade(1.74f);
		
		Student s12 = new Student("Natalie", "Sonne", 1954, w, 1999);
		s12.setStudy_end(2004);
		s12.setGrade(2.44f);
		
		Student s13 = new Student("Annette", "Stunde", 1973, w, 1994);
		s13.setStudy_end(1999);
		s13.setGrade(3.45f);
		
		Student s14 = new Student("Ute", "Locke", 1965, w, 1999);
		s14.setStudy_end(2000);
		
		Student s15 = new Student("Rafael", "Mulla", 1965, m1, 1997);
		s15.setStudy_end(2002);
		
		Student s16 = new Student("Jutta", "Kutta", 1965, w, 1965);
		s16.setStudy_end(1990);
		
		Student s17 = new Student("Walter", "Schorn", 1965, m1, 1999);
		s17.setStudy_end(2001);
		
		Student s18 = new Student("Heinz","Kischer",1931,m3,2004);
		s18.setStudy_end(2005);
		
		Student s19 = new Student("Annette","Halbestuhnde",1931,m3,2004);
		s19.setStudy_end(2007);
		
		Student s20 = new Student("Chirs","Sharma",1981,m2,2000);
		s20.setStudy_end(2002);
		
		
		germany.addPerson(s1);
		austria.addPerson(s2);
		austria.addPerson(s3);
		austria.addPerson(s4);
		austria.addPerson(s5);
		austria.addPerson(s6);
		austria.addPerson(s7);
		austria.addPerson(s8);
		austria.addPerson(s9);
		austria.addPerson(s10);
		austria.addPerson(s11);
		germany.addPerson(s12);
		austria.addPerson(s13);
		austria.addPerson(s14);
		belgium.addPerson(s15);
		austria.addPerson(s16);
		austria.addPerson(s17);
		austria.addPerson(s18);
		switzerland.addPerson(s19);
		austria.addPerson(s20);
		
		float avg_dropout_perc_m = 0;
		float avg_dropout_perc_f = 0;
		
		float avg_graduation_perc_m =0;
		float avg_graduation_perc_f=0;
		
		float avg_studytime_m = 0;
		float avg_studytime_f = 0;
		
		int k=0;
		
		for(Iterator i = states.iterator();i.hasNext();) {
			Staat s = (Staat) i.next();
			
			avg_dropout_perc_m += s.getPercentageOfDropout(1);
			avg_dropout_perc_f += s.getPercentageOfDropout(2);
			
			avg_graduation_perc_m += s.getPercentageOfGraduates(1);
			avg_graduation_perc_f += s.getPercentageOfGraduates(2);
			
			avg_studytime_m += s.getYearsOfStudyDuration(1);
			avg_studytime_f += s.getYearsOfStudyDuration(2);
			k++;
		}
		
		avg_dropout_perc_m = avg_dropout_perc_m/ k;
		avg_dropout_perc_f = avg_dropout_perc_f/ k;
		
		avg_graduation_perc_m = avg_graduation_perc_m/k;
		avg_graduation_perc_f = avg_graduation_perc_f/k;
		
		avg_studytime_m = avg_studytime_m/k;
		avg_studytime_f = avg_studytime_f/k;
		
		System.out.println("==========================");
		System.out.println("Searching for....");
		System.out.println("==========================");
		System.out.println("");
		System.out.println("Find Michael");
		Person f = austria.searchPerson("Michael", "Kandorf");
		System.out.println(f);
		
		System.out.println("Remove Michael");
		austria.removePerson("Michael", "Kandorf");
		Person a = austria.searchPerson("Michael", "Kandorf");
		System.out.println("Find Michael");
		System.out.println(a);
		
		System.out.println("");
		System.out.println("");
		
		System.out.println("==========================");
		System.out.println("Austria Statistics");
		System.out.println("==========================");
		
		System.out.println("Male graduates: " + austria.getPercentageOfGraduates(1) + "%");
		System.out.println("Female graduates: " + austria.getPercentageOfGraduates(2) + "%");
		System.out.println("Male & Female graduates: " + austria.getPercentageOfGraduates(0) + "%");
		
		System.out.println("Male duration: " + austria.getYearsOfStudyDuration(1) + " years");
		System.out.println("Female duration: " + austria.getYearsOfStudyDuration(2) + " years");
		System.out.println("Male & Female duration: " + austria.getYearsOfStudyDuration(0) + " years");
		
		System.out.println("Male dropouts: " + austria.getPercentageOfDropout(1) + "%");
		System.out.println("Female dropouts: " + austria.getPercentageOfDropout(2) + "%");
		System.out.println("Male & Female dropouts: " + austria.getPercentageOfDropout(0) + "%");
		
		System.out.println("");
		System.out.println("");
		
		System.out.println("==========================");
		System.out.println("Summarized Statistics of all States/Countries");
		System.out.println("==========================");
		
		System.out.println("");
		
		System.out.println("Male dropouts:" + avg_dropout_perc_m + "%");
		System.out.println("Female dropouts:" + avg_dropout_perc_f + "%");
		System.out.println("Dropouts:" + ((avg_dropout_perc_f+avg_dropout_perc_m)/2) + "%");
		
		System.out.println("");
		
		System.out.println("Male graduates:" + avg_graduation_perc_m + "%");
		System.out.println("Female graduates:" + avg_graduation_perc_f + "%");
		System.out.println("Graduates:" + ((avg_graduation_perc_f+avg_graduation_perc_m)/2) + "%");
		
		System.out.println("");
		
		System.out.println("Male duration:" + avg_studytime_m + " years");
		System.out.println("Female duration:" + avg_studytime_f + " years");
		System.out.println("Duration:" + ((avg_studytime_m+avg_studytime_f)/2) + " years");
		
	}

}
