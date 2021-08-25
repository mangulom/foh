prompt PL/SQL Developer Export User Objects for user MAURICIO@ZEIT.WORLD
prompt Created by PLOP on miÃ©rcoles, 25 de Agosto de 2021
set define off
spool Scripts.log

prompt
prompt Creating table CLIENTE
prompt ======================
prompt
create table MAURICIO.CLIENTE
(
  id        INTEGER not null,
  nombres   VARCHAR2(50) not null,
  apellidos VARCHAR2(50) not null,
  dni       VARCHAR2(50) not null,
  telefono  VARCHAR2(20),
  mail      VARCHAR2(50)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MAURICIO.CLIENTE
  add constraint PK_CLIENTE primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table DETALLE_VENTA
prompt ============================
prompt
create table MAURICIO.DETALLE_VENTA
(
  id          INTEGER not null,
  id_venta    INTEGER not null,
  id_producto INTEGER not null,
  cantidad    INTEGER not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table PRODUCTO
prompt =======================
prompt
create table MAURICIO.PRODUCTO
(
  id     INTEGER not null,
  nombre VARCHAR2(200) not null,
  precio NUMBER(10,2) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MAURICIO.PRODUCTO
  add constraint PK_1 primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table VENTA
prompt ====================
prompt
create table MAURICIO.VENTA
(
  id         INTEGER not null,
  id_cliente INTEGER not null,
  fecha      DATE not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MAURICIO.VENTA
  add constraint PK_VENTA primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MAURICIO.VENTA
  add constraint FK_1 foreign key (ID_CLIENTE)
  references MAURICIO.CLIENTE (ID);

prompt
prompt Creating package PCK_FOH_CLIENTE
prompt ================================
prompt
create or replace noneditionable package mauricio.PCK_FOH_CLIENTE is
  PROCEDURE PRC_CONSULTAR_CLIENTES(i_npagina    in INTEGER,
                                   i_nregistros in INTEGER,
                                   o_cursor     OUT SYS_REFCURSOR,
                                   o_retorno    OUT INTEGER,
                                   o_mensaje    OUT VARCHAR2,
                                   o_sqlerrm    OUT VARCHAR2);

  PROCEDURE PRC_REGISTRAR_CLIENTE(i_nombres   IN VARCHAR2,
                                  i_apellidos IN VARCHAR2,
                                  i_dni       IN VARCHAR2,
                                  i_telefono  IN VARCHAR2,
                                  i_mail      IN VARCHAR2,
                                  o_retorno   OUT INTEGER,
                                  o_mensaje   OUT VARCHAR2,
                                  o_sqlerrm   OUT VARCHAR2);

  PROCEDURE PRC_REGISTRAR_PRODUCTO(i_nombre  IN VARCHAR2,
                                   i_precio  IN NUMBER,
                                   o_retorno OUT INTEGER,
                                   o_mensaje OUT VARCHAR2,
                                   o_sqlerrm OUT VARCHAR2);

  PROCEDURE PRC_CONSULTAR_PRODUCTOS(i_npagina    IN INTEGER,
                                    i_nregistros IN INTEGER,
                                    o_cursor     OUT SYS_REFCURSOR,
                                    o_retorno    OUT INTEGER,
                                    o_mensaje    OUT VARCHAR2,
                                    o_sqlerrm    OUT VARCHAR2);

  PROCEDURE PRC_UPDATE_PRODUCTO(i_id      IN INTEGER,
                                i_nombre  IN VARCHAR2,
                                i_precio  IN NUMBER,
                                o_retorno OUT INTEGER,
                                o_mensaje OUT VARCHAR2,
                                o_sqlerrm OUT VARCHAR2);

  PROCEDURE PRC_ELIMINAR_PRODUCTO(i_id      IN INTEGER,
                                  o_retorno OUT INTEGER,
                                  o_mensaje OUT VARCHAR2,
                                  o_sqlerrm OUT VARCHAR2);

  PROCEDURE PRC_CONSULTAR_VENTAS_CAB(i_id         IN INTEGER,
                                     i_fecha      IN VARCHAR2,
                                     i_npagina    IN INTEGER,
                                     i_nregistros IN INTEGER,
                                     o_cursor     OUT SYS_REFCURSOR,
                                     o_retorno    OUT INTEGER,
                                     o_mensaje    OUT VARCHAR2,
                                     o_sqlerrm    OUT VARCHAR2);

  PROCEDURE PRC_CONSULTAR_VENTAS_DET(i_id_venta         IN INTEGER,
                                     i_npagina    IN INTEGER,
                                     i_nregistros IN INTEGER,
                                     o_cursor     OUT SYS_REFCURSOR,
                                     o_retorno    OUT INTEGER,
                                     o_mensaje    OUT VARCHAR2,
                                     o_sqlerrm    OUT VARCHAR2);

  PROCEDURE PRC_REGISTRAR_VENTA_CAB(i_id_cliente IN INTEGER,
                                    o_id         OUT INTEGER,
                                    o_retorno    OUT INTEGER,
                                    o_mensaje    OUT VARCHAR2,
                                    o_sqlerrm    OUT VARCHAR2);

  PROCEDURE PRC_REGISTRAR_VENTA_DET(i_id_venta    IN INTEGER,
                                    i_id_producto IN INTEGER,
                                    i_cantidad    IN INTEGER,
                                    o_retorno     OUT INTEGER,
                                    o_mensaje     OUT VARCHAR2,
                                    o_sqlerrm     OUT VARCHAR2);

end PCK_FOH_CLIENTE;
/

prompt
prompt Creating package body PCK_FOH_CLIENTE
prompt =====================================
prompt
create or replace noneditionable package body mauricio.PCK_FOH_CLIENTE is
  PROCEDURE PRC_CONSULTAR_CLIENTES(i_npagina    IN INTEGER,
                                   i_nregistros IN INTEGER,
                                   o_cursor     OUT SYS_REFCURSOR,
                                   o_retorno    OUT INTEGER,
                                   o_mensaje    OUT VARCHAR2,
                                   o_sqlerrm    OUT VARCHAR2) AS
  
  BEGIN
    o_retorno := 0;
    o_mensaje := 'Transacción Satisfactoria';
    o_sqlerrm := '';
    open o_cursor for
      SELECT *
        FROM (SELECT r.*, ROWNUM RNUM, COUNT(*) OVER() RESULT_COUNT
                FROM (SELECT *
                        FROM (SELECT * FROM CLIENTE)
                       ORDER BY nombres, apellidos ASC) R)
      
       WHERE RNUM between ((i_npagina - 1) * i_nregistros) + 1 and
             i_npagina * i_nregistros;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      o_retorno := 1;
      o_mensaje := SQLERRM;
      o_sqlerrm := SQLERRM;
  END PRC_CONSULTAR_CLIENTES;

  PROCEDURE PRC_REGISTRAR_CLIENTE(i_nombres   IN VARCHAR2,
                                  i_apellidos IN VARCHAR2,
                                  i_dni       IN VARCHAR2,
                                  i_telefono  IN VARCHAR2,
                                  i_mail      IN VARCHAR2,
                                  o_retorno   OUT INTEGER,
                                  o_mensaje   OUT VARCHAR2,
                                  o_sqlerrm   OUT VARCHAR2) AS
    secuencial_cliente NUMBER := 0;
  BEGIN
    o_retorno := 0;
    o_mensaje := 'Transacción Satisfactoria';
    o_sqlerrm := '';
  
    SELECT NVL(MAX(ID), 0) + 1 INTO secuencial_cliente FROM cliente;
  
    INSERT INTO CLIENTE
    VALUES
      (secuencial_cliente,
       i_nombres,
       i_apellidos,
       i_dni,
       i_telefono,
       i_mail);
    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      o_retorno := 1;
      o_mensaje := SQLERRM;
      o_sqlerrm := SQLERRM;
  END PRC_REGISTRAR_CLIENTE;

  PROCEDURE PRC_REGISTRAR_PRODUCTO(i_nombre  IN VARCHAR2,
                                   i_precio  IN NUMBER,
                                   o_retorno OUT INTEGER,
                                   o_mensaje OUT VARCHAR2,
                                   o_sqlerrm OUT VARCHAR2) AS
    secuencial_producto NUMBER := 0;
  BEGIN
    o_retorno := 0;
    o_mensaje := 'Transacción Satisfactoria';
    o_sqlerrm := '';
  
    SELECT NVL(MAX(ID), 0) + 1 INTO secuencial_producto FROM producto;
  
    INSERT INTO PRODUCTO VALUES (secuencial_producto, i_nombre, i_precio);
    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      o_retorno := 1;
      o_mensaje := SQLERRM;
      o_sqlerrm := SQLERRM;
    
  END PRC_REGISTRAR_PRODUCTO;

  PROCEDURE PRC_UPDATE_PRODUCTO(i_id      IN INTEGER,
                                i_nombre  IN VARCHAR2,
                                i_precio  IN NUMBER,
                                o_retorno OUT INTEGER,
                                o_mensaje OUT VARCHAR2,
                                o_sqlerrm OUT VARCHAR2) AS
  BEGIN
    o_retorno := 0;
    o_mensaje := 'Transacción Satisfactoria';
    o_sqlerrm := '';
    UPDATE PRODUCTO
       SET nombre = i_nombre, precio = i_precio
     where id = i_id;
    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      o_retorno := 1;
      o_mensaje := SQLERRM;
      o_sqlerrm := SQLERRM;
    
  END PRC_UPDATE_PRODUCTO;

  PROCEDURE PRC_ELIMINAR_PRODUCTO(i_id      IN INTEGER,
                                  o_retorno OUT INTEGER,
                                  o_mensaje OUT VARCHAR2,
                                  o_sqlerrm OUT VARCHAR2) AS
  BEGIN
    o_retorno := 0;
    o_mensaje := 'Transacción Satisfactoria';
    o_sqlerrm := '';
    DELETE PRODUCTO where id = i_id;
    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      o_retorno := 1;
      o_mensaje := SQLERRM;
      o_sqlerrm := SQLERRM;
    
  END PRC_ELIMINAR_PRODUCTO;

  PROCEDURE PRC_CONSULTAR_PRODUCTOS(i_npagina    IN INTEGER,
                                    i_nregistros IN INTEGER,
                                    o_cursor     OUT SYS_REFCURSOR,
                                    o_retorno    OUT INTEGER,
                                    o_mensaje    OUT VARCHAR2,
                                    o_sqlerrm    OUT VARCHAR2) AS
  
  BEGIN
    o_retorno := 0;
    o_mensaje := 'Transacción Satisfactoria';
    o_sqlerrm := '';
    open o_cursor for
      SELECT *
        FROM (SELECT r.*, ROWNUM RNUM, COUNT(*) OVER() RESULT_COUNT
                FROM (SELECT *
                        FROM (SELECT * FROM PRODUCTO)
                       ORDER BY nombre ASC) R)
      
       WHERE RNUM between ((i_npagina - 1) * i_nregistros) + 1 and
             i_npagina * i_nregistros;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      o_retorno := 1;
      o_mensaje := SQLERRM;
      o_sqlerrm := SQLERRM;
  END PRC_CONSULTAR_PRODUCTOS;

  PROCEDURE PRC_CONSULTAR_VENTAS_CAB(i_id         IN INTEGER,
                                     i_fecha      IN VARCHAR2,
                                     i_npagina    IN INTEGER,
                                     i_nregistros IN INTEGER,
                                     o_cursor     OUT SYS_REFCURSOR,
                                     o_retorno    OUT INTEGER,
                                     o_mensaje    OUT VARCHAR2,
                                     o_sqlerrm    OUT VARCHAR2) AS
  BEGIN
    o_retorno := 0;
    o_mensaje := 'Transacción Satisfactoria';
    o_sqlerrm := '';
    open o_cursor for
      select *
        FROM (SELECT r.*, ROWNUM RNUM, COUNT(*) OVER() RESULT_COUNT
                FROM (SELECT *
                        FROM (
                              
                              SELECT V.ID ID_VENTA_CABECERA,
                                      V.FECHA,
                                      V.ID_CLIENTE,
                                      C.NOMBRES,
                                      C.APELLIDOS
                                FROM VENTA V
                               LEFT OUTER JOIN CLIENTE C
                                  ON C.ID = V.ID_CLIENTE
                               WHERE 
                               (i_id > 0 and v.id = i_id)
                                 OR (i_fecha is not null and to_DATE(v.fecha, 'DD/MM/RRRR') >= TO_DATE(i_fecha,'DD/MM/RRRR')
                                     )
)
                       ORDER BY ID_VENTA_CABECERA ASC) R)
      
       WHERE RNUM between ((i_npagina - 1) * i_nregistros) + 1 and
             i_npagina * i_nregistros;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      o_retorno := 1;
      o_mensaje := SQLERRM;
      o_sqlerrm := SQLERRM;
  END PRC_CONSULTAR_VENTAS_CAB;

  PROCEDURE PRC_CONSULTAR_VENTAS_DET(i_id_venta         IN INTEGER,
                                     i_npagina    IN INTEGER,
                                     i_nregistros IN INTEGER,
                                     o_cursor     OUT SYS_REFCURSOR,
                                     o_retorno    OUT INTEGER,
                                     o_mensaje    OUT VARCHAR2,
                                     o_sqlerrm    OUT VARCHAR2) AS
  BEGIN
    o_retorno := 0;
    o_mensaje := 'Transacción Satisfactoria';
    o_sqlerrm := '';
    open o_cursor for
      select *
        FROM (SELECT r.*, ROWNUM RNUM, COUNT(*) OVER() RESULT_COUNT
                FROM (SELECT *
                        FROM (
                              
                              SELECT D.ID ID_DETALLE_VENTA,
                                      D.ID_PRODUCTO,
                                      D.CANTIDAD,
                                      P.NOMBRE,
                                      P.PRECIO
                                FROM DETALLE_VENTA D
                               INNER JOIN PRODUCTO P
                                  ON P.ID = D.ID_PRODUCTO
                               WHERE D.ID_VENTA = i_id_venta
                                 )
                       ORDER BY ID_DETALLE_VENTA ASC) R)
      
       WHERE RNUM between ((i_npagina - 1) * i_nregistros) + 1 and
             i_npagina * i_nregistros;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      o_retorno := 1;
      o_mensaje := SQLERRM;
      o_sqlerrm := SQLERRM;
  END PRC_CONSULTAR_VENTAS_DET;

  PROCEDURE PRC_REGISTRAR_VENTA_CAB(i_id_cliente IN INTEGER,
                                    o_id         OUT INTEGER,
                                    o_retorno    OUT INTEGER,
                                    o_mensaje    OUT VARCHAR2,
                                    o_sqlerrm    OUT VARCHAR2) AS
    secuencial_venta NUMBER := 0;
  BEGIN
    o_retorno := 0;
    o_mensaje := 'Transacción Satisfactoria';
    o_sqlerrm := '';
  
    SELECT NVL(MAX(ID), 0) + 1 INTO secuencial_venta FROM venta;
    o_id := secuencial_venta;
    INSERT INTO VENTA VALUES (secuencial_venta, i_id_cliente, sysdate);
    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      o_retorno := 1;
      o_mensaje := SQLERRM;
      o_sqlerrm := SQLERRM;
  END PRC_REGISTRAR_VENTA_CAB;

  PROCEDURE PRC_REGISTRAR_VENTA_DET(i_id_venta    IN INTEGER,
                                    i_id_producto IN INTEGER,
                                    i_cantidad    IN INTEGER,
                                    o_retorno     OUT INTEGER,
                                    o_mensaje     OUT VARCHAR2,
                                    o_sqlerrm     OUT VARCHAR2) AS
    secuencial_venta_det NUMBER := 0;
  BEGIN
    o_retorno := 0;
    o_mensaje := 'Transacción Satisfactoria';
    o_sqlerrm := '';
  
    SELECT NVL(MAX(ID), 0) + 1
      INTO secuencial_venta_det
      FROM detalle_venta;
  
    INSERT INTO DETALLE_VENTA
    VALUES
      (secuencial_venta_det, i_id_venta, i_id_producto, i_cantidad);
    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      o_retorno := 1;
      o_mensaje := SQLERRM;
      o_sqlerrm := SQLERRM;
    
  END PRC_REGISTRAR_VENTA_DET;

end PCK_FOH_CLIENTE;
/


prompt Done
spool off
set define on
