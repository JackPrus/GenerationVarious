package by.prus.arrayvarious;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GeneratorService {

    static final String[] rowLiter = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "k"};
    static final String[] columnNumber = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public String[][] generateArray(int row, int column) {
        String[][] resultValue = new String[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                resultValue[i][j] = generateCell(i, j);
            }
        }
        return resultValue;
    }

    public String generateCell(int i, int j) {
        //с вероятностью 40 процент возвращаем Null
        if (i < rowLiter.length && j < columnNumber.length) {
            return new Random().nextInt(10) > 4 ? rowLiter[i] + columnNumber[j] : null;
        }
        return null;
    }

}
