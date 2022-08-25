package by.prus.arrayvarious;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ArrayService {

    @Autowired
    private GeneratorService generatorService;

    public List<List<String>> createArrAndGenerateVarious(int i, int j) throws Exception {
        if (i > 0 && i < 10 && j > 0 && j < 10) {
            String[][] arr = generatorService.generateArray(i, j);
            return generateVariousByReadyArray(arr);
        } else {
            throw new Exception("Column and Row quantity must be 1-9");
        }
    }

    public List<List<String>> generateVariousByReadyArray(String[][] arr) throws Exception {
        if (isSymetric(arr) && isPosibleToUniteRows(arr)) {
            String[][] reversedArr = reverseArray(arr);
            printArray(arr);
            System.out.println("Reversed: ");
            printArray(reversedArr);
            System.out.println("_________");
            List<List<String>> result = new ArrayList<>();
            for (int i = 0; i < arr[0].length; i++) {
                generateLine(result, reversedArr, i);
            }
            printResult(result);
            return result;
        } else {
            throw new Exception("Wrong incoming data. Is not possible to create result");
        }
    }

    /**
     * Метод работает только если заходящий массив "arr" проверен на корректность.
     * Иначе Индекс аут оф баунд.
     * Сколько строк в newStrinList значит еще столько же возможных варианов.
     * Копируем листы с результатом столько раз, сколько ненулевых значений в новом итерируемом столбце.
     * Далее следует запутанная логика, записывающая в результирующий массив именно ту ячейку, которая нужна
     * дабы достичь всех вариантов. Если размер resultList массива поделить на номер текущей итерации
     * И полученное значение будет больше, чем
     * размер малого массива (newStringList) поделенного на counter - то мы записываем
     * в массив resultList пизицию под номером counter в newStringList.
     * Если же нет - мы увеличиваем каунтер на единицу, идалее в массив будем записывать уже следующее число.
     */
    public void generateLine(List<List<String>> resultList, String[][] arr, int columnNo) {
        List<List<String>> oldResultList = new ArrayList<>(resultList);
        List<String> newStringList = excludeNullFromArr(arr[columnNo]);

        if (resultList.size() == 0) {
            for (String s : newStringList) {
                resultList.add(new ArrayList<>(Collections.singletonList(s)));
            }
            return;
        }
        for (int i = 0; i < newStringList.size() - 1; i++) {
            for (List<String> stringList : oldResultList) {
                resultList.add(new ArrayList<>(stringList));
            }
        }
        int counter = 1;
        for (int i = 1; i < resultList.size() + 1; i++) {
            if (resultList.size() / i >= newStringList.size() / counter) {
                resultList.get(i - 1).add(newStringList.get(counter - 1));
            } else {
                counter++;
                resultList.get(i - 1).add(newStringList.get(counter - 1));
            }
        }
    }

    /**
     * Метод проверяет возможно ли вообще из этого массива что-то сделать
     * Т.к. если строка либо столбец состоит из NULL - то нельзя
     *
     * @param arr - массив данных из которого сгенерируем варианты
     * @return - результат возможности
     */
    public boolean isPosibleToUniteRows(String[][] arr) {
        String[][] reversedArr = reverseArray(arr);
        int countNullRows = (int) Arrays.stream(arr).filter(a -> excludeNullFromArr(a).size() == 0).count();
        int countNullColumns = (int) Arrays.stream(reversedArr).filter(a -> excludeNullFromArr(a).size() == 0).count();
        return countNullColumns == 0 && countNullRows == 0;
    }

    public boolean isSymetric(String[][] arr) {
        for (String[] s : arr) {
            if (s.length != arr[0].length) {
                return false;
            }
        }
        return true;
    }

    public List<String> excludeNullFromArr(String[] arr) {
        return Arrays.stream(arr).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public String[][] reverseArray(String[][] arrToReverse) {
        int m = arrToReverse.length;
        int n = arrToReverse[0].length;
        String[][] rotated = new String[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rotated[j][i] = arrToReverse[m - i - 1][j];
            }
        }
        return rotated;
    }

    //просто для визуализации метод
    public void printArray(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] != null) {
                    System.out.print(" " + array[i][j] + " |");
                } else {
                    System.out.print(array[i][j] + "|");
                }
            }
            System.out.println();
        }
    }

    void printResult(List<List<String>> listToPrint) {
        for (List<String> stringList : listToPrint) {
            System.out.print("[");
            for (String s : stringList) {
                System.out.print("[" + s + "]");
            }
            System.out.println("]");
        }
    }
}
