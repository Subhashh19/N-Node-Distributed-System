import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientServerInterface extends Remote {
	void sendMessage(String message) throws RemoteException;

	int getTheCounter() throws RemoteException;

	void adjustTheCounter(int counter) throws RemoteException;

}
