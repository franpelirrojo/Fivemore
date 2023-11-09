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
        btnCerrar.setFocusPainted(false);
        btnCerrar.setForeground(new Color(240,240,240));
        btnCerrar.setBackground(new Color(189,34,0));

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
