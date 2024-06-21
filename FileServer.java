import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class FileServer extends UnicastRemoteObject implements IFileServer {
    private final File storageDir;

    protected FileServer(String storagePath) throws RemoteException {
        super();
        storageDir = new File(storagePath);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
    }

    @Override
    public List<String> list() throws RemoteException {
        String[] fileList = storageDir.list();
        List<String> files = new ArrayList<>();
        if (fileList != null) {
            for (String file : fileList) {
                files.add(file);
            }
        }
        return files;
    }

    @Override
    public byte[] down(String fileName) throws RemoteException {
        File file = new File(storageDir, fileName);
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RemoteException("Erro em down", e);
        }
    }

    @Override
    public void up(String fileName, byte[] fileData) throws RemoteException {
        File file = new File(storageDir, fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(fileData);
        } catch (IOException e) {
            throw new RemoteException("Erro em up", e);
        }
    }

    public static void main(String[] args) {
        try {
            FileServer server = new FileServer("storage");
            Registry registry = LocateRegistry.createRegistry(8090);
            registry.rebind("FileServer", server);
            System.out.println("Servidor iniciado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
