package server;
import java.util.HashMap;

import dataModel.Loan;

/**
 * This class provides methods to create and delete LoanController
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class LoanControllerManager {
	// camps
	private HashMap<String, LoanController> controller;
	private static LoanControllerManager manager = null;

	private LoanControllerManager() {
		super();
		this.controller = new HashMap<String, LoanController>();
	}

	/**
	 * Add a new LoanController that control the given loan
	 * 
	 * @param loan
	 *            the loan to control
	 */
	public void addController(Loan loan) {
		this.controller.put(loan.getBook().getIsbn(), new LoanController(loan));
	}

	/**
	 * Remove the LoanController that control the given loan, if exists
	 * 
	 * @param loan
	 *            the loan to stop control
	 */
	public void removeController(Loan loan) {
		LoanController loanController = this.controller.get(loan.getBook().getIsbn());
		if (loanController != null) {
			loanController.interrupt();
			this.controller.remove(loan.getBook().getIsbn());
		}
	}

	/**
	 * Returns the loan control manager
	 * 
	 * @return the LoanControlManager object
	 */
	public static LoanControllerManager instance() {
		if (manager == null) {
			manager = new LoanControllerManager();
		}
		return manager;
	}
}
