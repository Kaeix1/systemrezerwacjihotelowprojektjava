public class Rezerwacja implements Platny {
    private static int nastepneId = 1;

    private int id;
    private Gosc gosc;
    private Pokoj pokoj;
    private int liczbaNocy;
    private Wyzywienie wyzywienie;
    private StatusRezerwacji status;

    public Rezerwacja(Gosc gosc, Pokoj pokoj, int liczbaNocy, Wyzywienie wyzywienie) {
        this.id = nastepneId++;
        this.gosc = gosc;
        this.pokoj = pokoj;
        this.liczbaNocy = liczbaNocy;
        this.wyzywienie = wyzywienie;
        this.status = StatusRezerwacji.AKTYWNA;
    }

    public static int getLiczbaUtworzonych() {
        return nastepneId - 1;
    }

    public int getId() {
        return id;
    }

    public Gosc getGosc() {
        return gosc;
    }

    public Pokoj getPokoj() {
        return pokoj;
    }

    public StatusRezerwacji getStatus() {
        return status;
    }

    public void anuluj() {
        status = StatusRezerwacji.ANULOWANA;
    }

    private double doplataZaNoc() {
        switch (wyzywienie) {
            case SNIADANIE:
                return 40;
            case PELNE:
                return 120;
            default:
                return 0;
        }
    }

    @Override
    public double kosztNetto() {
        return (pokoj.cenaZaNoc() + doplataZaNoc()) * liczbaNocy;
    }

    @Override
    public String toString() {
        return "Rezerwacja #" + id + " [" + status + "]\n  " + gosc + "\n  " + pokoj
                + "\n  Nocy: " + liczbaNocy + ", wyzywienie: " + wyzywienie
                + ", do zaplaty (brutto): " + Math.round(kosztBrutto() * 100) / 100.0 + " zl";
    }
}
