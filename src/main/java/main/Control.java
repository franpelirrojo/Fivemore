package main;

import java.awt.*;

public class Control {
    private ViewInterface view;
    private ModelCrono cronometro;

    private int vueltas;

    public Control() {
        vueltas = 0;
        cronometro = new ModelCrono(vueltas);
        cronometro.setControl(this);
    }

    public void start(){
            cronometro.execute();
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
        setState(State.DESCANSO);
        vueltas ++;
        cronometro = new ModelCrono(vueltas);
        cronometro.setControl(this);
        setTime(cronometro.toString());
    }

    public boolean isDone(){
        return cronometro.isDone();
    }

    public void cancel(){
        cronometro.cancel(true);
        vueltas = 0;
        cronometro = new ModelCrono(vueltas);
        cronometro.setControl(this);
        setTime(cronometro.toString());
    }

    public void beep(){
        Toolkit.getDefaultToolkit().beep();
    }
}
