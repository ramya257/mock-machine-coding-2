import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SplitWiseService {
	
	private ExpensesRecord expensesRecord;
	
	public SplitWiseService(){
		this.expensesRecord=new ExpensesRecord();
	}
	
	
	public void calculateExpenseByEqual(String userIdOfPersonWhoPaid, String expenseAmount, List<String> usersToPayExpense) {
		List<UserExpense> userExpensesForEqual=new ArrayList<UserExpense>();
		Object totalExpenseAmount=null;
		Object totalShare=null;
		if(expenseAmountContainsDot(expenseAmount)){
			totalExpenseAmount=Float.parseFloat(expenseAmount);
			totalShare=(Float)totalExpenseAmount/usersToPayExpense.size();
		}else{
			totalExpenseAmount=Integer.parseInt(expenseAmount);
			totalShare=(Integer)totalExpenseAmount/usersToPayExpense.size();
		}
		
		
		if(usersToPayExpense!=null){
			for(int i=0;i<usersToPayExpense.size();i++){
				if(!usersToPayExpense.get(i).equals(userIdOfPersonWhoPaid)){
					userExpensesForEqual.addAll(updateBalance(usersToPayExpense.get(i),userIdOfPersonWhoPaid,totalShare));
				}
				
				
			}
			
			addToUserExpensesMap(userExpensesForEqual,expensesRecord);
		}
		
		
	}



	public void calculateExpenseByExact(String userIdOfPersonWhoPaid, String expenseAmount, List<String> usersToPayExpense, List<String> expenseShares) {
		List<UserExpense> userExpensesForExact=new ArrayList<UserExpense>();
		Integer totalExpenseAmount=Integer.parseInt(expenseAmount);
		Integer totalShare=0;
		if(usersToPayExpense.size()>expenseShares.size() || usersToPayExpense.size()<expenseShares.size()){
			System.out.println("Mismatch in the number of users to pay expense and expenses.");
			return;
		}
		for(String exactShare:expenseShares){
			totalShare+=Integer.parseInt(exactShare);
		}
		if(totalShare!=totalExpenseAmount){
			System.out.println("Expense Amount mismatch with the shares");
			return;
		}
		
		//
		
		if(usersToPayExpense!=null){
			for(int i=0;i<usersToPayExpense.size();i++){
				userExpensesForExact=updateBalance(usersToPayExpense.get(i),userIdOfPersonWhoPaid,totalShare);
			}
			addToUserExpensesMap(userExpensesForExact,expensesRecord);
		}
		
		
	}

	public void calculateExpenseByPercentage(String userIdOfPersonWhoPaid, String expenseAmount,
			List<String> usersToPayExpense, List<String> expenseShares) {
		List<UserExpense> userExpensesForPercentage=new ArrayList<UserExpense>();
		Integer totalExpenseAmount=Integer.parseInt(expenseAmount);
		Integer totalShare=0;
		if(usersToPayExpense.size()>expenseShares.size() || usersToPayExpense.size()<expenseShares.size()){
			System.out.println("Mismatch in the number of users to pay expense and expenses.");
			return;
		}
		for(String exactShare:expenseShares){
			totalShare+=Integer.parseInt(exactShare);
		}
		if(totalShare!=100){
			System.out.println("Expense Amount Percentages not equal to 100");
			return;
		}
		
		
		if(usersToPayExpense!=null){
			for(int i=0;i<usersToPayExpense.size();i++){
				Integer percentageShare=Integer.parseInt(expenseShares.get(i))/100;
				Integer share=totalExpenseAmount*percentageShare;
				userExpensesForPercentage.addAll(updateBalance(usersToPayExpense.get(i),userIdOfPersonWhoPaid,share));
				
			}
			addToUserExpensesMap(userExpensesForPercentage,expensesRecord);
		}
		
		
	}

	public List<UserExpense> showAllUserBalances() {
		List<UserExpense> expensesList=new ArrayList<>();
		if(ExpensesRecord.getUserExpensesMap()!=null){
			for(Map.Entry<String, List<UserExpense>> entryOfExpenses:ExpensesRecord.getUserExpensesMap().entrySet()){
				expensesList.addAll(entryOfExpenses.getValue());
			}
			
		}
		
		return expensesList;
	}

	public List<UserExpense> getAllExpensesByUserId(String userId) {
		List<UserExpense> expensesList=new ArrayList<>();
		for(Map.Entry<String, List<UserExpense>> entryOfExpenses:ExpensesRecord.getUserExpensesMap().entrySet()){
			if(listContainsUserId(entryOfExpenses.getValue(),userId)!=null){
				expensesList.add(listContainsUserId(entryOfExpenses.getValue(),userId));
			}
		}
		return expensesList;
	}

	private UserExpense listContainsUserId(List<UserExpense> value,String userId) {
		for(UserExpense userExpenseObj:value){
			if((userExpenseObj.getBalanceOwner()!=null && userExpenseObj.getBalanceOwner().equals(userId)) || 
					(userExpenseObj.getUserOwesTo()!=null && userExpenseObj.getUserOwesTo().equals(userId))){
				return userExpenseObj;
			}
		}
		return null;
	}
	private List<UserExpense> updateBalance(String usersToPayExpense, String userIdOfPersonWhoPaid, Object totalShare) {
		List<UserExpense> updatedExpenseList=new ArrayList<>();
		
			if(ExpensesRecord.getUserExpensesMap()!=null){
				List<UserExpense> userExpenseList=ExpensesRecord.getUserExpensesMap().get(usersToPayExpense);
				for(UserExpense userExpense:userExpenseList){
					if(usersToPayExpense.equals(userExpense.getBalanceOwner()) && userIdOfPersonWhoPaid.equals(userExpense.getUserOwesTo())){
						userExpense.setBalanceAmount(userExpense.getBalanceAmount()+totalShare);
						updatedExpenseList.add(userExpense);
					}else{
						UserExpense userExpenseObj=new UserExpense(usersToPayExpense, userIdOfPersonWhoPaid, totalShare.toString());
						updatedExpenseList.add(userExpenseObj);
						
					}
					
				}
			}else{
				UserExpense userExpenseObj=new UserExpense(usersToPayExpense, userIdOfPersonWhoPaid, totalShare.toString());
				updatedExpenseList.add(userExpenseObj);
			}
			return updatedExpenseList;
		}
		


	private void addToUserExpensesMap(List<UserExpense> userExpensesList,ExpensesRecord expensesRecord) {
		
		Map<String,List<UserExpense>>userExpensesMap=ExpensesRecord.getUserExpensesMap();
		List<UserExpense> userExpenseList=new ArrayList<>();
		for(UserExpense userExpenses:userExpensesList){
			if(userExpensesMap!=null && userExpensesMap.get(userExpenses.getBalanceOwner())!=null){
				userExpenseList=userExpensesMap.get(userExpenses.getBalanceOwner());
				userExpenseList.add(userExpenses);
				userExpensesMap.put(userExpenses.getBalanceOwner(),userExpenseList);
				
			}else{
				userExpensesMap=new HashMap<>();
				userExpenseList.add(userExpenses);
			userExpensesMap.put(userExpenses.getBalanceOwner(),userExpenseList);
			}
			if(userExpensesMap!=null && userExpensesMap.get(userExpenses.getUserOwesTo())!=null){
				userExpenseList=userExpensesMap.get(userExpenses.getUserOwesTo());
				userExpenseList.add(userExpenses);
				userExpensesMap.put(userExpenses.getUserOwesTo(),userExpenseList);
				
			}else{
				userExpensesMap=new HashMap<>();
				userExpenseList.add(userExpenses);
				userExpensesMap.put(userExpenses.getUserOwesTo(),userExpenseList);
			}
			
		}
		ExpensesRecord.setUserExpensesMap(userExpensesMap);
	}

	private boolean expenseAmountContainsDot(String expenseAmount) {
		if(expenseAmount!=null && !expenseAmount.isEmpty() && expenseAmount.contains(".")){
				return true;
			}
		
	
		return false;
	}


	
}
