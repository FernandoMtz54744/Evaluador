/*
    Cortes Lopez Jaime Alejandro
    Martinez Martinez Fernando
    2CM3
    Proyecto Final de POO - Aplicador y generador de examenes
*/

drop database if exists Evaluador;
create database Evaluador;
use Evaluador;

create table Usuario(
	id_usuario int primary key,
    usuario nvarchar(20),
    pass nvarchar(20),
    tipo nvarchar(20));
    
create table Reactivo(
	id_reactivo int primary key,
    pregunta nvarchar(60),
    opcionA nvarchar(20),
    opcionB nvarchar(20),
    opcionC nvarchar(20),
    opcionD nvarchar(20),
    respuesta nvarchar(1));
    
create table Examen(
	id_examen int primary key,
    titulo nvarchar(20),
    id_usuario int references Usuario(id_usuario),
    fecha nvarchar(20),
    calificacion int);
    
create table User_Examen(
	id_usuario int references Usario(id_usuario),
    id_examen int references Examen(id_examen));
    
create table Examen_respondido(
	id_examen int references Examen(id_examen),
    id_reactivo int references Reactivo(id_reactivo),
    opc_usuario nvarchar(1));
    
delimiter **

drop procedure if exists UsuarioProcedure**
create procedure UsuarioProcedure(
	id_usr int,
    usr nvarchar(20),
    psw nvarchar(20),
    tip nvarchar(20),
    opc int)
BEGIN
declare existe int;
	/*Si opc = 1 Registra usuario*/
    if opc = 1 then 
		set id_usr = (select ifnull(max(id_usuario), 0) from Usuario) + 1;
		insert into usuario (id_usuario, usuario, pass, tipo) values (id_usr, usr, psw, tip);
        select id_usr as id_usuario;
	/*si opc = 2 inicia sesion*/
    elseif opc = 2 then
		set existe  = (select count(*) from Usuario where usuario = usr and pass = psw);
        if existe > 0 then
			select * from Usuario where usuario = usr and pass = psw;
		else
			select -1 as id_usuario, '' as tipo;
		end if;
	end if;
END**
    

drop procedure if exists ExamenProcedure**
create procedure ExamenProcedure(
	id_ex int,
	id_usr int,
    titu nvarchar(20),
    fech nvarchar(20),
    opc_usr nvarchar(1),
    id_react int,
    opc int)
BEGIN
declare x int;
declare id_random int;
declare calif int;
	/*Si opc = 1 entonces se crea un examen*/
    if opc = 1 then
		set id_ex = (select ifnull(max(id_examen), 0) from Examen) + 1;
		insert into Examen (id_examen, titulo, id_usuario, fecha, calificacion) values (id_ex, titu, id_usr, fech, -1);
        insert into User_Examen (id_examen, id_usuario) values(id_ex, id_usr);
        insert into Examen_respondido select id_ex, id_reactivo, 'n' from Reactivo ORDER BY RAND() Limit 10;	
		select id_ex as id_examen;
     /*Si opc = 2 consulta reactivos de examen [obtiene todos los id_reactivo de un examen]*/
    elseif opc = 2 then
		select * from Examen_respondido where id_examen = id_ex;
	/*Si opc = 3 Responde pregunta*/
    elseif opc = 3 then 
		update Examen_respondido set opc_usuario = opc_usr where id_examen = id_ex and id_reactivo = id_react;
        select 'actualizado' as msj;
	/* Si opc = 4 consulta examen*/
    elseif opc = 4 then
		select * from Examen where id_examen = id_ex;
	/*Si opc = 5 consulta todos los examenes de un usuario*/
    elseif opc = 5 then
		select * from User_Examen u join Examen e on e.id_examen = u.id_examen where u.id_usuario = id_usr;
	/*Si opc = 6 Califica el examen, devuelve la calificacion*/
    elseif opc = 6 then
		set calif = (select count(*) from Examen_respondido e
						inner join reactivo r on r.id_reactivo = e.id_reactivo 
                        where r.respuesta = e.opc_usuario and e.id_examen = id_ex);
		update Examen set calificacion = calif where id_examen = id_ex;
        select calif as calif;	
	elseif opc = 7 then /*Consulta los datos de un examen junto con la respuesta del alumno*/
		select * from Examen_respondido e inner join Reactivo r on e.id_reactivo = r.id_reactivo
			where e.id_examen = id_ex and e.id_reactivo = id_react;
    end if;
END**



drop procedure if exists reactivoProcedure**
create procedure ReactivoProcedure(
	id_react int,
    preg nvarchar(60),
    opcA nvarchar(20),
    opcB nvarchar(20),
    opcC nvarchar(20),
    opcD nvarchar(20),
    res nvarchar(1),
    opc int)
BEGIN
	/*Si opc = 1 registra reactivo*/
	if opc =1 then
		set id_react = (select ifnull(max(id_reactivo), 0) from Reactivo) + 1;
		insert into Reactivo (id_reactivo, pregunta, opcionA, opcionB, opcionC, opcionD, respuesta) values (id_react,preg, opcA, opcB, opcC, opcD, res);
		select id_react as id_reactivo;
    /*Si opc=2 consulta TODOS los reactivos*/
	elseif opc = 2 then
		select * from Reactivo;
	/*Si opc=3 consulta solo un reactivo*/
    elseif opc = 3 then
		select * from Reactivo where id_reactivo = id_react;
	/*si opc = 4 actualiza un reactivo*/
    elseif opc = 4 then
		update Reactivo set pregunta=preg, opcionA=opcA, opcionB=opcB, opcionC = opcC, opcionD=opcD, respuesta=res where id_reactivo=id_react;
        select 'actualizado' as msj;
	end if;
END**

delimiter ;
    
/*Insertando preguntas*/
insert into Reactivo values(1, 'Cual es el rio mas largo?', 'Nilo', 'Amazonas', 'Viena', 'Bravo', 'b');    
insert into Reactivo values(2, 'Que tipo de animal es la ballena?', 'oviparo', 'mamifero', 'invertebrado', 'terrestre', 'b');    
insert into Reactivo values(3, 'Cuantos huesos tiene el cuerpo humano?', '206', '215', '200', '201', 'a');    
insert into Reactivo values(4, 'Cuando acabo la segunda guerra mundial?', '1939', '1940', '1945', '1946', 'c');    
insert into Reactivo values(5, 'En que pais esta la torre de Pisa', 'Francia?', 'Suecia', 'Alemania', 'Italia', 'd');    
insert into Reactivo values(6, 'Que oceano es el mas grande?', 'Pacifico', 'Atlantico', 'Indico', 'Atlantico', 'a');    
insert into Reactivo values(7, 'En que anio llego Cristobal colon a America?', '1950', '1521', '1492', '1420', 'c');    
insert into Reactivo values(8, 'Cual es el pais mas grande?', 'EE. UU', 'Rusia', 'Canada', 'Brasil', 'b');    
insert into Reactivo values(9, 'Cual es el segundo planeta del sistema solar?', 'Marte', 'Tierra', 'Mercurio', 'Venus', 'd');    
insert into Reactivo values(10, 'En que pais se encuentra el Taj Mahal', 'Austria', 'India', 'EE. UU', 'Francia', 'b');    
insert into Reactivo values(11, 'Como se le llama al tringulo de lados iguales?', 'Isosceles', 'Escaleno', 'Equilatero', 'Cuadrado', 'c');    
insert into Reactivo values(12, 'Donde cayo la primera bomba atomica', 'Tokio', 'Nagazaki', 'El mar', 'Hiroshima', 'd');    
insert into Reactivo values(13, 'Cuantos anios tiene un lustro', '5', '10', '15', '20', 'a');    
insert into Reactivo values(14, 'En que anio empezo la primera guerra mundial', '1920', '1939', '1914', '1918', 'c');    
insert into Reactivo values(15, 'En que anio se produjo la revolucion francesa?', '1785', '1789', '1798', '1800', 'b');    
insert into Reactivo values(16, 'Cual es el organo mas grande del cuerpo humano?', 'Corazon', 'Pancreas', 'Cerebro', 'Piel', 'd');    
insert into Reactivo values(17, 'En que anio llego el primer humano a la luna?', '1950', '1969', '1970', '1955', 'b');    
insert into Reactivo values(18, 'Cuantos lados tiene un hexagono', '5', '6', '7', '12', 'b');    
insert into Reactivo values(19, 'En que anio cayo el muro de berlin', '1970', '1980', '1989', '1979', 'c');   
insert into Reactivo values(21, 'Donde esta la torre Eiffer', 'Italia', 'Alemania', 'Francia', 'Polonia', 'c');
insert into Reactivo values(22, 'En que continente esta Ecuador?', 'Europa', 'Asia', 'Oceania', 'America', 'd');
insert into Reactivo values(23, 'Capital de Uruguay', 'Santiago', 'Montevideo', 'Buenos aires', 'Madrid', 'b');
insert into Reactivo values(24, 'Cuanto equivale el numero PI', '9.81', '4.5', '3.14', '1.8182', 'c');
insert into Reactivo values(25, '¿Cual es el lugar más frio de la Tierra?', 'La Antartida', 'Rusia', 'Alaska', 'Greolandia', 'a');
insert into Reactivo values(26, 'Cuantos planetas tiene el sistema solar', '6', '7', '8', '9', 'c');
insert into Reactivo values(27, 'Cual es el pais mas pequenio', 'Vaticano', 'Andorra', 'Francia', 'Polonia', 'a');
insert into Reactivo values(28, 'Cuantos dientes tiene una persona adulta', '22', '32', '28', '34', 'b');
insert into Reactivo values(29, 'Capital de Alemania', 'Londres', 'Dublin', 'Berlin', 'Madrid', 'c');
insert into Reactivo values(30, 'Capital de Suecia', 'Quito', 'Moscu', 'Paris', 'Estocolmo', 'd');
insert into Reactivo values(31, 'Como se llamara el primer mamifero clonado', 'Dolly', 'Willy', 'Max', 'Laika', 'a');
insert into Reactivo values(32, 'Quien es el padre del psicoanalisis', 'Sigmund Freud', 'Carl Gustav Jung', 'Skinner', 'Carl Max', 'a');
insert into Reactivo values(33, 'Cual es el pais mas poblado', 'China', 'Rusia', 'India', 'Mexico', 'a');
insert into Reactivo values(34, 'En que guerra participo Juana de Arco', 'Indep. de Francia', 'G. civil espaniola', 'G. de los 100 anios', 'G. de Vietnam', 'c');
insert into Reactivo values(35, 'Que numero sigue? 2,10,12,16,17,18,19,...', '100', '200', '210', '20', 'b');
insert into Reactivo values(36, 'Quien fue el primer emperador romano?', 'César Augusto', 'Julio Cesar', 'Marco Aurelia', 'Marx', 'a');
insert into Reactivo values(37, 'En que isla murio Napoleon', 'Sicilia', 'Santa Elena', 'Creta', 'Galapagos', 'b');
insert into Reactivo values(38, 'Quien escribio la Odisea', 'Homero', 'Virgilio', 'Cervantes', 'Shakespeare', 'a');
insert into Reactivo values(39, 'A que se le denomina el septimo arte?', 'Pintura', 'Escultura', 'Cine', 'Deporte', 'c');
insert into Reactivo values(40, 'Que es un melomano?', 'Amante de la musica', 'Amante del cine', 'Amante de comics', 'Amante de melones', 'a');
insert into Reactivo values(41, 'Quien escibio Cien anios de Soledad', 'Mario V. Llosa', 'Gabriel G. Márquez', 'Camilo Cela', 'Emilio Pacheco', 'b');
insert into Reactivo values(42, 'Quien pinto La Noche Estrellada', 'Vincent van Gogh', 'Leonardo Da vinci', 'Vincent van Gogh', 'Velazquez', 'a');
insert into Reactivo values(43, 'Cual es el elemento quimico más abundante?', 'Oxigeno', 'Carbono', 'Helio', 'Nitrogeno', 'a');
insert into Reactivo values(44, 'Cual fue el primer metal que emplearon los humanos?', 'Acero', 'Oro', 'Cobre', 'Hierro', 'c');
insert into Reactivo values(45, 'Con que se fabricaba el pergamino?', 'piel humana', 'papel', 'piel de animales', 'hojas de arboles', 'c');
insert into Reactivo values(46, 'Cual es el primer numero primo?', '0', '1', '2', '3', 'c');
insert into Reactivo values(47, 'Cual es el simbolo del oro', 'Or', 'Au', 'Ao', 'Eu', 'b');
insert into Reactivo values(48, 'Cual es la moneda en Reino Unido?', 'Dolar', 'Bolivar', 'Libra', 'Yen', 'c');
insert into Reactivo values(49, 'Que porcentaje de agua esta compuesto el cuerpo humano?', '70%', '60%', '50%', '80%', 'a');
insert into Reactivo values(50, 'Quien pinto La ultima cena?', 'Vicent Van Gogh', 'Leonardo Da Vinci', 'Pablo Picasso', 'Diego de Velasquez', 'b');


 

