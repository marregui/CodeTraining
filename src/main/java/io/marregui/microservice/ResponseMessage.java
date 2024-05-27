package io.marregui.microservice;

import io.marregui.util.ThrlStringBuilder;

public class ResponseMessage<T> {
    public enum Status {
        Success, Failure;
    }

    /**
     * When everything went ok
     *
     * @param obj
     * @return status == Success
     */
    public static <T> ResponseMessage<T> success(T obj) {
        return new ResponseMessage<>(Status.Success, obj);
    }

    /**
     * When something went coo-coo
     *
     * @param obj
     * @return status == Failure
     */
    public static <T> ResponseMessage<T> failure(T obj) {
        return new ResponseMessage<>(Status.Failure, obj);
    }

    private final Status status;
    private final T cargo;

    private ResponseMessage(Status status, T cargo) {
        if (null == cargo) {
            throw new IllegalArgumentException("null");
        }
        this.status = status;
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        StringBuilder sb = ThrlStringBuilder.get();
        sb.append("{status=").append(status).append(", cargo='").append(cargo).append("'}");
        return sb.toString();
    }
}
