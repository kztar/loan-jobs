package tech.kztar.dao;

import java.util.Map;
import java.util.UUID;

public interface LoanAccountingDao {

	/**
	 * 
	 * @param loanAccountIds
	 * @return a {@link Map} object with key as loanAccountId and value as phone
	 *         numbers of the associated borrowers
	 */
	Map<UUID, String> findPhoneNumbersByLoanAccountIds(Iterable<UUID> loanAccountIds);

}
