#! /bin/bash

echo " ----------------------------------"
echo " ----------------------------------"
echo " -------Instalación JDK -----------"

echo "Yendo al directorio home"
cd
echo "Descargando jdk 7u45"
wget http://download.oracle.com/otn-pub/java/jdk/7u45-b18/jdk-7u45-linux-i586.tar.gz
echo "Descomprimiendo jdk"
tar -xvf jdk-7u45-linux-i586.tar.gz
echo "Creando directorio para el jdk"
sudo mkdir -p /usr/lib/jvm/jdk1.7.0
echo "Moviendo jdk a /usr/lib/jvm/"
sudo mv jdk1.7.0_45/* /usr/lib/jvm/jdk1.7.0/

echo "Configurando alternativas"
sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.7.0/bin/java" 1
sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk1.7.0/bin/javac" 1
sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/lib/jvm/jdk1.7.0/bin/javaws" 1

echo "Configurando alternativa por defecto"
sudo update-alternatives --config java

echo "Comprobación de que la configuración es correcta"
java -version

echo " ----------------------------------"
echo " ----------------------------------"

echo " -------Instalación de Maven ------"

echo "Descargando Maven 3"
wget http://ftp.cixug.es/apache/maven/maven-3/3.1.1/binaries/apache-maven-3.1.1-bin.tar.gz

echo "Creamos el directorio para maven"
sudo mkdir -p /usr/local/apache-maven

echo "Descomprimiendo Maven 3.1.1"
tar -xvf apache-maven-3.1.1-bin.tar.gz

echo "Copiando contenido al directorio de maven"
sudo cp -r apache-maven-3.1.1 /usr/local/apache-maven/

echo "Configurando variables de ENTORNO"
export M2_HOME=/usr/local/apache-maven/apache-maven-3.1.1 
echo $M2_HOME
export M2=$M2_HOME/bin
echo $M2
export MAVEN_OPTS="-Xms256m -Xmx512m"
echo $MAVEN_OPTS
export JAVA_HOME=/usr/lib/jvm/jdk1.7.0
echo $JAVA_HOME
export PATH=$JAVA_HOME/bin:$M2:$PATH_BACKUP


echo "Comprobación de que la configuración es correcta"
maven --version


echo " -------------------------------------"
echo " -------------------------------------"

echo " Instalación Servidor de Aplicaciones Jboss 7.1.0 Final "

echo "Descargando Jboss"
wget http://download.jboss.org/jbossas/7.1/jboss-as-7.1.0.Final/jboss-as-7.1.0.Final.tar.gz

echo "Descomprimiendo"
tar -xvf jboss-as-7.1.0.Final.tar.gz