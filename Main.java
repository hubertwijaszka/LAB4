import java.io.File;
import java.util.*;

public class Main {
    List<CodePair> codePairs=new ArrayList<>();
    String korpus;
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

}
