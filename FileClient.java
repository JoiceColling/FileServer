import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class FileClient {
    private IFileServer fileServer;

    public FileClient() throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 8090);
        fileServer = (IFileServer) registry.lookup("FileServer");
    }

    public void listFiles() throws Exception {
        List<String> files = fileServer.list();

        for (int i = 0; i < files.size(); i++) {
            System.out.println((i + 1) + "- " + files.get(i));
        }
    }

    public void downloadFile(int fileIndex) throws Exception {
        List<String> fileList = fileServer.list();

        if (fileIndex >= 0 && fileIndex < fileList.size()) {
            String fileName = fileList.get(fileIndex);
            byte[] fileData = fileServer.down(fileName);
            
            try (FileOutputStream fos = new FileOutputStream(new File(fileName))) {
                fos.write(fileData);
                System.out.println("Arquivo " + fileName + " salvo com sucesso!");
            }
        } else {
            System.out.println("Invalid file number");
        }
    }

    public void uploadFile(String filePath) throws Exception {
        File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            byte[] fileData = Files.readAllBytes(file.toPath());
            fileServer.up(file.getName(), fileData);

            System.out.println("Arquivo " + file.getName() + " transferido!");
        } else {
            System.out.println("Arquivo não encontrado");
        }
    }

    public static void main(String[] args) {
        try {
            FileClient client = new FileClient();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("\nDigite o comando: ");
                String command = scanner.nextLine();
                String[] parts = command.split(" ");
                String action = parts[0];

                switch (action) {
                    case "list":
                        client.listFiles();
                        break;
                    case "down":
                        int fileIndex = Integer.parseInt(parts[1]) - 1;
                        client.downloadFile(fileIndex);
                        break;
                    case "up":
                        String filePath = parts[1];
                        client.uploadFile(filePath);
                        break;
                    default:
                        System.out.println("Comando desconhecido");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
