package view;

import modelControl.ControlCrono;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JLabel lbCronometo;
    private JButton btnIniciar;
    private JButton btnSeguir;
    private JButton btnCancelar;
    private ControlCrono controlCrono;

    private JPanel contador;
    private JPanel botones;

    //Estados de la vista
    public enum Estado {PREPARACION, CONCENTRACION, DESCANSO, CANCELAR}
    private Estado estado;

    public MainFrame() {
        super("Pomodoro");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        setEstado(Estado.PREPARACION);

        //Marcamos la lógica de cada botón y del estado de la vista en un unico listener
        ActionListener mainListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object fuente = e.getSource();

                if (fuente.equals(btnIniciar)){ //Comenzar a concentrarse
                    controlCrono.execute();
                    setEstado(Estado.CONCENTRACION);
                }else if (fuente.equals(btnCancelar)){
                    if (!controlCrono.isDone()){ //Si no ha terminado lo terminamos
                        controlCrono.cancel(true);
                    }
                    //setEstado(Estado.CANCELAR);
                }
            }
        };
        btnIniciar.addActionListener(mainListener);
        btnCancelar.addActionListener(mainListener);
        btnSeguir.addActionListener(mainListener);

        add(contador, BorderLayout.CENTER);
        add(botones, BorderLayout.PAGE_END);
        pack();
    }

    private void inicializarComponentes(){
        //Inicializamos todos los componentes y los distribuimos
        //Estado.PREPARACIÓN
        contador = new JPanel();
        botones = new JPanel();

        lbCronometo = new JLabel("00:05:00");
        lbCronometo.setFont(new Font("Saint", Font.PLAIN, 32));
        contador.add(lbCronometo);
        btnIniciar = new JButton("Empezar a concentrarme");
        btnCancelar = new JButton("Dejar de concentrarme");
        btnCancelar.setEnabled(false);
        btnSeguir = new JButton("Seguimos!");
        btnSeguir.setEnabled(false);

        botones.add(btnIniciar);
        botones.add(btnSeguir);
        botones.add(btnCancelar);
        this.controlCrono = new ControlCrono(this);
    }

    public void setEstado(Estado estado){
        switch (estado){
            case PREPARACION -> inicializarComponentes();
            case CONCENTRACION -> {
                btnIniciar.setEnabled(false);
                btnCancelar.setEnabled(true);
                btnSeguir.setEnabled(false);
            }
            case DESCANSO -> btnSeguir.setEnabled(true);
            case CANCELAR -> {
                btnCancelar.setEnabled(false);
                btnIniciar.setEnabled(true);
                lbCronometo.setText("00:05:00");
            }
            default -> throw new IllegalStateException("Unexpected value: " + estado);
        }

        this.estado = estado;
    }

    //El controlador accede desde aquí al conometro
    public void setCrono(String tiempo){
        lbCronometo.setText(tiempo);
    }
}
