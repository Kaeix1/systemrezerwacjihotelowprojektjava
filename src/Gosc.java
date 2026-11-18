import java.util.Objects;

public class Gosc extends Osoba implements Comparable<Gosc> {
    private String pesel;

    public Gosc(String imie, String nazwisko, int rokUrodzenia, String pesel) {
        super(imie, nazwisko, rokUrodzenia);
        this.pesel = pesel;
    }

    public String getPesel() {
        return pesel;
    }

    @Override
    public String opisRoli() {
        return "Gosc hotelowy";
    }

    @Override
    public int compareTo(Gosc o) {
        return getNazwisko().compareTo(o.getNazwisko());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gosc gosc = (Gosc) o;
        return Objects.equals(pesel, gosc.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pesel);
    }

    @Override
    public String toString() {
        return "Gosc: " + super.toString() + ", PESEL: " + pesel;
    }
}
