package main;

import javax.swing.*;
import java.util.List;

public class Control {
    private ViewInterface view;
    private ModelCrono cronometro;

    private int vueltas;

    public Control() {
        vueltas = 0;
        cronometro = new ModelCrono(0,1, vueltas);
        cronometro.setControl(this);
    }

    public void start(){
        if (vueltas == 0){
            cronometro.execute();
        }else {
            cronometro = new ModelCrono(0,1, vueltas);
            cronometro.setControl(this);
            cronometro.execute();
        }
    }

    public void setView(ViewInterface view) {
        this.view = view;
    }

    public String getTime() {
        return cronometro.toString();
    }

    public void setTime(String time){
        view.bringUpToDate(time);
    }

    public void setState(State state){
        view.setState(state);
    }

    public void newLoop(){
        vueltas ++;
    }

    public boolean isDone(){
        return cronometro.isDone();
    }

    public void cancel(){
        cronometro.cancel(true);
    }
}
