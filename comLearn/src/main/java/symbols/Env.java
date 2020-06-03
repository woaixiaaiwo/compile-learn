package symbols;

import java.util.HashMap;

/**
 * 符号表实现
 */
public class Env {

    private HashMap<String,Symbol> hashMap;

    private Env prev;

    public Env(Env prev) {
        this.hashMap = new HashMap<String, Symbol>();
        this.prev = prev;
    }

    public void put(String s,Symbol symbol){
        hashMap.put(s,symbol);
    }

    public Symbol get(String s){
        for(Env env = this;env != null;env = env.prev){
            if(env.hashMap.containsKey(s)){
                return env.hashMap.get(s);
            }
        }
        return null;
    }

    public Env getPrev() {
        return prev;
    }
}
