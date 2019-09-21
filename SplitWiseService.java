import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SplitWiseService {
	
	private ExpensesRecord expensesRecord;
	
	public SplitWiseService(){
		this.expensesRecord=new ExpensesRecord();
	}
	
	

	public static List<UserExpense> showAllUserBalances() {
		List<UserExpense> expensesList=new ArrayList<>();
		for(Map.Entry<String, List<UserExpense>> entryOfExpenses:ExpensesRecord.getUserExpensesMap().entrySet()){
			expensesList.addAll(entryOfExpenses.getValue());
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
				userExpensesForEqual.add(new UserExpense(userIdOfPersonWhoPaid, totalShare.toString()));
				addToUserExpensesMap(userExpensesForEqual,expensesRecord);
			}
		}
		
		
	}

	private void addToUserExpensesMap(List<UserExpense> userExpensesList,ExpensesRecord expensesRecord) {
		
		Map<String,List<UserExpense>>userExpensesMap=new HashMap<>();
		for(UserExpense userExpenses:userExpensesList){
			if(userExpensesMap.get(userExpenses.getBalanceOwner())!=null){
				List<UserExpense> userExpenseList=userExpensesMap.get(userExpenses.getBalanceOwner());
				userExpenseList.add(userExpenses);
				userExpensesMap.put(userExpenses.getBalanceOwner(),userExpenseList);
				
			}else{
				userExpensesMap.put(userExpenses.getBalanceOwner(),new ArrayList<UserExpense>());
			}
			if(userExpensesMap.get(userExpenses.getUserOwesTo())!=null){
				List<UserExpense> userExpenseList=userExpensesMap.get(userExpenses.getUserOwesTo());
				userExpenseList.add(userExpenses);
				userExpensesMap.put(userExpenses.getUserOwesTo(),userExpenseList);
				
			}else{
				userExpensesMap.put(userExpenses.getUserOwesTo(),new ArrayList<UserExpense>());
			}
			
		}
	}

	private boolean expenseAmountContainsDot(String expenseAmount) {
		if(expenseAmount!=null && !expenseAmount.isEmpty() && expenseAmount.contains(".")){
				return true;
			}
		
	
		return false;
	}

	public void calculateExpenseByExact(String userIdOfPersonWhoPaid, String expenseAmount, List<String> usersToPayExpense, List<String> expenseShares) {
		List<UserExpense> userExpensesForEqual=new ArrayList<UserExpense>();
		Integer totalExpenseAmount=Integer.parseInt(expenseAmount);
		Integer totalShare=totalExpenseAmount/usersToPayExpense.size()+1;
		if(usersToPayExpense!=null){
			for(int i=0;i<usersToPayExpense.size();i++){
				userExpensesForEqual.add(new UserExpense(userIdOfPersonWhoPaid, totalShare.toString()));
			}
		}
		
		
	}

	public void calculateExpenseByPercentage(String userIdOfPersonWhoPaid, String expenseAmount,
			List<String> usersToPayExpense, List<String> expenseShares) {
		
		
	}

	
}
