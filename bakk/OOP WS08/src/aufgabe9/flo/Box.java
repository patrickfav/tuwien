package aufgabe9.flo;

public abstract class Box implements Gift {
	
	protected Form innerform;
	protected Form outerform;
	protected float thickness;
	protected float height;
	protected Gift gift;
	
	// abstract box methods
	public abstract Form getForm();
	public abstract float getHeight();
	public abstract void setGift(Gift gift);
	public abstract Gift getGift();
	public abstract Gift pack(Store store);
	public abstract float volume();
	
}
