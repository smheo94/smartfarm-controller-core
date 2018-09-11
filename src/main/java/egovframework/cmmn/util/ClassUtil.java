package egovframework.cmmn.util;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.WatchEvent.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClassUtil {
	private static final Logger LOG = LoggerFactory.getLogger( ClassUtil.class );
	public int aa  =5 ;
	public String [] bbb = new String [2];
	public List<ClassUtil> ccc = new ArrayList<ClassUtil>();
	
	public static Map toMap(Object classObject, Class<?> mapType) {
		Object fieldObj = null;		
		try {		
			Object mapObj = 	mapType.getConstructor().newInstance();			
			Map map = (Map)mapObj;			
			for(Field field : classObject.getClass().getFields()) {	
				fieldObj = field.get(classObject);
				
				if( fieldObj == null || field.getType().getPackage() == null || field.getType().getPackage().getName().startsWith("java") ) {
					map.put(field.getName(), fieldObj);	
				} else {
					//System.out.println( field.getType().getName() + " "  + field.getType().getPackage().getName());
					map.put(field.getName(), toMap(fieldObj, mapType));
				}				
			}
			return map;
		} catch (InstantiationException e) {
			LOG.error("[Exception]", e);
		} catch (IllegalAccessException e) {
			LOG.error("[Exception]", e);		
		} catch (IllegalArgumentException e) {			
			LOG.error("[Exception]", e);
		} catch (InvocationTargetException e) {
			LOG.error("[Exception]", e);
		} catch (NoSuchMethodException e) {
			LOG.error("[Exception]", e);
		} catch (SecurityException e) {
			LOG.error("[Exception]", e);
		} catch(Exception e ) {
			if( fieldObj != null ) {
				LOG.error("Type : " + fieldObj);
			}
		}	
		return null;		
	}
	
	public static HashMap<String, Object> toHashMap(Object classObject) {
		return toHashMap(classObject, false);
	}
	
	public static HashMap<String, Object> toHashMap(Object classObject, boolean excludeNull) {
		Object fieldObj = null;		
		try {		
			HashMap<String, Object> map = 	new HashMap<>();			
					
			for(Field field : classObject.getClass().getFields()) {
				
				fieldObj = field.get(classObject);
				if( fieldObj == null || field.getType().getPackage() == null || field.getType().getPackage().getName().startsWith("java") ) {
					if( java.lang.reflect.Modifier.isStatic(field.getModifiers()) && java.lang.reflect.Modifier.isFinal(field.getModifiers()) ) {
						continue;
					}
					if(!excludeNull ) {
						
						map.put(field.getName(), fieldObj);
					}
				} else {
					//System.out.println( field.getType().getName() + " "  + field.getType().getPackage().getName());
					map.put(field.getName(), toHashMap(fieldObj, excludeNull));
				}				
			}
			return map;
		}  catch (IllegalAccessException e) {
			LOG.error("[Exception]", e);		
		} catch (IllegalArgumentException e) {			
			LOG.error("[Exception]", e);
		} catch (SecurityException e) {
			LOG.error("[Exception]", e);
		} catch(Exception e ) {
			if( fieldObj != null ) {
				LOG.error("Type : " + fieldObj);
			}
		}	
		return null;		
	}
	
	
	public static String toString(Object classObject) {
		StringBuilder builder = new StringBuilder();
		//.append(classObject.getClass().getName())
		if( classObject.getClass().getPackage().getName().startsWith("java") ) {
			return "\"" +  classObject.toString() + "\"";
		}
		builder.append(" [");
		try {			
			Field[] fields = classObject.getClass().getFields();
			for(int i = 0; i < fields.length; i++) {
				Field field  = fields[i];
				if(field.getType().getName().startsWith("[L")) {
					//Array
					builder.append(field.getName()).append(":{");
					Object [] fieldObjs = (Object [])field.get(classObject);
					if( fieldObjs != null && fieldObjs.length > 0 ) { 
						for( int j = 0; j < fieldObjs.length ; j++) {
							if( fieldObjs[j] == null || fieldObjs[j].getClass().getPackage() == null || (fieldObjs[j].getClass().getPackage().getName().startsWith("java") && 
									!fieldObjs[j].getClass().getPackage().getName().startsWith("java.util.List") ) ) {
								builder.append("\"").append(fieldObjs[j]).append("\"");								
							} else {
								//System.out.println( field.getType().getName() + " "  + field.getType().getPackage().getName());
								builder.append( fieldObjs[j]  );
							}	
							builder.append(j < fieldObjs.length -1 ?  "," : "");	
						}
					}
					builder.append("}");
				} else if(field.getType().getName().startsWith("java.util.List")) {
					//List
					builder.append(field.getName()).append(":{");
					List<Object> fieldObjList = (List<Object>)field.get(classObject);
					if( fieldObjList != null && fieldObjList.size() > 0 ) { 
						for( int j = 0; j < fieldObjList.size() ; j++) {							
							if( fieldObjList.get(j) == null || fieldObjList.get(j).getClass().getPackage() == null || (fieldObjList.get(j).getClass().getPackage().getName().startsWith("java") && 
									!fieldObjList.get(j).getClass().getPackage().getName().startsWith("java.util.List") ) ) {
								builder.append("\"").append(fieldObjList.get(j)).append("\"");								
							} else {
								//System.out.println( field.getType().getName() + " "  + field.getType().getPackage().getName());
								builder.append( fieldObjList.get(j)  );
							}								
							builder.append(j < fieldObjList.size() -1 ?  "," : "");	
						}
					}
					builder.append("}");
				} else {				
					Object fieldObj = field.get(classObject);
					builder.append(field.getName()).append(":\"").append(fieldObj).append("\"");
				}
				builder.append(i < fields.length -1 ?  "," : "");	
				
			}		
		} catch (IllegalAccessException e) {
			LOG.error("[Exception]", e);
		}	
		builder.append("]");
		return builder.toString();
	}
	
	
	public static Object castToSomething(Object value, Class type) {
		try {
			return type.cast(value);
		} catch(ClassCastException e) {
    		String valueTypeName = value.getClass().getName();
    		String toTypeName = type.getName();
    		if( "java.lang.Integer".equals(toTypeName)  || "int".equals(toTypeName) ) {			 
    			if( "java.lang.Double".equals(valueTypeName) ) {
    				return (Integer)((Double)value).intValue();
    			} else if( "java.lang.String".equals(valueTypeName) ) {
    				return Integer.valueOf((String)value);
    			}				
    		}
    		LOG.error( "Exception : [invokeMethod] : %s --> %s, %s\r\n",  value.getClass().getName(), type.getName(), e.toString() );
    		throw e;
		}
	}
	
	
	
	
	public static String toSimpleString(Object classObject, int length) {
		StringBuilder builder = new StringBuilder();
		//.append(classObject.getClass().getName())
		if( classObject == null) {
			if( length != 0 ) {
				return String.format("\"%1$" + length +"s\"", " " );
			} else {
				return "\"\"";	
			}
		}
			
		if( classObject.getClass().getPackage().getName().startsWith("java") ) {
			
			if( length != 0 ) {
				return String.format("\"%1$" + length +"s\"", classObject.toString() );
			} else {
				return "\"\"";	
			}
		}
		try {			
			Field[] fields = classObject.getClass().getFields();
			for(int i = 0; i < fields.length; i++) {
				Field field  = fields[i];
				if(field.getType().getName().startsWith("[L")) {
					//Array
					builder.append("[");
					Object [] fieldObjs = (Object [])field.get(classObject);
					if( fieldObjs != null && fieldObjs.length > 0 ) { 
						for( int j = 0; j < fieldObjs.length ; j++) {
							builder.append( toSimpleString(fieldObjs[j], length)  );
							builder.append(j < fieldObjs.length -1 ?  "," : "");	
						}
					}
					builder.append("]");
				} else if(field.getType().getName().startsWith("java.util.List")) {
					//List		
					builder.append("[");
					List<Object> fieldObjList = (List<Object>)field.get(classObject);
					if( fieldObjList != null && fieldObjList.size() > 0 ) { 
						for( int j = 0; j < fieldObjList.size() ; j++) {
							builder.append( toSimpleString(fieldObjList.get(j), length)  );
							builder.append(j < fieldObjList.size() -1 ?  "," : "");	
						}
					}
					builder.append("]");
				} else {				
					Object fieldObj = field.get(classObject);
					builder.append( toSimpleString(fieldObj, length)  );
				}
				builder.append(i < fields.length -1 ?  "," : "");					
			}		
		} catch (IllegalAccessException e) {
			LOG.error("[Exception]", e);
		}	
		return builder.toString();
	}
	public static boolean isChange( Object a, Object b ) {
		if( (a == null && b != null)  ||  (a != null && b == null) ) {
			return true;
		}
		if( a == null && b == null) {
		    return false;	
		}		
		if( a.equals(b) ) {
			return false;
		}
		
		return !a.equals(b);
	}
	
//	public static void main(String [] args) {	
//		ClassUtil classUtil = new ClassUtil();
//		classUtil.bbb[0] = "ABC";
//		classUtil.bbb[1] = "DEF";
//		classUtil.ccc.add( new ClassUtil() );		
//		System.out.println(ClassUtil.toString(classUtil));
//		for(Field field : classUtil.getClass().getFields()) { 
//			System.out.println(field.getType().getPackage());
//			System.out.println(field.getDeclaringClass());
//			System.out.println(field.getGenericType());
//			
//		}
//		
//		
//	}
}
