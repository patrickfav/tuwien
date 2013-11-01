package findbugs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import at.ac.tuwien.ifs.bpse.dao.IStudentDAO;
import at.ac.tuwien.ifs.bpse.domain.Student;
import at.ac.tuwien.ifs.bpse.domain.StudentExam;

/**
 * Implementation of the Student Data Access Object for JDBC. This class makes
 * heavy use of the Spring Framework's facilities and provides access to the
 * data stored in the database aswell as defines how this data is mapped from
 * the application objects to the database tables.<br>
 * Also see the Bean-Config file
 * ({@value at.ac.tuwien.ifs.bpse.basis.helper.Constants#SPRINGBEANS})
 * for configuration.
 *
 * @author The SE-Team
 * @version 1.0
 * @see StudentDAO
 */
public class JdbcObjectStudentDAO implements IStudentDAO {

	/**
	 * Retrieves the logger for this class.
	 */
	private static Log log = LogFactory.getLog(JdbcObjectStudentDAO.class);

	/**
	 * SQL Datasource. In order to work with data from a database, we need to
	 * obtain a connection to the database. A Datasource is part of the JDBC
	 * specification and can be seen as a generalized connection factory.
	 */
	private DataSource dataSource = null;

	/**
	 * Transaction Manager. For encapsulating insert and updates in transaction.
	 */
	private PlatformTransactionManager transactionManager = null;

	/**
	 * SQL Query Strings.
	 */
	private String sql_getAllStudents = "";
	private String sql_getStudent = "";
	private String sql_insertStudent = "";
	private String sql_getInsertId = "";
	private String sql_updateStudent = "";
	private String sql_deleteStudent = "";

	/**
	 * Query Objects.
	 */
	private Query_GetStudent query_getStudent;
	private Query_InsertStudent query_insertStudent;
	private Query_UpdateStudent query_updateStudent;
	private Query_DeleteStudent query_deleteStudent;
	private Query_GetStudentID query_getStudentId;
	private Query_GetAllStudentsOrderMatnr query_getAllStudentsOrderMatnr;
	private Query_GetAllStudentsOrderNachname query_getAllStudentsOrderNachname;



	/** ******************************************************************* */
	/** ******************************************************************* */
	/* INNER CLASSES TO DEFINE SQL STATEMENTS AND MAPPINGS */
	/** ******************************************************************* */
	/** ******************************************************************* */

	/**
	 * Defines the base class used for all other queries. This is a private
	 * inner class, as this is used only in this data access object
	 */
	private class BaseStudentsQuery extends MappingSqlQuery {
		/**
		 * The Base query is extended by all other queries
		 *
		 * @param ds SQL Datasource
		 * @see #dataSource
		 * @param sql  The SQL search String
		 */
		public BaseStudentsQuery(DataSource ds, String sql) {
			super(ds, sql);
		}

		@Override
        protected Student mapRow(ResultSet rs, int rowNumber)
				throws SQLException {
			Student student = new Student();
			student.setId(rs.getInt("id"));
			student.setMatnr(rs.getString("matnr"));
			student.setFirstname(rs.getString("vorname"));
			student.setLastname(rs.getString("nachname"));
			student.setEmail(rs.getString("email"));
			return student;
		}
	}

	/**
	 * Retrieves all students in the DB ordered by their Matrikel number. This
	 * is a private inner class, as this is used only in this data access
	 * object.
	 */
	private class Query_GetAllStudentsOrderMatnr extends BaseStudentsQuery {
		/**
		 * Fetches all students from table
		 *
		 * @param ds SQL Datasource
		 *
		 * @see #dataSource
		 */
		public Query_GetAllStudentsOrderMatnr(DataSource ds) {
			super(ds, sql_getAllStudents + " order by matnr");
			compile();
		}
	}

	/**
	 * Retrieves all students in the DB ordered by their last name. This is a
	 * private inner class, as this is used only in this data access object.
	 */
	private class Query_GetAllStudentsOrderNachname extends BaseStudentsQuery {
		/**
		 * Fetches all students from table.
		 *
		 * @param ds  SQL Datasource
		 * @see #dataSource
		 */
		public Query_GetAllStudentsOrderNachname(DataSource ds) {
			super(ds, sql_getAllStudents + " order by nachname");
			compile();
		}
	}

	/**
	 * Retrieves one student from the DB. This is a private inner class, as this
	 * is used only in this data access object
	 */
	private class Query_GetStudent extends BaseStudentsQuery {
		/**
		 * Fetches only ONE student from database.
		 *
		 * @param ds  SQL Datasource
		 * @see #dataSource
		 */
		public Query_GetStudent(DataSource ds) {
			super(ds, sql_getStudent);
			super.declareParameter(new SqlParameter("id", Types.INTEGER));
			compile();
		}
	}

	/**
	 * Adds one student to database. This is a private inner class, as this is
	 * used only in this data access object.
     * SqlUpdate is a helper class provided by the Spring-Framework
	 */
	private class Query_InsertStudent extends SqlUpdate {
		/**
		 * Retrieves the ID of the student added to database.
		 *
		 * @param ds  SQL Datasource
		 * @see #dataSource
		 */
		public Query_InsertStudent(DataSource ds) {
			super.setDataSource(ds);
			setSql(sql_insertStudent);
			declareParameter(new SqlParameter("matnr", Types.VARCHAR));
			declareParameter(new SqlParameter("firstname", Types.VARCHAR));
			declareParameter(new SqlParameter("lastname", Types.VARCHAR));
			declareParameter(new SqlParameter("email", Types.VARCHAR));
		}
	}

	/**
	 * Updates one student in the database. This is a private inner class, as
	 * this is used only in this data access object.
     * SqlUpdate is a helper class provided by the Spring-Framework
	 */
	private class Query_UpdateStudent extends SqlUpdate {
		/**
		 * Create and initialises the SQL-Query for updating a student.
		 *
		 * @param ds  SQL Datasource
		 * @see #dataSource
		 */
		public Query_UpdateStudent(DataSource ds) {
			super.setDataSource(ds);
			setSql(sql_updateStudent);
			declareParameter(new SqlParameter("id", Types.INTEGER));
			declareParameter(new SqlParameter("matnr", Types.VARCHAR));
			declareParameter(new SqlParameter("firstname", Types.VARCHAR));
			declareParameter(new SqlParameter("lastname", Types.VARCHAR));
			declareParameter(new SqlParameter("email", Types.VARCHAR));
		}
	}

	/**
	 * Deletes one stundent from the database. This is a private inner class, as
	 * this is used only in this data access object.
	 */
	private class Query_DeleteStudent extends SqlUpdate {
		/**
		 * Creates and initializes the SQL-Query for deleting a student.
		 *
		 * @param ds  SQL Datasource
		 * @see #dataSource
		 */
		public Query_DeleteStudent(DataSource ds) {
			super.setDataSource(ds);
			setSql(sql_deleteStudent);
			declareParameter(new SqlParameter("id", Types.INTEGER));
		}
	}

	/**
	 * Retrieves the ID of the student added to database. This is a private
	 * inner class, as this is used only in this data access object.
	 */
	private class Query_GetStudentID extends MappingSqlQuery {
		/**
		 * Retrieves the ID of the student added to database.
		 *
		 * @param ds
		 *            SQL Datasource
		 * @see #dataSource
		 */
		public Query_GetStudentID(DataSource ds) {
			super(ds, sql_getInsertId);
			compile();
		}

		@Override
        protected Object mapRow(ResultSet rs, int rowNumber)
				throws SQLException {
			// "@p0" is the row name hsqldb creates for the one value returning
			// the ID
			Integer id = new Integer(rs.getInt("@p0"));
			return id;
		}
	}

	/** ******************************************************************* */
	/** ******************************************************************* */
	/* C O N S T R U C T O R */
	/** ******************************************************************* */
	/** ******************************************************************* */

	public JdbcObjectStudentDAO() {
		super();
	}

	/**
	 * The Initialise Method must be called after all bean values are set,
	 * particularly the datasource and the transaction manager. This is actually
	 * performed by the Spring Framework, which sets first of all, all Java Bean
	 * Properties and eventually calls this init method (see bean definition in
	 * beans.xml configuration file)
	 */
	public void init() {
		log.info("Initialise StudentDAO");
		query_getStudent = new Query_GetStudent(dataSource);
		query_insertStudent = new Query_InsertStudent(dataSource);
		query_getStudentId = new Query_GetStudentID(dataSource);
		query_updateStudent = new Query_UpdateStudent(dataSource);
		query_deleteStudent = new Query_DeleteStudent(dataSource);
		query_getAllStudentsOrderMatnr = new Query_GetAllStudentsOrderMatnr(
				dataSource);
		query_getAllStudentsOrderNachname = new Query_GetAllStudentsOrderNachname(
				dataSource);
	}

	/**
	 * The Destroy Method is called by the Spring Framework to end the lifecycle
	 * of this bean, but <b>only</b> when the bean is created as singleton.
	 * Check the bean definition in beans.xml configuration file for details.
	 */
	public void destroy() {
		log.info("Destroy StudentDAO");
	}

	/** ******************************************************************* */
	/** ******************************************************************* */
	/*
	 * BEAN SETTERS FOR DEPENDENCY INJECTION
	 *
	 * Dependency Injection is a design pattern in which the responsibility for
	 * object creation and object linking is removed from the objects themselves
	 * and transferred to a factory object. It is a way to achieve loose
	 * coupling between objects and results in highly testable objects
	 * (controlled unit tests).
	 *
	 * Factory Object: (Design Pattern) is an object for creating other objects.
	 * Typically a factory has a method for every kind of object it is capable
	 * of creating. these methods optionally accept parameters defining how the
	 * object is created and then return the created object.
	 */
	/** ******************************************************************* */
	/** ******************************************************************* */

	/**
	 * Sets the SQL String to get all students.
	 *
	 * @param sql_getAllStudents
	 *            SQL Statement as String
	 */
	public void setSql_getAllStudents(String sql_getAllStudents) {
		this.sql_getAllStudents = sql_getAllStudents;
	}

	/**
	 * Sets the SQL String to get one student with one SQL parameter.
	 *
	 * @param sql_getStudent
	 *            SQL Statement as String
	 */
	public void setSql_getStudent(String sql_getStudent) {
		this.sql_getStudent = sql_getStudent;
	}

	/**
	 * Sets the SQL String to insert one student into the database.
	 *
	 * @param sql_insertStudent
	 *            SQL Statement as String
	 */
	public void setSql_insertStudent(String sql_insertStudent) {
		this.sql_insertStudent = sql_insertStudent;
	}

	/**
	 * Sets the SQL String to retrieve the ID of the last executed SQL
	 * Statement.
	 *
	 * @param sql_getInsertId
	 *            SQL Statement as String
	 */
	public void setSql_getInsertId(String sql_getInsertId) {
		this.sql_getInsertId = sql_getInsertId;
	}

	/**
	 * Sets the SQL String to update a student.
	 *
	 * @param sql_updateStudent
	 *            SQL Statement as String
	 */
	public void setSql_updateStudent(String sql_updateStudent) {
		this.sql_updateStudent = sql_updateStudent;
	}

	/**
	 * Sets the SQL String to delete a student.
	 *
	 * @param sql_deleteStudent
	 *            SQL Statement as String
	 */
	public void setSql_deleteStudent(String sql_deleteStudent) {
		this.sql_deleteStudent = sql_deleteStudent;
	}

	/**
	 * Sets the Datasource to connect to database.
	 *
	 * @param dataSource
	 *            SQL Datasource
	 * @see #dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Sets the transaction manager.
	 *
	 * @param transactionManager
	 *            central interface in Spring's transaction infrastructure
	 * @see #transactionManager
	 */
	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/** ******************************************************************* */
	/** ******************************************************************* */
	/*
	 * DAO METHODS
	 *
	 * A Data Access Object (DAO) is a component which provides a common
	 * interface between the application and one or more data storage devices,
	 * such as a database or a file. The advantage of using data access objects
	 * is that any business object (which contains application or operation
	 * specific details) does not require direct knowledge of the final
	 * destination for the information it manipulates. As a result, _if_ it is
	 * necessary to change where or how the data is stored, these modifications
	 * can be made without needing to change the main application.
	 */
	/** ******************************************************************* */
	/** ******************************************************************* */

	public Student getStudent(int id) {
		log.info("Get Student ID = " + id);
		List<?> students = query_getStudent.execute(id);
		if (students.size() == 1) {
			Student s = (Student) students.get(0);
			log.debug("Returning Student \"" + s.getFirstname() + " "
					+ s.getLastname() + "\"");
			return s;
		} else {
			log.debug("No Student data");
			return null;
		}
	}

	/**
	 * Inserts one Student into the database. This method uses a transaction
	 * manager for performing two queries in one transaction:
	 * <ol>
	 * <li> the insert statement is performed adding the dataset to the database
	 * (first query)</li>
	 * <li> the database then automatically generates a unique ID for this
	 * dataset, </li>
	 * <li> the second query asks the database <i>which</i> id was used for
	 * this particular dataset</li>
	 * <li> this ID then is set in the student bean and returned by the function</li>
	 * </ol>
	 *
	 * @param student
	 *            Student object holding data of one student
	 * @return unique id generated by database assigned to the newly created
	 *         Student
	 */
	public Student saveStudent(Student student) {
		log.info("Add Student Name = " + student.getFirstname() + " "
				+ student.getLastname());
		log.debug("Initialise SQL Parameters");
		final Object[] param = new Object[] { student.getMatnr(),
				student.getFirstname(), student.getLastname(), student.getEmail() };
		log.debug("Initialise Transaction Manager");
		TransactionTemplate tt = new TransactionTemplate(transactionManager);
		Object result = tt.execute(new TransactionCallback() {
			public Object doInTransaction(TransactionStatus status) {
				// The transaction is run from here
				log.debug("Start Transaction");
				query_insertStudent.update(param);

				/*
				 * activate the following error line to create an Error which
				 * terminates this method. One will see, that the complete
				 * transaction is rolled back, hence the insert statement above
				 * is not executed, alternatively the second rollback statement
				 * can be activated with the same result which in that case is a
				 * manual rollback of the transaction
				 */

				// if (true) throw new Error("Test Exception");
				// or
				// status.setRollbackOnly();
				/*
				 * result from query is a list, actually containing only one row
				 * and one column
				 */
				List<?> results = query_getStudentId.execute();
				Integer id = (Integer) results.get(0);
				log.debug("End Transaction");
				return id;
				/*
				 * and the transaction ends here! if no error occurs the
				 * transaction is committed by Spring otherwise it is rolled
				 * back
				 */
			}
		});
		Integer id = (Integer) result;
		student.setId(id);
		log.info("Return ID from inserted dataset = " + id);
		return id!=0 ? student : null;
	}

	public Student updateStudent(Student student) {
		return updateStudent(student, student.getId());
	}

	public Student updateStudent(Student student, long id) {
		log.info("Update Student, ID = " + student.getId() + " new ID = "
				+ id);
		log.debug("Initialise SQL Parameters");
		final Object[] param = new Object[] { student.getMatnr(),
				student.getFirstname(), student.getLastname(),
				student.getEmail(), id };
		log.debug("Execute Update");
		if (query_updateStudent.update(param) == 1) {
			log.debug("Update Successfull");
			return student;
		}
		log.error("Update for Student ID = " + id + " failed.");
		return null;
	}

	/**
	 * Retrieves all students from the database. <br>
	 * <b>Warning:</b> this type of DAO method would not be used in a real-
	 * world application because there may be thousands of students in the
	 * database and this method would retrieve them all. <br>
	 * This is usually not needed: it will generate a huge load on the database
	 * and also require enormous amounts of memory. Morover, there is hardly an
	 * application conceivable that needs more than a few dozen datasets at any
	 * one time.
	 */
	@SuppressWarnings("unchecked")
	public List<Student> getStudents(String order) {
		log.info("Get all Students order = " + order);
		List<Student> students = new ArrayList<Student>();
		if (order.equals("Matrikelnummer")) {
			students = query_getAllStudentsOrderMatnr.execute();
		} else if(order.equals("Nachname")) {
			students = query_getAllStudentsOrderNachname.execute();
		}
		log.debug("Student List contains " + students.size() + " students");
		return students;
	}

	public boolean deleteStudent(int id) {
		log.info("Delete Student ID = " + id);
		log.debug("Initialize SQL Parameters");
		final Object[] param = new Object[] { id };
		log.debug("Executing SQL");
		if (query_deleteStudent.update(param) == 1) {
			log.debug("Deleting successfull");
			return true;
		}
		log.error("Deleting Student ID = " + id + " failed");
		return false;
	}

	public Student getStudentByMatrNr(String arg0) {
		// TODO Auto-generated method stub
		// TODO Implement this method
		return null;
	}

    public List<StudentExam> getRegisteredExams(Student student) {
        // TODO Auto-generated method stub
        return null;
    }
}
