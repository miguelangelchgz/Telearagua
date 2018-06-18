package ve.gob.tla.telearagua.telearagua;


import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebConection {
    public static final String url = "http://tla.gob.ve/";
    public static final int maxPages = 20;


    public static void main(String args[]) {


        String urlPage = String.format(url);
        System.out.println("Comprobando entradas de: " + urlPage);

        // Compruebo si me da un 200 al hacer la petición
        if (getStatusConnectionCode(urlPage) == 200) {

            // Obtengo el HTML de la web en un objeto Document2
            Document document = getHtmlDocument(urlPage);

            // Busco todas las historias de meneame que estan dentro de:
            Elements entradas = document.getElementsByTag("figure");
            System.out.println(entradas.size());


            // Paseo cada una de las entradas
            for (Element elem : entradas) {

                Boolean hasTitle = elem.getElementsByClass("img-responsive").hasAttr("title");


                if (hasTitle) {
                    String title = elem.getElementsByClass("img-responsive").attr("title");
                    String img_link = "http://tla.gob.ve/" + elem.getElementsByClass("img-responsive").attr("src");
                    String notice_link = "http://tla.gob.ve/noticias_nacionales.php?data=" + img_link.replace("archivos/", "");

                    System.out.println(title);
                    System.out.println(img_link);
                    System.out.println(notice_link);
                }

            }

        } else {
            System.out.println("El Status Code no es OK es: " + getStatusConnectionCode(urlPage));
        }

    }


    public static int getStatusConnectionCode(String url) {

        Response response = null;

        try {
            response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
        }
        return response.statusCode();
    }


    public static Document getHtmlDocument(String url) {

        Document doc = null;

        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
        }

        return doc;

    }
}
