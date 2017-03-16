package easymock;

public class StudentApplication {
    IStudent student=null;
    public StudentApplication(){
        
    }
    
    public String doMethod(){
        String str1=student.doMethod1();
        String str2=student.doMethod2();
        String str3=student.doMethod3();
        return str1+str2+str3;
    }

    public IStudent getStudent() {
        return student;
    }

    public void setStudent(IStudent student) {
        this.student = student;
    }
    
    

}