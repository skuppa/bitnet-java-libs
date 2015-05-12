# Overview
A list of improvements if this project was using Java 8.

# Java 8
 
Rather than using a try catch block each call against the bitnet API could be wrapped using:
 
```java
/**
 * Safely make request, logging any exceptions and returning an optional object.
 *
 * @param call request
 * @param <T>  return type
 * @return object wrapped as optional
 */
private static <T> Optional<T> call(Supplier<T> call) {
    try {
        return Optional.of(call.get());
    } catch (BitnetException | BitnetRetryableException | EncodeException e) {
        handleBitnetException(e);
    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }

    return Optional.ofNullable(null);
}
```
# Spark 2
Spark 2 requires Java 8 but provides a nicer approach to adding the notification web hook

```java
private static void startNotificationsWebhook() {
    post("/webhook", (request, response) -> {
        Map headers = new ImmutableMap.Builder<String, String>()
                .put("Digest", request.headers("Digest"))
                .put("Date", request.headers("Date"))
                .put("Authorization", request.headers("Authorization"))
                .build();
        if (notificationHelper.isVerifiedNotification(headers, request.body())) {
            return handleNotification(request);
        } else {
            return badRequestResponse(request, response, headers);
        }
    });
}
```    