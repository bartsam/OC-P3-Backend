# Chatop API — Backend

API REST développée avec Spring Boot pour l'application de location Chatop.

## Stack technique

- Java 17
- Spring Boot 4.1.0
- Spring Security + JWT
- Spring Data JPA
- MySQL 8

## Objectif

Ce dépôt contient le backend Java/Spring Boot de l'application Chatop. Il fournit les opérations suivantes :

- inscription et connexion avec JWT
- consultation du profil utilisateur connecté
- création, consultation et mise à jour des locations
- upload d'image pour une location
- envoi de messages liés à une location
- documentation Swagger/OpenAPI

## Prérequis

Avant de lancer le projet, vous devez avoir installé localement :

- Java 17+
- Maven Wrapper (présent dans le repo via `mvnw`)
- MySQL 8
- un client MySQL

## Installation et lancement du projet

### 1. Cloner le dépôt

```bash
git clone https://github.com/bartsam/OC-P3-Backend.git
cd OC-P3-Backend
```

### 2. Créer une base MySQL locale

```sql
CREATE DATABASE chatop_db;
```

Puis exécutez le script `script.sql` (fourni à la racine du projet) dans cette base :

```bash
mysql -u <user> -p chatop_db < script.sql
```

### 3. Configurer les variables d'environnement locales

Copiez le fichier d'exemple puis modifiez-le :

```bash
cp local.properties.example local.properties
```

Puis ajoutez les valeurs de connexion MySQL et la clé JWT dans `local.properties` :

```properties
spring.datasource.url=jdbc:mysql://<host>:<port>/<db_name>?serverTimezone=UTC
spring.datasource.username=<votre_utilisateur_mysql>
spring.datasource.password=<votre_mot_de_passe_mysql>
jwt.secret=<une_clé_secrète_jwt>
```

Ce fichier est importé automatiquement au démarrage via `application.properties`.

Important :

- `local.properties` ne doit jamais être commité dans Git.
- Les identifiants de connexion ne doivent pas être écrits en clair dans le code source.

### 4. Compiler le projet

```bash
./mvnw clean package
```

### 5. Lancer l'application

```bash
./mvnw spring-boot:run
```

L'application démarre sur http://localhost:3001 (port configurable dans `local.properties` via `server.port`).

### 6. Vérifier le démarrage

Après lancement, vous devez voir une application Spring Boot démarrée sans erreur dans la console.

## Documentation Swagger

Swagger UI est accessible à l'adresse suivante :

- http://localhost:3001/swagger-ui/index.html

La documentation OpenAPI est disponible via :

- http://localhost:3001/v3/api-docs

### Authentification Swagger

- Les endpoints publics sont : `register`, `login` et Swagger UI.
- Les autres endpoints nécessitent un token JWT dans l'en-tête `Authorization` au format `Bearer <token>`.

## Sécurité et bonnes pratiques

Ce projet applique :

- Spring Security
- authentification JWT
- chiffrement BCrypt des mots de passe
- exclusion des credentials de la base du code source
- fichier `local.properties` pour la configuration sensible

Les routes publiques sont limitées à l'inscription, la connexion et la documentation Swagger.

## Structure

Le projet suit l'architecture Spring Boot en couches :

- `controllers` : points d'entrée HTTP
- `services` : logique métier
- `repositories` : accès à la base via JPA
- `models` : entités JPA
- `dto` : objets de transfert de données
- `exceptions` : gestion des erreurs

## Vérification rapide

Pour vérifier que le projet compile correctement :

```bash
./mvnw test
```

## Axes d'amélioration

Ce backend a été développé pour répondre au contrat d'API défini par un mock fourni dans le cadre de la formation. Certaines opérations n'y sont donc pas implémentées, car non prévues par ce contrat :

- **Suppression d'une location** : aucun endpoint `DELETE /api/rentals/{id}` n'existe actuellement.
- **Mise à jour du profil utilisateur** : aucun endpoint `PUT /api/user/{id}` n'existe pour modifier nom, email ou mot de passe après inscription.
- **Suppression d'un compte utilisateur** : aucun endpoint `DELETE /api/user/{id}` n'est prévu.
- **Consultation des messages d'une location** : aucun `GET /api/messages` n'existe, seul l'envoi (`POST`) est implémenté.
- **Pagination des locations** : `GET /api/rentals` renvoie actuellement l'ensemble des locations sans pagination, ce qui pourrait poser un problème de performance à grande échelle.

Ces axes constituent des évolutions naturelles pour une version future de l'API, au-delà du périmètre couvert par le mock initial.

## Notes importantes

- Les images uploadées sont stockées localement dans le dossier `uploads`.
- Le frontend doit utiliser l'URL publique exposée par l'API pour afficher ces images.
