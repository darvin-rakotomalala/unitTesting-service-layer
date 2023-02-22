package com.poc.exception;

public enum ErrorsEnum {

    /**
     * ERR_MCS_POC
     */
    ERR_MCS_EMPLOYEE_NOT_FOUND("Error occurred - no Employee found with this id"),
    ERR_MCS_EMPLOYEE_ALREADY_EXIST("Error occurred - Employee already exist with this email");

    private final String errorMessage;

    private ErrorsEnum(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return " errorMessage : " + errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
