#! /bin/bash

echo " ----------------------------------"
echo " ----------------------------------"
echo " -------Instalación JDK -----------"

echo "[Yendo al directorio home]"
cd
echo "[Descargando jdk 7u45]"
wget https://dl.dropboxusercontent.com/u/3216105/jdk-7u45-linux-x64.tar.gz
echo "[Descomprimiendo jdk]"
sudo tar -xvf jdk-7u45-linux-x64.tar.gz
echo "[Creando directorio para el jdk]"
sudo mkdir -p /usr/lib/jvm/jdk1.7.0
echo "[Moviendo jdk a /usr/lib/jvm/]"
sudo cp -r jdk1.7.0_45/* /usr/lib/jvm/jdk1.7.0/

echo "[Configurando alternativas]"
sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.7.0/bin/java" 1
sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk1.7.0/bin/javac" 1
sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/lib/jvm/jdk1.7.0/bin/javaws" 1

echo "[Configurando alternativa por defecto]"
sudo update-alternatives --config java

echo "[Comprobación de que la configuración es correcta]"
java -version

echo "[Borrando carpeta de java]"
sudo rm -r jdk1.7.0_45/

echo " ----------------------------------"
echo " ----------------------------------"

echo " -------Instalación de Maven ------"

echo "[Descargando Maven 3]"
wget http://ftp.cixug.es/apache/maven/maven-3/3.1.1/binaries/apache-maven-3.1.1-bin.tar.gz

echo "[Creamos el directorio para maven]"
sudo mkdir -p /usr/local/apache-maven

echo "[Descomprimiendo Maven 3.1.1]"
tar -xvf apache-maven-3.1.1-bin.tar.gz

echo "[Copiando contenido al directorio de maven]"
sudo cp -r apache-maven-3.1.1 /usr/local/apache-maven/

echo "[Borrando carpeta de apache-maven-3]"
sudo rm -r apache-maven-3.1.1

echo "[Configurando variables de ENTORNO]"
export M2_HOME=/usr/local/apache-maven/apache-maven-3.1.1
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export JAVA_HOME=/usr/lib/jvm/jdk1.7.0
export PATH=$JAVA_HOME/bin:$M2:$PATH


echo "[Comprobación de que la configuración es correcta]"
mvn --version


echo "[Borrando todos los *.tar.gz]"
rm *.tar.gz