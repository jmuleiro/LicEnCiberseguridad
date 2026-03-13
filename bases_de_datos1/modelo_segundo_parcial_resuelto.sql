USE segparcial;

--* Ejercicios de Álgebra relacional
-- a) Obtener las cerraduras (códigos de cerraduras, nombres y precios) que NO contienen la pieza cuyo
-- nombre es “Pestillo p32”.
SELECT CE.*
FROM Cerradura AS CE
WHERE CE.Cod_cerradura NOT IN (
  SELECT CO.Cod_cerradura
  FROM Composicion AS CO
  INNER JOIN Pieza AS PI
  ON CO.Cod_pieza = PI.Cod_pieza
  WHERE PI.pi_nombre = 'Pestillo p32'
);

-- b) Obtener los códigos y nombres de las cerraduras que contienen alguna pieza comprada al proveedor
-- con código ita612.
SELECT CE.Cod_cerradura, CE.ce_nombre
FROM Cerradura AS CE
WHERE CE.Cod_cerradura IN (
  SELECT CO.Cod_cerradura
  FROM Composicion AS CO
  INNER JOIN Pieza AS PI
  ON CO.Cod_pieza = PI.Cod_pieza
  WHERE PI.Cod_proveedor = 612
);

-- c) Obtener los datos de los proveedores que no venden piezas de la cerradura con nombre Auto_Rt15
SELECT PR.Cod_proveedor, PR.Razon_social, PR.e_mail, PR.Tel
FROM Proveedor AS PR
WHERE NOT EXISTS (
  SELECT 1
  FROM Pieza AS PI
  INNER JOIN Composicion AS CO
  ON PI.Cod_pieza = CO.Cod_pieza
  INNER JOIN Cerradura AS CE
  ON CO.Cod_cerradura = CE.Cod_cerradura
  WHERE PI.Cod_proveedor = PR.Cod_proveedor
  AND CE.ce_nombre = 'Auto_Rt15'
);

-- Listar las cerraduras (Cod_cerradura, ce_nombre, precio, ce_stock) que contienen más de 10 piezas
-- diferentes del proveedor con razón social Cerramix ,y también el valor promedio de las piezas de
-- cada una de esas cerraduras
SELECT CE.Cod_cerradura, CE.ce_nombre, CE.precio, CE.ce_stock, AVG(PI.costo) AS PROM_PIEZA
FROM Cerradura AS CE
INNER JOIN Composicion AS CO
  ON CE.Cod_cerradura = CO.Cod_cerradura
INNER JOIN PIEZA AS PI
  ON CO.Cod_pieza = PI.Cod_pieza
WHERE EXISTS (
  SELECT 1
  FROM PROVEEDOR AS PR
  WHERE PR.Cod_proveedor = PI.Cod_proveedor
  AND PR.Razon_social = 'Cerramix'
)
GROUP BY CE.Cod_cerradura, CE.ce_nombre, CE.precio, CE.ce_stock
HAVING COUNT(DISTINCT PI.Cod_pieza) > 10;

-- Incrementar un 30 % el precio de las cerraduras de la marca Acitra (nombre de la marca) que
-- contienen la pieza Pestillo Muelle (nombre de la pieza)
UPDATE Cerradura
SET precio = precio * 1.3
WHERE EXISTS (
  SELECT 1
  FROM Composicion AS CO
  INNER JOIN Pieza AS PI
    ON CO.Cod_pieza = PI.Cod_pieza
  WHERE CO.Cod_cerradura = Cerradura.Cod_cerradura
  AND PI.pi_nombre = 'Pestillo Muelle'
)
AND cod_marca IN (
  SELECT MA.cod_marca
  FROM Marca AS MA
  WHERE MA.nombre_marca = 'Acitra'
);

-- Listar las cerraduras ( Cod_cerradura, ce_nombre, precio, ce_stock) que NO contienen piezas del
-- proveedor 432 (código).
SELECT CE.Cod_cerradura, CE.ce_nombre, CE.precio, CE.ce_stock
FROM Cerradura AS CE
WHERE NOT EXISTS (
  SELECT 1
  FROM Composicion AS CO
  INNER JOIN Pieza AS PI
    ON CO.Cod_pieza = PI.Cod_pieza
  WHERE CO.Cod_cerradura = CE.Cod_cerradura
  AND PI.Cod_proveedor = 432
);
