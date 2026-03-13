/*
CLIENTE (Nrocli, Nyape, Domicilio, Localidad, Saldocli)
FACTURA (Nrofactura, Cliente, Fecha, Total)
DETALLE (Nrofactura, Renglón, Artículo, Cantidad, Preciouni)
ARTICULO (Nroartic, Descripción, Rubro, Stock, Pto_reposicion, precio)
RUBRO (Cod_rubro, Descripcion) 

1. Listar todos los datos de los clientes que NUNCA compraron durante el año 2022. 
Ordenar el resultado alfabéticamente por nombre y apellido.
*/
SELECT * 
FROM CLIENTE AS C
WHERE NOT EXISTS (
    SELECT 1
    FROM FACTURA AS F
    WHERE F.CLIENTE = C.NROCLI
    AND YEAR(F.FECHA) = 2022
)
ORDER BY C.NYAPE;

/*
2. Listar todos los datos de los clientes que nunca compraron el artículo con código 4030.
*/
SELECT *
FROM CLIENTE AS C
WHERE NOT EXISTS (
  SELECT 1
  FROM FACTURA AS F INNER JOIN DETALLE AS D
    ON D.NROFACTURA = F.NROFACTURA
  WHERE D.ARTICULO = 4030
  AND F.CLIENTE = C.NROCLI
);

/*
3. Listar los datos de las facturas en que se vendieron 
artículos del rubro electrodomésticos (descripción).
*/
SELECT F.*
FROM FACTURA AS F 
  INNER JOIN DETALLE AS D
    ON F.NROFACTURA = D.NROFACTURA
  INNER JOIN ARTICULO AS A
    ON D.ARTICULO = A.NROARTIC
  INNER JOIN RUBRO AS R
    ON A.RUBRO = R.COD_RUBRO
WHERE R.DESCRIPCION = "Electrodomésticos";

/*
Realizar un listado completo de facturación, 
que incluya todas las columnas de las tablas Cliente y Factura. 
Las filas deben corresponder a las facturas de clientes catalogados 
(estén en la tabla de clientes), las facturas a consumidores finales 
(en la columna cliente tenga null) 
y los clientes sin facturas agregando null en los campos correspondientes a las facturas.
*/
SELECT F.*, C.*
FROM FACTURA AS F 
  FULL JOIN CLIENTE AS C
    ON F.CLIENTE = C.NROCLI