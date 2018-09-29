package ve.gob.tla.telearagua.telearagua;

import java.util.Hashtable;
import java.util.Map;

public class FormatString {
    private Hashtable<String, String> characters = new Hashtable<String, String>();

    private void fill(){
        characters = new Hashtable<String, String>();
        characters.put("\\u00c3\\u008d", "Í");
        characters.put("\\u00c3\\u0081", "Á");
        characters.put("\\u00c3\\u2030", "É");
        characters.put("\\u00c3\\u2018", "Ñ");
        characters.put("\\u00c3\\u00b1", "ñ");
        characters.put("\\u00c3\\u201c", "Ó");
        characters.put("\\u00c3\\u00ad", "í");
        characters.put("\\u00c3\\u00b3", "ó");
        characters.put("\\u00c3\\u00a1", "á");
        characters.put("\\u00c3\\u00a9", "é");
        characters.put("\\u00c3\\u00ba", "ú");
        characters.put("\\u00c3\\u00bc","ü");
        characters.put("\\u00e2\\u20ac\\u0153","“");
        characters.put("\\u00e2\\u20ac\\u009d","”");
        characters.put("\\u00c3\\u0161","Ú");
    }
    public  String format(String data){
        if (characters.isEmpty()){
            fill();
        }


        for (Map.Entry<String, String> entry : characters.entrySet()) {
            data = data.replace(entry.getKey(), entry.getValue());
        }
        return data;
    }
}
