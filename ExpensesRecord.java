
import java.util.List;
import java.util.Map;

public class ExpensesRecord {
	private static Map<String,List<UserExpense>> userExpensesMap;

	
	
	
	public static Map<String,List<UserExpense>> getUserExpensesMap() {
		return userExpensesMap;
	}

	public static void setUserExpensesMap(Map<String,List<UserExpense>> userExpensesMap) {
		ExpensesRecord.userExpensesMap=userExpensesMap;
	}

}
