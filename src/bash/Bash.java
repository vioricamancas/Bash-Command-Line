package bash;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public final class Bash {
    private Path currentDirectory;
    private StringBuffer history;

    private CommandPublisher publisher;
    private static final String EXIT = "exit";

    public Bash() {
        // TODO 2 Initialize history and currentDirectory;
         history = new StringBuffer();
         history.append("History is : ");
         currentDirectory = Paths.get(".");

        // TODO 2 Instantiate a new command publisher
        publisher = new BashCommandPublisher();

        // TODO 4 & 5 & 6 & 7
        BashUtils.Echo echo = new BashUtils().new Echo();
        publisher.subscribe(echo);
        
        BashUtils.Cd cd = new BashUtils().new Cd();
        publisher.subscribe(cd);
        
        BashUtils.Pwd pwd = new BashUtils().new Pwd();
        publisher.subscribe(pwd);
        
        BashUtils.Ls ls = new BashUtils().new Ls();
        publisher.subscribe(ls);
        
        BashUtils.History history = new BashUtils().new History();
        publisher.subscribe(history);
    }

    public StringBuffer getHistory() {
        return history;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        Path path = currentDirectory.toAbsolutePath();
        path = path.subpath(2, path.getNameCount()-1);
        System.out.print(path.toString()+">");
        while (true) {
            // TODO 3 Read commands from the command line
            String input = scanner.nextLine();
            history.append(input);
            history.append(" | ");
            // TODO 3 If we read the "exit" string then we should stop the program.
            if (input.equals(EXIT)) {
                scanner.close();
                break;
            }
            // TODO 3 Create an anonymous class which extends Thread.
            // It has to implement the 'run' method. From the 'run' method publish the command
            // so that the CommandSubscribers start executing it.
            // Don't forget to start the thread by calling the 'start' method on it!
           
            Thread thread = new Thread() {
                public void run() { 
                    Command command = new Command(input, Bash.this);
                    publisher.publish(command);
                    System.out.print(currentDirectory.toString()+">");
                }
            };
            thread.start();
        }
    }

    public Path getCurrentDirectory() {
        return currentDirectory;
    }

    public void setDirectory(String path) {
        currentDirectory = Paths.get(path);
    }
    
    class BashCommandPublisher implements CommandPublisher{
        ArrayList<CommandSubscriber> cmdSub = new  ArrayList<CommandSubscriber>();
        
        @Override
        public void subscribe(CommandSubscriber subscriber) {
            // TODO Auto-generated method stub
            cmdSub.add(subscriber);
        }

        @Override
        public void publish(Command command) {
            // TODO Auto-generated method stub
            for (CommandSubscriber c: cmdSub) {
                c.executeCommand(command);
            }
        }
        
    }

}
