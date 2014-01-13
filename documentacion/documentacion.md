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

## Descripción

La práctica consiste en diseñar una máquina virtual para una aplicación específica la cual debe de usar los mínimos recursos del sistema para que la aplicación sea lo más óptima posible, es decir, que los recursos no se queden cortos ni que tampoco se "desperdicien".

Para ello voy a usar nueva práctica, la nube, en este caso, Windows Azure.
Voy a crear 3 máquinas virtuales con los siguientes sistemas operativos:
* Ubuntu 12.04 LTS
* Ubuntu 13.10
* Centos 

**Pasos a seguir:**

0. Estar registrado en Azure.
1. Primero crearemos las máquinas virtuales en Azure.
2. Entraremos a la máquina mediante ssh.
3. Configuraremos el entorno para correr la aplicación (Instalación de jdk 1.7, maven, git).
4. Haremos pruebas de uso de recursos e iremos cambiando la configuración del sistema mediante el panel de control de Azure.


**NOTA:** Si estáis buscando como registrarse con un código gratuito de suscripción para *buenos estudiantes*, [pinchad aquí](https://github.com/oskyar/InfraestructuraVirtual/blob/master/Tema4/Ejercicios8-10.md#ejercicio-8)

-------------

## Empieza lo bueno:

### Máquina virtual 1: Ubuntu 12.04 LTS + Spring (Servidor de aplicaciones)

####1. Empezaremos creando la máquina virtual desde la página de Azure ya que es más atractivo e intuitivo.

1. Tan solo tenemos acceder a nuestra página una vez suscritos. Pinchar a la izquierda en `**Virtual Machines**` y hacer click en la esquina inferior izquierda en `**NEW**`

	![Captura de la página inicial](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/1-CreandoMaquinaVirtualEnAzure.png)

2. Ahora accedemos a crear la VM (Virtual Machine de ahora en adelante) desde la Galería (**`FROM GALERY`**) que es más cómodo y fácil.
	
	**NOTA:** También que al tener una cuenta de suscripción gratuita solo podemos crear un almacenamiento de objetos, por lo que nos conviene hacerlo desde la galería porque nos permite usar el mismo almacenamiento en caso de haber creado alguno ya.

	![Accediendo a la galería](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/2.CreandoDesdeGaleria.png)

3. En este paso es donde escogemos el Sistema Operativo que queramos tener en nuestra máquina virtual, en este caso escogeré **Ubuntu -> Ubuntu 12.04 LTS** 

	*(La captura es puramente ilustrativa).*

	![Escogiendo SO](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/3.EscogiendoSO.png)

4. Ahora procedemos a rellenar los que se nos pide:

	![Escogiendo SO](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/4.RellenandoDatos.png)
		
	1. **VIRTUAL MACHINE NAME:** Ponerle nombre a nuestra máquina virtual (formará parte de la URL de acceso)

	2. **SIZE:** Configuración que tendrá nuestra máquina, en nuestro caso solo podremos escoger o la *Extra small* ó *Small* debido a la suscripción. Empezaremos por la Extra small que tiene:

		* Un core compartido
		* 768 MB de memoria RAM

	3. **NEW USER NAME:** Viene a ser el nombre de nuestro usuario dentro de la máquina.

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

	**¿Esto que quiere decir?** - Que nos abre directamente los puertos a nuestra VM que vayamos a necesitar, ni más ni menos. Teniendo así bien capada nuestra VM en caso de haber seres intrusivos.

	Hay gran variedad de "**EndPoints**" por defecto configurados. Para añadir alguno tan fácil como seleccionar el que más nos convenga.

7. C'est Fini!


####2. ¡¡Eh, que ya tengo una VM creada!! ¿¡Cómo entro!?

* Se puede acceder de muchísimas formas, pero como ya hemos hecho demasiados clicks, toca escribir un poquito y acceder mediante nuestro terminal. Sigamos los siguientes pasos y veremos que sencillo es:

	> Yo lo hago mediante la *shell* de bash en Ubuntu 13.10 (saucy)

1. Debemos tener instalado ssh en nuestro sistema, para comprobarlo nos basta con escribir en nuestro terminal 

		$ ssh

	* Si nos sale un mensaje de error que no encuentra el ejecutable, no pasa nada, escribid lo siguiente e instalar las dependencias necesarias.

			$ sudo apt-get install ssh

2\. Para acceder mediante ssh debemos recordar el nombre de nuestra VM ó la ip por lo menos, para ello, sin tener que acceder a la web y tener que estar navegando, utilizaremos la siguiente orden

	$ azure vm list

![Lista de VM](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/8.ListaVM.png)

En el caso de seguir queriendo hacerlo mediante la web, debemos ir a `VIRTUAL MACHINES`

*Esta imagen es ilustrativa*
![Lista de VM](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/7.ListaMaquinasVirtuales.png)


3\. Bien, ya sabemos qué máquinas tenemos disponibles, lógicamente solo podremos acceder a una máquina que esté en funcionamiento (en caso contrario mostrará un mensaje de error `connection refused`), yo voy a usar la VM con nombre `app-conf3.cloudapp.net`, así que escribiremos la siguiente orden.

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

**NOTA:** Si es la primera vez que entras, te sale un mensaje como el anterior y no configuraste el certificado **SSH**; escribe `yes` y seguidamente deberás escribir la **contraseña** en caso de haberla establecido al crear la VM para poder acceder a la VM.


4\. ¡Ya estamos dentro! Ahora toca configurar nuestro entorno para la aplicación. En mi caso he creado un script para la configuración de todo el entorno. (Son meramente órdenes de la shell pero que ayuda el no tener que repetirlas en cada VM a configurar)

[Código del script](https://github.com/oskyar/Practica3-VirtualMachine/blob/master/scriptConfiguracion.sh)

* El script me descarga, instala y configura.

	*JDK 1.7.0_45

	*Maven 3.1.1

* Eso sí, habrá que descargarse el script ¿no?. Con estas 3 órdenes hacemos todo.

		$ wget https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/scriptConfiguracion.sh
		$ chmod +x scriptConfiguracion.sh
		$ ./scriptConfiguracion.sh

**NOTA:** En caso de haber algún error con el script, seguir estos enlaces:

 * [Instalar JDK1.7.0_45](https://github.com/oskyar/Practica2-Jaula-CHROOT/blob/master/documentacion/instalacion_java.md#instalamos-el-jdk170-java-depevolpment-kit) (*El paso 6 no hace falta hacerlo, es para jaulas con CHROOT*)

 * [Instalar Maven 3.1.1](https://github.com/oskyar/Practica2-Jaula-CHROOT/blob/master/documentacion/instalacion_maven.md#instalaci%C3%B3n-de-maven)


5\. Ya tengo las herramientas de desarrollo que necesito para mi entorno operativas, pero me falta el ¡¡código!!, vamos a instalar git.

	$ sudo apt-get install git

6\. Y ahora voy a descargar/clonar mi proyecto.

	$ git clone git@github.com:oskyar/Practica3-VirtualMachine.git

7\. Como con mi proyecto ya viene configurado **Spring** (*servidor de aplicaciones*), ahora necesito  **instalar** las dependencias de mi proyecto con **maven** y ya podré ejecutarlo.

	$ mvn install

8\. ¡Vamos a desplegar la aplicación! (**Aclaración**:Supongo que estoy dentro de la carpeta del proyecto)

	$ sudo sh target/bin/webapp

9\. Saldrá algo como en la siguiente captura y si todo sale bien, pondrá al final `INFO  - log                        - Started SelectChannelConnector@0.0.0.0:80`

![Proyecto desplegado](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/9.DesplegandoAplicacion.png)


10\. Comprobemos que está funcionando, [pinchemos aquí](http://app-conf3.cloudapp.net)


11\. Ya tenemos todo corriendo, ahora solo falta usar un **benchmark** como `ab` para testear la aplicación e intentar sacarle el máximo rendimiento con el mínimo hardware posible.
	
* Cabe recordar que tenemos que hacer comparaciones con el mismo hardware pero diferentes VM instaladas.

* Las configuraciones son las siguientes (***TODAS EN AZURE***):

	| Configuración | SO 		| Cores 		|	RAM				|Tiempo Total Test(s)  |Solicitudes por Segundo	|Tiempo por solicitud (ms)|Velocidad de Transferencia (KB/s) |
	| :-----------:	| :------: 	| :-----: 		|:-----:|:---------:|:-------------:	|:------------:			|:------------:|
	| 	 1   		| Ubuntu 12.04 LTS   | 1 compartido	|768 MB 	|17,82				|82,23 					|17,82|27,37|
	| 	 2   		| Ubuntu 13.10     	| 1 compartido	|768 MB		|14,42				|89,46					|14,42|29,78|
	| 	 3   		| CentOS       	| 1 compartido	|768 MB 		|10,48				|97,96					|10,42|32,62|
	| 	 4   		| Ubuntu 12.04 LTS     	| 1 			|1,75 GB	|9,93			|104,22					|9,93|34,69|
	| 	 5   		| Ubuntu 13.10        	| 1 			|1,75 GB	|7,75			|129,08					|7,75|42,97|
	| 	 6   		| CentOS     	| 1 			|1,75 GB	|			8,85		|113,51 				|8,85|37,79|
	| 	 7   		| Ubuntu 12.04 LTS       	| 2				|3,75 GB 	|9,32		|108,77					|9,32|36,20|
	| 	 8   		| Ubuntu 13.10      	| 2 			|3,75 GB	|11,045		|97,09 					|11,04|32,33|
	| 	 9   		| CentOS       	| 2 			|3,75 GB	|9,29					|110,54 				|9,29|36,79|



12\. Me he adelantado poniendo los resultados, pero ahora mismo explico qué instrucción he usado y cómo he cambiado la configuración de las VMs.

* La orden es la siguiente

		$ ab -n 1000 -c 100 app-conf3.cloudapp.net/

	~~~
	-n: Indica el número de peticiones que se harán (1000)
	-c: Cuántas peticiones se harán concurrentemente (100)
	~~~

* **¿Cómo se cambia la configuración de nuestra VM?**

	1. Para hacerlo más intuitivo y menos tedioso, lo haremos desde el panel de administración en la web.

		Una vez dentro del panel, accedemos a **Virtual Machines** .

		![Lista de VM](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/7.ListaMaquinasVirtuales.png)


	2. Pinchamos en el nombre de la VM que queramos cambiar de configuración/recursos y una vez dentro le damos a la opción `configure`.

		![Cambiando recursos](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/10.CambiandoRecursos.png)

	3. Vemos que hay muchos tamaños de VM, pero con la suscripción solo podemos optar por los 3 primeros,

		* Extra small *(1 core compartido y 768 MB de RAM)*

		* Small *(1 Core y 1,75 GB de RAM)*

		* Medium *(2 Cores y 3,5 GB de RAM)*

	4. Si lo modificamos veremos que abajo, a pie de página nos aparecen dos iconos **SAVE** y **DISCARD**, pues, si queremos guardar esa configuración le daremos a **SAVE** y si no, lo descartaremos (DISCARD).

		* Si le damos a SAVE, nos mostrará otro mensaje emergente a pie de página en el que nos indica que la VM se reiniciará. Si no tenemos nada que perder le daremos a `OK`.


	5. Una vez cambiada la configuración se procede a pasar el benchmark de nuevo. Así sucesivamente con todas las máquinas virtuales desde 1 core compartido hasta 2 cores.


#### Comparaciones gráficas de las configuraciones.

![Comparación 1, Tiempo Test](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/g1.TiempoPorTest.png)

![Comparación 2, Solicitud por Segundo](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/g2.SolicitudPorSegundo.png)

![Comparación 3, Tiempo por Solicitud](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/g3.TiempoPorSolicitud.png)

![Comparación 4, Velocidad de Transferencia](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/g4.VelocidadDeTransferencia.png)

![TODOS JUNTOS](https://raw2.github.com/oskyar/Practica3-VirtualMachine/master/documentacion/img/g5.TodosJuntos.png)



##Conclusión:

En vista de la información que nos ofrece el benchmark `ab`, podemos observar que para una aplicación realizada en el lenguaje de programación **JAVA** y con un servidor de aplicaciones como **SPRING** sin Base de datos, la VM con mejor rendimiento ha sido **Ubuntu 13.10 con 1 core y 1,75 GB de RAM**. No le he realizado ninguna prueba de estrés, puesto que siendo realista, esta aplicación a lo sumo tendría 30-40 solicitudes en horas puntas si los alumnos la utilizaran para saber cuánto saben de la asignatura de "Infraestructuras Virtuales". 

A la vista está también, que con menos recursos, CentOS con 1 core compartido y 768 MB de RAM está muy muy cerca del rendimiento de Ubuntu 13.10 con 1 Core y 1,75 GB de RAM.

Dado que tienen prácticamente el mismo rendimiento y que en IaaS como Azure se paga por minutos y por recursos contratados, para mi aplicación bastaría con CentOS (ExtraSmall) y quizá se podría añadir en la misma VM una Base de Datos y seguiría estando igual de optimizado.



##ENLACES:

[(ACTIVA) VM 1 - Ubuntu 13.10 - 1 Core - 1,75 GB RAM](http://app-conf2.cloudapp.net)

[(ACTIVA) VM 2 - CentOS - 1 Core Compartido - 768 MB RAM](http://app-conf3.cloudapp.net)

[Documentación](https://github.com/oskyar/Practica3-VirtualMachine/blob/master/documentacion/documentacion.md)

[Issue](https://github.com/IV-GII/GII-2013/issues/117)

[Repositorio](https://github.com/oskyar/Practica3-VirtualMachine)


##BIBLIOGRAFIA

http://jj.github.io/IV/documentos/temas/Almacenamiento#a_dnde_ir_desde_aqu

http://jj.github.io/IV/documentos/temas/Uso_de_sistemas

https://manage.windowsazure.com