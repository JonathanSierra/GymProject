INSERT INTO perfil VALUES (1,'ADMINISTRADOR'),(2,'TRABAJADOR'),(3,'CLIENTE');

INSERT INTO usuario VALUES (1,'PIPE','1234',1),(2,'JONATHAN','5678',1),(3,'elias','9012',2);
INSERT INTO membresias (nombre_membresia, precio_membresia, duracion_membresia)
VALUES ('Mensual', 80000, 30),('Trimestral', 120000, 60),('Anual', 160000, 365);

insert into miembros (cedula_miembro,nombre_miembro,telefono_miembro,apellido_miembro,fecha_nacimiento,id_membresia,estado_miembro,fecha_registro) 
values(12345,'Andres',12345,'Ruiz','2006-12-25',2,'ACTIVO','2025-10-25'),
(1001, 'Carlos', '3001112233', 'Gómez', '1990-04-12', 1, 'ACTIVO', '2025-10-04'),
(1002, 'Laura', '3012223344', 'Martínez', '1995-08-23', 2, 'ACTIVO', '2025-10-21'),
(1003, 'Andrés', '3023334455', 'Fernández', '1989-12-05', 1, 'ACTIVO','2025-10-30'),
(1004, 'Valentina', '3034445566', 'López', '1998-02-17', 3, 'ACTIVO', '2025-10-20');


INSERT INTO pagos(id_miembro, fecha_pago, fecha_fin_pago, monto_pago, estado_pago) VALUES
(1, '2025-09-28', NOW(), 80000, 'PAGADO'),
(2, '2025-10-05', '2025-10-30', 120000, 'PAGADO'),
(3, '2025-08-20', '2025-10-09', 80000, 'PAGADO'),
(4, '2025-10-25', '2025-11-25', 100000, 'PAGADO');

INSERT INTO asistencias(fecha_entrada, fecha_salida, id_miembro) VALUES
('2025-11-01 07:00:00', '2025-11-01 09:00:00', 1),
('2025-11-03 07:15:00', '2025-11-03 09:10:00', 1),
('2025-11-05 07:20:00', '2025-11-05 09:30:00', 1),
('2025-11-02 08:00:00', '2025-11-02 10:00:00', 2),
('2025-11-04 08:10:00', '2025-11-04 10:05:00', 2),
('2025-11-06 08:30:00', '2025-11-06 10:15:00', 2),
('2025-11-03 09:00:00', '2025-11-03 11:00:00', 3),
('2025-11-05 09:10:00', '2025-11-05 11:05:00', 3),
('2025-11-04 07:30:00', '2025-11-04 09:45:00', 4),
('2025-11-06 07:25:00', '2025-11-06 09:35:00', 4);