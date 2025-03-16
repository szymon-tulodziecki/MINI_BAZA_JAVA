import java.io.IOException;
import java.io.RandomAccessFile;

public class Procesor {
    private String producent;
    private String model;
    private int liczbaRdzeni;
    private float czestotliwosc;
    private float cenaNetto;
    private int VAT = 23;
    private int id;

    public Procesor(String producent, String model, int liczbaRdzeni, float czestotliwosc, float cenaNetto) {
        this.producent = producent;
        this.model = model;
        this.liczbaRdzeni = liczbaRdzeni;
        this.czestotliwosc = czestotliwosc;
        this.cenaNetto = cenaNetto;
    }

    public String getProducent() {
        return producent;
    }

    public String getModel() {
        return model;
    }

    public int getLiczbaRdzeni() {
        return liczbaRdzeni;
    }

    public float getCzestotliwosc() {
        return czestotliwosc;
    }

    public float getCenaNetto() {
        return cenaNetto;
    }

    public int getVAT() {
        return VAT;
    }

    public float getCenaBrutto() {
        return Math.round(cenaNetto * (1 + VAT / 100.0f) * 100.0f) / 100.0f;
    }

    public int getId() {
        return id;
    }

    public void setProducent(String producent) {
        this.producent = producent;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setLiczbaRdzeni(int liczbaRdzeni) {
        this.liczbaRdzeni = liczbaRdzeni;
    }

    public void setCzestotliwosc(float czestotliwosc) {
        this.czestotliwosc = czestotliwosc;
    }

    public void setCenaNetto(float cenaNetto) {
        this.cenaNetto = cenaNetto;
    }

    public void setVAT(int VAT) {
        this.VAT = VAT;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Procesor readFromFile(RandomAccessFile file) throws IOException {
        String producent = file.readUTF();
        String model = file.readUTF();
        int liczbaRdzeni = file.readInt();
        float czestotliwosc = file.readFloat();
        float cenaNetto = file.readFloat();
        int VAT = file.readInt();
        int id = file.readInt();
        Procesor procesor = new Procesor(producent, model, liczbaRdzeni, czestotliwosc, cenaNetto);
        procesor.VAT = VAT;
        procesor.id = id;
        return procesor;
    }

    public void writeToFile(RandomAccessFile file) throws IOException {
        file.writeUTF(producent);
        file.writeUTF(model);
        file.writeInt(liczbaRdzeni);
        file.writeFloat(czestotliwosc);
        file.writeFloat(cenaNetto);
        file.writeInt(VAT);
        file.writeInt(id);
    }
}