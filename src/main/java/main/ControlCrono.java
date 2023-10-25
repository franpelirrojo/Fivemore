package main;

import javax.swing.*;
import java.util.List;

public class ControlCrono extends SwingWorker<Void, Crono> {
    private Crono cronometro;
    private MainFrame view;

    public ControlCrono(MainFrame view, Crono cronometro) {
        this.cronometro = cronometro;
        this.view = view;
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
            cronometro.setVueltas();

            view.setCrono(cronometro.toString());
            view.setEstado(MainFrame.Estado.DESCANSO);
        }
    }
}
