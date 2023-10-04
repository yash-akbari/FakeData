package edu.fakedata;

//All imports
import static java.lang.System.exit;
import static java.lang.System.out;


public class Admin extends FunctionalityTools{

	 /*Admin class extends Functionality class to use some of its methods
     Here FunctionalityTools class acts as Parent Class, while Admin acts as a child class*/
	void menu() {// main menu
		clearScreen();
		out.println("1. Add User");
		out.println("2. Modify or Remove User");
		out.println("3. Check Logs");
		out.println("4. Exit");

		switch (userIntInput("Enter Row Number: "))
		{
			case 1 -> {
				clearScreen();
				addUser();
			}
			case 2 -> {
				clearScreen();
				modifyUser();
			}
			case 3 -> {
				clearScreen();
				readLog();
				menu();
			}
			case 4 ->exit(0);
			default -> {
				clearScreen();
				out.println("Wrong input");
				enterToContinue();
				menu();
			}
		}
	}

	void addUser() {
		String usernameInput=userStringInput("Enter User Name : ");
		if(userName.equals(usernameInput))//checks if user exist
		{
			clearScreen();
			out.println("User already Exist");
			enterToContinue();
			menu();
		}
		else
		{
			//if not  adds to the list
			userName.add(usernameInput);
			isResetPassword.add(true);
			password.add("default");
			writeCred();
			viewUser();
			enterToContinue();
			menu();
		}
	}
	void viewUser()
	{//prints all user on display
		clearScreen();
		readCred();
		for(int i=0;i<userName.size();i++) {
			out.printf("%2d %5s %b\n",(i+1),userName.get(i),isResetPassword.get(i));
		}
	}
	void modifyUser()
	{
		viewUser();
		int rowModifyNum;
		out.println("\n\n0. to Go back to Main menu");
		out.println("1. to Modify or Delete row");
		switch (userIntInput("Enter Row Number : ")){
			case 1 ->
			{
				try
				{
					rowModifyNum =userIntInput("\n\nEnter Row Number to Modify or Delete : ");
					rowModifyNum--;

					if(rowModifyNum==0)
					{
						clearScreen();
						System.out.println("Admin can't be Modified");
						enterToContinue();
						menu();
					}
					else{
						out.printf("%2d %5s %b\n",(rowModifyNum+1),userName.get(rowModifyNum),isResetPassword.get(rowModifyNum));
						modifier(rowModifyNum);
					}
				}
				catch (IndexOutOfBoundsException e) // if entered row doesn't exist for modification
				{
					out.println("Row Number doesn't Exist");
					enterToContinue();
					modifyUser();
				} catch (Exception e)
				{
					out.println(e.getMessage());
				}
			}
			case 0-> menu();
			default ->
			{
				out.println("Wrong Input");
				enterToContinue();
				modifyUser();
			}
		}
	}

	void modifier(int rowModifyNum){
		out.println("1: to Modify the user name");
		out.println("2: to Modify the Is forgotten password state");
		out.println("3: to Remove the user name");
		switch (userIntInput("Enter Row Number: ")) {
			case 1 -> {
				userName.set(rowModifyNum,userStringInput("Enter User Name : ")); //to replace new username at specified row
				writeCred();
				viewUser();
				out.println("Changes Made Successfully");
				enterToContinue();
				menu();
			}
			case 2 -> {
				out.println("Press any key to change state");// can be used to reset password
				if (!isResetPassword.get(rowModifyNum)) {
					isResetPassword.set(rowModifyNum, true);
				} else {
					isResetPassword.set(rowModifyNum, false);
				}
				writeCred();
				viewUser();
				out.println("Changes Made Successfully");
				enterToContinue();
				menu();
			}
			case 3 -> {// used to delete user at specified user
					userName.remove(rowModifyNum);
					isResetPassword.remove(rowModifyNum);
					password.remove(rowModifyNum);
					writeCred();
					out.println("Changes Made Successfully");
				enterToContinue();
				menu();
			}
			default -> {
				out.println("Wrong input");
				enterToContinue();
				modifyUser();
			}
		}
	}
}