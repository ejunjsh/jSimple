package com.sky.jSimple.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CastUtil {

    // 转为 String 型
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    // 转为 String 型（提供默认值）
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    // 转为 double 型
    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    // 转为 double 型（提供默认值）
    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    // 转为 long 型
    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    // 转为 long 型（提供默认值）
    public static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    // 转为 int 型
    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    // 转为 int 型（提供默认值）
    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    // 转为 boolean 型
    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    // 转为 boolean 型（提供默认值）
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }

    // 转为 String[] 型
    public static String[] castStringArray(Object[] objArray) {
        if (objArray == null) {
            objArray = new Object[0];
        }
        String[] strArray = new String[objArray.length];
        if (ArrayUtil.isNotEmpty(objArray)) {
            for (int i = 0; i < objArray.length; i++) {
                strArray[i] = castString(objArray[i]);
            }
        }
        return strArray;
    }

    // 转为 double[] 型
    public static double[] castDoubleArray(Object[] objArray) {
        if (objArray == null) {
            objArray = new Object[0];
        }
        double[] doubleArray = new double[objArray.length];
        if (!ArrayUtil.isEmpty(objArray)) {
            for (int i = 0; i < objArray.length; i++) {
                doubleArray[i] = castDouble(objArray[i]);
            }
        }
        return doubleArray;
    }

    // 转为 long[] 型
    public static long[] castLongArray(Object[] objArray) {
        if (objArray == null) {
            objArray = new Object[0];
        }
        long[] longArray = new long[objArray.length];
        if (!ArrayUtil.isEmpty(objArray)) {
            for (int i = 0; i < objArray.length; i++) {
                longArray[i] = castLong(objArray[i]);
            }
        }
        return longArray;
    }

    // 转为 int[] 型
    public static int[] castIntArray(Object[] objArray) {
        if (objArray == null) {
            objArray = new Object[0];
        }
        int[] intArray = new int[objArray.length];
        if (!ArrayUtil.isEmpty(objArray)) {
            for (int i = 0; i < objArray.length; i++) {
                intArray[i] = castInt(objArray[i]);
            }
        }
        return intArray;
    }

    // 转为 boolean[] 型
    public static boolean[] castBooleanArray(Object[] objArray) {
        if (objArray == null) {
            objArray = new Object[0];
        }
        boolean[] booleanArray = new boolean[objArray.length];
        if (!ArrayUtil.isEmpty(objArray)) {
            for (int i = 0; i < objArray.length; i++) {
                booleanArray[i] = castBoolean(objArray[i]);
            }
        }
        return booleanArray;
    }
    
	public static Object castPirmitiveObject(Class<?> clazz, String value) {
		if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
			return CastUtil.castInt(value);
		} else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
			return CastUtil.castLong(value);
		} else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
			return CastUtil.castDouble(value);
		} else if (clazz.equals(String.class)) {
			return value;
		} else {
			return null;
		}
	}
	
	 /**
     * java反射bean的get方法
     * 
     * @param objectClass
     * @param fieldName
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Method getGetMethod(Class<?> objectClass, String fieldName) {
        StringBuffer sb = new StringBuffer();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        try {
            return objectClass.getMethod(sb.toString());
        } catch (Exception e) {
        }
        return null;
    }
    
    /**
     * java反射bean的set方法
     * 
     * @param objectClass
     * @param fieldName
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Method getSetMethod(Class<?> objectClass, String fieldName) {
        try {
            Class<?>[] parameterTypes = new Class[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            StringBuffer sb = new StringBuffer();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            Method method = objectClass.getMethod(sb.toString(), parameterTypes);
            return method;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 执行set方法
     * 
     * @param o 执行对象
     * @param fieldName 属性
     * @param value 值
     */
    public static void invokeSet(Object o, String fieldName, Object value) {
        Method method = getSetMethod(o.getClass(), fieldName);
        try {
            method.invoke(o, new Object[] { value });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行get方法
     * 
     * @param o 执行对象
     * @param fieldName 属性
     */
    public static Object invokeGet(Object o, String fieldName) {
        Method method = getGetMethod(o.getClass(), fieldName);
        try {
            return method.invoke(o, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
