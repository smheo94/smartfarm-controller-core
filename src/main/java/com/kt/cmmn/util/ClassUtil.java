package com.kt.cmmn.util;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ClassUtil  {
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
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			log.error("[Exception]", e);
		} catch(Exception e ) {
			if( fieldObj != null ) {
				log.error("Type : " + fieldObj);
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
			log.error("[Exception]", e);
		} catch (IllegalArgumentException e) {
			log.error("[Exception]", e);
		} catch (SecurityException e) {
			log.error("[Exception]", e);
		} catch(Exception e ) {
			if( fieldObj != null ) {
				log.error("Type : " + fieldObj);
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
			log.error("[Exception]", e);
		}	
		builder.append("]");
		return builder.toString();
	}

	public static <T>  T castToSomething(Object value, Class<T> type) {
		return castToSomething(value, type, null);
	}
	public static <T>  T castToSomething(Object value, Class<T> type, T defaultValue)
	{
		try {
			if( value == null ) {
				return (T)defaultValue;
			}
			return (T)type.cast(value);
		} catch(ClassCastException e)  {
			return getT(value, type, defaultValue, e);
		}
	}

	private static <T> T getT(Object value, Class<T> type, Object defaultValue, ClassCastException e) {
		String valueTypeName = value.getClass().getName();
		String toTypeName = type.getName();
		if( "java.lang.Integer".equals(toTypeName)  || "int".equals(toTypeName) ) {
			if( "java.lang.Double".equals(valueTypeName) ) {
				return (T)(Integer)((Double)value).intValue();
			} else if( "java.lang.Long".equals(valueTypeName) ) {
				return (T)(Integer)((Long)value).intValue();
			} else if( "java.lang.String".equals(valueTypeName) ) {
				return (T)Integer.valueOf((String)value);
			}
		} else if( "java.lang.Double".equals(toTypeName)  || "double".equals(toTypeName) ) {
			if( "java.lang.Integer".equals(valueTypeName) ) {
				return (T)(Double)((Integer)value).doubleValue();
			} else if( "java.lang.Long".equals(valueTypeName) ) {
				return (T)(Double)((Long)value).doubleValue();
			} else if( "java.lang.String".equals(valueTypeName) ) {
				return (T)Double.valueOf((String)value);
			}
		} else if( "java.lang.Long".equals(toTypeName)  || "long".equals(toTypeName) ) {
			if( "java.lang.Integer".equals(valueTypeName) ) {
				return (T)(Long)((Integer)value).longValue();
			} else if( "java.lang.Double".equals(valueTypeName) ) {
				return (T)(Long)((Double)value).longValue();
			} else if( "java.lang.String".equals(valueTypeName) ) {
				return (T)Long.valueOf((String)value);
			}
		} else if( "java.lang.String".equals(toTypeName)  ) {
			return (T)String.valueOf(value);
		} else if( "java.sql.Time".equals(toTypeName)  ) {
			if( value instanceof String ) {
				if( ((String) value).length() == 8 ) {
					return (T) Time.valueOf( ((String) value).substring(((String) value).length() - 8 )  );
				} else if( ((String) value).length() > 8) {
					final LocalTime localTime = DateTime.parse((String) value).withZone(DateTimeZone.getDefault()).toLocalTime();
					return (T)new Time(localTime.toDateTimeToday().getMillis());
				}
			}
		} else if( "java.util.Date".equals(toTypeName)  ) {
			return (T)DateUtil.parse(value);
		} else if( defaultValue != null ) {
			return (T)defaultValue;
		}
		log.error( "Exception : [invokeMethod] : {} --> {}, {}\r\n",  value, type.getName(), e.toString() );
		throw e;
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
			log.error("[Exception]", e);
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
