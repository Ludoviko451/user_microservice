# Microservicio de Usuarios para OnClass

## Desafío:

Diseñar un microservicio de usuarios para la aplicación web OnClass que gestione usuarios con roles de estudiante, tutor o administrador.

## Descripción:

Este desafío se centra en el diseño de un microservicio de seguridad utilizando Spring Security para la aplicación web OnClass. El microservicio debe ser capaz de crear y gestionar usuarios con diferentes roles, como estudiante, tutor o administrador. Además, debe proporcionar funcionalidades de autenticación y autorización para garantizar que los usuarios tengan acceso adecuado a los recursos de la aplicación.

## Tecnologías Utilizadas:

| Tecnología | Descripción |
|------------|-------------|
| <img src="https://github.com/tandpfun/skill-icons/blob/main/icons/Java-Dark.svg" alt="Java Icon" width="50" height="50" /> | Java |
| <img src="https://github.com/tandpfun/skill-icons/blob/main/icons/Spring-Dark.svg" alt= "Spring icon" width="50" height="50"/> | Spring Boot |
| <img src="https://github.com/tandpfun/skill-icons/blob/main/icons/MySQL-Dark.svg" alt= "MySQL icon" width="50" height="50"/> | MySQL |
| <img src="https://github.com/tandpfun/skill-icons/blob/main/icons/Gradle-Dark.svg" alt= "MySQL icon" width="50" height="50"/> | Gradle |
## Pasos para Iniciar:

**Prerrequisitos:** Asegúrate de tener instalados JDK, Spring Boot y MySQL en tu sistema.

1. **Clonar el Repositorio:** Clona el repositorio de OnClass desde GitHub.
2. **Configuración de la Base de Datos:** Crea una nueva base de datos MySQL con el nombre user y actualiza la configuración de conexión en el archivo `application.properties` de OnClass.
3. **Ejecutar la Aplicación:** Utiliza IntelliJ u otro IDE compatible para ejecutar la clase principal de UserApplication y lanzar el servidor.
4. **Acceso a la Plataforma:** Abre [http://localhost:8090/swagger-ui/index.html#](http://localhost:8090/swagger-ui/index.html#) en tu navegador para acceder a la documentación interactiva de la API y comenzar a explorar las funcionalidades del sistema.

## Pruebas:

Se han incluido pruebas unitarias para garantizar la calidad y fiabilidad del sistema. Haz clic derecho en la carpeta de pruebas y elige Ejecutar pruebas con cobertura.
