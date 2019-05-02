/**
 *-------------------------------------------------------------------------------------------*
 * @(#)StringUtils.java	1.0 2005/01/15
 *-------------------------------------------------------------------------------------------*
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * (C) Copyright 2002-2005, by mskim, All rights reserved.
 *
 * Original Author: mskim
 */
package egovframework.cmmn.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 문자열 처리에 사용되는 각종 유틸리티들을 갖는 클래스<BR><BR>
 *
 * History : 2005년 1월 15일<BR><BR>
 *
 * @name StringUtils.java
 * @author <a href="mailto:master@mskim.net">mskim</a>
 * @version 1.0
 * @date 2005.1.15
 */
@Slf4j
public final class StringUtil  {

	// mcchae: abbrLen : Cut the string length by length and attach " ... " to the back.
	// Use for Bulletin Board Title, etc.
	public static String getAbbr(String str, int abbrLen)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); ++i) {
			if (sb.length() > abbrLen) {
				sb.append("...");
				break;
			}
			sb.append(str.charAt(i));
		}
		return sb.toString();
	}

//	// A method that can be utilized when writing a SQL Query.
//	public static String makeField( String str ) {
//        if(str == null){
//    		return null;
//        }else{
//            return " '" + replaceString( str.trim(), "'", "''" ) + "' ";
//        }
//	} // end makeFiled( String )





//	// Replace " oldStng " with " newStng " in the string of " source ".
//	public static String replaceString( String source, String oldStr, String newStr ) {
//		StringBuffer buffer = new StringBuffer();
//		int position = 0;
//		int index = 0;
//		while ( ( position = source.indexOf( oldStr, index ) ) != -1 ) {
//			buffer.append( source.substring( index, position ) );
//			buffer.append( newStr );
//			index = position + oldStr.length();
//		}
//		buffer.append( source.substring( index ) );
//		return buffer.toString();
//	} // end replaceString( String, String, String )



	// Examine whether strings are empty.
	public static boolean isNull( String str ) {
		if ( str == null ) {
			return true;
		}
		return false;
	} // end isNull( String )





	// Inspect whether the string is empty.
	public static boolean isEmpty( String str ) {
		if ( isNull( str ) ) {
			return true;
		}
		if ( str.length() <= 0 ) {
			return true;
		}
		return false;
	} // end isEmpty( String )





	// Inspect whether the string is not empty.
	public static boolean isNotEmpty( String str ) {
		return ! isEmpty( str );
	} // end isEmpty( String )




	public static String fixNull( String str ) {
		if ( isNull( str ) ){
			return "";
		}
			
		return str;
	} // end fixNull( String )
	public static String fixNull( Object str ) {
		return fixNull( (str!=null?str.toString():"") );
	} // end fixNull( String )



	public static String fixNull( String str, String replace ) {
		if ( isEmpty(str) ){
			return replace;
		}
		return str;
	} // end fixNull( Stirng, String )

	public static String fixNull( Object str, String replace ) {
		if ( str == null ) {
			return replace;
		}
		if ( str.getClass().equals(String.class) ) {
			return fixNull((String)str, replace);
		}

		if ( str.toString().length() == 0 ) {
			return replace;
		}
		return str.toString();
	}

	public static String getIntString( Object value, String defaultValue )
	{
		String result = defaultValue;
		
		try{
			result = String.valueOf(Integer.parseInt(StringUtil.fixNull(value, defaultValue)));
		}
		catch (Exception ex)
		{
			log.error(ex.getMessage(), ex);
		}
		return result;
	}




	public static String selectedUpper( String str ) {
		// need implementation
		return null;
	} // end selectedUpper( String )





	public static String selectedLower( String str ) {
		// need implementation
		return null;
	} // end selectedLower( String )



    public static String getPercent( int dividend, int divisor )
    {
        if ( dividend == 0 ) {
        	return "0%";
        }

        double result = dividend / (double)divisor * 100;

        java.text.DecimalFormat df  = new java.text.DecimalFormat("#0.##");

//        if ( result.indexOf(".") != -1 )
//        {
//            result = result.substring(0, result.indexOf(".")+3) + "%";
//        }

        return df.format(result) + "%";

    }

    public static double getPercent_double( double dividend, double divisor )
    {
        if ( dividend == 0 ) {
        	return 0;
        }

        double result = dividend / divisor;

        return result;

    }

    public static String getPercent( double dividend, double divisor )
    {
        if ( dividend == 0 ) {
        	return "0%";
        }

        double result = dividend / divisor * 100;

        java.text.DecimalFormat df  = new java.text.DecimalFormat("#0.##");

//        if ( result.indexOf(".") != -1 )
//        {
//            result = result.substring(0, result.indexOf(".")+3) + "%";
//        }

        return df.format(result) + "%";

    }

    public static double getSum( double count1, double count2, double count3, double count4 )
    {

        double result = count1 + count2 + count3 + count4;


        return result;

    }

    public static String getHiddenIpString(String ip)
    {
    	if ( ip == null || ip.split("[.]").length != 4 ){
    		return ip;
    	}
    		

    	String arr[] = ip.split("[.]");

    	return String.format("%s.%s.***.***", arr[0], arr[1]);
    }

    public static String numberFormat( double value )
    {
        if ( Double.isNaN(value) ){
        	 return "-";
        }
           
        else
            return new java.text.DecimalFormat  (",##0.##").format( value );
    }


    public static String numberFormat( long value )
    {
        return new java.text.DecimalFormat  (",##0.##").format( value );
    }

    public static String numberFormat( int value )
    {
        return new java.text.DecimalFormat  (",##0.##").format( (long)value );

    }

    public static String numberFormat( String value )
    {
        return numberFormat(value, null);
    }
    public static String numberFormat( String value , String pattern)
    {
    	String locPattern = pattern;
        if ( value == null ){
        	return "0";
        }
        if ( locPattern == null ){
        	locPattern = ",##0.##";
        }

        java.text.DecimalFormat df  = new java.text.DecimalFormat(locPattern);

        String result   = "0";

        try
        {
            result  = df.format( df.parse(value) );

        } catch ( java.text.ParseException pe )
        {
			log.error("valutToSec : {} ", pe);
        }

        return result;

    }
    
    public static String valueToSec(String value)
    {
        if ( value == null ) {
        	return "0";
        }

        String result   = "0";

        try
        {	
        	result = valueToSec(Double.parseDouble(value));

        } catch ( Exception ex )
        {
			log.error("valutToSec : {} ", ex);
        }

        return result;
    }

    public static String valueToSec(double value)
    {
        if ( value == 0d ) {
        	return "0";
        }

        java.text.DecimalFormat df  = new java.text.DecimalFormat("#.##");

        String result   = "0";

        try
        {	
            result  = df.format(value/1000d );

        } catch ( Exception ex )
        {
			log.error("valutToSec : {} ", ex);
        }

        return result;
    }

    
//    @Deprecated
//    public static String valueToSec_d(double value)
//    {
//        if ( value == 0.0 ) return "0";
//
//        java.text.DecimalFormat df  = new java.text.DecimalFormat("#.##");
//
//        String result   = "0";
//
//        try
//        {	
//        	double db =  value ;
//            result  = df.format(db/1000d );
//
//        } catch ( Exception ex )
//        {
//        	LOG.error("valutToSec : {} ", ex);
//        }
//
//        return result;
//    }


//    public static String getFixedSizeString( String str, int max_len )
//    {
//        if ( str == null ) return null;
//
//        try
//        {
//            if ( str.length() > max_len )
//            {
//            	int inPrvSize = max_len/2;
//
//            	str = str.substring(0, inPrvSize) + "..." + str.substring( str.length() - inPrvSize + 3, str.length() );
//            }
//        } catch ( Exception ex )
//        {
//        	
//        }
//
//        return str;
//
//    }

   /**
   *@parm String src  , String delim
   *@return String[]
   *@action Return to the array as separated by the separator " Delim " in " src ".
   */

  public static String[] split(String src ,String delim)
  {
	String locSrc = src;
    int count=0;
    int srcSize = 0;
//    Vector vResult = new Vector();
    int nCount = 0, nLastIndex = 0;
    String returnValue[]=null;
    try
    {
      // The location of the " delim " located at the rear.
      nLastIndex = locSrc.indexOf(delim);

      //If there's no one separated by " delim ", ...
      if (nLastIndex == -1)
      {
        returnValue=new String[1];
        returnValue[0]=locSrc;

      }
      else
      {

        for(int i = 0; i < locSrc.length(); i++){
          if( locSrc.charAt(i) == locSrc.charAt(locSrc.indexOf(delim))) {
            count++;
            srcSize +=locSrc.indexOf(delim);
          }
        }
        if(locSrc.lastIndexOf(delim) != locSrc.length() ){
          count++;
        }else{
          locSrc = locSrc.substring(0,locSrc.length()-1);
        }
        returnValue = new String[count];

        while ((locSrc.indexOf(delim) > -1))
        {
          nLastIndex = locSrc.indexOf(delim);
          returnValue[nCount] =locSrc.substring(0,nLastIndex);
          locSrc = locSrc.substring(nLastIndex + delim.length(), locSrc.length());
          nCount ++;
        }
        returnValue[nCount]=locSrc;
      }
    }
    catch (Exception e)
    {
		log.error(e.getMessage(), e);
      return null;
    }


    return returnValue;
  }

  public static String randomString(int minLength, int maxLength)
  {
	  int locMinLength = minLength;	  
	  if (locMinLength > maxLength )
	  {
		  log.warn("'minLength' can not be greater than 'maxLength' ..");
		  locMinLength = maxLength;
	  }

	  String str = "01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";//abcdefghijklmnopqrstuvwxyz!@#$%^&*()";
	  StringBuffer sb = new StringBuffer();

      int length = (int)Math.round(Math.random()*1000) % maxLength;

      if ( length < locMinLength ){
          length += locMinLength;
      }
      if ( length > maxLength ){
          length = maxLength;
      }
	  for ( int i=0; i<length; i++ )
	  {
		  int rnd = (int)Math.round(Math.random()*1000) % str.length();

		  sb.append(str.substring(rnd, rnd+1));
	  }
	  return sb.toString();
  }

    public static int parseInt(String str, int defaultValue)
  	{
  		int result = defaultValue;
  		try
  		{	
  			if ( !isEmpty(str)){
  				result = Integer.parseInt(str);
  			}
  		}
  		catch ( Exception ex )
  		{
			log.error(ex.getMessage(), ex);
  		}
  		return result;
  	}

    public static int parseInt( String str )
    {
    	return parseInt(str, 0);
    }

    public static int parseInt( Object str )
    {
	if ( str != null )
	    return parseInt(str.toString(), 0);
	else
	    return 0;
    }

	public static String replaceMetaString(String metaString)
	{
		String locMetaString = metaString;
		if ( locMetaString != null )
		{
			locMetaString = locMetaString.trim();
			locMetaString = locMetaString.replaceAll("<", "&lt;");
			locMetaString = locMetaString.replaceAll(">", "&gt;");
			locMetaString = locMetaString.replaceAll("\"", "&quot;");//&quot;
			locMetaString = locMetaString.replaceAll("\'", "&#39;");//&#39;
			locMetaString = locMetaString.replaceAll("cookie", "cook!e");
			locMetaString = locMetaString.replaceAll("document", "d0cument");
		}
		return locMetaString;
	}

	public static String replaceScriptString(String metaString)
	{
		String locMetaString = metaString;
		if ( locMetaString != null )
		{
			locMetaString = locMetaString.trim();
			locMetaString = locMetaString.replaceAll("<", "&lt;");
			locMetaString = locMetaString.replaceAll(">", "&gt;");
			locMetaString = locMetaString.replaceAll("\"", "\\\\\"");//&quot;
			locMetaString = locMetaString.replaceAll("\'", "\\\\'");//&#39;
			locMetaString = locMetaString.replaceAll("cookie", "cook!e");
			locMetaString = locMetaString.replaceAll("document", "d0cument");
		}
		return locMetaString;
	}
	public static String restoreScriptString(String metaString)
	{
		String locMetaString = metaString;
		if ( locMetaString != null )
		{
			locMetaString = locMetaString.trim();
			locMetaString = locMetaString.replaceAll("&lt;","<");
			locMetaString = locMetaString.replaceAll("&gt;",">");
			locMetaString = locMetaString.replaceAll("\\\\\"", "\"");//&quot;
            locMetaString = locMetaString.replaceAll("\\\\\'", "\'");//&#39;
			locMetaString = locMetaString.replaceAll("&#39;", "\'");//&#39;
			locMetaString = locMetaString.replaceAll("cook!e", "cookie");
			locMetaString = locMetaString.replaceAll("d0cument", "document");
		}
		return locMetaString;
	}

	public static String replaceScriptKeyword(String metaString)
	{
		String locMetaString = metaString;
		if ( locMetaString != null )
		{
			locMetaString = locMetaString.trim();
			locMetaString = locMetaString.replaceAll("cookie", "cook!e");
			locMetaString = locMetaString.replaceAll("document", "d0cument");
		}
		return locMetaString;
	}

	
	// Unicode( 8859_1 ) -> ( EUC-KR )
	public static String isoToDefault( String str ) {
		return changeCharset(str, "iso-8859-1", "UTF-8");
	} // end uniToEuc( String )

	// Unicode( 8859_1 ) -> ( EUC-KR )
	public static String uniToEuc( String str ) {
		return changeCharset(str, "8859_1", "EUC-KR");
	} // end uniToEuc( String )

	// ( EUC-KR ) -> Unicode( 8859_1 )
	public static String eucToUni( String str ) {
		return changeCharset(str, "EUC-KR", "8859_1");
	} // end eucToUni( String )

	public static String utf2iso(String str) {
		return changeCharset(str, "UTF-8", "iso-8859-1");
	}

	public static String iso2utf(String str) {
		return changeCharset(str, "iso-8859-1", "UTF-8");
	}

	public static String iso2kor(String str) {
		return changeCharset(str, "iso-8859-1", "euc-kr");
	}

	public static String kor2iso(String str) {
		return changeCharset(str, "euc-kr", "iso-8859-1");
	}
	public static String changeCharset(String str, String fromEncode, String toEncode) {
		if (str == null){
			return null;
		}
		try {
			return new String (str.getBytes(fromEncode), toEncode);
		} catch(Exception e) {
			log.error("valutToSec : {} ", e);
		}

		return str;
	}

	public static String getDebugEncoding(String str)
	{
		String result = String.format("utf2iso:%1$s, iso2utf:%2$s, iso2kor:%3$s, kor2iso:%4$s"
				, utf2iso(str), iso2utf(str), iso2kor(str), kor2iso(str) );

		return result;
	}

	public static String getRequestParam(String str)
	{
		return getRequestParam(str, "");
	}

	public static String getRequestParam(String str, String defaultValue)
	{
		return isoToDefault( fixNull(str, defaultValue) );
	}
	
	public static Map<String,String> parseParmeterString(String str)
	{
		Map<String,String> params = new HashMap<String, String>();
		if ( str == null ) {
			return params;
		}
		
		String[] arr = str.split("[&]");
		for(String nv : arr )
		{
			String nvs[] = nv.split("[=]");
			String key = nvs[0];
			String val = "";
			if ( nvs.length == 2 ){
				val = nvs[1];
			}
			params.put(key, val);
		}
		return params;
	}

	public static String camelCaseToUnderscore(final String camelCase) {
		if( StringUtils.isEmpty(camelCase) ) {
			return camelCase;
		}

		final String[] camelCaseParts = StringUtils.splitByCharacterTypeCamelCase(camelCase);
		for (int i = 0; i < camelCaseParts.length; i++) {
			camelCaseParts[i] = camelCaseParts[i].toLowerCase(Locale.ENGLISH);
		}
		return StringUtils.join(camelCaseParts, "_");
	}

	public static String camelize(String input, boolean startWithLowerCase) {
		String[] strings = StringUtils.split(input.toLowerCase(), "_");
		for (int i = startWithLowerCase ? 1 : 0; i < strings.length; i++) {
			strings[i] = StringUtils.capitalize(strings[i]);
		}
		input = StringUtils.join(strings);
		if (!startWithLowerCase) {
			return ucfirst(input);
		}
		return input;
	}
	public static  String ucfirst(String chaine){
		if(chaine.length() < 1) {
			return chaine;
		}
		return chaine.substring(0, 1).toUpperCase() + chaine.substring(1);
	}

} //end StringUtils
