	Esta archivo pertenece a la aplicación "Estadísticas de Objetivos + AdivinaAdivinanza" bajo licencia GPLv2.
	Copyright (C) 2013 Óscar R. Zafra Megías.
	
	Este programa es software libre. Puede redistribuirlo y/o modificarlo bajo los términos 
	de la Licencia Pública General de GNU según es publicada por la Free Software Foundation, 
	bien de la versión 2 de dicha Licencia o bien (según su elección) de cualquier versión 
	posterior.
	
	Este programa se distribuye con la esperanza de que sea útil, pero SIN NINGUNA GARANTÍA, 
	incluso sin la garantía MERCANTIL implícita o sin garantizar la CONVENIENCIA PARA UN 
	PROPÓSITO PARTICULAR. Véase la Licencia Pública General de GNU para más detalles.
	
	Debería haber recibido una copia de la Licencia Pública General junto con este programa. 
	Si no ha sido así, escriba a la Free Software Foundation, Inc., en 675 Mass Ave, Cambridge, 
	MA 02139, EEUU.

# Práctica 2: Aislamiento de una aplicación web usando jaulas

> Ìnformación gratuita:
> Instalar todo con permisos de superusuario para que no haya problemas.

### INSTALAR SIENDO SUPERUSUARIO DEL SISTEMA (Fuera de la jaula)

1. Empezamos creando una jaula con debootstrap. ([click para más información (Ejercicio3)](https://github.com/oskyar/InfraestructuraVirtual/blob/master/Tema2/Ejercicios3y4.md))

2. Creamos un usuario:

		$ useradd -s /bin/bash -m -d /home/jaulas/server/./home/user_limit -c "Saucy - Jaula con un usuario limitado en permisos" -g users user_limit

![Añadiendo Usuario a la Jaula](https://raw.github.com/oskyar/Practica2-Jaula-CHROOT/master/documentacion/img/2.%20A%C3%B1adiendo%20usuario%20a%20la%20jaula.png)

3. Le asignamos una contraseña

		$ passwd user_limit
		$ "Ponemos una vez la contraseña"
		$ "Repetimos la contraseña"

![Añadiendo Usuario a la Jaula](https://raw.github.com/oskyar/Practica2-Jaula-CHROOT/master/documentacion/img/3.a%C3%B1adiendo-contrase%C3%B1a-usuario-jaula.png)

4. Ya tenemos nuestro usuario en la jaula con una contraseña configurada.

5. Comprobamos que el usuario entra a la jaula, pero sin restricciones, es decir, **PELIGROOO: ¡¡ PUEDE SALIR DE LA JAULA SIN NINGÚN PROBLEMA !!**

![Usuario enjaulado sin restricciones](https://raw.github.com/oskyar/Practica2-Jaula-CHROOT/master/documentacion/img/4.Conenctandonos-por-ssh-sin-restricciones.png)

Por lo que pasamos al siguiente punto. 

6. (Como superusuario del sistema) Configuramos `/etc/ssh/sshd_conf` de la siguiente manera.

![Configurando sshd_conf](https://raw.github.com/oskyar/Practica2-Jaula-CHROOT/master/documentacion/img/5.Configurando-sshd_conf.png)

**Match User < usuario>**: Indica que la restricción va a ser para un usuario en concreto.

**ChrootDirectory < path>**: Nos indicará a partir del cual va a ser el directorio root, por lo que ese va a ser el directorio raíz del usuario "user_limit"
Las otras dos directivas son para asegurar el sistema.

Ni que decir tiene, que cada vez que se modifica el archivo `/etc/ssh/sshd_conf` hay que reiniciar el servicio ssh para que se aplique la configuración nueva.

		sudo service ssh restart

7. Comprobamos que funciona entrando de nuevo con el usuario "user_limit"

		$ ssh user_limit@localhost

![Entrando de nuevo con el usuario](https://raw.github.com/oskyar/Practica2-Jaula-CHROOT/master/documentacion/img/6.captura-usuario-path.png)

### A PARTIR DE AQUÍ HACEMOS TODO DESDE CHROOT (Dentro de la jaula)

1. Entramos en la Jaula como root:

		$ sudo chroot /home/jaulas/server



2. Instalamos aplicaciones necesarias para poder compilar, ejecutar, depurar y lanzar "Adivina Adivinanza" en local.


	i. [Ver instalación del JDK1.7.0](https://github.com/oskyar/Practica2-Jaula-CHROOT/blob/master/documentacion/instalacion_java.md)

	ii. [Ver instalación de Maven](https://github.com/oskyar/Practica2-Jaula-CHROOT/blob/master/documentacion/instalacion_maven.md)

	iii. Instalación de git 
	
	> a. Instalamos git desde el repositorio

	>		$ apt-get install git

	> b. Ahora damos permisos de usuario al home

	>		$ sudo chown user_limit /home/jaulas/server/home/user_limit

	> c. Clonamos el proyecto del repositorio

	>		$ git clone https://github.com/oskyar/Practica2-Jaula-CHROOT.git


-----------------------------------------------------
-----------------------------------------------------

+ Bien, ya tenemos jdk, maven y git instalados y configurados; el proyecto descargado, el usuario enjaulado y ahora vamos a ver cómo se lanza el proyecto.

**NOTA:** (Permisos de usuario)
> 1. Los permisos que se le pueden dar en este caso al usuario de la jaula pueden ser de ejecución, escritura y lectura a partir de su home para que tenga total acceso a los archivos de su home. Esto sería así, en caso de que el usuario fuera desarrollador, debido a que mvn necesita instalar dependencias y librerías, y sin permisos de escritura estaría restringido ( sin poder instalar ni compilar) y nos daría error , entonces... ¿Para qué queremos un usuario enjaulado si no puede ejecutar compilar y lanzar la principal tarea de esta práctica? ¡¡¡ESTADISTICAS DE IV + ADIVINA ADIVINANZA!!!

> 	Con esta orden conseguiremos lo que queremos (Desde el terminal del sistema, no desde la jaula)

>		$ sudo chown -R user_limit:users /home/jaulas/server/home/user_limit/

> 2. En caso de que queramos que sólo pueda lanzar la aplicación, olvidándonos de la instalación y compilación (dejándolo para el administrador u otro usuario con más privilegios), haremos lo siguiente desde el terminal del sistema

>			$ sudo chown -R root:root /home/jaulas/server/

> 	Y así el usuario enjaulado solo sería capaz de lanzar la aplicación.


En mi caso he optado por dejar al usuario solo como lanzador de la aplicación restringiéndole las demás acciones (para crear un sistema de alta seguridad).

### IMPORTANTE: Siempre que se inicie un terminal en la jaula, hay que usar el script de las variables de entorno o no encontrará los ejecutables.

1. Siguiendo en modo **CHROOT**, nos vamos a la carpeta del proyecto y escribimos

		$ mvn install

	![captura](https://raw.github.com/oskyar/Practica2-Jaula-CHROOT/master/documentacion/img/7.Maven-Install.png)

	Esto instalará todas las dependencias que necesite para compilar el proyecto.

2. Ahora empaquetamos todo

		$ mvn package

	![captura](https://raw.github.com/oskyar/Practica2-Jaula-CHROOT/master/documentacion/img/8.Maven-Package.png)

	Nos generará un archivo, en este caso con extensión **.jar** (dentro de la carpeta `target`) para poder desplegarlo en un servidor de aplicaciones (ej. JBOSS), pero este no es el caso.

3. ¡Bien!, ya podemos entrar con nuestro usuario enjaulado con:

		$ ssh user_limit@localhost
		"contraseña"

4. Ya podremos ejecutar la siguiente orden para que arranque el servidor (hay que estar en el directorio de la aplicación)

		$ sh target/bin/webapp

	![captura](https://raw.github.com/oskyar/Practica2-Jaula-CHROOT/master/documentacion/img/9.Desplegando-app.png)


5. Finalmente, podemos entrar en el navegador del sistema (fuera de la jaula) para comprobar que la aplicación está funcionando.

	![captura](https://raw.github.com/oskyar/Practica2-Jaula-CHROOT/master/documentacion/img/10.Navegador-app.png)



##Conclusión:

El crear una jaula puede ser muy interesante, porque nos puede ayudar a probar configuraciones para ver la estabilidad del sistema, crear usuarios con pocos o muchos permisos para comprobar la estabilidad y eficacia del sistema. En general, para hacer pruebas de cualquier tipo sin dañar nuestro entorno, poder crear un entorno de desarrollo de una aplicación, o de producción, etc.

Para un desarrollador puede ser más engorroso trabajar en una jaula respecto a un PaaS debido a que la primera vez tiene que instalar, configurar todas las aplicaciones, dependencias, etc y puede llegar a ser una pérdida de tiempo importante, pero nos brinda la posibilidad de poder crear, modificar todo a nuestro antojo, añadir nuevas configuraciones, instalar diferentes frameworks y testearlo todo. Sin embargo un PaaS, tipo Heroku, puede llegar a ser mucho más cómodo y ayudar a centrarte solo en la creación de una aplicación pero a la hora de crear un entorno de desarrollo, producción ó test está más limitado. O si quieres deslimitarlo tendrás que sacar el monedero.

Me ha parecido una práctica muy interesante en la que he aprendido bastante. ya que me he peleado con las distintas formas de instalar y utilizar una jaula, crear y modificar permisos, accesos al sistema desde chroot y desde un usuario enjaulado...


##ENLACES:

[APP en Heroku](http://estadisticasobjetivos-iv.herokuapp.com/)

[Documentación](https://github.com/oskyar/Practica2-Jaula-CHROOT/blob/master/documentacion/documentacion.md)

[Issiue](https://github.com/IV-GII/GII-2013/issues/58)

[Repositorio](https://github.com/oskyar/Practica2-Jaula-CHROOT)


##BIBLIOGRAFIA

http://askubuntu.com/questions/53177/bash-script-to-set-environment-variables-not-working

https://forum.linode.com/viewtopic.php?t=4409%3E

http://www.debian-administration.org/article/OpenSSH_SFTP_chroot_with_ChrootDirectory

http://askubuntu.com/questions/56104/how-can-i-install-sun-oracles-proprietary-java-6-7-jre-or-jdk