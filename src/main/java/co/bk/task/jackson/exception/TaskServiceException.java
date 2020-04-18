package co.bk.task.jackson.exception;

/**
 * Business logic exception.
 */
public class TaskServiceException extends RuntimeException {

    private transient ErrorCode errorCode;

    /**
     * Constructor.
     *
     * @param errorCode errorCode.
     */
    public TaskServiceException(final ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage());
    }

    /**
     * Constructor.
     *
     * @param errorCode errorCode.
     * @param params one or more params to output in message that contains param placeholders.
     */
    public TaskServiceException(final ErrorCode errorCode, final Object... params) {
        this(errorCode, String.format(errorCode.getMessage(), params), null);
    }

    /**
     * Construct exception with underlining exception.
     *
     * @param errorCode errorCode.
     * @param cause     - {@link Throwable} underlining exception.
     */
    public TaskServiceException(final ErrorCode errorCode, final Throwable cause) {
        this(errorCode, errorCode.getMessage(), cause);
    }

    /**
     * Construct exception with underlining exception.
     *
     * @param errorCode errorCode.
     * @param cause     - {@link Throwable} underlining exception.
     * @param params one or more params to output in message that contains param placeholders.
     */
    public TaskServiceException(final ErrorCode errorCode, final Throwable cause,
                                final Object... params) {
        this(errorCode, String.format(errorCode.getMessage(), params), cause);
    }

    /**
     * Constructor.
     *
     * @param errorCode error code.
     * @param message   message.
     */
    public TaskServiceException(final ErrorCode errorCode, final String message,
                                final Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }


    /**
     * Return the ErrorCode.
     * @return - ErrorCode
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Return the unique application error code.
     *
     * @return an error code or &quot;notdefined&quot;
     */
    public String getApplicationCode() {
        String applicationCode = "undefined";
        if (errorCode != null) {
            applicationCode = errorCode.getApplicationCode();
        }
        return applicationCode;
    }

    /**
     * Error Code enum.
     */
    public enum ErrorCode {

        RESERVED_FOR_UNKNOWN_ERRORS("ACCESSAPI-0", ""),
        ACCESS_REQUEST_INCOMPLETE("ACCESSAPI-1", "404 AccessRequest details submitted are incomplete or invalid."),
        ACCESSREQUEST_NOT_FOUND("ACCESSAPI-2", "404 AccessRequest not found for UUID: %s"),
        INVALID_S3_PATH_FOR_IAM_POLICY("ACCESSAPI-3", "400 IAM role could not be created due to invalid S3 bucket name format: %s"),
        INVALID_DATA_FOR_IAM_POLICY("ACCESSAPI-4", "400 IAM role could not be created due to empty bucketArns"),
        INVALID_METADATA_FROM_MIRRODIN("ACCESSAPI-5", "400 Invalid metadata received from Mirrodin. Dataitem key(group:artifact:version) was: %s"),
        METADATA_SERVICE_PARSE_ERROR("ACCESSAPI-6", "400 Conversion of MetadataSubscription object to JSON for SQS message failed.");

        private String applicationCode;
        private String message = "No message provided";

        private ErrorCode() {
        }

        /**
         * Private constructor only allows objects to be constructed from within the class.
         */
        private ErrorCode(final String applicationCode, String message) {
            this.applicationCode = applicationCode;
            this.message = message;
        }

        public String getApplicationCode() {
            return applicationCode;
        }

        public String getMessage() {
            return message;
        }

    }
}

