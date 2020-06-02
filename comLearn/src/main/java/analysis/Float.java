package analysis;

public class Float extends Token{

    private float flot;

    public Float(float flot) {
        super(Tag.Float);
        this.flot = flot;
    }

    @Override
    public String toString() {
        return "Float{" +
                "flot=" + flot +
                '}';
    }
}
