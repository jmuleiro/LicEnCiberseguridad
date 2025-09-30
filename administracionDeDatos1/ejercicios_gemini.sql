USE segparcial;

-- 1. Listar todas las piezas (`pi_nombre`, `costo`) junto con la razón social (`Razon_social`) y el email (`e_mail`) del proveedor que las suministra.
SELECT PI.pi_nombre, PI.costo, PR.Razon_social, PR.e_mail
FROM Pieza AS PI
INNER JOIN Proveedor AS PR
  ON PI.Cod_proveedor = PR.Cod_proveedor;

-- 2. Para cada marca de cerradura, calcular el precio promedio y la cantidad total de cerraduras en stock. Mostrar solo aquellas marcas cuyo precio promedio de cerraduras sea superior a $18,000.
SELECT MA.nombre_marca, AVG(CE.precio) AS PRECIO_PROMEDIO, SUM(CE.ce_stock) AS STOCK_TOTAL
FROM CERRADURA AS CE
INNER JOIN MARCA AS MA
ON CE.cod_marca = MA.cod_marca
GROUP BY MA.nombre_marca
HAVING AVG(CE.PRECIO) > 18000;

-- 3. Primero, crea una nueva tabla llamada `Proveedores_VIP` con las columnas `Cod_proveedor` (INT) y `Razon_social` (VARCHAR). 
-- Luego, usando una sola consulta `INSERT`, puebla esta tabla con los datos de los proveedores que suministran al menos una pieza con un costo superior a $7,000.
INSERT INTO Proveedores_VIP
SELECT PR.Cod_proveedor, PR.Razon_social
FROM Proveedor AS PR
WHERE EXISTS (
  SELECT 1
  FROM PIEZA AS PI
  WHERE PR.Cod_proveedor = PI.Cod_proveedor
  AND PI.costo > 7000
);

-- 4. Aplicar un descuento del 15% (`precio = precio * 0.85`) a todas las cerraduras de la marca 'Tesa' que estén compuestas por 2 o más piezas diferentes.
UPDATE Cerradura
SET precio = precio * 0.85
WHERE cod_marca IN (
  SELECT MA.cod_marca
  FROM Marca AS MA
  WHERE MA.nombre_marca = 'Tesa'
) AND 1 < (
  SELECT COUNT(DISTINCT PI.Cod_pieza)
  FROM Composicion AS CO
  INNER JOIN Pieza AS PI
    ON CO.Cod_pieza = PI.Cod_pieza
  WHERE CO.Cod_cerradura = Cerradura.Cod_cerradura
);


-- 5. Obtener todos los datos de los proveedores que **no** venden ninguna pieza para las cerraduras de la marca 'Schlage'.
SELECT PR.*
FROM Proveedor AS PR
WHERE NOT EXISTS (
  SELECT 1
  FROM Pieza AS PI
  INNER JOIN Composicion AS CO ON PI.Cod_pieza = CO.Cod_pieza
  INNER JOIN Cerradura AS CE ON CO.Cod_cerradura = CE.Cod_cerradura
  INNER JOIN MARCA AS MA ON CE.cod_marca = MA.cod_marca
  WHERE PI.Cod_proveedor = PR.Cod_proveedor
  AND MA.nombre_marca = 'Schlage'
);

-- 6. Listar el nombre y el precio de las cerraduras cuyo precio es inferior a $15,000 pero tienen un stock superior a 40 unidades.
SELECT CE.ce_nombre, CE.precio
FROM Cerradura AS CE
WHERE CE.precio < 15000
AND CE.ce_stock > 40;

-- 7. Calcular el costo total de fabricación de cada cerradura (sumando el costo de sus piezas multiplicado por la cantidad de cada una). 
-- Mostrar el nombre de la cerradura y su costo total de fabricación, ordenando los resultados de la más cara a la más barata de fabricar.
SELECT CE.ce_nombre, SUM(PI.costo * CO.cant) AS COSTO_FABRICACION
FROM Cerradura AS CE
INNER JOIN Composicion AS CO ON CE.Cod_cerradura = CO.Cod_cerradura
INNER JOIN Pieza AS PI ON CO.Cod_pieza = PI.Cod_pieza
GROUP BY CE.ce_nombre
ORDER BY COSTO_FABRICACION DESC;

select cod_cerradura, ce_nombre from Cerradura where ce_nombre = 'Cerradura ASSA ABLOY Pro';

-- 8. Incrementar en 50 unidades el stock (`pi_stock`) de todas las piezas que son parte de la cerradura "Cerradura Kwikset SmartKey".
UPDATE Pieza
SET pi_stock = pi_stock + 50
WHERE Cod_pieza IN (
  SELECT CO.Cod_pieza
  FROM Composicion AS CO
  INNER JOIN Cerradura AS CE
  ON CO.Cod_cerradura = CE.Cod_cerradura
  WHERE CO.Cod_pieza = Pieza.Cod_pieza
  AND CE.ce_nombre = 'Cerradura Kwikset SmartKey'
);

-- 9. Encontrar el nombre de las cerraduras que contienen **todas** las piezas suministradas por el proveedor 'Metalúrgica Gómez S.A.' (código 1).
SELECT CE.ce_nombre
FROM Cerradura AS CE
WHERE CE.Cod_cerradura NOT IN (
  SELECT DISTINCT CO.Cod_cerradura
  FROM Composicion AS CO
  INNER JOIN Pieza AS PI
  ON CO.Cod_pieza = PI.Cod_pieza
  INNER JOIN Proveedor AS PR
  ON PI.Cod_proveedor = PR.Cod_proveedor
  WHERE PR.Razon_social != 'Metalúrgica Gómez S.A.'
);

-- 10. Generar un reporte que muestre, por cada marca, la cantidad total de modelos de cerraduras que tiene y, en otra columna, 
-- cuántos de esos modelos son considerados "digitales" (su nombre contiene 'Digital', 'Touchscreen' o 'Electrónica').
SELECT MA.nombre_marca, 
  SUM(CE.Cod_cerradura) AS TOTAL_CERRADURAS, 
  COUNT(DISTINCT CASE WHEN CE.ce_nombre LIKE '%Digital%' OR
    CE.ce_nombre LIKE '%Touchscreen%' OR
    CE.ce_nombre LIKE '%Electrónica%' THEN CE.Cod_cerradura END) AS MODELOS_DIGITALES
FROM Cerradura AS CE
INNER JOIN Marca AS MA
ON CE.cod_marca = MA.cod_marca
GROUP BY MA.nombre_marca
ORDER BY MA.nombre_marca;
