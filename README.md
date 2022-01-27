# Kikichante

## Prerequis
__Posseder la version 17 du JDK de java.__  
__Posseder IntelliJ de présence afin de récupérer le projet github sans soucis.__

## Serveur  
Il existe deux façons de lancer le serveur (en local):
 - Depuis ___IntelliJ___ :  Il suffit de lancer _Server.java_ (Il se trouve le package _kikichanteFx_)
 - Si le serveur est en .jar, il faudrat verifier la présence du dossier ___musiques___ à la racine du .jar ainsi qu'efectuer la commande suivante pour l'executer :
```bash
java -jar kikichante-fx.jar
```
Pour lancer le serveur sur les services d'Amazon afin de jouer à plusieurs, il suffit d'upload le .jar ainsi que les musiques et d'executer la commandeci-dessus.

## Client
Afin d'effectuer la bonne execution du client il faut que le serveur soit lancé au préalable, soit en local, soit sur le serveur Amazon.  
En fonction du type de lancement du serveur, il est nécessaire d'effectuer un changement coté client.
- Si le serveur est en local, dans le fichier "Application.java" l:18, l'adresse renseignée devra être la suivante
```java
static final String ADRESS = "localhost";
```
- Si le serveur est lancé depuis amazon aws, l'adresse à renseignée sera la suivante :
```java
static final String ADRESS = "18.195.102.21";
```
Le client se lance depuis l'IDE

### Informations utiles
Le client ne peut tourner sans la présence du serveur, si le serveur crash alors le client se fermera automatiquement.