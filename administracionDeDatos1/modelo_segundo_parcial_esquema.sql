/*
Marca (cod_marca, nombre_marca)
Cerradura (Cod_cerradura, ce_nombre, precio, ce_stock, cod_marca)
Pieza (Cod_pieza, pi_nombre, Cod_proveedor, costo, pi_stock)
Composicion (Cod_cerradura, Cod_pieza, cant)
Proveedor (Cod_proveedor, Razon_social, Tel, e_mail)
*/

-- Base de datos
USE segparcial;

-- Tabla de marcas
CREATE TABLE Marca (
    cod_marca INT PRIMARY KEY,
    nombre_marca VARCHAR(100) NOT NULL
);

-- Tabla de proveedores
CREATE TABLE Proveedor (
    Cod_proveedor INT PRIMARY KEY,
    Razon_social VARCHAR(150) NOT NULL,
    Tel VARCHAR(20),
    e_mail VARCHAR(100)
);

-- Tabla de cerraduras
CREATE TABLE Cerradura (
    Cod_cerradura INT PRIMARY KEY,
    ce_nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    ce_stock INT NOT NULL,
    cod_marca INT NOT NULL,
    FOREIGN KEY (cod_marca) REFERENCES Marca(cod_marca)
);

-- Tabla de piezas
CREATE TABLE Pieza (
    Cod_pieza INT PRIMARY KEY,
    pi_nombre VARCHAR(100) NOT NULL,
    Cod_proveedor INT NOT NULL,
    costo DECIMAL(10, 2) NOT NULL,
    pi_stock INT NOT NULL,
    FOREIGN KEY (Cod_proveedor) REFERENCES Proveedor(Cod_proveedor)
);

-- Tabla de composición de cerraduras (relación muchos a muchos entre Cerradura y Pieza)
CREATE TABLE Composicion (
    Cod_cerradura INT NOT NULL,
    Cod_pieza INT NOT NULL,
    cant INT NOT NULL,
    PRIMARY KEY (Cod_cerradura, Cod_pieza),
    FOREIGN KEY (Cod_cerradura) REFERENCES Cerradura(Cod_cerradura),
    FOREIGN KEY (Cod_pieza) REFERENCES Pieza(Cod_pieza)
);

-- Marcas
INSERT INTO Marca (cod_marca, nombre_marca) VALUES 
(1, 'Yale'),
(2, 'Schlage'),
(3, 'Tesa'),
(4, 'Kwikset'),
(5, 'ASSA ABLOY');

-- Proveedores
INSERT INTO Proveedor (Cod_proveedor, Razon_social, Tel, e_mail) VALUES
(1, 'Metalúrgica Gómez S.A.', '011-4567-1234', 'contacto@gomezmetal.com'),
(2, 'Industrias Fierro SRL', '0341-789-4567', 'ventas@fierrosrl.com'),
(3, 'ProveLock Argentina', '011-1234-5678', 'info@provelock.com'),
(4, 'Importadora Segurimax', '0351-321-9876', 'comercial@segurimax.com'),
(5, 'TecnoPiezas Ltda.', '011-4444-8888', 'tecnopiezas@gmail.com'),
(6, 'Herrajes del Plata', '0221-876-3456', 'soporte@herrajesdelplata.com.ar'),
(7, 'Distribuidora Fénix', '0261-654-1234', 'fenixventas@gmail.com'),
(8, 'LockPro S.A.', '0381-789-2345', 'clientes@lockpro.com'),
(9, 'Soluciones Metálicas', '011-5555-1111', 'info@solucionesmetalicas.com'),
(10, 'Componentes Industriales SRL', '0291-987-6543', 'ventas@compind.com');
INSERT INTO Proveedor (Cod_proveedor, Razon_social, Tel, e_mail) VALUES
(11, 'Cerramix', '011-9999-9999', 'contacto@cerramix.com');

-- Cerraduras
INSERT INTO Cerradura (Cod_cerradura, ce_nombre, precio, ce_stock, cod_marca) VALUES
(1, 'Cerradura Yale 1000', 12500.00, 50, 1),
(2, 'Cerradura Yale Digital', 28700.00, 20, 1),
(3, 'Cerradura Schlage Classic', 13200.50, 35, 2),
(4, 'Cerradura Schlage Touchscreen', 31250.75, 15, 2),
(5, 'Cerradura Tesa Alta Seguridad', 14500.00, 40, 3),
(6, 'Cerradura Tesa Digital Slim', 19800.00, 18, 3),
(7, 'Cerradura Kwikset SmartKey', 16700.00, 25, 4),
(8, 'Cerradura Kwikset Tradicional', 11200.00, 60, 4),
(9, 'Cerradura ASSA ABLOY Residencial', 15400.00, 30, 5),
(10, 'Cerradura ASSA ABLOY Pro', 28900.00, 12, 5),
(11, 'Cerradura Yale Pro Plus', 35000.00, 10, 1),
(12, 'Cerradura Tesa Compacta', 9900.00, 55, 3),
(13, 'Cerradura Schlage Mini', 8800.00, 45, 2),
(14, 'Cerradura Kwikset Electrónica', 21000.00, 22, 4),
(15, 'Cerradura ASSA ABLOY Max', 41000.00, 8, 5);
INSERT INTO Cerradura (Cod_cerradura, ce_nombre, precio, ce_stock, cod_marca) VALUES
(16, 'Cerradura ASSA ABLOY Pro', 14500.00, 20, 5);

-- Piezas
INSERT INTO Pieza (Cod_pieza, pi_nombre, Cod_proveedor, costo, pi_stock) VALUES
(1, 'Cilindro Yale 1000', 1, 3200.00, 100),
(2, 'Placa frontal Yale 1000', 2, 1500.00, 80),
(3, 'Teclado Yale Digital', 3, 5000.00, 30),
(4, 'Motor Yale Digital', 4, 4800.00, 25),
(5, 'Mecanismo Schlage Classic', 5, 3400.00, 90),
(6, 'Placa interior Schlage Classic', 6, 1600.00, 70),
(7, 'Pantalla Schlage Touchscreen', 7, 6200.00, 20),
(8, 'Sensor biométrico Schlage', 8, 6900.00, 15),
(9, 'Núcleo Tesa Alta Seguridad', 9, 3000.00, 75),
(10, 'Manija Tesa Alta Seguridad', 1, 1800.00, 85),
(11, 'Pantalla táctil Tesa Slim', 2, 5400.00, 22),
(12, 'Caja interna Tesa Slim', 3, 2600.00, 33),
(13, 'Llave Kwikset SmartKey', 4, 2000.00, 95),
(14, 'Sistema SmartKey Kwikset', 5, 3500.00, 60),
(15, 'Manija Kwikset Tradicional', 6, 1200.00, 100),
(16, 'Cilindro Kwikset Tradicional', 7, 1400.00, 80),
(17, 'Placa ASSA ABLOY Residencial', 8, 2100.00, 70),
(18, 'Cilindro ASSA ABLOY Residencial', 9, 1900.00, 90),
(19, 'Motor ASSA ABLOY Pro', 10, 7300.00, 15),
(20, 'Sistema de control ASSA ABLOY Pro', 1, 7600.00, 10),
(21, 'Teclado Yale Pro Plus', 2, 6000.00, 20),
(22, 'Sensor magnético Yale Pro Plus', 3, 4500.00, 18),
(23, 'Cilindro Tesa Compacta', 4, 1700.00, 85),
(24, 'Caja metálica Tesa Compacta', 5, 1300.00, 65),
(25, 'Cuerpo Schlage Mini', 6, 2200.00, 75),
(26, 'Resorte interno Schlage Mini', 7, 900.00, 120),
(27, 'Pantalla LED Kwikset Electrónica', 8, 5700.00, 25),
(28, 'Controlador Kwikset Electrónica', 9, 5100.00, 20),
(29, 'Cilindro ASSA ABLOY Max', 10, 3200.00, 15),
(30, 'Panel digital ASSA ABLOY Max', 1, 8800.00, 10);
INSERT INTO Pieza (Cod_pieza, pi_nombre, Cod_proveedor, costo, pi_stock) VALUES
(31, 'Pieza A Cerramix', 11, 1000.00, 50),
(32, 'Pieza B Cerramix', 11, 1200.00, 60),
(33, 'Pieza C Cerramix', 11, 800.00, 70),
(34, 'Pieza D Cerramix', 11, 950.00, 40),
(35, 'Pieza E Cerramix', 11, 1100.00, 55),
(36, 'Pieza F Cerramix', 11, 1300.00, 35),
(37, 'Pieza G Cerramix', 11, 1250.00, 45),
(38, 'Pieza H Cerramix', 11, 1050.00, 65),
(39, 'Pieza I Cerramix', 11, 980.00, 80),
(40, 'Pieza J Cerramix', 11, 1010.00, 90),
(41, 'Pieza K Cerramix', 11, 1025.00, 100),
(42, 'Pieza L Cerramix', 11, 1080.00, 30);

-- Composiciones
INSERT INTO Composicion (Cod_cerradura, Cod_pieza, cant) VALUES
(1, 1, 1),
(1, 2, 1),
(2, 3, 1),
(2, 4, 1),
(3, 5, 1),
(3, 6, 1),
(4, 7, 1),
(4, 8, 1),
(5, 9, 1),
(5, 10, 1),
(6, 11, 1),
(6, 12, 1),
(7, 13, 1),
(7, 14, 1),
(8, 15, 1),
(8, 16, 1),
(9, 17, 1),
(9, 18, 1),
(10, 19, 1),
(10, 20, 1),
(11, 21, 1),
(11, 22, 1),
(12, 23, 1),
(12, 24, 1),
(13, 25, 1),
(13, 26, 1),
(14, 27, 1),
(14, 28, 1),
(15, 29, 1),
(15, 30, 1);
INSERT INTO Composicion (Cod_cerradura, Cod_pieza, cant) VALUES
(16, 31, 1),
(16, 32, 1),
(16, 33, 1),
(16, 34, 1),
(16, 35, 1),
(16, 36, 1),
(16, 37, 1),
(16, 38, 1),
(16, 39, 1),
(16, 40, 1),
(16, 41, 1),
(16, 42, 1);
