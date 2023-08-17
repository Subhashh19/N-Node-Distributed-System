import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Vector;

public class VNodeC extends UnicastRemoteObject implements VectorInterface, Runnable {

	private static final long serialVersionUID = -3231431858591062074L;

	private static int PORT = 8005;

	static Vector<Integer> vectorClock = new Vector<Integer>(3);

	protected VNodeC() throws RemoteException {
		super();

	}

	@Override
	public void smessage(Vector<Integer> currentVector) throws RemoteException {
		System.out.println("NodeC vector clock before Receving Message.");
		System.out.println(vectorClock);
		for (int i = 0; i < currentVector.size(); i++) {
			int maxTimer = Math.max(currentVector.get(i), vectorClock.get(i));
			vectorClock.set(i, maxTimer);
		}
		vectorClock.set(2, vectorClock.get(2) + 1); // Incrementing NodeC Timer
		System.out.println("NodeC vector clock after Receving Message.");
		System.out.println(vectorClock);
	}

	public static void main(String[] args) {

		try {
			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.bind("vector", new VNodeC());
			System.err.println("Node C is Started Sucessfully");
			synchronizeVector();
			Thread thread = new Thread(new VNodeC());
			thread.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Synchronizing the Timers from the Berkly

	private static void synchronizeVector() {
		try {
			ClientServerInterface nodeA = (ClientServerInterface) Naming.lookup("rmi://localhost:8000/berkly");
			ClientServerInterface nodeB = (ClientServerInterface) Naming.lookup("rmi://localhost:8001/berkly");
			ClientServerInterface nodeC = (ClientServerInterface) Naming.lookup("rmi://localhost:8002/berkly");
			int timerA = nodeA.getCounter();
			int timerB = nodeB.getCounter();
			int timerC = nodeC.getCounter();
			vectorClock.add(timerA);
			vectorClock.add(timerB);
			vectorClock.add(timerC);
			System.out.println("Synchronized Vector");
			System.out.println(vectorClock);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Select option : send message to Node A|B|C");
				Scanner sc = new Scanner(System.in);
				String node = sc.next();
				if (node.equals("C")) {
					// If Internal Event occurs
					smessage(vectorClock);
				} else if (node.equals("A")) { // Send Message to Node A
					System.out.println("NodeC vector clock before Sending Message.");
					System.out.println(vectorClock);
					vectorClock.set(2, vectorClock.get(2) + 1); // Incrementing NodeC Timer
					VectorInterface nodeA = (VectorInterface) Naming.lookup("rmi://localhost:8003/vector");
					nodeA.smessage(vectorClock);
					System.out.println("NodeC vector clock after Sending Message.");
					System.out.println(vectorClock);

				} else { // Send Message to NodeB
					System.out.println("NodeC vector clock before Sending Message.");
					System.out.println(vectorClock);
					vectorClock.set(2, vectorClock.get(2) + 1); // Incrementing NodeC Timer
					VectorInterface nodeC = (VectorInterface) Naming.lookup("rmi://localhost:8004/vector");
					nodeC.smessage(vectorClock);
					System.out.println("NodeC vector clock after Sending Message.");
					System.out.println(vectorClock);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
