package models;

import etu1809.annotation.*;
import etu1809.framework.ModelView;

public class Dept {
    @Model(url = "/Dept/find")
    public ModelView findall() {
        ModelView view  =  new ModelView("dept-findall.jsp");
        return view;
    }

    public void insert() {

    }
}
