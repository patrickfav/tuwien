package dst2.ejb.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Future;

import javax.ejb.Remote;

import dst2.model.AuditRecord;
import dst2.model.PriceStep;

@Remote
public interface GeneralManagement {
	public PriceStep storePriceStep(int numberOfHistoricalJobs, BigDecimal price);
	public Future<String> createBill(String usrName);
	public StringBuffer getAllAuditRecords();
}
