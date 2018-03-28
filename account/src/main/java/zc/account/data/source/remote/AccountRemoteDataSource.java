package zc.account.data.source.remote;


import zc.account.data.source.AccountDataSource;

public class AccountRemoteDataSource implements AccountDataSource{

    private static volatile AccountRemoteDataSource INSTANCE;

    private AccountRemoteDataSource() {
    }

    public static AccountRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (AccountRemoteDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AccountRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }
}
