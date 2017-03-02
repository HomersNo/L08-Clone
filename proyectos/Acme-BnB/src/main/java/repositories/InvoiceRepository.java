package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	
	@Query("select count(r.invoice) from Request r group by r.tenant")
	Double[] findAvgMinMaxPerTenant();
	
	@Query("select sum(i.totalAmount) from Invoice i")
	Double findTotalMoneyDue();

}
