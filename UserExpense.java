public class UserExpense{

	private String balanceOwner;
    private String userOwesTo;
    private String balanceAmount;

    public UserExpense(String userOwesTo,String balanceAmount){
        this.userOwesTo=userOwesTo;
        this.balanceAmount=balanceAmount;
    }

    public String getUserOwesTo() {
        return this.userOwesTo;
    }

    public void setUserOwesTo(String userOwesTo) {
        this.userOwesTo = userOwesTo;
    }

    public String getBalanceAmount() {
        return this.balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

	public String getBalanceOwner() {
		return balanceOwner;
	}

	public void setBalanceOwner(String balanceOwner) {
		this.balanceOwner = balanceOwner;
	}

}