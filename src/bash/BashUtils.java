package bash;

import java.io.File;
import java.nio.file.Paths;

enum Commands {
    CD("cd"),
    PWD("pwd"),
    LS("ls"),
    ECHO("echo"),
    HISTORY("history");

    private final String text;

    Commands(final String newText) {
        this.text = newText;
    }

    @Override
    public String toString() {
        return text;
    }
}

public class BashUtils {


    
    class Echo implements CommandSubscriber{
        @Override
        public void executeCommand(Command c) {
            // TODO Auto-generated method stub
            if (c.getCommand().startsWith("echo")) {
                if (c.getCommand().length() > 5)
                System.out.println(c.getCommand().substring(5));
                else
                    System.out.println(" ");
            }
        }
    }

    // TODO 5 Create Cd class
    class Cd implements CommandSubscriber{

        @Override
        public void executeCommand(Command c) {
            // TODO Auto-generated method stub
            if (c.getCommand().startsWith(Commands.CD.toString())) {
                if (c.getCommand().length() > 2) {
                    String path = c.getCommand().substring(3);
                    c.getBash().setDirectory(path);
                } else {
                    c.getBash().setDirectory("/home/vio");
                }
                
            }
        }
    }
    
    class Pwd implements CommandSubscriber{

        @Override
        public void executeCommand(Command c) {
            if (c.getCommand().startsWith(Commands.PWD.toString())) {
                System.out.println((c.getBash()).getCurrentDirectory().toAbsolutePath());
            }
        }
    }

    // TODO 6 Create the Ls class
    class Ls implements CommandSubscriber{
        public void listFilesForFolder(final File folder) {
            for (final File fileEntry : folder.listFiles()) {
                if(!fileEntry.getName().startsWith("."))
                if (fileEntry.isDirectory()) {
                    System.out.println(fileEntry.getName()+ "(dir)");
                } else {
                    System.out.println(fileEntry.getName());
                }
            }
        }
        
        @Override
        public void executeCommand(Command c) {
            // TODO Auto-generated method stub
            if (c.getCommand().startsWith(Commands.LS.toString())) {
                if (c.getCommand().length() == 2) {
                    listFilesForFolder(((c.getBash()).getCurrentDirectory()).toFile());
                } else {
                    String path = c.getCommand().substring(3);
                    listFilesForFolder(Paths.get(path).toFile());
                }
            }
        }
    }
    // TODO 7 Create the History class
    class History implements CommandSubscriber{

        @Override
        public void executeCommand(Command c) {
            // TODO Auto-generated method stub
            if (c.getCommand().equals(Commands.HISTORY.toString())) {
                System.out.println((c.getBash()).getHistory().toString());
            }
        }
        
    }
}
