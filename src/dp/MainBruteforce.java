package dp;
// https://www.youtube.com/watch?v=wcjqBf2qRe0
public class MainBruteforce {
    public static void main(String[] args) {
        int[] weights = {3, 4, 5, 8, 9};
        int[] prices = {1, 6, 4, 7, 6};

        int maxWeight = 13;

        long count = 2L << weights.length; //Возведение в степень. Кол-во комбинаций вещей

        int maxPrice = 0;
        long maxState = 0;

        for (long state = 0; state < count; state++) {
            int price = statePrice(state, prices);
            int weight = stateWeight(state, weights);
            if (weight <= maxWeight) {
                if (maxPrice < price) {
                    maxPrice = price;
                    maxState = state;
                }
            }
        }

        // Запомнили последнее состояние при котором уложились в вес и набрали максимум цены
        // Прим. битовую маску и определяем какие вещи дали максимальную цену
        System.out.println("Оптимальное содержание рюкзака: ");
        long powerOfTwo = 1;
        for (int i = 0; i < weights.length; i++) {
            if ((powerOfTwo & maxState) > 0) {
                System.out.println(i + 1); // номер ячейки + 1
            }
            powerOfTwo <<= 1;
        }
    }

    // По битовой маске определяем какую вещь класть
    // state в двоичном коде текущая комбинация вещей state = 5 0b00101
    private static int stateWeight(long state, int[] weights) {
        long powerOfTwo = 1;
        int weight = 0;
        for (int i = 0; i < weights.length; i++) {
            if ((powerOfTwo & state) != 0) {
                weight += weights[i];
            }
            powerOfTwo <<= 1; // сдвигаем единичку 00001 00010 00100 01000 10000
        }
        return weight;
    }

    private static int statePrice(long state, int[] prices) {
        long powerOfTWo = 1;
        int price = 0;
        for (int i = 0; i < prices.length; i++) {
            if ((powerOfTWo & state) != 0) {
                price += prices[i];
            }
            powerOfTWo <<= 1;
        }
        return price;
    }
}
