import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    List<CodePair> codePairs=new ArrayList<>();
    String korpus;
    String decodeKorpus;
    List<BitSet> encodeKorpus;

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public void create(int codeLenght){
      korpus=new String();
       try {
           File file =
                   new File("korpus.txt");
           Scanner sc = new Scanner(file);
           while (sc.hasNextLine())
               korpus=korpus+sc.nextLine();
       }
       catch (Exception e){
           System.out.println("blad wcvzytywania pliku");
       }

    String string=new String("qwertyuiopasdfghjklzxcvbnm1234567890 ");
        BitSet bitSet=new BitSet(codeLenght);
        for(char c:string.toCharArray() ){
            codePairs.add(new CodePair(c,bitSet));
            incrementBitSet(bitSet,codeLenght);
        }
    }
    public void encode(){
        encodeKorpus=new ArrayList<>();
        for(char c: korpus.toCharArray()){
            encodeKorpus.add(findBitSet(c));
        }
    }
    public void decode(){
        decodeKorpus=new String();
        for(BitSet bitSet:encodeKorpus){
            decodeKorpus+=findChar(bitSet);
        }
    }

    private String findChar(BitSet bitSet) {
        for(CodePair codePair:codePairs){
            if(codePair.bitSet.equals(bitSet)) return codePair.character.toString();
        }
        return null;
    }

    private BitSet findBitSet(char c) {
        for(CodePair codePair:codePairs){
            if(codePair.character.equals(c)) return codePair.bitSet;
        }
        return null;
    }

    private BitSet incrementBitSet(BitSet bitset,int codeLenght){
        int i=codeLenght-1;
        while(true){
            if(bitset.get(i)) {bitset.set(i,false); i-=1; continue;}
            bitset.set(i,true);
            break;
        }
        return bitset;
}

public void save(){
    byte data[] =null;
    Path file = Paths.get("encodeFile");
    Files.write(file, codePairs.get(0).bitSet.toByteArray()); //do poprawy
}

}
