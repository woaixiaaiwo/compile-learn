package analysis;

public class Word extends Token{

    private String str;

    public Word(String str) {
        super(Tag.Word);
        this.str = str;
    }

    @Override
    public String toString() {
        return "Word{" +
                "str='" + str + '\'' +
                '}';
    }
}
