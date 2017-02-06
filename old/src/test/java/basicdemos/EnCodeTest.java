package basicdemos;

import java.io.UnsupportedEncodingException;

public class EnCodeTest {
    public static void main1(String[] args) throws UnsupportedEncodingException {
        /**
         * һ��������̣���ҪԴ���ݵ��ַ������Ŀ���ַ������Լ�һ��ת������ 
         * �ַ����ݱ���ֻ��һ���ַ����룬���ܱ�����Я����
         * Ҳ���ܲ�Я���� ���� username bytes ���޷�Я���������ԡ���ֻ�д����µ����ݽṹ�ſ��ԣ�
         * �ַ������������ν����ֽ����к���α����ַ����У�-->�м��������ֽ����У�
         */
        String username = "�µ���"; // unicode
        byte[] bytes = username.getBytes("ISO-8859-1"); // ISO-8859-1
        username = new String(bytes, "UTF-8"); // unicode
        System.out.println("username: " + username);
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        /**
         * ���ڽ��׵�����Ϣ����������������
         */
        System.out.println("-----------ISO8859-1----------------");
        String Str = "����123";
        byte[] bytes = Str.getBytes("ISO8859-1");//�ַ�������ISO8859-1����
        String mm = new String(bytes,"UTF-8"  );//�ַ�������ISO8859-1����
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
        bytes = Str.getBytes("UTF-8");//�ַ�������ISO8859-1����
        mm = new String(bytes,"ISO8859-1"  );//�ַ�������ISO8859-1����
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
