import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Scanner;

public class NodeC extends UnicastRemoteObject implements ClientServerInterface, Runnable {

	private static final long serialVersionUID = 8962963187518362883L;

	private static int PORT = 8002;

	static Random rand = new Random();
	private static int nodeCCounter = rand.nextInt(60);

	protected NodeC() throws RemoteException {
		super();

	}

	@Override
	public void smessage(String message) throws RemoteException {
		System.out.println(nodeCCounter);
		System.out.println("Node C received Message");
		System.out.println("Incrementing Node C counter");
		nodeCCounter = nodeCCounter + 1;
		System.out.println(nodeCCounter);
	}

	public static void main(String[] args) {

		try {
			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.bind("berkly", new NodeC());
			System.err.println("Node C is Established Sucessfully");
			System.out.println("The NodeC Initial counter is : " + nodeCCounter);
			Thread thread = new Thread(new NodeC());
			thread.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getCounter() throws RemoteException {
		return nodeCCounter;
	}

	@Override
	public void adjustCounter(int counter) throws RemoteException {
		nodeCCounter = counter;
		System.out.println("The Node C counter is adjusted to : " + nodeCCounter);

	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Choose option : 1.Send message or 2.Synchronize");
				Scanner sc = new Scanner(System.in);
				Integer input = sc.nextInt();
				if (input == 1) {
					System.out.println("Select the node to send message A|B");
					String node = sc.next();
					if (node.equals("A")) {
						System.out.println("The NodeC counter before sending message");
						System.out.println(nodeCCounter);

						ClientServerInterface nodeA = (ClientServerInterface) Naming
								.lookup("rmi://localhost:8000/berkly");
						nodeA.smessage("Message");
						System.out.println("The NodeC counter after sending message");
						nodeCCounter = nodeCCounter + 1;
						System.out.println(nodeCCounter);
					} else {
						System.out.println("The NodeC counter before sending message");
						System.out.println(nodeCCounter);

						ClientServerInterface nodeB = (ClientServerInterface) Naming
								.lookup("rmi://localhost:8001/berkly");
						nodeB.smessage("Message");
						System.out.println("The NodeC counter after sending message");
						nodeCCounter = nodeCCounter + 1;
						System.out.println(nodeCCounter);
					}

				}
				if (input == 2) {
					System.out.println("Synchronizing the counters.");
					System.out.println("Getting the counters from all nodes.");
					ClientServerInterface nodeA = (ClientServerInterface) Naming.lookup("rmi://localhost:8000/berkly");
					int counterA = nodeA.getCounter();
					ClientServerInterface nodeB = (ClientServerInterface) Naming.lookup("rmi://localhost:8001/berkly");
					int counterB = nodeB.getCounter();
					int counterC = nodeCCounter;
					System.out.println("The NodeA counter is: " + counterA);
					System.out.println("The NodeB counter is: " + counterB);
					System.out.println("The NodeC counter is: " + counterC);

					int average = (counterA + counterB + counterC) / 3;

					System.out.println("Adjusting the counters in all the nodes .");
					nodeCCounter = average;
					nodeB.adjustCounter(average);
					nodeA.adjustCounter(average);
					System.out.println("The Node C counter is adjusted to : " + nodeCCounter);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
