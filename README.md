# Somos Más - Aceleración Alkemy.

El presente es un proyecto para la fundación “Somos Más”. Se trata de un servicio backend implementado en JAVA con el framework Spring Boot. El mismo está pensado para servir a una aplicación que actuará como carta de presentación donde Somos Más pueda compartir novedades con la comunidad, contar sobre el trabajo que llevan a cabo, invitar a interesados/as a participar como voluntarios/as o donantes (de dinero o materiales), y donde puedan compartir información (a partir de información recopilada a través de la página) sobre el equipo que está detrás de cada acción que lleva a cabo la organización.

### Pre-requisitos 📋

_Para correr esta aplicación necesitas tener instalado java en tu computadora._

_Tampoco vendría mal una noción básica sobre el uso de consola para iniciar el proyecto._


## Ejecutando las pruebas ⚙️

Existen Test Unitarios sobre cada Endpoint y algunos Services que se encuentran en la ruta “/src/test/java/com/restteam/ong”

También existe un Data Seed de usuarios que se puede usar para hacer pruebas manuales, que estaremos explicando a continuación.

###Usuarios De Pruebas *(User Seeds)*.

Para testear la API se crean diferentes usuarios por defecto, existen 10 usuarios regulares y 10 usuarios administradores para poder acceder y hacer diferentes pruebas.

* Se puede acceder a un **Usuario Regular** con alguno de los correos: registeredUser[***ID***]>@email.com

* Y para acceder a los **Usuarios Admin** se usa alguno de los correos: adminUser[***ID***]@email.com

**Los campos [*ID*] deben ser un numero del 1 al 10.**

**La contraseña es la misma para todos los usuarios: qwerty**

**Por ejemplo**, para acceder con un usuario administrador, debemos usar alguno de los correos, en este caso usaremos "adminUser4@email.com" cuya contraseña es "qwerty".
luego debemos hacer una petición POST a la ruta "/auth/login" de la API y se nos será asignado un JWT para poder autenticarnos.




## Construido con 🛠️

Está API utiliza los siguientes frameworks, librerias y herramientas de desarrollo:

* [SpringBoot](https://spring.io/) - El framework web utilizado
* [Maven](https://maven.apache.org/) - Gestionador de dependencias
* [GIT](https://git-scm.com/) - Versionador del projecto
* [MySql](https://www.mysql.com/) - Base de Datos.
* [Swagger](https://swagger.io/) - Documentacion del projecto
* [JUnit](https://junit.org/junit5/) - Testeo a la api
* [Mockito](https://site.mockito.org/) - Testeo a la api
* [Sendgrid](https://sendgrid.com/) - Envio de mails automatizado

## Guia de uso 📖

Puedes encontrar información de como usar la api desde la swagger UI, la cual puede ser accedida una vez levantada la api, en el path /api/docs. Si no sabes como levantar la api te dejamos un archivo en el root del proyecto, se llama [GUIA_USO.txt](GUIA_USO.txt) .

## Versionado 📌

Para el versionado del proyecto decidimos utilizar la herramienta BitBucket. Esto debido a que se complementa muy bien con JIRA, lo cual permite realizar metodologías agile scrum.

## Autores ✒️

* **Franco Rueta** - [FrancoRueta](https://github.com/FrancoRueta)
* **David García** - [Davidsgs](https://github.com/Davidsgs)
* **Leonel Peralta** - [leonelmperalta](https://github.com/leonelmperalta)
* **Héctor González** - [eletoor](https://github.com/eletooor)
* **Gisela Tamburro** - [GiseelaDaiana](https://github.com/GiseelaDaiana)
* **Nicolas Ramirez** - [nicoRamirez](https://github.com/nicoRamirez)

Hecho con ❤️ por el equipo RestTeam (Equipo 48) de Alkemy.

## Licencia 📄

Este proyecto está bajo la Licencia MIT - mira el archivo [LICENSE.md](LICENSE.md) para detalles

## Expresiones de Gratitud 🎁

* Muchas gracias a Nico Truk por estar con nosotros como mentor! 🤓.

---
