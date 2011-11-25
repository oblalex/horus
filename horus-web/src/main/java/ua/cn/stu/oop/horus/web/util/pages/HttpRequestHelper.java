package ua.cn.stu.oop.horus.web.util.pages;

import javax.servlet.http.HttpServletRequest;



/**
 *
 * @author alex
 */
public class HttpRequestHelper {

    public static String getContextRootUrl(HttpServletRequest request){
        StringBuilder builder = new StringBuilder();       
        
        builder.append(request.getScheme());
        builder.append("://");
        builder.append(request.getServerName());
        builder.append(":");
        builder.append(request.getServerPort());
        builder.append(request.getContextPath());
        builder.append("/");
        
        return builder.toString();
    }
}
