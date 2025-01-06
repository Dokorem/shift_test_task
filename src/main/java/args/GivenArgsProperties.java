package args;

import com.beust.jcommander.Parameter;
import lombok.Data;

import java.util.List;

@Data
public class GivenArgsProperties {

    @Parameter(names = "-p")
    private String outputFilePrefix;

    @Parameter(names = "-o")
    private String outputFilePath;

    @Parameter(names = "-a")
    private boolean supplementTheOutputFiles;

    @Parameter(names = "-s")
    private boolean isShortStatisticRequired;

    @Parameter(names = "-f")
    private boolean isFullStatisticRequired;

    @Parameter
    private List<String> files;

}
