package interceptors;

import model.Skill;
import annotations.interceptors.CreateInterceptor;
import annotations.interceptors.InterceptorForService;

public class CreateInterceptors {
	
	@CreateInterceptor
	public void globalCreate(Object o) {
		System.out.println("global0 create! "+o);
	}
	
	@InterceptorForService("skills")
	@CreateInterceptor
	public void skillCreate(Skill o) {
		System.out.println("skills create!"+o);
	}
}
