package Parser;
import java.io.*;

public class UrlFile {
    private final String path;
    private final File file;
    private PrintWriter fileWriter;
    private BufferedReader fileReader;

    public String getPath() {
        return path;
    }
    public File getFile() {
        return file;
    }
    public PrintWriter getFileWriter() {
        return fileWriter;
    }
    public BufferedReader getFileReader() {
        return fileReader;
    }

    public String lastLine(File file) throws IOException {
        String line = "";
        BufferedReader mindkeeperReader = new BufferedReader(new FileReader(file));
        while(mindkeeperReader.readLine() != null){
            line = mindkeeperReader.readLine();
        }
        return line;
    }
    public UrlFile(File file) throws IOException {
        this.path = file.getPath();
        this.file = file;
        setFileReader(file);
        setFileWriter(file);
    }
    protected void setFileReader(File file) throws FileNotFoundException {
        this.fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }
    protected void setFileWriter(File file) throws IOException {
        this.fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
    }
}
