public abstract class Osoba {
    private static int liczbaOsob = 0;

    private String imie;
    private String nazwisko;
    private int rokUrodzenia;

    public Osoba(String imie, String nazwisko, int rokUrodzenia) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.rokUrodzenia = rokUrodzenia;
        liczbaOsob++;
    }

    public static int getLiczbaOsob() {
        return liczbaOsob;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public int getRokUrodzenia() {
        return rokUrodzenia;
    }

    public abstract String opisRoli();

    @Override
    public String toString() {
        return imie + " " + nazwisko + " (ur. " + rokUrodzenia + ")";
    }
}
