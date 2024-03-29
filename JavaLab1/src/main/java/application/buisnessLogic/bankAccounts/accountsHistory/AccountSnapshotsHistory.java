package application.buisnessLogic.bankAccounts.accountsHistory;

import application.contracts.bankAccounts.AccountOperationResult;
import application.models.bankAccounts.Account;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

/**
 * Stores all previous states of account
 */
@NoArgsConstructor
public class AccountSnapshotsHistory {
    private ArrayList<IAccountSnapshot> snapshots = new ArrayList<>();

    /**
     * Restore last account state to undo transaction
     * @return last account state
     */
    public AccountOperationResult getLastAccountState(){
        if (snapshots.isEmpty()){
            return new AccountOperationResult.OperationError("No previous history");
        }
        snapshots.remove(snapshots.size()-1);
        return new AccountOperationResult.SuccessCheckingAccountHistory(snapshots.get(snapshots.size() - 1));
    }
    public AccountOperationResult addSnapshot(Account newAccountState){
        snapshots.add(new AccountSnapshot(newAccountState));
        return new AccountOperationResult.Success();
    }
}
