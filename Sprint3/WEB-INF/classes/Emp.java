package models;

import etu1809.annotation.*;

public class Emp {
    public Emp() {}
    
    @Model(url  = "emp-all")
    public void findAll() {}

    @Model(url  = "emp-detail")
    public void findById() {}
}
