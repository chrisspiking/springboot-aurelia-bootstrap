package uk.co.bitstyle.sbab.services.dao.user;

/**
 * @author cspiking
 */
public class AppUserDaoOpResult<T> {

    private final boolean success;

    private final T resultObject;

    private final String message;
    private final Exception exception;

    public boolean isSuccess() {
        return success;
    }

    public T getResultObject() {
        return resultObject;
    }

    public String getMessage() {
        return message;
    }

    public Exception getException() {
        return exception;
    }

    public AppUserDaoOpResult(boolean success, T resultObject, String message, Exception exception) {
        this.success = success;
        this.resultObject = resultObject;
        this.message = message;
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "AppUserDaoOpResult{" +
               "success=" +
               success +
               ", resultObject=" +
               resultObject +
               ", message='" +
               message +
               '\'' +
               ", exception=" +
               exception +
               '}';
    }

    public static final class Builder<T> {
        private boolean success;
        private T resultObject;
        private String message;
        private Exception exception;

        private Builder() {
        }

        public static <T> Builder<T> anAppUserDaoOpResult() {
            return new Builder<>();
        }

        public AppUserDaoOpResult<T> build() {
            return new AppUserDaoOpResult<>(success, resultObject, message, exception);
        }

        public Builder<T> withException(Exception exception) {
            this.exception = exception;
            return this;
        }

        public Builder<T> withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> withResultObject(T resultObject) {
            this.resultObject = resultObject;
            return this;
        }

        public Builder<T> withSuccess(boolean success) {
            this.success = success;
            return this;
        }
    }
}
