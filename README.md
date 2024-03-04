# Gestion de location de véhicules
Ce projet est une application web développée en Java pour gérer 
la location de véhicules. Il utilise Maven comme outil de gestion de 
dépendances et de construction de projet. L'architecture suit les principes 
de développement web Java EE.

## Configuration du Projet
1. Cloner le projet depuis le dépôt Git.
2. Importer le projet dans votre environnement de développement (par exemple, Eclipse).
3. Assurez-vous d'avoir Java JDK installé sur votre système.
4. Configurer le projet en tant qu'application web avec Maven.

## Installation
1. Exécuter la commande ``mvn clean install`` pour installer les dépendances et construire le projet.
2. Utiliser la commande ``mvn tomcat7:run`` pour lancer l'application sur un serveur Tomcat embarqué.

## Structure du Projet
* src/main : Contient le code source principal de l'application.
    * webapp : Contient les ressources web de l'application.
    * java : Contient les packages Java de l'application.
* src/test : Contient les tests unitaires de l'application.
* pom.xml : Fichier de configuration Maven du projet.

## Fonctionnalités Principales
1. Gestion des Clients : Création, mise à jour, suppression, et listing des clients.
2. Gestion des Véhicules : Création, mise à jour, suppression, et listing des véhicules.
3. Gestion des Réservations : Création, mise à jour, suppression, et listing des réservations.
4. Interface Utilisateur en Ligne de Commande : Permet d'accéder aux fonctionnalités via une interface en ligne de commande.

## Annexes
Consultez les annexes pour plus de détails sur le schéma des tables de la base 
de données, les requêtes SQL proposées, et des explications sur le design 
pattern Singleton.