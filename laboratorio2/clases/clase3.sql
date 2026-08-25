DECLARE
  -- Declarar "tipo de record"
  TYPE tr_emp IS RECORD (nombre EMPLOYEE.FIRST_NAME%TYPE,
                        apellido EMPLOYEE.LAST_NAME%TYPE,
                        depto DEPARTMENT.NAME%TYPE);
  -- "Tipo de tabla"
  TYPE tt_emp IS TABLE OF tr_emp INDEX BY BINARY_INTEGER; 
  
  -- Declarar variable de ese tipo
  r_emp tr_emp;
  t_emp tt_emp;
BEGIN
  r_emp.nombre := 'Jose';
  r_emp.apellido := 'Perez';
  DBMS_OUTPUT.PUT_LINE(r_emp.nombre || ' ' || r_emp.apellido);
  t_emp(1).nombre := 'Martin';
  t_emp(4).nombre := 'Lucia';
  t_emp(10).nombre := 'Jotaro';
  DBMS_OUTPUT.PUT_LINE(t_emp.next(4));
  
  SELECT E.FIRST_NAME, E.LAST_NAME, D.NAME
  INTO r_emp
  FROM EMPLOYEE E, DEPARTMENT D
  WHERE E.DEPARTMENT_ID = D.DEPARTMENT_ID
  AND E.EMPLOYEE_ID = 7369;

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    DBMS_OUTPUT.PUT_LINE('No existe el empleado');
  WHEN TOO_MANY_ROWS THEN
    DBMS_OUTPUT.PUT_LINE('Existe mas de un empleado ...');
END;

-- Ejercicio 15
DECLARE
  V_NUMERO INT;
  V_STEP INT := 0;
BEGIN
  V_NUMERO := &INGRESE_NUMERO;
  WHILE 2**V_STEP <= V_NUMERO LOOP
    DBMS_OUTPUT.PUT_LINE('Potencia: ' || 2**V_STEP);
    V_STEP := V_STEP + 1;
  END LOOP; 
END;

--
DECLARE
  v_dep_id department.department_id%type;
  v_emp_id employee.employee_id%type;
  e_fk exception;
  pragma exception_init(e_fk, -2291);
BEGIN
  v_dep_id := 10;
  v_emp_id := 7369;
  
  update employee
  set department_id = v_dep_id
  where employee_id = v_emp_id;

  if sql%rowcount = 0 then
    DBMS_OUTPUT.PUT_LINE('El empleado no existe');
  else
    DBMS_OUTPUT.PUT_LINE('Se actualizo correctamente');
  end if;
exception
  when e_fk then
    DBMS_OUTPUT.PUT_LINE('El departamento no existe');
  when others then
    DBMS_OUTPUT.PUT_LINE('Error inesperado ' || sqlerrm);
END;

--
DECLARE
  V_ID_DEPTO DEPARTMENT.DEPARTMENT_ID%TYPE;
  V_ID_LOCALIDAD DEPARTMENT.LOCATION_ID%TYPE;
  V_NOMBRE DEPARTMENT.NAME%TYPE;
  E_FK EXCEPTION;
  PRAGMA EXCEPTION_INIT(E_FK, -2291);
BEGIN
  V_ID_DEPTO := &INGRESE_ID_DEPTO;
  V_ID_LOCALIDAD := &INGRESE_ID_LOCALIDAD;
  V_NOMBRE := '&INGRESE_NOMBRE';

  INSERT INTO DEPARTMENT (DEPARTMENT_ID, LOCATION_ID, NAME)
  VALUES (V_ID_DEPTO, V_ID_LOCALIDAD, V_NOMBRE);

  DBMS_OUTPUT.PUT_LINE('Departamento creado exitosamente');
EXCEPTION
  WHEN DUP_VAL_ON_INDEX THEN
    DBMS_OUTPUT.PUT_LINE('El ID de departamento ' || V_ID_DEPTO || ' ya existe');
  WHEN E_FK THEN
    DBMS_OUTPUT.PUT_LINE('El ID de location ' || V_ID_LOCALIDAD || ' no existe');
END;

select * from location;
