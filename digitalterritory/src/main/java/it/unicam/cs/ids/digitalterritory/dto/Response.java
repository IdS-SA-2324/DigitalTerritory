package it.unicam.cs.ids.digitalterritory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response<T> {
    private T result;
    private boolean isSuccess;
    private String error;
}
