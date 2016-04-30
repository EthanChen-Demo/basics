import java.lang.reflect.Method;
import java.util.List;

import com.reflect.util.ReflectUtil;

public class TestAutoGenerator {

    public static String targetMethodTag = "{targetMethodName}";

    public static void main(String[] args) {
        String testMethodNamePattern = "test_{xxx}";
        
        System.out.println(testMethodNamePattern.replaceAll("\\{(.)?\\}", "method"));  // error  ——学习好深刻；并且不能忘记了！
        
        System.out.println(testMethodNamePattern.replaceAll("\\{[^}]*\\}", "method")); // right !
    }

    /**
     * @throws IllegalArgumentException
     * @param testSupperClass a super class that this tester class extends 
     * @param testMethodNamePattern  tester class name 
     * @param testClassNamePattern tester method name pattern 
     * @param targetFullSpecifiedClassName class to test
     */
    public void generateTest(String targetFullSpecifiedClassName, Class<?> testSupperClass, String testMethodNamePattern, String testClassNamePattern) {
        try {
            String emptys = "        "; // 空白符
            String nextLine = "\n"; // 换行
            String backspace = "\r"; // 退格
            String semicolon = ";"; // 分号
            String extendsStr = "extends ";
            Class<?> targetClass = Class.forName(targetFullSpecifiedClassName);
            List<Method> allPublicMethods = ReflectUtil.getAllMethod(targetClass);

            String testerClass = "";

            // imports
            testerClass += emptys + "import" + testSupperClass.getName() + semicolon + nextLine;
            testerClass += nextLine;
            // className
            testerClass += emptys + "public class " + testClassNamePattern + emptys + extendsStr + emptys + testSupperClass.getName() + "{" + nextLine;
            for (Method targetMethod : allPublicMethods) {
                // a method
                testerClass += emptys + backspace + "@Test" + nextLine;
                testerClass += emptys + backspace + "public void " + this.generateTestMethodName(targetMethod, testMethodNamePattern) + "{" + nextLine;
                testerClass += emptys + backspace + "}" + nextLine;
            }

            // end of tester class
            testerClass += emptys + backspace + "}" + nextLine;

            System.out.println();
            System.out.println();
            System.out.println(testerClass);


        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param targetMethod
     * @param testMethodNamePattern
     * @return
     */
    String generateTestMethodName(Method targetMethod, String testMethodNamePattern) {
        String targetMethodName = targetMethod.getName();
        return testMethodNamePattern.replaceFirst("\\{[^}]*\\}", targetMethodName);
    }

}
