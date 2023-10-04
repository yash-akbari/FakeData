package edu.fakedata; //package


// All Internal and External Imports
import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.company.Company;
import com.devskiller.jfairy.producer.payment.IBAN;
import com.devskiller.jfairy.producer.payment.IBANProperties;
import com.devskiller.jfairy.producer.person.Person;
import com.devskiller.jfairy.producer.person.PersonProperties;
import com.opencsv.CSVWriter;
import net.datafaker.Faker;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

//static import
import static java.lang.System.*;

public class Developer extends FunctionalityTools{

    /*Developer class extends Functionality class to use some of its methods
     Here FunctionalityTools class acts as Parent Class, while Developer acts as a child class*/

    //All variables
    static ArrayList<String> fieldName= new ArrayList<>();// to store user selected fields
    static ArrayList<String> columnName= new ArrayList<>();// to give unique name to particular data column as per developer's preference
    static ArrayList<String> generatedData= new ArrayList<>();// to store generated data


// all methods
    void menu()// main menu
    {
        out.println("1. To generate data");
        out.println("2. Check Own Log");
        out.println("\n0. Exit\n\n");

        switch (userIntInput("Enter Row Number to Select Menu : "))
        {
            case 1 ->generationConfig(false);

            case 2 ->viewOwnLogs();
            case 0 ->exit(0);

            default -> {
                out.println("Wrong Input");
                enterToContinue();
                menu();
            }
        }
    }
    void generationConfig(Boolean isToModify) { // isTModify used to determine whether loop running for replacement of field-name
        clearScreen();// to clear screen
        /* Separated Fields to differentiate Each section */
        out.println("1. Personal");
        out.println("2. Bank");
        out.println("3. Company");
        out.println("4. Address");
        if (!isToModify)
        {  //if this function is not called for-replacement field name
            out.println("5. To see selected Configuration");
            out.println("6. If you Completed Selection of Fields");
            out.println("0. Exit");

            switch (userIntInput("\n\nEnter row Number : ")) {
                case 1 -> personalFields(isToModify);
                case 2 -> bankFields(isToModify);
                case 3 -> companyFields(isToModify);
                case 4 -> locationFields(isToModify);
                case 5 -> userSelectedFields();
                case 6 -> {
                    int rowCount = userIntInput("\nGive input for Number of row to Generate : ");
                    generateData(rowCount);
                }
                case 0 -> exit(0);
                default -> {
                    clearScreen();
                    out.println("Wrong Input");
                    enterToContinue();
                    generationConfig(isToModify);
                }
            }
        }
        else {//if running for replacement loop-it won't show exit or some other options
            switch (userIntInput("\nEnter row Number : ")) {
                case 1 -> personalFields(isToModify);
                case 2 -> bankFields(isToModify);
                case 3 -> companyFields(isToModify);
                case 4 -> locationFields(isToModify);
                default -> {
                    clearScreen();
                    out.println("Wrong Input");
                    enterToContinue();
                    generationConfig(isToModify);
                }
            }
        }
    }

    void userSelectedFields()  { // shows user selected fields configuration with its index number, field name and column Name
    	clearScreen();
        out.format(" %s %20s %17s","Row","FieldName","ColumnName\n");

        for (int i=0;i<fieldName.size();i++)
        {
            out.format("%3d %16s %15s\n",(i+1),fieldName.get(i),columnName.get(i));

        }
        // asks whether here to modify or to view
        out.println("\n\n0. to Go back to Main menu");
        out.println("1. to Modify or Delete row\n");
        
        switch (userIntInput("\nEnter row Number : ")){
            case 1 -> {
                try { 
                    int rowModNum = userIntInput("\n\nEnter Row Number to Modify or Delete : ");
                    rowModNum--;
                    configModifier(rowModNum);
                } catch (IndexOutOfBoundsException e) { // to notify user that row number doesn't exist
                    out.println("Row Number doesn't Exist");
                    enterToContinue();
                    userSelectedFields();
                } catch (Exception e) {
                    out.println(e.getMessage());
                }
            }    
            case 0-> generationConfig(false);// to go back to previous menu
            default -> {
                out.println("Wrong Input");
                enterToContinue();
                userSelectedFields();
            }
        }
    }
    
    void configModifier(int rowModNum)
    {
    	clearScreen();
    	out.format("%3d %16s %15s\n",(rowModNum +1),fieldName.get(rowModNum),columnName.get(rowModNum));
        out.println("\n\n1. To Modify Field(Field Name)");
        out.println("2. To Modify Column Name");
        out.println("3. To Delete Row\n");
        switch(userIntInput("\nEnter row Number : "))
        {
            case 1 ->{
            	Boolean isToModify=true;
                generationConfig(isToModify);
                userSelectedFields();
            }
            case 2 ->{// to replace specific column name
                String columnNameInput=userStringInput("Enter Column Name:- ");
                columnName.set(rowModNum,columnNameInput);
                userSelectedFields();
            }
            case 3->{
                fieldName.remove(rowModNum);// to delete specified rows
                columnName.remove(rowModNum);
                userSelectedFields();
            }
            default -> configModifier(rowModNum);
        }
    }
    /*From here 4 functions are defined which are used to get configuration details from user
    * in these methods there is a methods setFieldName, which replaces the field-name at specific row*/
    void personalFields(Boolean isToModify)
    {
        clearScreen();// to clear Screen
        out.println("1. First name");
        out.println("2. Last name");
        out.println("3. Middle name");
        out.println("4. Full name");
        out.println("5. Gender");
        out.println("6. BirthDate");
        out.println("7. Phone Number");
        out.println("8. Email Id");
        out.println("9. User Id");
        out.println("10. Pet");
        out.println("11. Passport");
        out.println("12. University");
        out.println("13. Study Level\n\n");
        out.println("0. To go Back to Separate Fields Menu \n\n");
        switch (userIntInput("\nEnter row Number : ")) {

            case 1 -> {
                out.println("1. Mixed Gender Name");
                out.println("2. Male Name");
                out.println("3. Female Name\n\n");
                switch (userIntInput("\nEnter row Number : ")) {
                    case 1 -> setFieldName(isToModify, "FirstName", 1,1);
                    case 2 -> setFieldName(isToModify, "FirstMale", 1,1);
                    case 3 -> setFieldName(isToModify, "FirstFemale", 1,1);
                    default -> {
                        clearScreen();
                        out.println("Wrong Input");
                        enterToContinue();
                        personalFields(isToModify);
                    }
                }
            }


            case 2 -> setFieldName(isToModify,"LastName",1,1);
            case 3 -> setFieldName(isToModify,"MiddleName",1,1);
            case 4 -> setFieldName(isToModify,"FullName",1,1);
            case 5 -> setFieldName(isToModify,"Gender",1,1);
            case 6 -> setFieldName(isToModify,"BirthDate",1,1);
            case 7 -> setFieldName(isToModify,"PhoneNumber",1,1);
            case 8 -> setFieldName(isToModify,"EmailId",1,1);
            case 9 -> setFieldName(isToModify,"UserId",1,1);
            case 10 -> setFieldName(isToModify,"Pet",1,1);
            case 11 -> setFieldName(isToModify,"Passport",1,1);
            case 12 -> setFieldName(isToModify,"University",1,1);
            case 13 -> setFieldName(isToModify,"StudyLevel",1,1);
            case 0 -> generationConfig(isToModify);
            default -> {
                clearScreen();
                out.println("Wrong Input");
                enterToContinue();
                personalFields(isToModify);
            }
        }

    }

    void bankFields(Boolean isToModify)
    {
    	clearScreen();
        out.println("1. NI Number");
        out.println("2. Bank Account Number");
        out.println("3. Sort Code");
        out.println("4. Credit card Number");
        out.println("5. Credit card Type\n\n");
        out.println("0. To go Back to Separate Fields Menu \n\n");

        switch (userIntInput("\nEnter row Number : "))
        {

            case 1 -> setFieldName(isToModify,"NINumber",2,1);
            case 2 -> setFieldName(isToModify,"BankAccountNumber",2,1);
            case 3 -> setFieldName(isToModify,"SortCode",2,1);
            case 4 -> setFieldName(isToModify,"CreditCardNumber",2,1);
            case 5 -> setFieldName(isToModify,"CreditCardType",2,1);
            case 0 -> generationConfig(isToModify);
            default -> {
                clearScreen();
                out.println("Wrong Input");
                enterToContinue();
                bankFields(isToModify);
            }
        }
    }

    void companyFields(Boolean isToModify)
    {
    	clearScreen();
        out.println("1. Company Name");
        out.println("2. Job Title");
        out.println("3. Website");
        out.println("4. Contact Email");
        out.println("5. Company Provided user email\n\n");
        out.println("0. To go Back to Separate Fields Menu \n\n");

        switch (userIntInput("\nEnter row Number : ")) {

            case 1 -> setFieldName(isToModify,"CompanyName",3,1);
            case 2-> setFieldName(isToModify,"Website",3,1);
            case 3 -> setFieldName(isToModify,"JobTitle",3,1);
            case 4 -> setFieldName(isToModify,"ContactEmail",3,1);
            case 5 -> setFieldName(isToModify,"CompanyProvEmail",3,1);
            case 0 -> generationConfig(isToModify);

            default -> {
                clearScreen();
                out.println("Wrong Input");
                enterToContinue();
                companyFields(isToModify);
            }
        }
    }
    void locationFields(Boolean isToModify)
    {
    	clearScreen();
        out.println("1.House Number");
        out.println("2.Street Name");
        out.println("3.City");
        out.println("4.State");
        out.println("5.Country");
        out.println("6.ZipCode\n\n");
        out.println("0. To go Back to Separate Fields Menu \n\n");

        switch (userIntInput("\nEnter row Number : ")) {
            case 1 -> setFieldName(isToModify,"HouseNo",4,1);
            case 2 -> setFieldName(isToModify,"StreetName",4,1);
            case 3 -> setFieldName(isToModify,"City",4,1);
            case 4 -> setFieldName(isToModify,"State",4,1);
            case 5 -> setFieldName(isToModify,"Country",4,1);
            case 6 -> setFieldName(isToModify,"ZipCode",4,1);
            case 0 -> generationConfig(isToModify);
            default -> {
                clearScreen();
                out.println("Wrong Input");
                enterToContinue();
                locationFields(isToModify);
            }
        }
    }


    /*In this function four arguments are used
    * where first argument is to identify whether this is called for modification or not
    * second argument is for particular field-name passing to store it in fieldName arraylist
    * func index specifies from where it was called and where it will be redirected after run
    * Last is for row number for modification purposes*/
    void setFieldName(Boolean isToModify,String selectedField,int funcIndex, int rowModNum){
        String columnNameInput = userStringInput("Enter Column-name:");
        if(isToModify)
        {
            fieldName.set(rowModNum,selectedField);
            columnName.set(rowModNum,columnNameInput);
        }
        else
        {
            fieldName.add(selectedField);
            columnName.add(columnNameInput);
            switch (funcIndex)
            {
                case 1 -> personalFields(isToModify);
                case 2 -> bankFields(isToModify);
                case 3 -> companyFields(isToModify);
                case 4 -> locationFields(isToModify);
            }
        }
    }

    String niNumberGeneration() {//this method returns niNumber
        int niNumbers = ThreadLocalRandom.current().nextInt(111111, 999998 + 1); // generates value between specified range
        String AlphaString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String niPrefix;
        {
            StringBuilder sb = new StringBuilder(2);

            for (int i = 0; i < 2; i++) {
                // assigns index a random number, which is used to retrieve character on that number's position
                int index = (int) (AlphaString.length() * Math.random());
                sb.append(AlphaString.charAt(index)); //appends index number's character
            }
            niPrefix = sb.toString();
        }
        StringBuilder sb = new StringBuilder(1);
        int index = (int) (AlphaString.length() * Math.random());
        sb.append(AlphaString.charAt(index));
        String niSuffix = sb.toString();

        //at the end  it appends whole ni number and appends it
        return niPrefix.concat((String.valueOf(niNumbers)).concat(niSuffix));
    }
    void generateData(int rowCount)//used to generate and store data
    {
        for(int iteration=0;iteration<rowCount;iteration++)// runs till user's specified row number
        {
            // all class objects called after a set of data generated
            Faker faker=new Faker();
            Fairy fairy = Fairy.create();
            Company company = fairy.company();
            Person person = fairy.person(PersonProperties.withCompany(company));

            // to generate bank account number, sortcode
            long bankAccountNumber=ThreadLocalRandom.current().nextInt(10000000, 99999998 + 1);
            int branchCode=ThreadLocalRandom.current().nextInt(100000, 999998 + 1);
            String AlphaString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuilder sb = new StringBuilder(4);
            for (int i = 0; i < 4; i++)
            {
                int index = (int)(AlphaString.length()* Math.random());
                sb.append(AlphaString.charAt(index));
            }
            String bankCode=sb.toString();
            IBAN iban= fairy.iban(IBANProperties.country("GB"),
                    IBANProperties.accountNumber((String.valueOf(bankAccountNumber))), IBANProperties.bankCode(bankCode),
                    IBANProperties.branchCode(String.valueOf(branchCode)),IBANProperties.nationalCheckDigit("12"));
            for(String i:fieldName) //runs on how many fields user selected
	        {
	            switch(i)
	            {//all of these cases adds data to generatedData arraylist
	            case "FirstName" -> generatedData.add(person.getFirstName());
	            case "FirstMale" -> {person=fairy.person(PersonProperties.withCompany(company),PersonProperties.male());
                    generatedData.add(person.getFirstName());
                }
	            case "FirstFemale" -> {person=fairy.person(PersonProperties.withCompany(company),PersonProperties.female());
                    generatedData.add(person.getFirstName());
                }
                case "MiddleName" -> generatedData.add(person.getMiddleName());
	            case "LastName" -> generatedData.add(person.getLastName());
	            case "FullName" -> generatedData.add(person.getFullName());
	            case "Gender" -> generatedData.add(String.valueOf(person.getSex()));
	            case "BirthDate" -> generatedData.add(String.valueOf(person.getDateOfBirth()));
	            case "PhoneNumber" ->generatedData.add(person.getMobileTelephoneNumber());
	            case "EmailId" ->generatedData.add(person.getEmail());
	            case "UserId" ->generatedData.add(person.getUsername());
                case "Passport" -> generatedData.add(faker.passport().valid());
	            case "University" -> generatedData.add(faker.university().name());
                case "StudyLevel" -> generatedData.add(faker.educator().course());
                case "Pet" -> generatedData.add(faker.animal().name());
	            case "NINumber" -> generatedData.add(niNumberGeneration());
	            case "BankAccountNumber" -> generatedData.add(iban.getAccountNumber());
	            case "SortCode" -> generatedData.add(iban.getBranchCode());
	            case "CreditCardNumber" -> generatedData.add(fairy.creditCard().getCardNumber());
	            case "CreditCardType" -> generatedData.add(fairy.creditCard().getVendor());
	            case "CompanyName" ->generatedData.add(company.getName());
	            case "JobTitle" -> generatedData.add(faker.company().profession());
	            case "Website" ->generatedData.add(company.getUrl());
	            case "ContactEmail" ->generatedData.add(company.getDomain());
	            case "CompanyProvEmail" -> generatedData.add(person.getCompanyEmail());
                case "HouseNo" -> generatedData.add(person.getAddress().getStreetNumber());
	            case "StreetName" -> generatedData.add(person.getAddress().getStreet());
	            case "City" -> generatedData.add(person.getAddress().getCity());
	            case "State" -> generatedData.add(faker.address().state());
	            case "Country" -> generatedData.add(faker.address().country());
	            case "ZipCode" -> generatedData.add(person.getAddress().getPostalCode());
                    default -> {}
                }
	        }
        }
        askFileType(rowCount);
    }

    void askFileType(int rowCount) // asks in which file type data needs to be stored, this can even few lines on console
    {
        clearScreen();
        String fileName;
        out.println("1. Text");
        out.println("2. Csv");
        out.println("3. Sql\n");
        out.println("4. See First few Rows On Screen");
        out.println("5. Exit");
        switch (userIntInput("Select Data Type : "))
        {
            case 1 -> {
                fileName=userStringInput("Enter Filename : ");
                txtWriter(fileName);
                }
            case 2 ->{
                fileName=userStringInput("Enter Filename : ");
                csvWriter(fileName);
            }
            case 3 ->{
                fileName=userStringInput("Enter Filename : ");
                sqlQueryGen(fileName);

            }
            case 4 ->{// printing lines of data to console
                // if data sets are more than 50 it will print only 50 otherwise whatever Number of row given
                int index=0;
                if(rowCount>50) {
                    for (int i = 0; i < (50 * columnName.size()); i++) {
                        out.print(++index);
                        for (String ignored : columnName) {
                            out.print(" " + generatedData.get(i) + " ");
                            i++;
                        }
                        out.print("\n");
                        i--;
                    }
                }
                else
                {
                    for (int i = 0; i < generatedData.size(); i++) {
                        out.print(++index);
                        for (String ignored : columnName) {
                            out.print(" " + generatedData.get(i) + " ");
                            i++;
                        }
                        out.print("\n");
                        i--;
                    }
                }

                enterToContinue();
                askFileType(rowCount);
            }
            case 5 ->System.exit(0);
            default -> {
                out.println("Wrong Input");
                enterToContinue();
                askFileType(rowCount);
            }
        }

    }
    void txtWriter(String fileName)
    {
        try{
            FileWriter writer = new FileWriter("./"+fileName+".txt");// creating file name
            for(int i=0;i<columnName.size();i++) //Prints Column name in heading
            {
                if(i<columnName.size()-1){
                    writer.write(columnName.get(i)+",");
                }
                else
                {
                    writer.write(columnName.get(i)+"\n");
                }

            }
            for (int i=0;i<generatedData.size();i++) // to access each data
            {
                String values;
                values="";
                for (int j=0;j< fieldName.size();j++)// to print data by set
                {
                    values=values.concat(generatedData.get(i));
                    if(j< fieldName.size()-1)
                    {
                        values=values.concat(",");
                        i++;
                    }
                    else if(i<generatedData.size()-1)
                    {
                        values=values.concat("\n"); // \n to indicate the end of file
                    }
                }
                writer.write(values);// writing values
            }
            writer.close();
            writeLog(fileName+".txt");// writes log
        
        }
        catch (NullPointerException e)
        {
            out.println("Name not given to file");
            enterToContinue();
            txtWriter(fileName);
        }
        catch (IOException e)
        {
            out.println(e.getMessage());
        }
        
    }
    void sqlQueryGen(String fileName)
    {
        try {
            FileWriter writer = new FileWriter("./"+fileName+".sql");// creating file name
            writer.write("INSERT INTO "+fileName + " (");//Prints Column name in insert into part of sql query
            for(int i=0;i<columnName.size();i++)
            {
                if(i<columnName.size()-1){
                    writer.write(columnName.get(i)+",");
                }
                else
                {
                    writer.write(columnName.get(i));
                }

            }
            writer.write(")\n VALUES");

            for (int i=0;i<generatedData.size();i++) // writes data as per sql query format
            {
                String values;
                values="(";
                for (int j=0;j< fieldName.size();j++)
                {
                    values=values.concat("'"+generatedData.get(i)+"'");
                    if(j< fieldName.size()-1)
                    {
                        values=values.concat(",");
                        i++;
                    }
                    else if(i<generatedData.size()-1)
                    {
                        values=values.concat("),\n"); //to show end of line
                    }
                    else
                    {
                        values=values.concat(");");// end of file
                    }
                }
                writer.write(values);// writing values

            }
            writer.close();
            writeLog(fileName+".sql");// writing log
        }
        catch (NullPointerException e)// occurs when name not given to file
        {
            out.println("Name not given to file");
            enterToContinue();
            sqlQueryGen(fileName);
        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }
    void csvWriter(String fileName)
    {

        try {
            File file = new File("./"+fileName+".csv"); // creating file name
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object file-writer object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            writer.writeNext(columnName.toArray(new String[0]));
            int size=columnName.size();
            for (int i=0;i<generatedData.size();i++)
            {
                ArrayList<String> dataWriter=new ArrayList<>();
                for (int j=0;j<size;j++)
                {
                    if(generatedData.get(i)==null)
                    {
                        dataWriter.add("null");
                    }
                    else
                    {
                        dataWriter.add(generatedData.get(i));
                    }
                    if(j<size-1) i++;
                }
                writer.writeNext(dataWriter.toArray(new String[0]));
            }
            // closing writer connection
            writer.close();
            writeLog(fileName+".csv");
        }
        catch (NullPointerException e)// occurs when file name not given
        {
            out.println("Name not given to file");
            enterToContinue();
            csvWriter(fileName);
        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }
    void viewOwnLogs()// to check current user's log
    {
        clearScreen();
        readLog();
        out.println("       Time                User     FileName");
        for(int i=0;i<logUser.size();i++)
        {
            if(logUser.get(i).equals(Main.userId))
            {
                char[] array=logTime.get(i).toCharArray();
                char[] tempAr=new char[13];
                int j=0;
                //Strips(") , as it gets increased each time type conversion happen
                for (char c : array) {
                    if (c != '"' && j < 13) {
                        tempAr[j] = c;
                        j++;
                    }
                }
                long millis=Long.parseLong(String.valueOf(tempAr));
                // converting time to exact date and time
                ZonedDateTime dateTime = Instant.ofEpochMilli(millis).atZone(ZoneId.of("UTC"));
                String formatted = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                out.printf("%19s %10s  %10s\n",formatted,logUser.get(i),logFile.get(i));
            }
        }
        enterToContinue();
        menu();
    }


}
