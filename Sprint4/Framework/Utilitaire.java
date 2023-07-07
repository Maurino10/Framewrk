package etu1809.framework;

import javax.servlet.http.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.sql.Date;
import etu1809.annotation.*;
import etu1809.framework.*;
// import etu1896.framework.ModelView;
import java.lang.annotation.*;

public class Utilitaire{
        
    public Utilitaire() {
    }

    public static String getUrl(String url) {
        String[] decompose = url.split("/");
        String reponse = "";
        int itterator = 0;
        for(int i = decompose.length - 1; i > 3; i --) {
          if(itterator != 0) {
            reponse = "/" + reponse;
          }
          decompose[i] = decompose[i].replace('?', '=');
          String[] tableau = decompose[i].split("=");
          reponse = tableau[0] + reponse;
          itterator += 1;
        }
        
        return "/" + reponse;
    }

    public String getPath(){
        String path = "";
        File file = new File(new Utilitaire().getClass().getSimpleName()+".java");
        String[] chemins = file.getAbsolutePath().replace("\\", "/").split("/");
        for(int i = 0; i<chemins.length - 2; i++) {
            path += chemins[i] + "/";
        }
        path += chemins[chemins.length - 2];
        return path;
    }

    public static ArrayList<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file,packageName + "." + file.getName()));
            } 
            else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName+ '.'+ file.getName().substring(0,file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static HashMap<String,Mapping> getHashMap(String path) throws ClassNotFoundException{
        HashMap<String,Mapping> mappingUrls = new HashMap<String,Mapping>();
        ArrayList<Class<?>> classes = Utilitaire.findClasses(new File(path), "models");
        ArrayList<Method> methods = new ArrayList<>();
        Model anno = null;
        for(int i=0; i<classes.size(); i++) {
            Method[] methodes = classes.get(i).getDeclaredMethods();
            for (Method m: methodes) {
                methods.add(m);
            }
            for(int j=0; j<methods.size(); j++) {
                if(methods.get(j).isAnnotationPresent(Model.class)) {
                    anno = methods.get(j).getAnnotation(Model.class);
                    mappingUrls.put(anno.url(),new Mapping(classes.get(i).getName(), methods.get(j).getName()));
                }
            }
        }
        return mappingUrls;
    }

    public static Vector<String> paramsAnnotation(HashMap<String, Mapping> mapppingUrls, String url) {
        Vector<String> params = new Vector<String>();
        try {
            for(String key : mapppingUrls.keySet()) {
                if(url.equals(key)) {
                    Class<?> myClass = Class.forName(mapppingUrls.get(key).getClassName());
                    Method[] methods = myClass.getMethods();
                    for(Method method : methods) {
                        if(method.getName().equals(mapppingUrls.get(key).getMethod())) {
                            Annotation annoParams = method.getAnnotation(Model.class);
                            
                            if(annoParams instanceof Model) {
                                Model modelAnnoParams = (Model) annoParams;
        
                                for(int i = 0; i < modelAnnoParams.params().length; i++) {
                                    params.add(modelAnnoParams.params()[i]);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return params;
    }
    
    public static void afficher_MappingUrls(HashMap<String,Mapping> mappingUrls,PrintWriter out){
        ArrayList<String> keys =  new ArrayList<String>(mappingUrls.keySet());
        for(int i=0;i<keys.size();i++) {
            out.println("key:"+keys.get(i)+" , "+"(classname = "+mappingUrls.get(keys.get(i)).getClassName() + "; methosd = "+mappingUrls.get(keys.get(i)).getMethod()+")");
        }
    }



}