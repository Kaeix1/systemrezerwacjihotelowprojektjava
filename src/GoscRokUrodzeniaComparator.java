import java.util.Comparator;

public class GoscRokUrodzeniaComparator implements Comparator<Gosc> {
    @Override
    public int compare(Gosc g1, Gosc g2) {
        return g1.getRokUrodzenia() - g2.getRokUrodzenia();
    }
}
