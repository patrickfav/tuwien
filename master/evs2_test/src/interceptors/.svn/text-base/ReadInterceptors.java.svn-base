package interceptors;


import model.Person;
import annotations.interceptors.InterceptorForService;
import annotations.interceptors.ReadInterceptor;

public class ReadInterceptors {
	
	@ReadInterceptor
	public void globalRead(Object o) {
		System.out.println("global0 read! "+o);
	}
	
	@ReadInterceptor
	public void globalRead1(Object o) {
		System.out.println("global1 read! "+o);
	}
	
	@InterceptorForService("persons")
	@ReadInterceptor
	public void personsRead(Person o) {
		System.out.println("persons read!"+o);
	}
	
	@InterceptorForService("doesNotExist")
	@ReadInterceptor
	public void unknownRead(Person o) {
		System.out.println("persons read2!"+o);
	}
}
