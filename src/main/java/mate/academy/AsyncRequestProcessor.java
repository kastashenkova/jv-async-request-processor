package mate.academy;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public class AsyncRequestProcessor {
    private static final Map<String, UserData> cache = new ConcurrentHashMap<>();
    private final Executor executor;

    public AsyncRequestProcessor(Executor executor) {
        this.executor = executor;
    }

    public CompletableFuture<UserData> processRequest(String userId) {
        return CompletableFuture.supplyAsync(() -> getUserData(userId),
                executor);
    }

    private static UserData getUserData(String userId) {
        UserData res;
        if (cache.get(userId) != null) {
            res = cache.get(userId);
        } else {
            res = new UserData(userId, "Details for " + userId);
            cache.put(userId, res);
        }
        return res;
    }
}
