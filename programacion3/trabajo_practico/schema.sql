/* Base de Datos */
CREATE DATABASE IF NOT EXISTS prog3;
USE prog3;

/* Tipo de Usuario */
CREATE TABLE Tipo_Usuario (
    cod_tipo_usuario CHAR(3) NOT NULL,
    nombre_tipo CHAR(50) NOT NULL,
    PRIMARY KEY (cod_tipo_usuario)
);

-- Cargar Tipos
INSERT INTO Tipo_Usuario VALUES ('ADM', 'Administrador');
INSERT INTO Tipo_Usuario VALUES ('CLI', 'Cliente');

/* Usuario */
CREATE TABLE Usuario (
    usuario_id INT NOT NULL AUTO_INCREMENT,
    cod_tipo_usuario CHAR(3) NOT NULL,
    nombre CHAR(50) NOT NULL,
    apellido CHAR(50) NOT NULL,
    PRIMARY KEY (usuario_id),
    FOREIGN KEY (cod_tipo_usuario)
      REFERENCES Tipo_Usuario(cod_tipo_usuario)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);
