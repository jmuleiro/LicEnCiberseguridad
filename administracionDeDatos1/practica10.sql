/*
CLIENTE (Nrocli, Nyape, Domicilio, Localidad, Saldocli)
FACTURA (Nrofactura, Cliente, Fecha, Total)
DETALLE (Nrofactura, Renglón, Artículo, Cantidad, Preciouni)
ARTICULO ( Nroartic, Descripción, Rubro, Stock, Pto_reposicion, precio)
RUBRO(Cod_rubro, Descripcion)
*/
-- Mostrar el mejor precio de cada rubro, la cantidad de artículos del rubro, el código de rubro y la descripción del rubro.
SELECT R.COD_RUBRO, R.DESCRIPCION, MIN(A.PRECIO) AS MEJOR_PRECIO, COUNT(A.NROARTIC) AS CANT_ARTICULOS
FROM RUBRO AS R 
INNER JOIN ARTICULO AS A
  ON R.COD_RUBRO = A.RUBRO
GROUP BY R.COD_RUBRO, R.DESCRIPCION;

-- Mostrar los datos de los rubros cuyo precio promedio de sus artículos supere los $800 y el rubro tenga más de 2 artículos.
SELECT R.COD_RUBRO, R.DESCRIPCION, AVG(A.PRECIO), COUNT(A.NROARTIC)
FROM RUBRO AS R
INNER JOIN ARTICULO AS A
  ON R.COD_RUBRO = A.RUBRO
GROUP BY R.COD_RUBRO, R.DESCRIPCION
HAVING AVG(A.PRECIO) > 800
AND COUNT(A.NROARTIC) > 2;

-- Mostrar la cantidad de facturas de cada uno de los clientes que hayan comprado los artículos 4000 o 4010 y los datos de dichos clientes.
SELECT COUNT(distinct F.NROFACTURA) AS CANT_FACTURAS, C.NROCLI, C.NYAPE, C.DOMICILIO, C.LOCALIDAD, C.SALDOCLI
FROM FACTURA AS F
INNER JOIN DETALLE AS D
  ON F.NROFACTURA = D.NROFACTURA
INNER JOIN CLIENTE AS C
  ON F.CLIENTE = C.NROCLI
WHERE D.ARTICULO IN (4000, 4010)
GROUP BY C.NROCLI, C.NYAPE, C.DOMICILIO, C.LOCALIDAD, C.SALDOCLI
ORDER BY NROCLI ASC;

-- Mostrar los datos de los clientes que compraron artículos del rubro 1 y que tienen más de 2 facturas, mostrar también dicha cantidad.
SELECT C.NYAPE, C.DOMICILIO, C.LOCALIDAD, C.SALDOCLI, COUNT(DISTINCT F.NROFACTURA) AS CANTIDAD_FACTURAS
FROM CLIENTE AS C
  INNER JOIN FACTURA AS F
  ON F.CLIENTE = C.NROCLI
  INNER JOIN DETALLE AS D
  ON D.NROFACTURA = F.NROFACTURA
  INNER JOIN ARTICULO AS A
  ON A.NROARTIC = D.ARTICULO
WHERE A.RUBRO = 1
GROUP BY C.NYAPE, C.DOMICILIO, C.LOCALIDAD, C.SALDOCLI
HAVING COUNT(DISTINCT F.NROFACTURA) > 2;
