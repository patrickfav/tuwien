package interceptors;

import model.Skill;
import annotations.interceptors.InterceptorForService;
import annotations.interceptors.UpdateInterceptor;

public class DeleteInterceptors {
	
	@UpdateInterceptor
	public void globalUpdate(Object o) {
		System.out.println("global0 create! "+o);
	}
	
	@InterceptorForService("skills")
	@UpdateInterceptor
	public void skillsUpdate(Skill o) {
		System.out.println("skills create!"+o);
	}
}
