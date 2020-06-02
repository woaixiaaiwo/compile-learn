package analysis;

public class Token {

    private Integer id;

    public Token(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                '}';
    }
}
