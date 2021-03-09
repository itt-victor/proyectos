create database cuentasbbdd;
use cuentasbbdd;

create table cuentas
( numero_cuenta int   not null primary key,
saldo dec(11,2) not null,
tipo_cuenta varchar(45)
);

create table movimientos
( id_movimiento int not null auto_increment primary key,
id_cuenta int not null,
fecha datetime,
cantidad dec(11,2) not null,
operacion varchar(45),
foreign key(id_cuenta) references cuentas(numero_cuenta)
);

insert into cuentas values(1001,1000,'ahorro');
insert into cuentas values(1002,500,'corriente');

 /*
create user cajero_it identified by 'cajero';
grant all privileges on cuentasbbdd.*  to cajero_it;
 */