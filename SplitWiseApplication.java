import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.StringTokenizer;


public class SplitWiseApplication {

   
    
    private static List<User> listOfAllUsers;
    public static void main(String[] args){

        System.out.println("SPLIT WISE APPLICATION");
        //Creating Users
        createUsers();
       
        SplitWiseService splitWiseService=new SplitWiseService();
        System.out.println("Press 1 to Enter Expenses OR Press 2 To View Balance");
        int userInput=ScannerUtils.readInt();
        switch(userInput){
        //For entering expenses
        case 1:System.out.println("Enter Expenses in the format:");
        System.out.println("EXPENSE <user-id-of-person-who-paid> <expense-amount> <no-of-users> <space-separated-list-of-users> <EQUAL/EXACT/PERCENT> <space-separated-values-in-case-of-non-equal>");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
			String nextLine=null;
			try {
				nextLine = reader.readLine();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
        List<String> expenseTokens=tokenizeInput(nextLine);
        
        int currentCount=0;
       
        if(null!=expenseTokens && !expenseTokens.isEmpty()&& expenseTokens.get(currentCount++).equals("EXPENSE")){
        	String userIdOfPersonWhoPaid=expenseTokens.get(currentCount++);
        	String expenseAmount=expenseTokens.get(currentCount++);
           
            int noOfUsers=0;
            try{
            	
            	noOfUsers=Integer.parseInt(expenseTokens.get(currentCount++));
            }catch(IllegalFormatException e){
            	System.out.println("Invalid input");
            }
            //List of users to pay expense
            List<String> usersToPayExpense=new ArrayList<>();
            for(int i=0;i<noOfUsers;i++){
            	usersToPayExpense.add(expenseTokens.get(currentCount++));
            }
            //Expense Type
            Expense expense=Expense.valueOf(expenseTokens.get(currentCount++));
            String expenseType=null!=expense?expense.toString():null;
            
            //Shares of expense
            List<String> expenseShares=new ArrayList<>();
            switch(expenseType){
            case "EQUAL":
            	splitWiseService.calculateExpenseByEqual(userIdOfPersonWhoPaid,expenseAmount,usersToPayExpense);
            	break;
            case "EXACT":
            	for(int i=0;i<noOfUsers;i++){
            		expenseShares.add(expenseTokens.get(currentCount++));
            	}
            	splitWiseService.calculateExpenseByExact(userIdOfPersonWhoPaid,expenseAmount,usersToPayExpense,expenseShares);
            	break;
            case "PERCENTAGE":
            	for(int i=0;i<noOfUsers;i++){
            		expenseShares.add(expenseTokens.get(currentCount++));
            	}
            	splitWiseService.calculateExpenseByPercentage(userIdOfPersonWhoPaid,expenseAmount,usersToPayExpense,expenseShares);
            	break;
            case "SHARE":
            	//optional requirement
            	break;
            default:
            	System.out.println("Invalid expense type");
            	break;
            }
            
            
        }
        
        break;
        
        //for displaying expenses
        case 2:System.out.println("Enter SHOW to Show All User Balances OR Enter SHOW <userId> to Show Any User Balance");
        String[] showBalanceTokens=ScannerUtils.readString().split(" ");
        String showBalanceInput=showBalanceTokens[0];
        String userId=showBalanceTokens[1];
        if(Expense.valueOf("SHOW").equals(showBalanceInput.toUpperCase()) && (null==userId || userId.isEmpty())){
        	List<UserExpense> allUserExpenses=splitWiseService.showAllUserBalances();
        	if(null!=allUserExpenses && !allUserExpenses.isEmpty()){
        		for(UserExpense userExpenses:allUserExpenses){
            		System.out.println(userExpenses.getBalanceOwner()+"owes"+userExpenses.getUserOwesTo()+":  "+userExpenses.getBalanceAmount());
            	}
        	}else{
        		System.out.println("No Balances");
        	}
        	
        }else if(Expense.valueOf("SHOW").equals(showBalanceInput.toUpperCase()) && null!=userId && !userId.isEmpty()){
        	
        	List<UserExpense> userExpenses=splitWiseService.getAllExpensesByUserId(userId);
        	if(null!=userExpenses && !userExpenses.isEmpty()){
        		for(UserExpense userExpenseObject:userExpenses){
            		System.out.println(userExpenseObject.getBalanceOwner()+"owes"+userExpenseObject.getUserOwesTo()+":  "+userExpenseObject.getBalanceAmount());
            	}
        		
        	}else{
        		System.out.println("No Balances");
        	}
        }
        else{
        	System.out.println("Invalid Input");
        }
        break;
        default:
        	System.out.println("Invalid Input");
        }
    }
    private static List<String> tokenizeInput(String nextLine) {
    	List<String> tokens=new ArrayList<>();
    	StringTokenizer st = new StringTokenizer(nextLine);

		while (st != null && st.hasMoreElements()) {
			tokens.add(st.nextToken());
		}

		return tokens;
	}
	private static void createUsers(){
    	listOfAllUsers=new ArrayList<>();
        User user1=new User("u1", "James", "james123@gmail.com", null);
        User user2=new User("u2","Ramesh","ramesh257@gmail.com",null);
        User user3=new User("u3","Jatin",null,null);
        User user4=new User("u4","Rohan","rohan1234@gmail.com",null);
        listOfAllUsers.add(user1);
        listOfAllUsers.add(user2);
        listOfAllUsers.add(user3);
        listOfAllUsers.add(user4);
        
    }
}