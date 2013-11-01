package aufgabe7;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Media
		StandAloneMemory br = new BluRay();
		StandAloneMemory dvd = new DVD();
		StandAloneMemory cd = new CD();
		StandAloneMemory hd = new HardDisk(1000000000.0f);
		
		
		//Devices
		Drive brdrive = new BRDrive();
		Drive dvdrive = new DVDrive();
		
		USBDrive usbdrive = new USBDrive();
		USBPort usbdev0 = new USBPort(hd);
		DriveEnabledUSBPort usbdev1 = new DriveEnabledUSBPort(usbdrive);
		
		//Media Actions
		br.use(24696061952.0f); //use 23 GB
		dvd.use(4831838208.0f); //use 4.5 GB
		cd.use(478150656.0f); //use 456 MB
		
		//BR Drive Actions
		System.out.println("");
		System.out.println("=======================");
		System.out.println("BR DRIVE TESTS");
		System.out.println("=======================");
		
		brdrive.load(br);
		System.out.println((brdrive.available()/1024/1024/1024)+" GB left");
		brdrive.unload();
		
		brdrive.load(cd);
		brdrive.load(dvd);
		System.out.println((brdrive.available()/1024/1024/1024)+" GB left");
		brdrive.load(br);
		brdrive.unload();
		
		
		
		//DVD Drive Actions
		
		System.out.println("");
		System.out.println("=======================");
		System.out.println("DVD DRIVE TESTS");
		System.out.println("=======================");
		
		dvdrive.load(br);
		dvdrive.load(dvd);
		System.out.println((dvdrive.available()/1024/1024)+" MB left");
		dvdrive.load(cd);
		dvdrive.unload();
		
		dvdrive.load(cd);
		System.out.println((dvdrive.available()/1024/1024)+" MB left");
		dvdrive.unload();
		
		
		//USB Drive Actions
		
		System.out.println("");
		System.out.println("=======================");
		System.out.println("USB DRIVE TESTS");
		System.out.println("=======================");
		
		usbdev1.plugIn();
		usbdev1.load(dvd);
		System.out.println((usbdev1.available()/1024/1024)+" MB left");
		usbdev1.load(cd);
		usbdev1.unload();
		usbdev1.load(br);
		usbdev1.plugOut();
		
		usbdev1.load(dvd);
		usbdev1.plugOut();
		
		//USB Port Actions
		
		System.out.println("");
		System.out.println("=======================");
		System.out.println("USB Device (Port) TESTS");
		System.out.println("=======================");
		
		System.out.println((usbdev0.available()/1024/1024)+" MB left");
		
		usbdev0.plugIn();
		System.out.println((usbdev0.available()/1024/1024)+" MB left");
		usbdev0.plugOut();
		
		System.out.println((usbdev0.available()/1024/1024)+" MB left");
		
		usbdev0.plugIn();
		usbdev0.use(500.0f*1024.0f*1024.0f);
		System.out.println((usbdev0.available()/1024/1024)+" MB left");
		usbdev0.plugOut();
		
		USBPort usbdev2 = new USBPort(dvd);
		usbdev2.use(500.0f*1024.0f*1024.0f);
	}

}
