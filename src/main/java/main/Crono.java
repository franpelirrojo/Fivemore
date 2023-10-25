package main;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Crono {
    private LocalTime tiempo;
    private int horas;
    private int minutos;
    private int vueltas;

    public Crono(int horas, int minutos, int vueltas) {
        int multi = 0;
        if (vueltas > 1){
            multi = vueltas * 2;
        }

        this.horas = horas * multi;
        this.minutos = minutos  * multi;

        tiempo = LocalTime.of(horas, minutos);
    }

    public void minusSeconds(long seconds) {
        tiempo = tiempo.minusSeconds(seconds);
    }

    public boolean isFinish() {
        LocalTime o = LocalTime.of(0,0);
        return tiempo.getHour() == o.getHour()
                && tiempo.getMinute() == o.getMinute()
                && tiempo.getSecond() == 0;
    }

    @Override
    public String toString() {
        return tiempo.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setVueltas() {
        vueltas = vueltas + 1;
    }
}
