package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame implements ViewInterface{
    private JLabel lbCronometo;
    private JButton btnIniciar;
    private JButton btnSeguir;
    private JButton btnCancelar;

    private JPanel contador;
    private JPanel botones;
    private TitleBarPanel titleBarPanel;

    private Control control;

    private final int defaultX;
    private final int defaultY;

    public MainFrame() {
        super("Fivemore");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setState(State.PREPARACION);
        setUndecorated(true);

        //Marcamos la lógica de cada botón en un unico listener
        ActionListener mainListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object fuente = e.getSource();

                if (fuente.equals(btnIniciar)){ //Comenzar a concentrarse
                    control.start();
                    setState(State.CONCENTRACION);
                }else if (fuente.equals(btnCancelar)){
                    if (!control.isDone()){ //Si no ha terminado lo terminamos
                        control.cancel();
                    }
                    setState(State.CANCELAR);
                } else if (fuente.equals(btnSeguir)) {
                    control.start();
                    setState(State.CONCENTRACION);
                }
            }
        };
        btnIniciar.addActionListener(mainListener);
        btnCancelar.addActionListener(mainListener);
        btnSeguir.addActionListener(mainListener);

        add(titleBarPanel, BorderLayout.NORTH);
        add(contador, BorderLayout.CENTER);
        add(botones, BorderLayout.PAGE_END);
        pack();

        //Tras pack() que renderiza y ajusta las medidas de los componentes y la ventana
        //de acuerdo con el layout. La colocamos donde queremos, ya que tenemos las
        //dimensiones reales que usamos junto las limites de ventana del sistema
        //para ajustar nuestra ventana.
        Dimension medidas = getSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        defaultX = winSize.width - medidas.width;
        defaultY = winSize.height - medidas.height;
        setLocation(defaultX, defaultY);

        //La diferencia de tiempos no es muy grande por la velocidad de la jvm, pero
        //el pack() ya crea los componentes y los coloca antes de ejecutarse en el
        //proceso de ajuste de medidas. Está preparado para finalmente visualizarla.
        setVisible(true);
    }

    private void inicializarComponentes(){
        //Inicializamos todos los componentes y los distribuimos
        //se ejecuta desde Estado.PREPARACIÓN
        contador = new JPanel();
        botones = new JPanel();
        titleBarPanel = new TitleBarPanel();
        control = new Control();
        control.setView(this);

        titleBarPanel.setCloseInterface(new CloseInterface() {
            @Override
            public void close() {
                dispose();
            }
        });

        lbCronometo = new JLabel(control.getTime());
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
    }

    @Override
    public void bringUpToDate(String time) {
        lbCronometo.setText(time);
    }

    @Override
    public void setState(State state) {
        switch (state){
            case PREPARACION -> inicializarComponentes();
            case CONCENTRACION -> {
                btnIniciar.setEnabled(false);
                btnCancelar.setEnabled(true);
                btnSeguir.setEnabled(false);
            }
            case DESCANSO -> btnSeguir.setEnabled(true);
            case CANCELAR -> {
                btnCancelar.setEnabled(false);
                btnSeguir.setEnabled(false);
                btnIniciar.setEnabled(true);
            }
            default -> throw new IllegalStateException("Unexpected value: " + state);
        }
    }
}
