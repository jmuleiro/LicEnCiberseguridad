-- U1-E22
DECLARE
  -- Variables
  v_first_name EMPLOYEE.FIRST_NAME%TYPE;
  v_last_name EMPLOYEE.LAST_NAME%TYPE;
  v_new_salary EMPLOYEE.SALARY%TYPE;
  v_manager_id EMPLOYEE.MANAGER_ID%TYPE;
  v_manager_salary EMPLOYEE.SALARY%TYPE;
  -- Excepciones
  e_no_manager EXCEPTION;
  e_salary_too_high EXCEPTION;
  e_salary_too_large EXCEPTION;
  PRAGMA EXCEPTION_INIT(e_salary_too_large, -6502);
BEGIN
  v_first_name := '&INGRESE_NOMBRE';
  v_last_name := '&INGRESE_APELLIDO';
  v_new_salary := '&INGRESE_NUEVO_SUELDO';

  -- Obtener manager ID
  SELECT MANAGER_ID
  INTO v_manager_id
  FROM EMPLOYEE
  WHERE FIRST_NAME = v_first_name
    AND LAST_NAME = v_last_name;
  
  -- Validar si no tiene Jefe
  IF v_manager_id IS NULL THEN
    RAISE e_no_manager;
  END IF;

  -- Obtener salario del Jefe
  SELECT SALARY
  INTO v_manager_salary
  FROM EMPLOYEE
  WHERE EMPLOYEE_ID = v_manager_id;

  -- Validar que el salario nuevo no supere
  -- al del Jefe
  IF v_manager_salary < v_new_salary THEN
    RAISE e_salary_too_high;
  END IF;

  -- Actualizar salario del empleado
  UPDATE EMPLOYEE
  SET SALARY = V_NEW_SALARY
  WHERE FIRST_NAME = v_first_name
    AND LAST_NAME = v_last_name;
  
  -- Informar resultado exitoso
  DBMS_OUTPUT.PUT_LINE('Salario actualizado correctamente.');
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    DBMS_OUTPUT.PUT_LINE('No se encontró al empleado.');
  WHEN e_no_manager THEN
    DBMS_OUTPUT.PUT_LINE('El empleado no tiene jefe.');
  WHEN e_salary_too_high THEN
    DBMS_OUTPUT.PUT_LINE('El nuevo salario del empleado supera al de su jefe.');
  WHEN e_salary_too_large THEN
    DBMS_OUTPUT.PUT_LINE('El salario es demasiado grande.');
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error inesperado: ' || SQLERRM);
END;
