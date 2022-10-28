import java.util.concurrent.Semaphore;

public class DMVCustomer implements Runnable {
	int customer;
	int agentID;
	public DMVCustomer(int id){
		this.customer = id;
	}
	public void run() {
				// wait for info desk ready
				wait(Main.infoDeskReady);

				// customer enter info desk
				signal(Main.customerEnterInfoDesk);
				System.out.println("Customer " + customer + " created, enters DMV");

				// Customer is assigned unique number
				wait(Main.assignedTicketNumber);
				Main.customerTicket = customer + 1;
				signal(Main.numberHasBeenAssigned);
				System.out.println("Customer " + customer + " gets number " + Main.customerTicket +" , enters waiting room");

				// leave the infodesk to move to waiting room
				signal(Main.customerLeftInfoDesk);
				// enter waiting room
				signal(Main.enterWaitingRoom);
				// wait to be call by announcer
				wait(Main.announceCurrentCustomer);
				System.out.println("Announcer calls number " + customer);
				// move to the agent line
				System.out.println("Customer " + customer +  " moves to agent line");
				wait(Main.mutex);
				Main.agentLine.add(customer);
				signal(Main.mutex);
				// Enter agent line
				signal(Main.customerEnterAgentLine);
				// check if agent line full
				signal(Main.agentLineCapacity);

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
