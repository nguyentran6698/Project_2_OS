import java.util.concurrent.Semaphore;

public class DMVInfoDesk implements Runnable {

	public void run() {

		while(true) {

			// Signal that InfoDesk ready for new customer
			signal(Main.infoDeskReady);

			// Wait for customer to enter InfoDesk
			wait(Main.customerEnterInfoDesk);

			// assigned Ticket number for customer
			signal(Main.assignedTicketNumber);

			// customer id has been assigned
			wait(Main.numberHasBeenAssigned);

			// wait for customer to leave
			wait(Main.customerLeftInfoDesk);


		}
	}
	
	// wait ~ Semaphore.acquire()
	private void wait(Semaphore s) {
		try {
			s.acquire();
		}catch( InterruptedException e) {}
	}
	
	// signal ~ Semaphore.release()
	private void signal(Semaphore s) {
		try {
			s.release();
		}catch( Exception e) {}
	}
}
