
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    static Plik baza = new Plik();
    static List<Procesor> aktualne_procesory = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    
    //--------------------------------------------------------------------------------
    public static void czyscEkran() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------
    public static void menu() {
        int opcja;
        
        while (true) {
            System.out.println("==========MINIBAZA==========");
            System.out.println("1. Otworz baze danych");
            System.out.println("2. Utworz nowa baze");
            System.out.println("3. Przeglad bazy");
            System.out.println("4. Sortowanie bazy");
            System.out.println("5. Usun baze");
            System.out.println("6. Zakoncz program");
            System.out.println("Wybierz opcje: ");

            if (sc.hasNextInt()) {
                opcja = sc.nextInt();
                sc.nextLine(); 
                switch (opcja) {
                    case 1:
                        otworzBaze();
                        break;
                    case 2:
                        czyscEkran();
                        utworzBaze();
                        break;
                    case 3:
                        czyscEkran();
                        przegladBazy();
                        break;
                    case 4:
                        czyscEkran();
                        sortowanieBazy();
                        break;
                    case 5:
                        czyscEkran();
                        usunBaze();
                        break;
                    case 6:
                        czyscEkran();
                        System.out.println("Zakoncz program");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Nie ma takiej opcji");
                        break;
                }
            } else {
                System.out.println("Błędna wartość");
                sc.next();
            }
        }
    }
    //--------------------------------------------------------------------------------
    public static void utworzBaze() {
        if (!baza.isBazaOtwarta()) {
            if (czyZamknacBaze()) {
                System.out.print("Podaj nazwe nowej bazy (bazaxy.dat): ");
                String nowaNazwaBazy = sc.nextLine();
        
                if (!sprawdzNazweBazy(nowaNazwaBazy)) {
                    System.out.println("Nazwa bazy jest niepoprawna.");
                    return;
                }
        
                File plik = new File(nowaNazwaBazy);
                if (plik.exists()) {
                    System.out.println("Plik o tej nazwie już istnieje.");
                    return;
                }
        
                try (RandomAccessFile raf = new RandomAccessFile(plik, "rw")) {
                    baza.setBazaOtwarta(true);
                    baza.setNazwaOtwartejBazy(nowaNazwaBazy);
                    baza.setOtwartyPlik(plik); 
                    
                    aktualne_procesory.add(new Procesor("Intel", "Core i9", 8, 3.6f, 2000.00f));
                    aktualne_procesory.add(new Procesor("AMD", "Ryzen 9", 12, 3.8f, 1800.00f));
                    aktualne_procesory.add(new Procesor("Intel", "Core i7", 6, 3.4f, 1500.00f));
                    aktualne_procesory.add(new Procesor("AMD", "Ryzen 7", 8, 3.6f, 1300.00f));
                    aktualne_procesory.add(new Procesor("Intel", "Core i5", 4, 3.0f, 1000.00f));
                
                    aktualizujIdProcesorow();
            
                    for (Procesor procesor : aktualne_procesory) {
                        raf.writeUTF(procesor.getProducent());
                        raf.writeUTF(procesor.getModel());
                        raf.writeInt(procesor.getLiczbaRdzeni());
                        raf.writeFloat(procesor.getCzestotliwosc());
                        raf.writeFloat(procesor.getCenaNetto());
                        raf.writeInt(procesor.getVAT());
                        raf.writeInt(procesor.getId());
                    }
            
                    System.out.println("Nowa baza danych zostala utworzona: " + nowaNazwaBazy);
                } catch (IOException e) {
                    System.out.println("Blad tworzenia pliku minibazy.");
                }
            } else {
                System.out.println("Baza jest już otwarta");
                return;
            }
            przegladBazy();
        }
    }
    //--------------------------------------------------------------------------------
    public static void otworzBaze() {
        if (czyZamknacBaze()) {
            System.out.print("Podaj nazwe bazy do otwarcia: ");
            String nazwaBazy = sc.nextLine();

            if (!sprawdzNazweBazy(nazwaBazy)) {
                System.out.println("Nazwa bazy jest niepoprawna.");
                return;
            }

            File plik = new File(nazwaBazy);
            if (!plik.exists()) {
                System.out.println("Plik o tej nazwie nie istnieje.");
                return;
            }

            try (RandomAccessFile raf = new RandomAccessFile(plik, "rw")) {
                baza.setBazaOtwarta(true);
                baza.setOtwartyPlik(plik);
                baza.setNazwaOtwartejBazy(nazwaBazy);

                aktualne_procesory.clear();
                while (raf.getFilePointer() < raf.length()) {
                    Procesor procesor = Procesor.readFromFile(raf);
                    aktualne_procesory.add(procesor);
                }

                aktualizujIdProcesorow(); 

                System.out.println("Baza danych " + nazwaBazy + " zostala otwarta.");
                przegladBazy();
            } catch (IOException e) {
                System.out.println("Blad otwierania pliku minibazy.");
            }
        }
    }
    //--------------------------------------------------------------------------------
    public static boolean czyZamknacBaze() {
        if (baza.isBazaOtwarta()) {
            System.out.println("Czy chcesz zamknac baze danych? (t/n)");
            String odpowiedz = sc.nextLine();
            if (odpowiedz.equals("t")) {
                baza.setBazaOtwarta(false);
                baza.setNazwaOtwartejBazy(null);
                baza.setOtwartyPlik(null);
                System.out.println("Baza danych zostala zamknięta.");
                return true;
            }
            return false;
        }
        return true; 
    }
    //--------------------------------------------------------------------------------
    public static void przegladBazy() {
        List<Procesor> pomocnicza = new ArrayList<>(aktualne_procesory);

        if (!baza.isBazaOtwarta() || baza.getOtwartyPlik() == null) {
            System.out.println("Zadna baza nie jest otwarta.");
            return;
        }
        
        try (RandomAccessFile raf = new RandomAccessFile(baza.getOtwartyPlik(), "rw")) {
            pomocnicza.clear();
            while (raf.getFilePointer() < raf.length()) {
                Procesor procesor = Procesor.readFromFile(raf);
                pomocnicza.add(procesor);
            }
        } catch (IOException e) {
            System.out.println("Blad odczytu pliku minibazy.");
            return;
        }

        int pozycja = 0;
        while (true) {
            czyscEkran();
            if (pozycja >= 0 && pozycja < pomocnicza.size()) {
                Procesor procesor = pomocnicza.get(pozycja);
                System.out.println("========== Rekord " + (pozycja + 1) + " / " + pomocnicza.size() + " ==========");
                System.out.println("Producent: " + procesor.getProducent());
                System.out.println("Model: " + procesor.getModel());
                System.out.println("Liczba Rdzeni: " + procesor.getLiczbaRdzeni());
                System.out.println("Czestotliwosc: " + procesor.getCzestotliwosc() + " GHz");
                System.out.println("Cena Netto: " + procesor.getCenaNetto() + " PLN");
                System.out.println("VAT: " + procesor.getVAT() + " %");
                System.out.println("Cena Brutto: " + procesor.getCenaBrutto() + " PLN");
                System.out.println("ID: " + procesor.getId());
                System.out.println("========================================");
            } else {
                System.out.println("Brak rekordów do wyświetlenia.");
            }
    
            System.out.println("Nacisnij klawisz:");
            System.out.println("Y - Poprzedni");
            System.out.println("B - Nastepny");
            System.out.println("G - Poczatek");
            System.out.println("H - Koniec");
            System.out.println("D - Dopisz nowa strukture");
            System.out.println("U - Usun biezaca strukture");
            System.out.println("M - Modyfikuj biezaca strukture");
            System.out.println("Q - Wyjdz");
    
            char ch = sc.nextLine().charAt(0);
    
            switch (ch) {
                case 'Y': case 'y':
                    if (pozycja > 0) pozycja--;
                    break;
                case 'B': case 'b':
                    if (pozycja < pomocnicza.size() - 1) pozycja++;
                    break;
                case 'G': case 'g':
                    pozycja = 0;
                    break;
                case 'H': case 'h':
                    pozycja = pomocnicza.size() - 1;
                    break;
                case 'D': case 'd':
                    dopiszStrukture(pomocnicza, pozycja + 1);  
                    break;
                case 'U': case 'u':
                    if (pozycja >= 0 && pozycja < pomocnicza.size()) {
                        pomocnicza.remove(pozycja);
                        aktualizujIdProcesorow(pomocnicza);  
                        if (pozycja > 0) pozycja--;
                    }
                    break;
                case 'M': case 'm':
                    if (pozycja >= 0 && pozycja < pomocnicza.size()) {
                        modyfikujStrukture(pomocnicza, pozycja);
                    }
                    break;
                case 'Q': case 'q':
                    if (czyZapisac()) {
                        aktualne_procesory = new ArrayList<>(pomocnicza);
                        zapiszZmiany();
                    }
                    return;
                default:
                    System.out.println("Niepoprawna opcja.");
                    break;
            }
        }
    }
    //--------------------------------------------------------------------------------
    public static void dopiszStrukture(List<Procesor> lista, int index) {
        System.out.println("Podaj dane nowego procesora:");
    
        String producent;
        while (true) {
            System.out.print("Producent: ");
            producent = sc.nextLine();
            if (!producent.isEmpty()) {
                break;
            }
            System.out.println("Producent nie moze byc pusty.");
        }
    
        String model;
        while (true) {
            System.out.print("Model: ");
            model = sc.nextLine();
            if (!model.isEmpty()) {
                break;
            }
            System.out.println("Model nie moze byc pusty.");
        }
    
        int liczbaRdzeni;
        while (true) {
            System.out.print("Liczba Rdzeni: ");
            if (sc.hasNextInt()) {
                liczbaRdzeni = sc.nextInt();
                sc.nextLine(); 
                break;
            } else {
                System.out.println("Niepoprawny format. Podaj liczbe calkowita.");
                sc.nextLine(); 
            }
        }
    
        float czestotliwosc;
        while (true) {
            System.out.print("Czestotliwosc (GHz): ");
            if (sc.hasNextFloat()) {
                czestotliwosc = sc.nextFloat();
                sc.nextLine(); 
                break;
            } else {
                System.out.println("Niepoprawny format. Podaj liczbe zmiennoprzecinkowa.");
                sc.nextLine(); 
            }
        }
    
        float cenaNetto;
        while (true) {
            System.out.print("Cena Netto (PLN): ");
            if (sc.hasNextFloat()) {
                cenaNetto = sc.nextFloat();
                sc.nextLine(); 
                break;
            } else {
                System.out.println("Niepoprawny format. Podaj liczbe zmiennoprzecinkowa.");
                sc.nextLine(); 
            }
        }
    
        Procesor nowyProcesor = new Procesor(producent, model, liczbaRdzeni, czestotliwosc, cenaNetto);
    
        if (index >= 0 && index <= lista.size()) {
            lista.add(index, nowyProcesor);
            aktualizujIdProcesorow(lista);
        }
    }
    //--------------------------------------------------------------------------------
    public static void aktualizujIdProcesorow(List<Procesor> lista) {
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).setId(i + 1);
        }
    }
    //--------------------------------------------------------------------------------
    public static boolean sprawdzNazweBazy(String nazwaBazy) {
        if (nazwaBazy.length() != 10) {
            return false;
        }

        String lan1 = nazwaBazy.substring(0, 4);
        String lan2 = nazwaBazy.substring(4, 6);
        String lan3 = nazwaBazy.substring(6);

        if (!lan1.equals("baza")) {
            return false;
        }

        if (!lan2.matches("\\d{2}")) {
            return false;
        }

        if (!lan3.equals(".dat")) {
            return false;
        }

        return true;
    }
    //--------------------------------------------------------------------------------
    public static void modyfikujStrukture(List<Procesor> lista, int pozycja) {
        if (!baza.isBazaOtwarta() || baza.getOtwartyPlik() == null) {
            System.out.println("Brak otwartej bazy danych.");
            return;
        }
    
        if (pozycja < 0 || pozycja >= lista.size()) {
            System.out.println("Niepoprawna pozycja.");
            return;
        }
    
        Procesor procesor = lista.get(pozycja);
    
        int x = 10, y = 5;
        char wybor;
        do {
            czyscEkran();
            wyswietlRekord(procesor, x, y);
            gotoxy(x, y + 6);
            System.out.print("Podaj nr pola (lub ESC aby anulowac, 7 aby wyjsc): ");
    
            wybor = sc.nextLine().charAt(0);
            if (wybor == 27) {
                break;
            }
            gotoxy(x, y + 8);
            switch (wybor) {
                case '1':
                    System.out.print("Podaj nowego producenta: ");
                    procesor.setProducent(sc.nextLine());
                    break;
                case '2':
                    System.out.print("Podaj nowy model: ");
                    procesor.setModel(sc.nextLine());
                    break;
                case '3':
                    System.out.print("Podaj nowa liczbe rdzeni: ");
                    procesor.setLiczbaRdzeni(sc.nextInt());
                    sc.nextLine(); 
                    break;
                case '4':
                    System.out.print("Podaj nowa czestotliwosc: ");
                    procesor.setCzestotliwosc(sc.nextFloat());
                    sc.nextLine(); 
                    break;
                case '5':
                    System.out.print("Podaj nowy VAT: ");
                    procesor.setVAT(sc.nextInt());
                    sc.nextLine(); 
                    break;
                case '7':
                    wybor = 27;
                    break;
                default:
                    System.out.println("Niepoprawny numer pola.");
                    break;
            }
    
            lista.set(pozycja, procesor);
    
            try (RandomAccessFile raf = new RandomAccessFile(baza.getOtwartyPlik(), "rw")) {
                raf.seek(0);
                for (Procesor p : lista) {
                    p.writeToFile(raf);
                }
            } catch (IOException e) {
                System.out.println("Blad modyfikacji pliku minibazy.");
            }
    
        } while (wybor != 27);
    
        gotoxy(x, y + 10);
        System.out.println("Struktura zostala zmodyfikowana.");
    }
    //--------------------------------------------------------------------------------
    public static void gotoxy(int x, int y) {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            
        } else {
            System.out.print(String.format("\033[%d;%dH", y + 1, x + 1));
        }
    }
    //--------------------------------------------------------------------------------

public static void wyswietlRekord(Procesor procesor, int x, int y) {
    gotoxy(x, y);
    System.out.println("1. Producent: " + procesor.getProducent());
    gotoxy(x, y + 1);
    System.out.println("2. Model: " + procesor.getModel());
    gotoxy(x, y + 2);
    System.out.println("3. Liczba Rdzeni: " + procesor.getLiczbaRdzeni());
    gotoxy(x, y + 3);
    System.out.println("4. Czestotliwosc: " + procesor.getCzestotliwosc() + " GHz");
    gotoxy(x, y + 4);
    System.out.println("5. Cena Netto: " + procesor.getCenaNetto() + " PLN");
    gotoxy(x, y + 5);
    System.out.println("6. VAT: " + procesor.getVAT() + " %");
    gotoxy(x, y + 6);
    System.out.println("7. Cena Brutto: " + procesor.getCenaBrutto() + " PLN");
    gotoxy(x, y + 7);
    System.out.println("8. ID: " + procesor.getId());
}

//--------------------------------------------------------------------------------
public static boolean czyZapisac() {
    System.out.println("Czy chcesz zapisac zmiany? (t/n)");
    String odpowiedz = sc.nextLine();
    return odpowiedz.equalsIgnoreCase("t");
}

public static void zapiszZmiany() {
    if (!baza.isBazaOtwarta() || baza.getOtwartyPlik() == null) {
        System.out.println("Brak otwartej bazy danych.");
        return;
    }

    try (RandomAccessFile raf = new RandomAccessFile(baza.getOtwartyPlik(), "rw")) {
        raf.setLength(0); 
        for (Procesor procesor : aktualne_procesory) {
            procesor.writeToFile(raf);
        }
        System.out.println("Zmiany zostaly zapisane.");
    } catch (IOException e) {
        System.out.println("Blad zapisu do pliku minibazy.");
    }
} 

//--------------------------------------------------------------------------------
public static void aktualizujIdProcesorow() {
    for (int i = 0; i < aktualne_procesory.size(); i++) {
        aktualne_procesory.get(i).setId(i + 1);
    }
}

//--------------------------------------------------------------------------------
public static void usunBaze() {
    if (czyZamknacBaze()) {
        System.out.print("Podaj nazwe bazy do usuniecia: ");
        String nazwaBazy = sc.nextLine();

        if (!sprawdzNazweBazy(nazwaBazy)) {
            czyscEkran();
            System.out.println("Nazwa bazy jest niepoprawna.");
            return;
        }

        File plik = new File(nazwaBazy);
        if (!plik.exists()) {
            czyscEkran();
            System.out.println("Plik o tej nazwie nie istnieje.");
            return;
        }

        if (plik.delete()) {
            czyscEkran();
            System.out.println("Plik minibazy " + nazwaBazy + " zostal usuniety.");
        } else {
            czyscEkran();
            System.out.println("Blad usuwania pliku minibazy " + nazwaBazy + ".");
        }
    }
}

//--------------------------------------------------------------------------------
public static void sortowanieBazy() {
    List<Procesor> pomocnicza = new ArrayList<>(aktualne_procesory);

    if (!baza.isBazaOtwarta() || baza.getOtwartyPlik() == null) {
        System.out.println("Żadna baza nie jest otwarta.");
        return;
    }

    System.out.println("Wybierz opcję sortowania:");
    System.out.println("1. Sortuj po ID");
    System.out.println("2. Sortuj po nazwie producenta");
    System.out.println("3. Sortuj po cenie");
    int opcja = sc.nextInt();
    sc.nextLine();  

    boolean rosnaco = true;
    if (opcja == 1 || opcja == 2 || opcja == 3) {
        System.out.println("Wybierz kierunek sortowania:");
        System.out.println("1. Rosnąco");
        System.out.println("2. Malejąco");
        int kierunek = sc.nextInt();
        sc.nextLine(); 
        rosnaco = kierunek == 1;
    }

    switch (opcja) {
        case 1:
            if (rosnaco) {
                pomocnicza.sort(Comparator.comparingInt(Procesor::getId));
            } else {
                pomocnicza.sort(Comparator.comparingInt(Procesor::getId).reversed());
            }
            break;
        case 2:
            if (rosnaco) {
                pomocnicza.sort(Comparator.comparing(Procesor::getProducent));
            } else {
                pomocnicza.sort(Comparator.comparing(Procesor::getProducent).reversed());
            }
            break;
        case 3:
            if (rosnaco) {
                pomocnicza.sort(Comparator.comparingDouble(Procesor::getCenaNetto));
            } else {
                pomocnicza.sort(Comparator.comparingDouble(Procesor::getCenaNetto).reversed());
            }
            break;
        default:
            System.out.println("Niepoprawna opcja.");
            return;
    }

    pomocnicza.forEach(procesor -> {
        System.out.println("==========");
        System.out.println("Producent: " + procesor.getProducent());
        System.out.println("Model: " + procesor.getModel());
        System.out.println("Liczba Rdzeni: " + procesor.getLiczbaRdzeni());
        System.out.println("Czestotliwosc: " + procesor.getCzestotliwosc() + " GHz");
        System.out.println("Cena Netto: " + procesor.getCenaNetto() + " PLN");
        System.out.println("VAT: " + procesor.getVAT() + " %");
        System.out.println("Cena Brutto: " + procesor.getCenaBrutto() + " PLN");
        System.out.println("ID: " + procesor.getId());
    });

    if (czyZapisac()) {
        aktualne_procesory = new ArrayList<>(pomocnicza);
        zapiszZmiany();
    }
}

//--------------------------------------------------------------------------------
public static void main(String[] args) {
    menu();
}
}