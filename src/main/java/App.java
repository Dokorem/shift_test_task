import args.ArgsExecutor;

public class App {

    public static void main(String[] args) {
        ArgsExecutor executor = new ArgsExecutor();
        executor.executeProvidedArgs(args);
    }
}
