package sort;

import java.util.Arrays;

public class Sorting {
    public static void main(String[] args) {
        System.out.println("BubbleSort");
        int[] bubble = new int[]{3, 2, -6, 1, 5, 0};
        System.out.println("Have: " + Arrays.toString(bubble));
        bubbleSort(bubble);
        System.out.println("Sorted: " + Arrays.toString(bubble));
        System.out.println("--------------END-------------------");

        System.out.println("SelectionSort");
        int[] selection = new int[]{7, 11, -6, 1, 5, 3};
        System.out.println("Have: " + Arrays.toString(selection));
        selectionSort(selection);
        System.out.println("Sorted: " + Arrays.toString(selection));
        System.out.println("--------------END-------------------");

        System.out.println("InsertionSort");
        int[] insertion = new int[]{2, 15, -6, 10, 5, 3};
        System.out.println("Have: " + Arrays.toString(insertion));
        insertionSort(insertion);
        System.out.println("Sorted: " + Arrays.toString(insertion));
        System.out.println("--------------END-------------------");

        System.out.println("Recursion");
        System.out.println("Factorial recursive 20! =  " + recursiveFactorial(20));
        System.out.println("Factorial iteration 20! =  " + iterativeFactorial(20));
        System.out.println("--------------END-------------------");

        System.out.println("ShellSort");
        int[] shell = new int[]{15, 12, 10, -6, 13, 25, 100, 5, 7, -3, -20, 68};
        System.out.println("Have: " + Arrays.toString(shell));
        shellSort(shell);
        System.out.println("Sorted: " + Arrays.toString(shell));
        System.out.println("--------------END-------------------");

        System.out.println("MergeSort");
        int[] merge = new int[]{22, 15, 0, 31, -15, 40, -100, 80, 33};
        System.out.println("Have: " + Arrays.toString(merge));
        mergeSort(merge);
        System.out.println("Sorted: " + Arrays.toString(merge));
        System.out.println("--------------END-------------------");

        System.out.println("QuickSort");
        int[] quick = new int[]{15, 21, 10, 0, -40, 30, 80, 45};
        System.out.println("Have: " + Arrays.toString(quick));
        quickSort(quick, 0, quick.length - 1);
        System.out.println("Sorted: " + Arrays.toString(quick));
        System.out.println("--------------END-------------------");


    }

    // n^2 in-place stable
    public static void bubbleSort(int[] arr) {
        for (int partIndex = arr.length - 1; partIndex > 0; partIndex--) {
            for (int i = 0; i < partIndex; i++) {
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                }
            }
        }
    }

    // n^2 in-place unstable
    public static void selectionSort(int[] arr) {
        for (int partIndex = arr.length - 1; partIndex > 0; partIndex--) {
            int largestAt = 0;
            for (int i = 1; i <= partIndex; i++) {
                if (arr[i] > arr[largestAt]) {
                    largestAt = i;
                }
            }
            swap(arr, largestAt, partIndex);
        }
    }

    // n^2 in-place stable
    public static void insertionSort(int[] arr) {
        for (int partIndex = 1; partIndex < arr.length; partIndex++) {
            int currUnsorted = arr[partIndex];
            int i = 0; // чтобы иметь доступ после цикла для вставки
            for (i = partIndex; i > 0 && arr[i - 1] > currUnsorted; i--) {
                arr[i] = arr[i - 1];
            }
            arr[i] = currUnsorted;
        }
    }

    public static long iterativeFactorial(long number) {
        if (number == 0) {
            return 1L;
        }
        long factorial = 1;
        for (int i = 1; i <= number; i++) {
            factorial *= i;
        }

        return factorial;
    }

    private static long recursiveFactorial(long number) {
        if (number == 0) {
            return 1L;
        }
        return number * recursiveFactorial(number - 1);
    }

    // n^3/2 or n^2 or n^6/5 depends on gap     in-place unstable
    // https://www.youtube.com/watch?v=X-Pef9LHGos
    public static void shellSort(int[] arr) {
        int gap = 1;
        while (gap < arr.length / 3) {
            gap = 3 * gap + 1;
        }
        while (gap >= 1) {
            for (int i = gap; i < arr.length; i++) {
                for (int j = i; j >= gap && arr[j] < arr[j - gap]; j -= gap) {
                    swap(arr, j, j - gap);
                }
            }
            gap /= 3;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }

    //n * logN not-in-place stable(classic) may be unstable
    // https://www.youtube.com/watch?v=3j0SWDX4AtU English (code from here)
    // https://www.youtube.com/watch?v=wk6hUweJ4UA Russian
    public static void mergeSort(int[] arr) {
        int length = arr.length;
        if (length <= 1) return; //base case

        int middle = length / 2;
        int[] leftArray = new int[middle];
        int[] rightArray = new int[length - middle];

        int i = 0; //left array
        int j = 0; //right array

        for (; i < length; i++) {
            if (i < middle) {
                leftArray[i] = arr[i];
            } else {
                rightArray[j] = arr[i];
                j++;
            }
        }
        mergeSort(leftArray);
        mergeSort(rightArray);
        merge(leftArray, rightArray, arr);
    }

    private static void merge(int[] leftArray, int[] rightArray, int[] array) {

        int leftSize = array.length / 2;
        int rightSize = array.length - leftSize;
        int i = 0, l = 0, r = 0; //indices

        //check the conditions for merging
        while (l < leftSize && r < rightSize) {
            if (leftArray[l] < rightArray[r]) {
                array[i] = leftArray[l];
                i++;
                l++;
            } else {
                array[i] = rightArray[r];
                i++;
                r++;
            }
        }
        while (l < leftSize) {
            array[i] = leftArray[l];
            i++;
            l++;
        }
        while (r < rightSize) {
            array[i] = rightArray[r];
            i++;
            r++;
        }
    }

    // N * logN (bad N^2) in-place unstable
    // https://otus.ru/nest/post/788/
    // https://www.youtube.com/watch?v=Vtckgz38QHs English
    // https://www.youtube.com/watch?v=CeHJV4zu_Ts Russian
    public static void quickSort(int[] arr, int low, int high) {
        if (arr.length == 0)
            return;

        if (low >= high)
            return;

        int middle = low + (high - low) / 2;
        int opora = arr[middle];

        int i = low, j = high;
        while (i <= j) {
            while (arr[i] < opora) {
                i++;
            }

            while (arr[j] > opora) {
                j--;
            }

            if (i <= j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }

        if (low < j)
            quickSort(arr, low, j);

        if (high > i)
            quickSort(arr, i, high);
    }
}
