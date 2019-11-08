package zc.account.data.source.local;


import zc.account.data.source.AccountDataSource;

public class AccountLocalDataSource implements AccountDataSource {

    private static volatile AccountLocalDataSource INSTANCE;

    private AccountLocalDataSource() {
    }

    public static AccountLocalDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (AccountLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AccountLocalDataSource();
                }
            }
        }
        return INSTANCE;
    }
}
