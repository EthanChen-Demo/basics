package basicdemos;

import java.io.UnsupportedEncodingException;

public class EnCodeTest {
    public static void main1(String[] args) throws UnsupportedEncodingException {
        /**
         * 一个解码过程：需要源数据的字符编码和目标字符编码以及一个转换器； 
         * 字符数据本身只有一种字符编码，可能被自身携带，
         * 也可能不携带： 比如 username bytes 都无法携带编码属性——只有创建新的数据结构才可以！
         * 字符编码决定了如何解码字节序列和如何编码字符序列！-->中间数据是字节序列！
         */
        String username = "陈灯亮"; // unicode
        byte[] bytes = username.getBytes("ISO-8859-1"); // ISO-8859-1
        username = new String(bytes, "UTF-8"); // unicode
        System.out.println("username: " + username);
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        /**
         * 由于截首导致信息不完整？？？？？
         */
        System.out.println("-----------ISO8859-1----------------");
        String Str = "中文123";
        byte[] bytes = Str.getBytes("ISO8859-1");//字符串先用ISO8859-1编码
        String mm = new String(bytes,"UTF-8"  );//字符串再用ISO8859-1解码
        System.out.println(Str + "-->ISO8859-1-->UTF-8," + "mm: " + mm);  // mm: ??123
        System.out.println(Str + "-->ISO8859-1,length:" + bytes.length);  
        System.out.println(Str + "-->Str.getBytes().length:" + Str.getBytes().length);  
        System.out.println("-------------------------");
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]);
            System.out.print(";");
            System.out.println((char)(bytes[i]));
        }
        
        System.out.println("----------  UTF-8 ----------------");
        bytes = Str.getBytes("UTF-8");//字符串先用ISO8859-1编码
        mm = new String(bytes,"ISO8859-1"  );//字符串再用ISO8859-1解码
        System.out.println(Str + "-->UTF-8-->ISO8859-1," + "mm: " + mm);  // mm: ??123 
        System.out.println(Str + "-->utf-8,length:" + bytes.length); 
        System.out.println(Str + "-->Str.getBytes().length:" + Str.getBytes().length); 
        System.out.println("---------------------------");
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]);
            System.out.print(";");
            System.out.println((char)(bytes[i]));
        }

    }
    
   
}
