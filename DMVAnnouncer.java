import java.util.concurrent.Semaphore;

public class DMVAnnouncer implements Runnable  {
	public void run() {
		while(true) {
			// check if the agent line need to fill up
			wait(Main.enterWaitingRoom);
			// check for agent line size
			wait(Main.agentLineCapacity);
			// announce customer
			signal(Main.announceCurrentCustomer);

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
