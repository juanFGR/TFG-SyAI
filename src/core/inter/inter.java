package core.inter;

import java.util.Locale;
import java.util.ResourceBundle;

public class  inter {
   
public static Locale locale  = new Locale(System.getProperty("user.country"),System.getProperty("user.language"));
public static   ResourceBundle texts = ResourceBundle.getBundle("core.inter.Language_ES", locale);
    
}
