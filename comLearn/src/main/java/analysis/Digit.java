package analysis;

public class Digit extends Token{

    private Integer num;

    public Digit(Integer num) {
        super(Tag.Digit);
        this.num = num;
    }

    @Override
    public String toString() {
        return "Digit{" +
                "num=" + num +
                '}';
    }
}
