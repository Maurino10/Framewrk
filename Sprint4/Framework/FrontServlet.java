package etu1809.framework.servlet;

import java.io.*;
import java.lang.reflect.Method;
import javax.servlet.*; 
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import java.util.*;
import etu1809.framework.*;

public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mapping_url = null;

    @Override
    public void init() throws ServletException {
        super.init();
         try {
            ServletContext ctx = getServletContext() ;
            String path = ctx.getRealPath("/WEB-INF/classes/models");    
            this.mapping_url = Utilitaire.getHashMap(path);            
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
    }

    protected void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
        processRequest(req, res);
    }

    protected void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
        processRequest(req, res);
    }
}