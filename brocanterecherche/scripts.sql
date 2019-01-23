create database PFE_PETITES_ANNONCES;

use PFE_PETITES_ANNONCES;

CREATE TABLE CATEGORIE (
  categorie_id int(20) NOT NULL AUTO_INCREMENT,
  nom_categorie varchar(100) DEFAULT NULL,
  PRIMARY KEY (categorie_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO categorie (categorie_id, nom_categorie) VALUES
(1, 'EMPLOI'),
(2, 'VEHICULES'),
(3, 'IMMOBILIER'),
(4, 'MAISON'),
(5, 'VACANCES'),
(6, 'MULTIMEDIA'),
(7, 'LOISIRS'),
(8, 'MATERIEL PROFESSIONNEL'),
(9, 'SERVICES'),
(10, 'AUTRES');

CREATE TABLE COMPTE (
  compte_id int(20) NOT NULL AUTO_INCREMENT,
  nom varchar(100) DEFAULT NULL,
  prenom varchar(256) DEFAULT NULL,
  email varchar(256) DEFAULT NULL,
  password varchar(256) DEFAULT NULL,
  numeroPhone varchar(256) DEFAULT NULL,
  PRIMARY KEY (compte_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE ANNONCE (
  annonce_id int(20) NOT NULL AUTO_INCREMENT,
  titre varchar(100) DEFAULT NULL,
  description varchar(256) DEFAULT NULL,
  photo mediumblob,
  categorie_id int(20),
  prix double(8,3),
  compte_id int(20),
  PRIMARY KEY (annonce_id),
  FOREIGN KEY (compte_id) REFERENCES COMPTE(compte_id),
   FOREIGN KEY (categorie_id) REFERENCES CATEGORIE(categorie_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;







