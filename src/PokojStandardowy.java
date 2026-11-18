public class PokojStandardowy extends Pokoj {
    private boolean maBalkon;

    public PokojStandardowy(int numer, int liczbaMiejsc, double cenaBazowa, boolean maBalkon) {
        super(numer, liczbaMiejsc, cenaBazowa);
        this.maBalkon = maBalkon;
    }

    @Override
    public double cenaZaNoc() {
        return maBalkon ? getCenaBazowa() + 30 : getCenaBazowa();
    }

    @Override
    public String toString() {
        return "[Standard] " + super.toString() + (maBalkon ? ", z balkonem" : "");
    }
}
