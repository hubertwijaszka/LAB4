import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileService {
    List<CodePair> codePairs = new ArrayList<>();
    String korpus;
    String decodeKorpus;
    List<BitSet> encodeKorpus;
    static String allCharacters = new String("qwertyuiopasdfghjklzxcvbnm1234567890 ");
    public  void create(int codeLenght) {
        String string = allCharacters;
        BitSet bitSet = new BitSet(codeLenght);
        for (char c : string.toCharArray()) {
            bitSet= incrementBitSet(bitSet, codeLenght);
            codePairs.add(new CodePair(c, bitSet));
        }
    }

    public void encode() {
        korpus = new String();
        try {
            File file =
                    new File("korpus.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                korpus = korpus + sc.nextLine();
        } catch (Exception e) {
            System.out.println("blad wcvzytywania pliku");
        }
        encodeKorpus = new ArrayList<>();
        for (char c : korpus.toCharArray()) {
            encodeKorpus.add(findBitSet(c));
        }
        save();
    }

    public void decode() {
        decodeKorpus = new String();
        for (BitSet bitSet : encodeKorpus) {
            decodeKorpus += findChar(bitSet);
        }
    }

    private String findChar(BitSet bitSet) {
        for (CodePair codePair : codePairs) {
            if (codePair.bitSet.equals(bitSet)) return codePair.character.toString();
        }
        return null;
    }

    private BitSet findBitSet(char c) {
        for (CodePair codePair : codePairs) {
            if (codePair.character.equals(c)) return codePair.bitSet;
        }
        return null;
    }

    private BitSet incrementBitSet(BitSet bitset, int codeLenght) {
        int i = 0;
        while (true) {
            if (bitset.get(i)) {
                bitset.set(i, false);
                i += 1;
                continue;
            }
            bitset.set(i, true);
            break;
        }
        BitSet result= (BitSet) bitset.clone();
        return result;
    }
    private void insert(BitSet bitsetToInsert, int counter,BitSet destniationBitSet) {
        int i = counter*6;
        for(int k=0;k<6;k++){
            destniationBitSet.set(i+k,bitsetToInsert.get(k));
        }
    }

    public void save() {
        BitSet result=new BitSet(37*6+encodeKorpus.size()*6);
        int counter=0;
        try {
        FileOutputStream fos = new FileOutputStream("encodeFile");
            for (CodePair codePair : codePairs
            ) {
                insert(codePair.bitSet,counter,result);
                counter++;
            }
            for (BitSet bitSet : encodeKorpus
            ) {
                insert(bitSet,counter,result);
                counter++;
            }
            fos.write(result.toByteArray());
            fos.close();
        } catch (Exception e) {
            System.out.println("blad zapisu pliku pliku");
        }

    }
    public void load(String fileName) {
        try {
            String result=new String();
            int counter=0;
            FileInputStream fos = new FileInputStream(fileName);
            byte[] data=new byte[3000000];
            fos.read(data);
            result+=decodeByteArray(data);
            while(fos.read(data,counter*3000,3000)>0){
                decodeByteArray(data);
            }
            fos.close();
        } catch (Exception e) {
            System.out.println("blad zapisu pliku pliku");
        }

    }
    public  byte[] trimEnd(byte[] data)
    {
        int counter=27;
        while(data[counter-2]!=0 || data[counter-1]!=0 ) counter++;
        return Arrays.copyOfRange(data,0,counter);
    }
    private String decodeByteArray(byte[] data) {
        data=trimEnd(data);
        BitSet bitSet=BitSet.valueOf(data);
        String result=new String();
        int counter=0;
        if(codePairs==null){
            codePairs=new ArrayList<>();
            for (Character c: allCharacters.toCharArray()
                 ) {codePairs.add(new CodePair(c,new BitSet(6)));
            }
        }
        for(int i=0;i<37;i++){
            codePairs.get(i).bitSet=bitSet.get(counter*6,counter*6+6);
            counter++;
        }
        while(bitSet.length()>counter*6+5){
            result+=findChar(bitSet.get(counter*6,counter*6+6));
            counter++;
        }
        if(bitSet.length()-counter*6>0){
            //ostatni znak
            int difference=bitSet.length()-counter*6;
            result+=findChar(getLastChar(difference,bitSet,counter*6));
        }
        saveToFile(result);
        return result;
    }

    private BitSet getLastChar(int difference, BitSet bitSet,int copyStartIndex) {
        BitSet result=new BitSet(6);
        int i=0;
        for(;i<difference;i++,copyStartIndex++){
            result.set(i,bitSet.get(copyStartIndex));
        }
        for(;i<6;i++){
            result.set(i,false);
        }
        return result;
    }

    public Boolean checkDecodeEncodeFiles(String fileName1,String fileName2){
        try {
            String text1=new String();
            String text2=new String();
            File file =
                    new File(fileName1);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                text1 = text1 + sc.nextLine();
            File file2 =
                    new File(fileName2);
            Scanner sc2 = new Scanner(file2);
            while (sc2.hasNextLine())
                text2 = text2 + sc2.nextLine();
            if(text1.equals(text2)) return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;

    }
    private void saveToFile(String text){
       try{
        BufferedWriter writer = new BufferedWriter(new FileWriter("decodeFile"));
        writer.write(text);
        writer.close();}
       catch(Exception e){
           System.out.println("błąd zapisu");
       }
    }
}
