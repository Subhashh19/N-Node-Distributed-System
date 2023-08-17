import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Vector;

public class VNodeA extends UnicastRemoteObject implements VectorInterface, Runnable {

	private static final long serialVersionUID = 4852149851754585146L;

	private static int PORT = 8003;

	static Vector<Integer> vectorClock = new Vector<Integer>(3);

	protected VNodeA() throws RemoteException {
		super();

	}

	@Override
	public void smessage(Vector<Integer> currentVector) throws RemoteException {
		System.out.println("NodeA vector clock before Receiving Message.");
		System.out.println(vectorClock);
		for(int i=0;i<currentVector.size();i++) {
			int maxTimer = Math.max(currentVector.get(i), vectorClock.get(i));
			vectorClock.set(i, maxTimer);
		}
		vectorClock.set(0, vectorClock.get(0) + 1); // Incrementing NodeA Timer
		System.out.println("NodeA vector clock after Receiving Message.");
		System.out.println(vectorClock);
	}

	public static void main(String[] args) {

		try {
			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.bind("vector", new VNodeA());
			System.err.println("Node A is Established Sucessfully");
			synchronizeVector();
			Thread thread = new Thread(new VNodeA());
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
				if (node.equals("A")) {
					// If Internal Event occurs
					smessage(vectorClock);
				} else if (node.equals("B")) { // Send Message to Node B
					System.out.println("NodeA vector clock before Sending Message.");
					System.out.println(vectorClock);
					vectorClock.set(0, vectorClock.get(0) + 1); // Incrementing NodeA Timer
					VectorInterface nodeB = (VectorInterface) Naming.lookup("rmi://localhost:8004/vector");
					nodeB.smessage(vectorClock);
					System.out.println("NodeA vector clock after Sending Message.");
					System.out.println(vectorClock);

				} else { // Send Message to NodeC
					System.out.println("NodeA vector clock before Sending Message.");
					System.out.println(vectorClock);
					vectorClock.set(0, vectorClock.get(0) + 1); // Incrementing NodeA Timer
					VectorInterface nodeC = (VectorInterface) Naming.lookup("rmi://localhost:8005/vector");
					nodeC.smessage(vectorClock);
					System.out.println("NodeA vector clock after Sending Message.");
					System.out.println(vectorClock);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
