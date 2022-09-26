package Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ParserUrl extends UrlFile {
    private static int counter = 0;
    private static boolean index = true;
    private UrlFile mainFile;
    private File mindkeeper;
    private String connectionUrl;
    private ArrayList<String> fileNamesList = new ArrayList<>();

    public void setMindkeeper(File mindkeeper) {
        this.mindkeeper = mindkeeper;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }
    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }
    public File getMindkeeper() {
        return mindkeeper;
    }
    public ParserUrl(UrlFile mainFile, String connectionUrl) throws IOException {
        super(mainFile.getFile());
        this.mainFile = mainFile;
        this.connectionUrl = connectionUrl;
    }

    public void parse(){
        try {
            if(!connectionUrl.endsWith(".xml")){
                System.out.print("");
            }
            else {
                URL url = new URL(connectionUrl);
                fileNamesList.add(connectionUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                Document doc = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder()
                        .parse(con.getInputStream());

                if(con.getResponseCode() == 200) {
                    String tagName = doc.getFirstChild().getFirstChild().getNextSibling().getNodeName()/*tag-ul "principal" sitemap|url, se poate de indicat si denumiurea concreta in caz ca nu corespund pozitiile*/;
                    NodeList nodeList = doc.getElementsByTagName(tagName);
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Element el = (Element) nodeList.item(i);
                        String link = el
                                .getElementsByTagName(el
                                        .getFirstChild().getNextSibling().getNodeName()/*"loc"*/)
                                .item(0).getTextContent();
                        setConnectionUrl(link);// link-urile
                        mainFile.getFileWriter().append(link).append("\n");
                        mainFile.getFileWriter().flush();
//                        mindkeeper.getFileWriter().append(link).append("\n");
//                        mindkeeper.getFileWriter().flush();
                        parse();
                    }
                }else System.out.println(con.getResponseCode());
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
    public void parseLimited(int maxValue){
        try {
            if(!connectionUrl.endsWith(".xml")){
                System.out.print("");
            }
            else {
                URL url = new URL(connectionUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                Document doc = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder()
                        .parse(con.getInputStream());
                if(con.getResponseCode() == 200) {
                    String tagName = doc.getFirstChild().getFirstChild().getNextSibling().getNodeName()/*tag-ul "principal" sitemap|url, se poate de indicat si denumiurea concreta in caz ca nu corespund pozitiile*/;
                    NodeList nodeList = doc.getElementsByTagName(tagName);
                    File file = new File("src/Data/url1.txt");
                    PrintWriter urlFile = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Element el = (Element) nodeList.item(i);
                        setConnectionUrl(el
                                .getElementsByTagName(el
                                        .getFirstChild().getNextSibling().getNodeName()/*"loc"*/)
                                .item(0).getTextContent());// link-urile
                        if(counter < maxValue){
                            counter++;
                            mainFile.getFileWriter().append(getConnectionUrl()).append("\n");
                            mainFile.getFileWriter().flush();
                        }else{
                            urlFile.append(getConnectionUrl()).append("\n");
                            urlFile.flush();
                        }
                        parseLimited( maxValue);
                    }
                }else System.out.println(con.getResponseCode());
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public void startFromEnd(String line){
        try {
            setConnectionUrl(line);
            if (!connectionUrl.endsWith(".xml")) {
                System.out.print("");
            } else {
                URL url = new URL(connectionUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                Document doc = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder()
                        .parse(con.getInputStream());
                if (con.getResponseCode() == 200) {
                    String tagName = doc.getFirstChild().getFirstChild().getNextSibling().getNodeName()/*tag-ul "principal" sitemap|url, se poate de indicat si denumiurea concreta in caz ca nu corespund pozitiile*/;
                    NodeList nodeList = doc.getElementsByTagName(tagName);
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Element el = (Element) nodeList.item(i);
                        String link = el
                                .getElementsByTagName(el
                                        .getFirstChild().getNextSibling().getNodeName()/*"loc"*/)
                                .item(0).getTextContent();
                        setConnectionUrl(link);// link-urile
                        mainFile.getFileWriter().append(link).append("\n");
                        mainFile.getFileWriter().flush();
                        startFromEnd(connectionUrl);
                    }
                } else System.out.println(con.getResponseCode());
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
