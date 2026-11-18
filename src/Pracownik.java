public class Pracownik extends Osoba {
    private String stanowisko;
    private double pensja;

    public Pracownik(String imie, String nazwisko, int rokUrodzenia, String stanowisko, double pensja) {
        super(imie, nazwisko, rokUrodzenia);
        this.stanowisko = stanowisko;
        this.pensja = pensja;
    }

    public void podwyzka(int kwota) {
        pensja += kwota;
    }

    @Override
    public String opisRoli() {
        return "Pracownik - " + stanowisko;
    }

    @Override
    public String toString() {
        return "Pracownik: " + super.toString() + ", stanowisko: " + stanowisko + ", pensja: " + pensja + " zl";
    }
}
