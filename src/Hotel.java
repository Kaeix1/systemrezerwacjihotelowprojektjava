import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Hotel {
    private String nazwa;
    private String miasto;
    private HashSet<Gosc> goscie = new HashSet<>();
    private ArrayList<Pokoj> pokoje = new ArrayList<>();
    private ArrayList<Pracownik> pracownicy = new ArrayList<>();
    private HashMap<Integer, Rezerwacja> rezerwacje = new HashMap<>();
    private GoscRokUrodzeniaComparator komparatorGosci = new GoscRokUrodzeniaComparator();
    private PokojCenaComparator komparatorPokoi = new PokojCenaComparator();

    public Hotel(String nazwa, String miasto) {
        this.nazwa = nazwa;
        this.miasto = miasto;
    }

    public String getNazwa() {
        return nazwa;
    }

    public static <T extends Comparable<T>> void sortujIWypisz(T[] tablica) {
        Arrays.sort(tablica);
        for (T element : tablica) {
            System.out.println(element);
        }
    }

    public void dodajPokoj(Pokoj pokoj) {
        pokoje.add(pokoj);
    }

    public void dodajPracownika(Pracownik pracownik) {
        pracownicy.add(pracownik);
    }

    public void dodajGoscia(Gosc gosc) throws BladRezerwacjiException {
        int rok = gosc.getRokUrodzenia();
        if (rok < 1900 || rok > LocalDate.now().getYear()) {
            throw new BladRezerwacjiException("Niepoprawny rok urodzenia: " + rok);
        }
        if (gosc.getPesel().length() != 11) {
            throw new BladRezerwacjiException("PESEL musi miec 11 znakow.");
        }
        if (!goscie.add(gosc)) {
            throw new BladRezerwacjiException("Gosc o numerze PESEL " + gosc.getPesel() + " juz istnieje.");
        }
    }

    public Gosc znajdzGoscia(String pesel) throws BladRezerwacjiException {
        for (Gosc g : goscie) {
            if (g.getPesel().equals(pesel)) {
                return g;
            }
        }
        throw new BladRezerwacjiException("Nie znaleziono goscia o numerze PESEL " + pesel);
    }

    public Pokoj znajdzPokoj(int numer) throws BladRezerwacjiException {
        for (Pokoj p : pokoje) {
            if (p.getNumer() == numer) {
                return p;
            }
        }
        throw new BladRezerwacjiException("Nie ma pokoju o numerze " + numer);
    }

    private boolean czyPokojWolny(Pokoj pokoj) {
        for (Map.Entry<Integer, Rezerwacja> wpis : rezerwacje.entrySet()) {
            Rezerwacja r = wpis.getValue();
            if (r.getStatus() == StatusRezerwacji.AKTYWNA && r.getPokoj().getNumer() == pokoj.getNumer()) {
                return false;
            }
        }
        return true;
    }

    public Rezerwacja dodajRezerwacje(String pesel, int numerPokoju, int noce, Wyzywienie wyzywienie)
            throws BladRezerwacjiException {
        if (noce <= 0) {
            throw new BladRezerwacjiException("Liczba nocy musi byc wieksza od 0.");
        }
        Gosc gosc = znajdzGoscia(pesel);
        Pokoj pokoj = znajdzPokoj(numerPokoju);
        if (!czyPokojWolny(pokoj)) {
            throw new BladRezerwacjiException("Pokoj nr " + numerPokoju + " jest juz zarezerwowany.");
        }
        for (Rezerwacja r : rezerwacje.values()) {
            if (r.getStatus() == StatusRezerwacji.AKTYWNA && r.getGosc().equals(gosc)) {
                throw new BladRezerwacjiException("Ten gosc ma juz aktywna rezerwacje (#" + r.getId() + ").");
            }
        }
        Rezerwacja rezerwacja = new Rezerwacja(gosc, pokoj, noce, wyzywienie);
        rezerwacje.put(rezerwacja.getId(), rezerwacja);
        return rezerwacja;
    }

    public void anulujRezerwacje(int id) throws BladRezerwacjiException {
        Rezerwacja r = rezerwacje.get(id);
        if (r == null) {
            throw new BladRezerwacjiException("Brak rezerwacji o numerze " + id);
        }
        if (r.getStatus() == StatusRezerwacji.ANULOWANA) {
            throw new BladRezerwacjiException("Rezerwacja #" + id + " jest juz anulowana.");
        }
        r.anuluj();
    }

    public void wypiszRezerwacje() {
        if (rezerwacje.isEmpty()) {
            System.out.println("Brak rezerwacji.");
        }
        for (Map.Entry<Integer, Rezerwacja> wpis : rezerwacje.entrySet()) {
            System.out.println(wpis.getValue());
        }
    }

    private Pokoj[] pokojeJakoTablica() {
        Pokoj[] tablica = new Pokoj[pokoje.size()];
        for (int i = 0; i < pokoje.size(); i++) {
            tablica[i] = pokoje.get(i);
        }
        return tablica;
    }

    private Gosc[] gosciJakoTablica() {
        Gosc[] tablica = new Gosc[goscie.size()];
        int i = 0;
        for (Gosc g : goscie) {
            tablica[i] = g;
            i++;
        }
        return tablica;
    }

    public void wypiszPokojePoNumerze() {
        sortujIWypisz(pokojeJakoTablica());
    }

    public void wypiszPokojePoCenie() {
        Pokoj[] tablica = pokojeJakoTablica();
        Arrays.sort(tablica, komparatorPokoi);
        for (Pokoj p : tablica) {
            System.out.println(p);
        }
    }

    public void wypiszGosciPoNazwisku() {
        sortujIWypisz(gosciJakoTablica());
    }

    public void wypiszGosciPoRokuUrodzenia() {
        Gosc[] tablica = gosciJakoTablica();
        Arrays.sort(tablica, komparatorGosci);
        for (Gosc g : tablica) {
            System.out.println(g);
        }
    }

    private Osoba[] wszystkieOsoby() {
        Osoba[] osoby = new Osoba[goscie.size() + pracownicy.size()];
        int i = 0;
        for (Gosc g : goscie) {
            osoby[i] = g; // upcasting
            i++;
        }
        for (Pracownik p : pracownicy) {
            osoby[i] = p;
            i++;
        }
        return osoby;
    }

    public void wypiszWszystkieOsoby() {
        for (Osoba o : wszystkieOsoby()) {
            System.out.println(o.opisRoli()  + o);
        }
    }

    public void dajPodwyzke(int kwota) {
        for (Osoba o : wszystkieOsoby()) {
            if (o instanceof Pracownik) {
                Pracownik p = (Pracownik) o; // downcasting
                p.podwyzka(kwota);
                System.out.println(p);
            }
        }
    }

    public void wypiszApartamentyZJacuzzi() {
        boolean znaleziono = false;
        for (Pokoj p : pokoje) {
            if (p instanceof Apartament && ((Apartament) p).isMaJacuzzi()) {
                System.out.println(p);
                znaleziono = true;
            }
        }
        if (!znaleziono) {
            System.out.println("Brak apartamentow z jacuzzi. (lipa)");
        }
    }

    public double przychod() {
        double suma = 0;
        for (Rezerwacja r : rezerwacje.values()) {
            if (r.getStatus() == StatusRezerwacji.AKTYWNA) {
                suma += r.kosztBrutto();
            }
        }
        return suma;
    }

    @Override
    public String toString() {
        return nazwa + " (" + miasto + ")  pokoi: " + pokoje.size() + ", gosci: " + goscie.size()
                + ", pracownikow: " + pracownicy.size();
    }
}
