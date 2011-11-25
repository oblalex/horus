package ua.cn.stu.oop.horus.web.util.pages;

/**
 *
 * @author alex
 */
public class RawHtmlTags {

    /**
     * Break line
     */
    public static String BR = "<br />";
        
    /**
     * Horizontal line
     */
    public static String HR = "<hr />";
    
    /**
     * Build anchor by template &lt;a href="link"&gt;content&lt;/a&gt;
     */
    public static String buildAnchor(String link, String content){
        StringBuilder sb = new StringBuilder();        
        sb.append("<a href=\"").append(link).append("\">");
        sb.append(content);
        sb.append("</a>");                
        return sb.toString();
    }
}
