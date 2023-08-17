import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Vector;

public class VNodeB extends UnicastRemoteObject implements VectorInterface, Runnable {

	private static final long serialVersionUID = 2597576699482300257L;

	private static int PORT = 8004;

	static Vector<Integer> vectorClock = new Vector<Integer>(3);

	protected VNodeB() throws RemoteException {
		super();

	}

	@Override
	public void smessage(Vector<Integer> currentVector) throws RemoteException {
		System.out.println("NodeB vector clock before Receving Message.");
		System.out.println(vectorClock);
		for (int i = 0; i < currentVector.size(); i++) {
			int maxTimer = Math.max(currentVector.get(i), vectorClock.get(i));
			vectorClock.set(i, maxTimer);
		}
		vectorClock.set(1, vectorClock.get(1) + 1); // NodeB++
		System.out.println("NodeB vector clock after Receiving Message.");
		System.out.println(vectorClock);
	}

	public static void main(String[] args) {

		try {
			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.bind("vector", new VNodeB());
			System.err.println("Node B is Established Sucessfully");
			synchronizeVector();
			Thread thread = new Thread(new VNodeB());
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


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
				System.out.println("Select option : send message to Node A or B or C");
				Scanner sc = new Scanner(System.in);
				String node = sc.next();
				if (node.equals("B")) {
					// If Internal Event occurs
					smessage(vectorClock);
				} else if (node.equals("A")) { // Send Message to Node A
					System.out.println("NodeB vector clock before Sending Message.");
					System.out.println(vectorClock);
					vectorClock.set(1, vectorClock.get(1) + 1); // Incrementing NodeB Timer
					VectorInterface nodeA = (VectorInterface) Naming.lookup("rmi://localhost:8003/vector");
					nodeA.smessage(vectorClock);
					System.out.println("NodeB vector clock after Sending Message.");
					System.out.println(vectorClock);

				} else { // Send Message to NodeC
					System.out.println("NodeB vector clock before Sending Message.");
					System.out.println(vectorClock);
					vectorClock.set(1, vectorClock.get(1) + 1); // Incrementing NodeA Timer
					VectorInterface nodeC = (VectorInterface) Naming.lookup("rmi://localhost:8005/vector");
					nodeC.smessage(vectorClock);
					System.out.println("NodeB vector clock after Sending Message.");
					System.out.println(vectorClock);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
