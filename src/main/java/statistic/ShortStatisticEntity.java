package statistic;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortStatisticEntity {

    private int numbersCount;
    private int stringsCount;
    private int floatsCount;

    @Override
    public String toString() {
        return  "Краткая статистика: \n" +
                " numbersCount = " + numbersCount +
                ";\n stringsCount = " + stringsCount +
                ";\n floatsCount = " + floatsCount;
    }
}
