package restaurant.externals;


public enum ResultCode {
    TUPLE_ADDED("Torka dodata."), TUPLE_NOT_ADDED("Desila greska i torka nije dodata."),
    TUPLE_UPDATED("Torka izmenjena."), TUPLE_NOT_UPDATED("Desila greska i torka nije izmenjena."),
    TUPLE_DELETED("Torka obrisana."), TUPLE_NOT_DELETED("Desila greska i torka nije obrisana."), 
    LOGIN_EMPTY_FIELDS("Email i/ ili polje za lozinku su prazni."), 
    LOGIN_USER_DOESNT_EXISTS("Korisnik sa datim emailom ne postoji."),
    LOGIN_USER_NEEDS_ACTIVATION("Morate aktivirati nalog! Pogledajte vase postansko sanduce."),
    LOGIN_USER_ERROR("Neispravan email i/ ili lozinka"),
    LOGIN_USER_SUCCESS_GUEST("USPEH_GOST"),LOGIN_USER_SUCCESS_SM("USPEH__SISTEM_MENADZER"),
    LOGIN_USER_SUCCESS_RM("USPEH__RESTORAN_MENADZER"),
    REGISTER_USER_FILE_UPLOAD_SUCC("USPEH"), REGISTER_USER_FILE_UPLOAD_FAIL("GRESKA"),
    REGISTER_USER_ALREADY_EXISTS("Korisnik sa prosledjenim email-mom vec postoji."),
    REGISTER_USER_FIELD_EMPTY("Morate popuniti sva obavezna polja."),
    REGISTER_USER_ERROR("Nije uspelo cuvanje."), REGISTER_USER_SUCCESS("USPEH");



    ResultCode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    private String name;
}
