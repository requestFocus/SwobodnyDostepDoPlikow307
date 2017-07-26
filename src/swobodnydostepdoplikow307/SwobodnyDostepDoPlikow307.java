
package swobodnydostepdoplikow307;

import java.io.*;

public class SwobodnyDostepDoPlikow307 {

    public static void main(String[] args) {

        Towar[] towar = new Towar[3];
        
        towar[0] = new Towar();
        towar[1] = new Towar(29.0, "videokurs java");
        towar[2] = new Towar(39.0, "videokurs c#", 2017, 4, 3);
        
        try { /* 307
            DataOutputStream outS = new DataOutputStream(new FileOutputStream("nowy.txt"));
            outS.writeDouble(12962.122);
            outS.close();
            DataInputStream inS = new DataInputStream(new FileInputStream("nowy.txt"));
            System.out.println(inS.readDouble());
            inS.close();
                */
            /*
            RandomAccessFile RAF307 = new RandomAccessFile("nowy.txt", "rw");
            RAF307.writeDouble(123.42);
            RAF307.writeDouble(41.23);
            RAF307.writeChars("lalal  ");
            System.out.println(RAF307.getFilePointer());
            RAF307.seek(8);
            System.out.println(RAF307.readDouble());
            RAF307.close();
              307      */ 
            
            RandomAccessFile RAF = new RandomAccessFile("baza.txt", "rw");
            Towar.zapiszDoPliku(towar, RAF);
            RAF.seek(0);
            
            Towar[] towarki = Towar.odczytajZPliku(RAF);
            for (int i = 0; i < towarki.length; i++) {
                System.out.println(towarki[i].pobierzCene());
                System.out.println(towarki[i].pobierzNazwe());
                System.out.println(towarki[i].pobierzDate());
                System.out.println("=====");
            }
            
            try {                                               // jeśli dowolna z linii zwróci błąd to dalsza część się nie wykona i pójdzie catch
                Towar dowolnyRekord = new Towar();
                dowolnyRekord.czytajRekord(RAF, 2);
                System.out.println(dowolnyRekord);
                System.out.println("testowy wpis po wypisaniu rekordu");
            } 
            catch (BrakRekordu err) {
                System.out.println(err.getMessage());
            }
            
            /* proba użycia metody czytajDane dla dowolnego rekordu
            int n = 2;
            RAF.seek((n-1) * Towar.DLUGOSC_REKORDU);
            Towar a = new Towar();
            a.czytajDane(RAF);   
            System.out.println(a);
            */
            
            /* proba uzycia metody pobierzCene()
            RAF.seek(160);                            // po zapisie pointer jest na koncu pliku, trzeba wrocic na start lub konkretne miejsce
            Towar a = new Towar();                  // proba odczytania
            a.czytajDane(RAF);                      // wczytanie pierwszej linii z uzyciem metody czytajDane()
            System.out.println(a.pobierzCene());
            */
            
            RAF.close();
            
            
}
        catch (IOException e) {
            System.out.println(e.getMessage());    
        }
        
    }
    
}
