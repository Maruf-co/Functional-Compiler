package tokens;

public class List extends Token {
    
    public List() {
    }

    public List(String rawString) {
        super(rawString);
    }

    @Override
    public String getName() {
        return "LIST";
    }
}
