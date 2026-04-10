package com.hms.util;

import java.util.concurrent.*;

public final class Futures {

	private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	private Futures() {
	}

	public static <T> CompletableFuture<T> withTimeout(CompletableFuture<T> future, long timeout, TimeUnit unit) {

		CompletableFuture<T> timeoutFuture = new CompletableFuture<>();

		scheduler.schedule(
				() -> timeoutFuture
						.completeExceptionally(new TimeoutException("Timeout after " + timeout + " " + unit)),
				timeout, unit);

		return future.applyToEither(timeoutFuture, t -> t);
	}
}
