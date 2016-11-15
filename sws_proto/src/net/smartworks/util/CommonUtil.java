/*	
 * file 		 : CommonUtil.java
 * created by    : kmyu
 * creation-date : 2016. 11. 15.
 */

package net.smartworks.util;

public class CommonUtil {

	public static final String EMPTY = "";
	public static final String NULL = "null";
	public static final String SPACE = " ";
	public static final String EQUAL = "=";
	public static final String DASH = "-";
	public static final String UNDERBAR = "_";
	public static final String DOT = ".";
	public static final String COMMA = ",";
	public static final String ASTERISK = "*";
	public static final String QUESTION = "?";
	public static final String COLON = ":";
	public static final String SEMICOLON = ";";
	public static final String BAR = "|";
	public static final String SLASH = "/";
	public static final String PERCENT = "%";
	public static final String RN = "\r\n";
	public static final String TAB = "\t";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String Y = "Y";
	public static final String N = "N";
	public static final String ON = "on";
	public static final String ONE = "1";
	public static final String ZERO = "0";
	public static final String DELIMITEROPEN = "${";
	public static final String DELIMITERCLOSE = "}";

	public static final String IMAGE_TYPE_ORIGINAL = "_origin";
	public static final String IMAGE_TYPE_THUMB = "_thumb";
	
	public static boolean toBoolean(Object bool) {
		if (bool == null)
			return false;
		if (bool instanceof Boolean) {
			boolean b = ((Boolean) bool).booleanValue();
			return b;
		}
		bool = bool.toString();
		if (bool.equals(TRUE) || bool.equals(Y) || 
				bool.equals(ON) || bool.equals(ONE))
			return true;
		return false;
	}
	
}

