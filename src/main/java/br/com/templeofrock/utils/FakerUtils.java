package br.com.templeofrock.utils;

import br.com.templeofrock.models.Band;
import br.com.templeofrock.models.Disc;
import br.com.templeofrock.models.User;
import com.github.javafaker.Faker;

import java.util.*;

public class FakerUtils {

    private static final Faker faker = new Faker();

    public static String generateFakeMembers(int qty){
        List<String> members = new ArrayList<>();
        for(int i=0; i<qty; i++){
            members.add(faker.name().fullName());
        }
        return String.join(", ", members);
    }

    public static List<Disc> generateFakeDiscography(int formationYear, int total){
        List<Disc> discs = new ArrayList<>();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        for(int i=0; i < total; i++){
            String title = faker.rockBand().name() + " Album";
            int year = faker.number().numberBetween(formationYear, currentYear);
            discs.add(new Disc(title, year));
        }

        discs.sort(Comparator.comparing(Disc::getReleaseYear));
        return discs;
    }

    public static User generateFakeUser(){
        return new User(
                faker.name().username(),
                faker.internet().emailAddress(),
                faker.internet().password()
        );
    }

    private static final List<String> musicGenres = Arrays.asList(
            "Hard Rock", "Heavy Metal", "Punk", "Blues", "Classic Rock",
            "Symphonic Metal", "Progressive Metal", "Vicking Metal", "Black Metal"
    );

    public static String getRandomGenre(){
        return musicGenres.get(new Random().nextInt(musicGenres.size()));
    }

    public static Band generateFakeBand(){
        return new Band(
                faker.rockBand().name(),
                getRandomGenre(),
                generateFakeMembers(4),
                faker.number().numberBetween(1970, 2020),
                faker.country().name()
        );
    }
}
