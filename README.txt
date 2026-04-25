========================================================
  Projet Montre Connectée - POO1 2025-2026
========================================================

Auteurs : Mohamed El Brrah & Michael Djeatsa
Université de Picardie Jules Verne - Licence Informatique L2

--------------------------------------------------------
DESCRIPTION
--------------------------------------------------------

Application Java simulant l'écran d'une montre connectée.
Permet de personnaliser la forme du boîtier, la couleur du
fond, le type d'affichage (analogique ou numérique) et
d'ajouter des éléments optionnels : date, rythme cardiaque,
batterie. La configuration peut être sauvegardée et
rechargée depuis un fichier.

--------------------------------------------------------
PRÉREQUIS
--------------------------------------------------------

  - Java JDK >= 11
  - Système d'exploitation : Windows, macOS ou Linux

--------------------------------------------------------
EXÉCUTION RAPIDE (avec le jar fourni)
--------------------------------------------------------

  java -jar montre.jar

--------------------------------------------------------
COMPILATION DEPUIS LES SOURCES
--------------------------------------------------------


--------------------------------------------------------
STRUCTURE DU PROJET
--------------------------------------------------------

  ProjetMontre/
  ├── README.txt           - ce fichier
  ├── rapport.docx         - rapport du projet
  ├── montre.jar           - exécutable
  └── src/                 - sources Java
      ├── ElementMontre.java
      ├── ElementMontreAbstrait.java
      ├── Aiguille.java
      ├── AiguilleHeures.java
      ├── AiguilleMinutes.java
      ├── AiguilleSecondes.java
      ├── MiniCadranSecondes.java
      ├── CompteurSecondes.java
      ├── HorlogeNumerique.java
      ├── DateDisplay.java
      ├── RythmeCardiaque.java
      ├── EtatBatterie.java
      ├── WatchModel.java
      ├── ReglagesMontre.java
      ├── WatchPanel.java
      └── MainFrame.java

--------------------------------------------------------
NAVIGATION DANS L'APPLICATION
--------------------------------------------------------

L'application contient 5 pages accessibles via la barre
de boutons en bas de l'écran :

  - Montre   : cadran principal avec aiguilles
  - Date     : affichage de la date du jour
  - Rythme   : rythme cardiaque simulé
  - Batterie : état de la batterie
  - Réglages : configuration de la montre

========================================================