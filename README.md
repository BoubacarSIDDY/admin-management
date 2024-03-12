# Documentation

> Ce projet a pour but de mettre en place une application spring boot sécurisé en utilisant keycloak comme service de sécurité et ELK pour l'analyse des logs.

## Configuration du projet
Ci-dessous le détails des packages et quelques configurations à faire
1. #### Détails des différents packages:
* **config** : dans ce package nous avons gérés : les logs `LoggingAspect` d'une manière généralisée et aussi nous avons une classe `ApplicationConfig` pour gérer les jeux de caractères.
* **controlles** : nous utilisons des rest controllers.
* **dto** : pour la transformation de nos entités, pour éviter la manipulation directe des données.
* **entities** : ce package comme son nom l'indique contient nos entités
* **exceptions** : pour la gestion des exceptions : on a géré deux types d'exceptions (`EntityNotFoundException`,`RequestException`)
* **mapping** : ce package contient les classes de mapping (la transformation des dtos en entité et vice versa)
* **repositories** : les interfaces repository de chacune des nos entités
* **services** : ce package contient nos services
2. #### Quelques configurations à faire : 
> **Logs avec log4j** 
Créez un fichier `log4j2.xml` dans le dossier **resources** avec la configuration suivante :
```bash
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <Properties>
        <Property name="LOG_EXCEPTION_CONVERSION_WORD">%xwEx</Property>
        <Property name="LOG_PATTERN_LEVEL">%-5level [admin-management,%X{traceId},%X{spanId}]</Property>
        <Property name="LOG_DATEFORMAT_PATTERN">yyyy-MM-dd HH:mm:ss.SSS</Property>
        <Property name="LOG_PATTERN">%clr{%d{${sys:LOG_DATEFORMAT_PATTERN}}}{faint} %clr{${sys:LOG_PATTERN_LEVEL}} %clr{%pid}{magenta} %clr{---}{faint} %clr{[%15.15t]}{faint} %clr{%-40.40c{1.}}{cyan} %clr{:}{faint} %m%n${sys:LOG_EXCEPTION_CONVERSION_WORD}</Property>
    </Properties>
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT" follow="true">
            <PatternLayout pattern="${sys:LOG_PATTERN}"/>
        </Console>
        <Console name="Console_JSON" target="SYSTEM_OUT" follow="true">
            <JsonLayout complete="false" compact="false">
                <KeyValuePair key="service" value="admin-management"/>
                <KeyValuePair key="traceId" value="$${ctx:traceId}"/>
                <KeyValuePair key="spanId" value="$${ctx:spanId}"/>
            </JsonLayout>
        </Console>
        <!-- config fichier de log -->
        <File name="file" fileName="logs/logs.log">
            <!--PatternLayout pattern="[%t] %-5p | %-60c | %m (%F:%L)%n" /-->
            <JsonLayout complete="false" compact="false">
                <KeyValuePair key="service" value="admin-management"/>
            </JsonLayout>
        </File>
        <!-- config fichier de log -->
    </Appenders>

    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console" /><!-- pour avoir le format texte des logs -->
            <!-- AppenderRef ref="Console_JSON" / --><!-- pour avoir le format json des logs -->
            <AppenderRef ref="file" /><!-- config fichier de log -->
        </Root>

        <Logger name="com.groupeisi.adminmanagement.controllers" level="debug" additivity="false">
            <AppenderRef ref="Console_JSON" />
        </Logger>

        <Logger name="com.groupeisi.adminmanagement.services" level="debug" additivity="false">
            <AppenderRef ref="Console_JSON" />
        </Logger>

    </Loggers>

</Configuration>
```
> **Messages d'exceptions dans `messages.properties`**
Créez le fichier messages.properties dans le dossier **resources** et ajoutez les messages d'exceptions :
```bash
role.notfound=Requested Roles with id = {0} does not exist
role.exists=the Roles with id = {0} is already created
role.errordeletion=the Roles with id = {0} cannot be deleted

user.notfound=Requested User with id = {0} does not exist
user.exists=the User with id = {0} is already created
user.errordeletion=the User with id = {0} cannot be deleted

produit.notfound=Requested Product with id = {0} does not exist
produit.exists=the Product with id = {0} is already created
produit.errordeletion=the Product with id = {0} cannot be deleted
```
> **Configuration de la base de données avec Docker**
Utilisez le fichier docker-compose.yml à la racine du projet avec le code suivant :
```bash
version: '3.9'
services:
    mysql-admin-management-db:
      image: mysql:latest
      container_name: container_mysql-admin-management-db
    environment:
        MYSQL_ROOT_PASSWORD: root
        MYSQL_DATABASE: admin-management-db
        MYSQL_USER: diallo
        MYSQL_PASSWORD: passer
    ports:
    - 3306:3306
    volumes:
    - mysql_data:/var/lib/mysql
    healthcheck:
    test: mysqladmin ping -h 127.0.0.1 -u $$MYSQL_USER --password=$$MYSQL_PASSWORD


    phpmyadmin-admin-db:
        container_name: container_phpmyadmin-adminmgntdb
        image: phpmyadmin/phpmyadmin:latest
    ports:
    - 8085:80
    environment:
        MYSQL_ROOT_PASSWORD: root
        PMA_HOST: mysql-admin-management-db
        PMA_USER: diallo
        PMA_PASSWORD: passer
    depends_on:
    - mysql-admin-management-db
    restart: unless-stopped

volumes:
    mysql_data:
    driver: local
```

#### Endpoint :
> Ci-dessous une capture sur l'endPoint pour la gestion des rôles : **/roles** : 
> **Liste des roles**
![Screenshot 2023-12-25 031317](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/7bb1894a-0c72-46bc-aed7-2d94abd61bbe)
> **Récupération du role avec l'id 1**
> ![Screenshot 2023-12-25 031438](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/ac12816f-e148-4144-b4ae-8b5f1f4fc5aa)
> **Récupération d'un role qui n'existe pas, message d'erreur d'exception**
> ![Screenshot 2023-12-25 031612](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/182ded86-0bf7-41f8-85ff-8cafa7aa7a95)



