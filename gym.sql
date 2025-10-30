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


-- Procedures de registros
DELIMITER //

CREATE PROCEDURE RegistrarMembresia(IN p_nombre_membresia varchar(100),IN p_precio_membresia INT,IN p_duracion_membresia INT)
BEGIN
INSERT INTO membresia(nombre_membresia,precio_membresia,duracion_membresia)
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
CREATE PROCEDURE ActualizarEstadoMembresia(IN p_id_miembro INT)
BEGIN
    DECLARE fechaFin DATE;
    DECLARE nuevoEstado VARCHAR(20);
    DECLARE nuevoEstado2 varchar(20);

    SELECT fecha_fin_pago INTO fechaFin FROM pagos WHERE id_miembro = p_id_miembro
    ORDER BY fecha_fin_pago DESC LIMIT 1;

  
	IF fechaFin > CURDATE() THEN
        SET nuevoEstado = 'PAGADO';
        SET nuevoEstado2='ACTIVO';
    ELSE
        SET nuevoEstado = 'PENDIENTE';
        SET nuevoEstado2='INACTIVO';
    END IF;

    UPDATE pagos
    SET estado_pago = nuevoEstado
    WHERE id_miembro = p_id_miembro
    ORDER BY fecha_fin_pago DESC
	LIMIT 1;
     UPDATE miembros SET estado_miembro = nuevoEstado2 WHERE id_miembro = p_id_miembro;
END; //



CREATE PROCEDURE RegistrarEntrada(IN p_cedula_miembro INT)
BEGIN
    DECLARE u_asistencia_activa INT;
    DECLARE u_estado_miembro VARCHAR(100);
    DECLARE u_estado_pago VARCHAR(100);
    DECLARE u_id_miembro int;
    
    SELECT id_miembro,estado_miembro INTO u_id_miembro, u_estado_miembro FROM miembros
    WHERE cedula_miembro = p_cedula_miembro;
    call ActualizarEstadoMembresia(u_id_miembro);
    
    SELECT estado_pago INTO u_estado_pago FROM pagos
    WHERE u_id_miembro = id_miembro ORDER BY fecha_pago DESC
	LIMIT 1;
    
    call ActualizarEstadoMembresia(u_id_miembro);
	
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
    call ActualizarEstadoMembresia(id);
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

-- fin de los filtros




INSERT INTO membresias (nombre_membresia, precio_membresia, duracion_membresia)
VALUES ('Mensual', 80000, 30),('Trimestral', 120000, 60),('Anual', 160000, 365);//

-- inserts de miembros:
insert into miembros (cedula_miembro,nombre_miembro,telefono_miembro,apellido_miembro,fecha_nacimiento,id_membresia,estado_miembro,fecha_registro) 
values(12345,'a',12345,'r','2006-12-25',2,'ACTIVO','2025-10-25');//

-- inserts de pagos:
insert into pagos(id_miembro,fecha_pago,fecha_fin_pago,monto_pago,estado_pago)values(1,'2025-9-28','2025-10-30',80000,'PAGADO');//

CALL RegistrarMiembro(128456789,'Adriam','Thomas','3105558899','2006-12-25',1);//
CALL RegistrarMiembro(123456789,'Andrés','Ruiz','3102123145','2009-11-12',3);//
CALL RegistrarMiembro(201937801,'Yerson','Hurtado','3564641341','2011-05-12',2);//
CALL RegistrarMiembro(310974019,'Esteban','Oviedo','3145239876','2015-06-26',1);//
CALL RegistrarMiembro(198374762,'Harinton','Alvear','3102123145','1992-07-23',3);//










