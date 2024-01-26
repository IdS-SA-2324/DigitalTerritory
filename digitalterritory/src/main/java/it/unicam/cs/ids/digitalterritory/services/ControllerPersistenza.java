package it.unicam.cs.ids.digitalterritory.services;

import java.util.ArrayList;
import java.util.List;

public class ControllerPersistenza {

    private List<Object> objects;
    private static ControllerPersistenza instance;

    private ControllerPersistenza(){
        this.objects=new ArrayList<>();
    }
    public void insert(Object object){
        this.objects.add(object);
    }
    public void delete(Object object){
        this.objects.remove(object);
    }

    public boolean search(Object object){
    if(this.objects.contains(object)){return true;}
    return false;
    }

    public static ControllerPersistenza getInstance() {

        if (instance == null) {
            instance = new ControllerPersistenza();
        }

        return instance;
    }



}
