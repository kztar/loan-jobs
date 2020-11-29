package tech.kztar.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

@Component
public class LoanAccountingDaoImpl implements LoanAccountingDao {

	@PersistenceContext
	EntityManager entityManager;

	/**
	 * Find phone numbers of the borrowers associated with the given set of loanAccountIds.<br>
	 * Note: By default, this method DOES NOT fetch loan accounts with CLOSED status
	 */
	@Override
	public Map<UUID, String> findPhoneNumbersByLoanAccountIds(Iterable<UUID> loanAccountIds) {
		TypedQuery<Tuple> query = entityManager.createQuery("SELECT la.id, b.phoneNo FROM LoanAccount la, Borrower b"
				+ " WHERE la.status != 'CLOSED' AND la.id IN (?1) AND la.borrowerId = b.id", Tuple.class);
		List<Tuple> resultList = query.setParameter(1, loanAccountIds).getResultList();

		Map<UUID, String> phoneNoByLoanAccountIdMap = new HashMap<UUID, String>();
		for (Tuple resTup : resultList) {
			phoneNoByLoanAccountIdMap.put(resTup.get(0, UUID.class), resTup.get(1, String.class));
		}
		return phoneNoByLoanAccountIdMap;
	}

}
