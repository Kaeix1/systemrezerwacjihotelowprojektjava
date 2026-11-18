public interface Platny {
    double VAT = 0.08;

    double kosztNetto();

    default double kosztBrutto() {
        return kosztNetto() * (1 + VAT);
    }
}
