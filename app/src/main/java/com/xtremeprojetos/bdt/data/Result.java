package com.xtremeprojetos.bdt.data;

public class Result<T> {
    // Caso de sucesso
    public static class Success<T> extends Result<T> {
        public T data;

        public Success(T data) {
            this.data = data;
        }
    }

    // Caso de erro
    public static class Error<T> extends Result<T> {
        public Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }
    }
}