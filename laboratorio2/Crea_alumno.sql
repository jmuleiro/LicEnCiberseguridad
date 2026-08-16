REM Creación de usuarios 
REM Debe correrse desde una cuenta con Alumno/alumno
Undefine nro_de_legajo

Create user a&&nro_de_legajo  identified by a&nro_de_legajo
 default tablespace users
 temporary tablespace temp
 quota unlimited on users;

grant connect , resource, create procedure, create trigger  to a&nro_de_legajo;

Undefine nro_de_legajo
REM desde la cuenta creada correr el script tablas-bdemobld 