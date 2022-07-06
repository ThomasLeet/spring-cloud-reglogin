package com.thomas.common.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class XssUtils {
	private static final Logger logger=LoggerFactory.getLogger(XssUtils.class);
	
	public static String getSafeStringXSS(String s){
	       if (StringUtils.isBlank(s)) {  
	           return s;  
	       }  
	       StringBuilder sb = new StringBuilder(s.length() + 16);  
	       for (int i = 0; i < s.length(); i++) {  
	           char c = s.charAt(i);  
	           switch (c) {  
	           case '<':  
	               sb.append("&lt;");  
	               break; 
	           case '>':  
	               sb.append("&gt;");  
	               break;  
	           case '\'':  
	               sb.append("&prime;");// &acute;");  
	               break;  
	           case '′':  
	               sb.append("&prime;");// &acute;");  
	               break;  
	           case '\"':  
	               sb.append("&quot;");  
	               break;  
	           case '＂':  
	               sb.append("&quot;");  
	               break;  
	           case '&':  
	               sb.append("＆");  
	               break;  
	           case '#':  
	               sb.append("＃");  
	               break;  
	           case '\\':  
	               sb.append('￥');  
	               break; 
	           case '=':  
	               sb.append("&#61;");  
	               break;
	           default:  
	               sb.append(c);  
	               break;  
	           }  
	       }  
	       return sb.toString(); 
	   }

	public static String getSafeStringXSS2(String s){
		if (StringUtils.isBlank(s)) {
			return s;
		}
		StringBuilder sb = new StringBuilder(s.length() + 16);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '\'':
					sb.append("&prime;");// &acute;");
					break;
				case '′':
					sb.append("&prime;");// &acute;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}
	
}
