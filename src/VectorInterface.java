import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface VectorInterface extends Remote {
	void smessage(Vector<Integer> vector) throws RemoteException;
}
