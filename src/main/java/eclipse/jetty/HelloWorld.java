package eclipse.jetty;

import com.alibaba.fastjson.JSON;  
import org.eclipse.jetty.server.Request;  
import org.eclipse.jetty.server.Server;  
import org.eclipse.jetty.server.handler.AbstractHandler;  
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import java.io.IOException; 

public class HelloWorld extends AbstractHandler  
{  
    public void handle(String target,  
                       Request baseRequest,  
                       HttpServletRequest request,  
                       HttpServletResponse response)  
            throws IOException, ServletException  
    {  
  
        System.out.println(target);  
        System.out.println(JSON.toJSONString(baseRequest.getAuthentication()));  
        System.out.println(JSON.toJSONString(request.getParameterMap()));  
        System.out.println(JSON.toJSONString(response.getLocale()));  
  
        response.setContentType("text/html;charset=utf-8");  
        response.setStatus(HttpServletResponse.SC_OK);  
        baseRequest.setHandled(true);  
        response.getWriter().println("<h1>Hello World success</h1>");  
        response.getWriter().println("session=" + request.getSession(true).getId());  
  
    }  
  
    public static void main(String[] args) throws Exception  
    {  
        Server server = new Server(9998);  
        server.setHandler(new HelloWorld());  
  
        server.start();  
        server.join();  
        
        while(true);
        
    }  
} 
