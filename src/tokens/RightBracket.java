package tokens;

public class RightBracket extends Token {
    @Override
    public String getName() {
        return "RBRK";
    }

    public RightBracket() {
    }

    public RightBracket(String rawString) {
        super(rawString);
    }
}
