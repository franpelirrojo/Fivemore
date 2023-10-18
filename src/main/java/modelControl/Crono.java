package modelControl;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Crono {
    private LocalTime tiempo;
    private int horas;
    private int minutos;

    public Crono(int horas, int minutos) {
        this.horas = horas;
        this.minutos = minutos;
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

    public int getHoras() {
        return horas;
    }

    public int getMinutos() {
        return minutos;
    }
}
