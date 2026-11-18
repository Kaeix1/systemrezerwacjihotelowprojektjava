import java.util.Comparator;

public class PokojCenaComparator implements Comparator<Pokoj> {
    @Override
    public int compare(Pokoj p1, Pokoj p2) {
        Double cena1 = p1.cenaZaNoc();
        return cena1.compareTo(p2.cenaZaNoc());
    }
}
