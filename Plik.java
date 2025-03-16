import java.io.*;

public class Plik {
    private boolean bazaOtwarta = false;
    private String nazwaOtwartejBazy;
    private File otwartyPlik;

    public boolean isBazaOtwarta() {
        return bazaOtwarta;
    }

    public void setBazaOtwarta(boolean bazaOtwarta) {
        this.bazaOtwarta = bazaOtwarta;
    }

    public String getNazwaOtwartejBazy() {
        return nazwaOtwartejBazy;
    }

    public void setNazwaOtwartejBazy(String nazwaOtwartejBazy) {
        this.nazwaOtwartejBazy = nazwaOtwartejBazy;
    }

    public File getOtwartyPlik() {
        return otwartyPlik;
    }

    public void setOtwartyPlik(File otwartyPlik) {
        this.otwartyPlik = otwartyPlik;
    }
}