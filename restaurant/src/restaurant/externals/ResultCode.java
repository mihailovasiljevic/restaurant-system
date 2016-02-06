package restaurant.externals;


public enum ResultCode {
    TRUE("TRUE"), FALSE("FALSE"), USER_NOT_FOUND("USER_NOT_FOUND");



    ResultCode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    private String name;
}
