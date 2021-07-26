---

#Usuarios De Pruebas *(User Seeds)*.

Para testear la API se crean diferentes usuarios por defecto, existen 10 usuarios regulares y 10 usuarios administradores para poder acceder y hacer diferentes pruebas.

* Se puede acceder a un **Usuario Regular** con alguno de los correos: registeredUser[***ID***]>@email.com

* Y para acceder a los **Usuarios Admin** se usa alguno de los correos: adminUser[***ID***]@email.com

###**Los campos [*ID*] deben ser un numero del 1 al 10.**

**La contraseña es la misma para todos los usuarios: qwerty**

**Por ejemplo**, para acceder con un usuario administrador, debemos usar alguno de los correos, en este caso usaremos "adminUser4@email.com" cuya contraseña es "qwerty".
luego debemos hacer una petición POST a la ruta "/auth/login" de la API y se nos será asignado un JWT para poder autenticarnos.

