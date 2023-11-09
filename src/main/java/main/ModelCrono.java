package main;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ModelCrono extends SwingWorker<Void, String> {
    private Control control;
    private LocalTime tiempo;

    public ModelCrono(int vuelta) {
        int horas = 0;
        int minutos = 1;

        if (vuelta > 0){
            int multi = vuelta * 2;

            minutos = minutos * multi;

            if (minutos > 60){
                horas = minutos%60;
                minutos = minutos - (horas * 60);
            }
        }

        tiempo = LocalTime.of(horas, minutos);
    }

    @Override
    protected Void doInBackground() throws Exception {
        boolean fin = false;
        while(!fin){
            publish(toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {throw new RuntimeException(e);}
            tiempo = tiempo.minusSeconds(1);

            if (isFinish()){
                publish(toString());
                fin = true;
            }
        }

        return null;
    }

    @Override
    protected void process(List<String> tiempos) {
        for (String tiempo : tiempos){
            control.setTime(tiempo.toString());
        }
    }

    @Override
    protected void done() {
        if (isCancelled()) {
            control.setState(State.CANCELAR);
        } else {
            control.beep();
            control.newLoop();
        }
    }

    private boolean isFinish() {
        LocalTime o = LocalTime.of(0,0);
        return tiempo.getHour() == o.getHour()
                && tiempo.getMinute() == o.getMinute()
                && tiempo.getSecond() == 0;
    }

    public String toString() {
        return tiempo.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setControl(Control control) {
        this.control = control;
    }
}
