package db.interfaces;

import javax.ejb.Local;

import entities.constraint.Constraint;

@Local
public interface IConstraintDAO extends IGenericDAO<Constraint, Long>{

}
