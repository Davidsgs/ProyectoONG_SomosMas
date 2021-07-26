# Somos MÃ¡s - AceleraciÃ³n Alkemy.

El presente es un proyecto para la fundaciÃ³n â€œSomos MÃ¡sâ€. Se trata de un servicio backend implementado en JAVA con el framework Spring Boot. El mismo estÃ¡ pensado para servir a una aplicaciÃ³n que actuarÃ¡ como carta de presentaciÃ³n donde Somos MÃ¡s pueda compartir novedades con la comunidad, contar sobre el trabajo que llevan a cabo, invitar a interesados/as a participar como voluntarios/as o donantes (de dinero o materiales), y donde puedan compartir informaciÃ³n (a partir de informaciÃ³n recopilada a travÃ©s de la pÃ¡gina) sobre el equipo que estÃ¡ detrÃ¡s de cada acciÃ³n que lleva a cabo la organizaciÃ³n.

---
## Pre-requisitos ðŸ“‹

Para correr esta aplicaciÃ³n necesitas tener instalado java en tu computadora.

---

## Guia de uso ðŸ“–

Puedes encontrar informaciÃ³n de como usar la api desde la swagger UI, la cual puede ser accedida una vez levantada la api, en el path /api/docs. Si no sabes como levantar la api te dejamos un archivo en el root del proyecto, se llama [GUIA_USO.txt](GUIA_USO.txt) .

---

## Ejecutando las pruebas âš™ï¸

Existen Test Unitarios sobre cada Endpoint y algunos Services que se encuentran en la ruta [/src/test/java/com/restteam/ong](src/test/java/com/restteam/ong), donde hay algunos Test hechos a al Service y otros a los deferentes Endpoints.

TambiÃ©n existe un Data Seed de usuarios que se puede usar para hacer pruebas manuales, que estaremos explicando a continuaciÃ³n.

###Usuarios De Pruebas *(User Data Seeds)*.

Para testear la API se crean diferentes usuarios por defecto, existen 10 usuarios regulares y 10 usuarios administradores para poder acceder y hacer diferentes pruebas.

* Se puede acceder a un **Usuario Regular** con alguno de los correos: registeredUser[***ID***]>@email.com

* Y para acceder a los **Usuarios Admin** se usa alguno de los correos: adminUser[***ID***]@email.com

**Los campos [*ID*] deben ser un numero del 1 al 10.**

**La contraseÃ±a es la misma para todos los usuarios: qwerty**

**Por ejemplo**, para acceder con un usuario administrador, debemos usar alguno de los correos, en este caso usaremos "adminUser4@email.com" cuya contraseÃ±a es "qwerty".
luego debemos hacer una peticiÃ³n POST a la ruta "/auth/login" de la API y se nos serÃ¡ asignado un JWT para poder autenticarnos.

---

## DocumentaciÃ³n: ðŸ“šðŸ“–

Existe una documentaciÃ³n hecha con Swagger de cada Endpoint en la ruta: **http://localhost:9800/api/docs**

---

## Construido con ðŸ› ï¸

EstÃ¡ API utiliza los siguientes frameworks, librerias y herramientas de desarrollo:

* [SpringBoot](https://spring.io/) - El framework web utilizado
* [Maven](https://maven.apache.org/) - Gestionador de dependencias
* [GIT](https://git-scm.com/) - Versionador del projecto
* [MySql](https://www.mysql.com/) - Base de Datos.
* [Swagger](https://swagger.io/) - Documentacion del projecto
* [JUnit](https://junit.org/junit5/) - Testeo a la api
* [Mockito](https://site.mockito.org/) - Testeo a la api
* [Sendgrid](https://sendgrid.com/) - Envio de mails automatizado

---

## Versionado ðŸ“Œ

Para el versionado del proyecto decidimos utilizar la herramienta BitBucket. Esto debido a que se complementa muy bien con JIRA, lo cual permite realizar metodologÃ­as agile scrum.

---

## Autores âœ’ï¸

* [**Franco Rueta**](https://github.com/FrancoRueta)
* [**David GarcÃ­a**](https://github.com/Davidsgs)
* [**Leonel Peralta**](https://github.com/leonelmperalta)
* [**HÃ©ctor GonzÃ¡lez**](https://github.com/eletooor)
* [**Gisela Tamburro**](https://github.com/GiseelaDaiana)
* [**Nicolas Ramirez**](https://github.com/nicoRamirez)
* [**GastÃ³n Alanis**](https://github.com/Alanisgas)

Hecho con â¤ï¸ por el equipo RestTeam (Equipo 48) de Alkemy.

---

## Licencia ðŸ“„

Este proyecto estÃ¡ bajo la Licencia MIT - mira el archivo [LICENSE.md](LICENSE.md) para detalles

---

## Expresiones de Gratitud ðŸŽ

* Gracias a todo el personal de Alkemy por estÃ¡ experiencia tan buena donde pudimos aprender mucho mÃ¡s del uso de Spring Boot y otras herramientas para el desarrollo Back-End de una API REST.
* Muchas gracias tambiÃ©n en especial a Nico Truk por estar con nosotros como mentor! ðŸ¤“.

