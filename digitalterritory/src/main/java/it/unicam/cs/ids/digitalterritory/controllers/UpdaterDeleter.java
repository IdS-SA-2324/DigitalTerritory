package it.unicam.cs.ids.digitalterritory.controllers;

import it.unicam.cs.ids.digitalterritory.services.ControllerPersistenza;

import java.util.List;

public class UpdaterDeleter {

    public void save(Object object) {
        ControllerPersistenza.getInstance().insert(object);
    }

    ;

    public void delete(Object object) {
        ControllerPersistenza.getInstance().delete(object);
    }

    ;

    public boolean search(Object object) {
       return ControllerPersistenza.getInstance().search(object);
    }

}