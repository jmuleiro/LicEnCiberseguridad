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

-- Cargar Monedas
INSERT INTO Moneda VALUES ('ARS', 'Pesos Argentinos');
INSERT INTO Moneda VALUES ('USD', 'Dolares');

/* Tipo de Cuenta */
CREATE TABLE Tipo_Cuenta (
    cod_tipo_cuenta CHAR(3) NOT NULL,
    nombre_tipo CHAR(50) NOT NULL,
    PRIMARY KEY (cod_tipo_cuenta)
);

-- Cargar Tipos
INSERT INTO Tipo_Cuenta VALUES ('COR', 'Cuenta Corriente');
INSERT INTO Tipo_Cuenta VALUES ('SAV', 'Caja de Ahorro');

/* Tipo de Tarjeta */
CREATE TABLE Tipo_Tarjeta (
    cod_tipo_tarjeta CHAR(3) NOT NULL,
    nombre_tipo CHAR(50) NOT NULL,
    PRIMARY KEY (cod_tipo_tarjeta)
);

-- Cargar Tipos
INSERT INTO Tipo_Tarjeta VALUES ('CRE', 'Credito');
INSERT INTO Tipo_Tarjeta VALUES ('DEB', 'Debito');

/* Usuario */
/*todo: implementar contraseña hasheada si hay tiempo*/
CREATE TABLE Usuario (
    usuario_id INT NOT NULL AUTO_INCREMENT,
    cod_tipo_usuario CHAR(3) NOT NULL,
    nombre CHAR(50) NOT NULL,
    apellido CHAR(50) NOT NULL,
    usuario CHAR(50) NOT NULL,
    PRIMARY KEY (usuario_id),
    FOREIGN KEY (cod_tipo_usuario)
      REFERENCES Tipo_Usuario(cod_tipo_usuario)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Cargar Usuarios
INSERT INTO Usuario (cod_tipo_usuario, nombre, apellido, usuario)
VALUES ("ADM", "Joaquin", "Muleiro", "jmuleiro"),
("CLI", "Fulanito", "Gonzalez", "fgonzalez"),
("CLI", "Juan", "Perez", "jperez");

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
    saldo DOUBLE NOT NULL,
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

-- Cargar Cuentas
INSERT INTO Cuenta (cod_tipo_cuenta, usuario_id, cod_moneda, alias, cbu, limite_giro, porcentaje_interes, saldo)
VALUES ("SAV", 1, "ARS", NULL, 808508180, 0, 1, 100000);

/* Tarjeta */
CREATE TABLE Tarjeta (
    tarjeta_id INT NOT NULL AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    cod_tipo_tarjeta CHAR(3) NOT NULL,
    limite DOUBLE NOT NULL,
    numero VARCHAR(16) NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    cvc INT NOT NULL,
    PRIMARY KEY (tarjeta_id),
    
    -- Foreign Key 1: Usuario
    FOREIGN KEY (usuario_id)
        REFERENCES Usuario(usuario_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
        
    -- Foreign Key 2: Tipo de Tarjeta
    FOREIGN KEY (cod_tipo_tarjeta)
        REFERENCES Tipo_Tarjeta(cod_tipo_tarjeta)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

/* Consumo */
CREATE TABLE Consumo (
    tarjeta_id INT NOT NULL,
    consumo_id INT NOT NULL,
    fecha DATE NOT NULL,
    cantidad DOUBLE NOT NULL,
    cod_moneda CHAR(3) NOT NULL,
    PRIMARY KEY (tarjeta_id, consumo_id),
    
    -- Foreign Key 1: Tarjeta
    FOREIGN KEY (tarjeta_id)
        REFERENCES Tarjeta(tarjeta_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
        
    -- Foreign Key 2: Moneda
    FOREIGN KEY (cod_moneda)
        REFERENCES Moneda(cod_moneda)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

/* Transferencia */
CREATE TABLE Transferencia (
    transferencia_id INT NOT NULL AUTO_INCREMENT,
    monto DOUBLE NOT NULL,
    cod_moneda CHAR(3) NOT NULL,
    concepto CHAR(50) NOT NULL,
    PRIMARY KEY (transferencia_id),
    
    -- Foreign Key: Moneda
    FOREIGN KEY (cod_moneda)
        REFERENCES Moneda(cod_moneda)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

/* Movimiento */
CREATE TABLE Movimiento (
    transferencia_id INT NOT NULL,
    cuenta_id INT NOT NULL,
    entrante BOOLEAN NOT NULL, -- TRUE para transferencias entrantes, FALSE para salientes
    PRIMARY KEY (transferencia_id, cuenta_id),
    
    -- Foreign Key 1: Transferencia
    FOREIGN KEY (transferencia_id)
        REFERENCES Transferencia(transferencia_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
        
    -- Foreign Key 2: Cuenta
    FOREIGN KEY (cuenta_id)
        REFERENCES Cuenta(cuenta_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);
