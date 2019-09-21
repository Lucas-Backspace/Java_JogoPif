package jogodecartaspifpaf;

public class Carta {

    private final String FACE;
    private final String NAIPE;

    public Carta(String face, String naipe) {
        this.FACE = face;
        this.NAIPE = naipe;
    }

    @Override
    public String toString() {
        return FACE + " de " + NAIPE;
    }
    
    public String getFace() {
        return this.FACE;
    }
    
    public String getNaipe(){
        return this.NAIPE;
    }

}
