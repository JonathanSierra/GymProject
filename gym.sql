DROP DATABASE IF EXISTS Gym;
CREATE DATABASE Gym;
USE Gym;

CREATE TABLE mensualidades(
id_mensualidad INT AUTO_INCREMENT PRIMARY KEY,
mensualidad_name VARCHAR(50) NOT NULL,
mensualidad_precio INT NOT NULL,
mensualidad_duracion int not null
);

CREATE TABLE Miembros(
id_miembro INT  AUTO_INCREMENT PRIMARY KEY,
miembro_cedula int not null,
miembro_name VARCHAR(100) NOT NULL,
miembro_telefono varchar(20) not null,
miembro_lastName VARCHAR(100) NOT NULL,
miembro_birthDate DATE NOT NULL,
id_mensualidad INT not null,
miembro_estado ENUM('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
fechaRegistro timestamp DEFAULT (now()),
CONSTRAINT miembro_mensualidad FOREIGN KEY (id_mensualidad) REFERENCES mensualidades(id_mensualidad)
);

create table asistencias(id_asistencias int primary key auto_increment,fechaEntrada 
timestamp DEFAULT (now()),fechaSalida TIMESTAMP null,id_miembro int not null,
CONSTRAINT miembro_asistencia FOREIGN KEY (id_miembro) REFERENCES miembros(id_miembro) );


CREATE TABLE pagos(
id_pago INT AUTO_INCREMENT PRIMARY KEY,
id_miembro INT NOT NULL,
pago_date TIMESTAMP DEFAULT (NOW()),
pago_fecha_fin DATE,
pago_amount INT NOT NULL,
pago_state ENUM("PAGADO","PENDIENTE") DEFAULT "PENDIENTE",
CONSTRAINT miembro_pago FOREIGN KEY (id_miembro) REFERENCES miembros(id_miembro)
);


/* PROCEDURES */

DELIMITER //

CREATE PROCEDURE RegistrarMiembro(IN p_cedula INT,IN p_name VARCHAR(100),
IN p_lastName VARCHAR(100),IN p_telefono VARCHAR(20),IN p_birthDate DATE,IN p_id_mensualidad INT)
BEGIN
    DECLARE nuevo_id INT;
    DECLARE duracion INT;
    DECLARE valor INT;
    DECLARE fecha_fin DATE;

    INSERT INTO miembros (miembro_cedula,miembro_name,miembro_lastName,miembro_telefono,miembro_birthDate,id_mensualidad)
    VALUES (p_cedula, p_name, p_lastName, p_telefono, p_birthDate, p_id_mensualidad);
	-- obtiene el id que se registro 
    SET nuevo_id = LAST_INSERT_ID();

    SELECT mensualidad_duracion, mensualidad_precio
    INTO duracion, valor
    FROM mensualidades
    WHERE id_mensualidad = p_id_mensualidad;
	-- suma la duracion de la mensualidad con la fecha 
    set fecha_fin = DATE_ADD(CURDATE(), INTERVAL duracion DAY);
	-- registra lo de pago 
    INSERT INTO pagos (id_miembro, pago_date, pago_fecha_fin, pago_amount, pago_state)
    VALUES (nuevo_id, CURDATE(), fecha_fin, valor, 'PAGADO');
END //

create procedure comboMembresias()
begin
select * from mensualidades;
end;//

CREATE PROCEDURE RegistrarEntrada(IN p_id_miembro INT)
BEGIN
    declare asistencia_activa INT;
    declare miembroEstado varchar(100);
    declare pagoState varchar(100);
    
    select miembro_estado into miembroEstado from Miembros
    where  id_miembro=p_id_miembro;
    
    select pago_state into pagoState from pagos
    where  id_miembro=p_id_miembro;
    
    SELECT COUNT(*) INTO asistencia_activa FROM asistencias
    WHERE id_miembro = p_id_miembro
      AND fechaSalida IS NULL;
      IF pagoState='PENDIENTE' or miembroEstado='INACTIVO' then
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El miembro tiene el pago pendiente o esta inactivo.';
      ELSE
		 IF asistencia_activa > 0 THEN
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'El miembro ya esta adentro.';
		ELSE
			INSERT INTO asistencias (id_miembro, fechaEntrada, fechaSalida)
			VALUES (p_id_miembro, NOW(), NULL);
		END IF;
	end if;
   
END;//

CREATE PROCEDURE RegistrarSalida(IN p_id_miembro INT)
BEGIN
    DECLARE asistencia_activa INT DEFAULT 0;
    DECLARE id_asistencia INT DEFAULT 0;

    SELECT COUNT(*) INTO asistencia_activa FROM asistencias WHERE id_miembro = p_id_miembro 
    AND fechaSalida IS NULL;

    IF asistencia_activa = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El miembro ya registró su salida.';
    ELSE
        SELECT id_asistencias INTO id_asistencia FROM asistencias WHERE id_miembro = p_id_miembro
		AND fechaSalida IS NULL ORDER BY fechaEntrada DESC;
        
        UPDATE asistencias SET fechaSalida = NOW() WHERE id_asistencias = id_asistencia;
    END IF;
END;


create procedure mostrarAsistencias()
begin
SELECT m.miembro_name AS nombre,a.fechaEntrada AS entrada,a.fechaSalida AS salida FROM asistencias a
JOIN miembros m ON a.id_miembro = m.id_miembro;
end;//

INSERT INTO Mensualidades (mensualidad_name, mensualidad_precio,mensualidad_duracion)VALUES('Mensual', 80000,30),('trimensual',  120000,60),('Anual', 160000,365);//
CALL RegistrarMiembro(123456789,'Juan','Pérez','3105558899', '1998-04-12', 1);//










