package JanOrman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String input;
        Scanner scan = new Scanner(System.in);  // Scanner object for inputs
        boolean flag = false;
        int playerCount = 0;

        // Requesting number of players
        while (!flag) {
            System.out.println("Please enter the number of players:");
            try {
                input = scan.nextLine();
                if (input.equals("E")) {
                    System.out.println("does this happen");
                    System.exit(0);
                }
                playerCount = Integer.parseInt(input); // throws exception if input is not a number
                if (playerCount < 1) {
                    throw new IllegalArgumentException("There must be a positive, non-zero number of players");
                }
                flag = true;
            } catch (NumberFormatException e) {
                System.out.println("ERROR: you must enter an integer number");
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: there must be a positive, non-zero number of players");
            }
        }

        //Requesting file locations
        ArrayList<ArrayList<Integer>> blackBags = new ArrayList<>();
        blackBags.add(0, new ArrayList<>());
        blackBags.add(1, new ArrayList<>());
        blackBags.add(2, new ArrayList<>());

        for (int i=0; i<3; i++) {
            String exceptionType = "";
            flag = false;
            while (!flag) {
                try {
                    System.out.println("Please enter location of bag " + i + " to load:");
                    input = scan.nextLine(); //Requesting for location of the file
                    if (input.equals("E")){
                        System.exit(0);
                    }

                    //READ THE FILE HERE
                    System.out.println(System.getProperty("user.dir")+ "\\" + input);
                    BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+ "\\" + input));

                    String line;
                    while((line = br.readLine()) != null) {
                        String[] arrayOfNumbers = line.split(",");
                        System.out.println(Arrays.toString(arrayOfNumbers));

                        for (String arrayOfNumber : arrayOfNumbers) {
                            int value = Integer.parseInt(arrayOfNumber.trim());


                            if (value < 1) {
                                blackBags.get(i).clear(); //Removes all pebble elements if the files has an illegal number
                                exceptionType = "invalidPebbleWeight";
                                throw new IllegalArgumentException("All pebbles in the file must be a positive integer");
                            }

                            blackBags.get(i).add(value);
                        }
                    }

                    if (blackBags.get(i).size() < 11*playerCount) {
                        exceptionType = "notEnoughPebbles";
                        System.out.println("does this happen");
                        throw new IllegalArgumentException("The number of pebbles must be more than the number of players multiplied by 11");
                    }

                    for (int j=0; j<blackBags.get(i).size(); j++) {
                        System.out.println(blackBags.get(i).get(j));
                    }
                    flag = true;
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR: no file is was found") ;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    if (exceptionType.equals("invalidPebbleWeight")){
                        System.out.println("ERROR: this file must only contain positive values for each pebble");
                    } else if (exceptionType.equals("notEnoughPebbles")){
                        System.out.println("ERROR: this file must contain at least 11 times as many pebbles as players");

                    }
                }
            }
        }
    }
}
