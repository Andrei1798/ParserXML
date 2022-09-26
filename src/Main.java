import Parser.UrlFile;
import Parser.ParserUrl;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String connection = "https://makeup.md/sitemap/sitemap.xml";
//        String connection = "https://makeup.md/sitemap/sitemap-categorys-1.xml";
        File file = new File("src/Data/mindkeeper.txt");
        Scanner scan = new Scanner(file);
        String line = "";
        ArrayList<String> list = new ArrayList<>();
        while(scan.hasNextLine()){
            String temp = scan.nextLine();
            if(temp == null || temp.equals("")){
                System.out.println("Empty file");
                break;
            }else{
                if(temp.endsWith(".xml")){
                    line = temp;
                    System.out.println(line);
                }
            }
        }
        System.out.println(line);
//        UrlFile mindkeeper = new UrlFile(file);
        ParserUrl parserXML = new ParserUrl(new UrlFile(new File("src/Data/url.txt")), connection);
        parserXML.setMindkeeper(file);
        if(line.endsWith(".xml")){
            parserXML.startFromEnd(line);
        }
//        parserXML.startFromEnd(parserXML.firstLine());
    }
}