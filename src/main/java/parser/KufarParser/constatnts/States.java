package parser.KufarParser.constatnts;

public enum States {
    ADD_URL("add url"),
    ADD_URL_DESCRIPTION("add url description"),
    PARSING("parsing urls");

    private final String value;

    States(String str){
        this.value = str;
    }

    @Override
    public String toString() {
        return value;
    }

}
