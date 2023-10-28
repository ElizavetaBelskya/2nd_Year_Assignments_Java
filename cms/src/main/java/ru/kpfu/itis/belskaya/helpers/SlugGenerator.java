package ru.kpfu.itis.belskaya.helpers;

/**
 * @author Elizaveta Belskaya
 */
import java.text.Normalizer;

public class SlugGenerator {
    public static String generateSlug(String title) {
        String slug = title.trim();
        slug = slug.replaceAll("\\s+", "-");
        slug = slug.toLowerCase();
        slug = transliterate(slug);
        slug = slug.replaceAll("[^\\w\\-]", "");
        slug = slug.replaceAll("-{2,}", "-");
        return slug;
    }

    private static String transliterate(String input) {
        String[][] russian = {
                {"а", "a"}, {"б", "b"}, {"в", "v"}, {"г", "g"}, {"д", "d"}, {"е", "e"}, {"ё", "yo"},
                {"ж", "zh"}, {"з", "z"}, {"и", "i"}, {"й", "y"}, {"к", "k"}, {"л", "l"}, {"м", "m"},
                {"н", "n"}, {"о", "o"}, {"п", "p"}, {"р", "r"}, {"с", "s"}, {"т", "t"}, {"у", "u"},
                {"ф", "f"}, {"х", "h"}, {"ц", "c"}, {"ч", "ch"}, {"ш", "sh"}, {"щ", "sch"}, {"ъ", ""},
                {"ы", "y"}, {"ь", ""}, {"э", "e"}, {"ю", "yu"}, {"я", "ya"}};
        for (String[] pair : russian) {
            input = input.replaceAll(pair[0], pair[1]);
        }

        return input;
    }


}
