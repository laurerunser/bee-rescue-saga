package view.cli;

import view.WelcomeView;

import java.util.Scanner;

public class CliWelcomeView implements WelcomeView {
    @Override
    public void welcome() {
        System.out.println();
        System.out.println(" _______    _______   _______                                   ");
        System.out.println("|   _  \"\\  /\"     \"| /\"     \"|                                  ");
        System.out.println("(. |_)  :)(: ______)(: ______)                                  ");
        System.out.println("|:     \\/  \\/    |   \\/    |                                    ");
        System.out.println("(|  _  \\\\  // ___)_  // ___)_                                   ");
        System.out.println("|: |_)  :)(:      \"|(:      \"|                                  ");
        System.out.println("(_______/  \\_______) \\_______)                                  ");
        System.out.println();
        System.out.println("  _______    _______   ________  ______   ____  ____   _______  ");
        System.out.println(" /\"      \\  /\"     \"| /\"       )/\" _  \"\\ (\"  _||_ \" | /\"     \"| ");
        System.out.println("|:        |(: ______)(:   \\___/(: ( \\___)|   (  ) : |(: ______) ");
        System.out.println("|_____/   ) \\/    |   \\___  \\   \\/ \\     (:  |  | . ) \\/    |   ");
        System.out.println(" //      /  // ___)_   __/  \\\\  //  \\ _   \\\\ \\__/ //  // ___)_  ");
        System.out.println("|:  __   \\ (:      \"| /\" \\   :)(:   _) \\  /\\\\ __ //\\ (:      \"| ");
        System.out.println("|__|  \\___) \\_______)(_______/  \\_______)(__________) \\_______) ");
        System.out.println();
        System.out.println("  ________     __       _______       __                        ");
        System.out.println(" /\"       )   /\"\"\\     /\" _   \"|     /\"\"\\                       ");
        System.out.println("(:   \\___/   /    \\   (: ( \\___)    /    \\                      ");
        System.out.println(" \\___  \\    /' /\\  \\   \\/ \\        /' /\\  \\                     ");
        System.out.println("  __/  \\\\  //  __'  \\  //  \\ ___  //  __'  \\                    ");
        System.out.println(" /\" \\   :)/   /  \\\\  \\(:   _(  _|/   /  \\\\  \\                   ");
        System.out.println("(_______/(___/    \\___)\\_______)(___/    \\___)                  ");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("Welcome to the game Bee Rescue Saga !");
        System.out.println("In the game, you will have to save bees from many dangers.");
    }

    @Override
    public String askName() {
        System.out.print("What is your name ? ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}

