package com.sky.jSimple.utils;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {

 

    // 获取类加载器
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    // 获取类路径
    public static String getClassPath() {
        String classpath = "";
        URL resource = getClassLoader().getResource("");
        if (resource != null) {
            classpath = resource.getPath();
        }
        return classpath;
    }

    // 加载类
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return cls;
    }

    // 获取指定包名下的所有类
    public static List<Class<?>> getClassList(String packageName, boolean isRecursive) {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        String packagePath = url.getPath();
                        addClass(classList, packagePath, packageName, isRecursive);
                    } else if (protocol.equals("jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        Enumeration<JarEntry> jarEntries = jarFile.entries();
                        while (jarEntries.hasMoreElements()) {
                            JarEntry jarEntry = jarEntries.nextElement();
                            String jarEntryName = jarEntry.getName();
                            if (jarEntryName.endsWith(".class")) {
                                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                if (isRecursive || className.substring(0, className.lastIndexOf(".")).equals(packageName)) {
                                    classList.add(loadClass(className, false));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return classList;
    }

    // 获取指定包名下指定注解的所有类
    public static List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        String packagePath = url.getPath();
                        addClassByAnnotation(classList, packagePath, packageName, annotationClass);
                    } else if (protocol.equals("jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        Enumeration<JarEntry> jarEntries = jarFile.entries();
                        while (jarEntries.hasMoreElements()) {
                            JarEntry jarEntry = jarEntries.nextElement();
                            String jarEntryName = jarEntry.getName();
                            if (jarEntryName.endsWith(".class")) {
                                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                Class<?> cls = loadClass(className, false);
                                if (cls.isAnnotationPresent(annotationClass)) {
                                    classList.add(cls);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return classList;
    }

    // 获取指定包名下指定父类的所有类
    public static List<Class<?>> getClassListBySuper(String packageName, Class<?> superClass) {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        String packagePath = url.getPath();
                        addClassBySuper(classList, packagePath, packageName, superClass);
                    } else if (protocol.equals("jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        Enumeration<JarEntry> jarEntries = jarFile.entries();
                        while (jarEntries.hasMoreElements()) {
                            JarEntry jarEntry = jarEntries.nextElement();
                            String jarEntryName = jarEntry.getName();
                            if (jarEntryName.endsWith(".class")) {
                                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                Class<?> cls = loadClass(className, false);
                                if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                                    classList.add(cls);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return classList;
    }

    private static void addClass(List<Class<?>> classList, String packagePath, String packageName, boolean isRecursive) {
        try {
            File[] files = getClassFiles(packagePath);
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isFile()) {
                        String className = getClassName(packageName, fileName);
                        classList.add(loadClass(className, false));
                    } else {
                        if (isRecursive) {
                            String subPackagePath = getSubPackagePath(packagePath, fileName);
                            String subPackageName = getSubPackageName(packageName, fileName);
                            addClass(classList, subPackagePath, subPackageName, true);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static File[] getClassFiles(String packagePath) {
        return new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
    }

    private static String getClassName(String packageName, String fileName) {
        String className = fileName.substring(0, fileName.lastIndexOf("."));
        if (StringUtil.isNotEmpty(packageName)) {
            className = packageName + "." + className;
        }
        return className;
    }

    private static String getSubPackagePath(String packagePath, String filePath) {
        String subPackagePath = filePath;
        if (StringUtil.isNotEmpty(packagePath)) {
            subPackagePath = packagePath + "/" + subPackagePath;
        }
        return subPackagePath;
    }

    private static String getSubPackageName(String packageName, String filePath) {
        String subPackageName = filePath;
        if (StringUtil.isNotEmpty(packageName)) {
            subPackageName = packageName + "." + subPackageName;
        }
        return subPackageName;
    }

    private static void addClassByAnnotation(List<Class<?>> classList, String packagePath, String packageName, Class<? extends Annotation> annotationClass) {
        try {
            File[] files = getClassFiles(packagePath);
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isFile()) {
                        String className = getClassName(packageName, fileName);
                        Class<?> cls = loadClass(className, false);
                        if (cls.isAnnotationPresent(annotationClass)) {
                            classList.add(cls);
                        }
                    } else {
                        String subPackagePath = getSubPackagePath(packagePath, fileName);
                        String subPackageName = getSubPackageName(packageName, fileName);
                        addClassByAnnotation(classList, subPackagePath, subPackageName, annotationClass);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void addClassBySuper(List<Class<?>> classList, String packagePath, String packageName, Class<?> superClass) {
        try {
            File[] files = getClassFiles(packagePath);
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isFile()) {
                        String className = getClassName(packageName, fileName);
                        Class<?> cls = loadClass(className, false);
                        if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                            classList.add(cls);
                        }
                    } else {
                        String subPackagePath = getSubPackagePath(packagePath, fileName);
                        String subPackageName = getSubPackageName(packageName, fileName);
                        addClassBySuper(classList, subPackagePath, subPackageName, superClass);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /** 
     * 获取方法参数名称，按给定的参数类型匹配方法 
     *  
     * @param clazz 
     * @param method 
     * @param paramTypes 
     * @return 
     * @throws NotFoundException 
     *             如果类或者方法不存在 
     * @throws MissingLVException 
     *             如果最终编译的class文件不包含局部变量表信息 
     */  
    public static String[] getMethodParamNames(Class<?> clazz, String method, Class<?>... paramTypes)  
            throws NotFoundException, MissingLVException {  
  
        ClassPool pool = ClassPool.getDefault();  
        CtClass cc = pool.get(clazz.getName());  
        String[] paramTypeNames = new String[paramTypes.length];  
        for (int i = 0; i < paramTypes.length; i++)  
            paramTypeNames[i] = paramTypes[i].getName();  
        CtMethod cm = cc.getDeclaredMethod(method, pool.get(paramTypeNames));  
        return getMethodParamNames(cm);  
    }  
  
    /** 
     * 获取方法参数名称，匹配同名的某一个方法 
     *  
     * @param clazz 
     * @param method 
     * @return 
     * @throws NotFoundException 
     *             如果类或者方法不存在 
     * @throws MissingLVException 
     *             如果最终编译的class文件不包含局部变量表信息 
     */  
    public static String[] getMethodParamNames(Class<?> clazz, String method) throws NotFoundException, MissingLVException {  
  
        ClassPool pool = ClassPool.getDefault();  
        ClassClassPath classPath = new ClassClassPath(ClassUtil.class);
        pool.insertClassPath(classPath);
        CtClass cc = pool.get(clazz.getName());  
        CtMethod cm = cc.getDeclaredMethod(method);  
        return getMethodParamNames(cm);  
    }  
  
    /** 
     * 获取方法参数名称 
     *  
     * @param cm 
     * @return 
     * @throws NotFoundException 
     * @throws MissingLVException 
     *             如果最终编译的class文件不包含局部变量表信息 
     */  
    protected static String[] getMethodParamNames(CtMethod cm) throws NotFoundException, MissingLVException {  
        CtClass cc = cm.getDeclaringClass();  
        MethodInfo methodInfo = cm.getMethodInfo();  
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();  
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);  
        if (attr == null)  
            throw new MissingLVException(cc.getName());  
  
        String[] paramNames = new String[cm.getParameterTypes().length];  
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;  
        for (int i = 0; i < paramNames.length; i++)  
            paramNames[i] = attr.variableName(i + pos);  
        return paramNames;  
    }  
  
    /** 
     * 在class中未找到局部变量表信息<br> 
     * 使用编译器选项 javac -g:{vars}来编译源文件 
     *  
     * @author Administrator 
     *  
     */  
    public static class MissingLVException extends Exception {  
        static String msg = "class:%s 不包含局部变量表信息，请使用编译器选项 javac -g:{vars}来编译源文件。";  
  
        public MissingLVException(String clazzName) {  
            super(String.format(msg, clazzName));  
        }  
    } 
    
    public static Field[] getAllFields(Class<?> cls)
    {
    	  List<Field> fields=new ArrayList<Field>();
    	  Field[] fields2= cls.getDeclaredFields();
    	  for(Field field:fields2)
    	  {
    		  fields.add(field);
    	  }
    	  Class<?> superClass=cls.getSuperclass();
    	  if(superClass!=null)
    	  {
    		   fields2=getAllFields(superClass);
    		   for(Field field:fields2)
    	    	  {
    	    		  fields.add(field);
    	    	  }
    	  }
    	  fields2=new Field[fields.size()];
    	  for(int i=0;i<fields.size();i++)
    	  {
    		  fields2[i]=fields.get(i);
    	  }
    	  return fields2;
    }
    
}
