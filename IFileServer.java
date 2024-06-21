import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IFileServer extends Remote {
    List<String> list() throws RemoteException;
    byte[] down(String fileName) throws RemoteException;
    void up(String fileName, byte[] fileData) throws RemoteException;
}
