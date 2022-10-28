
import java.util.concurrent.Semaphore;

public class DMVAgent implements Runnable {
	int agentID;
	int customer;
	public DMVAgent(int id){
		this.agentID = id;
		System.out.println("Agent " + id + " created");
	}
	public void run() {
		while(true) {
			
			// wait for customer in agent line to be ready
			wait(Main.customerEnterAgentLine);
			// take customer @ front of the line & decrease agent line size (currently serving this custmer)
			wait(Main.mutex);
			customer = Main.agentLine.poll();
			signal(Main.mutex);
			System.out.println("Agent " + agentID  + " is serving customer " + customer);
			System.out.println("Agent " + agentID + " gives license to customer " + customer);
			System.out.println("Customer " +customer +  " gets license and departs");
			System.out.println("Customer " +customer + " was joined");
			// customer is good to go, ready for next customer
			signal(Main.enterWaitingRoom);

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
