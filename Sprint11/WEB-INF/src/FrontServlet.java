package etu1809.framework.servlet;

import java.io.*;
import java.lang.reflect.Method;
import javax.servlet.*; 
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import java.util.*;
import etu1809.framework.*;

@MultipartConfig(fileSizeThreshold = 2240 * 2240, maxFileSize = 2240 * 2240, maxRequestSize = 2240 * 2240 * 5 * 5)
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mapping_url = null;
    HashMap<String, Object> objects = null;

    @Override
    public void init() throws ServletException {
        super.init();
         try {
            ServletContext ctx = getServletContext() ;
            String path = ctx.getRealPath("/WEB-INF/classes/models");    
            this.mapping_url = Utilitaire.getHashMap(path);
            this.objects = Utilitaire.singletons(path);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void processRequest (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
        PrintWriter out = res.getWriter();
        out.println("********* Url and Path *********");
        out.println("Url: " + req.getRequestURL());
        out.println("Contexte: " + req.getContextPath());
        out.println("********* Url and Path *********");

        out.println("********* Valeur dans HashMap *********");
        Utilitaire.afficher_MappingUrls(this.mapping_url,out);
        out.println("********* Valeur dans HashMap *********");
        
        String url = String.valueOf(req.getRequestURL());
        String apres_contexte = Utilitaire.getUrl(url);
        out.println("url ou valeur annotation: " + apres_contexte);

        try {
            if(apres_contexte != null) {
                for (String key: mapping_url.keySet()) {
                    if(apres_contexte.equals(key)) {
                        Class<?> clazz = Class.forName(mapping_url.get(key).getClassName());
                        Object ob = clazz.getDeclaredConstructor().newInstance();

                        Vector<String> params = Utilitaire.paramsAnnotation(mapping_url, apres_contexte);
                        Vector<String> paramsValue = new Vector<String>();
                        for (int i = 0; i < params.size(); i++) {
                            paramsValue.add(req.getParameter(params.get(i)));
                        }
                        int condition = Utilitaire.countFields(req, ob);
                        if(params.size() != 0) {
                            condition = 0;
                        }
                        out.println("condition: " + condition);
                        ModelView view = ModelView.loadView(apres_contexte, this.mapping_url, paramsValue);
                        
                        String className = Utilitaire.classToSave(mapping_url, apres_contexte);
                        out.println("className: " + className);
                        out.println(this.objects.get(className).getClass().getSimpleName());
                        if(condition != 0) {
                            if (this.objects.get(className) != null) {
                                Utilitaire.resetSet(this.objects.get(className));
                                Utilitaire.save(req, mapping_url, this.objects.get(className));
                                view.addItem("data-form", this.objects.get(className));
                                req.setAttribute("data-form", view.getData());
                                System.out.println("singletons = " + this.objects.get(className));
                            } else {
                                Object data_form = Utilitaire.save(req, mapping_url, apres_contexte);
                                view.setData(new HashMap<String, Object>());
                                view.addItem("data-form", data_form);
                                req.setAttribute("data-form", view.getData());
                            }

                            RequestDispatcher dispatcher = req.getRequestDispatcher(view.getView());  
                            dispatcher.forward(req, res);
                        } else {
                            ArrayList<String> keys =  new ArrayList<String>(view.getData().keySet());
                            for(int i=0; i<keys.size(); i++) {
                                req.setAttribute(keys.get(i), view.getData().get(keys.get(i)));
                            }
                            out.println(view.getView());
                            out.println(view.getData().size());
                            RequestDispatcher dispatcher = req.getRequestDispatcher(view.getView());  
                            dispatcher.forward(req, res);                            
                        }
                            
                        
                        
                    } 
                }        
            }                
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
        processRequest(req, res);
    }

    protected void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
        processRequest(req, res);
    }
}