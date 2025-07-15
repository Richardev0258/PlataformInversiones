SELECT DISTINCT c.nombre
FROM clientes c
JOIN inscripciones i ON c.cliente_id = i.cliente_id
WHERE NOT EXISTS (
    SELECT 1
    FROM sucursal_productos sp
    WHERE sp.producto_id = i.producto_id
      AND sp.sucursal_id NOT IN (
          SELECT v.sucursal_id
          FROM visitas v
          WHERE v.cliente_id = c.cliente_id
      )
);