    package com.kreitek.editor;

    import com.kreitek.editor.commands.CommandFactory;

    import java.util.ArrayList;
    import java.util.Scanner;
    import java.util.Stack;

    public class ConsoleEditor implements Editor {
        public static final String TEXT_RESET = "\u001B[0m";
        public static final String TEXT_BLACK = "\u001B[30m";
        public static final String TEXT_RED = "\u001B[31m";
        public static final String TEXT_GREEN = "\u001B[32m";
        public static final String TEXT_YELLOW = "\u001B[33m";
        public static final String TEXT_BLUE = "\u001B[34m";
        public static final String TEXT_PURPLE = "\u001B[35m";
        public static final String TEXT_CYAN = "\u001B[36m";
        public static final String TEXT_WHITE = "\u001B[37m";

        private final CommandFactory commandFactory = new CommandFactory(this);
        private final Stack<Memento> history = new Stack<>();
        private final ArrayList<String> documentLines = new ArrayList<String>();

        @Override
        public void run() {
            boolean exit = false;
            while (!exit) {
                String commandLine = waitForNewCommand();
                try {
                    if (!commandLine.equals("undo")) {
                        saveState();
                    }
                    Command command = commandFactory.getCommand(commandLine);
                    command.execute(documentLines);
                    showDocumentLines(documentLines);
                } catch (BadCommandException e) {
                    printErrorToConsole("Bad command");
                } catch (ExitException e) {
                    exit = true;
                }
                showHelp();
            }
        }
        private void saveState() {
            Memento memento = new Memento(new ArrayList<>(documentLines));
            history.push(memento);
        }
        public Stack<Memento> getHistory(){
            return history;
        }

        private void showDocumentLines(ArrayList<String> textLines) {
            if (textLines.size() > 0){
                setTextColor(TEXT_YELLOW);
                printLnToConsole("START DOCUMENT ==>");
                for (int index = 0; index < textLines.size(); index++) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("[");
                    stringBuilder.append(index);
                    stringBuilder.append("] ");
                    stringBuilder.append(textLines.get(index));
                    printLnToConsole(stringBuilder.toString());
                }
                printLnToConsole("<== END DOCUMENT");
                setTextColor(TEXT_RESET);
            } else {
                printLnToConsole("Document is empty");
            }
        }

        private String waitForNewCommand() {
            printToConsole("Enter a command : ");
            Scanner scanner = new Scanner(System. in);
            return scanner.nextLine();
        }

        private void showHelp() {
            printLnToConsole("To add new line -> a \"your text\"");
            printLnToConsole("To update line -> u [line number] \"your text\"");
            printLnToConsole("To delete line -> d [line number]");
            printLnToConsole("To undo the last action -> undo");
            printLnToConsole("To exit the editor -> exit");
        }

        public void printErrorToConsole(String message) {
            setTextColor(TEXT_RED);
            printToConsole(message);
            setTextColor(TEXT_RESET);
        }

        private void setTextColor(String color) {
            System.out.println(color);
        }

        private void printLnToConsole(String message) {
            System.out.println(message);
        }

        private void printToConsole(String message) {
            System.out.print(message);
        }

    }
