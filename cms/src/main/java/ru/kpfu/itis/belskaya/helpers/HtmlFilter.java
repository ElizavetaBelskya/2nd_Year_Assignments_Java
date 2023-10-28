package ru.kpfu.itis.belskaya.helpers;

/**
 * @author Elizaveta Belskaya
 */
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;


public class HtmlFilter {
    public static String filterHtml(String html) {
        String cleanHtml = Jsoup.clean(html, Safelist.basic());
        return cleanHtml;
    }
}

