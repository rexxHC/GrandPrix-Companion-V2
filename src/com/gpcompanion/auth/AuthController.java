package com.gpcompanion.auth;

public class AuthController {
    private final AuthService authService;
    private final SessionContext session;

    public AuthController(AuthService authService, SessionContext session) {
        this.authService = authService;
        this.session = session;
    }

    public void handleLogin(String username, String password) throws AuthenticationException {
        authService.login(username, password);
    }

    public void handleRegister(String username, String password) throws DuplicateUserException {
        authService.register(username, password);
    }

    public SessionContext getSession() {
        return this.session;
    }
}