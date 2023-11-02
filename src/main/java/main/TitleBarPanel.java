package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleBarPanel extends JPanel {
    private CloseInterface closeInterface;
    private JButton btnCerrar;

    public TitleBarPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        btnCerrar = new JButton("X");
        btnCerrar.setBackground(Color.RED);

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeInterface.close();
            }
        });

        add(btnCerrar);
    }

    public void setCloseInterface(CloseInterface closeInterface) {
        this.closeInterface = closeInterface;
    }
}
