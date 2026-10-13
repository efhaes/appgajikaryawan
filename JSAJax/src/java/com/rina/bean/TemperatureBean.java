/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.rina.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class TemperatureBean {

    private int temp;

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getResult() {
        if (temp == 0) {
            return "";
        } else if (temp < 30) {
            return "it's cold";
        } else if (temp > 40) {
            return "it's hot";
        } else {
            return "it's normal";
        }
    }
}
