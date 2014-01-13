	Esta archivo pertenece a la aplicación "Estadísticas de Objetivos + AdivinaAdivinanza bajo una VM en Azure" bajo licencia GPLv2.
	Copyright (C) 2014 Óscar R. Zafra Megías.
	
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

# Práctica 3: Diseño de máquinas virtuales

### Descripción

La práctica consiste en diseñar una máquina virtual para una aplicación específica la cual debe de usar los mínimos recursos del sistema para que la aplicación sea lo más óptima posible, es decir, que los recursos no se queden cortos ni que tampoco se "desperdicien".

Para ello voy a usar nueva práctica, la nube, en este caso, Windows Azure.
Voy a crear 3 máquinas virtuales con los siguientes sistemas operativos:
* Ubuntu 12.04 LTS
* Ubuntu 13.10
* Centos 

Pasos a seguir:

0. Estar registrado en Azure.
1. Primero crearemos las máquinas virtuales en Azure.
2. Entraremos a la máquina mediante ssh.
3. Configuraremos el entorno para correr la aplicación (Instalación de jdk 1.7, maven, git).
4. Haremos pruebas de uso de recursos e iremos cambiando la configuración del sistema mediante el panel de control de Azure.


**NOTA:** Si estáis buscando como registrarse con un código gratuito de suscripción para *buenos estudiantes*, [pinchad aquí](https://github.com/oskyar/InfraestructuraVirtual/blob/master/Tema4/Ejercicios8-10.md#ejercicio-8)

### Empieza lo bueno:

#### Máquina virtual 1: Ubuntu 12.04 LTS + Spring (Servidor de aplicaciones)

#####1. Empezaremos creando la máquina virtual desde la página de Azure ya que es más atractivo e intuitivo.

1. Tan solo tenemos acceder a nuestra página una vez suscritos. Pinchar a la izquierda en `**Virtual Machines**` y hacer click en la esquina inferior izquierda en `**NEW**`

	![Captura de la página inicial](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/1-CreandoMaquinaVirtualEnAzure.png)

	2. Ahora accedemos a crear la VM (Virtual Machine de ahora en adelante) desde la Galería (`**FROM GALERY**`) que es más cómodo y fácil.
	**NOTA:** También que al tener una cuenta de suscripción gratuita solo podemos crear un almacenamiento de objetos, por lo que nos conviene hacerlo desde la galería porque nos permite usar el mismo almacenamiento en caso de haber creado alguno ya.

	![Accediendo a la galería](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/2.CreandoDesdeGaleria.png)

	3. En este paso es donde escogemos el Sistema Operativo que queramos tener en nuestra máquina virtual, en este caso escogeré Ubuntu -> Ubuntu 12.04 LTS (La captura es puramente ilustrativa).

	![Escogiendo SO](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/3.EscogiendoSO.png)

	4. Ahora procedemos a rellenar los que se nos pide:

	![Escogiendo SO](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/4.RellenandoDatos.png)
		
		1. Ponerle nombre a nuestra máquina virtual (formará parte de la URL de acceso)

		2. **SIZE:** Configuración que tendrá nuestra máquina, en nuestro caso solo podremos escoger o la *Extra small* ó *Small* debido a la suscripción. Empezaremos por la Extra small que tiene:

			* Un core compartido
			* 768 MB de memoria RAM

		3. **NEW USER NAME: ** Viene a ser el nombre de nuestro usuario dentro de la máquina.

		4. Os saldrá marcado en *AUTHENTICATION* `Upload compatible ssh key for authentication`, lo desmarcáis en caso de no tener un certificado para ssh.

		5. Yo proveo de password a mi VM para que no puedan acceder usuarios anónimos o ajenos al sistema, así que la selecciono y establezco una contraseña.

		6. ¡Podemos continuar!


	5. Ya nos falta poco, sigamos configurando... 

	![Rellenando el formulario](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/4.RellenandoDatos.png)
		
		1. **CLOUD SERVICE:** Ahora procedemos a indicar si es un nuevo servicio o lo queremos vincular a uno ya existente (esto sirve por si queremos tener en una VM a parte una Base de Datos, ó un entorno de desarrollo o de testeo), en mi caso crearé uno nuevo.

		2. **CLOUD SERVICE DNS NAME:** En caso de ser un nuevo servicio el nombre estará automáticamente puesto, ya que lo pusimos en el paso anterior.

		3. La región en la que queremos que esté nuestra VM, esto es importante porque dependiendo de qué ubicación queramos escoger, puede que nos puedan juzgar de una manera u otra en caso de hacer travesuras por la red. Por lo tanto en nuestro caso `West Europe`.

		4. **STORAGE ACCOUNT: ** Escogemos nuestro única cuenta de almacenamiento, ya que no podemos utilizar otra ni crear una nueva con la súper suscripción que tenemos, que se agradece mucho aunque sea bastante limitada.

		5. **AVAILABILITY SET: ** Esto es para que sincronices varias máquinas y en caso de que una VM esté muy "estresada" pueda entrar en acción otra máquina secundaria para balancear la carga. ¡¡Todo automáticamente!!.

		6. ¡Sigamos!


	6. Último pasito, configurar los "**Puntos Finales**"

	![Puntos Finales](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/6.ConfigurandoPuntosFinales.png)

	**¿Ésto que quiere decir?** - Que nos abre directamente los puertos a nuestra VM que vayamos a necesitar, ni más ni menos. Teniendo así bien capada nuestra VM en caso de seres intrusivos.

	Hay gran variedad de "EndPoints" por defecto configurados en el que solo deberemos de pinchar en el que queramos añadir.

	7. C'est Fini!

######2. ¡¡	Eh, que ya tengo una VM creada !! ¿¡Cómo entro!?

	* Se puede acceder de muchísimas formas, pero como ya hemos hecho demasiados clicks, toca escribir un poquito y acceder mediante nuestro terminal. Sigamos los siguientes pasos y veremos que sencillo es:
	
		> Yo lo hago mediante la *shell* de bash en Ubuntu 13.10 (saucy)

	1. Debemos tener instalado ssh en nuestro sistema, para comprobarlo nos basta con escribir en nuestro terminal 

			$ ssh

		* En de no tenerlo instalado

			$ sudo apt-get install ssh

	2. Para acceder mediante ssh debemos recordar el nombre de nuestra VM ó la ip por lo menos, para ello, sin tener que acceder a la web y tener que estar navegando, utilizaremos la siguiente orden

		$ azure vm list

	![Lista de VM](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/8.ListaVM.png)

	En el caso de seguir queriendo hacerlo mediante la web, debemos ir a `VIRTUAL MACHINES`

	Esta imagen es ilustrativa.
	![Lista de VM](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/7.ListaMaquinasVirtuales.png)


	3. Bien, ya sabemos qué máquinas tenemos disponibles, lógicamente solo podremos acceder a una máquina que esté en funcionamiento (en caso contrario mostrará un mensaje de error `connection refused`), por lo que lo haremos a la máquina con el nombre `app-conf3.cloudapp.net`, así que escribiremos la siguiente orden.

			$ ssh app-conf3.cloudapp.net


	~~~

	oskyar@oskyar-M60Vp:~/proyectosGit/Practica3-VirtualMachine$ ssh app-conf3.cloudapp.net
	Warning: the ECDSA host key for 'app-conf3.cloudapp.net' differs from the key for the IP address '137.117.146.38'
	Offending key for IP in /home/oskyar/.ssh/known_hosts:14
	Matching host key in /home/oskyar/.ssh/known_hosts:15
	Are you sure you want to continue connecting (yes/no)? yes
	oskyar@app-conf3.cloudapp.net's password: 
	Last login: Mon Jan 13 02:28:49 2014 from 89.140.178.72.static.user.ono.com

	~~~

	**NOTA:** Si es la primera vez que entras, te sale un mensaje como el anterior y no configuraste el certificado **SSH**; escribe `yes` y escribes la **contraseña** en caso de haberla establecido al crear la VM para poder acceder a la VM.


	4. 




##Conclusión:



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