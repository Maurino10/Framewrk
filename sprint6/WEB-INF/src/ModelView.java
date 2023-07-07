package etu1809.framework;

import java.lang.reflect.*;
import java.util.*;
import java.sql.Date;
import etu1809.framework.*;;
import etu1809.framework.Utilitaire;

public class ModelView {
  String view;
  HashMap<String, Object> data = new HashMap<String, Object>();

  public ModelView(){

  }

  public HashMap<String, Object> getData() {
    return data;
  }

  public void setData(HashMap<String, Object> data) {
    this.data = data;
  }

  public String getView() {
    return view;
  }

  public void setView(String view) {
    this.view = view;
  }

  
  public ModelView(String view) {
    this.view = view;
  }

  
  public void addItem(String key, Object value) {
    this.getData().put(key,value);
  }

  public static ModelView loadView(String url, HashMap<String, Mapping> mappingUrls) throws Exception {
    Set<String> set = mappingUrls.keySet();
    if (!set.contains(url)) {
      throw new Exception("404 not found!");
    }

    String className = mappingUrls.get(url).getClassName();
    String methodName = mappingUrls.get(url).getMethod();
    Class<?> classe = Class.forName(className);
    Constructor<?> constructor = classe.getDeclaredConstructor();
    Object object = constructor.newInstance();
    Method method = null;
    ModelView view = null;
    try {
      method = classe.getDeclaredMethod(methodName);
      view = (ModelView) method.invoke(object);      
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    
    return view;
  }

  
 
}