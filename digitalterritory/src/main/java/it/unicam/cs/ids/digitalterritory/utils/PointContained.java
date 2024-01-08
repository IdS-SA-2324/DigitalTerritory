package it.unicam.cs.ids.digitalterritory.utils;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PointContained {
    private Coordinate coordinate;
    private ArrayList<Double> polygonX= new ArrayList<>();
    private ArrayList<Double> polygonY= new ArrayList<>();

    public PointContained(Coordinate coordinate, ArrayList<ArrayList<Double>> listVertici) {
        this.coordinate = coordinate;

        for (int i = 0; i < listVertici.size(); i++) {

            polygonX.add(listVertici.get(i).get(0)); //componeti X corrispondenti alle LONGITUDINI
            polygonY.add(listVertici.get(i).get(1)); //componenti Y corrispondenti alle LATITUDINI

        }

    }


    //implementazione dell'algoritmo di ray casting: tracciando un raggio arbitrario dal punto in questione (Coordinate),
    //se tale raggio interseca il perimetro del poligono composto dagli arraylist appaiati polygonX e polygonY
    //per un numero dispari di volte, il punto è contenuto del poligono, altrimenti no
    //TODO ordinare gli array per aumentare l'efficacia dell'algoritmo
    public boolean isPointContained() {
        int n = this.polygonX.size();
        boolean isInside = false;

        for (int i = 0, j = n - 1; i < n; j = i++) {
            if ((this.polygonY.get(i) > this.coordinate.latitude()) != (this.polygonY.get(j) > this.coordinate.latitude()) &&
                    (this.coordinate.longitude() < (this.polygonX.get(j) - polygonX.get(i)) * (this.coordinate.latitude() - this.polygonY.get(i)) / (this.polygonY.get(j) - this.polygonY.get(i)) + polygonX.get(i))) {
                isInside = true;
            }
        }

        return isInside;
    }

    public void getLongitude(){
        this.polygonX.forEach((x) -> System.out.println(x));
    }

    public void getLatitude(){
        this.polygonY.forEach((y) -> System.out.println(y));
    }
}