package interceptors;

import model.Skill;
import annotations.interceptors.DeleteInterceptor;
import annotations.interceptors.InterceptorForService;

public class UpdateInterceptors {
	
	@DeleteInterceptor
	public void globalDel(Object o) {
		System.out.println("global0 del! "+o);
	}
	
	@InterceptorForService("skills")
	@DeleteInterceptor
	public void skillsDel(Skill o) {
		System.out.println("skills del!"+o);
	}
}
