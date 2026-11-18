import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    private static final String[] OPCJE = {
            "Wyjscie",
            "Pokoje wg numeru",
            "Pokoje wg ceny",
            "Apartamenty z jacuzzi",
            "Goscie wg nazwiska",
            "Goscie wg roku urodzenia",
            "Dodaj goscia",
            "Dodaj rezerwacje",
            "Anuluj rezerwacje",
            "Lista rezerwacji",
            "Wszystkie osoby w hotelu",
            "Podwyzka dla pracownikow",
            "Statystyki",
            "Zmien hotel"
    };

    public static void main(String[] args) {
        Hotel[] hotele = {
                new Hotel("Hotel Tatry", "Zakopane"),
                new Hotel("Hotel Baltyk", "Gdansk")
        };
        zaladujDane(hotele);

        System.out.println("SYSTEM REZERWACJI HOTELOWYCH");
        Hotel hotel = wybierzHotel(hotele);
        boolean dziala = true;

        try {
            while (dziala) {
                wypiszMenu(hotel);
                int wybor = wczytajLiczbe("Wybierz opcje: ");
                System.out.println();
                switch (wybor) {
                    case 0:
                        dziala = false;
                        break;
                    case 1:
                        hotel.wypiszPokojePoNumerze();
                        break;
                    case 2:
                        hotel.wypiszPokojePoCenie();
                        break;
                    case 3:
                        hotel.wypiszApartamentyZJacuzzi();
                        break;
                    case 4:
                        hotel.wypiszGosciPoNazwisku();
                        break;
                    case 5:
                        hotel.wypiszGosciPoRokuUrodzenia();
                        break;
                    case 6:
                        dodajGoscia(hotel);
                        break;
                    case 7:
                        dodajRezerwacje(hotel);
                        break;
                    case 8:
                        anulujRezerwacje(hotel);
                        break;
                    case 9:
                        hotel.wypiszRezerwacje();
                        break;
                    case 10:
                        hotel.wypiszWszystkieOsoby();
                        break;
                    case 11:
                        hotel.dajPodwyzke(wczytajLiczbe("Kwota podwyzki (zl): "));
                        break;
                    case 12:
                        wypiszStatystyki(hotel);
                        break;
                    case 13:
                        hotel = wybierzHotel(hotele);
                        break;
                    default:
                        System.out.println("Nie ma takiej opcji.");
                }
            }
        } finally {
            scanner.close();
            System.out.println("Dziekujemy za skorzystanie z systemu.");
        }
    }

    private static void zaladujDane(Hotel[] hotele) {
        hotele[0].dodajPokoj(new PokojStandardowy(101, 2, 200, true));
        hotele[0].dodajPokoj(new PokojStandardowy(102, 1, 150, false));
        hotele[0].dodajPokoj(new Apartament(201, 4, 400, 2, true));
        hotele[1].dodajPokoj(new PokojStandardowy(11, 2, 180, false));
        hotele[1].dodajPokoj(new Apartament(21, 3, 350, 3, false));
        hotele[1].dodajPokoj(new Apartament(22, 2, 300, 1, true));

        hotele[0].dodajPracownika(new Pracownik("Anna", "Lis", 1985, "Recepcjonistka", 5200));
        hotele[0].dodajPracownika(new Pracownik("Marek", "Wolny", 1979, "Kierownik", 8500));
        hotele[1].dodajPracownika(new Pracownik("Ewa", "Sowa", 1992, "Recepcjonistka", 5000));

        try {
            hotele[0].dodajGoscia(new Gosc("Jan", "Kowalski", 1990, "90010112345"));
            hotele[0].dodajGoscia(new Gosc("Ola", "Nowak", 1975, "75050598765"));
            hotele[0].dodajGoscia(new Gosc("Piotr", "Adamski", 2001, "01210154321"));
            hotele[1].dodajGoscia(new Gosc("Kasia", "Zielinska", 1988, "88030311111"));
            hotele[1].dodajGoscia(new Gosc("Tomasz", "Baran", 1995, "95121222222"));

            hotele[0].dodajRezerwacje("90010112345", 101, 3, Wyzywienie.SNIADANIE);
            hotele[0].dodajRezerwacje("01210154321", 201, 2, Wyzywienie.PELNE);
            hotele[1].dodajRezerwacje("88030311111", 21, 5, Wyzywienie.BRAK);
        } catch (BladRezerwacjiException e) {
            System.out.println("Blad danych startowych: " + e.getMessage());
        }
    }

    private static Hotel wybierzHotel(Hotel[] hotele) {
        System.out.println("\nDostepne hotele:");
        for (int i = 0; i < hotele.length; i++) {
            System.out.println((i + 1) + ". " + hotele[i]);
        }
        int nr;
        do {
            nr = wczytajLiczbe("Podaj numer hotelu: ");
        } while (nr < 1 || nr > hotele.length);
        System.out.println("Wybrano: " + hotele[nr - 1].getNazwa());
        return hotele[nr - 1];
    }

    private static void wypiszMenu(Hotel hotel) {
        System.out.println("\n"+ hotel.getNazwa());
        for (int i = 1; i < OPCJE.length; i++) {
            System.out.println(i + ". " + OPCJE[i]);
        }
        System.out.println("0. " + OPCJE[0]);
    }

    private static int wczytajLiczbe(String komunikat) {
        while (true) {
            System.out.print(komunikat);
            try {
                int liczba = scanner.nextInt();
                scanner.nextLine();
                return liczba;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("To nie jest liczba calkowita, sprobuj ponownie.");
            }
        }
    }

    private static String wczytajTekst(String komunikat) {
        System.out.print(komunikat);
        return scanner.nextLine();
    }

    private static Wyzywienie wybierzWyzywienie() {
        int wybor;
        do {
            wybor = wczytajLiczbe("Wyzywienie (1 - brak, 2 - sniadanie, 3 - pelne): ");
        } while (wybor < 1 || wybor > 3);

        if (wybor == 2) {
            return Wyzywienie.SNIADANIE;
        } else if (wybor == 3) {
            return Wyzywienie.PELNE;
        }
        return Wyzywienie.BRAK;
    }

    private static void dodajGoscia(Hotel hotel) {
        String imie = wczytajTekst("Imie: ");
        String nazwisko = wczytajTekst("Nazwisko: ");
        int rok = wczytajLiczbe("Rok urodzenia: ");
        String pesel = wczytajTekst("PESEL: ");
        try {
            hotel.dodajGoscia(new Gosc(imie, nazwisko, rok, pesel));
            System.out.println("Dodano goscia.");
        } catch (BladRezerwacjiException e) {
            System.out.println("Blad: " + e.getMessage());
        }
    }

    private static void dodajRezerwacje(Hotel hotel) {
        String pesel = wczytajTekst("PESEL goscia: ");
        int numer = wczytajLiczbe("Numer pokoju: ");
        int noce = wczytajLiczbe("Liczba nocy: ");
        Wyzywienie wyzywienie = wybierzWyzywienie();
        try {
            Rezerwacja r = hotel.dodajRezerwacje(pesel, numer, noce, wyzywienie);
            System.out.println("Utworzono rezerwacje:\n" + r);
        } catch (BladRezerwacjiException e) {
            System.out.println("Blad: " + e.getMessage());
        }
    }

    private static void anulujRezerwacje(Hotel hotel) {
        int id = wczytajLiczbe("Numer rezerwacji do anulowania: ");
        try {
            hotel.anulujRezerwacje(id);
            System.out.println("Rezerwacja #" + id + " zostala anulowana.");
        } catch (BladRezerwacjiException e) {
            System.out.println("Blad: " + e.getMessage());
        }
    }

    private static void wypiszStatystyki(Hotel hotel) {
        System.out.println(hotel);
        System.out.println("Liczba utworzonych osob: " + Osoba.getLiczbaOsob());
        System.out.println("Liczba utworzonych rezerwacji: " + Rezerwacja.getLiczbaUtworzonych());
        System.out.printf("Przychod z aktywnych rezerwacji: %.2f zl%n", hotel.przychod());
    }
}
