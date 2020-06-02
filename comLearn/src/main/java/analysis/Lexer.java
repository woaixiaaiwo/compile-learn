package analysis;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单词法分析器，将输入字符串分析为token
 */
public class Lexer {

    public static List<Token> scan(String str){
        List<Token> result = new ArrayList<Token>();
        char[] chars = str.toCharArray();
        int i = 0;
        while(i < chars.length){
            //过滤空格，换行和制表符
            if(Character.isSpaceChar(chars[i])){
                i++;
                continue;
            }
            //判断数字
            if(Character.isDigit(chars[i]) || chars[i] == '.'){
                StringBuilder digit = new StringBuilder();
                while (i < chars.length && Character.isDigit(chars[i])){
                    digit.append(chars[i] - '0');
                    i++;
                }
                StringBuilder floatData = new StringBuilder();
                while (i < chars.length && (Character.isDigit(chars[i]) || chars[i] == '.')){
                    if(chars[i] == '.'){
                        floatData.append('.');
                    }else{
                        floatData.append(chars[i] - '0');
                    }
                    i++;
                }
                if(floatData.length() == 0){
                    result.add(new Digit(Integer.valueOf(digit.toString())));
                }else{
                    if(digit.length() == 0){
                        result.add(new Float(java.lang.Float.valueOf(floatData.toString())));
                    }else{
                        result.add(new Float(java.lang.Float.valueOf(digit.append(floatData).toString())));
                    }
                }

                if(i >= chars.length)break;
            }
            //判断字母
            if(Character.isLetter(chars[i])){
                StringBuilder sb = new StringBuilder();
                while (i < chars.length && Character.isLetter(chars[i])){
                    sb.append(chars[i]);
                    i++;
                }
                String s = sb.toString();
                if("true".equals(s)){
                    result.add(new Token(Tag.TRUE));
                }else if("false".equals(s)){
                    result.add(new Token(Tag.FALSE));
                }else {
                    result.add(new Word(s));
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(scan("sda ds 323 .5454 34345.7687 fdf true false fdfgfgdg" +
                "ddsaddsa"));
    }

}
