package products.factory;

import java.util.Random;


import products.Teddybear;
import products.parts.Arm;
import products.parts.Body;
import products.parts.GreenCap;
import products.parts.Head;
import products.parts.Leg;
import products.parts.RedCap;
import products.parts.abstracts.Product;
import workers.id.WorkerID;

public class ProductsFactory {
	
	public static final int ASSEMBLE_TIME_MIN = 1000;
	public static final int ASSEMBLE_TIME_MAX = 3000;
	
	public static final int PRODUCE_ARMS = 0;
	public static final int PRODUCE_LEGS = 1;
	public static final int PRODUCE_BODIES = 2;
	public static final int PRODUCE_HEADS = 3;
	public static final int PRODUCE_REDCAPS = 4;
	public static final int PRODUCE_GREENCAPS = 5;
	
	private static Random rand = new Random();
	
	/*
	 * FACTORIES
	 */
	
	public static Leg createLeg(WorkerID creator,double defectRate) {
		return new Leg(generateAssembleTime(), creator, generateDefectBoolean(defectRate));
	}
	public static Arm createArm(WorkerID creator,double defectRate) {
		return new Arm(generateAssembleTime(), creator, generateDefectBoolean(defectRate));
	}
	public static Body createBody(WorkerID creator,double defectRate) {
		return new Body(generateAssembleTime(), creator, generateDefectBoolean(defectRate));
	}
	public static Head createHead(WorkerID creator,double defectRate) {
		return new Head(generateAssembleTime(), creator, generateDefectBoolean(defectRate));
	}
	public static GreenCap createGreenCap(WorkerID creator,double defectRate) {
		return new GreenCap(generateAssembleTime(), creator, generateDefectBoolean(defectRate));
	}
	public static RedCap createRedCap(WorkerID creator,double defectRate) {
		return new RedCap(generateAssembleTime(), creator, generateDefectBoolean(defectRate));
	}
	public static Teddybear createTeddybear() {
		return new Teddybear();
	}
	public static Teddybear createDefectCheckTemplate() {
		return new Teddybear(false,null);
	}
	public static Teddybear createWeightCheckTemplate() {
		return new Teddybear(null,false);
	}
	
	public static Product producePart(int type, WorkerID creator,double defectRate) {
		switch(type) {
			case PRODUCE_ARMS:
				return createArm(creator,defectRate);
			case PRODUCE_LEGS:
				return createLeg(creator,defectRate);
			case PRODUCE_BODIES:
				return createBody(creator,defectRate);
			case PRODUCE_HEADS:
				return createHead(creator,defectRate);
			case PRODUCE_REDCAPS:
				return createRedCap(creator,defectRate);
			case PRODUCE_GREENCAPS:
				return createGreenCap(creator,defectRate);
			default:
				return null;
		}
	}
	
	/*
	 * HELPER METHODS
	 */
	private static int generateAssembleTime() {
		return ASSEMBLE_TIME_MIN + (int)(rand.nextDouble() * (ASSEMBLE_TIME_MAX - ASSEMBLE_TIME_MIN));
	}
	private static boolean generateDefectBoolean(double defectRate) {
		if(rand.nextDouble() > defectRate) {
			return false;
		} else {
			return true;
		}
	}
}
