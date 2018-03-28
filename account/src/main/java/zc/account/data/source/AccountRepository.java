package zc.account.data.source;

import android.support.annotation.NonNull;

public class AccountRepository implements AccountDataSource {
    private static AccountRepository INSTANCE = null;

    private final AccountDataSource mRemoteDataSource;

    private final AccountDataSource mLocalDataSource;

    private AccountRepository(@NonNull AccountDataSource remoteDataSource,
                              @NonNull AccountDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public static AccountRepository getInstance(AccountDataSource tasksRemoteDataSource,
                                                AccountDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new AccountRepository(tasksRemoteDataSource, tasksLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
