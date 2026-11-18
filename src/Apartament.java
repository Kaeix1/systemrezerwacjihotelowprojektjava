public class Apartament extends Pokoj {
    private int liczbaPokoi;
    private boolean maJacuzzi;

    public Apartament(int numer, int liczbaMiejsc, double cenaBazowa, int liczbaPokoi, boolean maJacuzzi) {
        super(numer, liczbaMiejsc, cenaBazowa);
        this.liczbaPokoi = liczbaPokoi;
        this.maJacuzzi = maJacuzzi;
    }

    public boolean isMaJacuzzi() {
        return maJacuzzi;
    }

    @Override
    public double cenaZaNoc() {
        double cena = getCenaBazowa() + 100 * (liczbaPokoi - 1);
        if (maJacuzzi) {
            cena += 150;
        }
        return cena;
    }

    @Override
    public String toString() {
        return "[Apartament] " + super.toString() + ", pokoi: " + liczbaPokoi + (maJacuzzi ? ", jacuzzi" : "");
    }
}
