package com.example.apicontrolcrypt.domain.port.in;

import com.example.apicontrolcrypt.domain.model.AuthCredentials;
import com.example.apicontrolcrypt.domain.model.AuthToken;

public interface AuthUseCase {

    AuthToken authenticate(AuthCredentials credentials);
}
