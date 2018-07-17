package ve.gob.tla.telearagua.telearagua;

import java.util.Hashtable;
import java.util.Map;

public class FormatString {
    public static String format(String data){
        Hashtable<String, String> caracteres = new Hashtable<String, String>();
        caracteres.put("\\u00c3\\u008d", "Í");
        caracteres.put("\\u00c3\\u0081", "Á");
        caracteres.put("\\u00c3\\u2030", "É");
        caracteres.put("\\u00c3\\u2018", "Ñ");
        caracteres.put("\\u00c3\\u00b1", "ñ");
        caracteres.put("\\u00c3\\u201c", "Ó");
        caracteres.put("\\u00c3\\u00ad", "í");
        caracteres.put("\\u00c3\\u00b3", "ó");
        caracteres.put("\\u00c3\\u00a1", "á");
        caracteres.put("\\u00c3\\u00a9", "é");
        caracteres.put("\\u00c3\\u00ba", "ú");
        caracteres.put("\\u00c3\\u00bc","ü");
        caracteres.put("\\u00e2\\u20ac\\u0153","“");
        caracteres.put("\\u00e2\\u20ac\\u009d","”");
        for (Map.Entry<String, String> entry : caracteres.entrySet()) {
            data = data.replace(entry.getKey(), entry.getValue());
        }
        return data;
    }
}
