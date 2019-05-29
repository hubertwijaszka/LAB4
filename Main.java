import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;

public class Main {

    public static void main(String[] args)
    {
        FileService fileService=new FileService();
       fileService.create(6);//wczytuje kod
        fileService.encode();//wczytuje korpus.txt i zapisuje zakodowany tekst do encodeFile
        fileService.load("encodeFile");//wczytuje zakodowany tekst z pliku encodeFile i zapisuje odkodowany do decodeFile
     if(fileService.checkDecodeEncodeFiles("korpus.txt","decodeFile")) System.out.println("Poprawnie zakodowano i odkodowano");
     else System.out.println("NIE poprawnie zakodowano i odkodowano");



    }



}
