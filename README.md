# Spring Admin Management - Sécurisé, Analyse de journaux et Déploiement automatisé
> Ce projet implémente une application Spring Boot avec une sécurité robuste utilisant Keycloak, une analyse de journaux centralisée avec la pile ELK (l'intégration n'est pas incluse dans cet exemple) et un déploiement automatisé via GitHub Actions.

## Fonctionnalités principales
* **Authentification sécurisée:** Exploite Keycloak pour l'authentification et l'autorisation des utilisateurs, garantissant un accès contrôlé à votre application.
* **Journalisation centralisée:** S'intègre à la pile ELK (Elasticsearch, Logstash et Kibana) pour une analyse complète des journaux, fournissant des informations précieuses sur le comportement de l'application (configuration requise).
* **Déploiement automatisé:** Utilise GitHub Actions pour rationaliser le processus de construction, de test et de déploiement vers Docker Hub lors de la publication des modifications sur la branche principale.
  
## Instructions d'utilisation
1. Cloner le repository.
2. Docker installé et en cours d'exécution (https://docs.docker.com/get-docker/)
3. Une instance ELK Stack en cours d'exécution (pour l'analyse des logs)
4. Configurer Keycloak (voir les captures à la section *Configuration keycloak*)
5. Modifier les configurations d'accès à Docker Hub dans les secrets de votre repository GitHub (voir les captures à la section *Génération token docker hub*)
6. Pousser les modifications vers la branche principale pour déclencher le déploiement automatique.

## Messages d'exceptions dans `messages.properties`
Dans notre application, les messages d'exceptions sont personnalisés et définis dans le fichier `messages.properties` qui se trouve dans le dossier **resources**. Ces messages sont également enregistrés dans le fichier `/logs/logs.log`.
Vous pouvez personnaliser ces messages selon vos besoins. Voici les messages actuellement définis :

```properties
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
## Docker Compose
Le fichier `docker-compose.yml` contient la configuration pour les services MySQL, phpMyAdmin, PostgreSQL, pgAdmin, et Keycloak.
> L'application Spring Boot utilise MySQL comme base de données principale. Assurez-vous de configurer correctement les paramètres de connexion dans le fichier `application.yml`;

> Keycloak, d'autre part, est connecté à une base de données PostgreSQL. La configuration de Keycloak dans le fichier docker-compose.yml assure que Keycloak utilise la base de données PostgreSQL définie dans le service postgres-service:
```yaml
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

  # postgres service
  postgres-service:
    image: postgres
    container_name: postgres-service
    volumes:
      - postgres_data:/var/lib/postgresql/data
    environment:
      POSTGRES_DB: keycloak_db
      POSTGRES_USER: keycloak
      POSTGRES_PASSWORD: k1234
    ports:
      - '5432:5432'
    expose:
      - '5432'
  # pgadmin interface web pour l'administration de postgresql
  pgadmin4-service:
    image: dpage/pgadmin4
    container_name: pgadmin4
    restart: always
    ports:
      - "8888:80"
    environment:
      PGADMIN_DEFAULT_EMAIL: diallo@gmail.com
      PGADMIN_DEFAULT_PASSWORD: 1234
    volumes:
      - pgadmin_data:/var/lib/pgadmin

  # keycloak
  keycloak-service:
    image: quay.io/keycloak/keycloak:latest
    environment:
      KC_DB: postgres
      KC_DB_URL: jdbc:postgresql://postgres-service:5432/keycloak_db
      KC_DB_USERNAME: keycloak
      KC_DB_PASSWORD: k1234
      KEYCLOAK_ADMIN: admin
      KC_HTTP_ENABLED: "true"
      KC_HOSTNAME_STRICT_HTTPS: "false"
      KEYCLOAK_ADMIN_PASSWORD: passer
    command:
      - start-dev
    restart: always
    ports:
      - '8080:8080'
    expose:
      - '8080'
    depends_on:
      - postgres-service

volumes:
  pgadmin_data:
  postgres_data:
  mysql_data:
    driver: local
```
## Application Configuration
Le fichier `application.yml` contient la configuration de l'application Spring Boot, y compris la configuration de la base de données, la sécurité avec Keycloak, et les paramètres de logging.
![Capture d'écran 2024-03-12 095338](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/6efe2fd8-5aa7-42ab-8a1e-4acebaaf6e04)

## GitHub Actions
Le fichier `.github/workflows/deploy.yml` définit un workflow GitHub Actions qui construit et pousse une image Docker contenant l'application vers Docker Hub lors d'un push sur la branche principale (main).
![Capture d'écran 2024-03-12 095037](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/3a980d1f-bb19-4290-ab01-0ff8ea5f8927)

## Configuration keycloak
1. **Création d'un realm**: un realm est un domaine qui gère un ensemble d'utilisateurs, d'informations d'identification, des rôles et des groupes
![Screenshot 2024-02-14 001035](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/f88cc0e4-0806-4a59-8cb1-221431dd75ee)
2. **Création d'un client**: un client est une application, les clients sont des entités pouvant demander à Keycloak d'authentifier un utilisateur
![Screenshot 2024-02-14 002558](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/bc3f215a-88b6-4d93-bf1a-9cf470eacf98)
![Screenshot 2024-02-14 002627](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/5e006eda-3cb3-4936-b5ad-cacea6737063)
![Screenshot 2024-02-14 002656](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/84aca78c-e9c8-4fc0-9868-4aa24dc615c9)
3. **Création des rôles :** les rôles identifient un type ou une catégorie d'utilisateur, dans notre cas on a créé deux rôles (ADMIN,USER)
#### Endpoint :![Screenshot 2024-02-14 002928](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/617881a0-c202-4ef6-b2ea-5c3f96f3dfdf)
4. **Création des utilisateurs** : les utilisateurs sont des entités pouvant se connecter à notre système
![Screenshot 2024-02-14 003053](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/e9177bc1-fa04-47ab-9c7b-d58f27e69aa6)
![Screenshot 2024-02-14 003236](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/41d90938-a533-4f97-9066-187b29ac9811)
5. **Assigner des rôles aux utilisateurs** : faire appartenir un utilisateur à une catégorie
![Screenshot 2024-02-14 003436](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/dfe5ddfb-0aa9-4787-a118-efd9a5c2e22c)

## Test avec Postman

Pour tester les différentes fonctionnalités de l'API, nous utilisons Postman. Voici comment configurer les requêtes d'authentification :
Ces requêtes permettront d'obtenir un token d'authentification que nous pourrons utiliser pour tester les différentes fonctionnalités de l'API.

### 1. Authentification avec mot de passe

Pour utiliser l'authentification avec mot de passe, envoyez une requête **POST** à l'endpoint `http://localhost:8080/realms/admin-management-realm/protocol/openid-connect/token` avec les paramètres suivants dans le corps de la requête :

- `grant_type` : `password`
- `username` : `<votre_nom_d_utilisateur>`
- `password` : `<votre_mot_de_passe>`
- `client_id` : `<votre_client_id>`
![Screenshot 2024-02-14 004533](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/3130db08-744c-42fe-8d0f-daa6c4191e41)
![Screenshot 2024-02-14 004748](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/249fd50b-1cf9-4ba2-b920-8193e66f8219)

### 2. Authentification avec client_credentials

Pour utiliser l'authentification avec les identifiants du client, envoyez une requête **POST** à l'endpoint `http://localhost:8080/realms/admin-management-realm/protocol/openid-connect/token` avec les paramètres suivants dans le corps de la requête :

- `grant_type` : `client_credentials`
- `client_id` : `<votre_client_id>`
- `client_secret` : `<votre_client_secret>`

### 3. Authentification avec refresh_token

Pour utiliser l'authentification avec le token de rafraîchissement (refresh token), envoyez une requête **POST** à l'endpoint `http://localhost:8080/realms/admin-management-realm/protocol/openid-connect/token` avec les paramètres suivants dans le corps de la requête :

- `grant_type` : `refresh_token`
- `refresh_token` : `<votre_refresh_token>`
- `client_id` : `<votre_client_id>`
- `client_secret` : `<votre_client_secret>`
![Screenshot 2024-02-14 005829](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/6b858fe5-1df8-47c7-b317-194388582bc3)

### Test des endpoints sécurisés
> **Liste des roles**
![Screenshot 2023-12-25 031317](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/7bb1894a-0c72-46bc-aed7-2d94abd61bbe)
> **Récupération du role avec l'id 1**
> ![Screenshot 2023-12-25 031438](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/ac12816f-e148-4144-b4ae-8b5f1f4fc5aa)
> **Récupération d'un role qui n'existe pas, message d'erreur d'exception**
> ![Screenshot 2023-12-25 031612](https://github.com/BoubacarSIDDY/admin-management/assets/75427522/182ded86-0bf7-41f8-85ff-8cafa7aa7a95)



