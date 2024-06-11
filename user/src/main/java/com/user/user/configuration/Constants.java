package com.user.user.configuration;

public class Constants {

    private Constants(){
        throw new IllegalStateException("utility class");
    }

    public static final String PHONE_NUMBER_NOT_VALID_MESSAGE = "El numero de telefono no es valido";

    public static final Long ROLE_ADMIN = 1L;
    public static final Long ROLE_TEACHER = 2L;
    public static final Long ROLE_STUDENT = 3L;
    public static final String USER_SAVED_MESSAGE = "Usuario Creado Satisfactoriamente";

    public static final String PASSWORD_MISMATCH_EXCEPTION = "La clave no coincide";

    public static final String USER_NOT_EXIST_MESSAGE = "El usuario con el email %s no existe";

    public static final String ACCESS_DENIED = "No tiene los permisos necesarios para realizar esta operación";

    public static final String PHONE_NUMBER_REGEX = "^(300|3(?:0[1-3]|[1-2]\\d|3[0-3]))\\d{7}$";

    public static final String USER_ALREADY_EXISTS = "El usuario %s ya existe";

    public static final String ADMIN_CREATED = "Administrador Creado Satisfactoriamente";

    public static final String TEACHER_CREATED = "Tutor Creado Satisfactoriamente";

    public static final String STUDENT_CREATED = "Estudiante Creado Satisfactoriamente";

}
