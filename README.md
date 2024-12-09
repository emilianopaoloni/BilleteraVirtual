# Proyecto "CriptoCrash", billetera virtual de criptomonedas.
Trabajo final realizado en la materia Taller de Lenguajes 2.

En primera instancia, se realizó una evaluación y diseño de la aplicación mediante un diagrama UML de clases y un diagrama UML de secuencia. Posteriormente, se desarrolló la aplicación en Java utilizando las siguientes tecnologías:
-Patrón MVC (Modelo-Vista-Controlador): Este patrón fue utilizado para estructurar la aplicación y separar las responsabilidades entre la lógica de negocio, la interfaz de usuario y el control de flujo.

-SQLite: Se empleó SQLite como sistema de gestión de bases de datos relacional para almacenar los datos de la aplicación.

-Patrón Singleton: Se implementó el patrón Singleton para garantizar que exista una única instancia de la conexión a la base de datos, centralizando el acceso y evitando la creación de múltiples conexiones innecesarias.

-AWT y Swing: Se utilizaron las bibliotecas AWT y Swing para el desarrollo de las interfaces gráficas de usuario, permitiendo una interacción fluida y visualmente clara.

-Hilos (Threads): Se emplearon hilos para gestionar de forma concurrente el proceso de obtención de cotizaciones de criptomonedas desde una API externa, lo que permite una actualización en tiempo real de los datos sin bloquear la interfaz de usuario.

-Librería JSON para procesar las respuestas de la API y extraer la información necesaria.
