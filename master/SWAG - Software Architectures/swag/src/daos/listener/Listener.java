package daos.listener;

import interfaces.DAOListener;

public class Listener implements DAOListener {

	@Override
	public void beforeInsert(Class<?> c) {
		System.out.println("Listener: Before Insert of " + c.getSimpleName());

	}

	@Override
	public void afterInsert(Class<?> c) {
		System.out.println("Listener: After Insert of " + c.getSimpleName());

	}

	@Override
	public void beforeUpdate(Class<?> c) {
		System.out.println("Listener: Before Update of " + c.getSimpleName());

	}

	@Override
	public void afterUpdate(Class<?> c) {
		System.out.println("Listener: After Update of " + c.getSimpleName());

	}

	@Override
	public void beforeDelete(Class<?> c) {
		System.out.println("Listener: Before Delete of " + c.getSimpleName());

	}

	@Override
	public void afterDelete(Class<?> c) {
		System.out.println("Listener: After Delete of " + c.getSimpleName());

	}

	@Override
	public void beforeFind(Class<?> c, String search) {
		System.out.println("Listener: Before Find '" + search + "' of "
				+ c.getSimpleName());

	}

	@Override
	public void afterFind(Class<?> c, String search) {
		System.out.println("Listener: After Find '" + search + "' of "
				+ c.getSimpleName());

	}

}
