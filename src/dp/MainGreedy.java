package dp;

import java.util.ArrayList;
import java.util.List;

public class MainGreedy {
    public static void main(String[] args) {
        int[] weights = {3, 4, 5, 8, 9};
        int[] prices = {1, 6, 4, 7, 6};

        int maxWeight = 13;

        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < weights.length; i++) {
            indexes.add(i);
        }

        int resultWeight = 0;
        List<Integer> result = new ArrayList<>();
        while (!indexes.isEmpty()) {
            int maxValue = prices[indexes.get(0)];
            int maxIndex = indexes.get(0);

            for (int i = 1; i < indexes.size(); i++) {
                if (maxValue < prices[indexes.get(i)]) {
                    maxValue = prices[indexes.get(i)];
                    maxIndex = indexes.get(i);
                }
            }

            resultWeight += weights[maxIndex];
            if (resultWeight > maxWeight) {
                break;
            }

            result.add(maxIndex);
            indexes.remove(maxIndex);
        }

        System.out.println("Оптимальное содержание рюкзака:");
        for (Integer integer : result) {
            System.out.println(integer + 1);
        }
    }
}
