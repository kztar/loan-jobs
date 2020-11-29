package tech.kztar.scheduling;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tech.kztar.dao.LoanAccountingDao;
import tech.kztar.persistence.model.Amortization;
import tech.kztar.persistence.repo.AmortizationRepository;

@Component
public class CustomerMessagingJobs {

	@Autowired
	AmortizationRepository amortRepo;

	@Autowired
	LoanAccountingDao loanAccountingDao;

	@Scheduled(fixedRate = 100000)
	public void sendSMSAlertsForInstallmentDues() {
		Date startDate = DateUtils.truncate(new Date(), Calendar.DATE);
		Date endDate = DateUtils.addDays(startDate, 7);

		List<Amortization> amortList = amortRepo.findAllByPymtDateBetween(startDate, endDate);
		Map<UUID, Amortization> amortMap = getAmortizationByLoanAccountIdMap(amortList);

		Map<UUID, String> phoneNoByLoanAccountIdMap = loanAccountingDao.findPhoneNumbersByLoanAccountIds(amortMap.keySet());
		for (Map.Entry<UUID, String> entry : phoneNoByLoanAccountIdMap.entrySet()) {
			UUID loanAccountId = entry.getKey();
			String phoneNo = entry.getValue();

			Amortization amort = amortMap.get(loanAccountId);
			System.out.println("Phone no.: " + phoneNo + ", Payment date: " + amort.getPymtDate() + ", Installment amount: "
					+ amort.getInstallment());
		}
	}

	private Map<UUID, Amortization> getAmortizationByLoanAccountIdMap(List<Amortization> amortList) {
		Map<UUID, Amortization> amortMap = new HashMap<UUID, Amortization>();
		for (Amortization amort : amortList) {
			amortMap.put(amort.getLoanAccountId(), amort);
		}
		return amortMap;
	}

}
