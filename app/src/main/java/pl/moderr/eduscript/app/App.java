package pl.moderr.eduscript.app;

import pl.moderr.eduscript.EduScript;

public class App {

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        new App().run();
    }

    public void run() {
        System.out.println(getGreeting());
    }

}
