package server;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import dataModel.Booking;
import dataModel.Loan;
import dataModel.Login;
import dataModel.User;
import database.DbManager;
import utils.Operation;

/**
 * this class i a definition of thread that control a loan
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class LoanController extends Thread {
	// camps
	private Loan loanToControl;

	/**
	 * Create a new LoanToControl onbject that will control the given loan
	 * 
	 * @param loanToControl
	 *            the loan to control
	 */
	public LoanController(Loan loanToControl) {
		super();
		this.loanToControl = loanToControl;
		start();
	}

	/**
	 * Start to control the loan. If after 7 days the book is still present in the
	 * library delete the user booking and inform the new first user of the booking
	 * queue, if exists
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(604800000); // 604800000 ms = 7 days
			boolean hasRitireBook = (boolean) DbManager.instance().selectData("Control loan", this.loanToControl);
			if (!hasRitireBook) {
				Booking booking = new Booking(this.loanToControl.getBook(), this.loanToControl.getUser(),
						this.loanToControl.getLoanDate());
				DbManager.instance().deleteData(Operation.BOOKING_DELETE, booking);
				String[] firstUserQueue = (String[]) DbManager.instance().selectData("Select user booking book",
						this.loanToControl.getBook());
				if (firstUserQueue != null) {
					GregorianCalendar date = new GregorianCalendar();
					String[] dateData = firstUserQueue[1].split("/");
					date.set(Integer.parseInt(dateData[2]), Integer.parseInt(dateData[1]),
							Integer.parseInt(dateData[0]));
					User user = (User) DbManager.instance().selectData(Operation.LOGIN_FROM_LIBRARIAN,
							new Login(firstUserQueue[4], null, null));
					if (user != null) {
						LoanControllerManager.instance().addController(
								new Loan(this.loanToControl.getBook(), user, date, new GregorianCalendar(), false));
						new EmailSender("Book returned", firstUserQueue[0], firstUserQueue[1], firstUserQueue[2],
								firstUserQueue[3]);
					}
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Unable to control loan! Exception!");
		} catch (InterruptedException e) {
			System.out.println("LoanController interrupted");
		}
	}
}
