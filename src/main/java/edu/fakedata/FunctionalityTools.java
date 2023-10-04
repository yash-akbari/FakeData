package edu.fakedata;


// all imports
import com.opencsv.CSVWriter;
import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

public class FunctionalityTools{  // Parent Class
    public static final String ALPHABET = "!#$%&'()*+-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";// all ASCII value for encryption and Decryption

    public static Scanner in =new  Scanner(System.in);// to take
    public static Console console;// For better Experience while running program in console
    public static ArrayList<String> userName = new ArrayList<>();// to store userid
    public static ArrayList<String> password = new ArrayList<>();// to store user password
    public static ArrayList<Boolean> isResetPassword = new ArrayList<>();// to store user password

    /*All Variables having initial log will be used to read write log File*/
    public static ArrayList<String> logUser=new ArrayList<>();// to store username
    public static ArrayList<String> logTime=new ArrayList<>();//to store time
    public static ArrayList<String> logFile=new ArrayList<>();// to store file name

    int userIntInput(String instruction)  { //to ensure user entered right input

        int userIntInput=0;
        try{
            out.print(instruction);// Instruction will be printed , which was passed in userIntInput
            userIntInput=in.nextInt();
        }
        catch(InputMismatchException i) //if user enters any other data type exception will be caught in this block
        {
            out.println("Wrong input");// to notify user that he/she Entered Wrong input
            enterToContinue(); // function to stop program and to show user the last output
            userIntInput=userIntInput(instruction); // asking again to enter the correct input
        }
        catch(Exception e)
        {
            out.println(e.getMessage()); //If any other exception occurs
        }
        return userIntInput;// returns the input given by user
    }
    String userStringInput(String instruction)  { //to ensure user entered right input

        String userStringInput="";
        try{
            out.print(instruction); // Instruction will be printed , which was passed in userIntInput
            userStringInput=in.next();
        }
        catch(InputMismatchException i) //if user enters any other data type exception will be caught in this block
        {
            out.println("Wrong input");// to notify user that he/she Entered Wrong input
            enterToContinue();
            userStringInput=userStringInput(instruction);
        }
        catch(Exception e)
        {
            out.println(e.getMessage());//If any other exception occurs
        }
        return userStringInput;// returns the input given by user
    }

    void enterToContinue()
    {
        out.println("Press Enter to Continue"); // To Stop whole programming processes
        in.nextLine();
        in.nextLine();
    }
    public static String encryptData(String inputData, int shiftKey)
    {

        // encryptData to store encrypted data
        StringBuilder encryptData = new StringBuilder();

        // use for loop for traversing each character of the input string
        for (int i = 0; i < inputData.length(); i++)
        {
            // get position of each character of inputData in ALPHABET
            int pos = ALPHABET.indexOf(inputData.charAt(i));

            // get encrypted char for each char of inputData
            int encryptPos = (shiftKey + pos) % 92;// 92 Characters to be used in the encryption, it returns the Number which is later used to relocate the replacement character
            char encryptChar = ALPHABET.charAt(encryptPos);

            // add encrypted char to encrypted String
            encryptData.append(encryptChar);
        }

        // return encrypted string
        return encryptData.toString();
    }

    public static String decryptData(String inputData, int shiftKey)
    {// decryptData to store decrypted data
        StringBuilder decryptData = new StringBuilder();

        // use for loop for traversing each character of the input string
        for (int i = 0; i < inputData.length(); i++)
        {
            // get position of each character of inputData in ALPHABET
            int pos = ALPHABET.indexOf(inputData.charAt(i));
            // get decrypted char for each char of inputData
            int decryptPos = (pos - shiftKey) % 92;// 92 Characters to be used in the decryption, it returns the Number which is later used to relocate the replacement character
            // if decryptPos is negative
            if (decryptPos < 0){
                decryptPos = ALPHABET.length() + decryptPos;
            }
            char decryptChar = ALPHABET.charAt(decryptPos);

            // add decrypted char to decrypted string
            decryptData.append(decryptChar);
        }
        // return decrypted string
        decryptData.deleteCharAt(0);
    /*  to  strip the first and last character of a word, as it was converted to the String,
 each time it adds " double inverted comma to string then it encrypted to other letter, and then it is converted to "o"*/
        int lstIndex=decryptData.lastIndexOf("o");
        decryptData.deleteCharAt(lstIndex);
        return decryptData.toString(); //returns the decrypted String
    }
    void writeCred() {
        try {
            FileWriter file = new FileWriter("./cred.csv"); // to generate or open the file
            CSVWriter reader = new CSVWriter(file); // File-reader object passed to CSV reader to write in csv file
            // Loop will run till the end of username values, so all values stored in the variable can be written.
            for (int i = 0; i < userName.size(); i++) {
                String[] dataToBeWritten = new String[3]; // To store all 3 values, which needs to be written in one line
                String tempUser = userName.get(i);
                String tempBool = (isResetPassword.get(i)).toString();
                String tempPwd="";
                if(isResetPassword.get(i))
                {
                    tempPwd = "default";
                }
                else
                {
                    tempPwd = password.get(i);
                }
                // to encrypt all data with key of 15
                dataToBeWritten[0] = (encryptData(tempUser, 15));
                dataToBeWritten[1] = (encryptData(tempBool, 15));
                dataToBeWritten[2] = (encryptData(tempPwd, 15));

                reader.writeNext(dataToBeWritten); // to write in the file line by line
            }
            reader.close(); // to close the file , otherwise file won't store a single data
        }
        catch (IOException e) {
            out.println(e.getMessage());
        }
    }

        void readCred()
        {
            try {
                /*All three arraylist are cleared, so it can not make multiple copies of data*/
                userName.clear();
                isResetPassword.clear();
                password.clear();
                /*Opening the file with Scanner class*/
                Scanner reader = new Scanner(new File("./cred.csv"));
                /*Declaring array list, Later used for storing data read from file */
                ArrayList<String> readData=new ArrayList<>();
                reader.useDelimiter(",");   //sets the delimiter pattern
                String tempString = "";

                while (reader.hasNext())  //returns a boolean value
                {
                    tempString = (tempString.concat(reader.nextLine())).concat(","); //concatenates each word with , to differentiate

                }
                Collections.addAll(readData, tempString.split(","));// splits each word using separator (,) coma
                for (int i = 0; i < readData.size(); i++) { // will run till last value of read data
                    for (int j = 0; j < 3; j++) { // will run three time, so that each value can be delivered to right variable
                        switch (j) {
                            case 0 -> {
                                /*//returns the decrypted data, and then being stored to variable*/
                                userName.add(decryptData(readData.get(i), 15));
                                i++; // incrementing after each instance so that next value can not miss match the variable
                            }
                            case 1 -> {
                                isResetPassword.add(Boolean.valueOf(decryptData(readData.get(i), 15)));
                                i++;
                            }
                            case 2 -> password.add(decryptData(readData.get(i), 15));
                            //i++ not used because, this is the end of loop, so if incremented all Array list  will mismatch the next value
                        }
                    }
                }
                reader.close(); // closing file
            }
            /* if file doesn't exist. For file doesn't Exist has its Unique Exception known as FileNotFoundException
            * but here Scanner class doesn't or never invoke that exception, this is the reason to use Exception class*/
        catch (Exception e)
        {
            /*As the file not found we have no more access to this program, to prevent that problem,
            Below values are added to three perspective arraylist and then called writeCred to write those file,
             so that admin can later add more user to program*/
            userName.add("admin");
            isResetPassword.add(false);
            password.add("default");
            userName.add("aru");
            isResetPassword.add(false);
            password.add("aru");

            writeCred();
        }
    }

/*Java Doesn't come with proper Screen or console cleaning Function, and if it needs to be imported*/
    /*Here Console class is used for that problem, Which has actual method to clear the console
    * But it can not be run in IDEs as IDEs comes with their special consoles
    * For that System.out.println() will run 50 times to work similarly as clear screen*/
    void clearScreen()
    {
        if(console!=null)// deciding whether it is console or IDE
        {
            try
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            for (int i = 0; i < 50; ++i) System.out.println();
        }
    }
    void writeLog(String fileName)
    {
        try
        {
            readLog(); // reading log file so that it don't miss the previous log

            // here currentTimeMillis returns epoch time, which is numeric form of time with date and time
            logTime.add(String.valueOf(currentTimeMillis()));

            //Here Main.userId returns the current user's Name whenever data is generated
            logUser.add(Main.userId);

            //filename returned to store in file, which used generated
            logFile.add(fileName);

            //File Writer object to open the file and pass it to CSV Writer
            FileWriter logUsers = new FileWriter("./log.csv");
            CSVWriter writer = new CSVWriter(logUsers);

            //Every time this loop will work parallel to each arraylist which is used in this function
            for(int i = 0;i< logUser.size();i++)
            {
                String[] dataToBeWritten=new String[3];
                char[] array=logTime.get(i).toCharArray();
                char[] tempAr=new char[13];
                int j=0;
                //this loop is used to strip (") Double inverted Comma, each time its get double if not stripped because of type conversion
                for (char c : array) {
                    if (c != '"' && j < 13) {
                        tempAr[j] = c;
                        j++;
                    }
                }
                String tempTime=String.valueOf(tempAr);
                String tempUser=logUser.get(i);
                String tempFile=logFile.get(i);
                // Storing in String array so that it can be used to write in one line
                dataToBeWritten[0]=(encryptData(tempTime,15));
                dataToBeWritten[1]=(encryptData(tempUser,15));
                dataToBeWritten[2]=(encryptData(tempFile,15));
                writer.writeNext(dataToBeWritten); //Writing in the file
            }
            writer.close();// closing file
        }
        catch (IOException e)
        {
            out.println(e.getMessage());
        }
    }

    void readLog() {
        try {

            /*All three arraylist are cleared, so it can not make multiple copies of data*/
            logTime.clear();
            logFile.clear();
            logUser.clear();

            /*Declaring array list, Later used for storing data read from file */
            ArrayList<String> readData = new ArrayList<>();

            /*Opening the file with Scanner class*/
            Scanner reader = new Scanner(new File("./log.csv"));
            reader.useDelimiter(",");   //sets the delimiter pattern
            String tempString = "";
            while (reader.hasNext())  //returns a boolean value
            {
                tempString = (tempString.concat(reader.nextLine())).concat(",");//concatenates each word with , to differentiate

            }
            Collections.addAll(readData, tempString.split(","));
            for (int i = 0; i < readData.size(); i++) { // will run till last value of read data
                for (int j = 0; j < 3; j++) { //// will run three time, so that each value can be delivered to right variable
                    switch (j) {
                        case 0 -> {
                            /*returns the decrypted data, and then being stored to variable*/
                            logTime.add(decryptData(readData.get(i), 15));
                            i++;  // incrementing after each instance so that next value can not miss match the variable
                        }
                        case 1 -> {
                            logUser.add(decryptData(readData.get(i), 15));
                            i++;
                        }
                        case 2 -> logFile.add(decryptData(readData.get(i), 15));
                        //i++ not used because, this is the end of loop, so if incremented all Array list  will mismatch the next value
                    }
                }
            }

            reader.close(); //closing File
        } catch (IOException e) {
            out.println("No Logs Available");
            new File("./log.csv");// if file doesn't exist file will be made
        }
    }
}
