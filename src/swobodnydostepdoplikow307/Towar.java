
package swobodnydostepdoplikow307;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class Towar {
    
    public Towar() {                                                // dla przypadku braku danych, inicjuje pola i czas dodania
        this.cena = 0.0;
        this.nazwa = " ";
        this.dataWydania = new GregorianCalendar().getTime();
    }

    public Towar(double cena, String nazwa) {                       // dla przypadku podanej ceny i nazwy itemu, czas z pierwszego konstruktora
        this();
        this.cena = cena;
        this.nazwa = nazwa;
    }

    public Towar(double cena, String nazwa, int rok, int miesiac, int dzien) {      // dla przypadku podanej ceny i nazwy itemu oraz czasu wstecz
        this(cena, nazwa);
        GregorianCalendar kalendarz = new GregorianCalendar(rok, miesiac - 1, dzien);       // Constructs a GregorianCalendar with the given date set in the default time zone with the default locale.
        dataWydania = kalendarz.getTime();                              // ustawia zmienna dataWydania na DZIEN_TYG MIESIAC XY 00:00:00 CEST ROK
    }
    
    public double pobierzCene() {
        return this.cena;
    }
    
    public String pobierzNazwe() {
        return this.nazwa;
    }
    
    public Date pobierzDate() {
        return dataWydania;
    }
    
    public void ustawCene(double cena) {
        this.cena = cena;
    }
    
    public void ustawNazwe(String nazwa) {
        this.nazwa = nazwa;
    }
    
    public void ustawDate(int r, int m, int dz) {
        GregorianCalendar kalendarz = new GregorianCalendar(r, m - 1, dz);
        this.dataWydania = kalendarz.getTime();
    }
    
    public void ustawDate(Date data) {
        this.dataWydania = data;
    }
    
    public String toString() {                                              // tekstowa reprezentacja obiektu klasy
        GregorianCalendar kalendarz = new GregorianCalendar();
        kalendarz.setTime(this.pobierzDate());
        return this.cena + " zł; nazwa: " + this.nazwa + ", " + kalendarz.get(Calendar.YEAR) + " rok, " + (kalendarz.get(Calendar.MONTH)+1) + " miesiąc, " + kalendarz.get(Calendar.DAY_OF_MONTH) + " dzień"; 
    }

    public static void zapiszDoPliku(Towar[] towar, DataOutput outS) throws IOException {
        for (int i = 0; i < towar.length; i++) {
            towar[i].zapiszDane(outS);
        }
    }
    
    public static Towar[] odczytajZPliku(RandomAccessFile RAF) throws IOException {       // exception z powodu readLine() bedącego metodą BufferedReader
        
        int iloscRekordow = (int) (RAF.length() / DLUGOSC_REKORDU);                // dlugosc calego pliku w bajtach przez dlugosc jednego wpisu w bajtach daje w wyniku ilosc wpisow w pliku
        Towar[] towar = new Towar[iloscRekordow];
        
        for (int i = 0; i < iloscRekordow; i++) {
            towar[i] = new Towar();                             // dlatego, że tablica towar jest inicjowana nullami typu obiektowego Towar
            towar[i].czytajDane(RAF);
        }
        return towar;
    }
    
    public void zapiszDane(DataOutput outS) throws IOException {
        outS.writeDouble(this.cena);
        
        StringBuffer stringB = new StringBuffer(DLUGOSC_NAZWY);
        stringB.append(this.nazwa);                                          // wpisuje nazwe od pozycji 0
        stringB.setLength(Towar.DLUGOSC_NAZWY);                               // stała długość nazwy, 60 bajtów, static final
        outS.writeChars(stringB.toString());                            // stringB to obiekt typu StringBuffer, nie String
        
        GregorianCalendar kalendarz = new GregorianCalendar();
        kalendarz.setTime(this.dataWydania);
        outS.writeInt(kalendarz.get(Calendar.YEAR));
        outS.writeInt(kalendarz.get(Calendar.MONTH)+1);
        outS.writeInt(kalendarz.get(Calendar.DAY_OF_MONTH));
     }
    
    public void czytajDane(DataInput inS) throws IOException {
        this.cena = inS.readDouble();
        
        StringBuffer tempString = new StringBuffer(Towar.DLUGOSC_NAZWY);
        for (int i = 0; i < Towar.DLUGOSC_NAZWY; i++) {
            char tempChar = inS.readChar();
            if (tempChar != '\0')                           // to dlatego, bo StringBuffer uzupelnia Stringa znakami konca linii \0
                tempString.append(tempChar);                // petla musi przeleciec DLUGOSC_NAZWY-razy, żeby pointer równiez ustawil sie na koncu Stringa
        }
        this.nazwa = tempString.toString();
        
        int rok = inS.readInt();
        int miesiac = inS.readInt();
        int dzien = inS.readInt();
        GregorianCalendar kalenCalendar = new GregorianCalendar(rok, miesiac-1, dzien);
        this.dataWydania = kalenCalendar.getTime();
    }
    
    public void czytajRekord(RandomAccessFile RAF, int n) throws IOException, BrakRekordu {
        if (n <= (RAF.length() / Towar.DLUGOSC_REKORDU)) {                     // sprawdzamy czy siedzimy w pliku
            RAF.seek((n-1) * Towar.DLUGOSC_REKORDU); 
            this.czytajDane(RAF);
        }
        else
            throw new BrakRekordu("nie ma takiego rekordu");
    }
            
    public static final int DLUGOSC_NAZWY = 30;
    public static final int DLUGOSC_REKORDU = ((Double.SIZE + (Character.SIZE * DLUGOSC_NAZWY) + 3 * (Integer.SIZE)))/8;
    private double cena;        // 8 bajtow                                // automatycznie inicjuje na 0.0 
    private String nazwa;       // DLUGOSC_NAZWY * 2 bajty                 // automatycznie inicjuje na spacje, aby brak wpisu tworzyl poprawny widok pod tokeny
    private Date dataWydania;   // 4 bajty + 4 bajty + 4 bajty
    
}
