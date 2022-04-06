package br.edu.utfpr.pb.pw26s.server.controller;

import br.edu.utfpr.pb.pw26s.server.error.ApiError;
import br.edu.utfpr.pb.pw26s.server.error.UserNotValidException;
import br.edu.utfpr.pb.pw26s.server.model.User;
import br.edu.utfpr.pb.pw26s.server.repository.UserRepository;
import br.edu.utfpr.pb.pw26s.server.service.UserService;
import br.edu.utfpr.pb.pw26s.server.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    GenericResponse createUser(@Valid @RequestBody User user) {
        /* Não é uma boa prática validação nesse formato, pois temos
        regras de negócio no controller, e fica difícil manter novas validações
        if (user.getUsername() == null || user.getDisplayName() == null) {
            throw new UserNotValidException();
        }*/
        userService.save(user);
        return new GenericResponse("Registro salvo");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handlerValidationException(MethodArgumentNotValidException exception,
                                        HttpServletRequest request) {
        Map<String, String> validationErrors = new HashMap<>();

        for(FieldError error: exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        }

        return new ApiError(HttpStatus.BAD_REQUEST.value(), "validation error",
                request.getServletPath(), validationErrors);
    }

}
