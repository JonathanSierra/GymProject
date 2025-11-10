DROP DATABASE IF EXISTS Gimnasio;
CREATE DATABASE Gimnasio;
USE Gimnasio;

CREATE TABLE membresias(
id_membresia INT AUTO_INCREMENT PRIMARY KEY,
nombre_membresia VARCHAR(50) NOT NULL,
precio_membresia INT NOT NULL,
duracion_membresia INT NOT NULL
);

CREATE TABLE miembros(
id_miembro INT AUTO_INCREMENT PRIMARY KEY,
cedula_miembro INT NOT NULL,
nombre_miembro VARCHAR(100) NOT NULL,
telefono_miembro VARCHAR(20) NOT NULL,
apellido_miembro VARCHAR(100) NOT NULL,
fecha_nacimiento DATETIME NOT NULL,
id_membresia INT NOT NULL,
estado_miembro varchar(100),
fecha_registro DATETIME NOT NULL,
CONSTRAINT fk_membresia FOREIGN KEY (id_membresia) REFERENCES membresias(id_membresia)
);

CREATE TABLE asistencias(
id_asistencia INT PRIMARY KEY AUTO_INCREMENT,
fecha_entrada DATETIME,
fecha_salida DATETIME,
id_miembro INT NOT NULL,
CONSTRAINT fk_miembro_asistencia FOREIGN KEY (id_miembro) REFERENCES miembros(id_miembro)
);

CREATE TABLE pagos(
id_pago INT AUTO_INCREMENT PRIMARY KEY,
id_miembro INT NOT NULL,
fecha_pago DATETIME,
fecha_fin_pago DATETIME,
monto_pago INT NOT NULL,
estado_pago VARCHAR(100) NOT NULL,
CONSTRAINT fk_miembro_pago FOREIGN KEY (id_miembro) REFERENCES miembros(id_miembro)
);

CREATE TABLE perfil (
  idPerfil int PRIMARY KEY  AUTO_INCREMENT NOT NULL,
  nombrePerfil varchar(45) NOT NULL
);
INSERT INTO perfil VALUES (1,'ADMINISTRADOR'),(2,'TRABAJADOR'),(3,'CLIENTE');

CREATE TABLE usuario (
  idUsuario int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  usr varchar(45) NOT NULL,
  pass varchar(45) NOT NULL,
  idPerfil int NOT NULL,
  CONSTRAINT fk_usuario_perfil FOREIGN KEY (idPerfil) REFERENCES perfil (idPerfil)
) ;
INSERT INTO usuario VALUES (1,'PIPE','1234',1),(2,'JONATHAN','5678',1),(3,'NOSE','9012',2);

-- Trigger de usarios

DELIMITER //
CREATE TRIGGER crear_usuario_miembro
AFTER INSERT ON miembros
FOR EACH ROW
BEGIN
    DECLARE nuevo_usr VARCHAR(45);
    DECLARE nueva_pass VARCHAR(45);

    SET nuevo_usr = CONCAT('user', NEW.id_miembro);
    SET nueva_pass = SUBSTRING(MD5(RAND()), 1, 8);


    INSERT INTO usuario (usr, pass, idPerfil)
    VALUES (nuevo_usr, nueva_pass, 3);
END //



-- Procedures de registros
CREATE  PROCEDURE login(p_usuario VARCHAR(45), p_pass VARCHAR(45))
BEGIN
    DECLARE v_idUsuario INT;
    DECLARE v_usuario VARCHAR(45);
    DECLARE v_idPerfil INT;

    SELECT idUsuario, idPerfil INTO v_idUsuario, v_idPerfil
    FROM usuario 
    WHERE usr = p_usuario AND pass = p_pass
    LIMIT 1;

    IF v_idUsuario IS NOT NULL THEN
        SELECT v_idUsuario AS idUsuario, v_idPerfil AS idPerfil;
    ELSE
        SELECT 0 AS idUsuario,  0 AS idPerfil;
    END IF;
END ;//

CREATE PROCEDURE RegistrarMembresia(IN p_nombre_membresia varchar(100),IN p_precio_membresia INT,IN p_duracion_membresia INT)
BEGIN
INSERT INTO membresias(nombre_membresia,precio_membresia,duracion_membresia)
	VALUES(p_nombre_membresia,p_precio_membresia,p_duracion_membresia);

END;//

CREATE PROCEDURE RegistrarMiembro(IN p_cedula INT,IN p_nombre VARCHAR(100), IN p_apellido VARCHAR(100), IN p_telefono VARCHAR(20),
    IN p_fecha_nacimiento DATE,IN p_id_membresia INT
)
BEGIN
    DECLARE U_id_miembro INT;
    DECLARE duracion INT;
    DECLARE precio INT;
    DECLARE fecha_fin DATE;
	
    INSERT INTO miembros (cedula_miembro, nombre_miembro, apellido_miembro, telefono_miembro, fecha_nacimiento,fecha_registro ,id_membresia,estado_miembro)
    VALUES (p_cedula, p_nombre, p_apellido, p_telefono, p_fecha_nacimiento,NOW(), p_id_membresia,'ACTIVO');

    SET U_id_miembro = LAST_INSERT_ID();

    SELECT duracion_membresia, precio_membresia INTO duracion, precio FROM membresias WHERE id_membresia = p_id_membresia;

    SET fecha_fin = DATE_ADD(CURDATE(), INTERVAL duracion DAY);

    INSERT INTO pagos (id_miembro, fecha_pago, fecha_fin_pago, monto_pago, estado_pago)
    VALUES (U_id_miembro, CURDATE(), fecha_fin, precio, 'PAGADO');
END; //

CREATE PROCEDURE ActualizarEstadoMembresia()
BEGIN
    
    UPDATE pagos p
    -- lo que esta dentro de los parentecis es una sub consulta sirve para saber cual fue el ultimo pago
    JOIN (SELECT id_miembro, MAX(fecha_fin_pago) AS ultima_fecha FROM pagos GROUP BY id_miembro) 
    ult ON p.id_miembro = ult.id_miembro AND p.fecha_fin_pago = ult.ultima_fecha 
    -- este case funciona como un if si la ultima fecha es menor que la actual el estado cambia a pendiente
    -- y ya hace eso 
    SET p.estado_pago = CASE 
    WHEN ult.ultima_fecha > CURDATE() THEN 'PAGADO'
        ELSE 'PENDIENTE'
    END;
	 
	-- lo mismo que arriba pero solo cambia el estado de la membresia
    UPDATE miembros m
    JOIN ( SELECT id_miembro, MAX(fecha_fin_pago) AS ultima_fecha FROM pagos GROUP BY id_miembro) ult ON m.id_miembro = ult.id_miembro
    SET m.estado_miembro = CASE
        WHEN ult.ultima_fecha > CURDATE() THEN 'ACTIVO'
        ELSE 'INACTIVO'
    END;
END; //



CREATE PROCEDURE RegistrarEntrada(IN p_cedula_miembro INT)
BEGIN
    DECLARE u_asistencia_activa INT;
    DECLARE u_estado_miembro VARCHAR(100);
    DECLARE u_estado_pago VARCHAR(100);
    DECLARE u_id_miembro int;
    
    SELECT id_miembro,estado_miembro INTO u_id_miembro, u_estado_miembro FROM miembros
    WHERE cedula_miembro = p_cedula_miembro;
	
    
    SELECT estado_pago INTO u_estado_pago FROM pagos
    WHERE u_id_miembro = id_miembro ORDER BY fecha_pago DESC
	LIMIT 1;
    
	
    SELECT COUNT(*) INTO u_asistencia_activa FROM asistencias
    WHERE u_id_miembro = id_miembro AND fecha_salida IS NULL;
    IF u_estado_pago = 'PENDIENTE' OR u_estado_miembro = 'INACTIVO' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El miembro tiene el pago pendiente o está inactivo';
    ELSE
        IF u_asistencia_activa > 0 THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El miembro ya está adentro';
        ELSE
            INSERT INTO asistencias (id_miembro, fecha_entrada, fecha_salida)
            VALUES (u_id_miembro, NOW(),NULL);
        END IF;
    END IF;
END; //


CREATE PROCEDURE RegistrarSalida(IN p_cedula_miembro INT)
BEGIN
    DECLARE u_asistencia_activa INT DEFAULT 0;
    DECLARE u_id_asistencia INT DEFAULT 0;
    DECLARE u_id_miembro int;
    select id_miembro into u_id_miembro from miembros where cedula_miembro=p_cedula_miembro;

    SELECT COUNT(*) INTO u_asistencia_activa
    FROM asistencias
    WHERE u_id_miembro = id_miembro AND fecha_salida IS NULL;
	
    IF u_asistencia_activa = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El miembro no ha entrado';
    ELSE
        SELECT id_asistencia INTO u_id_asistencia FROM asistencias WHERE u_id_miembro = id_miembro AND fecha_salida IS NULL
        ORDER BY fecha_entrada DESC limit 1;
        
        UPDATE asistencias
        SET fecha_salida = NOW()
        WHERE id_asistencia = u_id_asistencia;
    END IF;
END; //
CREATE PROCEDURE pagar(IN p_cedula_miembro int)
BEGIN
	DECLARE duracion INT;
    DECLARE precio INT;
    DECLARE fecha_fin DATE;
    DECLARE estado_pagos VARCHAR(100);
    DECLARE cedula INT;
    DECLARE id INT;
	select id_miembro into id from miembros where cedula_miembro=p_cedula_miembro;
    
    SELECT estado_pago INTO estado_pagos FROM pagos where id_miembro=id;
	SELECT cedula_miembro,id_miembro INTO cedula,id from miembros where id_miembro=id;
    IF cedula=p_cedula_miembro THEN
		IF estado_pagos='PAGADO' THEN
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'A el miembro todavia no se le ha vencido la membresia';
		else
			SELECT duracion_membresia, precio_membresia INTO duracion, precio FROM membresias WHERE id_membresia = id;
			SET fecha_fin = DATE_ADD(CURDATE(), INTERVAL duracion DAY);
			INSERT INTO pagos (id_miembro, fecha_pago, fecha_fin_pago, monto_pago, estado_pago)
			VALUES (id, CURDATE(), fecha_fin, precio, 'PAGADO');
			UPDATE miembros	SET estado_miembro = 'ACTIVO' WHERE id_miembro = id;
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'Pago con exito';
            UPDATE pagos SET estado_pago = 'PAGADO' WHERE id_miembro = id
			ORDER BY fecha_fin_pago DESC LIMIT 1;
	
		END IF;
	else
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Cedula no encontrada';
		
	END IF;
    
    
END;//


-- mostrar informacion
CREATE PROCEDURE MostrarMembresias()
BEGIN
    SELECT * FROM membresias;
END;//


CREATE PROCEDURE MostrarAsistencias()
BEGIN
    SELECT  m.cedula_miembro AS cedula, m.nombre_miembro AS nombre,a.fecha_entrada AS entrada, a.fecha_salida AS salida
    FROM asistencias a JOIN miembros m ON a.id_miembro = m.id_miembro;
END; //


CREATE PROCEDURE MostrarMiembros()
BEGIN
    SELECT cedula_miembro AS cedula,nombre_miembro AS nombre,apellido_miembro AS apellido,
    telefono_miembro AS telefono, estado_miembro as Estado FROM miembros;
END; //

-- fin de mostrar informacion

-- filtros
create procedure Estados(IN p_estado_miembro VARCHAR(100))
begin
	if p_estado_miembro='ACTIVO' then
		select cedula_miembro as Cedula,nombre_miembro as Nombre,apellido_miembro as Apellido, estado_miembro as Estado from miembros where estado_miembro='ACTIVO';
	else	
		select cedula_miembro as Cedula,nombre_miembro as Nombre,apellido_miembro as Apellido, estado_miembro as Estado from miembros where estado_miembro='INACTIVO';
	end if;
end;//

CREATE PROCEDURE PagosPendientes()
BEGIN
    SELECT m.cedula_miembro as cedula,p.fecha_pago as fecha, p.estado_pago as estado FROM pagos p 
    inner join miembros m on p.id_miembro=m.id_miembro WHERE estado_pago = 'PENDIENTE';
END;//

CREATE PROCEDURE PagosPagados()
BEGIN
    SELECT m.cedula_miembro as cedula,p.fecha_pago as fecha, p.estado_pago as estado FROM pagos p 
    inner join miembros m on p.id_miembro=m.id_miembro WHERE estado_pago = 'PAGADO';
END;//

CREATE PROCEDURE ReporteAsistencias(IN p_Asistencia varchar(100))
BEGIN
	IF p_asistencia='DIAS' THEN
		SELECT DATE(fecha_entrada) AS dia, COUNT(*) AS total_asistencias FROM asistencias
		GROUP BY dia ORDER BY total_asistencias DESC;
	ELSE
		SELECT HOUR(fecha_entrada) AS hora, COUNT(*) AS total_asistencias FROM asistencias
		GROUP BY hora ORDER BY total_asistencias DESC;
	END IF;

END;//

CREATE PROCEDURE mostrarMembresiaTipo(IN p_id_membresia INT)
BEGIN
select m.cedula_miembro as Cedula, m.nombre_miembro as Nombre,me.nombre_membresia as Membresia from membresias me 
inner join miembros m on m.id_membresia=me.id_membresia where p_id_membresia=me.id_membresia;

END;//

CREATE PROCEDURE buscar_pagos_por_fecha(IN fecha_inicio DATE, IN fecha_fin DATE)
BEGIN
    SELECT  m.cedula_miembro AS Cedula,me.nombre_membresia AS Nombre_membresia,
           p.fecha_pago AS Fecha, p.monto_pago AS Pago 
    FROM pagos p
    INNER JOIN miembros m ON p.id_miembro = m.id_miembro
    INNER JOIN membresias me ON m.id_membresia = me.id_membresia
    WHERE DATE(p.fecha_pago) BETWEEN fecha_inicio AND fecha_fin AND p.estado_pago = 'PAGADO'
    ORDER BY p.fecha_pago ASC;
END; //
CREATE PROCEDURE mostrar_pagos_pagados()
BEGIN
    SELECT m.cedula_miembro AS Cedula,p.fecha_pago AS Fecha_Pago FROM pagos p
    INNER JOIN miembros m ON p.id_miembro = m.id_miembro
    WHERE p.estado_pago = 'PAGADO';
END;//
-- fin de los filtros

-- funciones:


CREATE FUNCTION diasRestantesMembresia(p_id_miembro INT)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE diasRestantes INT;
    
    SELECT DATEDIFF((SELECT p.fecha_fin_pago FROM pagos p WHERE p.id_miembro = p_id_miembro
	ORDER BY p.fecha_fin_pago DESC LIMIT 1),CURDATE())INTO diasRestantes; 
	RETURN diasRestantes;
END;
//

CREATE PROCEDURE verDiasRestantes(IN p_cedula VARCHAR(20))
BEGIN
    DECLARE v_id INT;
    DECLARE v_dias_restantes INT;
    
    SELECT id_miembro INTO v_id FROM miembros WHERE cedula_miembro = p_cedula LIMIT 1;
	SET v_dias_restantes = diasRestantesMembresia(v_id);
	SELECT v_dias_restantes AS Dias_Restantes;

END;
//

CREATE PROCEDURE verMiembro()
BEGIN
	SELECT * FROM miembros;
END;//



INSERT INTO membresias (nombre_membresia, precio_membresia, duracion_membresia)
VALUES ('Mensual', 80000, 30),('Trimestral', 120000, 60),('Anual', 160000, 365);//

-- inserts de miembros:
insert into miembros (cedula_miembro,nombre_miembro,telefono_miembro,apellido_miembro,fecha_nacimiento,id_membresia,estado_miembro,fecha_registro) 
values(12345,'Andres',12345,'Ruiz','2006-12-25',2,'ACTIVO','2025-10-25'),
(1001, 'Carlos', '3001112233', 'Gómez', '1990-04-12', 1, 'ACTIVO', '2025-10-04'),
(1002, 'Laura', '3012223344', 'Martínez', '1995-08-23', 2, 'ACTIVO', '2025-10-21'),
(1003, 'Andrés', '3023334455', 'Fernández', '1989-12-05', 1, 'ACTIVO','2025-10-30'),
(1004, 'Valentina', '3034445566', 'López', '1998-02-17', 3, 'ACTIVO', '2025-10-20');//

-- inserts de pagos:
INSERT INTO pagos(id_miembro, fecha_pago, fecha_fin_pago, monto_pago, estado_pago) VALUES
(1, '2025-09-28', '2025-10-30', 80000, 'PAGADO'),
(2, '2025-10-05', '2025-11-05', 120000, 'PAGADO'),
(3, '2025-08-20', '2025-09-20', 80000, 'PAGADO'),
(4, '2025-10-25', '2025-11-25', 100000, 'PAGADO');//

-- inserts de asistencias:
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
('2025-11-06 07:25:00', '2025-11-06 09:35:00', 4);//


