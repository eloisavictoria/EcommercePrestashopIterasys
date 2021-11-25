package util;

public class Funcoes {

    public static Double removeCifraoDevolveDouble(String texto){

       texto = texto.replace("$",""); //tirando o $ do valor do produto

        return  Double.parseDouble(texto);//converter a string em número

    }

    public static int removeTextoItensDevolveInt(String texto){
        texto = texto.replace(" items", "");

        return Integer.parseInt(texto);

    }

    public static String removeTexto(String texto, String textoParaRemover){
       texto = texto.replace(textoParaRemover, "");
        return  texto;
    }
}
