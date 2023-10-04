package edu.fakedata;//package name


//imports
import static java.lang.System.out;


public class Main extends FunctionalityTools{

	 /*Main class extends Functionality class to use some of its methods
     Here FunctionalityTools class acts as Parent Class, while Main acts as a child class*/

	public static String userId;// to store and keep it accessible from all classes and for all instances of all classes
	 String pwd;// to compare the user entered password
	 Admin admin=new Admin();// Later used to redirect as per username
	 Developer developer=new Developer();// Later used to redirect as per username


	Main()//default constructor
	{
		readCred(); // reading credentials
		writeCred();// writing credentials if not found

		try{
			console=System.console(); // assigning Console for better experience in console
		}
		catch(Exception e){
			login(); // if System.console returns null, it's an Ide, It will  simply call login method
		}
	}
	void login() {

		readCred(); // reading cred to verify cred with user input
		userId =userStringInput("Please Enter UserID:- ");
		
		if(console == null) //if null it won,t hide password in password Input
		{
			pwd=userStringInput("Please Enter Password:- ");
			clearScreen();
		}
		else //if console not null it will simply hide the password at input
		{ 
			pwd=String.valueOf(console.readPassword("Please Enter password:- "));
			clearScreen();
		}
		int index;
		if(userName.contains(userId))
		{
			index=userName.indexOf(userId); // takes index Number to verify user Provided details

			// if at the same index of username password matches the user entered password, it will let it log in or ask for reset password
			if(pwd.equals(password.get(index)))
			{
				if(index==0) // admin always stays at index 0
				{
					//welcome admin
					out.println("Hi Admin"); //Welcomes admin
					enterToContinue();
					admin.menu();
				}
				else if (isResetPassword.get(index)&&pwd.equals("default")) // if default password found, will ask to enter new password
				{
					String tempPwd;
					int i=0;
					do
					{
						if(i>0) out.println("Password Mismatched");
						pwd=userStringInput("Enter New password:- ");
						tempPwd=userStringInput("Enter New Password again:- ");
						i++;
					}
					while(!pwd.equals(tempPwd));
					password.set(index,pwd); // adds password at index of current user
					isResetPassword.set(index,false); // changes value at current user Index
					writeCred();
					clearScreen();
					login();// will ask for login again with new credentials
				}
				else
				{
					//welcome developer
					out.println("Hi Developer");
					enterToContinue();
					developer.menu();
				}
			}
			else
			{
				clearScreen();
				out.println("Wrong User Id or Password");
				login();
			}
		}
		else
		{
			clearScreen();
			out.println("Wrong User Id or Password");
			login();
		}
	}
	public static void main(String[] args) {
		Main m = new Main();
		m.login();
	}
}
