package statistic;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FullStatisticEntity {

    private int numbersCount;
    private BigDecimal minNumberValue;
    private BigDecimal numbersAverageValue;
    private BigDecimal maxNumberValue;

    private int floatsCount;
    private BigDecimal minFloatsValue;
    private BigDecimal floatsAverageValue;
    private BigDecimal maxFloatsValue;

    private int stringsCount;
    private BigDecimal mostShortWordLength;
    private BigDecimal mostLongWordLength;

    @Override
    public String toString() {
        return "Полная статистика: \n" +
                " numbersCount = " + numbersCount +
                ";\n minNumberValue = " + minNumberValue +
                ";\n numbersAverageValue = " + numbersAverageValue +
                ";\n maxNumberValue = " + maxNumberValue +
                ";\n floatsCount = " + floatsCount +
                ";\n minFloatsValue = " + minFloatsValue +
                ";\n floatsAverageValue = " + floatsAverageValue +
                ";\n maxFloatsValue = " + maxFloatsValue +
                ";\n stringsCount = " + stringsCount +
                ";\n mostShortWordLength = " + mostShortWordLength +
                ";\n mostLongWordLength = " + mostLongWordLength;
    }

}
