## Instalación de Maven

1. Descargamos `maven3.1.1` de la página oficial:

		$ wget http://ftp.cixug.es/apache/maven/maven-3/3.1.1/binaries/apache-maven-3.1.1-bin.tar.gz

2. Creamos el directorio para maven
	
		$ mkdir -p /usr/local/apache-maven

3. Copiamos el contenido en el directorio creado
	
		$ cp -r apache-maven-3.1.1 /usr/local/apache-maven/

4. Configuramos las variables de entorno necesarias para ejecutar maven y java(aunque java ya se configuró, no está de más ponerlas):
	
		$ export M2_HOME=/usr/local/apache-maven/apache-maven-3.1.1 
		$ export M2=$M2_HOME/bin
		$ export MAVEN_OPTS="-Xms256m -Xmx512m"
		$ export JAVA_HOME=/usr/lib/jvm/jdk1.7.0
		$ export PATH=$JAVA_HOME/bin:$M2:$PATH_BACKUP

	ó mediante el script (copiar en un archivo y darle permisos en modo de ejecución (*chmod +x script.sh*)

		#! /bin/bash
		PATH_BACKUP=$PATH
		echo $PATH_BACKUP
	
		export M2_HOME=/usr/local/apache-maven/apache-maven-3.1.1 
		echo $M2_HOME
		export M2=$M2_HOME/bin
		echo $M2
		export MAVEN_OPTS="-Xms256m -Xmx512m"
		echo $MAVEN_OPTS
		export JAVA_HOME=/usr/lib/jvm/jdk1.7.0
		echo $JAVA_HOME
		export PATH=$JAVA_HOME/bin:$M2:$PATH_BACKUP
		echo $PATH

5. Comprobamos que maven está bien configurado

		$ mvn --version


### [Volver a la práctica](https://github.com/oskyar/Practica2-Jaula-CHROOT/blob/master/documentacion/documentacion.md)