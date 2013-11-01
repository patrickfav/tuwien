/*
 * PROJECT: TLJava
 * $RCSfile: Entity.java,v $
 * $Revision: 1.3 $
 * AUTHOR: hiasi
 * DATE: 10.12.2004
 * LANGUAGE: Java
 * DESCRIPTION: See class comment
 *
 * $Log: Entity.java,v $
 * Revision 1.3  2006/05/12 20:25:23  0425906
 * code conventions update
 *
 * Revision 1.2  2006/05/05 14:37:23  0425906
 * hibernate update auf 3.0
 *
 * Revision 1.1  2005/11/25 21:58:11  0425426
 * TLCore
 *
 * Revision 1.1  2004/12/10 15:05:38  tlcorej1
 * initial commit
 *
 *
 */

package ticketline.db;

/**
 * @author hiasi
 */
public class Entity {

	private boolean saved = false;

	public void onSave() {
		saved = true;
	}

	public void onLoad() {
		saved = true;
	}

	public boolean isSaved() {
		return saved;
	}

}
