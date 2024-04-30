DROP DATABASE IF EXISTS proyectoBase;

CREATE DATABASE IF NOT EXISTS proyectoBase;

USE proyectoBase;

CREATE TABLE IF NOT EXISTS usuarios(
	nick VARCHAR(255) PRIMARY KEY,
    contrasenya VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS playlists(
	id INT PRIMARY KEY,
    nombre VARCHAR(255),
    fecha DATE,
    nick_usuarios VARCHAR(255),
    CONSTRAINT FOREIGN KEY(nick_usuarios) REFERENCES usuarios(nick)
);

CREATE TABLE IF NOT EXISTS canciones(
	id INT PRIMARY KEY,
    titulo VARCHAR (255),
    duracion VARCHAR(255),
    anyo DATE
);

CREATE TABLE IF NOT EXISTS playlistsRutas(
	id_playlist INT,
    id_cancion INT,
    PRIMARY KEY (id_playlist,id_cancion),
    CONSTRAINT FOREIGN KEY(id_playlist) REFERENCES playlists(id),
    CONSTRAINT FOREIGN KEY(id_cancion) REFERENCES canciones(id)
);

CREATE TABLE IF NOT EXISTS artistas(
	id INT PRIMARY KEY,
    nombre VARCHAR(255),
    fechanacimiento DATE
);

CREATE TABLE IF NOT EXISTS posesionCanciones(
	id_artista INT,
	id_cancion INT,
	PRIMARY KEY (id_artista,id_cancion),
	CONSTRAINT FOREIGN KEY(id_artista) REFERENCES artistas(id),
    CONSTRAINT FOREIGN KEY(id_cancion) REFERENCES canciones(id)
);

