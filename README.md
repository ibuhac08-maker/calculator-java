# Izveštaj o statičkoj analizi koda i metrikama

## 1. Metrika: LOC (Lines of Code)
Zadatak zahteva izračunavanje ukupnog broja linija koda (zbirno za sve fajlove u projektu):

*   *Calculator.java:* 188 linija koda (prema GitHub zaglavlju: 134 loc)
*   *Start.java:* 26 linija koda (prema GitHub zaglavlju: 19 loc)
*   *Ukupno za kompletan projekat:* 214 linija (ukupno tekstualnih linija) / 153 LOC (čiste linije koda bez praznina i komentara).

---

## 2. Neformalan pregled koda i statička analiza
Zaključci su formulisani u traženom formatu: fajl – broj linije koda – zapažanje.

### Klasa: Calculator.java

*   *Calculator.java – linija 6 – zapažanje:* Korišćenje globalnog statičkog stanja (static float finalResult). Polje nije konačno (non-final), što čini kod nesigurnim za višenitni rad (thread-unsafe) jer bi paralelni pozivi prepisivali vrednosti jedan drugom.
*   *Calculator.java – linija 8 – zapažanje:* Nepotrebna unutrašnja klasa Operations za čuvanje konstanti. U modernoj Javi bolja je praksa koristiti enum ili konstante izvući direktno na vrh klase Calculator.
*   *Calculator.java – linija 18 – zapažanje:* Sintaksička greška (Compile error). Definisana je metoda public static String toString(). Java ne dozvoljava statičko skrivanje metode toString() iz klase Object koja je instancna metoda. Kompajler javlja grešku.
*   *Calculator.java – linija 24 – zapažanje:* Kršenje konvencije imenovanja. Metoda public static String Run(...) počinje velikim slovom, što je suprotno Java camelCase standardu (treba da bude run).
*   *Calculator.java – linija 31 – zapažanje:* Kritičan bug (Rizik od StringIndexOutOfBoundsException). Metoda vrši proveru prvog karaktera sa expression.charAt(0) bez prethodne provere da li je string prazan (""). Ako je string prazan, program će se srušiti.
*   *Calculator.java – linija 37 – zapažanje:* Kritičan bug (Rizik od PatternSyntaxException). Izraz expression.split("[" + Operations.toString() + "]") koristi specijalne regex karaktere (- i *) unutar uglastih zagrada bez eskejponja, što dovodi do rušenja aplikacije pri svakom splitovanju.
*   *Calculator.java – linija 41 – zapažanje:* Logička greška u petlji. Uslov i < expression.length() - 1 preskače proveru poslednjeg karaktera u prosleđenom izrazu.
*   *Calculator.java – linija 52 – zapažanje:* Potencijalni bug sa petljom. Iako nema tačke-zareza na kraju for definicije, petlja zavisi od niza numbers koji se kreira u liniji 37, a koji zbog pomenutog regex buga nikada neće biti uspešno generisan.
*   *Calculator.java – linija 63 – zapažanje:* Loša praksa hvatanja izuzetaka (catch (Exception exc)). Hvatanje opšte klase Exception skriva stvarne probleme (poput NullPointerException) i otežava debagovanje. Treba eksplicitno hvatati NumberFormatException.
*   *Calculator.java – linija 77 – zapažanje:* Logički bug. Unutar void metode calculate, naredba return; bezuslovno prekida izvršavanje koda čim uđe u if blok, sprečavajući bilo kakvu dalju logiku računanja.

### Klasa: Start.java

*   *Start.java – linije 6 i 13 – zapažanje:* Kršenje konvencije imenovanja. Varijabla Expression je napisana sa velikim početnim slovom, što stvara zabunu jer izgleda kao naziv klase.
*   *Start.java – linije 11-13 – zapažanje:* Kritičan bug (Curenje resursa i rizik od beskonačne petlje / rušenja). Kreiranje novog objekta scanIn = new Scanner(System.in); unutar while petlje je opasno. Kada korisnik unese "exit", poziva se scanIn.close() što trajno zatvara standardni ulaz (System.in) za celu aplikaciju, uzrokujući NoSuchElementException u sledećim iteracijama.
*   *Start.java – linija 19 – zapažanje:* Poziv neispravno imenovane metode Calculator.Run(Expression) koja krši camelCase standarde Jave.
