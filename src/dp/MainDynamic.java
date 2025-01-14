package dp;

import java.util.ArrayList;
import java.util.List;

//https://neerc.ifmo.ru/wiki/index.php?title=%D0%97%D0%B0%D0%B4%D0%B0%D1%87%D0%B0_%D0%BE_%D1%80%D1%8E%D0%BA%D0%B7%D0%B0%D0%BA%D0%B5
public class MainDynamic {
    public static void main(String[] args) {
        int[] weights = {3, 4, 5, 8, 9};
        int[] prices = {1, 6, 4, 7, 6};

        int count = weights.length;
        int maxWeight = 13;

        int[][] A; // Матрица оптимальной стоимости
        A = new int[count + 1][];
        for (int i = 0; i < count + 1; i++) {
            A[i] = new int[maxWeight + 1];
        }

        // s - размер рюкзака, k - предмет
        for (int k = 0; k <= count; k++) {
            for (int s = 0; s <= maxWeight; s++) {
                if (k == 0 || s == 0) {
                    A[k][s] = 0;
                } else {
                    if (s >= weights[k - 1]) {
                        A[k][s] = Math.max(A[k - 1][s], A[k - 1][s - weights[k - 1]] + prices[k - 1]);
                    } else {
                        A[k][s] = A[k - 1][s];
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        traceResult(A, weights, count, maxWeight, result);

        System.out.println("Оптимальное содержимое рюкзака");
        for (Integer integer : result) {
            System.out.println(integer);
        }

    }

    private static void traceResult(int[][] A, int[] weight, int k, int s, List<Integer> result) {
        if (A[k][s] == 0) {
            return;
        }

        // Стоимость набора без К предмета равна стоимости набора с К т.е. К предмет не участвует
        if (A[k - 1][s] == A[k][s]) {
            traceResult(A, weight, k - 1, s, result);
        } else {
            // Уменьшаем размер рюкзака на вес К предмета и кладем К в итоговое решение
            traceResult(A, weight, k - 1, s - weight[k - 1], result);
            result.add(0, k);
        }
    }
}
