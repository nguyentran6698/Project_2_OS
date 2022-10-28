import java.util.*;
import java.util.concurrent.Semaphore;


public class Main {
	
	// customer stuff
	static int N_CUSTOMERS;
	static Semaphore customerLeftInfoDesk;
	static Semaphore numberHasBeenAssigned;
	static Semaphore enterWaitingRoom;

	// announcer ~ will try to keep agent line @ 4 people
	static Semaphore agentLineGood;
	static Semaphore agentLineCapacity;
	static Semaphore announceCurrentCustomer;

	// agent ~ ask customer to take eye exam / photo / give temp license
	static int N_AGENTS;
	static Semaphore customerEnterAgentLine;
	static LinkedList<Integer> agentLine;
	
    // general purpose
    static Semaphore customerEnterInfoDesk;
	static Semaphore infoDeskReady;
	static Semaphore mutex;
	static Semaphore assignedTicketNumber;
	static int customerTicket;


    public static void main(String[] args) {

//		if (args.length != 1 ) {
//			System.out.println("Error: Incorrect Arguments. Please enter the number of customers.");
//			System.exit(0);
//		}

//		N_CUSTOMERS = Integer.parseInt(args[0]);
		N_CUSTOMERS = 6;
		N_AGENTS = 2;
		// Initialize Semaphore
		customerLeftInfoDesk = new Semaphore(0);
		numberHasBeenAssigned = new Semaphore(0);
		enterWaitingRoom = new Semaphore(0);
		assignedTicketNumber = new Semaphore(0);
		agentLineGood = new Semaphore(0);
		agentLineCapacity = new Semaphore(4);
		announceCurrentCustomer = new Semaphore(0);
		customerEnterAgentLine = new Semaphore(0);
		agentLine  = new LinkedList<>();
		customerEnterInfoDesk = new Semaphore(0);
		infoDeskReady = new Semaphore(0);
		mutex = new Semaphore(1);



		// Information desk created
    	Thread infoDeskThread = new Thread(new DMVInfoDesk());
		System.out.println("Information desk created");
		// initialize info desk
		infoDeskThread.start();

		// initialize announcer
		Thread announcerThread = new Thread(new DMVAnnouncer());
		System.out.println("Announcer created");
		announcerThread.start();

		Thread customerThreads[] = new Thread[N_CUSTOMERS];
    	for(int i=0; i < N_CUSTOMERS; i++) {
    		customerThreads[i] = new Thread(new DMVCustomer(i));
    		customerThreads[i].start();
    	}

		Thread agent[] = new Thread[N_AGENTS];
    	for(int i=0; i<N_AGENTS; i++) {
			agent[i] = new Thread( new DMVAgent(i));
			agent[i].start();
    	}

    	// ~ all of these will complete at some point,
    	// and eventually we will execute (below),
    	// joining all threads
    	try {
    		for(int i=0; i<N_CUSTOMERS; i++) {
    			customerThreads[i].join();
    		}
    		// end of simulation
    	} catch(InterruptedException e) {}

		System.exit(0);
    }
}
