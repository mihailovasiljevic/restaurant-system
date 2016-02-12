package restaurant.externals;


public enum ResultCode {
    TUPLE_ADDED("Torka dodata."), TUPLE_NOT_ADDED("Desila greska i torka nije dodata."),
    TUPLE_UPDATED("Torka izmenjena."), TUPLE_NOT_UPDATED("Desila greska i torka nije izmenjena."),
    TUPLE_DELETED("Torka obrisana."), TUPLE_NOT_DELETED("Desila greska i torka nije obrisana.");



    ResultCode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    private String name;
}
