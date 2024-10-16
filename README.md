# Feedback2-eventos

## Enlace del repositorio

[Repositorio](https://github.com/Feedback2-eventos/Feedback2-eventos.git) realizado por Fernando Santamaria, Jose Antonio Oyono, Jose Daniel Martin, Hugo Sanchez

## Descripción del Proyecto

Feedback2-eventos es una aplicación móvil desarrollada en Kotlin utilizando Jetpack Compose y Firebase Firestore. La aplicación permite a los usuarios gestionar diferentes habitaciones de una casa (cocina, salón, dormitorio) y registrar el consumo energético de cada una de ellas. Los usuarios pueden iniciar sesión, registrarse y agregar habitaciones a su perfil. La aplicación también proporciona una interfaz interactiva para visualizar y actualizar el consumo energético en tiempo real.

## Flujo de Funcionamiento de la Aplicación

### Inicio de la Aplicación

- La aplicación se inicia ejecutando el método `main` en la clase `MainActivity`.
- Android Studio se encarga de inicializar el contexto de la aplicación y de inyectar las dependencias necesarias.

### Inicio de Sesión y Registro

- Los usuarios pueden iniciar sesión o registrarse en la pantalla de inicio (`StartScreen`).
- La información de inicio de sesión se verifica contra los datos almacenados en Firebase Firestore.
- Si el usuario no existe, se puede registrar un nuevo usuario y almacenar sus datos en Firestore.

### Gestión de Habitaciones

- Los usuarios pueden agregar diferentes tipos de habitaciones (cocina, salón, dormitorio) a su perfil.
- Cada tipo de habitación tiene un formulario específico (`CocinaForm`, `SalonForm`, `DormitorioForm`) para ingresar los detalles.
- Los datos de las habitaciones se almacenan en Firestore y se asocian con el usuario correspondiente.

### Visualización y Actualización del Consumo Energético

- Cada habitación tiene un método para calcular el consumo energético basado en los dispositivos presentes.
- El consumo energético se actualiza en tiempo real y se muestra en la interfaz de usuario.
- Los datos se transmiten y actualizan utilizando Firebase Firestore.

## Clases

### `MainActivity`

- Clase principal que inicia la aplicación y configura la navegación entre las diferentes pantallas.

### `StartScreen`

- Composable que maneja la pantalla de inicio de sesión y registro.
- Verifica las credenciales del usuario y permite el registro de nuevos usuarios.

### `Usuario`

- Clase de datos que representa a un usuario en la aplicación.
- Contiene información como nombre, contraseña y listas de habitaciones (cocinas, salones, dormitorios).

### `UsuarioRepository`

- Clase que maneja la interacción con Firebase Firestore para las operaciones relacionadas con los usuarios.
- Métodos para obtener, agregar y actualizar usuarios y sus habitaciones.

### `Cocina`, `Salon`, `Dormitorio`

- Clases de datos que representan diferentes tipos de habitaciones.
- Cada clase contiene información específica sobre los dispositivos presentes y métodos para calcular el consumo energético.

### `CocinaForm`, `SalonForm`, `DormitorioForm`

- Composables que proporcionan formularios para agregar nuevas habitaciones.
- Permiten a los usuarios ingresar detalles sobre las habitaciones y almacenarlos en Firestore.

### `AnimatedButton`

- Composable que proporciona un botón animado para mejorar la experiencia de usuario.

## Interfaz de Usuario

### Pantalla de Inicio

- Permite a los usuarios iniciar sesión o registrarse.
- Verifica las credenciales contra los datos almacenados en Firestore.

### Formularios de Habitaciones

- Formularios interactivos para agregar nuevas habitaciones.
- Los usuarios pueden especificar los dispositivos presentes en cada habitación.

### Visualización del Consumo Energético

- Muestra el consumo energético de cada habitación en tiempo real.
- Los datos se actualizan automáticamente utilizando Firebase Firestore.

## Problemas y Soluciones

### Problema 1: Sincronización del Consumo Energético

**Descripción:** La actualización del consumo energético no era precisa debido a la falta de sincronización entre los dispositivos.

**Solución:** Se implementaron métodos para calcular y actualizar el consumo energético en tiempo real, asegurando que los datos sean precisos y estén sincronizados.

### Problema 2: Manejo de Errores en Firestore

**Descripción:** La aplicación no manejaba correctamente los errores al interactuar con Firestore, lo que resultaba en fallos inesperados.

**Solución:** Se añadieron manejadores de errores para capturar y mostrar mensajes de error apropiados al usuario.

### Problema 3: Optimización de la Interfaz de Usuario

**Descripción:** La interfaz de usuario no era intuitiva y carecía de animaciones que mejoraran la experiencia del usuario.

**Solución:** Se implementaron componentes animados como `AnimatedButton` para mejorar la interactividad y la experiencia del usuario.

### Problema 4: Gestión de Sesiones de Usuario

**Descripción:** La aplicación no gestionaba correctamente las sesiones de usuario, lo que permitía a los usuarios acceder a datos sin iniciar sesión.

**Solución:** Se implementó un sistema de gestión de sesiones que asegura que los usuarios deben iniciar sesión para acceder a sus datos.

### Problema 5: Actualización en Tiempo Real

**Descripción:** Los datos de consumo energético no se actualizaban en tiempo real, lo que resultaba en información desactualizada.

**Solución:** Se utilizó Firebase Firestore para transmitir y actualizar los datos en tiempo real, asegurando que la información mostrada sea siempre actual.
