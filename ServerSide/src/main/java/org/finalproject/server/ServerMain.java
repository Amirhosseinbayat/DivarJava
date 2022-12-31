package org.finalproject.server;

public class ServerMain {
    public static int PORT = 12435;

    public static void main(String[] args) {
        for (int x = 0; x<=args.length-1; x++) {
            String key = args[x];
            String value = null;
            if (key.startsWith("-") && x+2<args.length) {
                value = args[x+1];
                processCLIArgs(x, key, value);
                x++;
            } else {
                boolean continueProcess = processCLIArgs(x, key, value);
                if (!continueProcess) return;
            }
        }

        System.out.println("Server Process is starting on port "+PORT);
    }

    public static boolean processCLIArgs(int index, String key, String value) {
        switch (key) {
            case "-port":
                PORT = Integer.parseInt(value);
                return true;
            case "--help":
            case "-h":
                if (index == 0) {
                    printHelpMessage();
                    return false;
                }
                break;
        }
        System.out.println("unknown argument "+key);
        return false;
    }

    public static void printHelpMessage() {
        System.out.println(
                "Server process for Divar software."+
                        "\n  usage: [options] "+
                        "\n--help , -h               shows this message. interpreted only if it is the first arg"+
                        "\n -port=number             specify port number to listen on [default: 12435]"
        );
    }
}