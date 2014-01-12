## Instalamos el `JDK1.7.0` (Java Depevolpment Kit)

1. Descargamos el JDK de la página oficial *(si no funciona descargarlo manualmente desde la página)*:

		$ wget http://download.oracle.com/otn-pub/java/jdk/7u45-b18/jdk-7u45-linux-x64.tar.gz

2. Instalación del paquete JDK. [Enlace 1](http://www.ubuntu-guia.com/2012/04/instalar-oracle-java-7-en-ubuntu-1204.html)  [Enlace 2](http://askubuntu.com/questions/56104/how-can-i-install-sun-oracles-proprietary-java-6-7-jre-or-jdk)

3. Ahora añadimos los ejecutables al directorio /usr/bin.

		$ update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.7.0/bin/java" 1
		 
		$ update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk1.7.0/bin/javac" 1
		
		$ update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/lib/jvm/jdk1.7.0/bin/javaws" 1

4. Damos permisos de ejecución a los usuarios

		$ chmod a+x /usr/bin/java
		
		$ chmod a+x /usr/bin/javac
		
		$ chmod a+x /usr/bin/javaws

5. Probamos que está bien instalado comprobando la versión de java.

		$ java -version

6. ***(EN CASO DE ERROR)***:

	Si nos indica que nos falta la librería `liblji.so` es porque tenemos que montar el directorio **proc**.

		$ mount -t proc proc /proc 

	*Volvemos al paso 5*


### [Volver a la práctica](https://github.com/oskyar/Practica2-Jaula-CHROOT/blob/master/documentacion/documentacion.md)