public class Font {
    private Integer fontSize;
    private String fontType;
    private static String[] fontList, fontSystem;
    private static String currentFont;
    private static Integer currentFontSize;

    public Font(String fontType, Integer fontSize){
        this.fontSize = fontSize;
        this.fontType = fontType;
    }

    public void changeFont(String fontType, Integer fontSize){
        currentFont = fontType;
        currentFontSize = fontSize;
    }

}
