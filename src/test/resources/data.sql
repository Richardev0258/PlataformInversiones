INSERT INTO cliente (id, nombre, saldo_disponible, email, telefono, preferencia_notificacion)
VALUES (1, 'Ricardo Marín', 500000, 'ricardomarin0258@gmail.com', '3214090619', 'email');

INSERT INTO fondo (id, nombre, categoria, monto_minimo)
VALUES (1, 'FPV_BTG_PACTUAL_RECAUDADORA', 'FPV', 75000),
       (2, 'FPV_BTG_PACTUAL_ECOPETROL', 'FPV', 125000),
       (3, 'DEUDAPRIVADA', 'FIC', 50000),
       (4, 'FDO-ACCIONES', 'FIC', 250000),
       (5, 'FPV_BTG_PACTUAL_DINAMICA', 'FPV', 100000);