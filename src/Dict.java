public enum Dict {
    I(1), V(5), X(10), L(50), C(100);
    private final int equivalent;

    Dict(int equivalent) {
        this.equivalent = equivalent;
    }

    int getEquivalent() {
        return equivalent;
    }
}
