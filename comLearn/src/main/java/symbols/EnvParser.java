package symbols;

import sun.misc.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析语句块，翻译内部标识符
 *
 * {int x;char y; {bool y;x;y;} x; y;}
 * 翻译为：
 * {{x:int;y:bool}x:int;y:char;}
 *
 * 文法：
 *
 * program ->                   {top = null;}
 *           block
 *
 * block -> '{'                 {saved = top;
 *                               top = new Env(top);
 *                              print('{')}
 *          decls stmts '}'     {top = saved;
 *                               print('}');}
 * decls -> decls decl | null
 *
 * decl -> type id;             {s = new Symbol;
 *                               s.type = type.lexeme;
 *                               top.put(id.lexeme,s)}
 *
 * stmts -> stmts stmt | null;
 *
 * stmt -> block
 *          | factor;           {print(';')}
 *
 * factor -> id;                {s = top.get(id.lexeme);
 *                               print(s.lexeme);
 *                               print(":");
 *                               print(s.type);}
 *
 */
public class EnvParser {

    private Env top = null;

    private char[] chars;

    private int lookahead = 1;

    public void program(String str){
        chars = str.toCharArray();
        block();
    }

    /**
     * 匹配定义
     */
    public void decls(String type,String id){
        Symbol symbol = new Symbol(id,type);
        top.put(id,symbol);
    }

    /**
     * 匹配语句
     */
    public void stmts(String stmt){
        factor(stmt);
        System.out.print(";");
    }

    /**
     * 匹配块
     */
    public void block(){
        while(lookahead < chars.length){
            top = new Env(top);
            System.out.print('{');
            StringBuilder sb = new StringBuilder();
            String type = null,lexeme = null;
            for(;lookahead<chars.length;lookahead++){
                if(Character.isSpaceChar(chars[lookahead])){
                    if(sb.length() > 0){
                        type = sb.toString();
                        sb  = new StringBuilder();
                    }
                    continue;
                }
                if(chars[lookahead] == ';'){
                    //说明是定义
                    lexeme = sb.toString();
                    if(type != null){
                        decls(type,lexeme);
                        type = null;
                    }else{
                        stmts(lexeme);
                    }
                    lexeme = null;
                    sb = new StringBuilder();
                    continue;
                }
                if(chars[lookahead] == '{'){
                    break;
                }
                if(chars[lookahead] == '}'){
                    top = top.getPrev();
                    System.out.print('}');
                    continue;
                }
                sb.append(chars[lookahead]);
            }
            lookahead++;
        }

    }

    /**
     * 匹配表达式
     */
    public void factor(String id){
        Symbol symbol = top.get(id);
        System.out.print(symbol.getLexeme()+":"+symbol.getType());
    }

    public static void main(String[] args) throws Exception {
        //new EnvParser().program("{int x;char y; {bool y;x;y;} x; y;}");
        runCMD( "netstat -aon|findstr \"8080\"");
    }

    public static boolean runCMD(String cmd) throws IOException, InterruptedException {
        final String METHOD_NAME = "runCMD";

        // Process p = Runtime.getRuntime().exec("cmd.exe /C " + cmd);
        Process p = Runtime.getRuntime().exec("cmd.exe /C " +cmd);
        BufferedReader br = null;
        try {
            // br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String readLine = br.readLine();
            StringBuilder builder = new StringBuilder();
            while (readLine != null) {
                readLine = br.readLine();
                builder.append(readLine);
            }
            System.out.println(METHOD_NAME + "#readLine: " + builder.toString());

            p.waitFor();
            int i = p.exitValue();
            System.out.println(METHOD_NAME + "#exitValue = " + i);
            if (i == 0) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            System.out.println(METHOD_NAME + "#ErrMsg=" + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }
}
