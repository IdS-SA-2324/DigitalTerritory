package it.unicam.cs.ids.digitalterritory.utils;

import it.unicam.cs.ids.digitalterritory.dto.Response;

public class ResponseFactory {
    public static <T> Response<T> createFromResult(T result, boolean isSuccess, String error) {
        return new Response<>(result, isSuccess, error);
    }

    public static <T> Response<T> createFromResult(T result) {
        return createFromResult(result, true, "");
    }
}
