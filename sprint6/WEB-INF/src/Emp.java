package models;

import java.util.ArrayList;
import etu1809.annotation.*;
import etu1809.framework.ModelView;
import etu1809.framework.Mapping;
import java.util.*;

public class Emp {
    Integer id;
    String nom;
    String prenom;
    Integer age;
    Double salaire;

    public Emp() {

    }

    public Emp(Integer id, String nom, String prenom, Integer age, Double salaire) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.salaire = salaire;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    @Model(url = "/Emp/findall")
    public ModelView findall() {
        ModelView view  = new ModelView();
        view.setView("/emp-findall.jsp");
        ArrayList<Emp> employes = new ArrayList<Emp>();
        Emp a = new Emp(1, "Employe 1", "Mahefa", 22, 200.5);
        Emp b = new Emp(2, "Employe 2", "Mau", 18, 205.5);
        Emp c = new Emp(3, "Employe 3", "Aina", 19, 250.5);
        employes.add(a);
        employes.add(b);
        employes.add(c);
        view.addItem("list-emp",employes);


        return view;
    }
}