package eu.europeana.corelib.db.exception;

/**
 * Exception about that user reached his/her API usage limit
 */
public class LimitReachedException extends Exception {

	/**
	 * The usage limit of a user
	 */
	private long limit;

	/**
	 * The number of API requests so far withing a given time period
	 */
	private long requested;

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new LimitReachedException
	 *
	 * @param limit
	 *   The usage limit of a user
	 * @param requested
	 *   The number of API requests so far withing a given time period
	 */
	public LimitReachedException(long limit, long requested) {
		super(String.format("Rate limit exceeded. Your limit: %s. Number of calls: %s.", limit, requested));
		this.limit = limit;
		this.requested = requested;
	}

	/**
	 * Gets the usage limit of a user
	 *
	 * @return
	 *   The usage limit of a user
	 */
	public long getLimit() {
		return limit;
	}

	/**
	 * Gets the number of API requests so far withing a given time period
	 *
	 * @return
	 *   The number of API requests so far withing a given time period
	 */
	public long getRequested() {
		return requested;
	}
}
