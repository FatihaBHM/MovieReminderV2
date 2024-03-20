[//]: # (#projet fil rouge Epita-La Fabrique Version4 en anglais)

[//]: # (mesFilmsEtSeries - movieReminder)

[//]: # ()
[//]: # (#Rebelote : )

[//]: # (#1-architecture avec modification du pom parents et des enfants)

[//]: # (#2Classes et relations #3configuration Docker)

# Docker
## Cloner le projet

```shell
git clone git@github.com:FatihaBHM/movieReminder-mesFilmsEtSeries.git
```

---

## Créer un fichier .env

Créer un fichier .env à la racine du projet avec les variables d'environnement suivantes :
```
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=movieReminder
```

---

## Lancer l'application

Pour lancer l'application avec Docker, il suffit de lancer la commande suivante à la racine du projet :
```shell
docker-compose up
```
ou
```shell
docker-compose up -d # pour lancer en arrière-plan
```

---

## Arrêter l'application

Pour arrêter l'application, il suffit de lancer la commande suivante à la racine du projet :
```shell
docker-compose down
```

---

## Accéder à l'application

L'application est accessible à l'adresse suivante :
```
http://localhost:8080
```

---

## Forcer la reconstruction des images

Pour forcer la reconstruction des images, il suffit de lancer la commande suivante à la racine du projet :
```shell
docker-compose up --build
```
ou
```shell
docker-compose up -d --build # pour lancer en arrière-plan
```
ou
```shell
docker-compose up --build --force-recreate # pour forcer la reconstruction des images
```


