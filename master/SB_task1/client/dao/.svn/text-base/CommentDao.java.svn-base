package dao;

import gui.mem.GuiMemory;

import java.rmi.RemoteException;

import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

import org.apache.log4j.Logger;

import dao.abstracts.AbstractDao;
import entities.Comment;
import entities.Peer;

public class CommentDao extends AbstractDao {

	private static Logger log = Logger.getLogger(CommentDao.class);
	private JavaSpace space;

	public static CommentDao createDao() {
		return new CommentDao();
	}

	public CommentDao() {
		super();
		this.space = GuiMemory.getSpaceInstance();
	}

	public Peer take(Peer temp, Transaction txn, long lease) {
		try {
			log.debug("Take " + temp + " from space");
			return (Peer) space.takeIfExists(temp, txn, lease);
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			return null;
		}
	}

	public Comment find(Comment comment) {
		return find(comment, null, DEFAULT_READ_TIME);
	}

	public Comment find(Comment comment, Transaction t, long lease) {
		try {
			log.debug("Read " + comment + " from space");
			return (Comment) space.readIfExists(comment, t, lease);
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			return null;
		}
	}

	
	public void write(Comment comment, Transaction txn, long lease) {
		try {
			log.debug("Write " + comment + " to space");
			space.write(comment, txn, lease);
		} catch (RemoteException e) {
			log.error("Remote Exception: " + e.getMessage());
		} catch (TransactionException e) {
			log.error("Transaction Exception: " + e.getMessage());
		}
	}

	public Comment getEmptyTemplate() {
		Comment temp = new Comment();
		temp.setAuthor(null);
		temp.setDate(null);
		temp.setText(null);
		return temp;
	}

}
