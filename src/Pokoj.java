public abstract class Pokoj implements Comparable<Pokoj> {
    private int numer;
    private int liczbaMiejsc;
    private double cenaBazowa;

    public Pokoj(int numer, int liczbaMiejsc, double cenaBazowa) {
        this.numer = numer;
        this.liczbaMiejsc = liczbaMiejsc;
        this.cenaBazowa = cenaBazowa;
    }

    public int getNumer() {
        return numer;
    }

    protected double getCenaBazowa() {
        return cenaBazowa;
    }

    public abstract double cenaZaNoc();

    @Override
    public int compareTo(Pokoj o) {
        return this.numer - o.numer;
    }

    @Override
    public String toString() {
        return "Pokoj nr " + numer + ", miejsc: " + liczbaMiejsc + ", cena za noc: " + cenaZaNoc() + " zl";
    }
}
