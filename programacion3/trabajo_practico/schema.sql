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

/* Moneda */
CREATE TABLE Moneda (
    cod_moneda CHAR(3) NOT NULL,
    nombre_moneda CHAR(50) NOT NULL,
    PRIMARY KEY (cod_moneda)
);

/* Tipo de Cuenta */
CREATE TABLE Tipo_Cuenta (
    cod_tipo_cuenta CHAR(3) NOT NULL,
    nombre_tipo CHAR(50) NOT NULL,
    PRIMARY KEY (cod_tipo_cuenta)
);

-- Cargar Tipos
INSERT INTO Tipo_Cuenta VALUES ('COR', 'Cuenta Corriente');
INSERT INTO Tipo_Cuenta VALUES ('SAV', 'Caja de Ahorro');

-- Cargar Monedas
INSERT INTO Moneda VALUES ('ARS', 'Pesos Argentinos');
INSERT INTO Moneda VALUES ('USD', 'Dolares');

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

/* Cuenta */
CREATE TABLE Cuenta (
    cuenta_id INT NOT NULL AUTO_INCREMENT,
    cod_tipo_cuenta CHAR(3) NOT NULL,
    usuario_id INT NOT NULL,
    cod_moneda CHAR(3) NOT NULL,
    alias CHAR(50),
    cbu INT NOT NULL,
    limite_giro DOUBLE NOT NULL,
    porcentaje_interes DOUBLE NOT NULL,
    PRIMARY KEY (cuenta_id),
    
    -- Foreign Key 1: Tipo_Cuenta
    FOREIGN KEY (cod_tipo_cuenta)
        REFERENCES Tipo_Cuenta(cod_tipo_cuenta)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
        
    -- Foreign Key 2: Usuario
    FOREIGN KEY (usuario_id)
        REFERENCES Usuario(usuario_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
        
    -- Foreign Key 3: Moneda
    FOREIGN KEY (cod_moneda)
        REFERENCES Moneda(cod_moneda)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);
