package com.dealornodeal.java;

import java.io.*;
import java.util.*;

import static java.lang.Thread.sleep;

public class Game {

    List<Integer> valueList = new ArrayList<>(Arrays.asList(1, 5, 10, 25, 50, 75, 100, 200, 300, 400, 500, 750, 1000, 5000, 10000, 25000, 50000, 75000, 100000, 200000, 300000, 400000, 500000, 750000, 1000000));
    ArrayList<Case> cases = new ArrayList<>();
    Banker bank = new Banker();
    Scanner sc = new Scanner(System.in);
    int in;
    int winnings;
    String c;


    public Game() {

        System.out.println("Welcome to Deal or No Deal!!");
        shhh(2000);

        /**STEP 1 - SHUFFLE THE CASES AND ASSIGN A VALUE TO EACH CASE**/
        Collections.shuffle(valueList);
        cases.add(new Case(0));
        cases.get(0).selected();
        for (int i = 0; i < 25; i++) {
            cases.add(new Case(valueList.get(i)));
        }
        Collections.sort(valueList);

        /**STEP 2 - PLAYER SELECTS A CASE**/
        System.out.println("There are 25 cases.");
        System.out.println("You must pick one as yours until the end of the game.");
        shhh(1500);
        System.out.println("Which case would you like?");
        whatsLeft();
        shhh(1000);
        System.out.println();
        System.out.print("Your choice: ");

        in = sc.nextInt();

        while (in < 1 || in > 25) {
            System.out.println("Cases are only numbers from 1 - 25.");
            System.out.print("Pick a case number: ");
            in = sc.nextInt();
        }

        cases.get(in).myCase();
        cases.get(in).selected();
        System.out.println("You picked case " + (in));

        /**STEP 3 - PLAY THE GAME!!**/
        for (int i = 0; i < 8; i++) {
            int j = i;

            do {
                switch (j-i) {
                    case 0: System.out.print("Select your first case to remove from the board: ");
                        break;
                    case 1: System.out.print("Select your second case to remove from the board: ");
                        break;
                    case 2: System.out.print("Select your third case to remove from the board: ");
                        break;
                    case 3: System.out.print("Select your fourth case to remove from the board: ");
                        break;
                    case 4: System.out.print("Select your fifth case to remove from the board: ");
                        break;
                    case 5: System.out.print("Select your sixth case to remove from the board: ");
                        break;
                }
                in = sc.nextInt();

                if (!cases.get(in).invalid()) {
                    System.out.println("Case number " + in + " was worth...");
                    System.out.println(cases.get(in).getVal() + "!");
                    cases.get(in).selected();
                    valueList.set(valueList.indexOf(cases.get(in).getVal()), 0);
                } else {
                    System.out.println("Invalid case number.");
                    if (j > 5) {
                        j = 4;
                    } else {
                        j--;
                    }
                }
                j++;

            } while (j < 6);


            bank.offer(cases);
            System.out.println("Do you accept that offer?");
            c = sc.next();
            if (c.toLowerCase().contains("y")) {
                winnings = bank.getOffer();
                System.out.println("Congratulations! You have won $" + winnings + "!");
                highScore(winnings);
                System.exit(0);
            } else if (i < 7) {
                System.out.println("No deal! Time for the next round!");
                whatsLeft();
                System.out.println();
            } else {
                lastCase();
                System.exit(0);
            }
        }
    }

    public void shhh(int time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) { e.printStackTrace();}
            System.out.println("Clearing screen");
    }

    public void whatsLeft() {
        System.out.print("Available cases: ");
        System.out.println();
        for (int i = 0; i < 26; i++) {
            if (!cases.get(i).invalid()) {
                System.out.print(i + "  ");
            } else {
                System.out.print("  ");
            }
            if (i % 5 == 0) {
                System.out.println();
            }
        }
        int j = 0;
        System.out.println();
        System.out.print("Available values: ");
        System.out.println();
        for (int i = 0; i < 12; i++) {
            if (valueList.get(i) != 0) {
                System.out.print(valueList.get(i) + " ");
            } else {
                System.out.print("   ");
            }
            if (valueList.get((i+12)) != 0) {
                System.out.print(valueList.get(i+12) + " ");
            } else {
                System.out.print("    ");
            }
            System.out.println();
        }
        if (valueList.get(24) != 0) {
            System.out.print(valueList.get(24) + " ");
        } else {
            System.out.print("   ");
        }
        System.out.println();
    }

    public void lastCase() {
        System.out.println("You only have one case left. Do you want to switch your case with the last case on the board?");
        c = sc.next();
        if (c.toLowerCase().contains("y")) {
            for (Case brief : cases) {
                if (!brief.isMyCase() && !brief.invalid()) {
                    winnings = brief.getVal();
                    System.out.println("Congratulations! You have won $" + winnings + "!");
                }
            }
        } else {
            for (Case brief : cases) {
                if (brief.isMyCase()) {
                    winnings = brief.getVal();
                    System.out.println("Congratulations! You have won $" + winnings + "!");
                }
            }
        }
        highScore(winnings);
    }

    public void highScore(int winnings) {
        String source = "files/topscore.txt";

        try (BufferedReader fr = new BufferedReader(new FileReader(source))) {
            Integer score = Integer.parseInt(fr.readLine());
            if (winnings > score) {

                try(PrintWriter fw = new PrintWriter(source)) {
                    fw.println(winnings);
                } catch (IOException e) {
                    System.out.println("Writer problem.");
                }

            }
        } catch (IOException e) {
            System.out.println("Reader problem.");
        }

        shhh(5000);
    }
}
