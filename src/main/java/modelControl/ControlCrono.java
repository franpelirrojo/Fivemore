package modelControl;

import view.MainFrame;

import javax.swing.*;
import java.util.List;

public class ControlCrono extends SwingWorker<Void, Crono> {
    private int vueltas;
    private Crono cronometro;
    private MainFrame view;

    public ControlCrono(MainFrame view) {
        cronometro = new Crono(0, 1);
        this.view = view;
        vueltas = 1;
    }

    @Override
    protected Void doInBackground() throws Exception {
        boolean fin = false;
        while(!fin){
            publish(cronometro);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {throw new RuntimeException(e);}
            cronometro.minusSeconds(1);

            if (cronometro.isFinish()){
                publish(cronometro);
                fin = true;
            }
        }

        return null;
    }

    @Override
    protected void process(List<Crono> tiempos) {
        for (Crono tiempo : tiempos){
            view.setCrono(tiempo.toString());
        }
    }

    @Override
    protected void done() {
        if (isCancelled()){
            view.setEstado(MainFrame.Estado.CANCELAR);
        }else{
            vueltas ++;
            if (vueltas > 1){
                int multi = vueltas * 2;
                int horas = cronometro.getHoras() * multi;
                int minutos = cronometro.getMinutos() * multi;
                cronometro = new Crono(horas, minutos);
            }

            view.setCrono(cronometro.toString());
        }
    }
}
