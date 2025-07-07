package com.example.userms.exception;

import lombok.Getter;

@Getter
public class DeactivatedUserException extends RuntimeException {

    public DeactivatedUserException(String message) {
        super(message);
    }

    public DeactivatedUserException() {
        super("İstifadəçi hesabı deaktivləşdirilib. Sistem inzibatçısı ilə əlaqə saxlamağınız xahiş olunur.");
    }

}
