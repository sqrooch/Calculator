/**
 * Словарь, в котором римские символы соответствуют их арабским эквивалентам.
 */
public enum Dict {
    I(1), V(5), X(10), L(50), C(100);
    private final int equivalent;

    //Конструктор
    Dict(int equivalent) {
        this.equivalent = equivalent;
    }

    /***
     * Вспомогательный геттер словаря.
     * @return Возвращает арабский эквивалент римского символа.
     */
    int getEquivalent() {
        return equivalent;
    }
}
