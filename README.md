# Chatop API — Backend (Chatop)

API REST développée avec Spring Boot pour l'application de location Chatop

## Stack technique

- Java 17
- Spring Boot 4.1.0
- Spring Security + JWT
- Spring Data JPA
- MySQL 8

## Prérequis

- Java 17 installé
- MySQL 8 installé et lancé localement

## Installation de la base de données

1. Se connecter à MySQL et créer la base :

   ```bash
   mysql -u root -p -e "CREATE DATABASE chatop_db;"
   ```

2. Importer le schéma :

   ```bash
   mysql -u root -p chatop_db < script.sql
   ```

## Configuration de l'application

L'application charge ses propriétés sensibles depuis un fichier `local.properties`, situé à la racine du module `api/`, pour ne jamais exposer les credentials dans le code source ni dans le JAR compilé.

1. Se placer dans le dossier `api/` et copier le fichier d'exemple :

   ```bash
   cd api
   cp local.properties.example local.properties
   ```

2. Ouvrir `local.properties` et renseigner vos propres valeurs :

   ```properties
   spring.datasource.url=jdbc:mysql://<host>:<port>/<db_name>?serverTimezone=UTC
   spring.datasource.username=<db_username>
   spring.datasource.password=<db_password>
   jwt.secret=<your_jwt_secret>
   ```

Ce fichier est chargé automatiquement au démarrage via `spring.config.import` défini dans `application.properties`.

## Installation et lancement du projet

### 1. Cloner le repo

```bash
git clone https://github.com/<votre-user>/OC-P3-Backend.git
cd OC-P3-Backend/api
```

### 2. Configurer local.properties

Voir la section "Configuration de l'application" ci-dessus.

### 3. Builder et lancer le projet

```bash
./mvnw clean package
./mvnw spring-boot:run
```

L'application démarre sur `http://localhost:8080`.

### 4. Vérifier le bon fonctionnement

- Aucune erreur ne doit apparaître dans la console au démarrage.
- L'API doit être accessible sur `http://localhost:8080/api/...`

## Structure du projet
