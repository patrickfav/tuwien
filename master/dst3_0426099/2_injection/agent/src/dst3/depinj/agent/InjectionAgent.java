package dst3.depinj.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import dst3.annotations.Component;

public class InjectionAgent implements ClassFileTransformer{
	
	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		
		//System.out.println("Transfrom called: "+transformURI(className)+" - "+classBeingRedefined);
		
		try {
			ClassPool pool = ClassPool.getDefault();
			
			CtClass cc = pool.get(transformURI(className));
			
			if(cc.getAnnotation(Component.class) !=null) {
				//System.out.println("Component type!");

				for(CtConstructor ctcons: cc.getConstructors()) {
					ctcons.insertAfter(getInjectedSource(transformURI(className)));
				}
				
				//cc.writeFile();
				return cc.toBytecode();
				
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		return null;
	}
	
	public String getInjectedSource(String name) {
		return 
		"Class cc = this.getClass();"+
		"if(cc.getName().equals(\""+name+"\")) {"
		+"dst3.depinj.InjectionController.getInstance().initialize(this); }";
	}
	
	public String transformURI(String src) {
		return src.replace("/", ".");
	}
}
