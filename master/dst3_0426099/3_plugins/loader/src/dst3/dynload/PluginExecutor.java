package dst3.dynload;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javassist.ByteArrayClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.bytecode.ClassFile;
import dst3.annotations.Component;
import dst3.dynload.io.DirectoryIndex;
import dst3.dynload.io.JarResources;

public class PluginExecutor implements IPluginExecutor {

	private final static int THREAD_POOL_SIZE = 10;
	private final static long POLLING_INTERVALL = 3000;
	private final static String INTERFACE_NAME = "IPluginExecutable";

	private List<DirectoryIndex> monitoredFiles;
	private Timer timer;
	private ExecutorService threadPool;

	public PluginExecutor() {
		timer = new Timer(true);
		monitoredFiles = Collections
				.synchronizedList(new ArrayList<DirectoryIndex>());
		threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}

	@Override
	public void monitor(File dir) {
		monitoredFiles.add(new DirectoryIndex(dir));
	}

	@Override
	public void stopMonitoring(File dir) {
		monitoredFiles.remove(new DirectoryIndex(dir));
	}

	@Override
	public void start() {
		System.out.println("Starting Service.");
		timer.schedule(new FileMonitor(), 0, POLLING_INTERVALL);
	}

	@Override
	public void stop() {
		System.out.println("Stopping Service.");
		threadPool.shutdown();
		timer.cancel();
	}

	/* ************************************************* PRIVATE METHODS */

	private List<File> getAllFilesFromDirectory(File folder) {
		if (folder.exists())
			return Arrays.asList(folder.listFiles());

		return null;
	}

	public static String getFileExtension(String filename) {
		String ext = filename.substring(filename.lastIndexOf('.') + 1,
				filename.length());
		return ext;
	}

	private void loadJar(File f) {

		JarResources jr = null;
		ClassFile cf = null;
		ByteArrayInputStream fin = null;
		JarClassLoader jcr = null;
		Class<?> c =null;
		byte[] byteCode;
		
		try {
			/* load jar */
			jr = new JarResources(f.getAbsolutePath());
			System.out.println("Found classes: "
					+ jr.getAllResources().keySet());

			/* load classes in new classloader */
			jcr = new JarClassLoader(f,jr.getAllResources());
			
			/* cycle classes and see if extends IPluginExecutable */
			for (String name : jr.getAllResources().keySet()) {
				
				System.out.println("Processing " + name);
				try {
					/* only process class files */
					if (getFileExtension(name).equals("class")) {
						byteCode = jr.getAllResources().get(name);
						
						fin = new ByteArrayInputStream(byteCode);
						cf = new ClassFile(new DataInputStream(fin));

						/* checks for interfaces */
						for (String s : cf.getInterfaces()) {
							if (getFileExtension(s).equals(INTERFACE_NAME)) {
								
								/* get class and execute in new thread */
								c = jcr.loadClass(name);
								threadPool.execute(new ClassExecutingWorker(c));
								//Thread.sleep(1000);
							}
						}
						fin.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (fin != null)
							fin.close();
					} catch (Exception e) {
					}

					/* gc help */
					byteCode = null;
					cf = null;
					fin = null;
					
				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jr = null;
			jcr = null;
		}

	}
	
	private String transformURI(String src) {
		src = src.replace(".class", "");
		return src.replace("/", ".");
	}
	
	private String untransformURI(String src) {
		src = src.replace(".class", "");
		src = src.replace(".", "/");
		return (src+".class");
	}
	
	public String getInjectedSource(String name) {
		return 
		"Class cc = this.getClass();"+
		"if(cc.getName().equals(\""+name+"\")) {"
		+"dst3.depinj.InjectionController.getInstance().initialize(this); }";
	}
	
	/* ************************************************* PRIVATE CLASSES */

	/* loading jar worker */
	private class ClassExecutingWorker extends Thread {
		private Class<?> c;
		
		public ClassExecutingWorker(Class<?> c) {
			super();
			this.c = c;
		}

		public void run() {
			Object o;
			try {
				o = c.newInstance();
				((IPluginExecutable)o).execute();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/* the monitor thread */
	private class FileMonitor extends TimerTask {
		public void run() {

			System.out.println("Scanning dirs...");

			for (DirectoryIndex di : monitoredFiles) {
				for (File f : getAllFilesFromDirectory(di.getDirectory())) {
					if (getFileExtension(f.getAbsolutePath()).equals("jar")) {
						if (di.getFileMap().containsKey(f)) {
							if (di.getFileMap().get(f) < f.lastModified()) {
								/* newer file found */
								di.getFileMap().put(f, f.lastModified());
								System.out.println("Putting "+ f.getAbsolutePath());
								loadJar(f);
							}
						} else {
							/* not found put */
							di.getFileMap().put(f, f.lastModified());
							System.out.println("Putting " + f.getAbsolutePath());
							loadJar(f);
						}
					}
				}
			}
		}
	}

	private class JarClassLoader extends SecureClassLoader {
		
		private ClassPool pool;
		private Map<String,byte[]> classMap;
		
		public JarClassLoader(File f,Map<String,byte[]> classMap) {
			this.classMap = classMap;
			pool = new ClassPool(true);
			
			for(String s: classMap.keySet()) {
				if(getFileExtension(s).equals("class")) {
					//System.out.println("Adding "+s);
					pool.insertClassPath(new ByteArrayClassPath(transformURI(s), classMap.get(s)));
					
				}
			}
			
			
		}
		
		@Override
		protected Class<?> findClass(String name) {
			try {
				//System.out.println("Check: "+transformURI(name)+" before: "+classMap.containsKey(name));
				
				if(classMap.containsKey(untransformURI(name))) {
					CtClass cc = pool.get(transformURI(name));
					
					
					if(cc.getAnnotation(Component.class) !=null) {
	
						for(CtConstructor ctcons: cc.getConstructors()) {
							ctcons.insertAfter(getInjectedSource(transformURI(name)));
						}
						
					}
					
					byte[] b = cc.toBytecode();
					
					//fin.close();
		            return defineClass(transformURI(name), b, 0, b.length);
				} else {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
	}
	
	
}
